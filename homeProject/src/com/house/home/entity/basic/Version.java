package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * Version信息
 */
@Entity
@Table(name = "tVersion")
public class Version extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Name")
	private String name;
	@Column(name = "VersionNo")
	private String versionNo;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Url")
	private String url;
	@Column(name = "Compatible")
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
