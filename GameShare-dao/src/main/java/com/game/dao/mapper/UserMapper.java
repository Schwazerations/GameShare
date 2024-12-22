package com.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.common.core.domain.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where uid=#{uid}")
    User selectUserByUid(String uid);

    @Select("select * from user where utel=#{utel}")
    User selectUserByUtel(String utel);

//    @Insert("""
//        Insert into user(uname, utel, upsw, uemail, ugender, uaddress, upoint, urole)
//        values (#{uname}, #{utel}, #{upsw}, #{uemail}, #{ugender}, #{uaddress}, #{upoint}, #{urole})
//    """)
//    int insertUser(User user);


    @Insert("""
        INSERT INTO user (uname, utel, upsw, uemail, ugender, uaddress, upoint, urole)
        VALUES (#{uname}, #{utel}, #{upsw}, #{uemail}, #{ugender}, #{uaddress}, #{upoint}, #{urole})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")   // 主键回填
    void insertUserAndSetUid(User user);

    @Insert("insert into user_role (user_id, role_id) VALUES (#{uid}, #{roleId})")
    void insertUserRole(String uid, int roleId);




    @Update("update user set upoint = upoint - #{price} where uid = #{uid};")
    void deductPoint(String uid, Float price);

    @Update("update user set upoint = upoint + #{point} where uid = #{uid};")
    void risePoint(String uid, Float point);

    @Update("insert into sign_in values(#{uid}, #{date})")
    void insertSignIn(String uid, String date);

    @Select("select count(*) from sign_in where uid = #{uid} and created_on = #{date}")
    int queryIsSignedIn(String uid, String date);
}
