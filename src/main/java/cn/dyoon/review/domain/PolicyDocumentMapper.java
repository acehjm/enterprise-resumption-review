package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface PolicyDocumentMapper extends BaseMapper<PolicyDocumentDO> {

    /**
     * 根据政策信息ID查询附件
     * @param policyId
     * @return
     */
    default List<PolicyDocumentDO> selectByPolicyId(String policyId) {
        return selectList(new QueryWrapper<PolicyDocumentDO>().eq("policy_id", policyId));
    }

    default int deleteByPolicyId(String policyId) {
        return delete(new QueryWrapper<PolicyDocumentDO>().eq("policy_id", policyId));
    }
}
