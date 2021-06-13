package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * AppServerUrl信息
 */
@Entity
@Table(name = "tAppServerUrl")
public class AppServerUrl extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Url")
	private String url;
	@Column(name = "Port")
	private String port;

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
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPort() {
		return this.port;
	}

}
