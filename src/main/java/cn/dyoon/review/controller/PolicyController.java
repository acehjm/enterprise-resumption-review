package cn.dyoon.review.controller;

import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.*;
import cn.dyoon.review.controller.vo.*;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * cn.dyoon.review.controller
 *
 * @author hs
 * @date 2020/2/7
 */
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String policyId, @RequestParam List<MultipartFile> files) {

        return new Result<>();
    }

    @PostMapping("/create")
    public Result<Void> create(@RequestBody PolicyParam param) {

        return new Result<>();
    }

    @PostMapping
    public Result<PageVO<PolicyListVO>> getPage(@RequestBody PolicyListParam param) {

        return new Result<>();
    }

    @GetMapping("/{policyId}")
    public Result<PolicyInfoVO> getInfo(@PathVariable String policyId) {

        return new Result<>();
    }

    @DeleteMapping("/{policyId}")
    public Result<Void> delete(@PathVariable String policyId) {

        return new Result<>();
    }

    @GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Result<ByteArray> download(@RequestParam String fileId) {

        return new Result<>();
    }

    @PostMapping("/publish")
    public Result<Void> review(@RequestBody PolicyPublishParam param) {

        return new Result<>();
    }

}
