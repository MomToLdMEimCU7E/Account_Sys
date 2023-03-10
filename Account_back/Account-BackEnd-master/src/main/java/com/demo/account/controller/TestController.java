package com.demo.account.controller;

import com.demo.account.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    UserMapper userMapper;

    @RequestMapping("/1")
    public String test(){
        //Date date = new Date();
        //Timestamp timeStamp = new Timestamp(date.getTime());
        //userMapper.addUser(2,"h","j",timeStamp);
        int k=userMapper.uidGenerate();
        System.out.println(k);
        return "success";
    }
}
