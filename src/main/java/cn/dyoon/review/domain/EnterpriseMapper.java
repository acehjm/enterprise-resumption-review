package cn.dyoon.review.domain;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.domain.entity.EnterpriseDO;
import cn.dyoon.review.util.base.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    default List<EnterpriseDO> findExportListByCondition(EnterpriseExportParam param) {
        LambdaQueryWrapper<EnterpriseDO> wrapper = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotEmpty(param.getType())) {
            wrapper.eq(EnterpriseDO::getType, param.getType());
        }
        if (ObjectUtil.isNotEmpty(param.getScaleType())) {
            wrapper.eq(EnterpriseDO::getScaleType, param.getScaleType());
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
     * 获取分页数据列表
     *
     * @param param
     * @return
     */
    default IPage<EnterpriseDO> findPageByCondition(EnterpriseSearchParam param) {
        LambdaQueryWrapper<EnterpriseDO> wrapper = Wrappers.lambdaQuery();
        wrapper.isNotNull(EnterpriseDO::getId);
        if (ObjectUtil.isNotEmpty(param.getStreet())) {
            wrapper.eq(EnterpriseDO::getStreet, param.getStreet());
        }
        if (ObjectUtil.isNotEmpty(param.getScaleType())) {
            wrapper.eq(EnterpriseDO::getScaleType, param.getScaleType());
        }
        if (ObjectUtil.isNotEmpty(param.getType())) {
            wrapper.eq(EnterpriseDO::getType, param.getType());
        }
        if (ObjectUtil.isNotEmpty(param.getReviewStatus())) {
            wrapper.eq(EnterpriseDO::getReviewStatus, param.getReviewStatus());
        }
        if (ObjectUtil.isNotEmpty(param.getName())) {
            wrapper.likeLeft(EnterpriseDO::getName, param.getName());
        }
        return selectPage(new Page<>(param.getPageNo(), param.getPageSize()), wrapper);
    }

    /**
     * 根据用户名获取企业信息
     *
     * @param username
     * @return
     */
    default EnterpriseDO findInfoByUsername(String username) {
        return selectOne(Wrappers.<EnterpriseDO>lambdaQuery().eq(EnterpriseDO::getUsername, username));
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
