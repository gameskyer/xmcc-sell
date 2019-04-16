package com.xmcc.wxsell.repository;

import com.google.common.collect.Lists;
import com.xmcc.wxsell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//泛型1：实体类类型 ； 泛型2：主键类型
public interface UserRespository extends JpaRepository<User,Integer> {

    //关键字定义
    List<User> findAllByIdIn(List<Integer> ids);

    //自定义SQL
    //jps底层实现是hibernate hibernate:hql(基于实体类进行查询) jpa:jpql(基于实体类)
    @Query(value = "select * from user where id = ?1",nativeQuery = true)
    User queryUserByUserId(Integer id);

    @Query(value = "select u from User u where id = ?1")
    User getUserByUserId(Integer id);
}
