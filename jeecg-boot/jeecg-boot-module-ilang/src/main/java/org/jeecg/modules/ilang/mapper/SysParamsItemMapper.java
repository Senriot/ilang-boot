package org.jeecg.modules.ilang.mapper;

import java.util.List;
import org.jeecg.modules.ilang.entity.SysParamsItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 参数详情
 * @Author: jeecg-boot
 * @Date:   2021-01-02
 * @Version: V1.0
 */
public interface SysParamsItemMapper extends BaseMapper<SysParamsItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<SysParamsItem> selectByMainId(@Param("mainId") String mainId);
}
