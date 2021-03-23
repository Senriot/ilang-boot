package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.DeviceProduce
import org.jeecg.modules.ilang.mapper.DeviceProduceMapper
import org.springframework.stereotype.Service

/**
 * @Description: 设备类别
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
class DeviceProduceService: ServiceImpl<DeviceProduceMapper, DeviceProduce>()
