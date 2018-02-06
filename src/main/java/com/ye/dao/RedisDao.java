package com.ye.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.ye.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(RedisDao.class);

    private final JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String host, Integer port) {
        jedisPool = new JedisPool(host, port);
    }

    public Seckill getSeckill(Long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //反序列化
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            } finally {
                if (jedis != null) jedis.close();
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                if (seckill != null) {
                    String key = "seckill:" + seckill.getSeckillId();
                    //序列化
                    byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                            LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                    Integer time = 60 * 60;//后端缓存时间秒
                    String result = jedis.setex(key.getBytes(), time, bytes);
                    return result;
                }
            } finally {
                if (jedis != null) jedis.close();
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return null;
    }
}
