package com.xmcc.wxsell.Controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
@Slf4j
public class TestController2 {
    //    Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping("test2")
    public String string(){
        log.info("info->{}","hello");
        return "hello";
    }
}