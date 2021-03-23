package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.ilang.entity.Device;
import org.jeecg.modules.ilang.entity.DeviceChannel;
import org.jeecg.modules.ilang.entity.DeviceLog;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
public interface IDeviceService extends IService<Device> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(Device device,List<DeviceChannel> deviceChannelList,List<DeviceLog> deviceLogList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(Device device,List<DeviceChannel> deviceChannelList,List<DeviceLog> deviceLogList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
