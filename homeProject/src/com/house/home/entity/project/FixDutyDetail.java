package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tFixDutyDetail")
public class FixDutyDetail extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "BaseCheckItemCode")
	private String baseCheckItemCode;
	@Column(name = "Qty")
	private Integer qty;
	@Column(name = "CfmQty")
	private Integer cfmQty;
	@Column(name = "OfferPri")
	private Double offerPri;
	@Column(name = "Material")
	private Double material;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	
	@Transient
	private String workType12;
	
	
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getBaseCheckItemCode() {
		return baseCheckItemCode;
	}
	public void setBaseCheckItemCode(String baseCheckItemCode) {
		this.baseCheckItemCode = baseCheckItemCode;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getCfmQty() {
		return cfmQty;
	}
	public void setCfmQty(Integer cfmQty) {
		this.cfmQty = cfmQty;
	}
	public Double getOfferPri() {
		return offerPri;
	}
	public void setOfferPri(Double offerPri) {
		this.offerPri = offerPri;
	}
	public Double getMaterial() {
		return material;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}


	
}
