package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 政策信息
 */
@Data
public class PolicyInfoVO {
    private String id;
    private String title;
    private String desc;
    private LocalDateTime createDate;
    private LocalDateTime releaseDate;
    private Integer status;
    private List<File> files;

    @Data
    public static class File {
        private String fileId;
        private String fileName;
        private Double fileSize;
        private String uploadUser;
        private LocalDateTime uploadTime;
    }
}
