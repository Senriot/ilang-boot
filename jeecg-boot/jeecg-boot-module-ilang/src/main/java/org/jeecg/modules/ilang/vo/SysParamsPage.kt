package org.jeecg.modules.ilang.vo

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.jeecgframework.poi.excel.annotation.Excel
import org.jeecgframework.poi.excel.annotation.ExcelCollection
import org.jeecg.modules.ilang.entity.SysParamsItem
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

/**
 * @Description: 系统参数
 * @Author: jeecg-boot
 * @Date: 2021-01-02
 * @Version: V1.0
 */
@ApiModel(value = "ilang_sys_paramsPage对象", description = "系统参数")
class SysParamsPage
{
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    var id: String? = null

    /**
     * 参数编码
     */
    @Excel(name = "参数编码", width = 15.0)
    @ApiModelProperty(value = "参数编码")
    var paramCode: String? = null

    /**
     * 参数名称
     */
    @Excel(name = "参数名称", width = 15.0)
    @ApiModelProperty(value = "参数名称")
    var paramName: String? = null

    /**
     * 参数值
     */
    @Excel(name = "参数值", width = 15.0)
    @ApiModelProperty(value = "参数值")
    var paramValue: String? = null

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

    @ExcelCollection(name = "参数详情")
    @ApiModelProperty(value = "参数详情")
    var sysParamsItemList: List<SysParamsItem> = arrayListOf()

    var paramsItems: Map<String, SysParamsItem> = mapOf()
}
