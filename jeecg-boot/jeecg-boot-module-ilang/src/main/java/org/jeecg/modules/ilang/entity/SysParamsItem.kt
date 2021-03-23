package org.jeecg.modules.ilang.entity

import io.swagger.annotations.ApiModel
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import org.jeecgframework.poi.excel.annotation.Excel
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*

/**
 * @Description: 参数详情
 * @Author: jeecg-boot
 * @Date: 2021-01-02
 * @Version: V1.0
 */
@ApiModel(value = "ilang_sys_params对象", description = "系统参数")
@TableName("ilang_sys_params_item")
class SysParamsItem : Serializable
{
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    var id: String? = null

    /**
     * 参数ID
     */
    @ApiModelProperty(value = "参数ID")
    var paramsId: String? = null

    /**
     * 名称
     */
    @Excel(name = "名称", width = 15.0)
    @ApiModelProperty(value = "名称")
    var paramsName: String = ""

    /**
     * 参数值
     */
    @Excel(name = "参数值", width = 15.0)
    @ApiModelProperty(value = "参数值")
    var paramsValue: String? = null

    /**
     * 说明
     */
    @Excel(name = "说明", width = 15.0)
    @ApiModelProperty(value = "说明")
    var paramsRemark: String? = null

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    var createBy: String? = null

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    var createTime: Date? = null

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    var updateBy: String? = null

    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    var updateTime: Date? = null
}
