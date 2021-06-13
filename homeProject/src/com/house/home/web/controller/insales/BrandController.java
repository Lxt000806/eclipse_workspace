package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.Brand;
import com.house.home.service.insales.BrandService;

@Controller
@RequestMapping("/admin/brand")
public class BrandController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value = "/getBrandList")
	@ResponseBody
	public JSONObject getBrand(HttpServletRequest request){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("itemType2", request.getParameter("itemType2"));
		param.put("itemType1", request.getParameter("itemType1"));
		List<Map<String,Object>> brandList = this.brandService.findBrand(param);
		return this.out(brandList, true);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Brand brand) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		brandService.findPageBySql(page, brand);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 品牌管理分页查询
	 * @author	created by zb
	 * @date	2018-12-24--上午10:41:17
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBrandJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getBrandJqGrid(HttpServletRequest request,
			HttpServletResponse response,Brand brand) throws Exception {
		brand.setItemRight(getUserContext(request).getItemRight());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		brandService.findBrandPageBySql(page, brand);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * Brand列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Brand brand) throws Exception {

		Page<Map<String,Object>> page = this.newPage(request);
		brandService.findPageBySql(page, brand);

		return new ModelAndView("admin/insales/brand/brand_list")
			.addObject(CommonConstant.PAGE_KEY, page)
			.addObject("brand", brand);
	}
	
	/**
	 * 跳转到新增Brand页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, Brand brand){
		logger.debug("跳转到新增Brand页面");
		return new ModelAndView("admin/insales/brand/brand_add")
			.addObject("brand", brand);
	}
	/**
	 * 跳转到修改Brand页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Brand页面");
		Brand brand = null;
		if (StringUtils.isNotBlank(id)){
			brand = brandService.get(Brand.class, id);
		}else{
			brand = new Brand();
		}
		
		return new ModelAndView("admin/brand/brand_update")
			.addObject("brand", brand);
	}
	
	/**
	 * 跳转到查看Brand页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Brand页面");
		Brand brand = brandService.get(Brand.class, id);
		
		return new ModelAndView("admin/brand/brand_detail")
				.addObject("brand", brand);
	}
	/**
	 * 添加Brand
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Brand brand){
		logger.debug("添加Brand开始");
		try{
			Boolean exist = this.brandService.checkDescr(brand);
			if (!exist) {
				ServletUtils.outPrintFail(request, response, "此品牌名称["+ brand.getDescr().trim() +"]已经存在，不允许插入");
				return;
			}
			String str = brandService.getSeqNo("tBrand");
			brand.setCode(str);
			brand.setLastUpdate(new Date());
			brand.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			brand.setExpired("F");
			brand.setActionLog("ADD");
			this.brandService.save(brand);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加Brand失败");
		}
	}
	
	/**
	 * 修改Brand
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Brand brand){
		logger.debug("修改Brand开始");
		try{
			Boolean exist = this.brandService.checkDescr(brand);
			if (!exist) {
				ServletUtils.outPrintFail(request, response, "此品牌名称["+ brand.getDescr().trim() +"]已经存在，不允许插入");
				return;
			}
			brand.setLastUpdate(new Date());
			brand.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			brand.setActionLog("Edit");
			this.brandService.update(brand);
			ServletUtils.outPrintSuccess(request, response, "修改成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Brand失败");
		}
	}
	
	/**
	 * 删除Brand
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Brand开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Brand编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Brand brand = brandService.get(Brand.class, deleteId);
				if(brand == null)
					continue;
				brand.setExpired("T");
				brandService.update(brand);
			}
		}
		logger.debug("删除Brand IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Brand导出Excel
	 * modify by zb on 20181224
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Brand brand){
		brand.setItemRight(getUserContext(request).getItemRight());
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		brandService.findBrandPageBySql(page, brand);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"品牌信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * brand查询code
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,Brand brand){
		return new ModelAndView("admin/insales/brand/brand_code").addObject("brand",brand);
	}
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBrand")
	@ResponseBody
	public JSONObject getBrand(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Brand brand = brandService.get(Brand.class, id);
		if(brand== null){
			return this.out("系统中不存在code="+id+"品牌信息", false);
		}
		return this.out(brand, true);
	}
}
