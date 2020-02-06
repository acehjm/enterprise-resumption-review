package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PolicyListVO {
    private String id;
    private String title;
    private String desc;
    private LocalDateTime createDate;
    private LocalDateTime releaseDate;
    private Integer status;
}
