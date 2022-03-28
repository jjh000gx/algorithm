package com.example.mapper.testone;

import com.example.entity.testone.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User Sel(int id);
}
