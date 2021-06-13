package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *功能说明:基础获取对象请求事件
 *
 */
public class GetPrjJobPhotoListEvt extends BaseEvt{
	
	//ID串
	@NotEmpty(message="ID不能为空")
	public String id;
	public String type;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
