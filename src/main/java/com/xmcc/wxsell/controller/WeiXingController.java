package com.xmcc.wxsell.controller;

import com.xmcc.wxsell.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.URIParameter;

@Controller
@RequestMapping("weixin")
@Slf4j
public class WeiXingController
{
    @Autowired
    private WxMpService wxMpService;
    @RequestMapping("getCode")
    @ResponseBody
    public  void getCode(@RequestParam("code") String code){
        log.info("成功回调Code方法" );
        log.info("微信授权码：{}",code  );
    }

    @RequestMapping("authorize")
    public String authoriza(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        //自己编写获得Openid的路径 在下面定义方法GetUserInfo
        String url="http://xinglin.natapp1.cc/sell/weixin/getUserInfo";
        //根据sdk文档获得路径  点击方法下载文档 很清晰的解释
        /**
         * 第一个参数是获得授权码code后回调的地址
         * 第二个是策略：获得简单的授权，还是希望获得用户的信息
         * 第三个参数是我们希望携带的参数:查看API文档需要返回returnUrl 所以我们就携带它
         */
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"UTF-8" ));
        System.out.println(redirectUrl);
        return "redirect:"+redirectUrl;
    }
    @RequestMapping("getUserInfo")
    public String getUserInfo(@RequestParam("code")String code,@RequestParam("state") String returnUrl) throws UnsupportedEncodingException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        //根据sdk文档 通过code获得令牌与openid
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
           log.error("微信获得Access_token异常：{}",e.getError().getErrorMsg() );
           throw new CustomException(e.getError().getErrorCode(),e.getError().getErrorMsg());
        }
        try {
            //获得用户信息  ,授权其实用不到的 这儿打出来看看
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            log.info("获得用户信息:{}",wxMpUser.getNickname());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+ URLDecoder.decode(returnUrl,"UTF-8" )+"?openid="+openId;

    }
    @RequestMapping("testOpenid")
    @ResponseBody
    public void testOpenid(@RequestParam("openid")String openid){
        log.info("获得用户的openid为:{}",openid );
    }
}