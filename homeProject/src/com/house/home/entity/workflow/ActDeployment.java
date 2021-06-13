package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActDeployment信息
 */
@Entity
@Table(name = "ACT_RE_DEPLOYMENT")
public class ActDeployment extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "CATEGORY_")
	private String category;
	@Column(name = "DEPLOY_TIME_")
	private Date deployTime;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
	}
	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}
	
	public Date getDeployTime() {
		return this.deployTime;
	}

}
