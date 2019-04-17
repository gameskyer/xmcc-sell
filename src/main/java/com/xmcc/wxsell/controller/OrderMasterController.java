package com.xmcc.wxsell.controller;

import com.google.common.collect.Maps;
import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.dto.OrderMasterDto;
import com.xmcc.wxsell.entity.OrderMaster;
import com.xmcc.wxsell.service.OrderMasterService;
import com.xmcc.wxsell.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;
    @RequestMapping("/create")
    @ApiOperation(value = "创建订单",httpMethod = "POST",response = ResultResponse.class)
    public ResultResponse create(@Valid @ApiParam(name = "订单对象",value = "传入json格式"
            ,required = true)OrderMasterDto orderMasterDto, BindingResult bindingResult){


        //创建Map接收参数
        Map<String,String> map = Maps.newHashMap();
        //判断参数是否有误
        if(bindingResult.hasErrors()){
            List<String> collect = bindingResult.getFieldErrors().stream().map(err ->
                    err.getDefaultMessage()).collect(Collectors.toList());
            map.put("参数校检异常", JsonUtil.object2string(collect));
           return ResultResponse.fail();
        }
        return orderMasterService.insertOder(orderMasterDto);
    }
}
