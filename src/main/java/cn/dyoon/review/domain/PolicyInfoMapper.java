package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.PolicyInfoDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

}
