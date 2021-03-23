package org.jeecg.modules.ilang.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.mapper.SysParamsItemMapper
import org.jeecg.modules.ilang.entity.SysParamsItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @Description: 参数详情
 * @Author: jeecg-boot
 * @Date: 2021-01-02
 * @Version: V1.0
 */
@Service
class SysParamsItemService(private val sysParamsItemMapper: SysParamsItemMapper) :
    ServiceImpl<SysParamsItemMapper?, SysParamsItem?>()
{
    fun selectByMainId(mainId: String?): List<SysParamsItem>
    {
        return sysParamsItemMapper.selectByMainId(mainId)
    }
}
