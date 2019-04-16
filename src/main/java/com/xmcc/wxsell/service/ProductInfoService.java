package com.xmcc.wxsell.service;

import com.xmcc.wxsell.common.ResultResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductInfoService {
    ResultResponse queryList();
}
