package com.atguigu.msmservice.service;

import java.util.HashMap;

public interface MsmService {
    //发送短信的方法
    boolean send(HashMap<Object, Object> param, String phone);
}
