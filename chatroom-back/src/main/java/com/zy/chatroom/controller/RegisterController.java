package com.zy.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zy.chatroom.service.UserService;

@Controller
@CrossOrigin
public class RegisterController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam("account") String account,
                           @RequestParam("password") String password){
        String result = userService.registerCheck(account,password);
        return result;
    }
}
