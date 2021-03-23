package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.Artist
import org.jeecg.modules.ilang.service.ArtistService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 歌星
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Api(tags = ["歌星"])
@RestController
@RequestMapping("/ilang/artist")
class ArtistController(private val artistService: ArtistService) : JeecgController<Artist, ArtistService>()
{

    /**
     * 分页列表查询
     *
     * @param artist
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "歌星-分页列表查询")
    @ApiOperation(value = "歌星-分页列表查询", notes = "歌星-分页列表查询")
    @GetMapping(value = ["/list"])
    fun queryPageList(
        artist: Artist?,
        @RequestParam(name = "pageNo", defaultValue = "1")
        pageNo: Long = 1,
        @RequestParam(name = "pageSize", defaultValue = "10")
        pageSize: Long = 10,
        req: HttpServletRequest
    ): Result<*>
    {
        val queryWrapper: QueryWrapper<Artist> = QueryGenerator.initQueryWrapper(artist, req.parameterMap)
        val page = Page<Artist>(pageNo, pageSize)
        val pageList = artistService.page(page, queryWrapper)
        return Result.OK(pageList)
    }

    /**
     *   添加
     *
     * @param artist
     * @return
     */
    @AutoLog(value = "歌星-添加")
    @ApiOperation(value = "歌星-添加", notes = "歌星-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody artist: Artist): Result<*>
    {
        artistService.save(artist)
        return Result.OK("添加成功！")
    }

    /**
     *  编辑
     *
     * @param artist
     * @return
     */
    @AutoLog(value = "歌星-编辑")
    @ApiOperation(value = "歌星-编辑", notes = "歌星-编辑")
    @PutMapping("/edit")
    fun edit(@RequestBody artist: Artist): Result<*>
    {
        artistService.updateById(artist)
        return Result.OK("编辑成功!")
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "歌星-通过id删除")
    @ApiOperation(value = "歌星-通过id删除", notes = "歌星-通过id删除")
    @DeleteMapping("/delete")
    fun delete(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        artistService.removeById(id)
        return Result.OK("删除成功!")
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "歌星-批量删除")
    @ApiOperation(value = "歌星-批量删除", notes = "歌星-批量删除")
    @DeleteMapping("/deleteBatch")
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*>
    {
        this.artistService.removeByIds(ids.split(","))
        return Result.OK("批量删除成功!")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "歌星-通过id查询")
    @ApiOperation(value = "歌星-通过id查询", notes = "歌星-通过id查询")
    @GetMapping("/queryById")
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        val artist = artistService.getById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(artist)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param artist
     */
    @RequestMapping("/exportXls")
    fun exportXls(request: HttpServletRequest, artist: Artist): ModelAndView?
    {
        return super.exportXls(request, artist, Artist::class.java, "歌星")
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
        return super.importExcel(request, response, Artist::class.java)
    }
}
