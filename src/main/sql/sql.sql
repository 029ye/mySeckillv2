-- 创建数据库
CREATE DATABASE seckill;

-- 使用seckill数据库
USE seckill;

-- 创建秒杀库存表
CREATE TABLE seckill(
  seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  name VARCHAR(120) NOT NULL COMMENT '商品名称',
  number INT NOT NULL COMMENT '商品库存',
  start_time TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '商品库存创建时间',
PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
) ENGINE = InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET = utf8 COMMENT = '秒杀库存表';

-- 初始化数据
insert into seckill(name,number,start_time,end_time)
    VALUES
      ('1000元iPad',100,'2017-11-01','2017-12-01'),
      ('1500元小米5',120,'2017-11-01','2017-12-01'),
      ('1000元荣耀6',170,'2017-11-01','2017-12-01'),
      ('1999元魅蓝note',190,'2017-11-01','2017-12-01');

-- 秒杀成功明细表
CREATE TABLE success_kill(
  seckill_id BIGINT NOT NULL COMMENT '商品id',
  user_phone INT NOT NULL COMMENT '用户手机号',
  status TINYINT NOT NULL DEFAULT -1 COMMENT '秒杀状态 -1：无效，0：成功',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '记录创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '秒杀成功明细表';