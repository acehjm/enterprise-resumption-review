package cn.dyoon.review.controller;

import cn.dyoon.review.common.response.Result;
import cn.dyoon.review.controller.param.*;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.controller.vo.ReviewTemplateDocumentListVO;
import cn.dyoon.review.manage.auth.constant.UserSession;
import cn.dyoon.review.manage.auth.constant.UserSessionHolder;
import cn.dyoon.review.service.EnterpriseService;
import cn.dyoon.review.service.ReviewTemplateDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/templateDocument")
public class ReviewTemplateDocumentController {

    @Autowired
    private ReviewTemplateDocumentService reviewTemplateDocumentService;

    @PostMapping
    public Result<PageVO<ReviewTemplateDocumentListVO>> getPage(@Validated @RequestBody ReviewTemplateDocumentParam param) {
        return new Result<>(reviewTemplateDocumentService.getPage(param));
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<Void> upload(@RequestParam List<MultipartFile> files) {
        UserSession userSession = UserSessionHolder.userSessionThreadLocal.get();
        reviewTemplateDocumentService.upload(userSession.getUsername(), files);
        return new Result<>();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/download")
    public Result<Void> download(@RequestParam String fileId, HttpServletResponse response) {
        reviewTemplateDocumentService.download(fileId, response);
        return new Result<>();
    }

    @DeleteMapping(value = "/actions/deleteFile")
    public Result<Void> deleteFile(@RequestParam String fileId) {
        reviewTemplateDocumentService.deleteFile(fileId);
        return new Result<>();
    }
}
