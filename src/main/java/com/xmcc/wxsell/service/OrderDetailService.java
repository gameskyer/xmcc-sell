package com.xmcc.wxsell.service;

import com.xmcc.wxsell.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    //批量插入
    void batchInsert(List<OrderDetail> list);
}
