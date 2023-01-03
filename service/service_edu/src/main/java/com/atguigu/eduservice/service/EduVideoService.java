package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
public interface EduVideoService extends IService<EduVideo> {
//    根据课程id删除小节
    void removeByCourseId(String courseId);
}
