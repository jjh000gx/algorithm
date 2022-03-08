package com.example.service;

import com.example.entity.testtwo.WechatUser;
import com.example.mapper.testtwo.WechatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatUserService {
    @Autowired
    WechatUserMapper wechatUserMapper;
    public WechatUser Sel(int id){
        return wechatUserMapper.Sel(id);
    }
}
