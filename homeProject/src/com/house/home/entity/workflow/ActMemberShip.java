package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActMemberShip信息
 */
@Entity
@Table(name = "ACT_ID_MEMBERSHIP")
public class ActMemberShip extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "USER_ID_")
	private String userId;
	@Column(name = "GROUP_ID_")
	private String groupId;

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupId() {
		return this.groupId;
	}

}
