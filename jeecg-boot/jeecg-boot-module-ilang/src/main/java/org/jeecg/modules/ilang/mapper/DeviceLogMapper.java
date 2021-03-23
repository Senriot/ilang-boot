package org.jeecg.modules.ilang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ilang.entity.DeviceLog;

import java.util.List;

/**
 * @Description: 设备日志
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
public interface DeviceLogMapper extends BaseMapper<DeviceLog> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<DeviceLog> selectByMainId(@Param("mainId") String mainId);
}
