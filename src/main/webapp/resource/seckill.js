var seckill = {
    //封装秒杀相关URL
    URL: {
        basepath: function () {
            return $("#basePath").val();
        },
        nowTimeUrl: function () {
            return seckill.URL.basepath() + 'seckill/time/now';
        },
        execSeckillUrl: function (seckillId) {
            return seckill.URL.basepath() + 'seckill/' + seckillId + '/exposer';
        }
    },
    //执行倒计时
    countdown: function (seckillId, startTime, endTime, nowTime) {
        var seckillBox = $("#seckill-box");
        if (nowTime > endTime) {
            seckillBox.html("秒杀已结束！");
        } else if (nowTime < startTime) {
            //秒杀未开始，倒计时逻辑
            var killtime = new Date(startTime + 1000);
            seckillBox.countdown(killtime, function (event) {
                // 时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                // 时间完成后回调事件
            }).on('finish.countdown', function () {
                // 获取秒杀地址，控制显示逻辑，执行秒杀

            });
        } else {
            //秒杀开始
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && !isNaN(phone) && phone.length == 11) {
            return true;
        } else {
            return false;
        }
    },
    //秒杀相关逻辑
    detail: {
        init: function (params) {
            //验证登录，这cookie中是否有有效的phone
            var phone = $.cookie('phone');
            var seckillId = params.seckillId;
            var startTime = params.startTime;
            var endTime = params.endTime;
            //验证手机号
            if (!seckill.validatePhone(phone)) {
                //绑定phone
                var phoneModal = $("#phoneModal");
                phoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false //关闭键盘事件
                });
                $("#phoneBtn").click(function () {
                    var inputPhone = $("#phone").val();
                    //验证输入的phone正确性
                    if (seckill.validatePhone(inputPhone)) {
                        //把phone放入cookie，expires有效期，path有效范围
                        $.cookie('phone', inputPhone, {expires: 1, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        //提示错误phone
                        $("#seckillMessage").hide().html('<label class="label label-danger">电话有误</label>').show(300);
                    }
                });
            }

            //计时交互
            $.get(seckill.URL.nowTimeUrl(), function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    seckill.countdown(seckillId, startTime, endTime, nowTime);
                } else {
                    console.log(result['error']);
                }
            });

        }
    }
}