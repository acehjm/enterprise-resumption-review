package cn.dyoon.review.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        enterpriseService.registered(param);
        return new Result<>();
    }

    @PostMapping
    public Result<PageVO<EnterpriseListVO>> getPage(@Validated @RequestBody EnterpriseSearchParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        return new Result<>(enterpriseService.getPage(userSession, param));
    }

    @GetMapping("/{enterpriseId}")
    public Result<EnterpriseInfoVO> getInfo(@PathVariable String enterpriseId) {
        return new Result<>(enterpriseService.getInfo(enterpriseId));
    }

    @GetMapping("/actions/byUser")
    public Result<EnterpriseInfoVO> getInfoByUsername() {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        return new Result<>(enterpriseService.getInfo(userSession.getUsername()));
    }

    @PutMapping("/{enterpriseId}")
    public Result<Void> update(@PathVariable String enterpriseId, @Validated @RequestBody EnterpriseUpdateParam param) {
        enterpriseService.update(enterpriseId, param);
        return new Result<>();
    }

    @DeleteMapping("/{enterpriseId}")
    public Result<Void> delete(@PathVariable String enterpriseId) {
        enterpriseService.delete(enterpriseId);
        return new Result<>();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String enterpriseId, @RequestParam String uploadUserName, @RequestParam List<MultipartFile> files) {
        enterpriseService.upload(enterpriseId, uploadUserName, files);
        return new Result<>();
    }

    @GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Result<Void> download(@RequestParam String fileId, HttpServletResponse response) {
        enterpriseService.download(fileId, response);
        return new Result<>();
    }

    @GetMapping("/{enterpriseId}/actions/apply")
    public Result<Void> submitApply(@PathVariable String enterpriseId) {
        enterpriseService.submitApply(enterpriseId);
        return new Result<>();
    }

    @PostMapping("/actions/reviewPass")
    public Result<Void> reviewPass(@Validated @RequestBody EnterpriseReviewParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.reviewPass(userSession, param);
        return new Result<>();
    }

    @PostMapping("/actions/reviewReturn")
    public Result<Void> reviewReturn(@Validated @RequestBody EnterpriseReviewParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.reviewReturn(userSession, param);
        return new Result<>();
    }

    @PostMapping("/export")
    public Result<Void> export(@RequestBody EnterpriseExportParam param, HttpServletResponse response) {
        enterpriseService.export(param, response);
        return new Result<>();
    }

}
