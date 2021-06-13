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
import com.house.home.entity.basic.IntSplPerfPer;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.IntSplPerfPerService;

@Controller
@RequestMapping("/admin/intSplPerfPer")
public class IntSplPerfPerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntSplPerfPerController.class);

	@Autowired
	private IntSplPerfPerService intSplPerfPerService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, IntSplPerfPer intSplPerfPer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intSplPerfPerService.findPageBySql(page, intSplPerfPer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntSplPerfPer列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/intSplPerfPer/intSplPerfPer_list");
	}
	/**
	 * IntSplPerfPer查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/intSplPerfPer/intSplPerfPer_code");
	}
	/**
	 * 跳转到新增IntSplPerfPer页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增IntSplPerfPer页面");
		IntSplPerfPer intSplPerfPer = null;
		if (StringUtils.isNotBlank(id)){
			intSplPerfPer = intSplPerfPerService.get(IntSplPerfPer.class, Integer.parseInt(id));
			intSplPerfPer.setPk(null);
		}else{
			intSplPerfPer = new IntSplPerfPer();
		}
		
		return new ModelAndView("admin/basic/intSplPerfPer/intSplPerfPer_save")
			.addObject("intSplPerfPer", intSplPerfPer);
	}
	/**
	 * 跳转到修改IntSplPerfPer页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改IntSplPerfPer页面");
		IntSplPerfPer intSplPerfPer = null;
		if (StringUtils.isNotBlank(id)){
			intSplPerfPer = intSplPerfPerService.get(IntSplPerfPer.class, Integer.parseInt(id));
		}else{
			intSplPerfPer = new IntSplPerfPer();
		}
		Supplier supplier=intSplPerfPerService.get(Supplier.class,intSplPerfPer.getSupplCode());
		intSplPerfPer.setSupplDescr(supplier.getDescr());
		return new ModelAndView("admin/basic/intSplPerfPer/intSplPerfPer_update")
			.addObject("intSplPerfPer", intSplPerfPer);
	}
	
	/**
	 * 跳转到查看IntSplPerfPer页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看IntSplPerfPer页面");
		IntSplPerfPer intSplPerfPer = intSplPerfPerService.get(IntSplPerfPer.class, Integer.parseInt(id));
		intSplPerfPer.setM_umState("V");
		Supplier supplier=intSplPerfPerService.get(Supplier.class,intSplPerfPer.getSupplCode());
		intSplPerfPer.setSupplDescr(supplier.getDescr());
		return new ModelAndView("admin/basic/intSplPerfPer/intSplPerfPer_update")
				.addObject("intSplPerfPer", intSplPerfPer);
	}
	/**
	 * 添加IntSplPerfPer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, IntSplPerfPer intSplPerfPer){
		logger.debug("添加IntSplPerfPer开始");
		try{
			intSplPerfPer.setExpired("F");
			intSplPerfPer.setActionLog("ADD");
			intSplPerfPer.setLastUpdate(new Date());
			intSplPerfPer.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intSplPerfPerService.save(intSplPerfPer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "供应商重复！");
		}
	}
	
	/**
	 * 修改IntSplPerfPer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, IntSplPerfPer intSplPerfPer){
		logger.debug("修改IntSplPerfPer开始");
		try{
			intSplPerfPer.setActionLog("EDIT");
			intSplPerfPer.setLastUpdate(new Date());
			intSplPerfPer.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intSplPerfPerService.update(intSplPerfPer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "供应商重复！");
		}
	}
	
	/**
	 * 删除IntSplPerfPer
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntSplPerfPer开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntSplPerfPer编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntSplPerfPer intSplPerfPer = intSplPerfPerService.get(IntSplPerfPer.class, Integer.parseInt(deleteId));
				if(intSplPerfPer == null)
					continue;
				intSplPerfPerService.delete(intSplPerfPer);
			}
		}
		logger.debug("删除IntSplPerfPer IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *IntSplPerfPer导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntSplPerfPer intSplPerfPer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intSplPerfPerService.findPageBySql(page, intSplPerfPer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"IntSplPerfPer_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
