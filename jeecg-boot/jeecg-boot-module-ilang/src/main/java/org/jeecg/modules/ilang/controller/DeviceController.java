package org.jeecg.modules.ilang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ilang.entity.Device;
import org.jeecg.modules.ilang.entity.DeviceChannel;
import org.jeecg.modules.ilang.entity.DeviceLog;
import org.jeecg.modules.ilang.service.IDeviceChannelService;
import org.jeecg.modules.ilang.service.IDeviceLogService;
import org.jeecg.modules.ilang.service.IDeviceService;
import org.jeecg.modules.ilang.vo.DevicePage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2020-12-24
 * @Version: V1.0
 */
@Api(tags="设备")
@RestController
@RequestMapping("/ilang/device")
@Slf4j
public class DeviceController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDeviceChannelService deviceChannelService;
	@Autowired
	private IDeviceLogService deviceLogService;

	/**
	 * 分页列表查询
	 *
	 * @param device
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "设备-分页列表查询")
	@ApiOperation(value="设备-分页列表查询", notes="设备-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Device device,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param devicePage
	 * @return
	 */
	@AutoLog(value = "设备-添加")
	@ApiOperation(value="设备-添加", notes="设备-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevicePage devicePage) {
		Device device = new Device();
		BeanUtils.copyProperties(devicePage, device);
		deviceService.saveMain(device, devicePage.getDeviceChannelList(),devicePage.getDeviceLogList());
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param devicePage
	 * @return
	 */
	@AutoLog(value = "设备-编辑")
	@ApiOperation(value="设备-编辑", notes="设备-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevicePage devicePage) {
		Device device = new Device();
		BeanUtils.copyProperties(devicePage, device);
		Device deviceEntity = deviceService.getById(device.getId());
		if(deviceEntity==null) {
			return Result.error("未找到对应数据");
		}
		deviceService.updateMain(device, devicePage.getDeviceChannelList(),devicePage.getDeviceLogList());
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备-通过id删除")
	@ApiOperation(value="设备-通过id删除", notes="设备-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		deviceService.delMain(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设备-批量删除")
	@ApiOperation(value="设备-批量删除", notes="设备-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.deviceService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备-通过id查询")
	@ApiOperation(value="设备-通过id查询", notes="设备-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Device device = deviceService.getById(id);
		if(device==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(device);

	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备通道通过主表ID查询")
	@ApiOperation(value="设备通道主表ID查询", notes="设备通道-通主表ID查询")
	@GetMapping(value = "/queryDeviceChannelByMainId")
	public Result<?> queryDeviceChannelListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DeviceChannel> deviceChannelList = deviceChannelService.selectByMainId(id);
		return Result.OK(deviceChannelList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备日志通过主表ID查询")
	@ApiOperation(value="设备日志主表ID查询", notes="设备日志-通主表ID查询")
	@GetMapping(value = "/queryDeviceLogByMainId")
	public Result<?> queryDeviceLogListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DeviceLog> deviceLogList = deviceLogService.selectByMainId(id);
		return Result.OK(deviceLogList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param device
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Device device) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Device> queryList = deviceService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Device> deviceList = new ArrayList<Device>();
      if(oConvertUtils.isEmpty(selections)) {
          deviceList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          deviceList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<DevicePage> pageList = new ArrayList<DevicePage>();
      for (Device main : deviceList) {
          DevicePage vo = new DevicePage();
          BeanUtils.copyProperties(main, vo);
          List<DeviceChannel> deviceChannelList = deviceChannelService.selectByMainId(main.getId());
          vo.setDeviceChannelList(deviceChannelList);
          List<DeviceLog> deviceLogList = deviceLogService.selectByMainId(main.getId());
          vo.setDeviceLogList(deviceLogList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "设备列表");
      mv.addObject(NormalExcelConstants.CLASS, DevicePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备数据", "导出人:"+sysUser.getRealname(), "设备"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<DevicePage> list = ExcelImportUtil.importExcel(file.getInputStream(), DevicePage.class, params);
              for (DevicePage page : list) {
                  Device po = new Device();
                  BeanUtils.copyProperties(page, po);
                  deviceService.saveMain(po, page.getDeviceChannelList(),page.getDeviceLogList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
