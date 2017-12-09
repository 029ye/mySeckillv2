package com.ye.dao;

import com.ye.entity.SuccessKill;

import java.util.List;

public interface SuccessKillDao {

    /**
     * 插入秒杀成功明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKill(long seckillId,Long userPhone);

    /**
     * 通过id 和 电话 查询秒杀成功明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKill queryByIdWithSeckill(long seckillId,Long userPhone);

    /**
     * 查询秒杀成功明细列表
     * @param seckillId
     * @return
     */
    List<SuccessKill> queryListByIdWithSeckill(long seckillId);
}
