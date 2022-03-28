package com.example.mapper.testtwo;

import com.example.entity.testtwo.WechatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatUserMapper {
    WechatUser Sel(int id);
}
