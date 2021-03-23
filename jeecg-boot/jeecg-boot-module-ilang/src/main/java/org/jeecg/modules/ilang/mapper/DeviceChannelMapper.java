package org.jeecg.modules.ilang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ilang.entity.DeviceChannel;

import java.util.List;

/**
 * @Description: 设备通道
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
public interface DeviceChannelMapper extends BaseMapper<DeviceChannel> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<DeviceChannel> selectByMainId(@Param("mainId") String mainId);
}
