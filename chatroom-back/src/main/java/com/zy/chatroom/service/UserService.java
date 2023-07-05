package com.zy.chatroom.service;

import com.zy.chatroom.entity.User;

public interface UserService {
    public User loginCheck(String account, String password);
    public String registerCheck(String account,String password);
    public User getUserByAccount(String account);
    public String getAccountById(Integer uid);

}
