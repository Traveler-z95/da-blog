package com.da.blog.mapper;

import com.da.blog.vo.LogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @package com.da.blog.mapper
 */
@Mapper
public interface LogMapper {
    /**
     * 保存日志信息
     * @param log
     */
    void save(LogInfo log);
}
