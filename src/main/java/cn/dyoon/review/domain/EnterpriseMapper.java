package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.EnterpriseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * cn.dyoon.review.domain
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Mapper
@Repository
public interface EnterpriseMapper extends BaseMapper<EnterpriseDO> {
}
