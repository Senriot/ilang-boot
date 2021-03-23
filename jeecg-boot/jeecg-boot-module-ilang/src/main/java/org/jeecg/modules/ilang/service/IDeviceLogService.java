package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.ilang.entity.DeviceLog;

import java.util.List;

/**
 * @Description: 设备日志
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
public interface IDeviceLogService extends IService<DeviceLog> {

	public List<DeviceLog> selectByMainId(String mainId);
}
