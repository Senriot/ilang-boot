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
 * @Description: 智能开关
 * @Author: Allen
 * @Date:   2020-07-25
 * @Version: V1.0
 */
@TableName("ilang_iot_switch")
@ApiModel(value = "ilang_iot_switch对象", description = "智能开关")
data class Switch(

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    /**
     * 编号
     */
    @Excel(name = "编号", width = 15.0)
    @ApiModelProperty(value = "编号")
    var code: String? = null,
    /**
     * 名称
     */
    @Excel(name = "名称", width = 15.0)
    @ApiModelProperty(value = "名称")
    var name: String? = null,
    /**
     * 城市
     */
    @Excel(name = "城市", width = 15.0)
    @ApiModelProperty(value = "城市")
    var city: String? = null,
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15.0)
    @ApiModelProperty(value = "地址")
    var address: String? = null,
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15.0)
    @ApiModelProperty(value = "状态")
    @Dict(dicCode = "switch_status")
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
    @Excel(name = "创建日期", width = 15.0, format = "yyyy-MM-dd HH:mm:ss")
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
    @Excel(name = "更新日期", width = 15.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    var updateTime: Date? = null,
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", width = 15.0)
    @ApiModelProperty(value = "所属部门")
    var sysOrgCode: String? = null,
    /**
     * IP地址
     */
    @Excel(name = "IP地址", width = 15.0)
    @ApiModelProperty(value = "IP地址")
    var ip: String? = null
)
{
    companion object
    {
        const val COL_ID = "id"
        const val COL_CODE = "code"
        const val COL_NAME = "name"
        const val COL_CITY = "city"
        const val COL_ADDRESS = "address"
        const val COL_STATUS = "status"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
        const val COL_SYS_ORG_CODE = "sys_org_code"
        const val COL_IP = "ip"
    }
}
