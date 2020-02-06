package cn.dyoon.review.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * cn.dyoon.review.controller.vo
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Data
public class PageVO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer total;
    private List<T> records;
}
