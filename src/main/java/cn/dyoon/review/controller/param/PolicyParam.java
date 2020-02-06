package cn.dyoon.review.controller.param;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PolicyParam {
    private String title;
    private String desc;
    private LocalDateTime createDate;
    private LocalDateTime releaseDate;
    private Integer status;
}
