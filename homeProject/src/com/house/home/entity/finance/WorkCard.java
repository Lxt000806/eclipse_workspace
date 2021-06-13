package com.house.home.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.deser.ValueInstantiators.Base;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tLaborFee")
public class WorkCard extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CardID")
	private String cardID;
	@Column(name = "ActName")
	private String actName;
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	
}
