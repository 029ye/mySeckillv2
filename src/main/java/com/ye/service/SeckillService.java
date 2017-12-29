package com.ye.service;

import com.ye.dto.Exposer;
import com.ye.dto.SeckillExecution;
import com.ye.entity.Seckill;
import com.ye.exception.RepeatKillException;
import com.ye.exception.SeckillCloseException;
import com.ye.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在使用者角度设计接口
 * 三个方面：方法定义粒度，参数（越简练越直接），返回类型（return 类型/异常）
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param id
     * @return
     */
    Seckill getById(long id);

    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param phone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long phone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
