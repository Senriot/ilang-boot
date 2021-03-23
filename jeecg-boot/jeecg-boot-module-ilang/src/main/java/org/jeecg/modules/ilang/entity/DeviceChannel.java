package org.jeecg.modules.ilang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 设备通道
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@ApiModel(value="ilang_device对象", description="设备")
@Data
@TableName("ilang_bgm_device_channel")
public class DeviceChannel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private String id;
	/**设备ID*/
	@ApiModelProperty(value = "设备ID")
	private String deviceId;
	/**通道编号*/
	@Excel(name = "通道编号", width = 15)
	@ApiModelProperty(value = "通道编号")
	private Integer chIndex;
	/**通道名称*/
	@Excel(name = "通道名称", width = 15)
	@ApiModelProperty(value = "通道名称")
	private String name;
	/**当前播放ID*/
	@Excel(name = "当前播放ID", width = 15)
	@ApiModelProperty(value = "当前播放ID")
	private String playId;
	/**播放曲目*/
	@Excel(name = "播放曲目", width = 15)
	@ApiModelProperty(value = "播放曲目")
	private String playName;
	/**音量*/
	@Excel(name = "音量", width = 15)
	@ApiModelProperty(value = "音量")
	private Integer volume;
	/**启用*/
	@Excel(name = "启用", width = 15)
	@ApiModelProperty(value = "启用")
	private String enabled;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private String status;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private String sysOrgCode;
}
