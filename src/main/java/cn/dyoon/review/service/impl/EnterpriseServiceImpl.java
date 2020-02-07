package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.constant.ResumptionReviewConstant;
import cn.dyoon.review.common.enums.EnterpriseScaleEnum;
import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.IndustryTypeEnum;
import cn.dyoon.review.common.enums.ResumptionTypeEnum;
import cn.dyoon.review.common.enums.ReviewStatusEnum;
import cn.dyoon.review.common.enums.StreetTypeEnum;
import cn.dyoon.review.common.enums.UserRoleEnum;
import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseRegisteredParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.param.EnterpriseUpdateParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.domain.EnterpriseMapper;
import cn.dyoon.review.domain.ReworkDocumentMapper;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.domain.entity.ReworkDocumentDO;
import cn.dyoon.review.domain.entity.UserDO;
import cn.dyoon.review.dto.EnterpriseExcelDTO;
import cn.dyoon.review.manage.auth.constant.UserSession;
import cn.dyoon.review.manage.excel.service.impl.ExcelWriterImpl;
import cn.dyoon.review.service.EnterpriseService;
import cn.dyoon.review.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cn.dyoon.review.common.constant.ResumptionReviewConstant.STANDARD_DATETIME_FORMAT;

/**
 * cn.dyoon.review.service.impl
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Slf4j
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ReworkDocumentMapper reworkDocumentMapper;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registered(EnterpriseRegisteredParam param) {
        boolean exists = enterpriseMapper.exists(param.getUsername());
        if (exists) {
            throw new BusinessException(BaseExceptionEnum.USER_NAME_HAS_EXISTS);
        }
        EnterpriseDO enterprise = new EnterpriseDO();
        enterprise.setName(param.getName());
        enterprise.setUnifiedSocialCreditCode(param.getUnifiedSocialCreditCode());
        enterprise.setType(param.getType());
        enterprise.setScaleType(param.getScaleType());
        enterprise.setResumptionType(param.getResumptionType());
        enterprise.setIndustryType(param.getIndustryType());
        enterprise.setEmployeeNum(param.getEmployeeNum());
        enterprise.setStreet(param.getStreet());
        enterprise.setUsername(param.getUsername());
        enterprise.setTransactorName(param.getTransactorName());
        enterprise.setPhone(param.getPhone());
        enterprise.setReviewStatus(ReviewStatusEnum.NOT_STARTED.getCode());
        enterpriseMapper.insert(enterprise);

        userService.addEnterpriseUser(param.getUsername(), param.getPassword());
    }

    @Override
    public PageVO<EnterpriseListVO> getPage(UserSession userSession, EnterpriseSearchParam param) {
        // 仅加载工业企业
        if (UserTypeEnum.ZF_JINGXIN.getName().equals(userSession.getUserType())) {
            param.setType(EnterpriseTypeEnum.INDUSTRIAL.getCode());
        }
        // 仅加载商贸业企业
        if (UserTypeEnum.ZF_SHANGWU.getName().equals(userSession.getUserType())) {
            param.setType(EnterpriseTypeEnum.BUSINESS.getCode());
        }
        // 仅加载所在街道的企业
        if (UserTypeEnum.ZF_STREET.getName().equals(userSession.getUserType())) {
            UserDO user = userService.findByUsername(userSession.getUsername());
            param.setStreet(user.getUserSubtype());
        }

        IPage<EnterpriseDO> page = enterpriseMapper.findPageByCondition(param);
        List<EnterpriseListVO> collect = page.getRecords().stream()
                .map(EnterpriseListVO::new)
                .collect(Collectors.toList());
        return new PageVO<>(param.getPageNo(), param.getPageSize(), page.getTotal(), collect);
    }

    @Override
    public EnterpriseInfoVO getInfo(String enterpriseId) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        List<ReworkDocumentDO> files = reworkDocumentMapper.findByEnterpriseId(enterpriseId);
        return new EnterpriseInfoVO(enterprise, files);
    }

    @Override
    public EnterpriseInfoVO getInfoByUsername(String username) {
        EnterpriseDO enterprise = enterpriseMapper.findInfoByUsername(username);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        List<ReworkDocumentDO> files = reworkDocumentMapper.findByEnterpriseId(enterprise.getId());
        return new EnterpriseInfoVO(enterprise, files);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(String enterpriseId, EnterpriseUpdateParam param) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        // 不允许修改审批中的企业信息
        Integer[] status = {ReviewStatusEnum.NOT_STARTED.getCode(), ReviewStatusEnum.PASS.getCode(),
                ReviewStatusEnum.NOT_PASS.getCode()};
        if (!Arrays.asList(status).contains(enterprise.getReviewStatus())) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_IN_PROCESSING);
        }

        enterprise.setUnifiedSocialCreditCode(param.getUnifiedSocialCreditCode());
        enterprise.setName(param.getName());
        enterprise.setType(param.getType());
        enterprise.setScaleType(param.getScaleType());
        enterprise.setResumptionType(param.getResumptionType());
        enterprise.setIndustryType(param.getIndustryType());
        enterprise.setEmployeeNum(param.getEmployeeNum());
        enterprise.setStreet(param.getStreet());
        enterprise.setTransactorName(param.getTransactorName());
        enterprise.setPhone(param.getPhone());
        enterpriseMapper.insert(enterprise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String enterpriseId) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            return;
        }
        enterpriseMapper.deleteById(enterpriseId);
        reworkDocumentMapper.deleteByEnterpriseId(enterpriseId);

        userService.deleteByUsername(enterprise.getUsername());
    }

    @Override
    public void upload(String enterpriseId, String uploadUserName, List<MultipartFile> files) {
        // TODO 清除旧文件
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        this.uploadFiles(uploadUserName, enterprise.getId(), files);
    }

    @Override
    public void download(String fileId, HttpServletResponse response) {
        ReworkDocumentDO reworkDocument = reworkDocumentMapper.selectById(fileId);

        Path path = Paths.get(reworkDocument.getPath(), reworkDocument.getVirtualName());
        boolean exists = Files.exists(path);
        if (!exists) {
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_NOT_EXISTS);
        }
        try (OutputStream os = response.getOutputStream()) {
            byte[] bytes = Files.readAllBytes(path);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(reworkDocument.getFileName(), "utf-8"));

            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            log.error("[下载文件] - 失败", e);
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_FAILURE);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitApply(String enterpriseId) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        enterprise.setApplyTime(LocalDateTime.now());
        enterprise.setReviewStatus(ReviewStatusEnum.ACCEPTED.getCode());
        // 清空审核意见
        enterprise.setReviewResult(null);
        enterpriseMapper.updateById(enterprise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reviewPass(UserSession userSession, EnterpriseReviewParam param) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(param.getEnterpriseId());
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        // 审核--受理人
        if (UserRoleEnum.ASSIGNEE_USER.getName().equals(userSession.getRole())) {
            enterprise.setReviewStatus(ReviewStatusEnum.STREET_REVIEW.getCode());
        }
        // 审核--审核人
        if (UserRoleEnum.REVIEW_USER.getName().equals(userSession.getRole())) {
            // 街道审核人
            if (UserTypeEnum.ZF_STREET.getName().equals(userSession.getUserType())) {
                // 规（限）上企业
                if (EnterpriseScaleEnum.ENTERPRISE_ABOVE_SCALE.getCode().equals(enterprise.getScaleType())) {
                    enterprise.setReviewStatus(ReviewStatusEnum.DEPARTMENT_REVIEW.getCode());
                }
                // 规（限）下企业；员工20人以下微型企业复工审核流程
                else {
                    enterprise.setReviewStatus(ReviewStatusEnum.PASS.getCode());
                }
            }
            // 商务局审核人
            if (UserTypeEnum.ZF_SHANGWU.getName().equals(userSession.getUserType())) {
                if (EnterpriseTypeEnum.INDUSTRIAL.getCode().equals(enterprise.getType())) {
                    throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_MATCH);
                }
                enterprise.setReviewStatus(ReviewStatusEnum.PASS.getCode());
            }
            // 经信局审核人
            if (UserTypeEnum.ZF_JINGXIN.getName().equals(userSession.getUserType())) {
                if (EnterpriseTypeEnum.BUSINESS.getCode().equals(enterprise.getType())) {
                    throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_MATCH);
                }
                enterprise.setReviewStatus(ReviewStatusEnum.PASS.getCode());
            }
        }
        enterprise.setReviewResult(param.getReviewResult());
        enterprise.setReviewUser(userSession.getUsername());
        enterprise.setReviewTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    public void reviewReturn(UserSession userSession, EnterpriseReviewParam param) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(param.getEnterpriseId());
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        enterprise.setReviewStatus(ReviewStatusEnum.NOT_PASS.getCode());
        enterprise.setReviewResult(param.getReviewResult());
        enterprise.setReviewUser(userSession.getUsername());
        enterprise.setReviewTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    public void export(EnterpriseExportParam param, HttpServletResponse response) {
        List<EnterpriseDO> list = enterpriseMapper.findExportListByCondition(param);
        List<EnterpriseExcelDTO> collect = list.stream()
                .map(it -> {
                    EnterpriseExcelDTO dto = new EnterpriseExcelDTO();
                    dto.setName(it.getName());
                    dto.setType(EnterpriseTypeEnum.getDesc(it.getType()));
                    dto.setStreet(StreetTypeEnum.getDesc(it.getStreet()));
                    dto.setResumptionType(ResumptionTypeEnum.getDesc(it.getResumptionType()));
                    dto.setIndustryType(IndustryTypeEnum.getDesc(it.getIndustryType()));
                    dto.setReviewTime(it.getReviewTime().format(DateTimeFormatter.ofPattern(STANDARD_DATETIME_FORMAT)));
                    dto.setEmployeeNum(it.getEmployeeNum());
                    return dto;
                })
                .collect(Collectors.toList());
        String fileName = "企业复工申请统计报表_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(STANDARD_DATETIME_FORMAT))
                .replaceAll("[[\\s-:punct:]]", "");
        new ExcelWriterImpl().writeExcelRsp(collect, EnterpriseExcelDTO.class, true, fileName, response);
    }

    /**
     * 上传文件
     *
     * @param username
     * @param enterpriseId
     * @param files
     */
    private void uploadFiles(String username, String enterpriseId, List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_IS_EMPTY);
        }
        String filePath = ResumptionReviewConstant.ENTERPRISE_RESUMPTION_PATH;
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
                    saveMetadata(username, enterpriseId, file.getSize(), actualName, filePath, virtualName);

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

    /**
     * 保存文件元数据
     *
     * @param username
     * @param enterpriseId
     * @param fileSize
     * @param fileName
     * @param filePath
     * @param virtualName
     */
    private void saveMetadata(String username, String enterpriseId, long fileSize, String fileName,
                              String filePath, String virtualName) {
        ReworkDocumentDO reworkDocument = new ReworkDocumentDO();
        reworkDocument.setCreateTime(LocalDateTime.now());
        reworkDocument.setFileName(fileName);
        reworkDocument.setVirtualName(virtualName);
        reworkDocument.setFileSize((double) fileSize / 1024);
        reworkDocument.setEnterpriseId(enterpriseId);
        reworkDocument.setPath(filePath);
        reworkDocument.setUploadUserName(username);
        reworkDocumentMapper.insert(reworkDocument);
    }

}
