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
import com.house.home.entity.basic.CstrDayStd;
import com.house.home.service.basic.CstrDayStdService;

@Controller
@RequestMapping("/admin/cstrDayStd")
public class CstrDayStdController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CstrDayStdController.class);

	@Autowired
	private CstrDayStdService cstrDayStdService;

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
			HttpServletResponse response, CstrDayStd cstrDayStd) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cstrDayStdService.findPageBySql(page, cstrDayStd);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CstrDayStd列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/cstrDayStd/cstrDayStd_list");
	}
	/**
	 * CstrDayStd查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/cstrDayStd/cstrDayStd_code");
	}
	/**
	 * 跳转到新增CstrDayStd页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CstrDayStd页面");
		CstrDayStd cstrDayStd = null;
		if (StringUtils.isNotBlank(id)){
			cstrDayStd = cstrDayStdService.get(CstrDayStd.class, Integer.parseInt(id));
			cstrDayStd.setPk(null);
		}else{
			cstrDayStd = new CstrDayStd();
		}
		
		return new ModelAndView("admin/basic/cstrDayStd/cstrDayStd_save")
			.addObject("cstrDayStd", cstrDayStd);
	}
	/**
	 * 跳转到修改CstrDayStd页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CstrDayStd页面");
		CstrDayStd cstrDayStd = null;
		if (StringUtils.isNotBlank(id)){
			cstrDayStd = cstrDayStdService.get(CstrDayStd.class, Integer.parseInt(id));
		}else{
			cstrDayStd = new CstrDayStd();
		}
		
		return new ModelAndView("admin/basic/cstrDayStd/cstrDayStd_update")
			.addObject("cstrDayStd", cstrDayStd);
	}
	
	/**
	 * 跳转到查看CstrDayStd页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CstrDayStd页面");
		CstrDayStd cstrDayStd = cstrDayStdService.get(CstrDayStd.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/cstrDayStd/cstrDayStd_view")
				.addObject("cstrDayStd", cstrDayStd);
	}
	/**
	 * 添加CstrDayStd
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CstrDayStd cstrDayStd){
		logger.debug("添加CstrDayStd开始");
		try{
			cstrDayStd.setLastUpdate(new Date());
			cstrDayStd.setLastUpdatedBy(getUserContext(request).getCzybh());
			cstrDayStd.setExpired("F");
			cstrDayStd.setActionLog("ADD");
			this.cstrDayStdService.save(cstrDayStd);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CstrDayStd失败");
		}
	}
	
	/**
	 * 修改CstrDayStd
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CstrDayStd cstrDayStd){
		logger.debug("修改CstrDayStd开始");
		try{
			if (!"T".equals(cstrDayStd.getExpired())) {
				cstrDayStd.setExpired("F");
			}
			cstrDayStd.setLastUpdate(new Date());
			cstrDayStd.setLastUpdatedBy(getUserContext(request).getCzybh());
			cstrDayStd.setActionLog("EDIT");
			this.cstrDayStdService.update(cstrDayStd);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改CstrDayStd失败");
		}
	}
	
	/**
	 * 删除CstrDayStd
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CstrDayStd开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CstrDayStd编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CstrDayStd cstrDayStd = cstrDayStdService.get(CstrDayStd.class, Integer.parseInt(deleteId));
				if(cstrDayStd == null)
					continue;
				cstrDayStdService.delete(cstrDayStd);
			}
		}
		logger.debug("删除CstrDayStd IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CstrDayStd导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CstrDayStd cstrDayStd){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cstrDayStdService.findPageBySql(page, cstrDayStd);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工工期配置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
