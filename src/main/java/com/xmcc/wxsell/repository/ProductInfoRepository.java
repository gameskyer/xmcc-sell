package com.xmcc.wxsell.repository;

import com.xmcc.wxsell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //根据类目的编号和状态查询商品
    List<ProductInfo> findAllByProductStatusAndCategoryTypeIn(Integer status,List<Integer> typeList);

}
