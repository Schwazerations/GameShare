package com.game.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.game.entity.User;
import com.game.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/listAll")
    public List<User> getAllUser(){
        return userMapper.selectList(null);
    }

    @PostMapping("/user")
    @Operation(summary = "增加用户")
    public int addUser(@PathVariable User user){
        return userMapper.insert(user);
    }

    @DeleteMapping("/user/{uid}")
    @Operation(summary = "删除用户")
    public int deleteUser(@PathVariable String uid){
        return userMapper.deleteById(uid);
    }





}
