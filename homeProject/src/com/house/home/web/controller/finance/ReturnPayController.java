package com.house.home.web.controller.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.ReturnPay;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.finance.ReturnPayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/returnPay")
public class ReturnPayController extends BaseController{
	@Autowired
	private  ReturnPayService returnPayService;
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/finance/returnPay/returnPay_list");
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ReturnPay returnPay) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		returnPayService.findPageBySql(page, returnPay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ReturnPay returnPay) throws Exception {
		Page<Map<String, Object>> page;
		page = this.newPageForJqGrid(request);
		try {
			returnPayService.findDetailByNo(page, returnPay);
			return new WebPage<Map<String,Object>>(page);
		} catch (Exception e) {
			//????????????????????????????????????List???result???
			e.printStackTrace();
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			page.setResult(arrayList);
			return new WebPage<Map<String,Object>>(page);
		}
		
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,ReturnPay rp){
		logger.debug("?????????????????????");	
		System.out.println(rp.getStatus());
		ReturnPay returnPay=null;
		if (StringUtils.isNotBlank(rp.getNo())){
			returnPay=returnPayService.getByNo(rp.getNo());
			returnPay.setM_umState(rp.getM_umState());
			if("C".equals(returnPay.getM_umState())){
				returnPay.setConfirmDate(new java.sql.Timestamp(new Date().getTime()));
				returnPay.setConfirmCZY(this.getUserContext(request).getCzybh());
				Employee employee = employeeService.get(Employee.class, returnPay.getConfirmCZY());
				if(employee!=null){
					returnPay.setConfirmCzyDescr(employee.getNameChi());
				}
			}
		}else{
			returnPay = new ReturnPay();
			returnPay.setM_umState("A");
			returnPay.setStatus("1");
			returnPay.setDate(new java.sql.Timestamp(new Date().getTime()));
			returnPay.setCheckDate(new java.sql.Timestamp(new Date().getTime()));
			returnPay.setAppCZY(this.getUserContext(request).getCzybh());
			Employee employee = employeeService.get(Employee.class, returnPay.getAppCZY());
			if(employee!=null){
				returnPay.setAppCzyDescr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(rp.getAppCzyDescr())){
			returnPay.setAppCzyDescr(rp.getAppCzyDescr());
		}
		if(StringUtils.isNotBlank(rp.getConfirmCzyDescr())){
			returnPay.setConfirmCzyDescr(rp.getConfirmCzyDescr());
		}
        return new ModelAndView("admin/finance/returnPay/returnPay_save")
			.addObject("returnPay", returnPay);
	}
	
	@RequestMapping("/settleRefunds")
	public ModelAndView settleRefunds(HttpServletRequest request,
	        HttpServletResponse response, ReturnPay returnPay) {
	    
	    Date now = DateUtil.getNow();
        returnPay.setCheckoutDateFrom(DateUtil.startOfTheMonth(now));
	    returnPay.setCheckoutDateTo(DateUtil.endOfTheMonth(now));
	    
	    return new ModelAndView("admin/finance/returnPay/returnPay_settleRefunds")
	            .addObject("returnPay", returnPay);
	}
	
    @RequestMapping("/settleRefundsGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> settleRefundsGrid(HttpServletRequest request,
            HttpServletResponse response, ReturnPay returnPay) {
             
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        returnPayService.settleRefundsGrid(page, returnPay);
        
        return new WebPage<Map<String, Object>>(page);
    }
	
	@RequestMapping("/goCustPayDetail")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response,ReturnPay returnPay){
        return new ModelAndView("admin/finance/returnPay/returnPay_custPayDetail").addObject("returnPay", returnPay);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,ReturnPay returnPay){
		logger.debug("??????ReturnPay??????");
		try{
			ReturnPay rp1 = this.returnPayService.getByNo(returnPay.getNo());
			if(rp1==null){
				returnPay.setActionLog("ADD");
				returnPay.setM_umState("A");
			}else{
				returnPay.setActionLog("EDIT");
				returnPay.setM_umState("M");
			}
			returnPay.setLastUpdate(new Date());
			returnPay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			returnPay.setExpired("F");
			Result result = this.returnPayService.doSave(returnPay);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	@RequestMapping("/doConfirmPass")
	public void doConfirmPass(HttpServletRequest request, HttpServletResponse response,ReturnPay returnPay){
		logger.debug("??????ReturnPay??????");
		try{
			returnPay.setLastUpdate(new Date());
			returnPay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			returnPay.setExpired("F");
			Result result = this.returnPayService.doConfirmPass(returnPay);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	@RequestMapping("/doConfirmCancel")
	public void doConfirmCancel(HttpServletRequest request, HttpServletResponse response,ReturnPay returnPay){
		logger.debug("??????ReturnPay??????");
		try{
			returnPay.setLastUpdate(new Date());
			returnPay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			returnPay.setExpired("F");
			Result result = this.returnPayService.doConfirmCancel(returnPay);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	@RequestMapping("doBatchCheck")
	public void doBatchCheck(HttpServletRequest request,
	        HttpServletResponse response, ReturnPay returnPay) {
	    try {
	        returnPay = returnPayService.get(ReturnPay.class, returnPay.getNo());
	        returnPay.setLastUpdatedBy(getUserContext(request).getCzybh());
	        
            returnPayService.doBatchCheck(request, response, returnPay);
            ServletUtils.outPrintSuccess(request, response, "???????????????????????????");
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
        }
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ReturnPay returnPay){		
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		returnPayService.findPageBySql(page, returnPay);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param returnPay
	 * @return
	 */
	@RequestMapping("/checkHasCustCode")
	@ResponseBody
	public List<Map<String, Object>> findReturnCustCode(HttpServletRequest request,ReturnPay returnPay){
		return returnPayService.findReturnCustCode(returnPay);
	}
	
	@RequestMapping("/doExcelForDetail")
	public void doExcelForDetail(HttpServletRequest request ,HttpServletResponse response,
			ReturnPay returnPay){		
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		returnPayService.findDetailByNo(page, returnPay);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
