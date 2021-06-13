package com.house.home.entity.workflow;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActInfo信息
 */
@Entity
@Table(name = "ACT_ID_INFO")
public class ActInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "USER_ID_")
	private String userId;
	@Column(name = "TYPE_")
	private String type;
	@Column(name = "KEY_")
	private String key;
	@Column(name = "VALUE_")
	private String value;
	@Column(name = "PASSWORD_")
	private Blob password;
	@Column(name = "PARENT_ID_")
	private String parentId;

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
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setPassword(Blob password) {
		this.password = password;
	}
	
	public Blob getPassword() {
		return this.password;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentId() {
		return this.parentId;
	}

}
