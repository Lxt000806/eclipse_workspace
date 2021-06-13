package com.house.home.client.service.resp;


import java.util.List;

public class BaseListQueryResp<T> extends BaseQueryResp {

	private List<T> datas;
	private List<T> datas2;

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public List<T> getDatas2() {
		return datas2;
	}

	public void setDatas2(List<T> datas2) {
		this.datas2 = datas2;
	}
}
