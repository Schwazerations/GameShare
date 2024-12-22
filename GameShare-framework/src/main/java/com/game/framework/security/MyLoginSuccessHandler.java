package com.game.framework.security;

import com.game.dao.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("【Login success】");

        response.setContentType("application/json;charset=UTF-8");
//        ServletOutputStream outputStream = response.getOutputStream();


//        System.out.println(">>> 【Login success】jwt生成成功，token：" + jwt);
        System.out.println(">>> 【Login success】");

//        outputStream.write(JSONUtil.toJson(result).getBytes(StandardCharsets.UTF_8));
//        outputStream.flush();
//        outputStream.close();
    }
}
