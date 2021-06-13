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
@Table (name="tSendMessage")
@SuppressWarnings("serial")
public class SendMessage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK")
	private int pk;
	@Column(name="CrtDate")
	private  Date crtDate;
	@Column(name="MsgType")
	private String  msgType;
	@Column(name="CustPhone")
	private String  custPhone;
	@Column(name="Remarks")
	private String  remarks;
	@Column(name="SINo")
	private String  siNo;
	@Column(name="SendStatus")
	private String  sendStatus;
	@Column(name="SendPara1")
	private String  sendPara1;
	@Column(name="SendPara2")
	private String  sendPara2;
	@Column(name="SendDate")
	private Date  sendDate;
	@Column(name="SendNum")
	private int  sendNum;
	@Column(name="RetMsg")
	private String  retMsg;
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSiNo() {
		return siNo;
	}
	public void setSiNo(String siNo) {
		this.siNo = siNo;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public int getSendNum() {
		return sendNum;
	}
	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	public String getSendPara1() {
		return sendPara1;
	}
	public void setSendPara1(String sendPara1) {
		this.sendPara1 = sendPara1;
	}
	public String getSendPara2() {
		return sendPara2;
	}
	public void setSendPara2(String sendPara2) {
		this.sendPara2 = sendPara2;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
