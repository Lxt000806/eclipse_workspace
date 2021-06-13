package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * Table信息
 */
@Entity
@javax.persistence.Table(name = "tTable")
public class Table extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ModuleUrl")
	private String moduleUrl;
	@Column(name = "TableCode")
	private String tableCode;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String czybh;
	@Transient
	private String isMain;//是否主界面查询
	@Transient
	private String detailJson;
	@Transient
	private Integer tableCzyMapperPk;
	@Transient
	private String isEnable;
	@Transient
	private Integer frozenNum;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	
	public String getModuleUrl() {
		return this.moduleUrl;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
	public String getTableCode() {
		return this.tableCode;
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

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Integer getTableCzyMapperPk() {
		return tableCzyMapperPk;
	}

	public void setTableCzyMapperPk(Integer tableCzyMapperPk) {
		this.tableCzyMapperPk = tableCzyMapperPk;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getFrozenNum() {
		return frozenNum;
	}

	public void setFrozenNum(Integer frozenNum) {
		this.frozenNum = frozenNum;
	}
	
}