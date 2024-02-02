package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;
    //分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,
                                 @PathVariable long limit){
        //1、封装分页条件参数对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //2、调用service层的方法
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    //讲师查询的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //1、查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        //1、查询讲师课程的基本信息
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        //讲师可能一个课程也可能多个课程
        List<EduCourse> list = eduCourseService.list(queryWrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",list);
    }
}
