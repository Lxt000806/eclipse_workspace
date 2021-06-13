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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.CustProfit;
import com.house.home.service.design.CustProfitService;
import com.house.home.bean.design.CustProfitBean;

@Controller
@RequestMapping("/admin/custProfit")
public class CustProfitController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustProfitController.class);

	@Autowired
	private CustProfitService custProfitService;

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
			HttpServletResponse response, CustProfit custProfit) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custProfitService.findPageBySql(page, custProfit);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustProfit列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custProfit/custProfit_list");
	}
	/**
	 * CustProfit查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custProfit/custProfit_code");
	}
	/**
	 * 跳转到新增CustProfit页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustProfit页面");
		CustProfit custProfit = null;
		if (StringUtils.isNotBlank(id)){
			custProfit = custProfitService.get(CustProfit.class, id);
			custProfit.setCustCode(null);
		}else{
			custProfit = new CustProfit();
		}
		
		return new ModelAndView("admin/design/custProfit/custProfit_save")
			.addObject("custProfit", custProfit);
	}
	/**
	 * 跳转到修改CustProfit页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改CustProfit页面");
		CustProfit custProfit = null;
		if (StringUtils.isNotBlank(id)){
			custProfit = custProfitService.get(CustProfit.class, id);
		}else{
			custProfit = new CustProfit();
		}
		
		return new ModelAndView("admin/design/custProfit/custProfit_update")
			.addObject("custProfit", custProfit);
	}
	
	/**
	 * 跳转到查看CustProfit页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看CustProfit页面");
		CustProfit custProfit = custProfitService.get(CustProfit.class, id);
		
		return new ModelAndView("admin/design/custProfit/custProfit_detail")
				.addObject("custProfit", custProfit);
	}
	/**
	 * 添加CustProfit
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustProfit custProfit){
		logger.debug("添加CustProfit开始");
		try{
			String str = custProfitService.getSeqNo("tCustProfit");
			custProfit.setCustCode(str);
			custProfit.setLastUpdate(new Date());
			custProfit.setLastUpdatedBy(getUserContext(request).getCzybh());
			custProfit.setExpired("F");
			this.custProfitService.save(custProfit);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CustProfit失败");
		}
	}
	
	/**
	 * 修改CustProfit
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustProfit custProfit){
		logger.debug("修改CustProfit开始");
		try{
			custProfit.setLastUpdate(new Date());
			custProfit.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custProfitService.update(custProfit);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustProfit失败");
		}
	}
	
	/**
	 * 删除CustProfit
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustProfit开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustProfit编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustProfit custProfit = custProfitService.get(CustProfit.class, deleteId);
				if(custProfit == null)
					continue;
				custProfit.setExpired("T");
				custProfitService.update(custProfit);
			}
		}
		logger.debug("删除CustProfit IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
