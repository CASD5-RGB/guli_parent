package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    //根据课程id删除小节
    @Override
    public void removeByCourseId(String courseId) {
        //根据课程id查询所有视频id
        QueryWrapper<EduVideo> Wrapper = new QueryWrapper<>();
        Wrapper.eq("course_id",courseId);
        Wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(Wrapper);
        //List<EduVideo>变成List<string>
        List<String> videoIds=new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo eduVideo = videoList.get(i);
            if(!StringUtils.isEmpty(eduVideo)){
                String videoSourceId = eduVideo.getVideoSourceId();
                videoIds.add(videoSourceId);
            }
        }
        if(videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("course_id",courseId);
        baseMapper.delete(QueryWrapper);
    }
}

