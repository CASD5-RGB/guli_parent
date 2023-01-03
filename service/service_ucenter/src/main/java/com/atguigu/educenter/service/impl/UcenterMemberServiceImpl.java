package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author text.java
 * @since 2022-12-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //判断手机号和密码是否为空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        //判断查询对象是否为空
        if(ucenterMember==null){
            //没有这个手机号
            throw new GuliException(20001,"登录失败");

        }
        //判断密码
        //把输入的密码进行加密，再和数据库密码进行比较
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(ucenterMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code=registerVo.getCode();//验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册失败");
        }

        //判断验证码
        //获取redis验证码
        String rediscode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(rediscode)){
            throw new GuliException(20001,"注册失败");
        }

        //判断手机是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getMenberByOperid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    //查询某一天的注册人数
    @Override
    public Integer ucenterMemberService(String day) {
        return baseMapper.ucenterMemberService(day);
    }

}
