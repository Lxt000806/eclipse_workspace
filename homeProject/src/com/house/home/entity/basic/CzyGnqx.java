package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CzyGnqx信息
 */
@Entity
@Table(name = "tCZYGNQX")
public class CzyGnqx extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CZYBH")
	private String czybh;
	@Column(name = "MKDM")
	private String mkdm;
	@Column(name = "GNMC")
	private String gnmc;

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
	public void setGnmc(String gnmc) {
		this.gnmc = gnmc;
	}
	
	public String getGnmc() {
		return this.gnmc;
	}

}
