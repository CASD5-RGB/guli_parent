package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.CourseQueryVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService chapterService;
    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //获取保存后的id，与课程描述建立关系
        String CourseId = eduCourse.getId();
        //向课程简介添加课程简介
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        description.setId(CourseId);
        courseDescriptionService.save(description);
        return CourseId;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1查询课程表类容
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //封装到CourseInfoVo中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2查询描述表
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new GuliException(20001,"修改课程信息失败");

        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);

        courseDescriptionService.updateById(courseDescription);

    }
    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {

        //根据课程id删除小节和视频
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);
        //根据课程id删除课程描述
        courseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage,
                                              CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断条件是否为空
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){//一级分类
            queryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())){//二级分类
            queryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {//销量排序
            queryWrapper.orderByDesc("buy_count");
        }
        // 先按时间排序，时间相同的，按照价格进行排序
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {//时间排序
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {//价格排序
            queryWrapper.orderByDesc("price");
        }

        //封装到page里面
        IPage<EduCourse> eduCourseIPage = baseMapper.selectPage(queryVoPage, queryWrapper);

        //eduCourseIPage中的内容与queryVoPage中的数据一样
        long total = queryVoPage.getTotal();
        List<EduCourse> records = queryVoPage.getRecords();
        long current = queryVoPage.getCurrent();
        long size = queryVoPage.getSize();
        boolean hasNext = queryVoPage.hasNext();
        boolean hasPrevious = queryVoPage.hasPrevious();
        long pages = queryVoPage.getPages();

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    //根据课程id，编写sql语句查询课程信息
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
