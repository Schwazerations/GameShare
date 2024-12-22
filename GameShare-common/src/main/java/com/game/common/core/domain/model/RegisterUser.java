package com.game.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)    // 需要声明 toString, hashCode, equals 方法不考虑父类元素
@Data
public class RegisterUser extends LoginUser {

    private String uname;

    private String second_pwd;

    private String uemail;

    private String ugender;

    private String uaddress;
}
