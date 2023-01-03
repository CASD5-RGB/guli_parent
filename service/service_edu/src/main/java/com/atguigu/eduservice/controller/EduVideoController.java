package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author text.java
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //刪除小节
    //删除小节同时把小节中的视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        System.out.println(id);
        //根据小节id查询出视频id，进行删除
        EduVideo eduVideobyId = eduVideoService.getById(id);
        String videoSourceId = eduVideobyId.getVideoSourceId();
        //判断是否有视频,有就删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用vod删除视频
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器....");
            }
        }
        //删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }

    //根据ID查询课时
    @ApiParam(name = "id", value = "课时ID", required = true)
    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video",video);
    }
    //修改小节
    @ApiOperation(value = "更新课时")
    @PostMapping("updateVideo")
    public R updateCourseInfo(@RequestBody EduVideo eduVideo){
        boolean update = eduVideoService.updateById(eduVideo);
        if(update){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

}

