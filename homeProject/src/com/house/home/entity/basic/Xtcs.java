package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * Xtcs信息
 */
@Entity
@Table(name = "tXTCS")
public class Xtcs extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "QZ")
	private String qz;
	@Column(name = "SM")
	private String sm;
	@Column(name = "SM_E")
	private String smE;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setQz(String qz) {
		this.qz = qz;
	}
	
	public String getQz() {
		return this.qz;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	
	public String getSm() {
		return this.sm;
	}
	public void setSmE(String smE) {
		this.smE = smE;
	}
	
	public String getSmE() {
		return this.smE;
	}

}
