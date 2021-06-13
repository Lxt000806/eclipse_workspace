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
@Table(name = "tSpecItemReq")
public class SpecItemReq extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CupUpHigh")
	private Double cupUpHigh;
	@Column(name = "CupDownHigh")
	private Double cupDownHigh;
	@Column(name = "CupLastUpdate")
	private Date cupLastUpdate;
	@Column(name = "CupLastupdatedBy")
	private String cupLastupdatedBy;
	@Column(name = "IntLastupdate")
	private Date intLastupdate;
	@Column(name = "IntLastupdatedBy")
	private String intLastupdatedBy;
	@Column(name = "BathUpHigh")
	private Double bathUpHigh;
	@Column(name = "BathDownHigh")
	private Double bathDownHigh;
	@Column(name = "HasStove")
	private String hasStove; //集成灶
	@Column(name = "IsSelfMetal_Cup")
	private String isSelfMetal_Cup; //自购五金（橱柜）
	@Column(name = "IsSelfMetal_Int")
	private String isSelfMetal_Int; //自购五金（集成）
	@Column(name = "IsSelfSink")
	private String isSelfSink; //自购水槽

	@Transient
	private String custName;
	@Transient 
	private String address;
	@Transient
	private String status;
	@Transient
	private String custType;
	@Transient
	private String cupLastupdatedByDescr;
	@Transient
	private String intLastupdatedByDescr;
	@Transient
	private String lastUpdatedBy;// 公用修改人
	@Transient
	private String detailJson;// 批量json转xml
	@Transient
	private String isCupboard;// 是否橱柜
	@Transient
	private String itemType2;
	@Transient
	private String itemType3;
	@Transient
	private String statistcsMethod;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Double getCupUpHigh() {
		return cupUpHigh;
	}
	public void setCupUpHigh(Double cupUpHigh) {
		this.cupUpHigh = cupUpHigh;
	}
	public Double getCupDownHigh() {
		return cupDownHigh;
	}
	public void setCupDownHigh(Double cupDownHigh) {
		this.cupDownHigh = cupDownHigh;
	}
	public Date getCupLastUpdate() {
		return cupLastUpdate;
	}
	public void setCupLastUpdate(Date cupLastUpdate) {
		this.cupLastUpdate = cupLastUpdate;
	}
	public String getCupLastupdatedBy() {
		return cupLastupdatedBy;
	}
	public void setCupLastupdatedBy(String cupLastupdatedBy) {
		this.cupLastupdatedBy = cupLastupdatedBy;
	}
	public Date getIntLastupdate() {
		return intLastupdate;
	}
	public void setIntLastupdate(Date intLastupdate) {
		this.intLastupdate = intLastupdate;
	}
	public String getIntLastupdatedBy() {
		return intLastupdatedBy;
	}
	public void setIntLastupdatedBy(String intLastupdatedBy) {
		this.intLastupdatedBy = intLastupdatedBy;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCupLastupdatedByDescr() {
		return cupLastupdatedByDescr;
	}
	public void setCupLastupdatedByDescr(String cupLastupdatedByDescr) {
		this.cupLastupdatedByDescr = cupLastupdatedByDescr;
	}
	public String getIntLastupdatedByDescr() {
		return intLastupdatedByDescr;
	}
	public void setIntLastupdatedByDescr(String intLastupdatedByDescr) {
		this.intLastupdatedByDescr = intLastupdatedByDescr;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getItemType3() {
		return itemType3;
	}
	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}
	public Double getBathUpHigh() {
		return bathUpHigh;
	}
	public void setBathUpHigh(Double bathUpHigh) {
		this.bathUpHigh = bathUpHigh;
	}
	public Double getBathDownHigh() {
		return bathDownHigh;
	}
	public void setBathDownHigh(Double bathDownHigh) {
		this.bathDownHigh = bathDownHigh;
	}
	public String getHasStove() {
		return hasStove;
	}
	public void setHasStove(String hasStove) {
		this.hasStove = hasStove;
	}
	public String getIsSelfMetal_Cup() {
		return isSelfMetal_Cup;
	}
	public void setIsSelfMetal_Cup(String isSelfMetal_Cup) {
		this.isSelfMetal_Cup = isSelfMetal_Cup;
	}
	public String getIsSelfMetal_Int() {
		return isSelfMetal_Int;
	}
	public void setIsSelfMetal_Int(String isSelfMetal_Int) {
		this.isSelfMetal_Int = isSelfMetal_Int;
	}
	public String getIsSelfSink() {
		return isSelfSink;
	}
	public void setIsSelfSink(String isSelfSink) {
		this.isSelfSink = isSelfSink;
	}
	public String getStatistcsMethod() {
		return statistcsMethod;
	}
	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}
	
}
