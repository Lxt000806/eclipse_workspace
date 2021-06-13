package com.house.home.bean.query;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Ddtj信息bean
 */
public class DdtjBean_Detail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="名次", order=1)
	private String pk;
	@ExcelAnnotation(exportName="员工编号", order=2)
	private String number;
	@ExcelAnnotation(exportName="设计师", order=3)
	private String namechi;
	@ExcelAnnotation(exportName="一级部门", order=4)
	private String depart1Descr;
	@ExcelAnnotation(exportName="二级部门", order=5)
	private String depart2Descr;
	@ExcelAnnotation(exportName="三级部门", order=6)
	private String depart3Descr;
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
//	@ExcelAnnotation(exportName="curSetCount", order=18)
//	private Double curSetCount;
//	@ExcelAnnotation(exportName="curSignCount", order=19)
//	private Double curSignCount;
//	@ExcelAnnotation(exportName="curEndCount", order=20)
//	private Double curEndCount;

	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNumber() {
		return this.number;
	}
	public void setNamechi(String namechi) {
		this.namechi = namechi;
	}
	
	public String getNamechi() {
		return this.namechi;
	}
	public void setDepart1Descr(String depart1Descr) {
		this.depart1Descr = depart1Descr;
	}
	
	public String getDepart1Descr() {
		return this.depart1Descr;
	}
	public void setDepart2Descr(String depart2Descr) {
		this.depart2Descr = depart2Descr;
	}
	
	public String getDepart2Descr() {
		return this.depart2Descr;
	}
	public void setDepart3Descr(String depart3Descr) {
		this.depart3Descr = depart3Descr;
	}
	
	public String getDepart3Descr() {
		return this.depart3Descr;
	}
	public void setSumContractFee(Double sumContractFee) {
		this.sumContractFee = sumContractFee;
	}
	
	public Double getSumContractFee() {
		return this.sumContractFee;
	}
	public void setAchieveFee(Double achieveFee) {
		this.achieveFee = achieveFee;
	}
	
	public Double getAchieveFee() {
		return this.achieveFee;
	}
	public void setSetCount(Double setCount) {
		this.setCount = setCount;
	}
	
	public Double getSetCount() {
		return this.setCount;
	}
	public void setCrtCount(Double crtCount) {
		this.crtCount = crtCount;
	}
	
	public Double getCrtCount() {
		return this.crtCount;
	}
	public void setSetCrtPercent(Double setCrtPercent) {
		this.setCrtPercent = setCrtPercent;
	}
	
	public Double getSetCrtPercent() {
		return this.setCrtPercent;
	}
	public void setSignCount(Double signCount) {
		this.signCount = signCount;
	}
	
	public Double getSignCount() {
		return this.signCount;
	}
	public void setSignSetCount(Double signSetCount) {
		this.signSetCount = signSetCount;
	}
	
	public Double getSignSetCount() {
		return this.signSetCount;
	}
	public void setSignSetPercent(Double signSetPercent) {
		this.signSetPercent = signSetPercent;
	}
	
	public Double getSignSetPercent() {
		return this.signSetPercent;
	}
	public void setNowSignCount(Double nowSignCount) {
		this.nowSignCount = nowSignCount;
	}
	
	public Double getNowSignCount() {
		return this.nowSignCount;
	}
	public void setCurSignPerc(Double curSignPerc) {
		this.curSignPerc = curSignPerc;
	}
	
	public Double getCurSignPerc() {
		return this.curSignPerc;
	}
	public void setSucPercent(Double sucPercent) {
		this.sucPercent = sucPercent;
	}
	
	public Double getSucPercent() {
		return this.sucPercent;
	}

}
