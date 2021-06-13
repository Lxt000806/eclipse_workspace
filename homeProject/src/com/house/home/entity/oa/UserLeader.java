package com.house.home.entity.oa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * UserLeader信息
 */
@Entity
@Table(name = "OA_USER_LEADER")
public class UserLeader extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "USER_ID_")
	private String userId;
    @Id
	@Column(name = "LEADER_ID_")
	private String leaderId;

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	
	public String getLeaderId() {
		return this.leaderId;
	}

}
