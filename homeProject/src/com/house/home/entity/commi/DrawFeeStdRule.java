package com.house.home.entity.commi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * DrawFeeStdRule信息
 */
@Entity
@Table(name = "tDrawFeeStdRule")
public class DrawFeeStdRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PayeeCode")
	private String payeeCode;
	@Column(name = "DrawPrice")
	private Double drawPrice;
	@Column(name = "DrawFeeMin")
	private Double drawFeeMin;
	@Column(name = "MustDesignPicCfm")
	private String mustDesignPicCfm;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Prior")
	private Integer prior;
	@Column(name = "CommiStdDesignRulePK")
	private Integer commiStdDesignRulePK;
	
	@Transient
	private Double beginArea;
	@Transient
	private Double endArea;
	@Transient
	private Double colorDrawFee;
	@Transient
	private Double colorDrawFee3D;
	@Transient
	private String drawFeeStdRuleDetailJson;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getPayeeCode() {
		return payeeCode;
	}
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}
	public Double getDrawPrice() {
		return drawPrice;
	}
	public void setDrawPrice(Double drawPrice) {
		this.drawPrice = drawPrice;
	}
	public Double getDrawFeeMin() {
		return drawFeeMin;
	}
	public void setDrawFeeMin(Double drawFeeMin) {
		this.drawFeeMin = drawFeeMin;
	}
	public String getMustDesignPicCfm() {
		return mustDesignPicCfm;
	}
	public void setMustDesignPicCfm(String mustDesignPicCfm) {
		this.mustDesignPicCfm = mustDesignPicCfm;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Integer getPrior() {
		return prior;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	public Double getBeginArea() {
		return beginArea;
	}
	public void setBeginArea(Double beginArea) {
		this.beginArea = beginArea;
	}
	public Double getEndArea() {
		return endArea;
	}
	public void setEndArea(Double endArea) {
		this.endArea = endArea;
	}
	public Double getColorDrawFee() {
		return colorDrawFee;
	}
	public void setColorDrawFee(Double colorDrawFee) {
		this.colorDrawFee = colorDrawFee;
	}
	public Double getColorDrawFee3D() {
		return colorDrawFee3D;
	}
	public void setColorDrawFee3D(Double colorDrawFee3D) {
		this.colorDrawFee3D = colorDrawFee3D;
	}
	public String getDrawFeeStdRuleDetailJson() {
		return drawFeeStdRuleDetailJson;
	}
	public void setDrawFeeStdRuleDetailJson(String drawFeeStdRuleDetailJson) {
		this.drawFeeStdRuleDetailJson = XmlConverUtil.jsonToXmlNoHead(drawFeeStdRuleDetailJson);;
	}
	public Integer getCommiStdDesignRulePK() {
		return commiStdDesignRulePK;
	}
	public void setCommiStdDesignRulePK(Integer commiStdDesignRulePK) {
		this.commiStdDesignRulePK = commiStdDesignRulePK;
	}
	
	
}
