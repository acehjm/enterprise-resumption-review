package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.PolicyDocumentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface PolicyDocumentMapper extends BaseMapper<PolicyDocumentDO> {

    /**
     * 获取政策附件
     *
     * @param policyId 政策信息ID
     * @return
     */
    default List<PolicyDocumentDO> selectByPolicyId(String policyId) {
        return selectList(Wrappers.<PolicyDocumentDO>lambdaQuery().eq(PolicyDocumentDO::getPolicyId, policyId));
    }

    /**
     * 删除政策附件
     *
     * @param policyId 政策信息ID
     * @return
     */
    default int deleteByPolicyId(String policyId) {
        return delete(Wrappers.<PolicyDocumentDO>lambdaQuery().eq(PolicyDocumentDO::getPolicyId, policyId));
    }

    /**
     * 查找重名文件
     *
     * @param policyId
     * @param fileName
     * @return
     */
    default List<PolicyDocumentDO> findSameFile(String policyId, String fileName) {
        return selectList(Wrappers.<PolicyDocumentDO>lambdaQuery().
                eq(PolicyDocumentDO::getPolicyId, policyId).
                eq(PolicyDocumentDO::getFileName, fileName));
    }
}
