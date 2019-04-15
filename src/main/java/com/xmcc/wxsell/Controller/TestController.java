package com.xmcc.wxsell.Controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
@Slf4j
public class TestController {
//    Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping("test")
    public String string(){
        log.info("info->{}","hello");
        return "dsadsa";
    }
}
