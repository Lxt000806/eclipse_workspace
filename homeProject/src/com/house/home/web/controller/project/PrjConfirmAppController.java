package com.house.home.web.controller.project;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjJobService;
import com.house.home.service.project.PrjProgConfirmService;

@Controller
@RequestMapping("/admin/PrjConfirmApp")
public class PrjConfirmAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjConfirmAppController.class);

	@Autowired
	private PrjProgConfirmService prjProgConfirmService;
	@Autowired
	private CustomerService customerService;

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
			HttpServletResponse response, PrjConfirmApp prjConfirmApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgConfirmService.findConfirmAppPageBySql(page, prjConfirmApp, getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrjConfirmApp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrjConfirmApp prjConfirmApp = new PrjConfirmApp();
		prjConfirmApp.setStatus("1");
		return new ModelAndView("admin/project/prjConfirmApp/prjConfirmApp_list").addObject("prjConfirmApp", prjConfirmApp);
	}
	/**
	 * 跳转到新增PrjConfirmApp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增PrjConfirmApp页面");
		return new ModelAndView("admin/project/prjConfirmApp/prjConfirmApp_save");
	}
	/**
	 * 跳转到修改PrjConfirmApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjConfirmApp页面");
		PrjConfirmApp prjConfirmApp = null;
		if (StringUtils.isNotBlank(id)){
			Customer customer = null;
			prjConfirmApp = prjProgConfirmService.get(PrjConfirmApp.class, Integer.parseInt(id));
			customer = customerService.get(Customer.class,prjConfirmApp.getCustCode());
			prjConfirmApp.setCustDescr(customer.getDescr());
			prjConfirmApp.setAddress(customer.getAddress());
		}else{
			prjConfirmApp = new PrjConfirmApp();
		}
		return new ModelAndView("admin/project/prjConfirmApp/prjConfirmApp_update")
			.addObject("prjConfirmApp", prjConfirmApp);
	}
	
	/**
	 * 跳转到查看PrjConfirmApp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			PrjConfirmApp prjConfirmApp){
		logger.debug("跳转到查看PrjConfirmApp页面");
		PrjConfirmApp pca = prjProgConfirmService.get(PrjConfirmApp.class, prjConfirmApp.getPk());
		pca.setDepartment1Descr(prjConfirmApp.getDepartment1Descr());
		pca.setDepartment2Descr(prjConfirmApp.getDepartment2Descr());
		pca.setProjectManDescr(prjConfirmApp.getProjectManDescr());
		pca.setDate(prjConfirmApp.getDate());
		pca.setPhone(prjConfirmApp.getPhone());
		pca.setConfirmCZY(prjConfirmApp.getConfirmCZY());
		pca.setPrjLevelDescr(prjConfirmApp.getPrjLevelDescr());
		pca.setAddress(prjConfirmApp.getAddress());
		pca.setWorkerName(prjConfirmApp.getWorkerName());
		
		return new ModelAndView("admin/project/prjConfirmApp/prjConfirmApp_detail")
				.addObject("prjConfirmApp", pca);
	}
	/**
	 * 添加PrjConfirmApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjConfirmApp prjConfirmApp){
		logger.debug("添加PrjConfirmApp开始");
		try{
			if(prjProgConfirmService.prjConfirmAppExist(prjConfirmApp.getCustCode(),prjConfirmApp.getPrjItem())!=null){
				ServletUtils.outPrintFail(request, response,"该验收申请已存在");
				return;
			}
			Map<String,Object> map = prjProgConfirmService.checkPrjConfirmPassProg(prjConfirmApp.getCustCode(), prjConfirmApp.getPrjItem());
			if(map != null){
				if(map.get("NameChi")!=null){
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(map.get("NameChi"));
					ServletUtils.outPrintFail(request, response,"当前施工节点已被 '"+stringBuilder+"' 验收,无法进行添加");
					return ;
				}
			}
			
			prjConfirmApp.setStatus("1");
			prjConfirmApp.setLastUpdate(new Date());
			prjConfirmApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjConfirmApp.setActionLog("ADD");
			prjConfirmApp.setExpired("F");
			prjProgConfirmService.save(prjConfirmApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjConfirmApp失败");
		}
	}
	
	/**
	 * 修改PrjConfirmApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,boolean isChange, PrjConfirmApp prjConfirmApp){
		logger.debug("修改PrjConfirmApp开始");
		try{
			if(isChange && prjProgConfirmService.prjConfirmAppExist(prjConfirmApp.getCustCode(),prjConfirmApp.getPrjItem())!=null){
				ServletUtils.outPrintFail(request, response,"该验收申请已存在");
				return;
			}
			Map<String,Object> map = prjProgConfirmService.checkPrjConfirmPassProg(prjConfirmApp.getCustCode(), prjConfirmApp.getPrjItem());
			if(map != null){
				if(map.get("NameChi")!=null){
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(map.get("NameChi"));
					ServletUtils.outPrintFail(request, response,"当前施工节点已被 '"+stringBuilder+"' 验收,无法进行添加");
					return ;
				}
			}
			
			PrjConfirmApp pca = prjProgConfirmService.get(PrjConfirmApp.class,prjConfirmApp.getPk());
			pca.setCustCode(prjConfirmApp.getCustCode());
			pca.setPrjItem(prjConfirmApp.getPrjItem());
			pca.setRemarks(prjConfirmApp.getRemarks());
			pca.setLastUpdate(new Date());
			pca.setLastUpdatedBy(getUserContext(request).getCzybh());
			pca.setActionLog("EDIT");
			this.prjProgConfirmService.update(pca);
			ServletUtils.outPrintSuccess(request, response);
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjConfirmApp失败");
		}
	}
	@RequestMapping("/getConfirmPrjItem")
	public void getConfirmPrjItem(HttpServletRequest request, HttpServletResponse response,String custCode){
		List<Map<String,Object>> list = this.prjProgConfirmService.getConfirmPrjItem(custCode);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<option value=\"\" selected>请选择......</option>");
		for(int i = 0; i<list.size();i++){
			stringBuilder.append("<option value=\""+list.get(i).get("CBM")+"\">"+list.get(i).get("CBM")+" "+list.get(i).get("NOTE")+"</option>");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("newPrjItem", stringBuilder.toString());
		PrintWriter writer = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	/**
	 * 验收申请管理导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response,PrjConfirmApp prjConfirmApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjProgConfirmService.findConfirmAppPageBySql(page, prjConfirmApp, getUserContext(request));
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"验收申请管理_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("删除PrjConfirmApp记录开始");
		try{
			PrjConfirmApp prjConfirmApp = prjProgConfirmService.get(PrjConfirmApp.class, pk);
			prjProgConfirmService.delete(prjConfirmApp);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除PrjConfirmApp记录失败");
		}
		
	}
}
