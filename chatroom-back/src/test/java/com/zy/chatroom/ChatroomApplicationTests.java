package com.zy.chatroom;

import com.zy.chatroom.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class ChatroomApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Test
    void insertUser() {//insert
        redisTemplate.opsForValue().set("113","sw");

        //RedisUtil.set("sddddddde",22);
    }

}
