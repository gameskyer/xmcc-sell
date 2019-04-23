package com.xmcc.wxsell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.Data;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class PayConfig {

    //进行payweixin支付的配置
    public BestPayService bestPayService(){
        WxPayH5Config wxPayH5Config =new WxPayH5Config();
        wxPayH5Config.setAppId("wxcec0b9e65c084712");
        wxPayH5Config.setAppSecret("05a7e861c1985ced86af77fb8f7163bc");
        wxPayH5Config.setMchId("1529533061");
        wxPayH5Config.setKeyPath("F:\\SpringBoot\\wx-sell\\src\\main\\resources\\static\\apiclient_cert.p12");
        wxPayH5Config.setMchKey("qwertyuiopasdfghjklzxcvbnm123456");
        wxPayH5Config.setNotifyUrl(" http://xmccjyqs.natapp1.cc/sell/pay/notify");
        //支付类，所有方法都在这个类里面
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return  bestPayService;
    }


}
