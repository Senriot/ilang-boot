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
 * @Description: 党政学习
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@TableName("ilang_dz_xiexi")
@ApiModel(value = "ilang_dz_xiexi对象", description = "党政学习")
data class DzXiexi(

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    /**
     * 名称
     */
    @Excel(name = "名称", width = 15.0)
    @ApiModelProperty(value = "名称")
    var name: String? = null,
    /**
     * 拼音
     */
    @Excel(name = "拼音", width = 15.0)
    @ApiModelProperty(value = "拼音")
    var pinyin: String? = null,
    /**
     * 类别
     */
    @Excel(name = "类别", width = 15.0)
    @ApiModelProperty(value = "类别")
    @Dict(dicCode = "dz_type")
    var type: String? = null,
    /**
     * 类别名称
     */
    @Excel(name = "类别名称", width = 15.0)
    @ApiModelProperty(value = "类别名称")
    var typeText: String? = null,
    /**
     * 封面图片
     */
    @Excel(name = "封面图片", width = 15.0)
    @ApiModelProperty(value = "封面图片")
    var pic: String? = null,
    /**
     * 内容文件
     */
    @Excel(name = "内容文件", width = 15.0)
    @ApiModelProperty(value = "内容文件")
    var contentFile: String? = null,
    /**
     * 字幕
     */
    @Excel(name = "字幕", width = 15.0)
    @ApiModelProperty(value = "字幕")
    var subtitle: String? = null,
    /**
     * 音频地址
     */
    @Excel(name = "音频地址", width = 15.0)
    @ApiModelProperty(value = "音频地址")
    var audioUrl: String? = null,
    /**
     * 背景图片
     */
    @Excel(name = "背景图片", width = 15.0)
    @ApiModelProperty(value = "背景图片")
    var bgPic: String? = null,
    /**
     * 阅读次数
     */
    @Excel(name = "阅读次数", width = 15.0)
    @ApiModelProperty(value = "阅读次数")
    var readCount: Int? = null,
    /**
     * 时长
     */
    @Excel(name = "时长", width = 15.0)
    @ApiModelProperty(value = "时长")
    var duration: String? = null,
    /**
     * 简介
     */
    @Excel(name = "简介", width = 15.0)
    @ApiModelProperty(value = "简介")
    var remark: String? = null,
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15.0)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "status")
    var status: String? = null,
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
        const val COL_NAME = "name"
        const val COL_PINYIN = "pinyin"
        const val COL_TYPE = "type"
        const val COL_TYPE_TEXT = "type_text"
        const val COL_PIC = "pic"
        const val COL_CONTENT_FILE = "content_file"
        const val COL_SUBTITLE = "subtitle"
        const val COL_AUDIO_URL = "audio_url"
        const val COL_BG_PIC = "bg_pic"
        const val COL_READ_COUNT = "read_count"
        const val COL_DURATION = "duration"
        const val COL_REMARK = "remark"
        const val COL_STATUS = "status"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
