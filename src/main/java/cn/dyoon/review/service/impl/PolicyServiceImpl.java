package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.constant.ResumptionReviewConstant;
import cn.dyoon.review.common.enums.PublishStatusEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.PolicyListParam;
import cn.dyoon.review.controller.param.PolicyParam;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
        policyInfoMapper.deleteById(policyId);
        this.deleteFiles(policyDocumentMapper.selectByPolicyId(policyId));
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
        PolicyDocumentDO document = policyDocumentMapper.selectById(fileId);
        FileUtil.readThenWriteResponse(document.getPath(), document.getVirtualName(), document.getFileName(), response);
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

    @Override
    public void deleteFile(String fileId) {
        PolicyDocumentDO policyDocument = policyDocumentMapper.selectById(fileId);
        if (null == policyDocument) {
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_NOT_EXISTS);
        }
        this.deleteFiles(Collections.singletonList(policyDocument));
    }

    @Override
    public void update(String policyId, PolicyParam param) {
        PolicyInfoDO policyInfo = policyInfoMapper.selectById(policyId);
        if (null == policyInfo) {
            throw new BusinessException(BaseExceptionEnum.POLICY_NOT_EXISTS);
        }

        if (PublishStatusEnum.PUBLISH.getCode() == policyInfo.getStatus()) {
            throw new BusinessException(BaseExceptionEnum.POLICY_HAS_PUBLISHED);
        }

        policyInfo.setTitle(param.getTitle());
        policyInfo.setDesc(param.getDesc());
        policyInfoMapper.updateById(policyInfo);
    }

    private void uploadFiles(String username, List<MultipartFile> files, String policyId) {
        if (files.isEmpty()) {
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_IS_EMPTY);
        }
        String filePath = ResumptionReviewConstant.POLICY_PATH;
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectory(Paths.get(ResumptionReviewConstant.BASE_PATH));
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
                    //如果上传了同名文件则删除之前的文件
                    PolicyDocumentDO preFile = policyDocumentMapper.findSameFile(policyId, actualName);
                    if (preFile != null) {
                        this.deleteFiles(Collections.singletonList(preFile));
                    }

                    saveMetadata(username, policyId, file.getSize(), actualName, filePath, virtualName);

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

    /**
     * 批量删除复工文件
     *
     * @param documents
     */
    private void deleteFiles(List<PolicyDocumentDO> documents) {
        documents.forEach(document -> {
            try {
                boolean delete = Files.deleteIfExists(Paths.get(document.getPath(), document.getVirtualName()));
                if (delete) {
                    policyDocumentMapper.deleteById(document.getId());
                }
            } catch (IOException e) {
                log.error("[删除文件] - 失败", e);
                throw new BusinessException(BaseExceptionEnum.DELETE_FILES_FAILURE);
            }
        });
    }
}
