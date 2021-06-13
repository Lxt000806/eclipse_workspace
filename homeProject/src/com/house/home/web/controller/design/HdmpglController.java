package com.house.home.web.controller.design;

import java.io.FileWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Hdmpgl;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.HdmpglService;
import com.house.home.service.design.CustomerService;

@Controller
@RequestMapping("/admin/hdmpgl")
public class HdmpglController extends BaseController{

	@Autowired
	private CustomerService customerService;
	@Autowired
	private HdmpglService hdmpglService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	
	
	/**
	 * 活动门票管理
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =getUserContext(request);
		hdmpglService.findPageBySql(page, hdmpgl,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goOrderDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goOrderDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =getUserContext(request);
		hdmpglService.findOrderDetailJqGrid(page, hdmpgl);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goGiftDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGiftDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Hdmpgl hdmpgl) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =getUserContext(request);
		hdmpglService.findGiftDetailJqGrid(page, hdmpgl);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		UserContext uc = getUserContext(request);
		String czybh= uc.getCzybh();
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 810);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0211", "高级管理");
		String provideType="1";
		Map<String, Object> validActMap=hdmpglService.getValidActNum();
		if(validActMap!=null){
			hdmpgl.setActNo(String.valueOf(validActMap.get("No")));
			hdmpgl.setActDescr(String.valueOf(validActMap.get("ActName")));
		}
		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_list").addObject("hhdmpgl",hdmpgl)
		.addObject("hasAuthByCzybh",hasAuthByCzybh)
		.addObject("czybh",czybh).addObject("provideType",provideType);
	}
	
	/**
	 * 跳转活动门票生成
	 * 
	 * */
	@RequestMapping("/goCreate")
	public ModelAndView goCreate(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		
		return new ModelAndView("admin/design/hdmpgl/hdmpgl_create").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 跳转活动门票删除
	 * 
	 * */
	@RequestMapping("/goDelete")
	public ModelAndView goDelete(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		
		return new ModelAndView("admin/design/hdmpgl/hdmpgl_delete").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 跳转活动门票打印
	 * 
	 * */
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		
		return new ModelAndView("admin/design/hdmpgl/hdmpgl_print").addObject("hdmpgl",hdmpgl);
	}
	
    /**
     * 活动门票发放
     * 
     * */
	@RequestMapping("/goProvide")
	public ModelAndView goProvide(HttpServletRequest request ,
			HttpServletResponse response,Hdmpgl hdmpgl ) throws Exception{
		String czylb=this.getUserContext(request).getCzylb();
		if("1".equals(czylb)){
			hdmpgl.setProvideType("1");
		}else if("2".equals(czylb)){
			hdmpgl.setProvideType("2");
		}
		
		hdmpgl.setProvideDate(new Date());
		hdmpgl.setProvideCZY(this.getUserContext(request).getCzybh());
		hdmpgl.setProvideDescr(this.getUserContext(request).getZwxm());
		
		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_provide").addObject("hdmpgl",hdmpgl).addObject("czylb",czylb);
	}
	
	/**
	 * 查看活动门票信息
	 * 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,Integer pk) throws Exception{
		Hdmpgl hdmpgl=null;
		
		hdmpgl=hdmpglService.get(Hdmpgl.class, pk);
		hdmpgl.setProvideDescr(this.getUserContext(request).getZwxm());

		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_view").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 查看活动门票信息
	 * 
	 * */
	@RequestMapping("/goOrderDetail")
	public ModelAndView goOrderDetail(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{
		Hdmpgl hdmpgl=new Hdmpgl();
		

		return new ModelAndView("admin/design/hdmpgl/hdmpgl_orderDetail").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 查看活动门票信息
	 * 
	 * */
	@RequestMapping("/goGiftDetail")
	public ModelAndView goGiftDetail(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{
		Hdmpgl hdmpgl=new Hdmpgl();
		

		return new ModelAndView("admin/design/hdmpgl/hdmpgl_giftDetail").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 修改活动门票信息
	 * 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,Integer pk) throws Exception{
		Hdmpgl hdmpgl=null;
		Employee businessMan=null;
		Employee designMan=null;
		String czylb=this.getUserContext(request).getCzylb();
		
		hdmpgl=hdmpglService.get(Hdmpgl.class, pk);
		if("1".equals(czylb)){
			hdmpgl.setProvideType("1");
		}else if("2".equals(czylb)){
			hdmpgl.setProvideType("2");
		}
		
		if(StringUtils.isNotBlank(hdmpgl.getBusinessMan())){
			businessMan=employeeService.get(Employee.class, hdmpgl.getBusinessMan());
			hdmpgl.setBusinessDescr(businessMan.getNameChi());
		}
		if(StringUtils.isNotBlank(hdmpgl.getDesignMan())){
			designMan=employeeService.get(Employee.class, hdmpgl.getDesignMan());
			hdmpgl.setDesignDescr(designMan.getNameChi());
		}
		
		hdmpgl.setProvideDescr(this.getUserContext(request).getZwxm());

		return new ModelAndView("admin/supplier/hdmpgl/hdmpgl_update").addObject("hdmpgl",hdmpgl);
	}
	
	/**
	 * 活动门票生成
	 * 
	 * */
	@RequestMapping("/doCreate")
	public void doCreate(HttpServletRequest request ,HttpServletResponse response ,
			String actNo,Integer length,String prefix,Integer numFrom, Integer ticketNum){
		logger.debug("生成门票");
		try{
			if(!hdmpglService.isRepetition(prefix,numFrom,numFrom+ticketNum-1,length,actNo)){
				ServletUtils.outPrintFail(request, response,"存在重复门票");
				return;
			}

			for(int i=0;i<ticketNum;i++){
				hdmpglService.doCreate(actNo,length,prefix,numFrom+i,this.getUserContext(request).getCzybh());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "门票生成失败");
		}
	}
	
	/**
	 * 活动门票发放操作
	 * 
	 * */
	@RequestMapping("/doProvide")
	public void doProvide(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票发放");
		
		//判断业主电话是否已存在
		if (hdmpglService.isExistPhone(hdmpgl.getActNo(), hdmpgl.getPhone())) {
			ServletUtils.outPrintFail(request, response, "该业主电话已存在，不能重复录入！");
			return;
		}
		
		Hdmpgl hd=null;
		String pkString =hdmpglService.getPk(hdmpgl.getActNo(),hdmpgl.getTicketNo());
		hd=hdmpglService.get(Hdmpgl.class, hdmpgl.getPk());
		try{
			hd.setLastUpdate(new Date());
			hd.setProvideDate(new Date());
			hd.setDescr(hdmpgl.getDescr());
			hd.setAddress(hdmpgl.getAddress());
			hd.setPhone(hdmpgl.getPhone());
			hd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			hd.setBusiManName(hdmpgl.getBusiManName());
			hd.setBusiManPhone(hdmpgl.getBusiManPhone());
			hd.setStatus("2");
			hd.setProvideCZY(this.getUserContext(request).getCzybh());
			hd.setProvideType(hdmpgl.getProvideType());
			
			this.hdmpglService.update(hd);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "发放门票失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票发放编辑");
		Hdmpgl hd=null;
		hd=hdmpglService.get(Hdmpgl.class, hdmpgl.getPk());
		try{
			hd.setLastUpdate(new Date());
			hd.setProvideDate(new Date());
			hd.setDescr(hdmpgl.getDescr());
			hd.setAddress(hdmpgl.getAddress());
			hd.setPhone(hdmpgl.getPhone());
			hd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			hd.setBusiManName(hdmpgl.getBusiManName());
			hd.setBusiManPhone(hdmpgl.getBusiManPhone());
			hd.setStatus("2");
			//hd.setProvideCZY(this.getUserContext(request).getCzybh());
			hd.setProvideType(hdmpgl.getProvideType());
			
			this.hdmpglService.update(hd);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 收回门票
	 * 
	 * */
	@RequestMapping("/doCallback")
	public void doCallback(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票收回");
		try{
			
			this.hdmpglService.doCallBack(hdmpgl.getPk(),this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 门票签到
	 * 
	 * */
	@RequestMapping("/doSign")
	public void doSign(HttpServletRequest request ,
			HttpServletResponse response , Hdmpgl hdmpgl){
		logger.debug("门票收回");
		try{
			
			this.hdmpglService.doSign(hdmpgl.getPk(),this.getUserContext(request).getCzybh(),hdmpgl.getStatus());
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request ,
			HttpServletResponse response ,String actNo,Integer length,String prefix,Integer numFrom, Integer numTo){
		logger.debug("门票删除");
		try{
			
			this.hdmpglService.doDeleteTickets(prefix,numFrom,numTo,length,actNo);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doPrint")
	public void doPrint(HttpServletRequest request ,HttpServletResponse response ,
			String actNo,Integer length,String prefix,Integer numFrom, Integer numTo,String times,String sites,String actName){
		logger.debug("门票删除");
		try{
			 FileWriter fileWriter = new FileWriter("C:/Commander/SmallScan/mpprint.txt");
			 String printString ="";
			 for(int x=0;x<=numTo-numFrom;x++){
				 int num=numFrom+x;	
				 String zero="";
				 for(int i=0;i<length-(prefix+String.valueOf(numFrom)).length();i++){
					 zero=zero+"0";
				 }
				  printString = printString+"编码："+prefix+zero+num+"\r\n"+times+"   "+sites+"\r\n"+actName+"\r\n重要凭证请切勿撕毁\r\n";
			 }
			 fileWriter.write(printString);
	           fileWriter.flush();
	           fileWriter.close();
	           
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}

	@RequestMapping("/isSend")
	@ResponseBody
	public boolean isSend(HttpServletRequest request, HttpServletResponse response
			,String actNo,Integer length,String prefix,Integer numFrom, Integer numTo){
		
		boolean isSend=this.hdmpglService.isSend(prefix,numFrom,numTo,length,actNo);
		
		return isSend;
	}

	@RequestMapping("/hasTicket")
	@ResponseBody
	public boolean hasTicket(HttpServletRequest request, HttpServletResponse response
			,String actNo,Integer length,String prefix,Integer numFrom, Integer numTo){
		
		boolean hasTicket=this.hdmpglService.hasTicket(prefix,numFrom,numTo,length,actNo);
		
		return hasTicket;
	}
	
	
	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Hdmpgl hdmpgl){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		hdmpglService.findPageBySql(page, hdmpgl,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"活动门票_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doOrderDetailExcel")
	public void doOrderDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			Hdmpgl hdmpgl){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		hdmpglService.findOrderDetailJqGrid(page, hdmpgl);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"下定明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doGiftDetailExcel")
	public void doGiftDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			Hdmpgl hdmpgl){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		hdmpglService.findGiftDetailJqGrid(page, hdmpgl);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"礼品发放明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
	
}
