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
 * @Description: 设备类别
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@TableName("ilang_device_produce")
@ApiModel(value = "ilang_device_produce对象", description = "设备类别")
data class DeviceProduce(

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    /**
     * 产品名称
     */
    @Excel(name = "产品名称", width = 15.0)
    @ApiModelProperty(value = "产品名称")
    var name: String? = null,
    /**
     * 说明
     */
    @Excel(name = "说明", width = 15.0)
    @ApiModelProperty(value = "说明")
    var remark: String? = null,
    /**
     * 排序
     */
    @Excel(name = "排序", width = 15.0)
    @ApiModelProperty(value = "排序")
    var sortNo: Int? = null,
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
    var updateTime: Date? = null,
)
{
    companion object
    {
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_REMARK = "remark"
        const val COL_SORT_NO = "sort_no"
        const val COL_STATUS = "status"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
