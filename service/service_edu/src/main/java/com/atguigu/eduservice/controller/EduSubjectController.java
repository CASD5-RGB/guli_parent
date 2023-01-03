package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author text.java
 * @since 2022-12-19
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类返回结构(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

