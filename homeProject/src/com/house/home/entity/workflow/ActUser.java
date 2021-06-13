package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActUser信息
 */
@Entity
@Table(name = "ACT_ID_USER")
public class ActUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "FIRST_")
	private String first;
	@Column(name = "LAST_")
	private String last;
	@Column(name = "EMAIL_")
	private String email;
	@Column(name = "PWD_")
	private String pwd;
	@Column(name = "PICTURE_ID_")
	private String pictureId;

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
	public void setFirst(String first) {
		this.first = first;
	}
	
	public String getFirst() {
		return this.first;
	}
	public void setLast(String last) {
		this.last = last;
	}
	
	public String getLast() {
		return this.last;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	
	public String getPictureId() {
		return this.pictureId;
	}

}
