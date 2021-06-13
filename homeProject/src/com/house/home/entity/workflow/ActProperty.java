package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActProperty信息
 */
@Entity
@Table(name = "ACT_GE_PROPERTY")
public class ActProperty extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "NAME_")
	private String name;
	@Column(name = "VALUE_")
	private String value;
	@Column(name = "REV_")
	private Integer rev;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}

}
