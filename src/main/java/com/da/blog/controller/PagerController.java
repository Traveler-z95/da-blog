package com.da.blog.controller;

import com.da.blog.service.PagerService;
import com.da.blog.service.TagService;
import com.da.blog.vo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面管理的controller
 * package com.da.blog.controller.admin
 * @name PagerController
 */
@RestController
public class PagerController {

    @Autowired
    private PagerService pagerService;  //分页的service
    @Autowired
    private TagService tagService;  //标签的service

    /**
     * 初始化文章分页信息
     * @return
     */
    @RequestMapping("/pager/articles/load")
    public Pager loadArticlePager(Pager pager){
        pagerService.initPage(pager);
        return pager;
    }

    /**
     * 初始化当前分类id的文章分页信息
     * @param pager 分页对象 分页对象
     * @param categoryId 分类id
     * @return
     */
    @RequestMapping("/pager/categories/{categoryId}")
    public Pager loadCategoryPager(Pager pager,@PathVariable Integer categoryId){
        pagerService.loadCategoryPager(pager,categoryId);
        return pager;
    }

    /**
     *初始化当前标签的文章分页信息
     * @param pager 分页对象 分页对象
     * @param tagId 标签
     * @return
     */
    @RequestMapping("/pager/tags/{tagId}")
    public Pager initPage(Pager pager,@PathVariable Integer tagId){
        tagService.articleTagPage(pager,tagId);
        return pager;
    }

    @GetMapping("/pager/archive/{createTime}")
    public Pager loadArchivePager(Pager pager,@PathVariable String createTime){
        pagerService.loadArchivePager(pager,createTime);
        return pager;
    }

}
