package com.house.home.client.service.resp;

public class PrjWithHoldResp {
	
	private Integer pk;
	private String custCode;
	private String workType2;
	private String workType1Descr;
	private String workType2Descr;
	private Double amount;
	private Double ysqje;
	private Double bcsqje;
	
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public String getWorkType1Descr() {
		return workType1Descr;
	}
	public void setWorkType1Descr(String workType1Descr) {
		this.workType1Descr = workType1Descr;
	}
	public String getWorkType2Descr() {
		return workType2Descr;
	}
	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getYsqje() {
		return ysqje;
	}
	public void setYsqje(Double ysqje) {
		this.ysqje = ysqje;
	}
	public Double getBcsqje() {
		return bcsqje;
	}
	public void setBcsqje(Double bcsqje) {
		this.bcsqje = bcsqje;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}

}
