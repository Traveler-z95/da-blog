package com.da.blog.controller.admin;

import com.da.blog.constant.ProjectConstant;
import com.da.blog.service.TagService;
import com.da.blog.util.ResultInfo;
import com.da.blog.util.ResultInfoFactory;
import com.da.blog.vo.Pager;
import com.da.blog.vo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * FILE: com.da.blog.controller.admin.TagController.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 */
@Controller
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;


    /**
     * 初始化分页信息
     * @param pager 分页对象
     * @param model 对象
     * @return
     */
    @RequestMapping("/initPage")
    @ResponseBody
    public Pager initPage(Pager pager,Model model){
        tagService.initPage(pager);
        return pager;
    }

    /**
     * 编辑一个标签
     * @param id
     * @param model 对象
     * @return
     */
    @RequestMapping("/editJump/{id}")
    public String editPage(@PathVariable Integer id, Model model){
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag",tag);
        return "admin/label/labelEdit";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/addJump")
    public String addPage(){
        return "admin/label/labelAdd";
    }

    /**
     * 分页加载标签
     * @param pager 分页对象
     * @param tagName
     * @param model 对象
     * @return
     */
    @RequestMapping("/load")
    public String loadTagList(Pager pager,String tagName,Model model){
        List<Tag> tagList = tagService.loadTagList(pager,tagName);
        System.out.println(tagList);
        model.addAttribute("tagList",tagList);
        return "admin/label/labelTable";
    }

    /**
     * 保存标签
     * @param tag
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveTag(Tag tag){
        try {
//            tag.setAliasName(URLDecoder.decode(tag.getAliasName(),"UTF-8"));
//            tag.setTagName(URLDecoder.decode(tag.getTagName(),"UTF-8"));
            tag.setAliasName(URLDecoder.decode(tag.getAliasName(), ProjectConstant.CHARSET_UTF8));
            tag.setTagName(URLDecoder.decode(tag.getTagName(),ProjectConstant.CHARSET_UTF8));
            if (tagService.checkExist(tag)){
                return ResultInfoFactory.getErrorResultInfo("标签名或别名已经存在");
            }
            tagService.saveTag(tag);
        } catch (UnsupportedEncodingException e) {
            ResultInfoFactory.getErrorResultInfo("添加失败,字符串格式化错误");
        }
        return ResultInfoFactory.getSuccessResultInfo();
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateTag(Tag tag){
        if (tagService.checkExist(tag)){
            return ResultInfoFactory.getErrorResultInfo("已存在相同的标签名或者别名");
        }
        tagService.updateTag(tag);
        return ResultInfoFactory.getSuccessResultInfo();
    }

    /**
     * 删除一个标签
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ResultInfo deleteTag(@PathVariable int id){
        tagService.deleteTagById(id);
        return ResultInfoFactory.getSuccessResultInfo();
    }
}
