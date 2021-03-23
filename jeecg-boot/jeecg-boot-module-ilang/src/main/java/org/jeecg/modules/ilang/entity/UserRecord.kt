package org.jeecg.modules.ilang.entity;

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.jeecgframework.poi.excel.annotation.Excel
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

/**
 * @Description: 用户录音
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@TableName("ilang_user_record")
@ApiModel(value = "ilang_user_record对象", description = "用户录音")
data class UserRecord(

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    /**
     * 微信ID
     */
    @Excel(name = "微信ID", width = 15.0)
    @ApiModelProperty(value = "微信ID")
    var openId: String? = null,
    /**
     * 歌曲ID
     */
    @Excel(name = "歌曲ID", width = 15.0)
    @ApiModelProperty(value = "歌曲ID")
    var songId: String? = null,


    var song: Song? = null,
    /**
     * 朗读ID
     */
    @Excel(name = "朗读ID", width = 15.0)
    @ApiModelProperty(value = "朗读ID")
    var langduId: String? = null,


    var langDu: LangDu? = null,
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15.0)
    @ApiModelProperty(value = "地址")
    var url: String? = null,
    /**
     * 类别
     */
    @Excel(name = "类别", width = 15.0)
    @ApiModelProperty(value = "类别")
    var recordType: String? = null,
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
    var updateTime: Date? = null,
)
{
    companion object
    {
        const val COL_ID = "id"
        const val COL_OPEN_ID = "open_id"
        const val COL_SONG_ID = "song_id"
        const val COL_LANGDU_ID = "langdu_id"
        const val COL_URL = "url"
        const val COL_RECORD_TYPE = "record_type"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
