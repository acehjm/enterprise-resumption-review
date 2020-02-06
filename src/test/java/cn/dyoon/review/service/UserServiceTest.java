package cn.dyoon.review.service;

import cn.dyoon.review.common.enums.UserTypeEnum;
import cn.dyoon.review.domain.UserMapper;
import cn.dyoon.review.domain.entity.UserDO;
import cn.dyoon.review.util.security.SHAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * cn.dyoon.review.service
 *
 * @author majhdk
 * @date 2020/2/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void login() {
        String digest = SHAUtil.Digest.SHA256.digest("root*@123");
        System.out.println(digest);
        String digestBase64 = SHAUtil.Digest.SHA256.digestBase64("root*@123");
        System.out.println(digestBase64);
    }

    @Test
    public void signout() {
    }

    @Test
    public void init() {
        UserDO user1 = new UserDO();
        user1.setUsername("admin");
        user1.setPassword(SHAUtil.Digest.SHA256.digest("root*@123"));
        user1.setUserType(UserTypeEnum.SUPER_ADMIN.getCode());
        user1.setCreateTime(LocalDateTime.now());
        userMapper.insert(user1);

        UserDO user2 = new UserDO();
        user2.setUsername("jingxinju");
        user2.setPassword(SHAUtil.Digest.SHA256.digest("jingxinju@123&"));
        user2.setUserType(UserTypeEnum.ZF_COMMON.getCode());
        user2.setCreateTime(LocalDateTime.now());
        userMapper.insert(user2);

        UserDO user3 = new UserDO();
        user3.setUsername("shangwuju");
        user3.setPassword(SHAUtil.Digest.SHA256.digest("shangwuju@123&"));
        user3.setUserType(UserTypeEnum.ZF_COMMON.getCode());
        user3.setCreateTime(LocalDateTime.now());
        userMapper.insert(user3);

        //------------------------------street
        UserDO user4 = new UserDO();
        user4.setUsername("zhaobaoshan1");
        user4.setPassword(SHAUtil.Digest.SHA256.digest("zhaobaoshan1@123&"));
        user4.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user4.setCreateTime(LocalDateTime.now());
        userMapper.insert(user4);

        UserDO user5 = new UserDO();
        user5.setUsername("zhaobaoshan2");
        user5.setPassword(SHAUtil.Digest.SHA256.digest("zhaobaoshan2@123&"));
        user5.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user5.setCreateTime(LocalDateTime.now());
        userMapper.insert(user5);

        UserDO user6 = new UserDO();
        user6.setUsername("jiaochuan1");
        user6.setPassword(SHAUtil.Digest.SHA256.digest("jiaochuan1@123&"));
        user6.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user6.setCreateTime(LocalDateTime.now());
        userMapper.insert(user6);

        UserDO user7 = new UserDO();
        user7.setUsername("jiaochuan2");
        user7.setPassword(SHAUtil.Digest.SHA256.digest("jiaochuan2@123&"));
        user7.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user7.setCreateTime(LocalDateTime.now());
        userMapper.insert(user7);

        UserDO user8 = new UserDO();
        user8.setUsername("luotuo1");
        user8.setPassword(SHAUtil.Digest.SHA256.digest("luotuo1@123&"));
        user8.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user8.setCreateTime(LocalDateTime.now());
        userMapper.insert(user8);

        UserDO user9 = new UserDO();
        user9.setUsername("luotuo2");
        user9.setPassword(SHAUtil.Digest.SHA256.digest("luotuo2@123&"));
        user9.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user9.setCreateTime(LocalDateTime.now());
        userMapper.insert(user9);

        UserDO user10 = new UserDO();
        user10.setUsername("zhuangshi1");
        user10.setPassword(SHAUtil.Digest.SHA256.digest("zhuangshi1@123&"));
        user10.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user10.setCreateTime(LocalDateTime.now());
        userMapper.insert(user10);

        UserDO user11 = new UserDO();
        user11.setUsername("zhuangshi2");
        user11.setPassword(SHAUtil.Digest.SHA256.digest("zhuangshi2@123&"));
        user11.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user11.setCreateTime(LocalDateTime.now());
        userMapper.insert(user11);

        UserDO user12 = new UserDO();
        user12.setUsername("guisi1");
        user12.setPassword(SHAUtil.Digest.SHA256.digest("guisi1@123&"));
        user12.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user12.setCreateTime(LocalDateTime.now());
        userMapper.insert(user12);

        UserDO user13 = new UserDO();
        user13.setUsername("guisi2");
        user13.setPassword(SHAUtil.Digest.SHA256.digest("guisi2@123&"));
        user13.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user13.setCreateTime(LocalDateTime.now());
        userMapper.insert(user13);

        UserDO user14 = new UserDO();
        user14.setUsername("xiepu1");
        user14.setPassword(SHAUtil.Digest.SHA256.digest("xiepu1@123&"));
        user14.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user14.setCreateTime(LocalDateTime.now());
        userMapper.insert(user14);

        UserDO user15 = new UserDO();
        user15.setUsername("xiepu2");
        user15.setPassword(SHAUtil.Digest.SHA256.digest("xiepu2@123&"));
        user15.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user15.setCreateTime(LocalDateTime.now());
        userMapper.insert(user15);

        UserDO user16 = new UserDO();
        user16.setUsername("jiulonghu1");
        user16.setPassword(SHAUtil.Digest.SHA256.digest("jiulonghu1@123&"));
        user16.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user16.setCreateTime(LocalDateTime.now());
        userMapper.insert(user16);

        UserDO user17 = new UserDO();
        user17.setUsername("jiulonghu2");
        user17.setPassword(SHAUtil.Digest.SHA256.digest("jiulonghu2@123&"));
        user17.setUserType(UserTypeEnum.ZF_STREET.getCode());
        user17.setCreateTime(LocalDateTime.now());
        userMapper.insert(user17);
    }
}