package com.house.home.entity.insales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;


@Entity
@Table(name = "tPurchasePay")
public class PurchasePay extends BaseEntity{

	 	@Id
	@Column(name = "Pk")
	private Integer pk;
	@Column
	private String puno;
	
	
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getPuno() {
		return puno;
	}
	public void setPuno(String puno) {
		this.puno = puno;
	}
	
	
	
}
