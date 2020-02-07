package cn.dyoon.review.domain;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.util.verify.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * cn.dyoon.review.domain
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Mapper
@Repository
public interface EnterpriseMapper extends BaseMapper<EnterpriseDO> {

    /**
     * 获取导出数据列表
     *
     * @param param
     * @return
     */
    default List<EnterpriseDO> getExportListByCondition(EnterpriseExportParam param) {
        QueryWrapper<EnterpriseDO> wrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(param.getType())) {
            wrapper.eq("type", param.getType());
        }
        if (ObjectUtil.isNotEmpty(param.getStreet())) {
            wrapper.eq("street", param.getStreet());
        }
        if (ObjectUtil.isNotEmpty(param.getReviewStatus())) {
            wrapper.eq("review_status", param.getReviewStatus());
        }
        if (ObjectUtil.isNotEmpty(param.getName())) {
            wrapper.likeLeft("name", param.getName());
        }
        return selectList(wrapper);
    }

}
