package com.da.blog.controller;

import com.da.blog.service.CategoryService;
import com.da.blog.vo.ArticleCustom;
import com.da.blog.vo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 文章归档的controller
 * FILE: com.da.blog.controller.ArchiveController.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 */
@Controller
public class ArchiveController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 文章归档列表
     *
     * @param createTime 创建时间
     * @param pager 分页对象
     * @param model 对象
     * @return
     */
    @RequestMapping("/archive/load/{createTime}")
    public String categoryList(@PathVariable String createTime, Pager pager, Model model){
        List<ArticleCustom> articleList = categoryService.loadArticleByArchive(createTime,pager);
        if (articleList != null && !articleList.isEmpty()) {
            model.addAttribute("articleList", articleList);
            model.addAttribute("pager", pager);
            model.addAttribute("createTime", createTime);
        }
        return "blog/part/archiveSummary";
    }
}