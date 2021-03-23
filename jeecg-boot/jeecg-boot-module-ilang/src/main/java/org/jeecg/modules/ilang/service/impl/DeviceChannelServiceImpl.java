package org.jeecg.modules.ilang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.ilang.entity.DeviceChannel;
import org.jeecg.modules.ilang.mapper.DeviceChannelMapper;
import org.jeecg.modules.ilang.service.IDeviceChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 设备通道
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
public class DeviceChannelServiceImpl extends ServiceImpl<DeviceChannelMapper, DeviceChannel> implements IDeviceChannelService {

	@Autowired
	private DeviceChannelMapper deviceChannelMapper;

	@Override
	public List<DeviceChannel> selectByMainId(String mainId) {
		return deviceChannelMapper.selectByMainId(mainId);
	}
}
