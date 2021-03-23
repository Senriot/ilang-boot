package org.jeecg.modules.ilang.service;

import org.jeecg.modules.ilang.entity.UserRecord;
import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.mapper.UserRecordMapper

/**
 * @Description: 用户录音
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
class UserRecordService : ServiceImpl<UserRecordMapper, UserRecord>()
{
    fun listByOpenId(openId: String): List<UserRecord>
    {
        return baseMapper.findAllByOpenId(openId)
    }

    fun getOneById(id: String): UserRecord?
    {
        return  baseMapper.getOneById(id)
    }


}
