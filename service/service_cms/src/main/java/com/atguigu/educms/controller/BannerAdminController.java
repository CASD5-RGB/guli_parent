package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author text.java
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;
    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    @ApiOperation(value = "获取Banner分页列表")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> crmBannerPage = new Page<CrmBanner>(page, limit);
        crmBannerService.page(crmBannerPage,null);
        return R.ok().data("items",crmBannerPage.getRecords()).data("total",crmBannerPage.getTotal());

    }

    //添加banner
    @ApiOperation(value = "新增Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if(save){
            return R.ok();
        }
        else{
            return R.error();
        }
    }
    //通过id获取信息
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("item",crmBanner);
    }
    //修改banner
    @PutMapping("update")
    @ApiOperation(value = "修改Banner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean b = crmBannerService.updateById(crmBanner);
        if(b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除Banner")
    public R remove(@PathVariable String id){
        boolean b = crmBannerService.removeById(id);
        if(b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

}

