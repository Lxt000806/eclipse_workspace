package com.house.home.client.service.resp;
import java.util.List;
import java.util.Map;
public class EmployeePermissionResp extends BaseResponse {
	private List<Map<String,Object>> permissions;

	public List<Map<String, Object>> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Map<String, Object>> permissions) {
		this.permissions = permissions;
	}
	
}
