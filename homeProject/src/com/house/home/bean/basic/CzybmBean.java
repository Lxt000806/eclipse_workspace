package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Czybm信息bean
 */
public class CzybmBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="操作员编号", order=1)
	private String czybh;
	@ExcelAnnotation(exportName="员工编号", order=2)
	private String emnum;
	@ExcelAnnotation(exportName="中文姓名", order=3)
	private String zwxm;
	@ExcelAnnotation(exportName="客户权限", order=4)
	private String custRightDescr;
	@ExcelAnnotation(exportName="查看成本", order=5)
	private String costRightDescr;
	@ExcelAnnotation(exportName="材料权限", order=6)
	private String itemRightDescr;
	@ExcelAnnotation(exportName="操作客户类型", order=7)
	private String custTypeDescr;
	@ExcelAnnotation(exportName="一级部门", order=8)
	private String department1Descr;
	@ExcelAnnotation(exportName="二级部门", order=9)
	private String department2Descr;
	@ExcelAnnotation(exportName="三级部门", order=10)
	private String department3Descr;
	@ExcelAnnotation(exportName="离开日期", pattern="yyyy-MM-dd", order=11)
	private Date leaveDate;
	@ExcelAnnotation(exportName="状态", order=12)
	private String statusDescr;
	@ExcelAnnotation(exportName="职位", order=13)
	private String positionDescr;
	@ExcelAnnotation(exportName="电话号码", order=14)
	private String dhhm;
	@ExcelAnnotation(exportName="手机", order=15)
	private String sj;
	@ExcelAnnotation(exportName="开户日期", order=16)
	private String khrq;
	@ExcelAnnotation(exportName="操作员类型", order=17)
	private String jslx;
	@ExcelAnnotation(exportName="销户标志", order=18)
	private int zfbz;
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getEmnum() {
		return emnum;
	}
	public void setEmnum(String emnum) {
		this.emnum = emnum;
	}
	public String getZwxm() {
		return zwxm;
	}
	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}
	public String getCustRightDescr() {
		return custRightDescr;
	}
	public void setCustRightDescr(String custRightDescr) {
		this.custRightDescr = custRightDescr;
	}
	public String getCostRightDescr() {
		return costRightDescr;
	}
	public void setCostRightDescr(String costRightDescr) {
		this.costRightDescr = costRightDescr;
	}
	public String getItemRightDescr() {
		return itemRightDescr;
	}
	public void setItemRightDescr(String itemRightDescr) {
		this.itemRightDescr = itemRightDescr;
	}
	public String getCustTypeDescr() {
		return custTypeDescr;
	}
	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}
	public String getDepartment1Descr() {
		return department1Descr;
	}
	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}
	public String getDepartment2Descr() {
		return department2Descr;
	}
	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}
	public String getDepartment3Descr() {
		return department3Descr;
	}
	public void setDepartment3Descr(String department3Descr) {
		this.department3Descr = department3Descr;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getPositionDescr() {
		return positionDescr;
	}
	public void setPositionDescr(String positionDescr) {
		this.positionDescr = positionDescr;
	}
	public String getDhhm() {
		return dhhm;
	}
	public void setDhhm(String dhhm) {
		this.dhhm = dhhm;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getKhrq() {
		return khrq;
	}
	public void setKhrq(String khrq) {
		this.khrq = khrq;
	}
	public int getZfbz() {
		return zfbz;
	}
	public void setZfbz(int zfbz) {
		this.zfbz = zfbz;
	}
	public String getJslx() {
		return jslx;
	}
	public void setJslx(String jslx) {
		this.jslx = jslx;
	}

}
