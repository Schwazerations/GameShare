package com.game.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private String utel;

    private String upsw;

    private String checkinput;

    private String uuid;

    private List<GrantedAuthority> authorityList;

    public LoginUser(String utel, String upsw, List<GrantedAuthority> authorityList) {   // 用于 loadUserByUsername 方法封装UserDetails信息
        this.utel = utel;
        this.upsw = upsw;
        this.authorityList = authorityList;
    }

    @Override
    public String getPassword() {
        return this.getUpsw();
    }

    @Override
    public String getUsername() {   // 注意，这里返回的并不是 uname 而是 utel 。
        return this.getUtel();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

}

