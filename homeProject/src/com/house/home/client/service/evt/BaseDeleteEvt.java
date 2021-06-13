package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *功能说明:基础删除请求事件
 *
 */
public class BaseDeleteEvt extends BaseEvt{
	
	//ID串
	@NotEmpty(message="ID串不能为空")
	public String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
