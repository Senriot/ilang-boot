package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.LangDu
import org.jeecg.modules.ilang.service.LangDuService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 朗读内容
 * @Author: Allen
 * @Date:   2020-12-22
 * @Version: V1.0
 */
@Api(tags=["朗读内容"])
@RestController
@RequestMapping("/ilang/langDu")
class LangDuController(private val langDuService: LangDuService) : JeecgController<LangDu, LangDuService>() {

	/**
	* 分页列表查询
	*
	* @param langDu
	* @param pageNo
	* @param pageSize
	* @param req
	* @return
	*/
	@AutoLog(value = "朗读内容-分页列表查询")
	@ApiOperation(value="朗读内容-分页列表查询", notes="朗读内容-分页列表查询")
	@GetMapping(value = ["/list"])
	fun queryPageList( langDu: LangDu?,
						@RequestParam(name="pageNo", defaultValue="1")
						pageNo: Long = 1,
						@RequestParam(name="pageSize", defaultValue="10")
						pageSize: Long = 10,
 						req: HttpServletRequest): Result<*>
	{
		val queryWrapper: QueryWrapper<LangDu> = QueryGenerator.initQueryWrapper(langDu, req.parameterMap)
		val page = Page<LangDu>(pageNo, pageSize)
		val pageList = langDuService.page(page, queryWrapper)
		return Result.OK(pageList)
	}

	/**
	*   添加
	*
	* @param langDu
	* @return
	*/
	@AutoLog(value = "朗读内容-添加")
	@ApiOperation(value="朗读内容-添加", notes="朗读内容-添加")
	@PostMapping(value = ["/add"])
	fun add(@RequestBody langDu: LangDu): Result<*>
	{
		langDuService.save(langDu)
		return Result.OK("添加成功！")
	}

	/**
	*  编辑
	*
	* @param langDu
	* @return
	*/
	@AutoLog(value = "朗读内容-编辑")
	@ApiOperation(value="朗读内容-编辑", notes="朗读内容-编辑")
	@PutMapping("/edit")
	fun edit(@RequestBody langDu:LangDu ):Result<*>
	{
		langDuService.updateById(langDu)
		return Result.OK("编辑成功!")
	}

	/**
	*   通过id删除
	*
	* @param id
	* @return
	*/
	@AutoLog(value = "朗读内容-通过id删除")
	@ApiOperation(value="朗读内容-通过id删除", notes="朗读内容-通过id删除")
	@DeleteMapping("/delete")
	fun delete(@RequestParam(name="id",required=true) id: String ):Result<*>
	{
		langDuService.removeById(id)
		return Result.OK("删除成功!")
	}

	/**
	*  批量删除
	*
	* @param ids
	* @return
	*/
	@AutoLog(value = "朗读内容-批量删除")
	@ApiOperation(value="朗读内容-批量删除", notes="朗读内容-批量删除")
	@DeleteMapping("/deleteBatch")
	fun deleteBatch(@RequestParam(name="ids",required=true) ids: String ):Result<*>
	{
		this.langDuService.removeByIds(ids.split(","))
		return Result.OK("批量删除成功!")
	}

	/**
	* 通过id查询
	*
	* @param id
	* @return
	*/
	@AutoLog(value = "朗读内容-通过id查询")
	@ApiOperation(value="朗读内容-通过id查询", notes="朗读内容-通过id查询")
	@GetMapping("/queryById")
	fun queryById(@RequestParam(name="id",required=true) id: String ):Result<*>
	{
		val langDu = langDuService.getById(id) ?: return Result.error("未找到对应数据")
		return Result.OK(langDu)
	}

	/**
	* 导出excel
	*
	* @param request
	* @param langDu
	*/
	@RequestMapping("/exportXls")
	fun exportXls(request: HttpServletRequest ,langDu: LangDu ):ModelAndView?
	{
		return super.exportXls(request, langDu, LangDu::class.java, "朗读内容")
	}


	/**
	* 通过excel导入数据
	*
	* @param request
	* @param response
	* @return
	*/
	@RequestMapping("/importExcel", method = [RequestMethod.POST])
	fun importExcel(request:HttpServletRequest , response:HttpServletResponse ):Result<*>
	{
		return super.importExcel(request, response, LangDu::class.java)
	}
}
