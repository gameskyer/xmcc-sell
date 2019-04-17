package com.xmcc.wxsell.service.ipml;

import com.xmcc.wxsell.dao.ipml.BatchDaoipml;
import com.xmcc.wxsell.entity.OrderDetail;
import com.xmcc.wxsell.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceimpl extends BatchDaoipml<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional//加入事务管理
    public void batchInsert(List<OrderDetail> list) {
        super.batchInsert(list);
    }
}
