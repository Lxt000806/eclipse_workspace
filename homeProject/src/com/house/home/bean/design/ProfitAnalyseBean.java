package com.house.home.bean.design;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ProfitAnalyseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double area;
	private Double baseProfit;//基础毛利
	private Double baseProfit_area;
	private Double softProfit;
	private Double softProfit_area;
	private Double intProfit;
	private Double intProfit_area;
	private Double mainProfit;
	private Double mainProfit_area;
	private Double cupProfit;
	private Double cupProfit_area;
	private Double mainServProfit;
	private Double mainServProfit_area;
	private Double designProfit;
	private Double designProfit_area;
	private Double manageProfit;
	private Double manageProfit_area;
	private Double sumProfit;
	private Double sumProfit_area;
	public Double getBaseProfit() {
		return baseProfit;
	}
	public void setBaseProfit(Double baseProfit) {
		this.baseProfit = baseProfit;
	}
	public Double getSoftProfit() {
		return softProfit;
	}
	public void setSoftProfit(Double softProfit) {
		this.softProfit = softProfit;
	}
	public Double getIntProfit() {
		return intProfit;
	}
	public void setIntProfit(Double intProfit) {
		this.intProfit = intProfit;
	}
	public Double getMainProfit() {
		return mainProfit;
	}
	public void setMainProfit(Double mainProfit) {
		this.mainProfit = mainProfit;
	}
	public Double getCupProfit() {
		return cupProfit;
	}
	public void setCupProfit(Double cupProfit) {
		this.cupProfit = cupProfit;
	}
	public Double getMainServProfit() {
		return mainServProfit;
	}
	public void setMainServProfit(Double mainServProfit) {
		this.mainServProfit = mainServProfit;
	}
	public Double getDesignProfit() {
		return designProfit;
	}
	public void setDesignProfit(Double designProfit) {
		this.designProfit = designProfit;
	}
	public Double getManageProfit() {
		return manageProfit;
	}
	public void setManageProfit(Double manageProfit) {
		this.manageProfit = manageProfit;
	}
	public Double getBaseProfit_area() {
		return  (double)(Math.round(this.baseProfit/this.area*100)/100.0);
	}
	public void setBaseProfit_area(Double baseProfit_area) {
		this.baseProfit_area = baseProfit_area;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getSoftProfit_area() {
		return  (double)(Math.round(this.softProfit/this.area*100)/100.0);
	}
	public void setSoftProfit_area(Double softProfit_area) {
		this.softProfit_area = softProfit_area;
	}
	public Double getIntProfit_area() {
		return  (double)(Math.round(this.intProfit/this.area*100)/100.0);
	}
	public void setIntProfit_area(Double intProfit_area) {
		this.intProfit_area = intProfit_area;
	}
	public Double getMainProfit_area() {
		return  (double)(Math.round(this.mainProfit/this.area*100)/100.0);
	}
	public void setMainProfit_area(Double mainProfit_area) {
		this.mainProfit_area = mainProfit_area;
	}
	public Double getCupProfit_area() {
		return  (double)(Math.round(this.cupProfit/this.area*100)/100.0);
	}
	public void setCupProfit_area(Double cupProfit_area) {
		this.cupProfit_area = cupProfit_area;
	}
	public Double getMainServProfit_area() {
		return  (double)(Math.round(this.mainServProfit/this.area*100)/100.0);
	}
	public void setMainServProfit_area(Double mainServProfit_area) {
		this.mainServProfit_area = mainServProfit_area;
	}
	public Double getDesignProfit_area() {
		return  (double)(Math.round(this.designProfit/this.area*100)/100.0);
	}
	public void setDesignProfit_area(Double designProfit_area) {
		this.designProfit_area = designProfit_area;
	}
	public Double getManageProfit_area() {
		return  (double)(Math.round(this.manageProfit/this.area*100)/100.0);
	}
	public void setManageProfit_area(Double manageProfit_area) {
		this.manageProfit_area = manageProfit_area;
	}
	public Double getSumProfit() {
		DecimalFormat df= new DecimalFormat("######0.00");    
		return Double.parseDouble(df.format(this.baseProfit+this.mainProfit+this.softProfit+this.intProfit+this.mainServProfit+this.manageProfit+this.cupProfit+this.designProfit));
	}
	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}
	public Double getSumProfit_area() {
		return  (double)(Math.round(this.getSumProfit()/this.area*100)/100.0);
	}
	public void setSumProfit_area(Double sumProfit_area) {
		this.sumProfit_area = sumProfit_area;
	}
	
	
	
}
