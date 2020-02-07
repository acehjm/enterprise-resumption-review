package cn.dyoon.review.controller;

import cn.dyoon.review.common.enums.EnterpriseTypeEnum;
import cn.dyoon.review.common.enums.StreetTypeEnum;
import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.EnterpriseStreetVO;
import cn.dyoon.review.controller.vo.EnterpriseTypeVO;
import cn.dyoon.review.controller.vo.PageVO;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/registered")
    public Result<Void> registered(@RequestBody EnterpriseParam param) {

        return new Result<>();
    }

    @GetMapping("/types")
    public Result<List<EnterpriseTypeVO>> getTypes() {
        List<EnterpriseTypeVO> collect = Arrays.stream(EnterpriseTypeEnum.values())
                .map(it -> {
                    EnterpriseTypeVO vo = new EnterpriseTypeVO();
                    vo.setCode(it.getCode());
                    vo.setName(it.getDesc());
                    return vo;
                })
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @GetMapping("/streets")
    public Result<List<EnterpriseStreetVO>> getStreets() {
        List<EnterpriseStreetVO> collect = Arrays.stream(StreetTypeEnum.values())
                .map(it -> {
                    EnterpriseStreetVO vo = new EnterpriseStreetVO();
                    vo.setCode(it.getCode());
                    vo.setName(it.getDesc());
                    return vo;
                })
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @PostMapping
    public Result<PageVO<EnterpriseListVO>> getPage(@RequestBody EnterpriseSearchParam param) {

        return new Result<>();
    }

    @GetMapping("/{enterpriseId}")
    public Result<EnterpriseInfoVO> getInfo(@PathVariable String enterpriseId) {

        return new Result<>();
    }

    @DeleteMapping("/{enterpriseId}")
    public Result<Void> delete(@PathVariable String enterpriseId) {

        return new Result<>();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String enterpriseId, @RequestParam List<MultipartFile> files) {

        return new Result<>();
    }

    @GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Result<ByteArray> download(@RequestParam String fileId) {

        return new Result<>();
    }

    @GetMapping("/{enterpriseId}/actions/apply")
    public Result<Void> submitApply(@PathVariable String enterpriseId) {

        return new Result<>();
    }

    @PostMapping("/review")
    public Result<Void> review(@RequestBody EnterpriseReviewParam param) {

        return new Result<>();
    }

    @PostMapping("/export")
    public Result<Void> export(@RequestBody EnterpriseExportParam param) {

        return new Result<>();
    }

}
