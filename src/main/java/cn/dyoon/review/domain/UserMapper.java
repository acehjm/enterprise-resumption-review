package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * cn.dyoon.review.domain
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
