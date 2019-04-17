package com.xmcc.wxsell.repository;

import com.xmcc.wxsell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
