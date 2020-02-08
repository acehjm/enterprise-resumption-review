package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.constant.ResumptionReviewConstant;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.controller.param.ReviewTemplateDocumentParam;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.controller.vo.ReviewTemplateDocumentListVO;
import cn.dyoon.review.domain.ReviewTemplateDocumentMapper;
import cn.dyoon.review.domain.entity.ReviewTemplateDocumentDO;
import cn.dyoon.review.service.ReviewTemplateDocumentService;
import cn.dyoon.review.util.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewTemplateDocumentServiceImpl implements ReviewTemplateDocumentService {

    @Autowired
    private ReviewTemplateDocumentMapper reviewTemplateDocumentMapper;

    @Override
    public void download(String fileId, HttpServletResponse response) {
        ReviewTemplateDocumentDO document = reviewTemplateDocumentMapper.selectById(fileId);
        FileUtil.readThenWriteResponse(document.getPath(), document.getVirtualName(), document.getFileName(), response);
    }

    @Override
    public PageVO<ReviewTemplateDocumentListVO> getPage(ReviewTemplateDocumentParam param) {
        IPage<ReviewTemplateDocumentDO> page = reviewTemplateDocumentMapper.selectPage(new Page<>(param.getPageNo(), param.getPageSize()), null);
        List<ReviewTemplateDocumentListVO> collect = page.getRecords().stream()
                .map(ReviewTemplateDocumentListVO::new)
                .collect(Collectors.toList());
        return new PageVO<>(param.getPageNo(), param.getPageSize(), page.getTotal(), collect);
    }

    @Override
    public void upload(String uploadUserName, List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_IS_EMPTY);
        }
        String filePath = ResumptionReviewConstant.REVIEW_TEMPLATE_DOCUMENT_PATH;
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
//                Files.createDirectory(Paths.get(ResumptionReviewConstant.BASE_PATH));
                Files.createDirectory(path);
            }
            files.forEach(file -> {
                if (file.isEmpty()) {
                    // 处理下一个文件
                    return;
                }
                try {
                    String actualName = file.getOriginalFilename();
                    String virtualName = IdWorker.get32UUID();
                    //如果上传了同名文件则删除之前的文件
                    ReviewTemplateDocumentDO preFile = reviewTemplateDocumentMapper.findSameFile(actualName);
                    if (preFile != null) {
                        this.deleteFiles(Collections.singletonList(preFile));
                    }

                    saveMetadata(uploadUserName, file.getSize(), actualName, filePath, virtualName);

                    Files.write(Paths.get(filePath, virtualName), file.getBytes());
                } catch (IOException e) {
                    log.error("[上传文件] - 失败", e);
                    throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_FAILURE);
                }
            });
        } catch (Exception e) {
            log.error("[上传文件] - 失败", e);
            throw new BusinessException(BaseExceptionEnum.UPLOAD_FILES_FAILURE);
        }
    }

    @Override
    public void deleteFile(String fileId) {
        ReviewTemplateDocumentDO reviewTemplateDocument = reviewTemplateDocumentMapper.selectById(fileId);
        if (null == reviewTemplateDocument) {
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_NOT_EXISTS);
        }
        this.deleteFiles(Collections.singletonList(reviewTemplateDocument));
    }

    /**
     * 批量删除复工文件
     *
     * @param documents
     */
    private void deleteFiles(List<ReviewTemplateDocumentDO> documents) {
        documents.forEach(document -> {
            try {
                boolean delete = Files.deleteIfExists(Paths.get(document.getPath(), document.getVirtualName()));
                if (delete) {
                    reviewTemplateDocumentMapper.deleteById(document.getId());
                }
            } catch (IOException e) {
                log.error("[删除文件] - 失败", e);
                throw new BusinessException(BaseExceptionEnum.DELETE_FILES_FAILURE);
            }
        });
    }

    private void saveMetadata(String username, long fileSize, String fileName,
                              String filePath, String virtualName) {
        ReviewTemplateDocumentDO reviewTemplateDocument = new ReviewTemplateDocumentDO();
        reviewTemplateDocument.setCreateTime(LocalDateTime.now());
        reviewTemplateDocument.setFileName(fileName);
        reviewTemplateDocument.setVirtualName(virtualName);
        reviewTemplateDocument.setFileSize((double) fileSize / 1024);
        reviewTemplateDocument.setPath(filePath);
        reviewTemplateDocument.setUploadUserName(username);
        reviewTemplateDocumentMapper.insert(reviewTemplateDocument);
    }

}
