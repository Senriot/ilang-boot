package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.LdBgm
import org.jeecg.modules.ilang.service.LdBgmService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 朗读背景音乐
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@Api(tags = ["朗读背景音乐"])
@RestController
@RequestMapping("/ilang/ldBgm")
class LdBgmController(private val ldBgmService: LdBgmService) : JeecgController<LdBgm, LdBgmService>()
{

    /**
     * 分页列表查询
     *
     * @param ldBgm
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "朗读背景音乐-分页列表查询")
    @ApiOperation(value = "朗读背景音乐-分页列表查询", notes = "朗读背景音乐-分页列表查询")
    @GetMapping(value = ["/list"])
    fun queryPageList(
        ldBgm: LdBgm?,
        @RequestParam(name = "pageNo", defaultValue = "1")
        pageNo: Long = 1,
        @RequestParam(name = "pageSize", defaultValue = "10")
        pageSize: Long = 10,
        req: HttpServletRequest
    ): Result<*>
    {
        val queryWrapper: QueryWrapper<LdBgm> = QueryGenerator.initQueryWrapper(ldBgm, req.parameterMap)
        val page = Page<LdBgm>(pageNo, pageSize)
        val pageList = ldBgmService.page(page, queryWrapper)
        return Result.OK(pageList)
    }

    /**
     *   添加
     *
     * @param ldBgm
     * @return
     */
    @AutoLog(value = "朗读背景音乐-添加")
    @ApiOperation(value = "朗读背景音乐-添加", notes = "朗读背景音乐-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody ldBgm: LdBgm): Result<*>
    {
        ldBgmService.save(ldBgm)
        return Result.OK("添加成功！")
    }

    /**
     *  编辑
     *
     * @param ldBgm
     * @return
     */
    @AutoLog(value = "朗读背景音乐-编辑")
    @ApiOperation(value = "朗读背景音乐-编辑", notes = "朗读背景音乐-编辑")
    @PutMapping("/edit")
    fun edit(@RequestBody ldBgm: LdBgm): Result<*>
    {
        ldBgmService.updateById(ldBgm)
        return Result.OK("编辑成功!")
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "朗读背景音乐-通过id删除")
    @ApiOperation(value = "朗读背景音乐-通过id删除", notes = "朗读背景音乐-通过id删除")
    @DeleteMapping("/delete")
    fun delete(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        ldBgmService.removeById(id)
        return Result.OK("删除成功!")
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "朗读背景音乐-批量删除")
    @ApiOperation(value = "朗读背景音乐-批量删除", notes = "朗读背景音乐-批量删除")
    @DeleteMapping("/deleteBatch")
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*>
    {
        this.ldBgmService.removeByIds(ids.split(","))
        return Result.OK("批量删除成功!")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "朗读背景音乐-通过id查询")
    @ApiOperation(value = "朗读背景音乐-通过id查询", notes = "朗读背景音乐-通过id查询")
    @GetMapping("/queryById")
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        val ldBgm = ldBgmService.getById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(ldBgm)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ldBgm
     */
    @RequestMapping("/exportXls")
    fun exportXls(request: HttpServletRequest, ldBgm: LdBgm): ModelAndView?
    {
        return super.exportXls(request, ldBgm, LdBgm::class.java, "朗读背景音乐")
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
        return super.importExcel(request, response, LdBgm::class.java)
    }
}
