package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tWorkerEval")
public class WorkerEval extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK")
	private Integer PK;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="WorkerCode")
	private String workerCode;
	@Column(name="CustWkPk")
	private Integer custWkPk;
	@Column(name="Date")
	private Date date;
	@Column(name="Type")
	private String type;
	@Column(name="EvaMan")
	private String evaMan;
	@Column(name="Score")
	private Integer score;
	@Column(name="LastUpDate")
	private Date lastUpdate;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Remark")
	private String remark;

	@Column(name="EvalWorker")
	private String evalWorker; //增加“评价工人”字段
	@Column(name="HealthScore")
	private Integer healthScore; //增加“卫生评分”字段
	@Column(name="ToolScore")
	private Integer toolScore; //增加“工具评分”字段
	
	@Transient
	private String workType12;
	
	public Integer getPK() {
		return PK;
	}
	public void setPK(Integer pK) {
		PK = pK;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEvaMan() {
		return evaMan;
	}
	public void setEvaMan(String evaMan) {
		this.evaMan = evaMan;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEvalWorker() {
		return evalWorker;
	}
	public void setEvalWorker(String evalWorker) {
		this.evalWorker = evalWorker;
	}
	public Integer getHealthScore() {
		return healthScore;
	}
	public void setHealthScore(Integer healthScore) {
		this.healthScore = healthScore;
	}
	public Integer getToolScore() {
		return toolScore;
	}
	public void setToolScore(Integer toolScore) {
		this.toolScore = toolScore;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

}
