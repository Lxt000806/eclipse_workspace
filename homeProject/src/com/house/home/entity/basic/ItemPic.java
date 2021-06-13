package com.house.home.entity.basic;

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * PrjProgPhoto信息
 */
@Entity
@Table(name = "tItemPic")
public class ItemPic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "PicType")
	private String picType;
	@Column(name = "PicFile")
	private String picFile;
	@Column(name = "Pic")
	private Blob Pic;
	@Column(name = "ItemCode")
	private String 	itemCode;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public String getPicFile() {
		return picFile;
	}
	public void setPicFile(String picFile) {
		this.picFile = picFile;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Blob getPic() {
		return Pic;
	}
	public void setPic(Blob pic) {
		Pic = pic;
	}
	
	
}
