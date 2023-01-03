package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author text.java
 * @since 2022-12-31
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    //统计一天的注册人数,生成统计数据
    void countRegister(String day);
    //图标显示，返回两部分数据，日期json数组，数量json数组
    Map<String, Object> getShowData(String type,String begin, String end);
}
