package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * CustMapped信息
 */
@Entity
@Table(name = "tCustMapped")
public class CustMapped extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK")
    private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustAccountPK")
	private Integer custAccountPK;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getCustAccountPK() {
		return custAccountPK;
	}
	public void setCustAccountPK(Integer custAccountPK) {
		this.custAccountPK = custAccountPK;
	}

}
