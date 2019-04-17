package com.xmcc.wxsell.controller;

import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.service.ProductInfoService;
import com.xmcc.wxsell.service.ipml.ProductInfoServiceipml;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/product")
@Api(description = "商品信息接口")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductInfoServiceipml productInfoServiceipml;
    @RequestMapping("/list")
    @ApiOperation(value = "查询商品列表")
    public ResultResponse list(){

        return productInfoServiceipml.queryList();
    }
}
