package com.house.home.client.service.resp;

/**
 * 新增领料回复类
 * @author lenovo
 *create by zzr 2017/12/18
 */
public class ItemPreAppAddResp extends BaseResponse {
    private String itemConfCheck; // add by zzr 2017/12/18 是否检查主材跟踪结果
    private String isExistsSaleIndependence;
    
	public String getItemConfCheck() {
		return itemConfCheck;
	}

	public void setItemConfCheck(String itemConfCheck) {
		this.itemConfCheck = itemConfCheck;
	}

	public String getIsExistsSaleIndependence() {
		return isExistsSaleIndependence;
	}

	public void setIsExistsSaleIndependence(String isExistsSaleIndependence) {
		this.isExistsSaleIndependence = isExistsSaleIndependence;
	}
    
}
