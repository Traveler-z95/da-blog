package com.da.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 分页的mapper
 * @package com.da.blog.mapper
 * @name PagerMapper.java
 */
@Mapper
public interface PagerMapper {
    /**
     * 找到目标分类下的分页条件
     * @param categoryId
     * @return
     */
    int loadCategoryPager(Integer categoryId);

    /**
     * 通过tagId获取文章总数量
     * @param tagId
     * @return
     */
    int loadTagPager(Integer tagId);

    /**
     * 初始化时间归档的分页信息
     * @param createTime 创建时间
     * @return
     */
    int loadArchivePager(String createTime);
}
