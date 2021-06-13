package com.house.home.client.service.evt;

public class ItemBatchDetailQueryEvt extends BaseQueryEvt{
	
	private String ibdno;
	private String itemType1;
	private String descr;
	private String isSetItem;
	private String workType12;

	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getIbdno() {
		return ibdno;
	}
	public void setIbdno(String ibdno) {
		this.ibdno = ibdno;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

}
