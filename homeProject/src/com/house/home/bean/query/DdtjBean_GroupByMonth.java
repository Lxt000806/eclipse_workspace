package com.house.home.bean.query;

import java.io.Serializable;

import com.house.framework.commons.excel.ExcelAnnotation;

public class DdtjBean_GroupByMonth implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="名次", order=1)
	private String pk;
	@ExcelAnnotation(exportName="月份", order=2)
	private String mDescr;
	@ExcelAnnotation(exportName="签单金额", order=7)
	private Double sumContractFee;
	@ExcelAnnotation(exportName="业绩金额", order=8)
	private Double achieveFee;
	@ExcelAnnotation(exportName="下定数量（A）", order=9)
	private Double setCount;
	@ExcelAnnotation(exportName="接待数量（B）", order=10)
	private Double crtCount;
	@ExcelAnnotation(exportName="当期下定率", order=11)
	private Double setCrtPercent;
	@ExcelAnnotation(exportName="签单数量（C）", order=12)
	private Double signCount;
	@ExcelAnnotation(exportName="统计期存单总数（D）", order=13)
	private Double signSetCount;
	@ExcelAnnotation(exportName="订单转换率(C/D*100%)", order=14)
	private Double signSetPercent;
	@ExcelAnnotation(exportName="当前有效存单数", order=15)
	private Double nowSignCount;
	@ExcelAnnotation(exportName="当期签单率", order=16)
	private Double curSignPerc;
	@ExcelAnnotation(exportName="当期成功率", order=17)
	private Double sucPercent;
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
	public String getmDescr() {
		return mDescr;
	}
	public void setmDescr(String mDescr) {
		this.mDescr = mDescr;
	}
	public Double getSumContractFee() {
		return sumContractFee;
	}
	public void setSumContractFee(Double sumContractFee) {
		this.sumContractFee = sumContractFee;
	}
	public Double getAchieveFee() {
		return achieveFee;
	}
	public void setAchieveFee(Double achieveFee) {
		this.achieveFee = achieveFee;
	}
	public Double getSetCount() {
		return setCount;
	}
	public void setSetCount(Double setCount) {
		this.setCount = setCount;
	}
	public Double getCrtCount() {
		return crtCount;
	}
	public void setCrtCount(Double crtCount) {
		this.crtCount = crtCount;
	}
	public Double getSetCrtPercent() {
		return setCrtPercent;
	}
	public void setSetCrtPercent(Double setCrtPercent) {
		this.setCrtPercent = setCrtPercent;
	}
	public Double getSignCount() {
		return signCount;
	}
	public void setSignCount(Double signCount) {
		this.signCount = signCount;
	}
	public Double getSignSetCount() {
		return signSetCount;
	}
	public void setSignSetCount(Double signSetCount) {
		this.signSetCount = signSetCount;
	}
	public Double getSignSetPercent() {
		return signSetPercent;
	}
	public void setSignSetPercent(Double signSetPercent) {
		this.signSetPercent = signSetPercent;
	}
	public Double getNowSignCount() {
		return nowSignCount;
	}
	public void setNowSignCount(Double nowSignCount) {
		this.nowSignCount = nowSignCount;
	}
	public Double getCurSignPerc() {
		return curSignPerc;
	}
	public void setCurSignPerc(Double curSignPerc) {
		this.curSignPerc = curSignPerc;
	}
	public Double getSucPercent() {
		return sucPercent;
	}
	public void setSucPercent(Double sucPercent) {
		this.sucPercent = sucPercent;
	}
	
}
