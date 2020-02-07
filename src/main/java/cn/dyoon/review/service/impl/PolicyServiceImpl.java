package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import cn.dyoon.review.domain.PolicyDocumentMapper;
import cn.dyoon.review.domain.PolicyInfoMapper;
import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import cn.dyoon.review.domain.entity.PolicyInfoDO;
import cn.dyoon.review.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyInfoMapper policyInfoMapper;
    @Autowired
    private PolicyDocumentMapper policyDocumentMapper;

    @Override
    public PolicyInfoVO create(String title, String desc, String uploadUserName, List<MultipartFile> files) {
        PolicyInfoDO policyInfoDO = new PolicyInfoDO();
        policyInfoDO.setTitle(title);
        policyInfoDO.setDesc(desc);
        policyInfoDO.setCreateTime(LocalDateTime.now());
        policyInfoDO.setStatus(0);
        policyInfoMapper.insert(policyInfoDO);

        //保存文件
        String filePath = "D:\\";
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
                policyDocumentDO.setPolicyId(policyInfoDO.getId());
                policyDocumentDO.setPath(filePath);
                policyDocumentDO.setUploadUserName(uploadUserName);
                policyDocumentMapper.insert(policyDocumentDO);

                file.transferTo(dest);
            } catch (IOException e) {
                throw new BusinessException("500", "上传文件失败");
            }
        }

        PolicyInfoVO policyInfoVO = new PolicyInfoVO();
        policyInfoVO.setId(policyInfoDO.getId());
        policyInfoVO.setTitle(policyInfoDO.getTitle());
        policyInfoVO.setDesc(policyInfoDO.getDesc());
        policyInfoVO.setCreateDate(policyInfoDO.getCreateTime());
        policyInfoVO.setReleaseDate(policyInfoVO.getReleaseDate());
        policyInfoVO.setStatus(policyInfoDO.getStatus());
        List<PolicyInfoVO.File> policyDocuments = new ArrayList<>();
        List<PolicyDocumentDO> policyDocumentDOS = policyDocumentMapper.selectByPolicyId(policyInfoDO.getId());
        for (PolicyDocumentDO policyDocumentDO : policyDocumentDOS) {
            PolicyInfoVO.File file = new PolicyInfoVO.File();
            file.setFileId(policyDocumentDO.getId());
            file.setFileName(policyDocumentDO.getFileName());
            file.setFileSize(policyDocumentDO.getFileSize());
            file.setUploadTime(policyDocumentDO.getCreateTime());
            file.setUploadUser(policyDocumentDO.getUploadUserName());
            policyDocuments.add(file);
        }
        policyInfoVO.setFiles(policyDocuments);
        return policyInfoVO;
    }

    @Override
    public PolicyInfoVO findById(String policyId) {
        PolicyInfoDO policyInfoDO = policyInfoMapper.selectById(policyId);
        PolicyInfoVO policyInfoVO = new PolicyInfoVO();
        policyInfoVO.setId(policyInfoDO.getId());
        policyInfoVO.setTitle(policyInfoDO.getTitle());
        policyInfoVO.setDesc(policyInfoDO.getDesc());
        policyInfoVO.setCreateDate(policyInfoDO.getCreateTime());
        policyInfoVO.setReleaseDate(policyInfoVO.getReleaseDate());
        policyInfoVO.setStatus(policyInfoDO.getStatus());

        List<PolicyInfoVO.File> policyDocuments = new ArrayList<>();
        List<PolicyDocumentDO> policyDocumentDOS = policyDocumentMapper.selectByPolicyId(policyId);
        for (PolicyDocumentDO policyDocumentDO : policyDocumentDOS) {
            PolicyInfoVO.File file = new PolicyInfoVO.File();
            file.setFileId(policyDocumentDO.getId());
            file.setFileName(policyDocumentDO.getFileName());
            file.setFileSize(policyDocumentDO.getFileSize());
            file.setUploadTime(policyDocumentDO.getCreateTime());
            file.setUploadUser(policyDocumentDO.getUploadUserName());
            policyDocuments.add(file);
        }
        policyInfoVO.setFiles(policyDocuments);

        return policyInfoVO;
    }

    @Override
    public void deleteById(String policyId) {

    }

    @Override
    public void publish(PolicyPublishParam param) {

    }
}
