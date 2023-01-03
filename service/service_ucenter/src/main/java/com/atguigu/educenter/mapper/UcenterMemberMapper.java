package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author text.java
 * @since 2022-12-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询某一天的注册人数
    Integer ucenterMemberService(String day);
}
