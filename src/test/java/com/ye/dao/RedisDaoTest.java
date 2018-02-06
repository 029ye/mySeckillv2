package com.ye.dao;

import com.ye.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class RedisDaoTest {
    static final Logger log = LoggerFactory.getLogger(RedisDaoTest.class);

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void getSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(1000L);
        if (seckill == null){
            seckill = seckillDao.queryById(1000L);
            log.info(redisDao.putSeckill(seckill));
            log.info(redisDao.getSeckill(1000L).toString());
        }
    }

}