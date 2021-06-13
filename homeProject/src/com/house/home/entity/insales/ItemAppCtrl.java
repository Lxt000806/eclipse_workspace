package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemAppCtrl信息
 */
@Entity
@Table(name = "tItemAppCtrl")
public class ItemAppCtrl extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustType")
	private String custType;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CanInPlan")
	private String canInPlan;
	@Column(name = "CanOutPlan")
	private String canOutPlan;

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setCanInPlan(String canInPlan) {
		this.canInPlan = canInPlan;
	}
	
	public String getCanInPlan() {
		return this.canInPlan;
	}
	public void setCanOutPlan(String canOutPlan) {
		this.canOutPlan = canOutPlan;
	}
	
	public String getCanOutPlan() {
		return this.canOutPlan;
	}

}
