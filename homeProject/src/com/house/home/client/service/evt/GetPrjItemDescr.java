package com.house.home.client.service.evt;

public class GetPrjItemDescr extends BaseQueryEvt {
	private String prjRole;
	private String prjItem;
	private String fromPageFlag;
	private String workerApp;
	
	
	
	public String getWorkerApp() {
		return workerApp;
	}

	public void setWorkerApp(String workerApp) {
		this.workerApp = workerApp;
	}

	public String getPrjRole() {
		return prjRole;
	}

	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getFromPageFlag() {
		return fromPageFlag;
	}

	public void setFromPageFlag(String fromPageFlag) {
		this.fromPageFlag = fromPageFlag;
	}

}
