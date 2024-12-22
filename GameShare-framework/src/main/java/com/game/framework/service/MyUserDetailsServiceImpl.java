package com.game.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.exception.user.UserNotFoundException;
import com.game.dao.mapper.PermissionMapper;
import com.game.dao.mapper.RoleMapper;
import com.game.dao.mapper.UserMapper;
import com.game.common.core.domain.entity.Permission;
import com.game.common.core.domain.entity.Role;
import com.game.common.core.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录服务实现类型
 */
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserMapper userMapper;
    @Autowired private RoleMapper roleMapper;
    @Autowired private PermissionMapper permissionMapper;

    /**
     * 方法实现内容：根据用户名（在这里指手机号）查询用户对象，和用户权限列表
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String utel) throws UsernameNotFoundException {
        System.out.println("【Loading User By Username...】调用loadUserByUsername方法，现在正在找该用户及其权限集合：");
        // 查用户
        User user = getUser(utel);
        // 查权限
        List<String> authorityList = getAuthorityList(user);
        // 生成并返回UserDetails对象
        return generateUserDetails(user.getUtel(), user.getUpsw(), authorityList);
    }

    private User getUser(String utel) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUtel , utel);       //where utel=参数
        User user = userMapper.selectOne(lqw);
        if(ObjectUtils.isEmpty(user)) {
            throw new UserNotFoundException();
        }

        return user;
    }

    private List<String> getAuthorityList(User user){
        //开始查询该用户的权限集合：
        //1，查询角色：
        List<Role> roles = roleMapper.selectRolesByUtel(user.getUtel());
        //2，查询权限：
        List<Permission> permissionList = permissionMapper.selectPermissionByUserId(user.getUid());
        //3，定义一个字符串泛型的list集合对象：
        List<String> authorityList = new ArrayList<>();
        for(Role role : roles){
            authorityList.add("ROLE_" + role.getName());    //角色名称纳入到权限管理，必须加入前缀 ‘ROLE_’
        }
        for (Permission m : permissionList){
            authorityList.add(m.getPermit());   //权限字符串描述，不需要加入前缀‘ROLE_’
        }

        System.out.println("已查找到该用户。其权限列表为："+authorityList);
        return authorityList;
    }

    /**
     * 必须返回一个实现了UserDetails接口的User对象；这里是LoginUser。
     */
    private UserDetails generateUserDetails(String utel, String upsw, List<String> authorityList){
        return new LoginUser(
                utel,     // 用户名
                upsw,
                AuthorityUtils.createAuthorityList(authorityList)   //权限列表
        );
    }

}
