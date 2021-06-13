/**
 * 
 */
package edu.mju.carwork.domain;

/**
 * @author HY
 *
 */
public class Area {

	private int areaNo;
	private String areaName;
	private byte[] areaPic;
	private String areaAddress;
	private String areaTel;
	private int carNum;
	
	public int getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(int areaNo) {
		this.areaNo = areaNo;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public byte[] getAreaPic() {
		return areaPic;
	}
	public void setAreaPic(byte[] areaPic) {
		this.areaPic = areaPic;
	}
	public String getAreaAddress() {
		return areaAddress;
	}
	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}
	public String getAreaTel() {
		return areaTel;
	}
	public void setAreaTel(String areaTel) {
		this.areaTel = areaTel;
	}
	public int getCarNum() {
		return carNum;
	}
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	
	@Override
	public String toString() {
		return "Area [areaNo=" + areaNo + ", areaName=" + areaName + ", areaPic=" + areaPic + ", areaAddress="
				+ areaAddress + ", areaTel=" + areaTel + ", carNum=" + carNum + "]";
	}

}
