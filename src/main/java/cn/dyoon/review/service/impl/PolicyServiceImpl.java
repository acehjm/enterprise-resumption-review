package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.enums.PublishStatusEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import cn.dyoon.review.domain.PolicyDocumentMapper;
import cn.dyoon.review.domain.PolicyInfoMapper;
import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import cn.dyoon.review.domain.entity.PolicyInfoDO;
import cn.dyoon.review.service.PolicyService;
import cn.dyoon.review.util.base.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String filePath = "D:\\" + title + "\\";
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
                policyDocumentDO.setPolicyId(policyInfoDO.getId());
                policyDocumentDO.setPath(filePath);
                policyDocumentDO.setUploadUserName(uploadUserName);
                policyDocumentMapper.insert(policyDocumentDO);

                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("500", "上传文件失败");
            }
        }

        return getPolicyInfoVO(policyInfoDO, policyInfoDO.getId());
    }

    @Override
    public PolicyInfoVO findById(String policyId) {
        PolicyInfoDO policyInfoDO = policyInfoMapper.selectById(policyId);
        return getPolicyInfoVO(policyInfoDO, policyId);
    }

    @Override
    public void deleteById(String policyId) {
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
        policyInfoMapper.publish(policyInfoDO, param.getPolicyId());
        policyInfoDO = policyInfoMapper.selectById(param.getPolicyId());
        return getPolicyInfoVO(policyInfoDO, param.getPolicyId());
    }

    private PolicyInfoVO getPolicyInfoVO(PolicyInfoDO policyInfoDO, String id) {
        PolicyInfoVO policyInfoVO = new PolicyInfoVO();
        policyInfoVO.setId(policyInfoDO.getId());
        policyInfoVO.setTitle(policyInfoDO.getTitle());
        policyInfoVO.setDesc(policyInfoDO.getDesc());
        policyInfoVO.setCreateDate(policyInfoDO.getCreateTime());
        policyInfoVO.setReleaseDate(policyInfoVO.getReleaseDate());
        policyInfoVO.setStatus(policyInfoDO.getStatus());
        List<PolicyInfoVO.File> policyDocuments = new ArrayList<>();
        List<PolicyDocumentDO> policyDocumentDOS = policyDocumentMapper.selectByPolicyId(id);
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
    public void download(String fileId) {
        PolicyDocumentDO policyDocumentDO = policyDocumentMapper.selectById(fileId);
        String fileName = policyDocumentDO.getFileName();
        String path = policyDocumentDO.getPath();
        File file = new File(path, fileName);
        if (!file.exists())
            throw new BusinessException("500", "文件不存在!");
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" +  fileName + ";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));// 设置文件名
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("500", "下载文件失败");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException("500", "下载文件失败");
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException("500", "下载文件失败");
                }
            }
        }
    }
}
