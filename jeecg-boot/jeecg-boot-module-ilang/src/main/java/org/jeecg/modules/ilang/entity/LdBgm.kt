package org.jeecg.modules.ilang.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 朗读背景音乐
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@TableName("ilang_ld_bgm")
@ApiModel(value = "ilang_ld_bgm对象", description = "朗读背景音乐")
data class LdBgm(

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
     * 图片
     */
    @Excel(name = "图片", width = 15.0)
    @ApiModelProperty(value = "图片")
    var pics: String? = null,
    /**
     * 下载地址
     */
    @Excel(name = "下载地址", width = 15.0)
    @ApiModelProperty(value = "下载地址")
    var url: String? = null,
    /**
     * 文件名
     */
    @Excel(name = "文件名", width = 15.0)
    @ApiModelProperty(value = "文件名")
    var fileName: String? = null,
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
        const val COL_PICS = "pics"
        const val COL_URL = "url"
        const val COL_FILE_NAME = "file_name"
        const val COL_CREATE_BY = "create_by"
        const val COL_CREATE_TIME = "create_time"
        const val COL_UPDATE_BY = "update_by"
        const val COL_UPDATE_TIME = "update_time"
    }
}
