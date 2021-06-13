package com.house.home.client.service.resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDeptCodeResp extends BaseResponse {
	
	private List<Map<String, Object>> deptList;

	public List<Map<String, Object>> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Map<String, Object>> deptList) {
		if(deptList != null){
			this.deptList = deptList;
		}else{
			this.deptList = new ArrayList<Map<String,Object>>();
		}
	}
	
}
