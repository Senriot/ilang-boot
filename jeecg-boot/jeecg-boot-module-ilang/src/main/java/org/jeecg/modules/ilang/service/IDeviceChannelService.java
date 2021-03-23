package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.ilang.entity.DeviceChannel;

import java.util.List;

/**
 * @Description: 设备通道
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
public interface IDeviceChannelService extends IService<DeviceChannel> {

	public List<DeviceChannel> selectByMainId(String mainId);
}
