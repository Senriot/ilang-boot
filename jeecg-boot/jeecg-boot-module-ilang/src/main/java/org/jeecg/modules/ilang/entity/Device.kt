package org.jeecg.modules.ilang.entity

import io.swagger.annotations.ApiModel
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import org.jeecg.common.aspect.annotation.Dict
import org.jeecgframework.poi.excel.annotation.Excel
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@ApiModel(value = "ilang_device对象", description = "设备")
@TableName("ilang_device")
class Device : Serializable
{
    /**主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    var id: String? = null

    /**设备ID */
    @Excel(name = "设备ID", width = 15.0)
    @ApiModelProperty(value = "设备ID")
    var devId: String? = null

    /**名称 */
    @Excel(name = "名称", width = 15.0)
    @ApiModelProperty(value = "名称")
    var devName: String? = null

    /**所属产品 */
    @Excel(name = "所属产品", width = 15.0, dictTable = "ilang_device_produce", dicText = "name", dicCode = "id")
    @Dict(dictTable = "ilang_device_produce", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "所属产品")
    var productId: String? = null

    /**激活时间 */
    @Excel(name = "激活时间", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "激活时间")
    var activeDate: Date? = null

    /**所在城市 */
    @Excel(name = "所在城市", width = 15.0)
    @ApiModelProperty(value = "所在城市")
    var city: String? = null

    /**详细地址 */
    @Excel(name = "详细地址", width = 15.0)
    @ApiModelProperty(value = "详细地址")
    var address: String? = null

    /**固件版本 */
    @Excel(name = "固件版本", width = 15.0)
    @ApiModelProperty(value = "固件版本")
    var firmwareVersion: String? = null

    /**在线时间 */
    @Excel(name = "在线时间", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "在线时间")
    var onlineDate: Date? = null

    /**在线状态 */
    @Excel(name = "在线状态", width = 15.0)
    @ApiModelProperty(value = "在线状态")
    var onlineStatus: String? = null

    /**到期时间 */
    @Excel(name = "到期时间", width = 20.0, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "到期时间")
    var expireDate: Date? = null

    /**设备状态 */
    @Excel(name = "设备状态", width = 15.0, dicCode = "status")
    @Dict(dicCode = "status")
    @ApiModelProperty(value = "设备状态")
    var status: String? = null

    /**说明 */
    @Excel(name = "说明", width = 15.0)
    @ApiModelProperty(value = "说明")
    var remark: String? = null

    /**创建人 */
    @ApiModelProperty(value = "创建人")
    var createBy: String? = null

    /**创建日期 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    var createTime: Date? = null

    /**更新人 */
    @ApiModelProperty(value = "更新人")
    var updateBy: String? = null

    /**更新日期 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    var updateTime: Date? = null
}
