package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tWorkerMessage")
public class WorkerMessage extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer PK;
	@Column(name = "MsgType")
	private String msgType;
	@Column(name = "MsgText")
	private String msgText;
	@Column(name = "MsgRelNo")
	private String msgRelNo;
	@Column(name = "MsgRelCustCode")
	private String msgRelCustCode;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "SendDate")
	private Date sendDate;
	@Column(name = "RcvCZY")
	private String rcvCZY;
	@Column(name = "RcvDate")
	private Date rcvDate;
	@Column(name = "RcvStatus")
	private String rcvStatus;
	@Column(name = "IsPush")
	private String isPush;
	@Column(name = "PushStatus")
	private String pushStatus;
	public Integer getPK() {
		return PK;
	}
	public void setPK(Integer pK) {
		PK = pK;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgRelNo() {
		return msgRelNo;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	public String getMsgRelCustCode() {
		return msgRelCustCode;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getRcvCZY() {
		return rcvCZY;
	}
	public void setRcvCZY(String rcvCZY) {
		this.rcvCZY = rcvCZY;
	}
	public Date getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}


}
