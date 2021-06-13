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
 * CustOrder信息
 */
@Entity
@Table(name = "tCustOrder")
public class CustOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
    
	@Column(name = "Descr")
	private String descr;
	
	@Column(name = "Mobile1")
	private String mobile1;
	
	@Column(name = "CustAccountPk")
	private Integer custAccountPk;
	
	@Column(name = "Date")
	private Date date;

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public Integer getCustAccountPk() {
		return custAccountPk;
	}

	public void setCustAccountPk(Integer custAccountPk) {
		this.custAccountPk = custAccountPk;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
