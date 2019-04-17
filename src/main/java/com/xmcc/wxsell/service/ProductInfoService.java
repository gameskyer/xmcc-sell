package com.xmcc.wxsell.service;

import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.entity.ProductInfo;
import org.springframework.stereotype.Service;

@Service
public interface ProductInfoService {
    ResultResponse queryList();

    ResultResponse<ProductInfo> queryById(String productId);

    void updateProuct(ProductInfo productInfo);
}
