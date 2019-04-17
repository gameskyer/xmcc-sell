package com.xmcc.wxsell.dao.ipml;

import com.xmcc.wxsell.dao.BatchDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public class BatchDaoipml<T> implements BatchDao<T> {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void batchInsert(List<T> list) {
        int size = list.size();
        //循环放入缓冲区
        for (int i = 0; i <size ; i++) {
            em.persist(list.get(i));
            //每一百条数据写入数据库，如果不足100条，就直接将全部数据写入数据库
            if(i %100 == 0 || i == size-1){
                em.flush();
                em.clear();
            }
        }
    }
}
