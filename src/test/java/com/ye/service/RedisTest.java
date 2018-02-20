package com.ye.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

public class RedisTest {

    @Test
    public void jedisTest(){
        Jedis jedis = new Jedis("192.168.2.105",6379);
        System.out.println(jedis.get("hi"));
        jedis.close();
    }
}
