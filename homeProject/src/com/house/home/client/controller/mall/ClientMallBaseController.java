package com.house.home.client.controller.mall;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;


import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.controller.ClientBaseController;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.WorkType12Resp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.mall.MallBaseService;

@RequestMapping("/client/mallBase")
@Controller
public class ClientMallBaseController extends ClientBaseController{
	
	@Autowired
	private MallBaseService mallBaseService;

	
	@RequestMapping("/getWorkType12List")
	public void getWorkType12List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		BasePageQueryResp<WorkType12Resp> respon=new BasePageQueryResp<WorkType12Resp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String, Object>> list = mallBaseService.getWorkType12List();
			List<WorkType12Resp> listBean = BeanConvertUtil.mapToBeanList(list, WorkType12Resp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	

}
