package org.jeecg.modules.ilang.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.shiro.SecurityUtils
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.common.system.vo.LoginUser
import org.jeecg.common.util.oConvertUtils
import org.jeecg.modules.ilang.entity.SysParams
import org.jeecg.modules.ilang.entity.SysParamsItem
import org.jeecg.modules.ilang.service.SysParamsItemService
import org.jeecg.modules.ilang.service.SysParamsService
import org.jeecg.modules.ilang.vo.SysParamsPage
import org.jeecgframework.poi.excel.ExcelImportUtil
import org.jeecgframework.poi.excel.def.NormalExcelConstants
import org.jeecgframework.poi.excel.entity.ExportParams
import org.jeecgframework.poi.excel.entity.ImportParams
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 系统参数
 * @Author: jeecg-boot
 * @Date: 2021-01-02
 * @Version: V1.0
 */
@Api(tags = ["系统参数"])
@RestController
@RequestMapping("/ilang/sysParams")
class SysParamsController(
    private val sysParamsService: SysParamsService,
    private val sysParamsItemService: SysParamsItemService
)
{

    /**
     * 分页列表查询
     *
     * @param sysParams
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "系统参数-分页列表查询")
    @ApiOperation(value = "系统参数-分页列表查询", notes = "系统参数-分页列表查询")
    @GetMapping(value = ["/list"])
    fun queryPageList(
        sysParams: SysParams?,
        @RequestParam(name = "pageNo", defaultValue = "1") pageNo: Long = 1,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Long = 10,
        req: HttpServletRequest
    ): Result<*>
    {
        val queryWrapper: QueryWrapper<SysParams> = QueryGenerator.initQueryWrapper(sysParams, req.parameterMap)
        val page = Page<SysParams>(pageNo, pageSize)
        val pageList: IPage<SysParams> = sysParamsService.page(page, queryWrapper)
        return Result.OK(pageList)
    }

    /**
     * 添加
     *
     * @param sysParamsPage
     * @return
     */
    @AutoLog(value = "系统参数-添加")
    @ApiOperation(value = "系统参数-添加", notes = "系统参数-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody sysParamsPage: SysParamsPage): Result<*>
    {
        val sysParams = SysParams()
        BeanUtils.copyProperties(sysParamsPage, sysParams)
        sysParamsService.saveMain(sysParams, sysParamsPage.sysParamsItemList)
        return Result.OK("添加成功！")
    }

    /**
     * 编辑
     *
     * @param sysParamsPage
     * @return
     */
    @AutoLog(value = "系统参数-编辑")
    @ApiOperation(value = "系统参数-编辑", notes = "系统参数-编辑")
    @PutMapping(value = ["/edit"])
    fun edit(@RequestBody sysParamsPage: SysParamsPage): Result<*>
    {
        val sysParams = SysParams()
        BeanUtils.copyProperties(sysParamsPage, sysParams)
        val (id, paramCode, paramName, paramValue, createBy, createTime, updateBy, updateTime) = sysParamsService.getById(
            sysParams.id
        ) ?: return Result.error("未找到对应数据")
        sysParamsService.updateMain(sysParams, sysParamsPage.sysParamsItemList)
        return Result.OK("编辑成功!")
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "系统参数-通过id删除")
    @ApiOperation(value = "系统参数-通过id删除", notes = "系统参数-通过id删除")
    @DeleteMapping(value = ["/delete"])
    fun delete(@RequestParam(name = "id", required = true) id: String?): Result<*>
    {
        sysParamsService.delMain(id!!)
        return Result.OK("删除成功!")
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "系统参数-批量删除")
    @ApiOperation(value = "系统参数-批量删除", notes = "系统参数-批量删除")
    @DeleteMapping(value = ["/deleteBatch"])
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*>
    {
        sysParamsService.delBatchMain(listOf(*ids.split(",".toRegex()).toTypedArray()))
        return Result.OK("批量删除成功！")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "系统参数-通过id查询")
    @ApiOperation(value = "系统参数-通过id查询", notes = "系统参数-通过id查询")
    @GetMapping(value = ["/queryById"])
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        val sysParams = sysParamsService.getById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(sysParams)
    }

    @AutoLog(value = "系统参数-通过code查询")
    @ApiOperation(value = "系统参数-通过code查询", notes = "系统参数-通过code查询")
    @GetMapping(value = ["/queryByCode"])
    fun queryByCode(@RequestParam(name = "code", required = true) code: String): Result<*>
    {
        val sysParams = sysParamsService.getOne(QueryWrapper<SysParams>().eq(SysParams.COL_PARAM_CODE, code))
            ?: return Result.error("未找到对应数据")
        val items = sysParamsItemService.selectByMainId(sysParams.id)
        val result = SysParamsPage()
        BeanUtils.copyProperties(sysParams, result)

        val params = mutableMapOf<String, SysParamsItem>()

        items.forEach {
            params[it.paramsName] = it
        }

        result.paramsItems = params
        return Result.OK(result)
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "参数详情通过主表ID查询")
    @ApiOperation(value = "参数详情主表ID查询", notes = "参数详情-通主表ID查询")
    @GetMapping(value = ["/querySysParamsItemByMainId"])
    fun querySysParamsItemListByMainId(@RequestParam(name = "id", required = true) id: String?): Result<*>
    {
        val sysParamsItemList = sysParamsItemService.selectByMainId(id)
        return Result.OK(sysParamsItemList)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param sysParams
     */
    @RequestMapping(value = ["/exportXls"])
    fun exportXls(request: HttpServletRequest, sysParams: SysParams?): ModelAndView
    {
        // Step.1 组装查询条件查询数据
        val queryWrapper: QueryWrapper<SysParams> = QueryGenerator.initQueryWrapper(sysParams, request.parameterMap)
        val sysUser = SecurityUtils.getSubject().principal as LoginUser

        //Step.2 获取导出数据
        val queryList = sysParamsService.list(queryWrapper)
        // 过滤选中数据
        val selections = request.getParameter("selections")
        var sysParamsList: List<SysParams> = ArrayList()
        sysParamsList = if (oConvertUtils.isEmpty(selections))
        {
            queryList
        } else
        {
            val selectionList = listOf(*selections.split(",".toRegex()).toTypedArray())
            queryList.stream().filter { (id) -> selectionList.contains(id) }.collect(Collectors.toList())
        }

        // Step.3 组装pageList
        val pageList: MutableList<SysParamsPage> = ArrayList()
        for (main in sysParamsList)
        {
            val vo = SysParamsPage()
            BeanUtils.copyProperties(main, vo)
            val sysParamsItemList = sysParamsItemService.selectByMainId(main.id)
            vo.sysParamsItemList = sysParamsItemList
            pageList.add(vo)
        }

        // Step.4 AutoPoi 导出Excel
        val mv = ModelAndView(JeecgEntityExcelView())
        mv.addObject(NormalExcelConstants.FILE_NAME, "系统参数列表")
        mv.addObject(NormalExcelConstants.CLASS, SysParamsPage::class.java)
        mv.addObject(NormalExcelConstants.PARAMS, ExportParams("系统参数数据", "导出人:" + sysUser.realname, "系统参数"))
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList)
        return mv
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = ["/importExcel"], method = [RequestMethod.POST])
    fun importExcel(request: HttpServletRequest, response: HttpServletResponse?): Result<*>
    {
        val multipartRequest = request as MultipartHttpServletRequest
        val fileMap = multipartRequest.fileMap
        for ((_, file) in fileMap)
        {
            val params = ImportParams()
            params.titleRows = 2
            params.headRows = 1
            params.isNeedSave = true
            return try
            {
                val list =
                    ExcelImportUtil.importExcel<SysParamsPage>(file.inputStream, SysParamsPage::class.java, params)
                for (page in list)
                {
                    val po = SysParams()
                    BeanUtils.copyProperties(page, po)
                    sysParamsService.saveMain(po, page.sysParamsItemList)
                }
                Result.OK("文件导入成功！数据行数:" + list.size)
            } catch (e: Exception)
            {
                Result.error("文件导入失败:" + e.message)
            } finally
            {
                try
                {
                    file.inputStream.close()
                } catch (e: IOException)
                {
                    e.printStackTrace()
                }
            }
        }
        return Result.OK("文件导入失败！")
    }
}
