package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.UserDO;
import cn.dyoon.review.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    /**
     * 根据用户名删除
     *
     * @param userType
     * @param subType
     */
    default UserDO findByUserType(Integer userType, Integer subType) {
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserDO::getUserType, userType);
        if (ObjectUtil.isNotEmpty(subType)) {
            wrapper.eq(UserDO::getUserSubtype, subType);
        }
        return selectOne(wrapper);
    }

    /**
     * 是否存在
     *
     * @param username
     * @return
     */
    default boolean exists(String username) {
        return selectCount(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username)) > 0;
    }

}
