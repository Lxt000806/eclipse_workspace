package com.house.home.client.service.evt;

import java.util.Date;

public class CompletionReportEvt extends BaseQueryEvt{

	private int custWkPk;
	private Date conPlanEnd;
	
	public int getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(int custWkPk) {
		this.custWkPk = custWkPk;
	}

	public Date getConPlanEnd() {
		return conPlanEnd;
	}

	public void setConPlanEnd(Date conPlanEnd) {
		this.conPlanEnd = conPlanEnd;
	}
	
}
