package com.xmcc.wxsell.controller;

import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.service.ProductInfoService;
import com.xmcc.wxsell.service.lpml.ProductInfoServicelpml;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    private ProductInfoServicelpml productInfoServicelpml;
    @RequestMapping("/list")
    @ApiOperation(value = "查询商品列表")
    public ResultResponse list(){

        return productInfoServicelpml.queryList();
    }
}
