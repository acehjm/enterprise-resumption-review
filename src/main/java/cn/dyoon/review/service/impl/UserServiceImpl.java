package cn.dyoon.review.service.impl;

import cn.dyoon.review.common.enums.UserRoleEnum;
import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.common.exception.BaseExceptionEnum;
import cn.dyoon.review.common.exception.BusinessException;
import cn.dyoon.review.common.json.JsonUtil;
import cn.dyoon.review.controller.param.UserLoginParam;
import cn.dyoon.review.controller.vo.UserVO;
import cn.dyoon.review.domain.UserMapper;
import cn.dyoon.review.domain.entity.UserDO;
import cn.dyoon.review.manage.auth.constant.UserSessionHolder;
import cn.dyoon.review.manage.auth.jwt.JwtTokenUtil;
import cn.dyoon.review.manage.auth.jwt.JwtUser;
import cn.dyoon.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * cn.dyoon.review.service.impl
 *
 * @author majhdk
 * @date 2020/2/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO login(UserLoginParam param) {
        UserDO user = userMapper.selectByUsername(param.getUsername());
        if (null == user) {
            throw new BusinessException(BaseExceptionEnum.USER_NOT_EXISTS);
        }
        if (!param.getPassword().equals(user.getPassword())) {
            throw new BusinessException(BaseExceptionEnum.USER_PASSWORD_ERROR);
        }
        UserVO vo = new UserVO();
        vo.setUserid(user.getId());
        vo.setUsername(user.getUsername());
        vo.setUserType(UserTypeEnum.get(user.getUserType()).getName());
        vo.setRole(UserRoleEnum.get(user.getRole()).getName());
        // 配置JWT令牌
        vo.setToken(getToken(user));
        // 配置用户菜单权限
        vo.setMenus(UserTypeEnum.get(user.getUserType()).authorities());
        return vo;
    }

    @Override
    public void signout(String username) {
        UserSessionHolder.userSessionThreadLocal.remove();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEnterpriseUser(String username, String password) {
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(password);
        userDO.setUserType(UserTypeEnum.ENTERPRISE.getCode());
        userDO.setRole(UserRoleEnum.GENERAL_USER.getCode());
        userDO.setCreateTime(LocalDateTime.now());
        userMapper.insert(userDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByUsername(String username) {
        userMapper.deleteByUsername(username);
    }

    /**
     * 生产token
     *
     * @param user
     * @return
     */
    private String getToken(UserDO user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername(user.getUsername());
        jwtUser.setUserType(UserTypeEnum.get(user.getUserType()).getName());
        jwtUser.setRole(UserRoleEnum.get(user.getRole()).getName());
        return JwtTokenUtil.token(JsonUtil.toJson(jwtUser));
    }
}
