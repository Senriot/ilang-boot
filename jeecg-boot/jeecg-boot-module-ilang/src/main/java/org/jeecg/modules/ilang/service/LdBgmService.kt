package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.LdBgm
import org.jeecg.modules.ilang.mapper.LdBgmMapper
import org.springframework.stereotype.Service

/**
 * @Description: 朗读背景音乐
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@Service
class LdBgmService: ServiceImpl<LdBgmMapper, LdBgm>()
