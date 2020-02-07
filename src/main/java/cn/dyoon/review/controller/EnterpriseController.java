package cn.dyoon.review.controller;

import cn.dyoon.review.common.enums.EnterpriseScaleEnum;
import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.StreetTypeEnum;
import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.BaseTypeVO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public Result<Void> registered(@Validated @RequestBody EnterpriseParam param) {
        enterpriseService.registered(param);
        return new Result<>();
    }

    @GetMapping("/types")
    public Result<List<BaseTypeVO>> getTypes() {
        List<BaseTypeVO> collect = Arrays.stream(EnterpriseTypeEnum.values())
                .map(it -> {
                    BaseTypeVO vo = new BaseTypeVO();
                    vo.setCode(it.getCode());
                    vo.setName(it.getDesc());
                    return vo;
                })
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/scales")
    public Result<List<BaseTypeVO>> getScales() {
        List<BaseTypeVO> collect = Arrays.stream(EnterpriseScaleEnum.values())
                .map(it -> {
                    BaseTypeVO vo = new BaseTypeVO();
                    vo.setCode(it.getCode());
                    vo.setName(it.getDesc());
                    return vo;
                })
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/streets")
    public Result<List<BaseTypeVO>> getStreets() {
        List<BaseTypeVO> collect = Arrays.stream(StreetTypeEnum.values())
                .map(it -> {
                    BaseTypeVO vo = new BaseTypeVO();
                    vo.setCode(it.getCode());
                    vo.setName(it.getDesc());
                    return vo;
                })
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @PostMapping
    public Result<PageVO<EnterpriseListVO>> getPage(@Validated @RequestBody EnterpriseSearchParam param) {
        return new Result<>(enterpriseService.getPage(param));
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

    @DeleteMapping("/{enterpriseId}")
    public Result<Void> delete(@PathVariable String enterpriseId) {
        enterpriseService.delete(enterpriseId);
        return new Result<>();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String enterpriseId, @RequestParam List<MultipartFile> files) {

        return new Result<>();
    }

    @GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Result<Void> download(@RequestParam String fileId, HttpServletResponse response) {

        return new Result<>();
    }

    @GetMapping("/{enterpriseId}/actions/apply")
    public Result<Void> submitApply(@PathVariable String enterpriseId) {
        enterpriseService.submitApply(enterpriseId);
        return new Result<>();
    }

    @PostMapping("/review")
    public Result<Void> review(@Validated @RequestBody EnterpriseReviewParam param) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        enterpriseService.review(userSession.getUsername(), param);
        return new Result<>();
    }

    @PostMapping("/export")
    public Result<Void> export(@RequestBody EnterpriseExportParam param, HttpServletResponse response) {
        enterpriseService.export(param, response);
        return new Result<>();
    }

}
