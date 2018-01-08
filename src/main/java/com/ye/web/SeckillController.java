package com.ye.web;

import com.ye.dto.Exposer;
import com.ye.dto.SeckillExecution;
import com.ye.dto.SeckillResult;
import com.ye.entity.Seckill;
import com.ye.enums.SeckillStateEnum;
import com.ye.exception.RepeatKillException;
import com.ye.exception.SeckillCloseException;
import com.ye.exception.SeckillException;
import com.ye.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("seckill") //url:/模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "phone", required = false) Long phone,
                                                   @PathVariable("md5") String md5, Model model) {
        //此处验证，也可以用spirngmvc valid
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false, execution);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
