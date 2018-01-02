package com.ye.service.impl;

import com.ye.dao.SeckillDao;
import com.ye.dao.SuccessKillDao;
import com.ye.dto.Exposer;
import com.ye.dto.SeckillExecution;
import com.ye.entity.Seckill;
import com.ye.exception.RepeatKillException;
import com.ye.exception.SeckillCloseException;
import com.ye.exception.SeckillException;
import com.ye.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKillDao successKillDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,100);
    }

    public Seckill getById(long id) {
        return seckillDao.queryById(id);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        return null;
    }

    public SeckillExecution executeSeckill(long seckillId, long phone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        return null;
    }
}
