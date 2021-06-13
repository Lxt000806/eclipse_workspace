package com.house.home.client.service.resp;

import java.util.List;


@SuppressWarnings("rawtypes")
public class DesignDemoResp extends BaseListQueryResp{
	
	private int orderNum;
	private int constructNum;
	private int endNum;
	private int totalNum;
	private String code;
	private String descr;
	private boolean hasNext;
	private int designDemoNum;
	private int contractNum;
	private int notBeginNum;
	
	
	public int getNotBeginNum() {
		return notBeginNum;
	}
	public void setNotBeginNum(int notBeginNum) {
		this.notBeginNum = notBeginNum;
	}
	public int getContractNum() {
		return contractNum;
	}
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}
	public int getDesignDemoNum() {
		return designDemoNum;
	}
	public void setDesignDemoNum(int designDemoNum) {
		this.designDemoNum = designDemoNum;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getConstructNum() {
		return constructNum;
	}
	public void setConstructNum(int constructNum) {
		this.constructNum = constructNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
	
}
