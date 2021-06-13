package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * tLOGO信息
 */
@Entity
@Table(name = "tLOGO")
public class Logo extends BaseEntity {

	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DlID")
	private Integer dlID;
	@Column(name = "Mkdm")
	private String mkdm;
	@Column(name = "Dlrq")
	private String dlrq;
	@Column(name = "Dlsj")
	private String dlsj;
	@Column(name = "Tcrq")
	private String tcrq;
	@Column(name = "Tcsj")
	private String tcsj;
	@Column(name = "IP")
	private String ip;
	@Column(name = "MAC")
	private String mac;
	@Column(name = "Dlcgbz")
	private int dlcgbz;
	@Column(name = "Czybh")
	private String czybh;
	@Column(name = "MenuID")
	private int menuID;
	public Integer getDlID() {
		return dlID;
	}
	public void setDlID(Integer dlID) {
		this.dlID = dlID;
	}
	public String getMkdm() {
		return mkdm;
	}
	public void setMkdm(String mkdm) {
		this.mkdm = mkdm;
	}
	public String getDlrq() {
		return dlrq;
	}
	public void setDlrq(String dlrq) {
		this.dlrq = dlrq;
	}
	public String getDlsj() {
		return dlsj;
	}
	public void setDlsj(String dlsj) {
		this.dlsj = dlsj;
	}
	public String getTcrq() {
		return tcrq;
	}
	public void setTcrq(String tcrq) {
		this.tcrq = tcrq;
	}
	public String getTcsj() {
		return tcsj;
	}
	public void setTcsj(String tcsj) {
		this.tcsj = tcsj;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getDlcgbz() {
		return dlcgbz;
	}
	public void setDlcgbz(int dlcgbz) {
		this.dlcgbz = dlcgbz;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
		
}
