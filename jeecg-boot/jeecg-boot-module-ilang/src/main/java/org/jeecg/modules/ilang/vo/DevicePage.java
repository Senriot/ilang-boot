package org.jeecg.modules.ilang.vo;

import java.util.List;

import org.jeecg.modules.ilang.entity.Device;
import org.jeecg.modules.ilang.entity.DeviceChannel;
import org.jeecg.modules.ilang.entity.DeviceLog;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2020-12-24
 * @Version: V1.0
 */
@Data
@ApiModel(value = "ilang_devicePage对象", description = "设备")
public class DevicePage
{

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 设备ID
     */
    @Excel(name = "设备ID", width = 15)
    @ApiModelProperty(value = "设备ID")
    private String devId;
    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String devName;
    /**
     * 所属产品
     */
    @Excel(name = "所属产品", width = 15, dictTable = "ilang_device_produce", dicText = "name", dicCode = "id")
    @Dict(dictTable = "ilang_device_produce", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "所属产品")
    private String productId;
    /**
     * 激活时间
     */
    @Excel(name = "激活时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "激活时间")
    private Date   activeDate;
    /**
     * 所在城市
     */
    @Excel(name = "所在城市", width = 15)
    @ApiModelProperty(value = "所在城市")
    private String city;
    /**
     * 详细地址
     */
    @Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private String address;
    /**
     * 固件版本
     */
    @Excel(name = "固件版本", width = 15)
    @ApiModelProperty(value = "固件版本")
    private String firmwareVersion;
    /**
     * 在线时间
     */
    @Excel(name = "在线时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "在线时间")
    private Date   onlineDate;
    /**
     * 在线状态
     */
    @Excel(name = "在线状态", width = 15)
    @ApiModelProperty(value = "在线状态")
    private String onlineStatus;
    /**
     * 到期时间
     */
    @Excel(name = "到期时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "到期时间")
    private Date   expireDate;
    /**
     * 设备状态
     */
    @Excel(name = "设备状态", width = 15, dicCode = "status")
    @Dict(dicCode = "status")
    @ApiModelProperty(value = "设备状态")
    private String status;
    /**
     * 说明
     */
    @Excel(name = "说明", width = 15)
    @ApiModelProperty(value = "说明")
    private String remark;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date   createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date   updateTime;

    @ExcelCollection(name = "设备通道")
    @ApiModelProperty(value = "设备通道")
    private List<DeviceChannel> deviceChannelList;
    @ExcelCollection(name = "设备日志")
    @ApiModelProperty(value = "设备日志")
    private List<DeviceLog>     deviceLogList;

}
