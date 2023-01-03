package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author text.java
 * @since 2022-12-31
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计一天的注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        statisticsDailyService.countRegister(day);
        return R.ok();
    }

    //图标显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("getShowData/{type}/{begin}/{end}")
    public R getShowData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

