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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String filePath = ResumptionReviewConstant.POLICY_PATH + title + "\\";
        uploadFiles(uploadUserName, files, policyInfoDO, filePath);

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
        PolicyDocumentDO policyDocumentDO = policyDocumentMapper.selectById(fileId);
        FileUtil.downloadFile(response, policyDocumentDO.getFileName(), policyDocumentDO.getPath());
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

        String filePath = ResumptionReviewConstant.POLICY_PATH + policyInfo.getTitle() + "\\";
        uploadFiles(uploadUserName, files, policyInfo, filePath);
    }

    private void uploadFiles(String uploadUserName, List<MultipartFile> files, PolicyInfoDO policyInfo, String filePath) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                throw new BusinessException("500", "文件为空");
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                PolicyDocumentDO policyDocumentDO = new PolicyDocumentDO();
                policyDocumentDO.setCreateTime(LocalDateTime.now());
                policyDocumentDO.setFileName(fileName);
                policyDocumentDO.setFileSize((double) file.getSize()/1024);
                policyDocumentDO.setPolicyId(policyInfo.getId());
                policyDocumentDO.setPath(filePath);
                policyDocumentDO.setUploadUserName(uploadUserName);
                policyDocumentMapper.insert(policyDocumentDO);

                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("500", "上传文件失败");
            }
        }
    }
}
