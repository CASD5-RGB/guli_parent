package com.atguigu.msmservice.service.impl;

import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;


@Service
public class MsmServiceImpl implements MsmService {
    @Autowired
    public JavaMailSender javaMailSender;
    //发送短信的方法
    @Override
    public boolean send(HashMap<Object, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tP8drFdf3HHNk6sd6oE", "le7RL6GJB8FGpSjDH74GSy9YeryVlC");//自己账号的AccessKey信息
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
//        request.setSysVersion("2017-05-25");//API的版本号
//        request.setSysAction("SendSms");//API的名称
//        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
//        request.putQueryParameter("SignName", "我的黑马在线网站");//短信签名名称
//        request.putQueryParameter("TemplateCode", "SMS_255305171");//短信模板ID
//        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//短信模板变量对应的实际值
//
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            boolean success = response.getHttpResponse().isSuccess();
//            return success;
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

        String to =phone+"@qq.com";//收件人
        System.out.println("向"+to+"发送邮件");
        String from = "3150847886@qq.com";//发送人邮箱必须开启..？？？.服务
        String subject = "验证码邮件";
        String context = "阿里云短信服务没钱用====>验证码"+param;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setText(context);
        message.setTo(to);
        message.setSubject(subject);
        javaMailSender.send(message);
        System.out.println("成功发送邮件---->");
        return true;
    }
}
