package com.house.home.web.controller.builderRep;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BuilderRep;
import com.house.home.service.basic.BuilderRepService;
import com.house.home.service.design.CustomerService;
@Controller
@RequestMapping("/admin/builderAnalysis")
public class builderAnalysisController extends BaseController { 	
	@Autowired
	private BuilderRepService builderRepService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * BuilderRep列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * 新增跳转BuilderRep表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, BuilderRep builderRep) throws Exception {
		builderRep.setBeginDate(DateUtil.startOfTheMonth(new Date()));
		builderRep.setEndDate(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/project/builderAnalysis/builderAnalysis_list").addObject("builderRep", builderRep).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
//	/*this.getUserContext(request).getCzybh()   调用权限
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	/**
	 * 查询工地报备分析表格数据    工地停工排行 	//工地停工排行查询
	 * @throws Exception
	 */
	@RequestMapping("/builderJqGridgdtgap")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqgdtgap(HttpServletRequest request,
			HttpServletResponse response,BuilderRep builderRep) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (builderRep.getBeginDate()==null){
			builderRep.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			builderRep.setEndDate(DateUtil.endOfTheMonth(new Date()));	
		}
		builderRep.setBeginDate(new java.sql.Timestamp(builderRep.getBeginDate().getTime()));
		builderRep.setEndDate(new java.sql.Timestamp(builderRep.getEndDate().getTime()));
		builderRep.setType("1");//设置类型为1，工地停工排行查询
		if ("F".equals(builderRep.getExpired())){
			builderRep.setBrtg("F");			
		}else{
			builderRep.setBrtg(null);
		}
		builderRepService.findPageBySqlBrbb(page, builderRep);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询工地报备分析表格数据    工地本日报表 	//工地本日报表
	 * @throws Exception
	 */
	@RequestMapping("/builderJqGridbrbb")
	@ResponseBody
	public WebPage<Map<String,Object>> dataTableGroupByBrbb(HttpServletRequest request,
			HttpServletResponse response,BuilderRep builderRep) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (builderRep.getBeginDate()==null){
			builderRep.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			builderRep.setEndDate(DateUtil.endOfTheMonth(new Date()));	
		}
		builderRep.setBeginDate(new java.sql.Timestamp(builderRep.getBeginDate().getTime()));
		builderRep.setEndDate(new java.sql.Timestamp(builderRep.getEndDate().getTime()));
		builderRep.setType("2");  //工地本日报备
		builderRepService.findPageBySqlBrbb(page, builderRep);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * 查询工地报备分析表格数据    工地停工分析 	//工地停工分析查询
	 * @throws Exception
	 */
	@RequestMapping("/builderJqGridtgfx")
	@ResponseBody
	public WebPage<Map<String,Object>> dataTableGroupByTgfx(HttpServletRequest request,
			HttpServletResponse response,BuilderRep builderRep) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (builderRep.getBeginDate()==null){
			builderRep.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			builderRep.setEndDate(DateUtil.endOfTheMonth(new Date()));	
		}
		builderRep.setBeginDate(new java.sql.Timestamp(builderRep.getBeginDate().getTime()));
		builderRep.setEndDate(new java.sql.Timestamp(builderRep.getEndDate().getTime()));
		builderRep.setType("3");//工地停工分析
		builderRepService.findPageBySqlBrbb(page, builderRep);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/*
	 *工地报备---停工排行  查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,Customer customer){
		logger.debug("跳转查看页面 ");

		return new ModelAndView("admin/project/builderAnalysis/builderAnalysis_view")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goJqGridtgphView")     //   停工排行的查看页面表格
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridtgphView(HttpServletRequest request,
			HttpServletResponse response,BuilderRep builderRep) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderRep.setCustCode(builderRep.getCustCode().trim());
		builderRepService.findPageBySqltgphView(page, builderRep);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除工地报备信息开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "工地报备不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BuilderRep builderRep = this.builderRepService.get(BuilderRep.class, Integer.parseInt(deleteId));
				builderRep.setExpired("T");
				builderRep.setM_umState("M");					
				builderRep.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = builderRepService.deleteForProc(builderRep);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug(" IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,String id){
		Page<Map<String,Object>> page = this.newPage(request);
		BuilderRep builderRep = null;
		if (StringUtils.isNotBlank(id)){
			builderRep = builderRepService.get(BuilderRep.class, id);
		}else{
			builderRep = new BuilderRep();
		}		
		page.setPageSize(10000);
		builderRepService.findPageBySql(page, builderRep);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地报备_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
}
