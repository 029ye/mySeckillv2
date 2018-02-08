package com.ye.service.impl;

import com.ye.dao.RedisDao;
import com.ye.dao.SeckillDao;
import com.ye.dao.SuccessKillDao;
import com.ye.dto.Exposer;
import com.ye.dto.SeckillExecution;
import com.ye.entity.Seckill;
import com.ye.entity.SuccessKill;
import com.ye.enums.SeckillStateEnum;
import com.ye.exception.RepeatKillException;
import com.ye.exception.SeckillCloseException;
import com.ye.exception.SeckillException;
import com.ye.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SuccessKillDao successKillDao;
    private final static String sbase = "hjs&MKkfnskijk^&$%54j54^&$%9034630^U&lj&^&IJKLjl)&^^&$%#$$hjksdhfjk";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    public Seckill getById(Long id) {
        return seckillDao.queryById(id);
    }

    public Exposer exportSeckillUrl(Long seckillId) {
        //优化点：缓存优化：超时的基础上维护一致性
        //1、访问缓存
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            //2、访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            }else {
                //3、放入缓存
                String result = redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(Long seckillId) {
        String base = seckillId + "/" + sbase;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long phone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
            if (md5 == null || !md5.equals(getMd5(seckillId))) {
                throw new SeckillException("秒杀数据重复");
            }
            Date nowTime = new Date();

            int insertNumber = successKillDao.insertSuccessKill(seckillId, phone);
            if (insertNumber <= 0) {
                throw new RepeatKillException("重复秒杀");
            } else {
                int reduceNumber = seckillDao.reduceNumber(seckillId, nowTime);
                if (reduceNumber <= 0) {
                    throw new SeckillCloseException("秒杀已结束");
                } else {
                    SuccessKill successKill = successKillDao.queryByIdWithSeckill(seckillId, phone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKill);
                }
            }


        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SeckillException("内部错误：" + e.getMessage());
        }
    }
}
