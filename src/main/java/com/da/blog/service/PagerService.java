package com.da.blog.service;

import com.da.blog.vo.Pager;

/**
 *
 * @package com.da.blog.service
 * @name PagerService
 */
public interface PagerService {
    /**
     * 初始化分页信息
     * @return
     * @param pager 分页对象
     */
    void initPage(Pager pager);

    /**
     * 初始化某个category的分页信息
     * @param pager 分页对象
     * @param categoryId
     */
    void loadCategoryPager(Pager pager,Integer categoryId);

    /**
     * 初始化某个tag的分页信息
     * @param pager 分页对象
     * @param tagId
     */
    void loadTagPager(Pager pager, Integer tagId);

    /**
     * 初始化时间归档的分页信息
     * @param pager 分页对象
     * @param createTime 创建时间
     */
    void loadArchivePager(Pager pager, String createTime);
}
