package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 修改手机号明细表
 */
@Entity
@Table(name = "tPhoneModifyDetail")
public class PhoneModifyDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="Status")
	private String status;
	@Column(name="OldPhone")
	private String oldPhone;
	@Column(name="NewPhone")
	private String newPhone;
	@Column(name="Date")
	private Date date;
	@Column(name="CZYBH")
	private String czybh;
	@Column(name="Module")
	private String module;
	
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOldPhone() {
		return oldPhone;
	}
	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	
}
