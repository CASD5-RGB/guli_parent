package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.CourseQueryVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程基本信息的方法
    String  saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);
    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
    //删除课程
    void removeCourse(String courseId);

    Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage, CourseQueryVo courseQueryVo);

    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
