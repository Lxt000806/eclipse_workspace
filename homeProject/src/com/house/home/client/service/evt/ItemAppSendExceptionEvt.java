package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemAppSendExceptionEvt extends BaseEvt {
	@NotEmpty(message="发货单号不能为空")
	private String no;
	private String confirmMan;
	@NotEmpty(message="确认异常原因不能为空")
	private String confirmReason;
	private String projectManRemark;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getConfirmMan() {
		return confirmMan;
	}
	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}
	public String getConfirmReason() {
		return confirmReason;
	}
	public void setConfirmReason(String confirmReason) {
		this.confirmReason = confirmReason;
	}
	public String getProjectManRemark() {
		return projectManRemark;
	}
	public void setProjectManRemark(String projectManRemark) {
		this.projectManRemark = projectManRemark;
	}
	
}
