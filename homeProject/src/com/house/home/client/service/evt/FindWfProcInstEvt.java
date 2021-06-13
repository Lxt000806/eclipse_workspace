package com.house.home.client.service.evt;

public class FindWfProcInstEvt extends BaseQueryEvt{
	
	private Integer type;
	private String procKey;
	private String summary;
	private String status;
	private String no;
	private String rcvStatus;
	
	public String getRcvStatus() {
		return rcvStatus;
	}

	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProcKey() {
		return procKey;
	}

	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
