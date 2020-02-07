package cn.dyoon.review.controller;

import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.*;
import cn.dyoon.review.controller.vo.*;
import cn.dyoon.review.service.PolicyService;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PolicyService policyService;

    /**
     * 新建政策信息
     * @param title 标题
     * @param desc 描述
     * @param files 附件
     * @return 政策信息
     */
    @PostMapping("/create")
    public Result<PolicyInfoVO> create(@RequestParam String title, @RequestParam String desc, @RequestParam String uploadUserName, @RequestParam List<MultipartFile> files) {
        return new Result<>(policyService.create(title, desc, uploadUserName, files));
    }

    @PostMapping
    public Result<PageVO<PolicyListVO>> getPage(@RequestBody PolicyListParam param) {

        return new Result<>();
    }

    @GetMapping("/{policyId}")
    public Result<PolicyInfoVO> getInfo(@PathVariable String policyId) {
        return new Result<>(policyService.findById(policyId));
    }

    @DeleteMapping("/{policyId}")
    public Result<Void> delete(@PathVariable String policyId) {
        policyService.deleteById(policyId);
        return new Result<>();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam String policyId, @RequestParam List<MultipartFile> files) {

        return new Result<>();
    }

    @GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Result<ByteArray> download(@RequestParam String fileId) {

        return new Result<>();
    }

    @PostMapping("/publish")
    public Result<Void> review(@RequestBody PolicyPublishParam param) {
        policyService.publish(param);
        return new Result<>();
    }

}
