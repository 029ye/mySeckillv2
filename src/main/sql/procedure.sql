CREATE PROCEDURE p_execute_seckill(IN seckillId BIGINT, IN phone INT, IN nowTime TIMESTAMP, OUT r_result INT)
  BEGIN
    DECLARE insert_count INT;

    START TRANSACTION;

    INSERT IGNORE INTO success_kill (seckill_id, user_phone, status, create_time)
      VALUE (seckillId, phone, 1, now());

    SELECT row_count()
    INTO insert_count;

    IF (insert_count = 0)
    THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < -1)
      THEN
        ROLLBACK;
        SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = seckillId AND start_time < nowTime AND end_time > nowTime AND number > 0;

      SELECT row_count()
      INTO insert_count;

      IF (insert_count = 0)
      THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF (insert_count < -1)
        THEN
          ROLLBACK;
          SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;