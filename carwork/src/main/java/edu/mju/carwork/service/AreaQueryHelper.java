package edu.mju.carwork.service;

public class AreaQueryHelper {
	private String qryAreaName;
	private String qryAreaAddress;
	
	public String getQryAreaName() {
		return qryAreaName;
	}
	public void setQryAreaName(String qryAreaName) {
		this.qryAreaName = qryAreaName;
	}
	public String getQryAreaAddress() {
		return qryAreaAddress;
	}
	public void setQryAreaAddress(String qryAreaAddress) {
		this.qryAreaAddress = qryAreaAddress;
	}
	
	@Override
	public String toString() {
		return "AreaQueryHelper [qryAreaName=" + qryAreaName + ", qryAreaAddress=" + qryAreaAddress + "]";
	}
	

	
	
}
