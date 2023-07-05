package com.zy.chatroom.entity;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Message {
    private String fromWho;
    private String toWho;
    private String text;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date date;

}
