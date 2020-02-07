package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.ReviewStatusEnum;
import cn.dyoon.review.common.enums.StreetTypeEnum;
import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.domain.EnterpriseMapper;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.dto.EnterpriseExcelDTO;
import cn.dyoon.review.manage.excel.service.impl.ExcelWriterImpl;
import cn.dyoon.review.service.EnterpriseService;
import cn.dyoon.review.service.UserService;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * cn.dyoon.review.service.impl
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registered(EnterpriseParam param) {
        boolean exists = enterpriseMapper.exists(param.getUsername());
        if (exists) {
            throw new BusinessException(BaseExceptionEnum.USER_NAME_HAS_EXISTS);
        }
        EnterpriseDO enterprise = new EnterpriseDO();
        enterprise.setName(param.getName());
        enterprise.setUnifiedSocialCreditCode(param.getUnifiedSocialCreditCode());
        enterprise.setType(param.getType());
        enterprise.setStreet(param.getStreet());
        enterprise.setUsername(param.getUsername());
        enterprise.setTransactorName(param.getTransactorName());
        enterprise.setPhone(param.getPhone());
        enterprise.setReviewStatus(ReviewStatusEnum.NOT_STARTED.getCode());
        enterpriseMapper.insert(enterprise);

        userService.add(param.getUsername(), param.getPassword(), UserTypeEnum.ENTERPRISE.getCode());
    }

    @Override
    public PageVO<EnterpriseListVO> getPage(EnterpriseSearchParam param) {
        return null;
    }

    @Override
    public EnterpriseInfoVO getInfo(String enterpriseId) {
        return null;
    }

    @Override
    public EnterpriseInfoVO getInfo(String username, String enterpriseId) {
        return null;
    }

    @Override
    public void delete(String enterpriseId) {

    }

    @Override
    public void upload(String enterpriseId, List<MultipartFile> files) {

    }

    @Override
    public ByteArray download(String fileId, HttpServletResponse response) {

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitApply(String enterpriseId) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(enterpriseId);
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        enterprise.setApplyTime(LocalDateTime.now());
        enterprise.setReviewStatus(ReviewStatusEnum.IN_REVIEW.getCode());
        enterpriseMapper.updateById(enterprise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void review(String username, EnterpriseReviewParam param) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(param.getEnterpriseId());
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        enterprise.setReviewStatus(param.getReviewStatus());
        enterprise.setReviewResult(param.getReviewResult());
        enterprise.setReviewUser(username);
        enterprise.setReviewTime(LocalDateTime.now());
        enterpriseMapper.updateById(enterprise);
    }

    @Override
    public void export(EnterpriseExportParam param, HttpServletResponse response) {
        List<EnterpriseDO> list = enterpriseMapper.getExportListByCondition(param);
        List<EnterpriseExcelDTO> collect = list.stream()
                .map(it -> {
                    EnterpriseExcelDTO dto = new EnterpriseExcelDTO();
                    dto.setName(it.getName());
                    dto.setType(EnterpriseTypeEnum.getDesc(it.getType()));
                    dto.setUniqueCode(it.getUnifiedSocialCreditCode());
                    dto.setStreet(StreetTypeEnum.getDesc(it.getStreet()));
                    dto.setTransactorName(it.getTransactorName());
                    dto.setPhone(it.getPhone());
                    dto.setApplyTime(it.getApplyTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    dto.setReviewStatus(ReviewStatusEnum.getDesc(it.getReviewStatus()));
                    dto.setReviewTime(it.getReviewTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    dto.setReviewResult(it.getReviewResult());
                    return dto;
                })
                .collect(Collectors.toList());
        String fileName = "企业复工申请报表_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .replaceAll("[[\\s-:punct:]]", "");
        new ExcelWriterImpl().writeExcelRsp(collect, EnterpriseExcelDTO.class, true, fileName, response);
    }


}
