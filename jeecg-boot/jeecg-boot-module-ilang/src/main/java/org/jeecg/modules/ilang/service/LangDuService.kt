package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.LangDu
import org.jeecg.modules.ilang.mapper.LangDuMapper
import org.springframework.stereotype.Service

/**
 * @Description: 朗读内容
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@Service
class LangDuService: ServiceImpl<LangDuMapper, LangDu>()
