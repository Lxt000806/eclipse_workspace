package com.house.home.client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang3.StringUtils;

import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.entity.basic.Employee;
import com.house.home.service.basic.EmployeeService;

/**
 * 
 *功能说明:校验服务类
 *
 */
public class ValidateServerMgr {
	
    private ValidateServerMgr() {
    }

    public static BaseResponse validatePortalAccount(String portalAccount,String portalPwd) {
    	BaseResponse respon = new BaseResponse();
    	try {
			portalAccount = URLDecoder.decode(portalAccount, "UTF-8");
			portalPwd = URLDecoder.decode(portalPwd, "UTF-8");
			if(StringUtils.isBlank(portalAccount) || StringUtils.isBlank(portalPwd)){
				respon.setReturnCode("100008");
				return respon;
			}else{
				EmployeeService employeeService = (EmployeeService)SpringContextHolder.getBean("employeeServiceImpl");
				portalPwd = DesUtils.encode(portalPwd);
				Employee employee = employeeService.getByPhoneAndMm(portalAccount, portalPwd);
				if(employee==null){
					respon.setReturnCode("100009");
					return respon;
				}
			}
	    	return respon;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			respon.setReturnCode("100010");
			return respon;
		}
    }
}
