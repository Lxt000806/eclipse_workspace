package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.PrjQtlFeeEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.PrjQtlFeeResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;
import com.house.home.service.finance.WorkQltFeeService;

/**
 * 项目经理质保金相关的接口
 * @author 
 *
 */
@RequestMapping("/client/prjQtlFee")
@Controller
public class ClientPrjQtlFeeController extends ClientBaseController{
	@Autowired
	private WorkQltFeeService workQltFeeService ;
	
	/**
	 * 查看项目经理质保金、意外险余额接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjQtlFeeList")
	public void getPrjQtlFeeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjQtlFeeEvt evt=new PrjQtlFeeEvt();
		BasePageQueryResp<PrjQtlFeeResp> respon=new BasePageQueryResp<PrjQtlFeeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjQtlFeeEvt)JSONObject.toBean(json,PrjQtlFeeEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(100);
			Worker worker = new Worker();
			worker.setEmpCode(evt.getEmpCode());
			workQltFeeService.findPageBySql(page, worker);
			List<PrjQtlFeeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjQtlFeeResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看项目经理质保金变更详情接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjQtlFeeDetailList")
	public void getPrjQtlFeeDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjQtlFeeEvt evt=new PrjQtlFeeEvt();
		BasePageQueryResp<PrjQtlFeeResp> respon=new BasePageQueryResp<PrjQtlFeeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjQtlFeeEvt)JSONObject.toBean(json,PrjQtlFeeEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10);
			WorkQltFeeTran workQltFeeTran = new WorkQltFeeTran();
			workQltFeeTran.setType(evt.getType());
			workQltFeeTran.setEmpCode(evt.getEmpCode());
			workQltFeeService.findDetailBySql(page, workQltFeeTran);
			List<PrjQtlFeeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjQtlFeeResp.class);
			if(listBean.size()==0){
				respon.setNoListTip("暂无变更明细");
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setPageNo(page.getPageNo());
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

}
