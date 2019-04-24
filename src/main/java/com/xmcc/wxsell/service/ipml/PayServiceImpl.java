package com.xmcc.wxsell.service.ipml;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.xmcc.wxsell.common.Constant;
import com.xmcc.wxsell.common.OrderEnums;
import com.xmcc.wxsell.common.PayEnums;
import com.xmcc.wxsell.entity.OrderMaster;
import com.xmcc.wxsell.exception.CustomException;
import com.xmcc.wxsell.repository.OrderMasterRepository;
import com.xmcc.wxsell.service.PayService;
import com.xmcc.wxsell.util.BigDecimalUtil;
import com.xmcc.wxsell.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
@Service
@Slf4j

public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public OrderMaster findOneById(String orderId) {
        Optional<OrderMaster> byId = orderMasterRepository.findById(orderId);
        if(!byId.isPresent()){
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        return byId.get();
    }

    @Override
    public PayResponse create(OrderMaster orderMaster) {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOpenid(orderMaster.getOrderId());
        payRequest.setOrderName(Constant.ORDER_NAME);
        payRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderMaster.getBuyerOpenid());
        log.info("微信支付的请求:{}", JsonUtil.object2string(payRequest));
        PayResponse response = bestPayService.pay(payRequest);
        log.info("卫星支付的返回结果为:{}",JsonUtil.object2string(response) );
        return response;
    }

    @Override
    public void weixin_notify(String notifyData) {
        //调用API进行验证
        PayResponse response = bestPayService.asyncNotify(notifyData);
        //查询订单
        OrderMaster orderMaster = findOneById(response.getOrderId());
        //比较金额  这里注意 orderMaster中是BigDecimal 而 response里面是double
        //还需要注意的点 new BigDecimal的时候只能用字符串类型，不然精度会丢失
        if(!BigDecimalUtil.equals2(orderMaster.getOrderAmount(),new BigDecimal(String.valueOf(response.getOrderAmount())))){
            //有异常的地方必须打印日志
            log.error("微信支付回调，订单金额不一致.微信:{},数据库:{}",response.getOrderAmount(),orderMaster.getOrderAmount());
            throw new CustomException(OrderEnums.AMOUNT_CHECK_ERROR.getMsg());
        }
        //判断支付状态是否为可支付（ 等待支付才能支付） 避免重复通知等其他因素
        if(!(orderMaster.getPayStatus()== PayEnums.WAIT.getCode())){
            log.error("微信回调,订单状态异常：{}",orderMaster.getPayStatus());
            throw new CustomException(PayEnums.STATUS_ERROR.getMsg());
        }
        //比较结束以后 完成订单支付状态的修改
        //实际项目中 这儿还需要把交易流水号与订单的对应关系存入数据库，比较简单，这儿不做了,大家需要知道
        orderMaster.setPayStatus(PayEnums.FINISH.getCode());
//        orderMaster.setOrderStatus(OrderEnums.FINSH.getCode());
        //注意:这儿只是支付状态OK  订单状态的修改 需要其他业务流程，发货，用户确认收货
        //修改支付状态
        orderMasterRepository.save(orderMaster);
        log.info("微信支付异步回调,订单支付状态修改完成");
    }

    @Override
    public RefundResponse refund(OrderMaster orderMaster) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderMaster.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        //执行退款
        RefundResponse refund = bestPayService.refund(refundRequest);
        log.info("微信退款请求相应:{}",refund );
        return refund;
    }
}
