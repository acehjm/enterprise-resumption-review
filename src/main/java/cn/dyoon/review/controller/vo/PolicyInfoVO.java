package cn.dyoon.review.controller.vo;

import cn.dyoon.review.common.enums.PublishStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 政策信息
 */
@Data
public class PolicyInfoVO {
    private Integer id;
    private String title;
    private String desc;
    private Date createDate;
    private Date releaseDate;
    private Integer status;
    private List<File> files;

    @Data
    public static class File {
        private String fileId;
        private String fileName;
        private Integer fileSize;
        private String uploadUser;
        private LocalDateTime uploadTime;
    }
}
