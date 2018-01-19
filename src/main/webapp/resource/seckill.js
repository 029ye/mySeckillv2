var seckill = {
    //封装秒杀相关URL
    URL:{
        nowTimeUrl:'seckill/time/now',
        execSeckillUrl:'seckill/seckillId/exposer'
    },
    //验证手机号
    validatePhone:function (phone) {
        if(phone && !isNaN(phone) && phone.length == 11){
            return true;
        }else{
            return false;
        }
    },
    //秒杀相关逻辑
    detail:{
        init:function (params) {
            //验证登录，这cookie中是否有有效的phone
            var phone = $.cookie('phone');
            var seckillId = params.seckillId;
            var startTime = params.startTime;
            var endTime = params.endTime;
            //验证手机号
            if(!seckill.validatePhone(phone)){
                //绑定phone
                var phoneModal = $("#phoneModal");
                phoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyboard:false //关闭键盘事件
                });
                $("#phoneBtn").click(function () {
                    var inputPhone = $("#phone").val();
                    //验证输入的phone正确性
                    if (seckill.validatePhone(inputPhone)){
                        //把phone放入cookie，expires有效期，path有效范围
                        $.cookie('phone',inputPhone,{expires:1,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        //提示错误phone
                        $("#seckillMessage").hide().html('<label class="label label-danger">电话有误</label>').show(300);
                    }
                });
            }

        }
    }
}