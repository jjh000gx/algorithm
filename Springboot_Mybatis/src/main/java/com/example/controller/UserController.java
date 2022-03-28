package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.service.UserService;
import com.example.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/testBoot")
@Scope("prototype")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WechatUserService wechatUserService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){
        System.out.println("dsfds");
        return JSONObject.toJSONString(userService.Sel(id));
    }

    @RequestMapping("getWeUser/{id}")
    public String getWeUser(@PathVariable int id){
        return JSONObject.toJSONString(wechatUserService.Sel(id));
    }
}
