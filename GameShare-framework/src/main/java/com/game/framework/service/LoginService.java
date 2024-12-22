package com.game.framework.service;

import com.game.common.constants.CacheConstants;
import com.game.common.core.Redis.RedisCache;
import com.game.common.core.domain.entity.User;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.core.domain.model.RegisterUser;
import com.game.common.exception.BaseException;
import com.game.common.exception.user.BadUsernamePasswordException;
import com.game.common.exception.user.CaptchaException;
import com.game.common.exception.user.RegisterUsernameNotUniqueException;
import com.game.common.utils.JwtUtil;
import com.game.common.utils.Result;
import com.game.common.utils.StrUtil;
import com.game.dao.mapper.UserMapper;
import com.game.framework.security.Encoder.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyPasswordEncoder mypasswordEncoder;


    public String login(LoginUser loginUser)
    {
        // 前置校验
        preValidate(loginUser);
        // 校验验证码
        validateCaptcha(loginUser.getCheckinput(), loginUser.getUuid());
        // 正式校验
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        /* 注意这行代码会调用 loadUserByUsername 方法    */
        authenticationManager.authenticate(authenticationToken);
        // 发令牌
        return generateToken(loginUser);
    }

    /*
     * 事务失效的场景：
     * 1，异常类型不为RuntimeException 或 try-catch吞了异常；
     * 2，方法不为public；
     * 3，方法为多线程；
     */
    @Transactional
    public String register(RegisterUser registerUser) {
        // 前置校验
        preValidate(registerUser);
        // 验证码校验
        validateCaptcha(registerUser.getCheckinput(), registerUser.getUuid());
        // 唯一性校验
        if (userMapper.selectUserByUtel(registerUser.getUtel()) != null) {
            throw new RegisterUsernameNotUniqueException();
        }
        // 插入数据库
        User user = new User();
        user.setUname(StrUtil.nvl(registerUser.getUname(), null));
        user.setUtel(registerUser.getUtel());
        user.setUpsw(mypasswordEncoder.encode(registerUser.getUpsw()));
        user.setUemail(StrUtil.nvl(registerUser.getUemail(), null));
        user.setUgender(StrUtil.nvl(registerUser.getUgender(), null));
        user.setUaddress(StrUtil.nvl(registerUser.getUaddress(), null));
        user.setUrole("user");  // 设定默认角色为普通用户
        user.setUpoint(20f);    // 设定新用户积分为 20
        userMapper.insertUserAndSetUid(user);     // 插入user表，并回填主键
        String uid = user.getUid();
        userMapper.insertUserRole(uid, 2);      // 插入user_role表，2为普通USER角色的ID

        return generateToken(registerUser);
    }


    private void preValidate(LoginUser loginUser) {
        if (StrUtil.isSpaceOrNull(loginUser.getUsername()) || StrUtil.isSpaceOrNull(loginUser.getPassword())) {
            throw new BadUsernamePasswordException();
        }
        // 注册时的表单验证
        if (loginUser instanceof RegisterUser user) {
            String msg = "";
            if (!user.getUname().matches(".{1,20}")) {
                msg += "请保持用户名长度在1-20位之间；";
            }
            if (!user.getUtel().matches("^1\\d{10}$")) {
                msg += "不是合法的中国大陆手机号；";
            }
            if (!user.getUpsw().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                msg += "密码长度需要在8-20位之间，包含至少一个小写字母、一个大写字母和一个数字；";
            }
            if (!user.getUpsw().equalsIgnoreCase(user.getSecond_pwd())) {
                msg += "两次输入的密码不一致；";
            }
            if (StrUtil.isNotNull(user.getUemail()) &&
                    !user.getUemail().matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
                msg += "不是合法的邮箱地址；";
            }
            if (!StrUtil.isSpaceOrNull(msg)) {
                throw new BaseException("user", "user.login.input.invalid", msg);
            }
        }
        System.out.println("前置校验成功。");
    }

    public void validateCaptcha(String checkCode, String uuid) {
        String verifyCode = CacheConstants.Captcha_Cache_Key + StrUtil.nvl(uuid, "");
        String cache_code = (String) redisCache.getCacheObject(verifyCode);
        if (StrUtil.isSpaceOrNull(cache_code) || !cache_code.equalsIgnoreCase(checkCode)) {
            throw new CaptchaException();
        }
        redisCache.removeCacheObject(verifyCode);
        System.out.println("验证码校验成功。");
    }

    private String generateToken(LoginUser loginUser) {
        User user = userMapper.selectUserByUtel(loginUser.getUtel());
        HashMap<String, String> claims = new HashMap<>();
        claims.put("uname", user.getUname());
        return JwtUtil.generateToken(user.getUid(), claims);    // jwt包含信息：uid, uname
    }

}
