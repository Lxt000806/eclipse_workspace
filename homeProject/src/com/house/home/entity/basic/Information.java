package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * Information信息
 */
@Entity
@Table(name = "tInformation")
public class Information extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Number")
	private String number;
	@Column(name = "InfoType")
	private String infoType;
	@Column(name = "Status")
	private String status;
	@Column(name = "SendCZY")
	private String sendCZY;
	@Column(name = "SendDate")
	private Date sendDate;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "InfoTitle")
	private String infoTitle;
	@Column(name = "InfoText")
	private String infoText;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String readStatus;
	@Transient
	private String department1;
	@Transient
	private String rcvCzy;
	@Transient
	private String sendCzyDescr;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient 
	private String infoCata;
	@Transient
	private String infoCataDesc;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String infoAttach;
	@Transient
	private String rcvCzys;
	@Transient
	private String dispSeq;
	@Transient
	private int PK;
	
	

	public int getPK() {
		return PK;
	}

	public void setPK(int pK) {
		PK = pK;
	}

	public String getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(String dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getInfoCata() {
		return infoCata;
	}

	public void setInfoCata(String infoCata) {
		this.infoCata = infoCata;
	}

	public String getInfoCataDesc() {
		return infoCataDesc;
	}

	public void setInfoCataDesc(String infoCataDesc) {
		this.infoCataDesc = infoCataDesc;
	}

	public Date getSendDateFrom() {
		return sendDateFrom;
	}

	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}

	public Date getSendDateTo() {
		return sendDateTo;
	}

	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return this.number;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	
	public String getInfoType() {
		return this.infoType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	
	public String getInfoTitle() {
		return this.infoTitle;
	}
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	
	public String getInfoText() {
		return this.infoText;
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

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getRcvCzy() {
		return rcvCzy;
	}

	public void setRcvCzy(String rcvCzy) {
		this.rcvCzy = rcvCzy;
	}

	public String getSendCzyDescr() {
		return sendCzyDescr;
	}

	public void setSendCzyDescr(String sendCzyDescr) {
		this.sendCzyDescr = sendCzyDescr;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	
	public String getInfoAttach() {
		String xml = XmlConverUtil.jsonToXmlNoHead(infoAttach);
		return xml;
	}

	public void setInfoAttach(String infoAttach) {
		this.infoAttach = infoAttach;
	}

	public String getRcvCzys() {
		String xml = XmlConverUtil.jsonToXmlNoHead(rcvCzys);
    	return xml;
	}
	
	public void setRcvCzys(String rcvCzys) {
		this.rcvCzys = rcvCzys;
	}

	public String getSendCZY() {
		return sendCZY;
	}

	public void setSendCZY(String sendCZY) {
		this.sendCZY = sendCZY;
	}

	public String getConfirmCZY() {
		return confirmCZY;
	}

	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}

	

}
