package com.xmcc.wxsell.config;

import com.xmcc.wxsell.properties.WeiXinProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatConfig {
    @Autowired
    private WeiXinProperties weiXinProperties;
    @Bean
    public WxMpService wxMpServoce(){
        WxMpService wxMpService  = new WxMpServiceHttpClientImpl();
        //设置微信配置的存储
        wxMpService .setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService ;

    }
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage
                = new WxMpInMemoryConfigStorage();
        //设置appid  这个在项目中肯定是通过配置来实现
        wxMpInMemoryConfigStorage.setAppId(weiXinProperties.getAppid());
        //设置密码
        wxMpInMemoryConfigStorage.setSecret(weiXinProperties.getSecret());
        return wxMpInMemoryConfigStorage;
    }
}
