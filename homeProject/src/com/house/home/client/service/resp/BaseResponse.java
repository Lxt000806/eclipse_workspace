package com.house.home.client.service.resp;

import com.house.home.client.util.ReturnInfo;

/**
 * 
 *功能说明:返回基类
 *
 */
public class BaseResponse{
	public static final String SUCCESS = "000000";
	
	//返回码
	private String returnCode = "000000";
    //返回信息
	private String returnInfo;
    
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
   
	public String getReturnInfo() {
	    if (returnInfo == null) {
            this.returnInfo = ReturnInfo.getReturnInfo(returnCode);
        }
		return returnInfo;
	}
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	public boolean isSuccess(){
    	return SUCCESS.equals(returnCode);
    }
   
	public String toString() {
        return "Response{" +
                "returnCode" + getReturnCode() +
                "returnInfo" + getReturnInfo() +
                '}';
    }
}
