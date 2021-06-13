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
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.WorkerItemAppEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.ItemBatchHeaderQueryResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.project.WorkerService;

@RequestMapping("/client/workerItemApp")
@Controller
public class ClientWorkerItemAppController extends ClientBaseController{
	
	@Autowired
	private WorkerService workerService;
	@Autowired
	protected Validator springValidator;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemBatchHeaderList")
	public void getItemBatchHeaderList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		BasePageQueryResp<ItemBatchHeaderQueryResp> respon=new BasePageQueryResp<ItemBatchHeaderQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerItemAppEvt)JSONObject.toBean(json,WorkerItemAppEvt.class);
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
			page.setPageNo(1);
			page.setPageSize(100);
			/*if(StringUtils.isNotBlank(itemBatchHeader.getCustCode())){
				Customer customer = itemBatchHeaderService.get(Customer.class, itemBatchHeader.getCustCode());
				if(customer != null){
					itemBatchHeader.setCustType(customer.getCustType());
				}
			}*/
			workerService.getItemBatchList(page,evt.getItemType1(),evt.getCustWkPk());
			List<ItemBatchHeaderQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemBatchHeaderQueryResp.class);
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
	
	
}
