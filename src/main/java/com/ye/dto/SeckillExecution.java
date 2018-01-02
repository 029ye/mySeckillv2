package com.ye.dto;

import com.ye.entity.SuccessKill;
import com.ye.enums.SeckillStateEnum;

/**
 * 秒杀执行后结果
 */
public class SeckillExecution {
    private long seckillId;
    private int state;

    private String stateInfo;

    private SuccessKill successKill;

    public SeckillExecution(long seckillId, SeckillStateEnum state, SuccessKill successKill) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.successKill = successKill;
    }

    public SeckillExecution(long seckillId, SeckillStateEnum state) {
        this.seckillId = seckillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
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
