package com.xmcc.wxsell.repository;

import com.xmcc.wxsell.dto.OrderDetailMasterDto;
import com.xmcc.wxsell.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    OrderMaster findByOrderIdAndBuyerOpenid(String orderId,String openId);

    int deleteByOrderIdAndBuyerOpenid(String orderId, String openId);
}
