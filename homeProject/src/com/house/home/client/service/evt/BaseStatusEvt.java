package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *功能说明:基础获取对象请求事件
 *
 */
public class BaseStatusEvt extends BaseGetEvt{
	
	@NotEmpty(message="状态不能为空")
	public String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
