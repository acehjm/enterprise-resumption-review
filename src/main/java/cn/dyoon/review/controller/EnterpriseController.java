package cn.dyoon.review.controller;

import cn.dyoon.review.common.enums.EnterpriseScaleEnum;
import cn.dyoon.review.common.enums.IndustryTypeEnum;
import cn.dyoon.review.common.enums.ResumptionTypeEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseRegisteredParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.param.EnterpriseUpdateParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.manage.auth.constant.UserSession;
import cn.dyoon.review.manage.auth.constant.UserSessionHolder;
import cn.dyoon.review.service.EnterpriseService;
import cn.dyoon.review.util.PatternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * cn.dyoon.review.controller
 *
 * @author majhdk
 * @date 2020/2/6
 */
@RestController
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @PostMapping("/registered")
    public Result<Void> registered(@Validated @RequestBody EnterpriseRegisteredParam param) {
        this.checkParams(param.getScaleType(), param.getEmployeeNum(), param.getResumptionType(),
                param.getIndustryType(), param.getPhone());
        enterpriseService.registered(param);
        return new Result<>();
    }

    @PreAuthorize("!hasAuthority('ENTERPRISE_USER')")
    @PostMapping
    public Result<PageVO<EnterpriseListVO>> getPage(@Validated @RequestBody EnterpriseSearchParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        return new Result<>(enterpriseService.getPage(userSession, param));
    }

    @PreAuthorize("!hasAuthority('ENTERPRISE_USER')")
    @GetMapping("/{enterpriseId}")
    public Result<EnterpriseInfoVO> getInfo(@PathVariable String enterpriseId) {
        return new Result<>(enterpriseService.getInfo(enterpriseId));
    }

    @PreAuthorize("hasAuthority('ENTERPRISE_USER')")
    @GetMapping("/actions/byUser")
    public Result<EnterpriseInfoVO> getInfoByUsername() {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        return new Result<>(enterpriseService.getInfoByUsername(userSession.getUsername()));
    }

    @PreAuthorize("hasAuthority('ENTERPRISE_USER')")
    @PutMapping("/{enterpriseId}")
    public Result<Void> update(@PathVariable String enterpriseId, @Validated @RequestBody EnterpriseUpdateParam param) {
        this.checkParams(param.getScaleType(), param.getEmployeeNum(), param.getResumptionType(),
                param.getIndustryType(), param.getPhone());
        enterpriseService.update(enterpriseId, param);
        return new Result<>();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/{enterpriseId}")
    public Result<Void> delete(@PathVariable String enterpriseId) {
        enterpriseService.delete(enterpriseId);
        return new Result<>();
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String enterpriseId, @RequestParam List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (!PatternUtil.checkFileType(file.getOriginalFilename())) {
                throw new BusinessException(BaseExceptionEnum.FILE_TYPE_NOT_SUPPORT);
            }
        }
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.upload(enterpriseId, userSession.getUsername(), files);
        return new Result<>();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/download")
    public Result<Void> download(@RequestParam String fileId, HttpServletResponse response) {
        enterpriseService.download(fileId, response);
        return new Result<>();
    }

    @PreAuthorize("hasAuthority('ENTERPRISE_USER')")
    @DeleteMapping(value = "/actions/deleteFile")
    public Result<Void> deleteFile(@RequestParam String fileId) {
        enterpriseService.deleteFile(fileId);
        return new Result<>();
    }

    @PreAuthorize("hasAuthority('ENTERPRISE_USER')")
    @GetMapping("/{enterpriseId}/actions/apply")
    public Result<Void> submitApply(@PathVariable String enterpriseId) {
        enterpriseService.submitApply(enterpriseId);
        return new Result<>();
    }

    @PreAuthorize("hasAnyAuthority('JINGXINJU_USER','SHANGWUJU_USER','STREET_USER','PREVENTION_USER')")
    @PostMapping("/actions/reviewPass")
    public Result<Void> reviewPass(@Validated @RequestBody EnterpriseReviewParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.reviewPass(userSession, param);
        return new Result<>();
    }

    @PreAuthorize("hasAnyAuthority('JINGXINJU_USER','SHANGWUJU_USER','STREET_USER','PREVENTION_USER')")
    @PostMapping("/actions/reviewReturn")
    public Result<Void> reviewReturn(@Validated @RequestBody EnterpriseReviewParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.reviewReturn(userSession, param);
        return new Result<>();
    }

    @PreAuthorize("!hasAuthority('ENTERPRISE_USER')")
    @PostMapping("/export")
    public Result<Void> export(@Validated @RequestBody EnterpriseExportParam param, HttpServletResponse response) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.export(userSession, param, response);
        return new Result<>();
    }

    private void checkParams(Integer scaleType, Integer employeeNum, Integer resumptionType,
                             Integer industryType, String phone) {
        if (EnterpriseScaleEnum.ENTERPRISE_MICRO_SCALE.getCode().equals(scaleType)
                && employeeNum > 20) {
            throw new BusinessException(BaseExceptionEnum.ENTERPRISE_EMPLOYEE_ERROR);
        }

        if (!ResumptionTypeEnum.ENTERPRISE_STEADY_RESUMPTION.getCode().equals(resumptionType)) {
            if (!IndustryTypeEnum.isValidType(industryType)) {
                throw new BusinessException(BaseExceptionEnum.ENTERPRISE_INDUSTRY_ERROR);
            }
        }

        if (!PatternUtil.checkTelephone(phone) && !PatternUtil.checkLandline(phone)) {
            throw new BusinessException(BaseExceptionEnum.TELEPHONE_LANDLINE_ERROR);
        }
    }
}
