/**
 * 
 */
package edu.mju.carwork.domain;

import java.util.Arrays;

import edu.mju.carwork.domain.ValueObject;

/**
 * @author HY
 *
 */
public class Car extends ValueObject{

	private String carNo;
	private String carType;
	private String carBrand;
	private String carColor;
	private String carValue;
	private String[] carFeature;
	private String carStatus;
	private String areaName;
	
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getCarValue() {
		return carValue;
	}
	public void setCarValue(String carValue) {
		this.carValue = carValue;
	}
	public String[] getCarFeature() {
		return carFeature;
	}
	public void setCarFeature(String[] carFeature) {
		this.carFeature = carFeature;
	}
	public String getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Override
	public String toString() {
		return "Car [carNo=" + carNo + ", areaName=" + areaName + ", carType=" + carType + ", carBrand=" + carBrand
				+ ", carColor=" + carColor + ", carValue=" + carValue + ", carFeature=" + Arrays.toString(carFeature)
				+ ", carStatus=" + carStatus + "]";
	}

}
