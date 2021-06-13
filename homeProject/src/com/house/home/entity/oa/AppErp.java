package com.house.home.entity.oa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "OA_ERP")
public class AppErp extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "APPLY_TIME")
	private Date applyTime;
	
	
	@Transient
	private String erpCzy;
	@Transient
	private String userName;
	@Transient
	private String erpCzyBackReason;
	
	
	public String getErpCzyBackReason() {
		return erpCzyBackReason;
	}
	public void setErpCzyBackReason(String erpCzyBackReason) {
		this.erpCzyBackReason = erpCzyBackReason;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getErpCzy() {
		return erpCzy;
	}
	public void setErpCzy(String erpCzy) {
		this.erpCzy = erpCzy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	

}
