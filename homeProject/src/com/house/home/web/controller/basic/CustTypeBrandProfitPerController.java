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
import com.house.home.entity.basic.CustTypeBrandProfitPer;
import com.house.home.entity.insales.Brand;
import com.house.home.service.basic.CustTypeBrandProfitPerService;

@Controller
@RequestMapping("/admin/custTypeBrandProfitPer")
public class CustTypeBrandProfitPerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustTypeBrandProfitPerController.class);

	@Autowired
	private CustTypeBrandProfitPerService custTypeBrandProfitPerService;

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
			HttpServletResponse response, CustTypeBrandProfitPer custTypeBrandProfitPer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypeBrandProfitPerService.findPageBySql(page, custTypeBrandProfitPer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustTypeBrandProfitPer列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypeBrandProfitPer/custTypeBrandProfitPer_list");
	}
	/**
	 * CustTypeBrandProfitPer查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypeBrandProfitPer/custTypeBrandProfitPer_code");
	}
	/**
	 * 跳转到新增CustTypeBrandProfitPer页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustTypeBrandProfitPer页面");
		CustTypeBrandProfitPer custTypeBrandProfitPer = null;
		if (StringUtils.isNotBlank(id)){
			custTypeBrandProfitPer = custTypeBrandProfitPerService.get(CustTypeBrandProfitPer.class, Integer.parseInt(id));
			custTypeBrandProfitPer.setPk(null);
		}else{
			custTypeBrandProfitPer = new CustTypeBrandProfitPer();
		}
		
		return new ModelAndView("admin/basic/custTypeBrandProfitPer/custTypeBrandProfitPer_save")
			.addObject("custTypeBrandProfitPer", custTypeBrandProfitPer);
	}
	/**
	 * 跳转到修改CustTypeBrandProfitPer页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustTypeBrandProfitPer页面");
		CustTypeBrandProfitPer custTypeBrandProfitPer = null;
		if (StringUtils.isNotBlank(id)){
			custTypeBrandProfitPer = custTypeBrandProfitPerService.get(CustTypeBrandProfitPer.class, Integer.parseInt(id));
		}else{
			custTypeBrandProfitPer = new CustTypeBrandProfitPer();
		}
		custTypeBrandProfitPer.setM_umState("M");
		Brand brand=custTypeBrandProfitPerService.get(Brand.class, custTypeBrandProfitPer.getBrandCode());
		custTypeBrandProfitPer.setBrandDescr(brand.getDescr());		
		return new ModelAndView("admin/basic/custTypeBrandProfitPer/custTypeBrandProfitPer_update")
			.addObject("custTypeBrandProfitPer", custTypeBrandProfitPer);
	}
	
	/**
	 * 跳转到查看CustTypeBrandProfitPer页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustTypeBrandProfitPer页面");
		CustTypeBrandProfitPer custTypeBrandProfitPer = custTypeBrandProfitPerService.get(CustTypeBrandProfitPer.class, Integer.parseInt(id));
		custTypeBrandProfitPer.setM_umState("V");
		Brand brand=custTypeBrandProfitPerService.get(Brand.class, custTypeBrandProfitPer.getBrandCode());
		custTypeBrandProfitPer.setBrandDescr(brand.getDescr());		
		return new ModelAndView("admin/basic/custTypeBrandProfitPer/custTypeBrandProfitPer_update")
				.addObject("custTypeBrandProfitPer", custTypeBrandProfitPer);
	}
	/**
	 * 添加CustTypeBrandProfitPer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustTypeBrandProfitPer custTypeBrandProfitPer){
		logger.debug("添加CustTypeBrandProfitPer开始");
		try{
			custTypeBrandProfitPer.setExpired("F");
			custTypeBrandProfitPer.setLastUpdate(new Date());
			custTypeBrandProfitPer.setLastUpdatedBy(getUserContext(request).getCzybh());
			custTypeBrandProfitPer.setActionLog("ADD");
			this.custTypeBrandProfitPerService.save(custTypeBrandProfitPer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustTypeBrandProfitPer失败");
		}
	}
	
	/**
	 * 修改CustTypeBrandProfitPer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustTypeBrandProfitPer custTypeBrandProfitPer){
		logger.debug("修改CustTypeBrandProfitPer开始");
		try{
			custTypeBrandProfitPer.setActionLog("EDIT");
			custTypeBrandProfitPer.setLastUpdate(new Date());
			custTypeBrandProfitPer.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custTypeBrandProfitPerService.update(custTypeBrandProfitPer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustTypeBrandProfitPer失败");
		}
	}
	
	/**
	 * 删除CustTypeBrandProfitPer
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustTypeBrandProfitPer开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustTypeBrandProfitPer编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustTypeBrandProfitPer custTypeBrandProfitPer = custTypeBrandProfitPerService.get(CustTypeBrandProfitPer.class, Integer.parseInt(deleteId));
				if(custTypeBrandProfitPer == null)
					continue;
				custTypeBrandProfitPerService.delete(custTypeBrandProfitPer);
			}
		}
		logger.debug("删除CustTypeBrandProfitPer IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CustTypeBrandProfitPer导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustTypeBrandProfitPer custTypeBrandProfitPer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custTypeBrandProfitPerService.findPageBySql(page, custTypeBrandProfitPer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"品牌利润率配置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
