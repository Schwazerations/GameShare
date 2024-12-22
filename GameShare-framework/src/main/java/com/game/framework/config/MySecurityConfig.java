package com.game.framework.config;

import com.game.framework.security.*;
import com.game.framework.security.Encoder.MyPasswordEncoder;
import com.game.framework.service.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public MyLoginAuthenticationProvider myLoginAuthenticationProvider() {
        return new MyLoginAuthenticationProvider(userDetailsServiceImpl, myPasswordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(myLoginAuthenticationProvider());
    }

    @Autowired
    private MyUserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Bean
    public MyJwtAuthenticationFilter jwtAuthenticationFilter() {
        return new MyJwtAuthenticationFilter();
    }
    @Bean
    public MyJwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new MyJwtAuthenticationEntryPoint();
    }

    @Autowired
    private CustomCorsConfigurationSource customCorsConfigurationSource;


    private static final String[] URL_WHITELIST = {
            "/error",
            "/register",
            "/login",
            "/logout",
            "/captchaImage",        //验证码
            "/favicon.ico",     // 前端的某个图标
            "/v3/api-docs/**","/swagger**/**",  // swagger
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(customCorsConfigurationSource))      // 解决security 跨域问题
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)     // 配置自定义JWT认证过滤器
//                .formLogin(form -> form     // 启用/login路径交由security管理
//                        .usernameParameter("utel").passwordParameter("upsw")
//                        .loginPage("/login")
//                        .successHandler(loginSuccessHandler())      // 配置自定义登录成功处理器（解决302问题）
//                        .failureHandler(loginFailureHandler())      // 配置自定义登录失败处理器
//                )
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint())  // 配置自定义JWT认证失败处理器
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(URL_WHITELIST).permitAll()
                            .anyRequest().authenticated();
                    }
                )
        ;

        return http.build();
    }
}
