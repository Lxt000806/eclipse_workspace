package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.IntMeasureBrand;
import com.house.home.service.basic.IntMeasureBrandService;

@Controller
@RequestMapping("/admin/intMeasureBrand")
public class IntMeasureBrandController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntMeasureBrandController.class);

	@Autowired
	private IntMeasureBrandService intMeasureBrandService;

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
			HttpServletResponse response, IntMeasureBrand intMeasureBrand) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intMeasureBrandService.findPageBySql(page, intMeasureBrand);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntMeasureBrand列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/intMeasureBrand/intMeasureBrand_list");
	}
	/**
	 * IntMeasureBrand查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/intMeasureBrand/intMeasureBrand_code");
	}
	/**
	 * 跳转到新增IntMeasureBrand页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增IntMeasureBrand页面");
		IntMeasureBrand intMeasureBrand = null;
		if (StringUtils.isNotBlank(id)){
			intMeasureBrand = intMeasureBrandService.get(IntMeasureBrand.class, id);
			intMeasureBrand.setCode(null);
		}else{
			intMeasureBrand = new IntMeasureBrand();
		}
		
		return new ModelAndView("admin/basic/intMeasureBrand/intMeasureBrand_save")
			.addObject("intMeasureBrand", intMeasureBrand);
	}
	/**
	 * 跳转到修改IntMeasureBrand页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改IntMeasureBrand页面");
		IntMeasureBrand intMeasureBrand = null;
		if (StringUtils.isNotBlank(id)){
			intMeasureBrand = intMeasureBrandService.get(IntMeasureBrand.class, id);
		}else{
			intMeasureBrand = new IntMeasureBrand();
		}
		
		return new ModelAndView("admin/basic/intMeasureBrand/intMeasureBrand_update")
			.addObject("intMeasureBrand", intMeasureBrand);
	}
	
	/**
	 * 跳转到查看IntMeasureBrand页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看IntMeasureBrand页面");
		IntMeasureBrand intMeasureBrand = intMeasureBrandService.get(IntMeasureBrand.class, id);
		
		return new ModelAndView("admin/basic/intMeasureBrand/intMeasureBrand_detail")
				.addObject("intMeasureBrand", intMeasureBrand);
	}
	/**
	 * 添加IntMeasureBrand
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, IntMeasureBrand intMeasureBrand){
		logger.debug("添加IntMeasureBrand开始");
		try{
			String str = intMeasureBrandService.getSeqNo("tIntMeasureBrand");
			intMeasureBrand.setCode(str);
			intMeasureBrand.setLastUpdate(new Date());
			intMeasureBrand.setLastUpdatedBy(getUserContext(request).getCzybh());
			intMeasureBrand.setExpired("F");
			this.intMeasureBrandService.save(intMeasureBrand);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加IntMeasureBrand失败");
		}
	}
	
	/**
	 * 修改IntMeasureBrand
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, IntMeasureBrand intMeasureBrand){
		logger.debug("修改IntMeasureBrand开始");
		try{
			intMeasureBrand.setLastUpdate(new Date());
			intMeasureBrand.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intMeasureBrandService.update(intMeasureBrand);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改IntMeasureBrand失败");
		}
	}
	
	/**
	 * 删除IntMeasureBrand
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntMeasureBrand开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntMeasureBrand编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntMeasureBrand intMeasureBrand = intMeasureBrandService.get(IntMeasureBrand.class, deleteId);
				if(intMeasureBrand == null)
					continue;
				intMeasureBrand.setExpired("T");
				intMeasureBrandService.update(intMeasureBrand);
			}
		}
		logger.debug("删除IntMeasureBrand IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *IntMeasureBrand导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntMeasureBrand intMeasureBrand){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intMeasureBrandService.findPageBySql(page, intMeasureBrand);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"IntMeasureBrand_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
