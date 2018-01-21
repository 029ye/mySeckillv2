var seckill = {
    //封装秒杀相关URL
    URL: {
        basepath: function () {
            return $("#basePath").val();
        },
        nowTimeUrl: function () {
            return seckill.URL.basepath() + 'seckill/time/now';
        },
        exposerUrl: function (seckillId) {
            return seckill.URL.basepath() + 'seckill/' + seckillId + '/exposer';
        },
        executionUrl: function (seckillId, md5) {
            return seckill.URL.basepath() + 'seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    //执行秒杀逻辑
    execution:function (seckillId,node) {
        //创建秒杀按钮
        node.hide().html('<button type="button" class="btn btn-primary btn-info" id="execSeckillBtn">秒杀</button>');
        //获取秒杀地址
        $.post(seckill.URL.exposerUrl(seckillId),{},function (result) {
            if (result && result['success']){
               var exposer = result['data'];
                if (exposer['exposed']){//秒杀开启

                    $('#execSeckillBtn').one('click',function () {
                        //禁用按钮
                        $(this).addClass('disabled');
                        var md5 = exposer['md5'];
                        var killUrl = seckill.URL.executionUrl(seckillId,md5);
                        $.post(killUrl,{},function (result) {
                            if (result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }else {
                                node.html('<span class="label label-danger">'+result['error']+'</span>');
                            }
                        });
                    });
                    //显示秒杀按钮
                    node.show();
                }else {//秒杀未开启
                    var now  = exposer['now'];
                    var start  = exposer['start'];
                    var end  = exposer['end'];
                    seckill.countdown(seckillId,start,end,now);
                }
            }else{
                console.log(result['error']);
            }
        });

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
                seckill.execution(seckillId,seckillBox);
            });
        } else {
            //秒杀开始
            seckill.execution(seckillId,seckillBox);
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