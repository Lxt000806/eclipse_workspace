package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PersonMessage信息bean
 */
public class PersonMessageBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="msgType", order=2)
	private String msgType;
	@ExcelAnnotation(exportName="msgText", order=3)
	private String msgText;
	@ExcelAnnotation(exportName="msgRelNo", order=4)
	private String msgRelNo;
	@ExcelAnnotation(exportName="msgRelCustCode", order=5)
	private String msgRelCustCode;
    	@ExcelAnnotation(exportName="crtDate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date crtDate;
    	@ExcelAnnotation(exportName="sendDate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date sendDate;
	@ExcelAnnotation(exportName="rcvType", order=8)
	private String rcvType;
	@ExcelAnnotation(exportName="rcvCzy", order=9)
	private String rcvCzy;
    	@ExcelAnnotation(exportName="rcvDate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date rcvDate;
	@ExcelAnnotation(exportName="status", order=11)
	private String status;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getMsgType() {
		return this.msgType;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	public String getMsgText() {
		return this.msgText;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	
	public String getMsgRelNo() {
		return this.msgRelNo;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	
	public String getMsgRelCustCode() {
		return this.msgRelCustCode;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	public Date getCrtDate() {
		return this.crtDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setRcvType(String rcvType) {
		this.rcvType = rcvType;
	}
	
	public String getRcvType() {
		return this.rcvType;
	}
	public void setRcvCzy(String rcvCzy) {
		this.rcvCzy = rcvCzy;
	}
	
	public String getRcvCzy() {
		return this.rcvCzy;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	
	public Date getRcvDate() {
		return this.rcvDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

}
