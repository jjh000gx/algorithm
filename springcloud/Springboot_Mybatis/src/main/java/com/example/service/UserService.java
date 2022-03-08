package com.example.service;

import com.example.entity.testone.User;
import com.example.mapper.testone.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
@Scope("prototype")
public class UserService {

    private   int i = 0;
    @Autowired
    UserMapper userMapper;
    public synchronized User Sel(int id)
    {
        i++;
        System.out.println("single==:"+ i);
        return userMapper.Sel(id);
    }
}
