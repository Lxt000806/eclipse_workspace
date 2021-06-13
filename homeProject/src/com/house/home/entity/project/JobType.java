package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * JobType信息
 */
@Entity
@Table(name = "tJobType")
public class JobType extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ChooseMan")
	private String chooseMan;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Position")
	private String position;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "remarks")
	private String remarks; 
	@Column(name = "IsJobDepart")
	private String isJobDepart;
	@Column(name = "IsNeedSuppl")
	private String isNeedSuppl;
	@Column(name = "IsDispCustPhn")
	private String isDispCustPhn;
	@Column(name = "CanEndCust")
	private String canEndCust;
	@Column(name = "role")
	private String role;
	@Column(name = "IsNeedPic")
	private String isNeedPic;
	@Column(name = "IsNeedReq")
	private String isNeedReq;
	@Column(name = "PrjItem")
	private String prjItem;//项目未验收提醒
	@Column(name = "AllowManySubmit")
	private String allowManySubmit;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "IsMaterialSendJob")
	private String isMaterialSendJob;
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
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

	public String getChooseMan() {
		return chooseMan;
	}

	public void setChooseMan(String chooseMan) {
		this.chooseMan = chooseMan;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsJobDepart() {
		return isJobDepart;
	}

	public void setIsJobDepart(String isJobDepart) {
		this.isJobDepart = isJobDepart;
	}

	public String getIsNeedSuppl() {
		return isNeedSuppl;
	}

	public void setIsNeedSuppl(String isNeedSuppl) {
		this.isNeedSuppl = isNeedSuppl;
	}

	public String getIsDispCustPhn() {
		return isDispCustPhn;
	}

	public void setIsDispCustPhn(String isDispCustPhn) {
		this.isDispCustPhn = isDispCustPhn;
	}

	public String getCanEndCust() {
		return canEndCust;
	}

	public void setCanEndCust(String canEndCust) {
		this.canEndCust = canEndCust;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIsNeedPic() {
		return isNeedPic;
	}

	public void setIsNeedPic(String isNeedPic) {
		this.isNeedPic = isNeedPic;
	}

	public String getIsNeedReq() {
		return isNeedReq;
	}

	public void setIsNeedReq(String isNeedReq) {
		this.isNeedReq = isNeedReq;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getAllowManySubmit() {
		return allowManySubmit;
	}

	public void setAllowManySubmit(String allowManySubmit) {
		this.allowManySubmit = allowManySubmit;
	}
	
	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsMaterialSendJob() {
		return isMaterialSendJob;
	}

	public void setIsMaterialSendJob(String isMaterialSendJob) {
		this.isMaterialSendJob = isMaterialSendJob;
	}
	
}
