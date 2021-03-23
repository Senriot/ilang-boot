package org.jeecg.modules.ilang.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.SysParams
import org.jeecg.modules.ilang.entity.SysParamsItem
import org.jeecg.modules.ilang.mapper.SysParamsItemMapper
import org.jeecg.modules.ilang.mapper.SysParamsMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/**
 * @Description: 系统参数
 * @Author: jeecg-boot
 * @Date: 2021-01-02
 * @Version: V1.0
 */
@Service
class SysParamsService(
    private val sysParamsMapper: SysParamsMapper,
    private val sysParamsItemMapper: SysParamsItemMapper
) : ServiceImpl<SysParamsMapper, SysParams>()
{
    @Transactional
    fun saveMain(sysParams: SysParams, sysParamsItemList: List<SysParamsItem>?)
    {
        sysParamsMapper.insert(sysParams)
        if (sysParamsItemList != null && sysParamsItemList.isNotEmpty())
        {
            for (entity in sysParamsItemList)
            {
                //外键设置
                entity.paramsId = sysParams.id
                sysParamsItemMapper.insert(entity)
            }
        }
    }

    @Transactional
    fun updateMain(sysParams: SysParams, sysParamsItemList: List<SysParamsItem>?)
    {
        sysParamsMapper.updateById(sysParams)

        //1.先删除子表数据
        sysParamsItemMapper.deleteByMainId(sysParams.id)

        //2.子表数据重新插入
        if (sysParamsItemList != null && sysParamsItemList.isNotEmpty())
        {
            for (entity in sysParamsItemList)
            {
                //外键设置
                entity.paramsId = sysParams.id
                sysParamsItemMapper.insert(entity)
            }
        }
    }

    @Transactional
    fun delMain(id: String)
    {
        sysParamsItemMapper.deleteByMainId(id)
        sysParamsMapper.deleteById(id)
    }

    @Transactional
    fun delBatchMain(idList: Collection<Serializable>)
    {
        for (id in idList)
        {
            sysParamsItemMapper.deleteByMainId(id.toString())
            sysParamsMapper.deleteById(id)
        }
    }
}
