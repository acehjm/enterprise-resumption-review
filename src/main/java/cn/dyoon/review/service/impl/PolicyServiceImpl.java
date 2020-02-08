package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.constant.ResumptionReviewConstant;
import cn.dyoon.review.common.enums.PublishStatusEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.PolicyListParam;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import cn.dyoon.review.controller.vo.PolicyListVO;
import cn.dyoon.review.domain.PolicyDocumentMapper;
import cn.dyoon.review.domain.PolicyInfoMapper;
import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import cn.dyoon.review.domain.entity.PolicyInfoDO;
import cn.dyoon.review.service.PolicyService;
import cn.dyoon.review.util.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyInfoMapper policyInfoMapper;
    @Autowired
    private PolicyDocumentMapper policyDocumentMapper;
    @Autowired
    private HttpServletResponse response;

    @Override
    public PolicyInfoVO create(String title, String desc, String uploadUserName, List<MultipartFile> files) {
        boolean exists = policyInfoMapper.exists(title);
        if (exists) {
            throw new BusinessException(BaseExceptionEnum.POLICY_TITLE_HAS_EXISTS);
        }

        PolicyInfoDO policyInfoDO = new PolicyInfoDO();
        policyInfoDO.setTitle(title);
        policyInfoDO.setDesc(desc);
        policyInfoDO.setCreateTime(LocalDateTime.now());
        policyInfoDO.setStatus(PublishStatusEnum.UNPUBLISHED.getCode());
        policyInfoMapper.insert(policyInfoDO);

        //保存文件
        uploadFiles(uploadUserName, files, policyInfoDO.getId());

        List<PolicyDocumentDO> policyFiles = policyDocumentMapper.selectByPolicyId(policyInfoDO.getId());

        return new PolicyInfoVO(policyInfoDO, policyFiles);
    }

    @Override
    public PolicyInfoVO findById(String policyId) {
        PolicyInfoDO policyInfo = policyInfoMapper.selectById(policyId);
        if (null == policyInfo) {
            throw new BusinessException(BaseExceptionEnum.POLICY_NOT_EXISTS);
        }
        List<PolicyDocumentDO> files = policyDocumentMapper.selectByPolicyId(policyId);
        return new PolicyInfoVO(policyInfo, files);
    }

    @Override
    public void deleteById(String policyId) {
        PolicyInfoDO policyInfo = policyInfoMapper.selectById(policyId);
        if (null == policyInfo) {
            return;
        }
        List<PolicyDocumentDO> policyDocumentDOS = policyDocumentMapper.selectByPolicyId(policyId);
        Optional<PolicyDocumentDO> policyDocumentDO = policyDocumentDOS.stream().findAny();
        String dictoryPath = policyDocumentDO.get().getPath();
        FileUtil.DeleteFolder(dictoryPath);
        policyInfoMapper.deleteById(policyId);
        policyDocumentMapper.deleteByPolicyId(policyId);
    }

    @Override
    public PolicyInfoVO publish(PolicyPublishParam param) {
        PolicyInfoDO policyInfoDO = new PolicyInfoDO();
        policyInfoDO.setStatus(param.getStatus());
        policyInfoDO.setReleaseTime(LocalDateTime.now());
        policyInfoMapper.publish(policyInfoDO, param.getPolicyId());
        policyInfoDO = policyInfoMapper.selectById(param.getPolicyId());
        List<PolicyDocumentDO> files = policyDocumentMapper.selectByPolicyId(param.getPolicyId());
        return new PolicyInfoVO(policyInfoDO, files);
    }

    @Override
    public void download(String fileId) {
        PolicyDocumentDO policyDocument = policyDocumentMapper.selectById(fileId);
        Path path = Paths.get(policyDocument.getPath(), policyDocument.getVirtualName());
        boolean exists = Files.exists(path);
        if (!exists) {
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_NOT_EXISTS);
        }
        try (OutputStream os = response.getOutputStream()) {
            byte[] bytes = Files.readAllBytes(path);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(policyDocument.getFileName(), "utf-8"));

            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            log.error("[下载文件] - 失败", e);
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_FAILURE);
        }
    }

    @Override
    public PageVO<PolicyListVO> getPage(PolicyListParam param) {
        IPage<PolicyInfoDO> page = policyInfoMapper.findPageByCondition(param);
        List<PolicyListVO> collect = page.getRecords().stream()
                .map(PolicyListVO::new)
                .collect(Collectors.toList());
        return new PageVO<>(param.getPageNo(), param.getPageSize(), page.getTotal(), collect);
    }

    @Override
    public void upload(String policyId, String uploadUserName, List<MultipartFile> files) {
        PolicyInfoDO policyInfo = policyInfoMapper.selectById(policyId);
        if (null == policyInfo) {
            throw new BusinessException(BaseExceptionEnum.POLICY_NOT_EXISTS);
        }
        uploadFiles(uploadUserName, files, policyInfo.getId());
    }

    private void uploadFiles(String username, List<MultipartFile> files, String policyInfoId) {
        if (files.isEmpty()) {
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_IS_EMPTY);
        }
        String filePath = ResumptionReviewConstant.POLICY_PATH;
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            files.forEach(file -> {
                if (file.isEmpty()) {
                    // 处理下一个文件
                    return;
                }
                try {
                    String actualName = file.getOriginalFilename();
                    String virtualName = IdWorker.get32UUID();
                    saveMetadata(username, policyInfoId, file.getSize(), actualName, filePath, virtualName);

                    Files.write(Paths.get(filePath, virtualName), file.getBytes());
                } catch (IOException e) {
                    log.error("[上传文件] - 失败", e);
                    throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_FAILURE);
                }
            });
        } catch (Exception e) {
            log.error("[上传文件] - 失败", e);
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_FAILURE);
        }
    }

    private void saveMetadata(String username, String policyId, long fileSize, String fileName,
                              String filePath, String virtualName) {
        PolicyDocumentDO policyDocument = new PolicyDocumentDO();
        policyDocument.setCreateTime(LocalDateTime.now());
        policyDocument.setFileName(fileName);
        policyDocument.setVirtualName(virtualName);
        policyDocument.setFileSize((double) fileSize / 1024);
        policyDocument.setPolicyId(policyId);
        policyDocument.setPath(filePath);
        policyDocument.setUploadUserName(username);
        policyDocumentMapper.insert(policyDocument);
    }
}
