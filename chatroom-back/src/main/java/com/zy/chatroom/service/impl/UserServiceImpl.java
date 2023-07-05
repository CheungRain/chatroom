package com.zy.chatroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.chatroom.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zy.chatroom.mapper.UserMapper;
import com.zy.chatroom.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User loginCheck(String account, String password) {
        return userMapper.loginCheck(account,password);
    }

    @Override
    public String registerCheck(String account,String password) {
        User user = userMapper.selectByAccount(account);
        if(user==null) {
            userMapper.insertUser(account, password);
            return "SUCCESS";
        }else{
            return "ERROR";
        }
    }

    @Override
    public User getUserByAccount(String account){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public String getAccountById(Integer uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        User user = userMapper.selectOne(queryWrapper);
        String account = user.getAccount();
        return account;
    }


}
