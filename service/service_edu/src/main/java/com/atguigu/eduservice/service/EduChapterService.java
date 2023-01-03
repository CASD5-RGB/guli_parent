package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
public interface EduChapterService extends IService<EduChapter> {
    //添加课程基本信息的方法
    List<ChapterVo> getChapterVoByCourseId(String courseId);
    //删除课程章节
    boolean deleteChapterById(String chapterId);
    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
