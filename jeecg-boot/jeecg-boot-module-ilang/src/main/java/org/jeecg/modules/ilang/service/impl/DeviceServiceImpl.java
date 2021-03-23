package org.jeecg.modules.ilang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.ilang.entity.Device;
import org.jeecg.modules.ilang.entity.DeviceChannel;
import org.jeecg.modules.ilang.entity.DeviceLog;
import org.jeecg.modules.ilang.mapper.DeviceChannelMapper;
import org.jeecg.modules.ilang.mapper.DeviceLogMapper;
import org.jeecg.modules.ilang.mapper.DeviceMapper;
import org.jeecg.modules.ilang.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2020-12-24
 * @Version: V1.0
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService
{

    @Autowired
    private DeviceMapper        deviceMapper;
    @Autowired
    private DeviceChannelMapper deviceChannelMapper;
    @Autowired
    private DeviceLogMapper     deviceLogMapper;



    @Override
    @Transactional
    public void saveMain(Device device, List<DeviceChannel> deviceChannelList, List<DeviceLog> deviceLogList)
    {
        deviceMapper.insert(device);
        if (deviceChannelList != null && deviceChannelList.size() > 0)
        {
            for (DeviceChannel entity : deviceChannelList)
            {
                //外键设置
                entity.setDeviceId(device.getId());
                deviceChannelMapper.insert(entity);
            }
        }
        if (deviceLogList != null && deviceLogList.size() > 0)
        {
            for (DeviceLog entity : deviceLogList)
            {
                //外键设置
                entity.setDeviceId(device.getId());
                deviceLogMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(Device device, List<DeviceChannel> deviceChannelList, List<DeviceLog> deviceLogList)
    {
        deviceMapper.updateById(device);

        //1.先删除子表数据
        deviceChannelMapper.deleteByMainId(device.getId());
        deviceLogMapper.deleteByMainId(device.getId());

        //2.子表数据重新插入
        if (deviceChannelList != null && deviceChannelList.size() > 0)
        {
            for (DeviceChannel entity : deviceChannelList)
            {
                //外键设置
                entity.setDeviceId(device.getId());
                deviceChannelMapper.insert(entity);
            }
        }
        if (deviceLogList != null && deviceLogList.size() > 0)
        {
            for (DeviceLog entity : deviceLogList)
            {
                //外键设置
                entity.setDeviceId(device.getId());
                deviceLogMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id)
    {
        deviceChannelMapper.deleteByMainId(id);
        deviceLogMapper.deleteByMainId(id);
        deviceMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList)
    {
        for (Serializable id : idList)
        {
            deviceChannelMapper.deleteByMainId(id.toString());
            deviceLogMapper.deleteByMainId(id.toString());
            deviceMapper.deleteById(id);
        }
    }

}
