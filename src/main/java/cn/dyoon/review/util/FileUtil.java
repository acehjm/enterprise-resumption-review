package cn.dyoon.review.util;

import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 */
@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 读取文件并写入响应流
     *
     * @param filePath
     * @param virtualName
     * @param fileName
     * @param response
     */
    public static void readThenWriteResponse(String filePath, String virtualName, String fileName,
                                             HttpServletResponse response) {
        Path path = Paths.get(filePath, virtualName);
        if (!Files.exists(path)) {
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_NOT_EXISTS);
        }
        try (OutputStream os = response.getOutputStream()) {
            byte[] bytes = Files.readAllBytes(path);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, "utf-8"));

            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            log.error("[下载文件] - 失败", e);
            throw new BusinessException(BaseExceptionEnum.DOWNLOAD_FILES_FAILURE);
        }
    }
}
