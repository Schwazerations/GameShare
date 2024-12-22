package com.game.web.controller;

import com.game.common.core.domain.model.LoginUser;
import com.game.common.core.domain.model.RegisterUser;
import com.game.common.exception.BaseException;
import com.game.common.exception.ServiceException;
import com.game.common.utils.Result;
import com.game.framework.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
        try {
            String Token = loginService.login(loginUser);
            return Result.ok().message("登录成功").data("Token", Token);
        } catch (BaseException e) {
            return Result.error().data("ErrorCode", e.getCode());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return Result.error().data("ErrorCode", "系统未知异常").data("ErrorMessage", e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterUser user) {
        try {
            String Token = loginService.register(user);
            return Result.ok().message("注册成功").data("Token", Token);
        } catch (BaseException e) {
            return Result.error().data("ErrorCode", e.getCode()).data("ErrorMessage", e.getDefaultMessage());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return Result.error().data("ErrorCode", "系统未知异常").data("ErrorMessage", e.getMessage());
        }

    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}
