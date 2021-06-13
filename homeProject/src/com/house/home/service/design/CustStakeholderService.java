package com.house.home.service.design;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustStakeholder;

public interface CustStakeholderService extends BaseService {

	/**CustStakeholder分页信息
	 * @param page
	 * @param custStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholder custStakeholder,UserContext uc);
	/**CustStakeholder分页信息-客户信息查询
	 * @param page
	 * @param custStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, CustStakeholder custStakeholder);
	
	/**
	 * 修改CustStakeholder存储过程
	 * 
	 * @param customer
	 * @return
	 */
	public Result updateForProc(CustStakeholder custStakeholder);

	public Result doSave(CustStakeholder custStakeholder);
	
	public Result updateGcxxglDesigner(CustStakeholder custStakeholder);

	public Result doUpdateMainManager(CustStakeholder custStakeholder);
	
	public int getCount(String custCode, String role);
	
	public List<Map<String,Object>> getListByCustCodeAndRole(String custCode, String role);
	
	public int getPkByCustCodeAndRole(String custCode, String role);
	
	/**
	 * 是否显示电话
	 * @param custCode
	 * @param empCode
	 * @param status
	 * @return
	 */
	public boolean phoneShow(String custCode,String empCode,String status);
	
	public void doDelCGEmp(Integer pk);
	
	public void doDelInteEmp(String  code);

	/**
	 * 获取干系人信息
	 * @param custCode
	 * 
	 * @return
	 */
	public Map<String,Object> getStakeholderInfo(String custCode);

	public int onlyBusinessMan(String custCode);
	
	public int onlyDesigner(String custCode);
	
	public String getRoleByCustCodeAndEmpCode(String custCode, String empCode);
	/**
	 * 通过角色获取链接起来的干系人
	 * @author	created by zb
	 * @date	2020-4-24--下午5:47:21
	 * @param custCode
	 * @param role
	 * @return
	 */
	public String getStakeholderLinkedByRole(String custCode, String role);
	
	public boolean hasCustAuthorityByRole(String custCode, String role, UserContext uc);
	
}
