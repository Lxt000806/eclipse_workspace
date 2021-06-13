package com.house.home.bean.basic;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Version信息bean
 */
public class VersionBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="name", order=1)
	private String name;
	@ExcelAnnotation(exportName="versionNo", order=2)
	private String versionNo;
	@ExcelAnnotation(exportName="descr", order=3)
	private String descr;
	@ExcelAnnotation(exportName="url", order=4)
	private String url;
	@ExcelAnnotation(exportName="compatible", order=5)
	private String compatible;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	public String getVersionNo() {
		return this.versionNo;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}
	
	public String getCompatible() {
		return this.compatible;
	}

}
