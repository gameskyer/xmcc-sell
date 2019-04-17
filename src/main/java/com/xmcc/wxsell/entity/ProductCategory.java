package com.xmcc.wxsell.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity//表示该类是实体类
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null,就不会加入到update语句中
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
