package edu.mju.carwork.service;

public class CarQueryHelper {
	
	private String qryAreaName;
	private String qryCarType;
	private String qryCarStatus;
	
	public String getQryAreaName() {
		return qryAreaName;
	}
	public void setQryAreaName(String qryAreaName) {
		this.qryAreaName = qryAreaName;
	}
	public String getQryCarType() {
		return qryCarType;
	}
	public void setQryCarType(String qryCarType) {
		this.qryCarType = qryCarType;
	}
	public String getQryCarStatus() {
		return qryCarStatus;
	}
	public void setQryCarStatus(String qryCarStatus) {
		this.qryCarStatus = qryCarStatus;
	}
	
	@Override
	public String toString() {
		return "CarQueryHelper [qryAreaName=" + qryAreaName + ", qryCarType=" + qryCarType + ", qryCarStatus="
				+ qryCarStatus + "]";
	}

}
