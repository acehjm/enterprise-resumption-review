package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.IndustryTypeEnum;
import cn.dyoon.review.common.enums.ReviewStatusEnum;
import cn.dyoon.review.common.enums.StreetTypeEnum;
import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.EnterpriseApplyParam;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.domain.EnterpriseMapper;
import cn.dyoon.review.domain.ReworkDocumentMapper;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.domain.entity.ReworkDocumentDO;
import cn.dyoon.review.dto.EnterpriseExcelDTO;
import cn.dyoon.review.manage.excel.service.impl.ExcelWriterImpl;
import cn.dyoon.review.service.EnterpriseService;
import cn.dyoon.review.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

import static cn.dyoon.review.common.constant.ResumptionReviewConstant.STANDARD_DATETIME_FORMAT;

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
    private ReworkDocumentMapper reworkDocumentMapper;
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
    public void upload(String enterpriseId, List<MultipartFile> files) {
        // TODO 清除旧文件
    }

    @Override
    public ByteArray download(String fileId, HttpServletResponse response) {

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitApply(EnterpriseApplyParam param) {
        EnterpriseDO enterprise = enterpriseMapper.selectById(param.getEnterpriseId());
        if (null == enterprise) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_NOT_EXISTS);
        }
        enterprise.setScaleType(param.getScaleType());
        enterprise.setEmployeeNum(param.getEmployeeNum());
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
        List<EnterpriseDO> list = enterpriseMapper.findExportListByCondition(param);
        List<EnterpriseExcelDTO> collect = list.stream()
                .map(it -> {
                    EnterpriseExcelDTO dto = new EnterpriseExcelDTO();
                    dto.setName(it.getName());
                    dto.setType(EnterpriseTypeEnum.getDesc(it.getType()));
                    dto.setStreet(StreetTypeEnum.getDesc(it.getStreet()));
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


}
