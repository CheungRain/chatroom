package com.zy.chatroom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "userinfo")
public class User {
    @TableId("uid")
    private Integer uid;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;


    public User(Integer uid, String account) {
        super();
        this.uid = uid;
        this.account = account;
    }
}
