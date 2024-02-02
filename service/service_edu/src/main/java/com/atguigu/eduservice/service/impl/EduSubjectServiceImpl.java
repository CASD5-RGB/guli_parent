package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author text.java
 * @since 2022-12-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        //获取输入流
        try {
            //获取输入流
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class,
                    new SubjectExcelListener(subjectService))
                    .sheet()
                    .doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1查询出所有一级分类 parent_id=0

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        //获取一级分类
        wrapper.eq("parent_id", "0");
        //封装一级分类的数据
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);
        //2查询出所有二级分类 parent_id!=0
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        //获取二级分类
        wrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        //3封装最终的返回结果，返回多个OneSubject对象
        List<OneSubject> finnalList = new ArrayList<>();
        for (EduSubject eduSubject : oneSubjectList) {
            //new OneSubject设置值，add加入list
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //复制操作
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finnalList.add(oneSubject);
            //4封装二级分类
            //创建list集合封装每一个一级分类下的多个二级分类
            ArrayList<TwoSubject> twoFinnalList = new ArrayList<>();
            for (EduSubject eduSubject2 : twoSubjectList) {
                if (eduSubject.getId().equals(eduSubject2.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //如过一级分类的id==二级分类的parent_id,进行封装
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoFinnalList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinnalList);
        }

        return finnalList;
    }
}
