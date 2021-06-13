package com.house.home.entity.oa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * OaAll信息
 */
@Entity
@Table(name = "OA_ALL")
public class OaAll extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "APPLY_TIME")
	private Date applyTime;
	@Column(name = "FINISH_TIME")
	private Date finishTime;
	@Column(name = "STATUS")
	private String status;

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Date getApplyTime() {
		return this.applyTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
