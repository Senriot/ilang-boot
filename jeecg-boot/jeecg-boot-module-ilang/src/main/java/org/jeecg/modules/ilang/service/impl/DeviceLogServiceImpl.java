package org.jeecg.modules.ilang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.ilang.entity.DeviceLog;
import org.jeecg.modules.ilang.mapper.DeviceLogMapper;
import org.jeecg.modules.ilang.service.IDeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 设备日志
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
public class DeviceLogServiceImpl extends ServiceImpl<DeviceLogMapper, DeviceLog> implements IDeviceLogService {

	@Autowired
	private DeviceLogMapper deviceLogMapper;

	@Override
	public List<DeviceLog> selectByMainId(String mainId) {
		return deviceLogMapper.selectByMainId(mainId);
	}
}
