package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * Czrz信息
 */
@Entity
@Table(name = "tCZRZ")
public class Czrz extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Cid")
	private Long cid;
	@Column(name = "Czdate")
	private Date czdate;
	@Column(name = "Czybh")
	private String czybh;
	@Column(name = "Mkdm")
	private String mkdm;
	@Column(name = "RefPk")
	private String refPk;
	@Column(name = "Czlx")
	private String czlx;
	@Column(name = "Zy")
	private String zy;

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	public Long getCid() {
		return this.cid;
	}
	public void setCzdate(Date czdate) {
		this.czdate = czdate;
	}
	
	public Date getCzdate() {
		return this.czdate;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setMkdm(String mkdm) {
		this.mkdm = mkdm;
	}
	
	public String getMkdm() {
		return this.mkdm;
	}
	public void setRefPk(String refPk) {
		this.refPk = refPk;
	}
	
	public String getRefPk() {
		return this.refPk;
	}
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	
	public String getCzlx() {
		return this.czlx;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	
	public String getZy() {
		return this.zy;
	}

}
