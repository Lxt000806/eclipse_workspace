package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class OperateWareHousePosiEvt extends BaseEvt {
	@NotEmpty(message="仓库编号不能为空")
	private String whCode;
	@NotEmpty(message="材料编号不能为空")
	private String itCode;
	@NotEmpty(message="货架编号不能为空")
	private String whPk;
	@NotEmpty(message="数量不能为空")
	private String qty;
	private String czy;
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public String getWhPk() {
		return whPk;
	}
	public void setWhPk(String whPk) {
		this.whPk = whPk;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getCzy() {
		return czy;
	}
	public void setCzy(String czy) {
		this.czy = czy;
	}

	
}
