package com.xmcc.wxsell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.xmcc.wxsell.entity.OrderMaster;
import com.xmcc.wxsell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView cteate(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl){
        //根据Id查询订单
        OrderMaster oneById = payService.findOneById(orderId);
        Map map = new HashMap();
        //根据订单创建支付
        PayResponse payResponse = payService.create(oneById);
        map.put("payResponse", payResponse);
        map.put("returnUrl",returnUrl );
        return new ModelAndView("weixin/pay",map);
    }
    @RequestMapping("notify")
    public ModelAndView wexin_notify(@RequestBody String notifyData){
        log.info("notifyData:->{}",notifyData );
        //验证数据，修改订单
        payService.weixin_notify(notifyData);
        return new ModelAndView("wexin/success");
    }
    @RequestMapping("test")
    public void test(){
        log.info("异步回调成功" );
    }

}
