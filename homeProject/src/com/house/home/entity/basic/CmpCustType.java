package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * CmpCustType信息
 */
@Entity
@Table(name = "tCmpCustType")
public class CmpCustType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CmpCode")
	private String cmpCode;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "CmpnyName")
	private String cmpnyName;
	@Column(name = "LogoFile")
	private String logoFile;
	@Column(name = "CmpnyFullName")
	private String cmpnyFullName;
	@Column(name = "CmpnyAddress")
	private String cmpnyAddress;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "PayeeCode")
	private String payeeCode;
	@Column(name = "DesignPayeeCode")
	private String designPayeeCode;
	@Column(name = "ContractTempName")
	private String contractTempName;
	
	@Transient
	private String cmpDescr;
	@Transient
	private String custTypeDescr;

	public String getContractTempName() {
		return contractTempName;
	}

	public void setContractTempName(String contractTempName) {
		this.contractTempName = contractTempName;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	
	public String getCmpCode() {
		return this.cmpCode;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}
	
	public String getCmpnyName() {
		return this.cmpnyName;
	}
	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}
	
	public String getLogoFile() {
		return this.logoFile;
	}
	public void setCmpnyFullName(String cmpnyFullName) {
		this.cmpnyFullName = cmpnyFullName;
	}
	
	public String getCmpnyFullName() {
		return this.cmpnyFullName;
	}
	public void setCmpnyAddress(String cmpnyAddress) {
		this.cmpnyAddress = cmpnyAddress;
	}
	
	public String getCmpnyAddress() {
		return this.cmpnyAddress;
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

	public String getCmpDescr() {
		return cmpDescr;
	}

	public void setCmpDescr(String cmpDescr) {
		this.cmpDescr = cmpDescr;
	}

	public String getCustTypeDescr() {
		return custTypeDescr;
	}

	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getDesignPayeeCode() {
		return designPayeeCode;
	}

	public void setDesignPayeeCode(String designPayeeCode) {
		this.designPayeeCode = designPayeeCode;
	}
	
}
