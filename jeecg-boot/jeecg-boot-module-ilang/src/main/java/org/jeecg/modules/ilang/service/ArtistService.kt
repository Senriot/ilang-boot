package org.jeecg.modules.ilang.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.jeecg.modules.ilang.entity.Artist
import org.jeecg.modules.ilang.mapper.ArtistMapper
import org.springframework.stereotype.Service

/**
 * @Description: 歌星
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Service
class ArtistService: ServiceImpl<ArtistMapper, Artist>()
