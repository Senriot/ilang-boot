package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.Song
import org.jeecg.modules.ilang.service.SongService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 歌曲
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Api(tags = ["歌曲"])
@RestController
@RequestMapping("/ilang/song")
class SongController(private val songService: SongService) : JeecgController<Song, SongService>()
{

    /**
     * 分页列表查询
     *
     * @param song
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "歌曲-分页列表查询")
    @ApiOperation(value = "歌曲-分页列表查询", notes = "歌曲-分页列表查询")
    @GetMapping(value = ["/list"])
    fun queryPageList(
        song: Song?,
        @RequestParam(name = "pageNo", defaultValue = "1")
        pageNo: Long = 1,
        @RequestParam(name = "pageSize", defaultValue = "10")
        pageSize: Long = 10,
        req: HttpServletRequest
    ): Result<*>
    {
        val queryWrapper: QueryWrapper<Song> = QueryGenerator.initQueryWrapper(song, req.parameterMap)
        val page = Page<Song>(pageNo, pageSize)
        val pageList = songService.page(page, queryWrapper)
        return Result.OK(pageList)
    }

    /**
     *   添加
     *
     * @param song
     * @return
     */
    @AutoLog(value = "歌曲-添加")
    @ApiOperation(value = "歌曲-添加", notes = "歌曲-添加")
    @PostMapping(value = ["/add"])
    fun add(@RequestBody song: Song): Result<*>
    {
        songService.save(song)
        return Result.OK("添加成功！")
    }

    /**
     *  编辑
     *
     * @param song
     * @return
     */
    @AutoLog(value = "歌曲-编辑")
    @ApiOperation(value = "歌曲-编辑", notes = "歌曲-编辑")
    @PutMapping("/edit")
    fun edit(@RequestBody song: Song): Result<*>
    {
        songService.updateById(song)
        return Result.OK("编辑成功!")
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "歌曲-通过id删除")
    @ApiOperation(value = "歌曲-通过id删除", notes = "歌曲-通过id删除")
    @DeleteMapping("/delete")
    fun delete(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        songService.removeById(id)
        return Result.OK("删除成功!")
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "歌曲-批量删除")
    @ApiOperation(value = "歌曲-批量删除", notes = "歌曲-批量删除")
    @DeleteMapping("/deleteBatch")
    fun deleteBatch(@RequestParam(name = "ids", required = true) ids: String): Result<*>
    {
        this.songService.removeByIds(ids.split(","))
        return Result.OK("批量删除成功!")
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "歌曲-通过id查询")
    @ApiOperation(value = "歌曲-通过id查询", notes = "歌曲-通过id查询")
    @GetMapping("/queryById")
    fun queryById(@RequestParam(name = "id", required = true) id: String): Result<*>
    {
        val song = songService.getById(id) ?: return Result.error("未找到对应数据")
        return Result.OK(song)
    }

    /**
     * 导出excel
     *
     * @param request
     * @param song
     */
    @RequestMapping("/exportXls")
    fun exportXls(request: HttpServletRequest, song: Song): ModelAndView?
    {
        return super.exportXls(request, song, Song::class.java, "歌曲")
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
        return super.importExcel(request, response, Song::class.java)
    }
}
