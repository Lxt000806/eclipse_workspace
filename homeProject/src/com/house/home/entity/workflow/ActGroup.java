package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * ActGroup信息
 */
@Entity
@Table(name = "ACT_ID_GROUP")
public class ActGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "TYPE_")
	private String type;
	
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String wfProcNo;
	
	
	public String getWfProcNo() {
		return wfProcNo;
	}

	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
}
