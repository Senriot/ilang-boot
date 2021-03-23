package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jeecg.common.api.vo.Result
import org.jeecg.common.aspect.annotation.AutoLog
import org.jeecg.common.system.base.controller.JeecgController
import org.jeecg.common.system.query.QueryGenerator
import org.jeecg.modules.ilang.entity.DeviceProduce
import org.jeecg.modules.ilang.service.DeviceProduceService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Description: 设备类别
 * @Author: Allen
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Api(tags=["设备类别"])
@RestController
@RequestMapping("/ilang/deviceProduce")
class DeviceProduceController(private val deviceProduceService: DeviceProduceService) : JeecgController<DeviceProduce, DeviceProduceService>() {

	/**
	* 分页列表查询
	*
	* @param deviceProduce
	* @param pageNo
	* @param pageSize
	* @param req
	* @return
	*/
	@AutoLog(value = "设备类别-分页列表查询")
	@ApiOperation(value="设备类别-分页列表查询", notes="设备类别-分页列表查询")
	@GetMapping(value = ["/list"])
	fun queryPageList( deviceProduce: DeviceProduce?,
						@RequestParam(name="pageNo", defaultValue="1")
						pageNo: Long = 1,
						@RequestParam(name="pageSize", defaultValue="10")
						pageSize: Long = 10,
 						req: HttpServletRequest): Result<*>
	{
		val queryWrapper: QueryWrapper<DeviceProduce> = QueryGenerator.initQueryWrapper(deviceProduce, req.parameterMap)
		val page = Page<DeviceProduce>(pageNo, pageSize)
		val pageList = deviceProduceService.page(page, queryWrapper)
		return Result.OK(pageList)
	}

	/**
	*   添加
	*
	* @param deviceProduce
	* @return
	*/
	@AutoLog(value = "设备类别-添加")
	@ApiOperation(value="设备类别-添加", notes="设备类别-添加")
	@PostMapping(value = ["/add"])
	fun add(@RequestBody deviceProduce: DeviceProduce): Result<*>
	{
		deviceProduceService.save(deviceProduce)
		return Result.OK("添加成功！")
	}

	/**
	*  编辑
	*
	* @param deviceProduce
	* @return
	*/
	@AutoLog(value = "设备类别-编辑")
	@ApiOperation(value="设备类别-编辑", notes="设备类别-编辑")
	@PutMapping("/edit")
	fun edit(@RequestBody deviceProduce:DeviceProduce ):Result<*>
	{
		deviceProduceService.updateById(deviceProduce)
		return Result.OK("编辑成功!")
	}

	/**
	*   通过id删除
	*
	* @param id
	* @return
	*/
	@AutoLog(value = "设备类别-通过id删除")
	@ApiOperation(value="设备类别-通过id删除", notes="设备类别-通过id删除")
	@DeleteMapping("/delete")
	fun delete(@RequestParam(name="id",required=true) id: String ):Result<*>
	{
		deviceProduceService.removeById(id)
		return Result.OK("删除成功!")
	}

	/**
	*  批量删除
	*
	* @param ids
	* @return
	*/
	@AutoLog(value = "设备类别-批量删除")
	@ApiOperation(value="设备类别-批量删除", notes="设备类别-批量删除")
	@DeleteMapping("/deleteBatch")
	fun deleteBatch(@RequestParam(name="ids",required=true) ids: String ):Result<*>
	{
		this.deviceProduceService.removeByIds(ids.split(","))
		return Result.OK("批量删除成功!")
	}

	/**
	* 通过id查询
	*
	* @param id
	* @return
	*/
	@AutoLog(value = "设备类别-通过id查询")
	@ApiOperation(value="设备类别-通过id查询", notes="设备类别-通过id查询")
	@GetMapping("/queryById")
	fun queryById(@RequestParam(name="id",required=true) id: String ):Result<*>
	{
		val deviceProduce = deviceProduceService.getById(id) ?: return Result.error("未找到对应数据")
		return Result.OK(deviceProduce)
	}

	/**
	* 导出excel
	*
	* @param request
	* @param deviceProduce
	*/
	@RequestMapping("/exportXls")
	fun exportXls(request: HttpServletRequest ,deviceProduce: DeviceProduce ):ModelAndView?
	{
		return super.exportXls(request, deviceProduce, DeviceProduce::class.java, "设备类别")
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
		return super.importExcel(request, response, DeviceProduce::class.java)
	}
}
