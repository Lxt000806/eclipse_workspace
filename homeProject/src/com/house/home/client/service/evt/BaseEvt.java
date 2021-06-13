package com.house.home.client.service.evt;

/**
 * 
 *功能说明:基础请求事件
 *
 */
public class BaseEvt{
	//账号
    private String portalAccount;
    //密码
    private String portalPwd;
   
	//接入Portal类型
	//	0：IOS客户端
    //	1：Android客户端
	private String portalType;
	
	//app类型
	// 1 客户app
	// 2项目经理app
	// 3易居通app
	// 4小程序
	// 5工人app
	// 6司机app
	private String appType;
	
	private String dataSign;
	
	private String basePath;// 切换城市的路径

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}
	
	public String getPortalAccount() {
		return portalAccount;
	}
	public void setPortalAccount(String portalAccount) {
		this.portalAccount = portalAccount;
	}
	public String getPortalPwd() {
		return portalPwd;
	}
	public void setPortalPwd(String portalPwd) {
		this.portalPwd = portalPwd;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getDataSign() {
		return dataSign;
	}

	public void setDataSign(String dataSign) {
		this.dataSign = dataSign;
	}

}
