package com.xmcc.wxsell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xmcc.wxsell.entity.OrderMaster;
import org.springframework.stereotype.Service;

@Service
public interface PayService {
    OrderMaster findOneById(String orderId);

    PayResponse create(OrderMaster orderMaster);

    void weixin_notify(String notifyData);

    RefundResponse refund(OrderMaster orderMaster);
}
