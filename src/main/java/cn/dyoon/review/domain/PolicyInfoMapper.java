package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.PolicyInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PolicyInfoMapper extends BaseMapper<PolicyInfoDO> {

}
