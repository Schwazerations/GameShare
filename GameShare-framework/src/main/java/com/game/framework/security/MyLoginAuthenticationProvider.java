package com.game.framework.security;
import com.game.common.exception.user.PasswordNotMatchException;
import com.game.framework.security.Encoder.MyPasswordEncoder;
import com.game.framework.security.context.MyAuthenticationContextHolder;
import com.game.framework.service.MyUserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MyLoginAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsServiceImpl userDetailsService;
    private final MyPasswordEncoder passwordEncoder;

    public MyLoginAuthenticationProvider(MyUserDetailsServiceImpl userDetailsService, MyPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("【Auth Provider】开始校验密码……");

        // 从客户端获取的用户信息：
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 从数据库获取的用户信息：
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!ObjectUtils.isEmpty(userDetails) && passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("【Auth Provider】密码匹配");
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            System.err.println("【Auth Provider】密码不匹配！");
            throw new PasswordNotMatchException();
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}