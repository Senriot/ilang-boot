package org.jeecg.modules.ilang.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ilang.entity.UserRecord;
import org.jeecg.modules.ilang.service.UserRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 用户录音
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Api(tags = ["用户录音"])
@RestController
@RequestMapping("/ilang/record")
class UserRecordController(private val userRecordService: UserRecordService) :
    JeecgController<UserRecord, UserRecordService>()
{

    /**
     * 分页列表查询
     *
     * @param userRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "用户录音-分页列表查询")
    @ApiOperation(value = "用户录音-分页列表查询", notes = "用户录音-分页列表查询")
    @GetMapping(value = ["/list"])
    fun queryPageList(
       openId:String
    ): Result<*>
    {
        val r = userRecordService.listByOpenId(openId)
        return Result.OK(r)
//        val queryWrapper: QueryWrapper<UserRecord> = QueryGenerator.initQueryWrapper(userRecord, req.parameterMap)
//        val page = Page<UserRecord>(pageNo, pageSize)
//        val pageList = userRecordService.page(page, queryWrapper)
//        return Result.OK(pageList)
    }

    /**
     *   添加
     *
     * @param userRecord
     * @return
     */
    @AutoLog(value = "用户录音-添加")
    @ApiOperation(value = "用户录音-添加", notes = "用户录音-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody userRecord: UserRecord): Result<*>
    {
        userRecordService.save(userRecord)
        return Result.OK("添加成功！")
    }

    /**
     *  编辑
     *
     * @param userRecord
     * @return
     */
    @AutoLog(value = "用户录音-编辑")
    @ApiOperation(value = "用户录音-编辑", notes = "用户录音-编辑")
    @PutMapping("/edit")
    fun edit(@RequestBody userRecord: UserRecord): Result<*>
    {
        userRecordService.updateById(userRecord)
        return Result.OK("编辑成功!")
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户录音-通过id删除")
    @ApiOperation(value = "用户录音-通过id删除", notes = "用户录音-通过id删除")
    @DeleteMapping("/delete")
    fun delete(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        userRecordService.removeById(id)
        return Result.OK("删除成功!")
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "用户录音-批量删除")
    @ApiOperation(value = "用户录音-批量删除", notes = "用户录音-批量删除")
    @DeleteMapping("/deleteBatch")
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*>
    {
        this.userRecordService.removeByIds(ids.split(","))
        return Result.OK("批量删除成功!")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户录音-通过id查询")
    @ApiOperation(value = "用户录音-通过id查询", notes = "用户录音-通过id查询")
    @GetMapping("/queryById")
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*>
    {

        val userRecord = userRecordService.getOneById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(userRecord)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param userRecord
     */
    @RequestMapping("/exportXls")
    fun exportXls(request: HttpServletRequest, userRecord: UserRecord): ModelAndView?
    {
        return super.exportXls(request, userRecord, UserRecord::class.java, "用户录音")
    }


    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/importExcel", method = [RequestMethod.POST])
    fun importExcel(request: HttpServletRequest, response: HttpServletResponse): Result<*>
    {
        return super.importExcel(request, response, UserRecord::class.java)
    }
}
