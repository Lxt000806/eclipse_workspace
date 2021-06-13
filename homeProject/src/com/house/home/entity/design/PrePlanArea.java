package com.house.home.entity.design;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tPrePlanArea")
public class PrePlanArea extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "FixAreaType")
	private String fixAreaType;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Area")
	private Double area;
	@Column(name = "Perimeter")
	private Double perimeter;
	@Column(name = "Height")
	private Double height;
	@Column(name = "PaveType")
	private String paveType;
	@Column(name = "BeamLength")
	private Double beamLength;
	@Column(name = "ShowerLength")
	private Double showerLength;
	@Column(name = "ShowerWidth")
	private Double showerWidth;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "CustCode")
	private String custCode;
	
	@Transient
	private String tempNo;
	@Transient
	private String detailJson;
	@Transient
	private Integer p1pk;
	@Transient
	private Integer p2pk;
	@Transient
	private Double height1;
	@Transient
	private Double height2;
	@Transient
	private Double length1;
	@Transient
	private Double length2;
	@Transient
	private String copyCustCode;
	@Transient
	private String module;
	
	
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCopyCustCode() {
		return copyCustCode;
	}
	public void setCopyCustCode(String copyCustCode) {
		this.copyCustCode = copyCustCode;
	}
	public Integer getP1pk() {
		return p1pk;
	}
	public void setP1pk(Integer p1pk) {
		this.p1pk = p1pk;
	}
	public Integer getP2pk() {
		return p2pk;
	}
	public void setP2pk(Integer p2pk) {
		this.p2pk = p2pk;
	}
	public Double getHeight1() {
		return height1;
	}
	public void setHeight1(Double height1) {
		this.height1 = height1;
	}
	public Double getHeight2() {
		return height2;
	}
	public void setHeight2(Double height2) {
		this.height2 = height2;
	}
	public Double getLength1() {
		return length1;
	}
	public void setLength1(Double length1) {
		this.length1 = length1;
	}
	public Double getLength2() {
		return length2;
	}
	public void setLength2(Double length2) {
		this.length2 = length2;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public String getPaveType() {
		return paveType;
	}
	public void setPaveType(String paveType) {
		this.paveType = paveType;
	}
	public Double getBeamLength() {
		return beamLength;
	}
	public void setBeamLength(Double beamLength) {
		this.beamLength = beamLength;
	}
	public Double getShowerLength() {
		return showerLength;
	}
	public void setShowerLength(Double showerLength) {
		this.showerLength = showerLength;
	}
	public Double getShowerWidth() {
		return showerWidth;
	}
	public void setShowerWidth(Double showerWidth) {
		this.showerWidth = showerWidth;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	
	
}
