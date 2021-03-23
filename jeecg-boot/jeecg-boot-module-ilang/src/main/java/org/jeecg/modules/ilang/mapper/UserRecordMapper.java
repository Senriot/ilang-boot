package org.jeecg.modules.ilang.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ilang.entity.UserRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Description: 用户录音
 * @Author: jeecg-boot
 * @Date: 2020-12-24
 * @Version: V1.0
 */
public interface UserRecordMapper extends BaseMapper<UserRecord>
{
    List<UserRecord> findAllByOpenId(@Param("openId") String openId);


    @Nullable
    UserRecord getOneById(@NotNull String id);
}
