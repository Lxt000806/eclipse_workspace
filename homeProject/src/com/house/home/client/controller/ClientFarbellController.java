package com.house.home.client.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.utils.FarbellUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.bean.basic.FarbellBean;
import com.house.home.client.service.evt.FarbellEvt;
import com.house.home.client.service.resp.FarbellResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.UnlockInfo;
import com.house.home.entity.basic.Xtcs;
import com.house.home.service.basic.XtcsService;

/**
 * 泛达门禁系统
 */
@RequestMapping("/client/farbell")
@Controller
public class ClientFarbellController extends ClientBaseController {
	
	@Autowired
	private XtcsService xtcsService;
	
	/**
	 * 生成门禁二维码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/qrcode")
	public void qrcode(HttpServletRequest request, HttpServletResponse response) {
		//解析请求参数等......
		FarbellEvt evt = new FarbellEvt();
		FarbellResp respon = new FarbellResp();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FarbellEvt) JSONObject.toBean(json, FarbellEvt.class);
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
			//客户端要传递qrId（二维码ID）
			//二维码ID规则：共9位，第1位代表员工（1）/客户（2）、2-3位代表公司编码、4-9位代表员工号/客户号（不足前面补0）
			Xtcs xtcs = this.xtcsService.get(Xtcs.class, "CmpnyCode");
			String qz = String.format("%02d",Integer.parseInt(xtcs.getQz().trim()));
			String userCode = "000000";
			if ("1".equals(evt.getRole())) {
				userCode = String.format("%06d",Integer.parseInt(getUserContext(request).getEmnum().trim()));
			} else {
				userCode = String.format("%06d",Integer.parseInt(evt.getUserCode().trim()));
			}
			String qrIdString = evt.getRole().trim()+qz+userCode;
			int qrId = 0;
			qrId = Integer.parseInt(qrIdString);
			// 生成二维码
			String qrcode = FarbellUtils.qrcode(qrId, 0);
			//设置返回客户端的信息等
			respon.setQrCode(qrcode);
			respon.setDurationTime((int) FarbellBean.DURATION_TIME);//app无法读取Long类型数据
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * callback
	 * @author	created by zb
	 * @date	2019-6-19--下午5:07:58
	 * @param request
	 * @param response
	 */
	@RequestMapping("/callback")
	public void callback(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type"); 
		if ("unlockInfo".equals(type)) {
			unlockInfo(request, response);
		} else if("verificationQrCode".equals(type)) {
			verifyQrCode(request, response);
		}
	}
	
	/**
	 * 开门记录（泛达门禁系统将调用此接口，用于推送开门记录）
	 * 传入的参数如下：
	 * 参数类型		参数名			参数说明	备注
	 * String	type		命令类型	固定值为unlockInfo
	 * String	localId		门禁机序列号	
	 * int		unlockTime	开门时间戳	10位时间戳
	 * int		unlockType	开门方式	1：IC卡开门 2： 密码开门 3： 授权码开门 4：蓝牙开门 7：身份证开门 8：居住证开门 10：二维码
	 * String	unlockUser	开门用户
	 * int		isUnlock	开门结果	0：失败 1：成功
	 * 
	 * @param request
	 * @param response
	 */
	public void unlockInfo(HttpServletRequest request, HttpServletResponse response) {
		// 将泛达系统推送的开门信息保存到开门记录表（tUnlockInfo）
		UnlockInfo unlockInfo = new UnlockInfo();	
		try {
			unlockInfo.setLocalId(request.getParameter("localId"));
			unlockInfo.setUnlockTime(new Date(Long.parseLong(request.getParameter("unlockTime")+"000")));
			unlockInfo.setUnlockType(request.getParameter("unlockType"));
			unlockInfo.setUnlockUser(request.getParameter("unlockUser"));
			if ("10".equals(request.getParameter("unlockType"))) {
				String qrIdString = request.getParameter("unlockUser");
				String role = qrIdString.substring(0, 1);
				String qz = qrIdString.substring(1, 3);
				String userCode = "";
				if ("1".equals(role)) {
					userCode = qrIdString.substring(4, 9);
				} else {
					userCode = "CT"+qrIdString.substring(3, 9);
				}
				unlockInfo.setUnlockCZYType(role);
				unlockInfo.setCmpCode(qz);
				unlockInfo.setUnlockCZY(userCode);
			}
			unlockInfo.setIsUnlock(request.getParameter("isUnlock"));
			unlockInfo.setLastUpdate(new Date());
			unlockInfo.setLastUpdatedBy("1");
			unlockInfo.setExpired("F");
			unlockInfo.setActionLog("ADD");
			this.xtcsService.save(unlockInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 校验二维码（泛达门禁系统将调用此接口，用于校验二维码是否合法）
	 * 只有时间段内一次有效的二维码才会调用此接口
	 * 传入的参数如下：
	 * 参数类型		参数名			参数说明	备注
	 * String	type		命令类型	固定值为verificationQrCode
	 * String	localId		门禁机序列号	
	 * int		id			二维码ID
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void verifyQrCode(HttpServletRequest request, HttpServletResponse response) {
		// 如二维码通过校验，被通知端需返回如下 json 字符串：{"code":10000}
		// 如二维码校验失败，则返回{"code":-1}
		// 我们暂时不会用到校验二维码，都返回成功
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print("{\"code\":10000}");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
