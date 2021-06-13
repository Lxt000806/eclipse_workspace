
package com.house.home.web.controller.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.service.basic.BuilderService;

@Controller
@RequestMapping("/admin/builder")
public class BuilderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BuilderController.class);

	@Autowired
	private BuilderService builderService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Builder builder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderService.findPageBySql(page, builder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 专盘信息表
	 * 
	 * */
	@RequestMapping("/goSpcBuilderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSpcBuilderJqGrid(HttpServletRequest request,
			HttpServletResponse response,Builder builder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderService.findSpcBuilderPageBySql(page, builder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSpcbuilderAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSpcbuilderAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,Builder builder,String arr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderService.findSpcBuilderAddPageBySql(page, builder,arr);
		return new WebPage<Map<String,Object>>(page);
	} 
	
	/**
	 * 根据ID查询项目名称详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBuilder")
	@ResponseBody
	public JSONObject getBuilder(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Builder builder = builderService.get(Builder.class, id);
		if(builder == null){
			return this.out("系统中不存在code="+id+"的项目名称信息", false);
		}
		return this.out(builder, true);
	}
	/**
	 * 项目名称列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/builder/builder_list");
	}
	
	/**
	 * 项目名称Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Builder builder) throws Exception {

		return new ModelAndView("admin/basic/builder/builder_code");
	}
	
	/**
	 * 跳转到新增项目名称页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增项目名称页面");
		Builder builder = new Builder();
		
		return new ModelAndView("admin/basic/builder/builder_save")
			.addObject("builder", builder);
	}
	/**
	 * 跳转到修改项目名称页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改项目名称页面");
		Map<String, Object> map=builderService.findByCode(id);
		//经度和纬度都不为空时合并经纬度
		map.put("tude",map.get("Longitude")!=null&&map.get("Latitude")!=null?map.get("Longitude").toString()+"|"+map.get("Latitude").toString():"");
		return new ModelAndView("admin/basic/builder/builder_update")
			.addObject("builder", map);
	}
	
	/**
	 * 跳转到查看项目名称页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看项目名称页面");
		Map<String, Object> map=builderService.findByCode(id);
		//经度和纬度都不为空时合并经纬度
		map.put("tude",map.get("Longitude")!=null&&map.get("Latitude")!=null?map.get("Longitude").toString()+"|"+map.get("Latitude").toString():"");
		return new ModelAndView("admin/basic/builder/builder_detail")
			.addObject("builder", map);
	}
	/**
	 * 跳转到复制项目名称页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看项目名称页面");
		Map<String, Object> map=builderService.findByCode(id);
		//经度和纬度都不为空时合并经纬度
		map.put("tude",map.get("Longitude")!=null&&map.get("Latitude")!=null?map.get("Longitude").toString()+"|"+map.get("Latitude").toString():"");
		return new ModelAndView("admin/basic/builder/builder_copy")
			.addObject("builder", map);
	}
	/**
	 * 添加项目名称
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Builder builder){
		logger.debug("添加 项目名称开始");
		try{
			Builder xt = this.builderService.getByDescr(builder.getDescr());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "项目名称重复");
				return;
			}
			if(builder.getOffset()==null)builder.setOffset(0);
			if(builder.getHouseNum()==null)builder.setHouseNum(0);
			if(StringUtils.isBlank(builder.getIsPrjSpc()))builder.setIsPrjSpc("0");
			//经纬度不为空时拆分，并分别赋值
			if(StringUtils.isNotBlank(builder.getTude())){
				String[]array=builder.getTude().split("\\|");
				builder.setLongitude(Double.parseDouble(array[0]));
				builder.setLatitude(Double.parseDouble(array[1]));
			}
			String str = builderService.getSeqNo("tBuilder");//获取流水号
			builder.setCode(str);
			builder.setLastUpdate(new Date());
			builder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			builder.setExpired("F");
			this.builderService.save(builder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加项目名称失败");
		}
	}
	/**
	 * 跳转到交房批次管理页面
	 * @return
	 */
	@RequestMapping("/goHouseManage")
	public ModelAndView goHouseManage(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到交房批次管理页面");
		Map<String, Object> map=builderService.findByCode(id);
		return new ModelAndView("admin/basic/builder/builder_houseManage")
			.addObject("builder", map);
	}
	/**
	 * 跳转到工程信息编辑页面
	 * @return
	 */
	@RequestMapping("/goInfoUpdate")
	public ModelAndView goInfoUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到工程信息编辑页面");
		Map<String, Object> map=builderService.findByCode(id);
		map.put("tude",map.get("Longitude")!=null&&map.get("Latitude")!=null?map.get("Longitude").toString()+"|"+map.get("Latitude").toString():"");
		return new ModelAndView("admin/basic/builder/builder_infoUpdate")
			.addObject("builder", map);
	}
	/**
	 * 跳转到配送区域编辑页面
	 * @return
	 */
	@RequestMapping("/goRegionUpdate")
	public ModelAndView goRegionUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到配送区域编辑页面");
		Map<String, Object> map=builderService.findByCode(id);
		return new ModelAndView("admin/basic/builder/builder_regionUpdate")
			.addObject("builder", map);
	}
	/**
	 * 修改项目名称
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Builder builder){
		logger.debug("修改项目名称开始");
		
		try{
				if (!"T".equals(builder.getExpired())) {
					builder.setExpired("F");
				}
				if(builder.getOffset()==null)builder.setOffset(0);
				//经纬度不为空时拆分，并分别赋值
				if(StringUtils.isNotBlank(builder.getTude())){
					String[]array=builder.getTude().split("\\|");
					builder.setLongitude(Double.parseDouble(array[0]));
					builder.setLatitude(Double.parseDouble(array[1]));
				}
				builder.setLastUpdate(new Date());
				builder.setActionLog("EDIT");
				builder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				this.builderService.update(builder);
				ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改项目名称失败");
		}
	}
	
	/**
	 * 项目名称导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Builder builder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		builderService.findPageBySql(page, builder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"项目名称管理信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/checkBlur")
	public void checkBlur(HttpServletRequest request, HttpServletResponse response, String id, String info){
		logger.debug("check项目名称开始");
		try{
			if (StringUtils.isNotBlank(id)){
				Builder builder = builderService.get(Builder.class, id);
				if (builder!=null){
					JSONObject obj = new JSONObject();
					obj.put("descr", builder.getDescr());
					ServletUtils.outPrintSuccess(request, response, obj);
				}else{
					if (StringUtils.isBlank(info)){
						info = "信息";
					}
					ServletUtils.outPrintFail(request, response, "该"+info+"不存在，请重新输入");
				}
			}else{
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "获取项目名称失败");
		}
	}
	
	/**
	 * 一二级区域联动
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/regionCodeByAuthority/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getWorkTypeByAuthority(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = this.builderService.findRegionCodeByAuthority(type, pCode, uc);
		return this.out(regionList, true);
	}
	
	/**
	 * 百度地图选择经纬度
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLatitude")
	public ModelAndView goLatitude(HttpServletRequest request,
			HttpServletResponse response,Builder builder) throws Exception {
		//经纬度不为空时拆分，并分别赋值
		if(StringUtils.isNotBlank(builder.getTude())){
			String[]array=builder.getTude().split("\\|");
			builder.setLongitude(Double.parseDouble(array[0]));
			builder.setLatitude(Double.parseDouble(array[1]));
		}
		return new ModelAndView("admin/basic/builder/builder_latitude").addObject("builder", builder);
	}
	/**
	 * 工程信息编辑
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doInfoUpdate")
	public void doInfoUpdate(HttpServletRequest request, HttpServletResponse response, Builder builder){
		logger.debug("工程信息编辑");
		if(builder.getOffset()==null)builder.setOffset(0);
		//经纬度不为空时拆分，并分别赋值
		if(StringUtils.isNotBlank(builder.getTude())){
			String[]array=builder.getTude().split("\\|");
			builder.setLongitude(Double.parseDouble(array[0]));
			builder.setLatitude(Double.parseDouble(array[1]));
		}
		if("0".equals(builder.getIsPrjSpc())){
			builder.setPrjLeader("");
		}
		builder.setLastUpdatedBy(getUserContext(request).getCzybh());
		try{
			builderService.infoUpdate(builder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改项目名称失败");
		}
	}
	/**
	 * 配送区域编辑
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRegionUpdate")
	public void doRegionUpdate(HttpServletRequest request, HttpServletResponse response, Builder builder){
		logger.debug("配送区域编辑");
		builder.setLastUpdatedBy(getUserContext(request).getCzybh());
		try{
			builderService.regionUpdate(builder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改项目名称失败");
		}
	}
	/**
	 * 根据二级区域查工程大区
	 * @param request
	 * @param regionCode2
	 * @return
	 */
	@RequestMapping("/findPrjRegion") 
	@ResponseBody
	public List<Map<String,Object>> findPrjRegion(HttpServletRequest request,String regionCode2){
		List<Map<String,Object>> list = this.builderService.findPrjRegion(regionCode2);
		return list;
	}
	
}
