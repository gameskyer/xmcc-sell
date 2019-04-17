package com.xmcc.wxsell.service;

import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.dto.OrderMasterDto;

public interface OrderMasterService {
    ResultResponse insertOder(OrderMasterDto orderMasterDto);
}
