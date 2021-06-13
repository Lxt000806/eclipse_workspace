package com.house.home.client.service.evt;


public class WorkerEvalEvt extends BaseEvt{

	private String pk;
	private String custCode;
	private String workerCode;
	private Integer custWkPk;
	private String type;
	private String evaMan;
	private Integer score;
	private Integer healthScore;
	private Integer toolScore;
	private String expired;
	private String remark;
	private String m_umState;
	private String evalWorker;
	private Integer workerEvalId;
	
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
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
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
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
	public String getEvalWorker() {
		return evalWorker;
	}
	public void setEvalWorker(String evalWorker) {
		this.evalWorker = evalWorker;
	}
	public Integer getWorkerEvalId() {
		return workerEvalId;
	}
	public void setWorkerEvalId(Integer workerEvalId) {
		this.workerEvalId = workerEvalId;
	}
	
	
}
