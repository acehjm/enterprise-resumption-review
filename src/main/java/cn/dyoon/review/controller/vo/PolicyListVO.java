package cn.dyoon.review.controller.vo;

import cn.dyoon.review.domain.entity.PolicyInfoDO;
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

    public PolicyListVO(PolicyInfoDO policyInfoDO) {
        this.id = policyInfoDO.getId();
        this.title = policyInfoDO.getTitle();
        this.desc = policyInfoDO.getDesc();
        this.createDate = policyInfoDO.getCreateTime();
        this.releaseDate = policyInfoDO.getReleaseTime();
        this.status = policyInfoDO.getStatus();
    }
}
