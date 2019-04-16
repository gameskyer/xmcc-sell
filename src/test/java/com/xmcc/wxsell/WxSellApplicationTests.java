package com.xmcc.wxsell;

import com.google.common.collect.Lists;
import com.xmcc.wxsell.entity.User;
import com.xmcc.wxsell.repository.UserRespository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxSellApplicationTests {
@Autowired
private UserRespository userRespository;
    @Test
    public void contextLoads() {
//        List<User> all = userRespository.findAll();
//        all.stream().forEach(System.out::println);


//        User user = new User(null, "王三", "2018-02-27 17:47:08", "男", "深圳");
//        userRespository.save(user);
//        System.out.println(user.toString());


//        List<Integer> ids = new ArrayList<>();
//        ids.add(63);
//        ids.add(64);


//        ArrayList<Integer> ids = Lists.newArrayList(64, 63, 62, 61);
//        List<User> all = userRespository.findAllByIdIn(ids);
//        all.stream().forEach(System.out::println);


//        User user = userRespository.queryUserByUserId(64);
//        System.out.println(user.toString());

        User user = userRespository.getUserByUserId(64);
        System.out.println(user.toString());

    }

}
