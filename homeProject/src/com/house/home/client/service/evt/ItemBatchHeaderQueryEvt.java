package com.house.home.client.service.evt;

public class ItemBatchHeaderQueryEvt extends BaseQueryEvt{
	
	private String batchType;
	private String itemType1;
	private String custCode;
	private Integer custWkPk;
	private String workerCode;
	private String itemBatchHeaderRemarks;
	private String searchAddress;
	private String czybh;
	private String fixAreaType;
	private String itemType12;
	private Integer prePlanAreaPk;
	private boolean isShowReplaced;
	private String itemCode;
	private String itemType2;
	private String itemDescr;
	private String alreadyReplaceStr;
	private String workType12;
	
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getAlreadyReplaceStr() {
		return alreadyReplaceStr;
	}
	public void setAlreadyReplaceStr(String alreadyReplaceStr) {
		this.alreadyReplaceStr = alreadyReplaceStr;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isShowReplaced() {
		return isShowReplaced;
	}
	public void setIsShowReplaced(boolean isShowReplaced) {
		this.isShowReplaced = isShowReplaced;
	}
	public Integer getPrePlanAreaPk() {
		return prePlanAreaPk;
	}
	public void setPrePlanAreaPk(Integer prePlanAreaPk) {
		this.prePlanAreaPk = prePlanAreaPk;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	public String getItemBatchHeaderRemarks() {
		return itemBatchHeaderRemarks;
	}
	public void setItemBatchHeaderRemarks(String itemBatchHeaderRemarks) {
		this.itemBatchHeaderRemarks = itemBatchHeaderRemarks;
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
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}


}
