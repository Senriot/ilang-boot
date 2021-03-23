package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.DzXiexi
import org.jeecg.modules.ilang.service.DzXiexiService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 党政学习
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@Api(tags = ["党政学习"])
@RestController
@RequestMapping("/ilang/dzXiexi")
class DzXiexiController(private val dzXiexiService: DzXiexiService) : JeecgController<DzXiexi, DzXiexiService>() {

    /**
     * 分页列表查询
     *
     * @param dzXiexi
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "党政学习-分页列表查询")
    @ApiOperation(value = "党政学习-分页列表查询", notes = "党政学习-分页列表查询")
    @GetMapping(value = ["/list/{type}"])
    fun queryPageList(
            @PathVariable("type")
            type:String,
            dzXiexi: DzXiexi?,
            @RequestParam(name = "pageNo", defaultValue = "1")
            pageNo: Long = 1,
            @RequestParam(name = "pageSize", defaultValue = "10")
            pageSize: Long = 10,
            req: HttpServletRequest
    ): Result<*> {
        val queryWrapper: QueryWrapper<DzXiexi?>? = QueryGenerator.initQueryWrapper(dzXiexi, req.parameterMap)?.and { it.eq("media_type",type) }
        val page = Page<DzXiexi>(pageNo, pageSize)
        val pageList = dzXiexiService.page(page, queryWrapper)
        return Result.OK(pageList)
    }

    /**
     *   添加
     *
     * @param dzXiexi
     * @return
     */
    @AutoLog(value = "党政学习-添加")
    @ApiOperation(value = "党政学习-添加", notes = "党政学习-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody dzXiexi: DzXiexi): Result<*> {
        dzXiexiService.save(dzXiexi)
        return Result.OK("添加成功！")
    }

    /**
     *  编辑
     *
     * @param dzXiexi
     * @return
     */
    @AutoLog(value = "党政学习-编辑")
    @ApiOperation(value = "党政学习-编辑", notes = "党政学习-编辑")
    @PutMapping("/edit")
    fun edit(@RequestBody dzXiexi: DzXiexi): Result<*> {
        dzXiexiService.updateById(dzXiexi)
        return Result.OK("编辑成功!")
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "党政学习-通过id删除")
    @ApiOperation(value = "党政学习-通过id删除", notes = "党政学习-通过id删除")
    @DeleteMapping("/delete")
    fun delete(@RequestParam(name = "id", required = true) id: String): Result<*> {
        dzXiexiService.removeById(id)
        return Result.OK("删除成功!")
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "党政学习-批量删除")
    @ApiOperation(value = "党政学习-批量删除", notes = "党政学习-批量删除")
    @DeleteMapping("/deleteBatch")
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*> {
        this.dzXiexiService.removeByIds(ids.split(","))
        return Result.OK("批量删除成功!")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "党政学习-通过id查询")
    @ApiOperation(value = "党政学习-通过id查询", notes = "党政学习-通过id查询")
    @GetMapping("/queryById")
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*> {
        val dzXiexi = dzXiexiService.getById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(dzXiexi)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param dzXiexi
     */
    @RequestMapping("/exportXls")
    fun exportXls(request: HttpServletRequest, dzXiexi: DzXiexi): ModelAndView? {
        return super.exportXls(request, dzXiexi, DzXiexi::class.java, "党政学习")
    }


    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/importExcel", method = [RequestMethod.POST])
    fun importExcel(request: HttpServletRequest, response: HttpServletResponse): Result<*> {
        return super.importExcel(request, response, DzXiexi::class.java)
    }
}
