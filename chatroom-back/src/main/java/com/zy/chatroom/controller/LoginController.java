package com.zy.chatroom.controller;

import com.zy.chatroom.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.zy.chatroom.entity.User;
import com.zy.chatroom.service.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redis;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("account")String account,
                            @RequestParam("password")String password,
                            HttpServletResponse response){
        User user = userService.loginCheck(account, password);
        if(user!=null){
            //redis.opsForValue().set(JWTUtils.getToken(user), user.getUid());
            String token = JWTUtils.getToken(user);
            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Expose-Headers","Account");
            response.addHeader("Authorization",token);
            response.addHeader("Account",account);
            //return user.getUid().toString();
            return "SUCCESS";
        }
        return "ERROR";
    }

    @GetMapping("/haha")
    @ResponseBody
    public String getCurrentUser(){
        return "123456";
    }
}
