package com.house.home.entity.commi;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tBusinessCommiFloatRuleDetail")
public class BusinessCommiFloatRuleDetail extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "FloatRulePK")
	private Integer floatRulePK;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CardinalFrom")
	private Double cardinalFrom;
	@Column(name = "CardinalTo")
	private Double cardinalTo;
	@Column(name = "CommiPer")
	private Double commiPer;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getFloatRulePK() {
		return floatRulePK;
	}
	public void setFloatRulePK(Integer floatRulePK) {
		this.floatRulePK = floatRulePK;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getCardinalFrom() {
		return cardinalFrom;
	}
	public void setCardinalFrom(Double cardinalFrom) {
		this.cardinalFrom = cardinalFrom;
	}
	public Double getCardinalTo() {
		return cardinalTo;
	}
	public void setCardinalTo(Double cardinalTo) {
		this.cardinalTo = cardinalTo;
	}
	public Double getCommiPer() {
		return commiPer;
	}
	public void setCommiPer(Double commiPer) {
		this.commiPer = commiPer;
	}
	public Double getSubsidyPer() {
		return subsidyPer;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
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
