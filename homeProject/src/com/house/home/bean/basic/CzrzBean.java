package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Czrz信息bean
 */
public class CzrzBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="cid", order=1)
	private Long cid;
    	@ExcelAnnotation(exportName="czdate", pattern="yyyy-MM-dd HH:mm:ss", order=2)
	private Date czdate;
	@ExcelAnnotation(exportName="czybh", order=3)
	private String czybh;
	@ExcelAnnotation(exportName="mkdm", order=4)
	private String mkdm;
	@ExcelAnnotation(exportName="refPk", order=5)
	private String refPk;
	@ExcelAnnotation(exportName="czlx", order=6)
	private String czlx;
	@ExcelAnnotation(exportName="zy", order=7)
	private String zy;

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	public Long getCid() {
		return this.cid;
	}
	public void setCzdate(Date czdate) {
		this.czdate = czdate;
	}
	
	public Date getCzdate() {
		return this.czdate;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setMkdm(String mkdm) {
		this.mkdm = mkdm;
	}
	
	public String getMkdm() {
		return this.mkdm;
	}
	public void setRefPk(String refPk) {
		this.refPk = refPk;
	}
	
	public String getRefPk() {
		return this.refPk;
	}
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	
	public String getCzlx() {
		return this.czlx;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	
	public String getZy() {
		return this.zy;
	}

}
