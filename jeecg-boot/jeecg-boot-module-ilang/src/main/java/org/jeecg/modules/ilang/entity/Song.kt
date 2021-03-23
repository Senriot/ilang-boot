package org.jeecg.modules.ilang.entity;

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.jeecg.common.aspect.annotation.Dict
import org.jeecgframework.poi.excel.annotation.Excel
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

/**
 * @Description: 歌曲
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@TableName("ilang_song")
@ApiModel(value = "ilang_song对象", description = "歌曲")
data class Song(

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    /**
     * 编码
     */
    @Excel(name = "编码", width = 15.0)
    @ApiModelProperty(value = "编码")
    var code: String? = null,
    /**
     * 歌名
     */
    @Excel(name = "歌名", width = 15.0)
    @ApiModelProperty(value = "歌名")
    var name: String? = null,
    /**
     * 拼音码
     */
    @Excel(name = "拼音码", width = 15.0)
    @ApiModelProperty(value = "拼音码")
    var inputCode: String? = null,
    /**
     * 字数
     */
    @Excel(name = "字数", width = 15.0)
    @ApiModelProperty(value = "字数")
    var wordCount: Int? = null,
    /**
     * 语种
     */
    @Excel(name = "语种ID", width = 15.0)
    @ApiModelProperty(value = "语种ID")
    @Dict(dicCode = "song_language")
    var langId: String? = null,
    /**
     * 语种
     */
    @Excel(name = "语种", width = 15.0)
    @ApiModelProperty(value = "语种")
    var langText: String? = null,
    /**
     * 分类
     */
    @Excel(name = "分类ID", width = 15.0)
    @ApiModelProperty(value = "分类ID")
    @Dict(dicCode = "song_category")
    var typeId: String? = null,
    /**
     * 分类
     */
    @Excel(name = "分类", width = 15.0)
    @ApiModelProperty(value = "分类")
    var typeText: String? = null,
    /**
     * 歌星
     */
    @Excel(name = "歌星ID", width = 15.0)
    @ApiModelProperty(value = "歌星ID")
    var artistIds: String? = null,
    /**
     * 歌星
     */
    @Excel(name = "歌星", width = 15.0)
    @ApiModelProperty(value = "歌星")
    var artistsName: String? = null,
    /**
     * 伴唱音轨
     */
    @Excel(name = "伴唱音轨", width = 15.0)
    @ApiModelProperty(value = "伴唱音轨")
    var amTrack: String? = null,
    /**
     * 歌曲画质
     */
    @Excel(name = "歌曲画质", width = 15.0)
    @ApiModelProperty(value = "歌曲画质")
    var quality: String? = null,
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15.0)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "status")
    var status: String? = null,
    /**
     * 播放量
     */
    @Excel(name = "播放量", width = 15.0)
    @ApiModelProperty(value = "播放量")
    var hot: Int? = null,
    /**
     * 本地路径
     */
    @Excel(name = "本地路径", width = 15.0)
    @ApiModelProperty(value = "本地路径")
    var filePath: String? = null,
    /**
     * 文件名
     */
    @Excel(name = "文件名", width = 15.0)
    @ApiModelProperty(value = "文件名")
    var fileName: String? = null,
    /**
     * 原唱音量
     */
    @Excel(name = "原唱音量", width = 15.0)
    @ApiModelProperty(value = "原唱音量")
    var orVol: Int? = null,
    /**
     * 伴唱音量
     */
    @Excel(name = "伴唱音量", width = 15.0)
    @ApiModelProperty(value = "伴唱音量")
    var acVol: Int? = null,
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15.0)
    @ApiModelProperty(value = "创建人")
    var createBy: String? = null,
    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    var createTime: Date? = null,
    /**
     * 更新人
     */
    @Excel(name = "更新人", width = 15.0)
    @ApiModelProperty(value = "更新人")
    var updateBy: String? = null,
    /**
     * 更新日期
     */
    @Excel(name = "更新日期", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    var updateTime: Date? = null
)
{
    companion object
    {
        const val COL_ID = "id"
        const val COL_CODE = "code"
        const val COL_NAME = "name"
        const val COL_INPUT_CODE = "input_code"
        const val COL_WORD_COUNT = "word_count"
        const val COL_LANG_ID = "lang_id"
        const val COL_LANG_TEXT = "lang_text"
        const val COL_TYPE_ID = "type_id"
        const val COL_TYPE_TEXT = "type_text"
        const val COL_ARTIST_IDS = "artist_ids"
        const val COL_ARTISTS_NAME = "artists_name"
        const val COL_AM_TRACK = "am_track"
        const val COL_QUALITY = "quality"
        const val COL_STATUS = "status"
        const val COL_HOT = "hot"
        const val COL_FILE_PATH = "file_path"
        const val COL_FILE_NAME = "file_name"
        const val COL_OR_VOL = "or_vol"
        const val COL_AC_VOL = "ac_vol"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
