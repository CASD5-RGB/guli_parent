package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //发送短信的方法
    @GetMapping("send/{mobile}")
    public R sendMsm(@PathVariable String mobile){
//        先从redis获取验证码，如果获取到直接返回
        String code= redisTemplate.opsForValue().get(mobile);
        if(!StringUtils.isEmpty(code)){
            System.out.println("redis中的code"+code);
            return R.ok();
        }
        //如果redis获取不到，进行阿里云发送

        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        HashMap<Object, Object> param = new HashMap<>();
        param.put("code",code);
        System.out.println("=====验证码===="+code);
        //调用service发送短信的方法
        boolean isSend=msmService.send(param,mobile);
        System.out.println(mobile);
        if(isSend){
            redisTemplate.opsForValue().set(mobile,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        else{
            return R.error().message("短信发送失败");
        }

    }
}


