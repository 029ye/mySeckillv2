package com.ye.dto;

import com.ye.entity.SuccessKill;

/**
 * 秒杀执行后结果
 */
public class SeckillExecution {
    private long seckillId;
    private int state;

    private String stateInfo;

    private SuccessKill successKill;

    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKill successKill) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKill = successKill;
    }

    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKill getSuccessKill() {
        return successKill;
    }

    public void setSuccessKill(SuccessKill successKill) {
        this.successKill = successKill;
    }
}
