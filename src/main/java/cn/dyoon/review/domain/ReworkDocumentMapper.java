package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.ReworkDocumentDO;
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
public interface ReworkDocumentMapper extends BaseMapper<ReworkDocumentDO> {

    /**
     * 获取企业文件
     *
     * @param enterpriseId
     * @return
     */
    default List<ReworkDocumentDO> findByEnterpriseId(String enterpriseId) {
        return selectList(Wrappers.<ReworkDocumentDO>lambdaQuery().eq(ReworkDocumentDO::getEnterpriseId, enterpriseId));
    }

    /**
     * 删除企业文件
     *
     * @param enterpriseId
     */
    default void deleteByEnterpriseId(String enterpriseId) {
        delete(Wrappers.<ReworkDocumentDO>lambdaQuery().eq(ReworkDocumentDO::getEnterpriseId, enterpriseId));
    }

    /**
     * 查找企业同名文件
     * @param enterpriseId
     * @param fileName
     * @return
     */
    default ReworkDocumentDO findSameFile(String enterpriseId, String fileName) {
        return selectOne(Wrappers.<ReworkDocumentDO>lambdaQuery()
                .eq(ReworkDocumentDO::getEnterpriseId, enterpriseId)
                .eq(ReworkDocumentDO::getFileName, fileName));
    }

}
