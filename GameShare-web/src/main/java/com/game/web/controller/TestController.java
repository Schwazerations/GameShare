package com.game.web.controller;

import com.game.common.utils.Result;
import com.game.dao.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService test;

    @GetMapping("/redis")
    public Result Redis(){
        return test.redis();
    }
}
