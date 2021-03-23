package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.Song
import org.jeecg.modules.ilang.mapper.SongMapper
import org.springframework.stereotype.Service

/**
 * @Description: 歌曲
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
class SongService: ServiceImpl<SongMapper, Song>()
