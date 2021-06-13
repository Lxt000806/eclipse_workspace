package com.house.home.client.service.resp;

@SuppressWarnings("rawtypes")
public class WaterAftInsItemResp extends BaseListQueryResp{
	
	private String descr;
	private String uom;
	private String uomDescr;
	private String no;
	private String itemType2;
	private String itemType2Descr;
	private int pk;
	private boolean isWaterAftInsItemApp;
	private Double qty;
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public boolean getIsWaterAftInsItemApp() {
		return isWaterAftInsItemApp;
	}
	public void setIsWaterAftInsItemApp(boolean isWaterAftInsItemApp) {
		this.isWaterAftInsItemApp = isWaterAftInsItemApp;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	
}
