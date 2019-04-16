package com.xmcc.wxsell.service.lpml;

import com.xmcc.wxsell.common.ResultEnums;
import com.xmcc.wxsell.common.ResultResponse;
import com.xmcc.wxsell.dto.ProductCategoryDto;
import com.xmcc.wxsell.dto.ProductInfoDto;
import com.xmcc.wxsell.entity.ProductCategory;
import com.xmcc.wxsell.entity.ProductInfo;
import com.xmcc.wxsell.repository.ProductCategoryRepository;
import com.xmcc.wxsell.repository.ProductInfoRepository;
import com.xmcc.wxsell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductInfoServicelpml implements ProductInfoService {
  @Autowired
  private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ResultResponse queryList() {
        //查询所有分类
        List<ProductCategory> all = productCategoryRepository.findAll();
        //将all转换为Dto
        List<ProductCategoryDto> productCategoryDtosList = all.stream().map(productCategory ->
                ProductCategoryDto.build(productCategory)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }
        //获取类目编号集合
        List<Integer> typeList = productCategoryDtosList.stream().map(productCategoryDto ->
                productCategoryDto.getCategoryType()).collect(Collectors.toList());
        //根据类目编号集合查询商品列表
        List<ProductInfo> productInfoList
                = productInfoRepository.findAllByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);
//        对productCategoryDtosList集合进行遍历 取出每个商品的类目编号 设置到对应的目录中
        //将productInfo设置到foodsz中
        //过滤：不同的type,进行不同的封装
        //将productInfo转成Dto
        List<ProductCategoryDto> productCategoryDtos = productCategoryDtosList.parallelStream().map(productCategoryDto -> {
            productCategoryDto.setProductInfoDtoList(productInfoList.stream().filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));

            return productCategoryDto;
        }).collect(Collectors.toList());
        return ResultResponse.success(productCategoryDtos);
    }
}
