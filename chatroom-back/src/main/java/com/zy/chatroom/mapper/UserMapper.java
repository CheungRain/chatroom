package com.zy.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.chatroom.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public void insertUser(String account,String password);
    public User selectByAccount(String account);
    public User loginCheck(String account,String password);
}
