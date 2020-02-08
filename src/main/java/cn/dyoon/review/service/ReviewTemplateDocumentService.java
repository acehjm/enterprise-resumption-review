package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.ReviewTemplateDocumentParam;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.controller.vo.ReviewTemplateDocumentListVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReviewTemplateDocumentService {

    void download(String fileId, HttpServletResponse response);

    PageVO<ReviewTemplateDocumentListVO> getPage(ReviewTemplateDocumentParam param);

    void upload(String uploadUserName, List<MultipartFile> files);

    void deleteFile(String fileId);
}
