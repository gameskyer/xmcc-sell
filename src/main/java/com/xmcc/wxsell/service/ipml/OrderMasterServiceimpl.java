package com.xmcc.wxsell.service.ipml;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.wxsell.common.*;
import com.xmcc.wxsell.dto.OrderDetailDto;
import com.xmcc.wxsell.dto.OrderDetailMasterDto;
import com.xmcc.wxsell.dto.OrderMasterDto;
import com.xmcc.wxsell.entity.OrderDetail;
import com.xmcc.wxsell.entity.OrderMaster;
import com.xmcc.wxsell.entity.ProductInfo;
import com.xmcc.wxsell.exception.CustomException;
import com.xmcc.wxsell.repository.OrderDetailRepository;
import com.xmcc.wxsell.repository.OrderMasterRepository;
import com.xmcc.wxsell.service.OrderDetailService;
import com.xmcc.wxsell.service.OrderMasterService;
import com.xmcc.wxsell.service.ProductInfoService;
import com.xmcc.wxsell.util.BigDecimalUtil;
import com.xmcc.wxsell.util.IDUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceimpl implements OrderMasterService {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    @Transactional
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //取出订单项
        /**
         * @Valid:用于配合JSR303注解（@NoBlank）,验证参数，只能在Controller层验证
         * validetor:在Service层验证
         * */
//        @NotEmpty(message = "订单项不能为空")
//        @Valid
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建集合来存储Ordetail
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        //设置初始化订单的总金额
        BigDecimal totalPrice = new BigDecimal("0");
        //遍历订单项，获取订单详情
        for (OrderDetailDto orderDetailDto:items){
            //查询订单
            ResultResponse<ProductInfo> resultResponse = productInfoService.queryById(orderDetailDto.getProductId());
            //判断ResultResponse的Code即可
            if(resultResponse.getCode() == ResultEnums.FAIL.getCode()){
                throw  new CustomException(resultResponse.getMsg());
            }
            //得到商品
            ProductInfo productInfo = resultResponse.getData();
            //比较库存
            if(productInfo.getProductStock()<orderDetailDto.getProductQuantity()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //创建订单项
            OrderDetail orderDetail =
                    OrderDetail.builder()
                            .detailId(IDUtils.createIdbyUUID())
                            .productIcon(productInfo.getProductIcon())
                            .productId(orderDetailDto.getProductId())
                            .productName(productInfo.getProductName())
                            .productPrice(productInfo.getProductPrice())
                            .productQuantity(orderDetailDto.getProductQuantity())
                    .build();
            //添加到订单项集合中
            orderDetailList.add(orderDetail);
            //减少库存
            productInfo.setProductStock(productInfo.getProductStock()-orderDetailDto.getProductQuantity());
            //跟新商品数据
            productInfoService.updateProuct(productInfo);
            //计算价格
            totalPrice = BigDecimalUtil.add(totalPrice
                    , BigDecimalUtil.multi(productInfo.getProductPrice(),orderDetailDto.getProductQuantity() ));
        }

        //生成订单id
        String idbyUUID = IDUtils.createIdbyUUID();
        //构建订单信息
        OrderMaster orderMaster = OrderMaster.builder()
                .orderId(idbyUUID)
                .buyerAddress(orderMasterDto.getAddress())
                .buyerName(orderMasterDto.getName())
                .buyerOpenid(orderMasterDto.getOpenid())
                .buyerPhone(orderMasterDto.getPhone())
                .orderAmount(totalPrice)
                .orderStatus(OrderEnums.NEW.getCode())
                .payStatus(PayEnums.WAIT.getCode())
                .build();
        //将订单ID设置到订单项中
        List<OrderDetail> orderDetailList1 = orderDetailList.stream().map(orderDetail ->
        {
            orderDetail.setOrderId(idbyUUID);
            return orderDetail;
        }).collect(Collectors.toList());
        //批量插入订单项
        orderDetailService.batchInsert(orderDetailList1);
        //插入订单
        orderMasterRepository.save(orderMaster);
        HashMap<String,String> map = Maps.newHashMap();
        map.put("orderId",idbyUUID );
        return ResultResponse.success(map);
    }

    @Override
    public ResultResponse listOrder(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<OrderMaster> all = orderMasterRepository.findAll(pageable);
        System.out.println(all.toString());
        List<OrderMaster> content = all.getContent();
        if(all.isEmpty()){
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        return ResultResponse.success(content);
    }


    @Override
    public ResultResponse<OrderMaster> OderDetail(String orderId,String openId) {
        OrderMaster orderMaster = orderMasterRepository.findByOrderIdAndBuyerOpenid(orderId,openId);
        if(orderMaster == null){
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        OrderDetailMasterDto orderDetailMasterDto = OrderDetailMasterDto.build(orderMaster);
        List<OrderDetail> orderDetails =orderDetailRepository.findByOrderId(orderId);
        orderDetailMasterDto.setOrderDetailList(orderDetails);
        return ResultResponse.success(orderDetailMasterDto);
    }

    @Override
    @Transactional
    public ResultResponse removeOrder(String orderId, String openId) {
        OrderMaster orderMaster = orderMasterRepository.findByOrderIdAndBuyerOpenid(orderId,openId);
        if(orderMaster == null){
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        orderMaster.setOrderStatus(OrderEnums.CANCEL.getCode());
        List<OrderDetail> byOrderId = orderDetailRepository.findByOrderId(orderId);
        //查询订单
        for (OrderDetail orderDetail:byOrderId) {
            ResultResponse<ProductInfo> resultResponse = productInfoService.queryById(orderDetail.getProductId());
            //判断ResultResponse的Code即可
            if (resultResponse.getCode() == ResultEnums.FAIL.getCode()) {
                throw new CustomException(resultResponse.getMsg());
            }
            //得到商品
            ProductInfo productInfo = resultResponse.getData();
            productInfo.setProductStock(productInfo.getProductStock()+orderDetail.getProductQuantity());
            productInfoService.updateProuct(productInfo);
        }
        orderMasterRepository.save(orderMaster);
        return ResultResponse.success();
    }
}
