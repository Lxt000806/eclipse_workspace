package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
/**
 * @Description: TODO 切割费设置
 * @author created by zb
 * @date   2018-10-22--上午10:16:48
 */
@Entity
@Table(name = "tCutFeeSet")
public class CutFeeSet extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CutType")
	private String cutType;
	@Column(name = "Size")
	private Double size;
	@Column(name = "CutFee")
	private Double cutFee;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "SupplCutFee")
	private Double supplCutFee;
	@Column(name = "FactCutFee")
	private Double factCutFee;
	@Column(name = "AllowModify")
	private String allowModify;
	
	public String getCutType() {
		return cutType;
	}
	public void setCutType(String cutType) {
		this.cutType = cutType;
	}
	public Double getSize() {
		return size;
	}
	public void setSize(Double size) {
		this.size = size;
	}
	public Double getCutFee() {
		return cutFee;
	}
	public void setCutFee(Double cutFee) {
		this.cutFee = cutFee;
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
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public Double getSupplCutFee() {
		return supplCutFee;
	}
	public void setSupplCutFee(Double supplCutFee) {
		this.supplCutFee = supplCutFee;
	}
	public Double getFactCutFee() {
		return factCutFee;
	}
	public void setFactCutFee(Double factCutFee) {
		this.factCutFee = factCutFee;
	}
	public String getAllowModify() {
		return allowModify;
	}
	public void setAllowModify(String allowModify) {
		this.allowModify = allowModify;
	}


}
