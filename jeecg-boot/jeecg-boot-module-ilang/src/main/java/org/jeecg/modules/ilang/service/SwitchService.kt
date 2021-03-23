package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.Switch
import org.jeecg.modules.ilang.mapper.SwitchMapper
import org.springframework.stereotype.Service

/**
 * @Description: 智能开关
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
class SwitchService: ServiceImpl<SwitchMapper, Switch>()
