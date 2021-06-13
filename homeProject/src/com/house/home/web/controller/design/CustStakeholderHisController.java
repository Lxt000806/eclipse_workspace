package com.house.home.web.controller.design;

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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.CustStakeholderHis;
import com.house.home.service.design.CustStakeholderHisService;

@Controller
@RequestMapping("/admin/custStakeholderHis")
public class CustStakeholderHisController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustStakeholderHisController.class);

	@Autowired
	private CustStakeholderHisService custStakeholderHisService;

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
			HttpServletResponse response, CustStakeholderHis custStakeholderHis) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custStakeholderHisService.findPageBySql(page, custStakeholderHis);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustStakeholderHis列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custStakeholderHis/custStakeholderHis_list");
	}
	/**
	 * CustStakeholderHis查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custStakeholderHis/custStakeholderHis_code");
	}
	/**
	 * 跳转到新增CustStakeholderHis页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustStakeholderHis页面");
		CustStakeholderHis custStakeholderHis = null;
		if (StringUtils.isNotBlank(id)){
			custStakeholderHis = custStakeholderHisService.get(CustStakeholderHis.class, Integer.parseInt(id));
			custStakeholderHis.setPk(null);
		}else{
			custStakeholderHis = new CustStakeholderHis();
		}
		
		return new ModelAndView("admin/design/custStakeholderHis/custStakeholderHis_save")
			.addObject("custStakeholderHis", custStakeholderHis);
	}
	/**
	 * 跳转到修改CustStakeholderHis页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustStakeholderHis页面");
		CustStakeholderHis custStakeholderHis = null;
		if (StringUtils.isNotBlank(id)){
			custStakeholderHis = custStakeholderHisService.get(CustStakeholderHis.class, Integer.parseInt(id));
		}else{
			custStakeholderHis = new CustStakeholderHis();
		}
		
		return new ModelAndView("admin/design/custStakeholderHis/custStakeholderHis_update")
			.addObject("custStakeholderHis", custStakeholderHis);
	}
	
	/**
	 * 跳转到查看CustStakeholderHis页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustStakeholderHis页面");
		CustStakeholderHis custStakeholderHis = custStakeholderHisService.get(CustStakeholderHis.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/custStakeholderHis/custStakeholderHis_detail")
				.addObject("custStakeholderHis", custStakeholderHis);
	}
	/**
	 * 添加CustStakeholderHis
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustStakeholderHis custStakeholderHis){
		logger.debug("添加CustStakeholderHis开始");
		try{
			this.custStakeholderHisService.save(custStakeholderHis);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustStakeholderHis失败");
		}
	}
	
	/**
	 * 修改CustStakeholderHis
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustStakeholderHis custStakeholderHis){
		logger.debug("修改CustStakeholderHis开始");
		try{
			custStakeholderHis.setLastUpdate(new Date());
			custStakeholderHis.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custStakeholderHisService.update(custStakeholderHis);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustStakeholderHis失败");
		}
	}
	
	/**
	 * 删除CustStakeholderHis
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustStakeholderHis开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustStakeholderHis编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustStakeholderHis custStakeholderHis = custStakeholderHisService.get(CustStakeholderHis.class, Integer.parseInt(deleteId));
				if(custStakeholderHis == null)
					continue;
				custStakeholderHis.setExpired("T");
				custStakeholderHisService.update(custStakeholderHis);
			}
		}
		logger.debug("删除CustStakeholderHis IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CustStakeholderHis导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustStakeholderHis custStakeholderHis){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custStakeholderHisService.findPageBySql(page, custStakeholderHis);
	}

}
