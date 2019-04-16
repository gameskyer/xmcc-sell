package com.xmcc.wxsell.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data //相当于get set ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")//制定是哪一张表
@Entity
public class User implements Serializable {
    @Id //指定主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;
    private String username;
    private String birthday;
    private String sex;
    private String address;
}
