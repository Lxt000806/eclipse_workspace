package com.house.home.web.controller.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.excel.FixAreaModel_ZC;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.insales.Brand;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.design.FixAreaService;

@Controller
@RequestMapping("/admin/fixArea")
public class FixAreaController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FixAreaController.class);

	@Autowired
	private FixAreaService fixAreaService;
	
	private void resetFixArea(FixArea fixArea){
		if (fixArea!=null){
			if (StringUtils.isNotBlank(fixArea.getCustCode())){
				Customer customer = fixAreaService.get(Customer.class, fixArea.getCustCode());
				if(customer!=null){
					fixArea.setCustDescr(customer.getDescr());
					fixArea.setAddress(customer.getAddress());
				}
			}
			if (fixArea.getPrePlanAreaPK()!=null){
				PrePlanArea prePlanArea = fixAreaService.get(PrePlanArea.class, fixArea.getPrePlanAreaPK());
				if(prePlanArea!=null){
					fixArea.setPrePlanAreaDescr(prePlanArea.getDescr());
				}
			}
		}
	}

	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixArea fixArea) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixAreaService.findPageBySql(page, fixArea);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * FixArea??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/fixArea/fixArea_list");
	}
	/**
	 * FixArea??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,FixArea fixArea) throws Exception {

		return new ModelAndView("admin/design/fixArea/fixArea_code").addObject("fixArea", fixArea);
	}
	/**
	 * ???????????????FixArea??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			FixArea fixArea){
		logger.debug("???????????????FixArea??????");
		resetFixArea(fixArea);
		if(fixArea.getIsService()==null){
			fixArea.setIsService(0);
		}
		fixArea.setDispSeq(0);
		return new ModelAndView("admin/design/fixArea/fixArea_save")
			.addObject("fixArea", fixArea);
	}
	/**
	 * ???????????????FixArea??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????FixArea??????");
		FixArea fixArea = null;
		if (StringUtils.isNotBlank(id)){
			fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(id));
			resetFixArea(fixArea);
		}else{
			fixArea = new FixArea();
		}
		return new ModelAndView("admin/design/fixArea/fixArea_update")
			.addObject("fixArea", fixArea);
	}
	/**
	 * ???????????????FixArea??????
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????FixArea??????");
		FixArea fixArea = null;
		if (StringUtils.isNotBlank(id)){
			fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(id));
			resetFixArea(fixArea);
		}else{
			fixArea = new FixArea();
		}
		return new ModelAndView("admin/design/fixArea/fixArea_copy")
			.addObject("fixArea", fixArea);
	}
	/**
	 * ???????????????FixArea??????
	 * @return
	 */
	@RequestMapping("/goInsert")
	public ModelAndView goInsert(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????FixArea??????");
		FixArea fixArea = null;
		if (StringUtils.isNotBlank(id)){
			fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(id));
			resetFixArea(fixArea);
		}else{
			fixArea = new FixArea();
		}
		return new ModelAndView("admin/design/fixArea/fixArea_insert")
			.addObject("fixArea", fixArea);
	}
	
	/**
	 * ???????????????FixArea??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????FixArea??????");
		FixArea fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(id));
		resetFixArea(fixArea);
		return new ModelAndView("admin/design/fixArea/fixArea_detail")
				.addObject("fixArea", fixArea);
	}
	/**
	 * ??????FixArea
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, FixArea fixArea){
		logger.debug("??????FixArea??????");
		try{
			fixArea.setLastUpdate(new Date());
			fixArea.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result =fixAreaService.addtFixArea(fixArea); 
			if(result.isSuccess()){
				//ServletUtils.outPrintSuccess(request, response,"????????????????????????");
				ServletUtils.outPrint(request, response, true, "????????????????????????", null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????FixArea
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, FixArea fixArea){
		logger.debug("??????FixArea??????");
		try{
			if(fixAreaService.isExisted(fixArea)){
				ServletUtils.outPrintFail(request, response, "?????????????????????");
				return;
			}
			if (StringUtils.isBlank(fixArea.getExpired())) {
				fixArea.setExpired("F");
			} 
			fixArea.setActionLog("EDIT");
			fixArea.setLastUpdate(new Date());
			fixArea.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.fixAreaService.update(fixArea);
			//ServletUtils.outPrintSuccess(request, response,"????????????????????????");
			ServletUtils.outPrint(request, response, true, "????????????????????????", null, true);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????FixArea
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????FixArea??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				FixArea fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(deleteId));
				if(fixArea == null)
					continue;
				fixArea.setExpired("T");
				fixAreaService.update(fixArea);
			}
		}
		logger.debug("??????FixArea IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	/**
	 * ??????FixArea
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doInsertFixArea")
	public void doInsertFixArea(HttpServletRequest request, HttpServletResponse response, FixArea fixArea){
		logger.debug("??????FixArea??????");
		try{
			if(fixAreaService.isExisted(fixArea)){
				ServletUtils.outPrintFail(request, response, "?????????????????????");
			}
			if (StringUtils.isBlank(fixArea.getExpired())) {
				fixArea.setExpired("F");
			} 
			fixArea.setActionLog("ADD");
			fixArea.setLastUpdate(new Date());
			fixArea.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result =fixAreaService.insertFixArea(fixArea); 
			if(result.isSuccess()){
				//ServletUtils.outPrintSuccess(request, response,"????????????");
				ServletUtils.outPrint(request, response, true, "??????????????????", null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			//ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}

	/**
	 *Item??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,FixArea fixArea){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(1000);
		fixAreaService.findPageBySql(page, fixArea);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping("/addtFixArea")
	@ResponseBody
	public boolean addtFixArea(HttpServletRequest request, 
			HttpServletResponse response, FixArea fixArea){
		if(fixAreaService.isExisted(fixArea)){
			return true;
		}
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		fixArea.setLastUpdatedBy(uc.getCzybh());
		fixAreaService.addtFixArea(fixArea);
		 return false;
	}
	/**
	 * ??????????????????
	 */
	@RequestMapping("/updateFixArea")
	@ResponseBody
	public boolean updateFixArea(HttpServletRequest request, 
			HttpServletResponse response, FixArea fixArea){
		if(fixAreaService.isExisted(fixArea)){
			return true;
		}
		FixArea fixArea2=fixAreaService.get(FixArea.class, fixArea.getPk());
		fixArea2.setDescr(fixArea.getDescr());
		fixAreaService.update(fixArea2);
		 return false;
	}
	/**
	 * ????????????????????????
	 */
	@RequestMapping("/insertFixArea")
	@ResponseBody
	public boolean insertFixArea(HttpServletRequest request, 
			HttpServletResponse response, FixArea fixArea){
		if(fixAreaService.isExisted(fixArea)){
			return true;
		}
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		fixArea.setLastUpdatedBy(uc.getCzybh());
		fixAreaService.insertFixArea(fixArea);
		return false;
	}
	/**
	 * ??????????????????
	 */
	@RequestMapping("/deleteFixArea")
	@ResponseBody
	public Map<String, Object> deleteFixArea(HttpServletRequest request, 
			HttpServletResponse response, FixArea fixArea){
		return fixAreaService.deleteFixArea(fixArea);
		
	}
	/**
	 * ??????id????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getFixArea")
	@ResponseBody
	public JSONObject getFixArea(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		FixArea fixArea = fixAreaService.get(FixArea.class, Integer.parseInt(id));
		if(fixArea== null){
			return this.out("??????????????????code="+id+"??????????????????", false);
		}
		return this.out(fixArea, true);
	}
	/**
	 *??????????????????
	 * @return
	 */
	@RequestMapping("/goReloadAreaJqGrid")
	public WebPage<Map<String,Object>> goReloadAreaJqGrid(HttpServletRequest request, HttpServletResponse response, 
		FixArea fixArea){
		UserContext uc=this.getUserContext(request);
		if("ZC".equals(fixArea.getItemType1())){
			ExcelImportUtils<FixAreaModel_ZC> excelImportUtils = new ExcelImportUtils<FixAreaModel_ZC>();   
			File file =null;
			if(1==fixArea.getIsService()){
	        	file = new File(PathUtil.CLASSES + "com/house/framework/commons/excel/???????????????????????????.xls");    
	        }else{
	        	file = new File(PathUtil.CLASSES + "com/house/framework/commons/excel/??????????????????.xls");    
	        }
			 List<FixAreaModel_ZC> result=null;
				try {
					FileInputStream in = new FileInputStream(file);
					if(in!=null) result = (ArrayList<FixAreaModel_ZC>) excelImportUtils.importExcel(in, FixAreaModel_ZC.class,null);
				} catch (FileNotFoundException e) {
					
					//e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result!=null){
					 for (int i = 0; i < result.size(); i++) {   
			        	 FixAreaModel_ZC fiZc=result.get(i);
			        	 fixAreaService.addItem_FixArea(fixArea.getCustCode(), fiZc.getName(), uc.getCzybh(), "ZC");
			        	
			         } 	
				}
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixAreaService.findPageBySql(page, fixArea);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping("/doAddRegular_FixAreaFixArea")
	@ResponseBody
	public void doAddRegular_FixAreaFixArea(HttpServletRequest request, 
			HttpServletResponse response, FixArea fixArea){
		try {	
			UserContext uc=this.getUserContext(request);
			fixAreaService.addRegular_FixArea(fixArea.getCustCode(), uc.getCzybh());
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goFixAreaItemPlan")
	public ModelAndView goFixAreaItemPlan(HttpServletRequest request, HttpServletResponse response, 
			FixArea fixArea){
		logger.debug("???????????????FixArea??????");
		if (!"A".equals(fixArea.getM_umState())){
			String m_umState = fixArea.getM_umState();
			String mustImportTemp = fixArea.getMustImportTemp();
			fixArea = fixAreaService.get(FixArea.class, fixArea.getPk());
			fixArea.setM_umState(m_umState);
			fixArea.setMustImportTemp(mustImportTemp);
			if("I".equals(fixArea.getM_umState())){
				fixArea.setDescr("");
			}
		}
		resetFixArea(fixArea);
		if(fixArea.getIsService()==null){
			fixArea.setIsService(0);
		}
		if(fixArea.getDispSeq()==null){
			fixArea.setDispSeq(0);
		}
		return new ModelAndView("admin/design/fixArea/fixArea_itemPlan")
			.addObject("fixArea", fixArea);
	}
	
	@RequestMapping("/checkPrePlanArea")
	@ResponseBody
	public boolean checkPrePlanArea(HttpServletRequest request, HttpServletResponse response, FixArea fixArea){
		
		PrePlanArea prePlanArea = new PrePlanArea();
		if(fixArea.getPrePlanAreaPK() != null && StringUtils.isNotBlank(fixArea.getCustCode())){
			prePlanArea = fixAreaService.get(PrePlanArea.class, fixArea.getPrePlanAreaPK());
			if(prePlanArea != null && StringUtils.isNotBlank(prePlanArea.getCustCode()) 
					&& !fixArea.getCustCode().equals(prePlanArea.getCustCode())){
				return false;
			}
		}
		return true;
	}
	
	
	
}
