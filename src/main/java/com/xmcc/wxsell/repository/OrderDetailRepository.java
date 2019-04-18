package com.xmcc.wxsell.repository;

import com.xmcc.wxsell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String openId);

    int deleteByOrderId(String orderId);
}
