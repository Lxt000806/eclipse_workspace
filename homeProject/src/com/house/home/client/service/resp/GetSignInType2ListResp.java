package com.house.home.client.service.resp;

public class GetSignInType2ListResp extends BaseResponse {
	
	private String code;
	private String descr;
	private String isNeedPic;
	private String isNeedPrjItem;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getIsNeedPic() {
		return isNeedPic;
	}
	public void setIsNeedPic(String isNeedPic) {
		this.isNeedPic = isNeedPic;
	}
	public String getIsNeedPrjItem() {
		return isNeedPrjItem;
	}
	public void setIsNeedPrjItem(String isNeedPrjItem) {
		this.isNeedPrjItem = isNeedPrjItem;
	}

	
}
