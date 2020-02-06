package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * cn.dyoon.review.controller.vo
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class EnterpriseListVO {
    private String id;
    private String name;
    private String unifiedSocialCreditCode;
    private Integer type;
    private Integer street;
    private String transactorName;
    private String phone;
    private Integer reviewStatus;
    private LocalDateTime reviewTime;
}
