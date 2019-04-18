package com.xmcc.wxsell.service;

import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.dto.OrderMasterDto;
import com.xmcc.wxsell.entity.OrderMaster;
import org.springframework.data.domain.Page;

public interface OrderMasterService {
    ResultResponse insertOrder(OrderMasterDto orderMasterDto);
    ResultResponse listOrder(Integer page, Integer size);
    ResultResponse<OrderMaster> OderDetail(String orderId,String openId);
    ResultResponse removeOrder(String orderId,String openId);
}
