package com.ye.exception;

import com.ye.dto.SeckillExecution;
import com.ye.entity.SuccessKill;

public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
