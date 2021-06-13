package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *功能说明:基础获取对象请求事件
 *
 */
public class BaseGetEvt extends BaseEvt{
	
	//ID串
	@NotEmpty(message="ID不能为空")
	public String id;
	public String phone;
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
