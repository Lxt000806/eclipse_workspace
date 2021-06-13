package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotBlank;

public class WorkType2QueryEvt extends BaseQueryEvt{
	
	@NotBlank(message="工种类型1不能为空")
	private String workType1;
	private String workType2;

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getWorkType2() {
		return workType2;
	}

	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	

}
