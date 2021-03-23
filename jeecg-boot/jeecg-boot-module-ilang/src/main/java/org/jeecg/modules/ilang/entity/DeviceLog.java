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
 * @Description: 设备日志
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@ApiModel(value="ilang_device对象", description="设备")
@Data
@TableName("ilang_iot_device_log")
public class DeviceLog implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "id")
	private String id;
	/**生成时间*/
	@Excel(name = "生成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "生成时间")
	private Date generateTime;
	/**Topic*/
	@Excel(name = "Topic", width = 15)
	@ApiModelProperty(value = "Topic")
	private String topic;
	/**设备*/
	@ApiModelProperty(value = "设备")
	private String deviceId;
	/**内容*/
	@Excel(name = "内容", width = 15)
	@ApiModelProperty(value = "内容")
	private String payload;
	/**消息类别*/
	@Excel(name = "消息类别", width = 15)
	@ApiModelProperty(value = "消息类别")
	private String msgType;
}
