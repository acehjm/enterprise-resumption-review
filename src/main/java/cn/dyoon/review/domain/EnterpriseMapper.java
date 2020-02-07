package cn.dyoon.review.domain;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.util.verify.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
        LambdaQueryWrapper<EnterpriseDO> wrapper = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotEmpty(param.getType())) {
            wrapper.eq(EnterpriseDO::getType, param.getType());
        }
        if (ObjectUtil.isNotEmpty(param.getStreet())) {
            wrapper.eq(EnterpriseDO::getStreet, param.getStreet());
        }
        if (ObjectUtil.isNotEmpty(param.getReviewStatus())) {
            wrapper.eq(EnterpriseDO::getReviewStatus, param.getReviewStatus());
        }
        if (ObjectUtil.isNotEmpty(param.getName())) {
            wrapper.likeLeft(EnterpriseDO::getName, param.getName());
        }
        wrapper.isNotNull(EnterpriseDO::getId);
        return selectList(wrapper);
    }

    /**
     * 是否存在
     *
     * @param username
     * @return
     */
    default boolean exists(String username) {
        return selectCount(Wrappers.<EnterpriseDO>lambdaQuery().eq(EnterpriseDO::getUsername, username)) > 0;
    }

}
