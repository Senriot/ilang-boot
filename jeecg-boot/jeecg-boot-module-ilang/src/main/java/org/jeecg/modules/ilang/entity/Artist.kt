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
 * @Description: 歌星
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@TableName("ilang_artist")
@ApiModel(value = "ilang_artist对象", description = "歌星")
data class Artist(

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: Int? = null,
    /**
     * 图片
     */
    @Excel(name = "图片", width = 15.0)
    @ApiModelProperty(value = "图片")
    var avatar: String? = null,
    /**
     * 歌星名称
     */
    @Excel(name = "歌星名称", width = 15.0)
    @ApiModelProperty(value = "歌星名称")
    var name: String? = null,
    /**
     * 拼音码
     */
    @Excel(name = "拼音码", width = 15.0)
    @ApiModelProperty(value = "拼音码")
    var inputCode: String? = null,
    /**
     * 区域
     */
    @Excel(name = "区域ID", width = 15.0)
    @ApiModelProperty(value = "区域ID")
    @Dict(dicCode = "artist_type")
    var areaId: String? = null,
    /**
     * 区域
     */
    @Excel(name = "区域", width = 15.0)
    @ApiModelProperty(value = "区域")
    var areaText: String? = null,
    /**
     * 类别
     */
    @Excel(name = "类别", width = 15.0)
    @ApiModelProperty(value = "类别")
    @Dict(dicCode = "artist_type")
    var gender: String? = null,
    /**
     * 出生地
     */
    @Excel(name = "出生地", width = 15.0)
    @ApiModelProperty(value = "出生地")
    var birthplace: String? = null,
    /**
     * 生日
     */
    @Excel(name = "生日", width = 15.0, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    var birthday: Date? = null,
    /**
     * 所属公司
     */
    @Excel(name = "所属公司", width = 15.0)
    @ApiModelProperty(value = "所属公司")
    var company: String? = null,
    /**
     * 星座
     */
    @Excel(name = "星座", width = 15.0)
    @ApiModelProperty(value = "星座")
    var constellation: String? = null,
    /**
     * 简介
     */
    @Excel(name = "简介", width = 15.0)
    @ApiModelProperty(value = "简介")
    var info: String? = null,
    /**
     * 热度
     */
    @Excel(name = "热度", width = 15.0)
    @ApiModelProperty(value = "热度")
    var hot: Int? = null,
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15.0)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "status")
    var status: String? = null,
    /**
     * 体重
     */
    @Excel(name = "体重", width = 15.0)
    @ApiModelProperty(value = "体重")
    var weight: String? = null,
    /**
     * 身高
     */
    @Excel(name = "身高", width = 15.0)
    @ApiModelProperty(value = "身高")
    var stature: String? = null,
    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    var updateTime: Date? = null,
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    var createTime: Date? = null,
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15.0)
    @ApiModelProperty(value = "创建人")
    var createBy: String? = null,
    /**
     * 更新人
     */
    @Excel(name = "更新人", width = 15.0)
    @ApiModelProperty(value = "更新人")
    var updateBy: String? = null
)
{
    companion object
    {
        const val COL_ID = "id"
        const val COL_AVATAR = "avatar"
        const val COL_NAME = "name"
        const val COL_INPUT_CODE = "input_code"
        const val COL_AREA_ID = "area_id"
        const val COL_AREA_TEXT = "area_text"
        const val COL_GENDER = "gender"
        const val COL_BIRTHPLACE = "birthplace"
        const val COL_BIRTHDAY = "birthday"
        const val COL_COMPANY = "company"
        const val COL_CONSTELLATION = "constellation"
        const val COL_INFO = "info"
        const val COL_HOT = "hot"
        const val COL_STATUS = "status"
        const val COL_WEIGHT = "weight"
        const val COL_STATURE = "stature"
        const val COL_UPDATE_TIME = "update_time"
        const val COL_CREATE_TIME = "create_time"
        const val COL_CREATE_BY = "create_by"
        const val COL_UPDATE_BY = "update_by"
    }
}
