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
 * Czybm信息
 */
@Entity
@Table(name = "tCZYBM")
public class Czybm extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "Czybh")
	private String czybh;
	@Column(name = "Bmbh")
	private String bmbh;
	@Column(name = "Ywxm")
	private String ywxm;
	@Column(name = "Zwxm")
	private String zwxm;
	@Column(name = "Jslx")
	private String jslx;
	@Column(name = "Emnum")
	private String emnum;
	@Column(name = "Qmcode")
	private String qmcode;
	@Column(name = "Zjm")
	private String zjm;
	@Column(name = "Mm")
	private String mm;
	@Column(name = "Khrq")
	private String khrq;
	@Column(name = "Khsj")
	private String khsj;
	@Column(name = "Xhrq")
	private String xhrq;
	@Column(name = "Xhsj")
	private String xhsj;
	@Column(name = "Zfbz")
	private Boolean zfbz;
	@Column(name = "Dxlb")
	private String dxlb;
	@Column(name = "Dxhm")
	private String dxhm;
	@Column(name = "Dhhm")
	private String dhhm;
	@Column(name = "Sj")
	private String sj;
	@Column(name = "Cz")
	private String cz;
	@Column(name = "Email")
	private String email;
	@Column(name = "Msnid")
	private String msnid;
	@Column(name = "Qq")
	private String qq;
	@Column(name = "Yzbm")
	private String yzbm;
	@Column(name = "Txdz")
	private String txdz;
	@Column(name = "Sm")
	private String sm;
	@Column(name = "Xldm")
	private Integer xldm;
	@Column(name = "Zwdm")
	private Integer zwdm;
	@Column(name = "Csrq")
	private String csrq;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "SplCode")
	private String splCode;
	@Column(name = "CustRight")
	private String custRight;
	@Column(name = "CostRight")
	private String costRight;
	@Column(name = "ItemRight")
	private String itemRight;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "CZYLB")
	private String czylb;
	@Column(name = "ProjectCostRight")
	private String projectCostRight;
	@Column(name = "SaleType")
	private String saleType;
	@Column(name = "IsOutUser")
	private String isOutUser;
	@Column(name = "PrjRole")
	private String prjRole;
	@Column(name = "SupplyRecvModel")
	private String supplyRecvModel;
	@Column(name = "ModifyMMDate")
	private Date modifyMMDate;
	@Column(name = "DefaultPoolNo")
	private String defaultPoolNo;
	
	@Transient
	private String phone;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String department3;
	@Transient
	private String userRole;//用户角色
	@Transient
	private String departmentRight;//部门权限
	@Transient
	private String emnumDescr;//员工姓名
	@Transient
	private String isLead;
	@Transient 
	private String forTicket;
	@Transient
	private String position;
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String auth;//权限
	@Transient
	private String empStatus; //员工状态 add by zb on 20200328
	@Transient
	private String czybhs; //多个操作员编码
	
	@Transient
	private String token;
	@Transient
	private String operatorType;
	
	public String getDefaultPoolNo() {
		return defaultPoolNo;
	}

	public void setDefaultPoolNo(String defaultPoolNo) {
		this.defaultPoolNo = defaultPoolNo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getForTicket() {
		return forTicket;
	}

	public void setForTicket(String forTicket) {
		this.forTicket = forTicket;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}
	
	public String getBmbh() {
		return this.bmbh;
	}
	public void setYwxm(String ywxm) {
		this.ywxm = ywxm;
	}
	
	public String getYwxm() {
		return this.ywxm;
	}
	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}
	
	public String getZwxm() {
		return this.zwxm;
	}
	public void setJslx(String jslx) {
		this.jslx = jslx;
	}
	
	public String getJslx() {
		return this.jslx;
	}
	public void setEmnum(String emnum) {
		this.emnum = emnum;
	}
	
	public String getEmnum() {
		return this.emnum;
	}
	public void setQmcode(String qmcode) {
		this.qmcode = qmcode;
	}
	
	public String getQmcode() {
		return this.qmcode;
	}
	public void setZjm(String zjm) {
		this.zjm = zjm;
	}
	
	public String getZjm() {
		return this.zjm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	
	public String getMm() {
		return this.mm;
	}
	public void setKhrq(String khrq) {
		this.khrq = khrq;
	}
	
	public String getKhrq() {
		return this.khrq;
	}
	public void setKhsj(String khsj) {
		this.khsj = khsj;
	}
	
	public String getKhsj() {
		return this.khsj;
	}
	public void setXhrq(String xhrq) {
		this.xhrq = xhrq;
	}
	
	public String getXhrq() {
		return this.xhrq;
	}
	public void setXhsj(String xhsj) {
		this.xhsj = xhsj;
	}
	
	public String getXhsj() {
		return this.xhsj;
	}
	public void setZfbz(Boolean zfbz) {
		this.zfbz = zfbz;
	}
	
	public Boolean getZfbz() {
		return this.zfbz;
	}
	public void setDxlb(String dxlb) {
		this.dxlb = dxlb;
	}
	
	public String getDxlb() {
		return this.dxlb;
	}
	public void setDxhm(String dxhm) {
		this.dxhm = dxhm;
	}
	
	public String getDxhm() {
		return this.dxhm;
	}
	public void setDhhm(String dhhm) {
		this.dhhm = dhhm;
	}
	
	public String getDhhm() {
		return this.dhhm;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	
	public String getSj() {
		return this.sj;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	
	public String getCz() {
		return this.cz;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setMsnid(String msnid) {
		this.msnid = msnid;
	}
	
	public String getMsnid() {
		return this.msnid;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getQq() {
		return this.qq;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	
	public String getYzbm() {
		return this.yzbm;
	}
	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}
	
	public String getTxdz() {
		return this.txdz;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	
	public String getSm() {
		return this.sm;
	}
	public void setXldm(Integer xldm) {
		this.xldm = xldm;
	}
	
	public Integer getXldm() {
		return this.xldm;
	}
	public void setZwdm(Integer zwdm) {
		this.zwdm = zwdm;
	}
	
	public Integer getZwdm() {
		return this.zwdm;
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	
	public String getCsrq() {
		return this.csrq;
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
	public void setSplCode(String splCode) {
		this.splCode = splCode;
	}
	
	public String getSplCode() {
		return this.splCode;
	}
	public void setCustRight(String custRight) {
		this.custRight = custRight;
	}
	
	public String getCustRight() {
		return this.custRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	
	public String getCostRight() {
		return this.costRight;
	}
	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}
	
	public String getItemRight() {
		return this.itemRight;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getDepartment3() {
		return department3;
	}

	public void setDepartment3(String department3) {
		this.department3 = department3;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getDepartmentRight() {
		return departmentRight;
	}

	public void setDepartmentRight(String departmentRight) {
		this.departmentRight = departmentRight;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmnumDescr() {
		return emnumDescr;
	}

	public void setEmnumDescr(String emnumDescr) {
		this.emnumDescr = emnumDescr;
	}

	public String getCzylb() {
		return czylb;
	}

	public void setCzylb(String czylb) {
		this.czylb = czylb;
	}

	public String getProjectCostRight() {
		return projectCostRight;
	}

	public void setProjectCostRight(String projectCostRight) {
		this.projectCostRight = projectCostRight;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getIsLead() {
		return isLead;
	}

	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}

	public String getIsOutUser() {
		return isOutUser;
	}

	public void setIsOutUser(String isOutUser) {
		this.isOutUser = isOutUser;
	}

	public String getPrjRole() {
		return prjRole;
	}

	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}

	public String getSupplyRecvModel() {
		return supplyRecvModel;
	}

	public void setSupplyRecvModel(String supplyRecvModel) {
		this.supplyRecvModel = supplyRecvModel;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Date getModifyMMDate() {
		return modifyMMDate;
	}

	public void setModifyMMDate(Date modifyMMDate) {
		this.modifyMMDate = modifyMMDate;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getCzybhs() {
		return czybhs;
	}

	public void setCzybhs(String czybhs) {
		this.czybhs = czybhs;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	
	
}
