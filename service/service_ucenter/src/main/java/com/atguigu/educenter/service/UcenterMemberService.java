package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author text.java
 * @since 2022-12-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //登录
    String login(UcenterMember member);
    //注册
    void register(RegisterVo registerVo);
    //判断该微信信息是否注册过
    UcenterMember getMenberByOperid(String openid);
    //查询某一天的注册人数
    Integer ucenterMemberService(String day);
}
