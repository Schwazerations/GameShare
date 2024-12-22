package com.game.framework.security;

import com.game.common.core.domain.entity.User;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.utils.StrUtil;
import com.game.dao.mapper.UserMapper;
import com.game.common.utils.JwtUtil;
import com.game.common.utils.Result;
import com.game.framework.service.MyUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class MyJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = getJwt(authorizationHeader);
        SecurityContext context = SecurityContextHolder.getContext();

        if (StrUtil.isSpaceOrNull(jwt)) {
            System.out.println("【Jwt Auth】 jwt为空!");
        } else {
            System.out.println("【Jwt Auth】 请求头附带的jwt是：" + authorizationHeader);
        }
        if (ObjectUtils.isEmpty(context.getAuthentication())) {
            System.out.println("【Jwt Auth】 用户状态：未授权!");
        } else {
            System.out.println("【Jwt Auth】 用户状态：已授权");
        }

        if (jwt != null && context.getAuthentication() == null) {
            Result result = JwtUtil.validateJWT(jwt);
            if (result.isSuccess()) {
                String uid = (String)result.getData().get("uid");
                String uname = (String)result.getData().get("uname");
                User user = userMapper.selectUserByUid(uid);
                String utel = user.getUtel();

                System.out.println("【Jwt Auth】 jwt验证通过，用户id：" + uid + " ；uname：" + uname);
                UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(utel);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authentication);       // 设置到Security上下文中
                System.out.println("【Jwt Auth】 权限设置成功。" + context.getAuthentication().getAuthorities());
            } else {
                System.out.println("【Jwt Auth】 jwt校验失败：" + result.getMessage());
            }
        }

        chain.doFilter(request, response);
    }

    private static String getJwt(String authorizationHeader) {
        String jwt = null;
        if (StrUtil.isNotNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }
        return jwt;
    }

    /**
     * 获取 UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String utel) {
        UserDetails userDetails  = myUserDetailsService.loadUserByUsername(utel);

        return new UsernamePasswordAuthenticationToken(
                utel,   // 用户名
                null,   // 密码
                userDetails.getAuthorities()  // 权限列表
        );
    }
}
