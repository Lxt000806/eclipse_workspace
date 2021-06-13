package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.house.framework.commons.utils.XmlConverUtil;

public class WarehouseSendResp extends BaseResponse {

	private String no;
	private String iaNo;
	private String custCode;
	private String address;
	private String whCode;
	private String whDescr;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	private String itemType1;
	private String itemType1Descr;
	private String itemType2;
	private String itemType2Descr;
	private Double sendQtyTotal;
	//领料单
	private Date confirmDate;
	private Integer sendCount; //已发货次数
	private String itemSendBatchNo; //发货批次号
	private String manySendRsn; //多次配送原因
	private String delayReson; //延误原因
	private String delayRemark; //延误说明
	//领料单明细
	private Integer pk;
	private Integer reqpk;
	private Integer fixareapk;
	private Integer intprodpk;
	private String itemcode;
	private Double qty;
	private String remarks;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastupdate;
	private String lastupdatedby;
	private String expired;
	private String actionlog;
	private Double cost;
	private Double aftqty;
	private Double aftcost;
	private Double sendqty;
	private Double projectcost;
	private Integer preappdtpk;
	private Double shortqty;
	private Double declareqty;
	private Double reqqty;
	private Double sendedqty;
	private Double thesendqty;
	private String color;
	private String fixareadescr;
	private String itemdescr;
	
	private String sqldescr;
	private String detailJson;
	//仓库材料余额
	private String itCode;
	private Double qtyCal;
	private Double avgCost;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getIaNo() {
		return iaNo;
	}

	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhDescr() {
		return whDescr;
	}

	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getItemType1Descr() {
		return itemType1Descr;
	}

	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemType2Descr() {
		return itemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}

	public Double getSendQtyTotal() {
		return sendQtyTotal;
	}

	public void setSendQtyTotal(Double sendQtyTotal) {
		this.sendQtyTotal = sendQtyTotal;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getLastupdatedby() {
		return lastupdatedby;
	}

	public void setLastupdatedby(String lastupdatedby) {
		this.lastupdatedby = lastupdatedby;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Double getSendqty() {
		return sendqty;
	}

	public void setSendqty(Double sendqty) {
		this.sendqty = sendqty;
	}

	public String getActionlog() {
		return actionlog;
	}

	public void setActionlog(String actionlog) {
		this.actionlog = actionlog;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Double getAftqty() {
		return aftqty;
	}

	public void setAftqty(Double aftqty) {
		this.aftqty = aftqty;
	}

	public Integer getReqpk() {
		return reqpk;
	}

	public void setReqpk(Integer reqpk) {
		this.reqpk = reqpk;
	}

	public Integer getFixareapk() {
		return fixareapk;
	}

	public void setFixareapk(Integer fixareapk) {
		this.fixareapk = fixareapk;
	}

	public Integer getIntprodpk() {
		return intprodpk;
	}

	public void setIntprodpk(Integer intprodpk) {
		this.intprodpk = intprodpk;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getAftcost() {
		return aftcost;
	}

	public void setAftcost(Double aftcost) {
		this.aftcost = aftcost;
	}

	public Double getProjectcost() {
		return projectcost;
	}

	public void setProjectcost(Double projectcost) {
		this.projectcost = projectcost;
	}

	public Integer getPreappdtpk() {
		return preappdtpk;
	}

	public void setPreappdtpk(Integer preappdtpk) {
		this.preappdtpk = preappdtpk;
	}

	public Double getShortqty() {
		return shortqty;
	}

	public void setShortqty(Double shortqty) {
		this.shortqty = shortqty;
	}

	public Double getDeclareqty() {
		return declareqty;
	}

	public void setDeclareqty(Double declareqty) {
		this.declareqty = declareqty;
	}

	public Double getReqqty() {
		return reqqty;
	}

	public void setReqqty(Double reqqty) {
		this.reqqty = reqqty;
	}

	public Double getSendedqty() {
		return sendedqty;
	}

	public void setSendedqty(Double sendedqty) {
		this.sendedqty = sendedqty;
	}

	public Double getThesendqty() {
		return thesendqty;
	}

	public void setThesendqty(Double thesendqty) {
		this.thesendqty = thesendqty;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFixareadescr() {
		return fixareadescr;
	}

	public void setFixareadescr(String fixareadescr) {
		this.fixareadescr = fixareadescr;
	}

	public String getItemdescr() {
		return itemdescr;
	}

	public void setItemdescr(String itemdescr) {
		this.itemdescr = itemdescr;
	}

	public String getItemSendBatchNo() {
		return itemSendBatchNo;
	}

	public void setItemSendBatchNo(String itemSendBatchNo) {
		this.itemSendBatchNo = itemSendBatchNo;
	}

	public String getManySendRsn() {
		return manySendRsn;
	}

	public void setManySendRsn(String manySendRsn) {
		this.manySendRsn = manySendRsn;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getDelayReson() {
		return delayReson;
	}

	public void setDelayReson(String delayReson) {
		this.delayReson = delayReson;
	}

	public String getDelayRemark() {
		return delayRemark;
	}

	public void setDelayRemark(String delayRemark) {
		this.delayRemark = delayRemark;
	}

	public String getSqldescr() {
		return sqldescr;
	}

	public void setSqldescr(String sqldescr) {
		this.sqldescr = sqldescr;
	}

	public String getItCode() {
		return itCode;
	}

	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public Double getQtyCal() {
		return qtyCal;
	}

	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
	}

	public Double getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}
}
