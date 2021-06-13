package com.house.home.client.service.resp;

import java.util.Date;

public class PrjQtlFeeResp extends BaseResponse {
	
	private Date date;
	private String typeDescr;
	private Double qualityFee;
	private Double accidentFee;
	private Double aftFee;
	private Double tryFee;
	private String address;
	private String refAddress;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public Double getQualityFee() {
		return qualityFee;
	}
	public void setQualityFee(Double qualityFee) {
		this.qualityFee = qualityFee;
	}
	public Double getAccidentFee() {
		return accidentFee;
	}
	public void setAccidentFee(Double accidentFee) {
		this.accidentFee = accidentFee;
	}
	public Double getAftFee() {
		return aftFee;
	}
	public void setAftFee(Double aftFee) {
		this.aftFee = aftFee;
	}
	public Double getTryFee() {
		return tryFee;
	}
	public void setTryFee(Double tryFee) {
		this.tryFee = tryFee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRefAddress() {
		return refAddress;
	}
	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
	}
	

}
