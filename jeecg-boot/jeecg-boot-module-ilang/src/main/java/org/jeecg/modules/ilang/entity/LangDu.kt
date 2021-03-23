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
 * @Description: 朗读内容
 * @Author: Allen
 * @Date:   2020-12-31
 * @Version: V1.0
 */
@TableName("ilang_ld_item")
@ApiModel(value = "ilang_ld_item对象", description = "朗读内容")
data class LangDu(

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
     * 简拼
     */
    @Excel(name = "简拼", width = 15.0)
    @ApiModelProperty(value = "简拼")
    var pinyin: String? = null,
    /**
     * 作者
     */
    @Excel(name = "作者", width = 15.0)
    @ApiModelProperty(value = "作者")
    var author: String? = null,
    /**
     * 是否推荐
     */
    @Excel(name = "是否推荐", width = 15.0)
    @ApiModelProperty(value = "是否推荐")
    var recommends: String? = null,
    /**
     * 封面
     */
    @Excel(name = "封面", width = 15.0)
    @ApiModelProperty(value = "封面")
    var cover: String? = null,
    /**
     * 分类ID
     */
    @Excel(name = "分类ID", width = 15.0)
    @ApiModelProperty(value = "分类ID")
    @Dict(dicCode = "ld_category")
    var categoryId: String? = null,
    /**
     * 分类
     */
    @Excel(name = "分类", width = 15.0)
    @ApiModelProperty(value = "分类")
    var categoryText: String? = null,
    /**
     * 背景图片
     */
    @Excel(name = "背景图片", width = 15.0)
    @ApiModelProperty(value = "背景图片")
    var bgPic: String? = null,
    /**
     * 背景音乐
     */
    @Excel(name = "背景音乐", width = 15.0)
    @ApiModelProperty(value = "背景音乐")
    @Dict(dicCode = "id", dicText = "name", dictTable = "ilang_ld_bgm")
    var bgMusic: String? = null,
    /**
     * 热度
     */
    @Excel(name = "热度", width = 15.0)
    @ApiModelProperty(value = "热度")
    var readCount: Int? = null,
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15.0)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "status")
    var status: String? = null,
    /**
     * 字幕
     */
    @Excel(name = "字幕", width = 15.0)
    @ApiModelProperty(value = "字幕")
    var lyric: String? = null,
    /**
     * 内容
     */
    @Excel(name = "内容", width = 15.0)
    @ApiModelProperty(value = "内容")
    var content: String? = null,
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
        const val COL_AUTHOR = "author"
        const val COL_RECOMMENDS = "recommends"
        const val COL_COVER = "cover"
        const val COL_CATEGORY_ID = "category_id"
        const val COL_CATEGORY_TEXT = "category_text"
        const val COL_BG_PIC = "bg_pic"
        const val COL_BG_MUSIC = "bg_music"
        const val COL_READ_COUNT = "read_count"
        const val COL_STATUS = "status"
        const val COL_LYRIC = "lyric"
        const val COL_CONTENT = "content"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
