package com.xmcc.wxsell.dto;

import com.xmcc.wxsell.entity.OrderDetail;
import com.xmcc.wxsell.entity.OrderMaster;
import com.xmcc.wxsell.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailMasterDto extends OrderMaster implements Serializable {
    private List<OrderDetail> orderDetailList ;
    public static OrderDetailMasterDto build(OrderMaster orderMaster){
        OrderDetailMasterDto orderDetailMasterDto = new OrderDetailMasterDto();
        BeanUtils.copyProperties(orderMaster,orderDetailMasterDto);
        return orderDetailMasterDto;
    }
}
