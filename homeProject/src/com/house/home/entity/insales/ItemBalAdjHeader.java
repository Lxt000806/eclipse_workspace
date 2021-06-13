package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;


/**
 * ItemBalAdjHeader信息
 */
@Entity
@Table(name = "tItemBalAdjHeader")
public class ItemBalAdjHeader extends BaseEntity  {
		/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
		@Id
		@Column(name = "No")
		private String no;
		@Column(name = "Status") //状态
		private String status;
		@Column(name="WHCode") //仓库编号
		private String whCode;
		@Column(name = "AdjType") //类型
		private String adjType;		
		@Column(name = "Date") //调整日期
		private Date date;
		@Column(name="Remarks")
		private String remarks;
		@Column(name="LastUpdate")
		private Date lastUpdate;
		@Column(name="LastUpdatedBy")
		private String lastUpdatedBy;
		@Column(name="Expired")
		private String expired;
		@Column(name="ActionLog")
		private String actionLog;
		@Column(name="AdjReason") //调整原因
		private String adjReason;
		@Column(name="AppEmp") //申请人
		private String appEmp;
		@Column(name = "AppDate") //申请日期
		private Date appDate;
		@Column(name="ConfirmEmp") //审核人员
		private String confirmEmp;
		@Column(name = "ConfirmDate") //审核日期
		private Date confirmDate;
		@Column(name="DocumentNo") //档案号
		private String documentNo;
		
		@Transient
		private String appEmpDescr;
		@Transient
		private String detailJson;
		@Transient
		private int temp;
		@Transient
		private String whDescr;
		@Transient
		private String confirmEmpDescr;
		@Transient
		private String allItCode;
		@Transient
		private String noRepeat;
				

		public String getNoRepeat() {
			return noRepeat;
		}
		public void setNoRepeat(String noRepeat) {
			this.noRepeat = noRepeat;
		}
		public String getAllItCode() {
			return allItCode;
		}
		public void setAllItCode(String allItCode) {
			this.allItCode = allItCode;
		}
		public String getNo() {
			return this.no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		
		public String getStatus() {
			return this.status;
		}		
		public void setStatus(String status) {
			this.status = status;
		}
	
		
		public String getWhCode() {
			return whCode;
		}
		public void setWhCode(String whCode) {
			this.whCode = whCode;
		}
		
		
		public String getAdjType() {
			return this.adjType;
		}
		public void setAdjType(String adjType) {
			this.adjType = adjType;
		}
		
		
		public Date getDate() {
			return this.date;
		}
		public void setDate(Date date) {
			this.date = date;
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
		
		
		public String getAdjReason() {
			return this.adjReason;
		}
		public void setAdjReason(String adjReason) {
			this.adjReason = adjReason;
		}
	
		
		public String getAppEmp() {
			return this.appEmp;
		}
		public void setAppEmp(String appEmp) {
			this.appEmp = appEmp;
		}
		
		
		public Date getAppDate() {
			return this.appDate;
		}
		public void setAppDate(Date appDate) {
			this.appDate = appDate;
		}
		
		
		public String getConfirmEmp() {
			return this.confirmEmp;
		}
		public void setConfirmEmp(String confirmEmp) {
			this.confirmEmp = confirmEmp;
		}
		
		
		public Date getConfirmDate() {
			return this.confirmDate;
		}
		public void setConfirmDate(Date confirmDate) {
			this.confirmDate = confirmDate;
		}
		
		
		public String getDocumentNo() {
			return this.documentNo;
		}
		public void setDocumentNo(String documentNo) {
			this.documentNo = documentNo;
		}
		
		
		public String getAppEmpDescr() {
			return this.appEmpDescr;
		}
		public void setAppEmpDescr(String appEmpDescr) {
			this.appEmpDescr = appEmpDescr;
		}
		
		
		public int getTemp() {
			return temp;
		}
		public void setTemp(int temp) {
			this.temp = temp;
		}
		
		
		public String getItemBalAdjDetailXml(){
			String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
			return xml;
		}
		public String getDetailJson() {
			return detailJson;
		}
		public void setDetailJson(String detailJson) {
			this.detailJson = detailJson;
		}
		
		
		public String getWhDescr() {
			return whDescr;
		}
		public void setWhDescr(String whDescr) {
			this.whDescr = whDescr;
		}
		
		
		public String getConfirmEmpDescr() {
			return confirmEmpDescr;
		}
		public void setConfirmEmpDescr(String confirmEmpDescr) {
			this.confirmEmpDescr = confirmEmpDescr;
		}
}
