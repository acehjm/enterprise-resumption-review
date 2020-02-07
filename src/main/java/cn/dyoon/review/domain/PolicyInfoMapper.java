package cn.dyoon.review.domain;

import cn.dyoon.review.controller.param.PolicyListParam;
import cn.dyoon.review.domain.entity.PolicyInfoDO;
import cn.dyoon.review.util.verify.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PolicyInfoMapper extends BaseMapper<PolicyInfoDO> {

    default int publish(PolicyInfoDO policyInfoDO, String policyId) {
        return update(policyInfoDO, new QueryWrapper<PolicyInfoDO>().eq("id", policyId));
    }

    /**
     * 标题是否存在
     * @param title 政策标题
     * @return
     */
    default boolean exists(String title) {
        return selectCount(Wrappers.<PolicyInfoDO>lambdaQuery().eq(PolicyInfoDO::getTitle, title)) > 0;
    }

    /**
     * 获取政策信息分页数据列表
     *
     * @param param
     * @return
     */
    default IPage<PolicyInfoDO> findPageByCondition(PolicyListParam param) {
        LambdaQueryWrapper<PolicyInfoDO> wrapper = Wrappers.lambdaQuery();
        wrapper.isNotNull(PolicyInfoDO::getId);
        if (ObjectUtil.isNotEmpty(param.getStatus())) {
            wrapper.eq(PolicyInfoDO::getStatus, param.getStatus());
        }
        return selectPage(new Page<>(param.getPageNo(), param.getPageSize()), wrapper);
    }

}
