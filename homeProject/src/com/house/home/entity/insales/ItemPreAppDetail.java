package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemPreAppDetail信息
 */
@Entity
@Table(name = "tItemPreAppDetail")
public class ItemPreAppDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "ReqPk")
	private Integer reqPk;
	@Column(name = "LastUpdate")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "BDPk")
	private String bdpk;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	
	public Integer getReqPk() {
		return this.reqPk;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}

	public String getBdpk() {
		return bdpk;
	}

	public void setBdpk(String bdpk) {
		this.bdpk = bdpk;
	}

}
