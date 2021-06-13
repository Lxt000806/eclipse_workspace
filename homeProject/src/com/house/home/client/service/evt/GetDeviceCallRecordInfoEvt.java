package com.house.home.client.service.evt;

public class GetDeviceCallRecordInfoEvt extends BaseEvt{
	
	private String manufacturer;
	private String model;
	private String version;
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
