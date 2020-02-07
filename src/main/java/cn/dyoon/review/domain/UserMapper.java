package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * cn.dyoon.review.domain
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 根据名称获取用户信息
     *
     * @param username
     * @return
     */
    default UserDO selectByUsername(String username) {
        return selectOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username));
    }

    /**
     * 根据用户名删除
     *
     * @param username
     */
    default void deleteByUsername(String username) {
        delete(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username));
    }

}
