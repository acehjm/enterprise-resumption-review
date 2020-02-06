package cn.dyoon.review.controller.vo;

import cn.dyoon.review.common.enums.PublishStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * 政策信息
 */
@Data
public class PolicyInfoVo {
    private Integer id;
    private String title;
    private String desc;
    private Date createDate;
    private Date releaseDate;
    private PublishStatusEnum status;
}
