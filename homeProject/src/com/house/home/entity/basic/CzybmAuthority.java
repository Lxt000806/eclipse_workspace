package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CzybmAuthority信息
 */
@Entity
@Table(name = "TSYS_CZYBM_AUTHORITY")
public class CzybmAuthority extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	@Column(name = "CZYBH",nullable = false)
	private String czybh;
	@Column(name = "AUTHORITY_ID",nullable = false)
	private Long authorityId;
	@Column(name = "GEN_TIME")
	private Date genTime;

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
	
	public Long getAuthorityId() {
		return this.authorityId;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	
	public Date getGenTime() {
		return this.genTime;
	}

}
