package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Czybm;

public interface CzybmService extends BaseService {

	/**Czybm分页信息
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Czybm czybm);
	
	/**修改操作员
	 * @param czybm
	 * @param xml
	 * @return
	 */
	public Result updateForProc(Czybm czybm, String xml, String xmlCk);

	/**根据操作员编号和密码获取操作员
	 * @param trim
	 * @param password
	 * @return
	 */
	public Czybm getByCzybhAndMm(String czybh, String mm);
	
	/**
	 * 
	 *功能说明:获取所有超级管理员的id列表
	 *@return List<Long>
	 *
	 */
	public List<String> getAllSuperAdminID();

	public List<Czybm> getSuperAdminList();

	public List<Czybm> getByRoleId(Long roleId);
	
	public List<Czybm> getByRoleCode(String roleCode);

	public boolean isSuperAdmin(String czybh);

	public Czybm getByEmnum(String number);

	/**
	 * 分配角色
	 * @param czybm
	 */
	public void assignRole(Czybm czybm);
	/**
	 * 获取操作员权限
	 */
	public List<Map<String, Object>> getPermission(String id);

	/**
	 * 设置操作员权限
	 * @param czybh
	 * @param addList
	 * @param delList
	 */
	public Result setCzybmAuths(String czybh, List<Long> addList, List<Long> delList);
	
	public List<Long> getAuthIdsByCzybh(String czybh);
	
	// 现在只用于供应商平台判断,其他还是用hasGNQXByCzybh判断  by zjf
	public boolean hasAuthByCzybh(String czybh, int authId);
	
	public String getHasAgainMan();
	
	public boolean hasGNQXByCzybh(String czybh, String mkdm, String gnmc);
	
	public List<Map<String,Object>> findGNQXByCzybhAndMkdm(String czybh,String mkdm);
	
	public List<Map<String,Object>> getCZYList(String czybh);
	
	public Result doCopyRight(Czybm czybm);
	
	public Result doAppRight(Czybm czybm);
	
	public List<Map<String, Object>> getQX_ALLMK(int ptbh);
	
	/**
	 * 获取操作员权限
	 * @param page
	 * @param czybh
	 * @param ptbh
	 * @param containRoleAuth 包含角色权限
	 * @return
	 */
	public List<Map<String, Object>> getQX_CZYQX(String czybh, int ptbh, String containRoleAuth);
	
	public boolean isHasEMNum(Czybm czybm );
	
	/**麦田表格明细
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_mtRegion(Page<Map<String,Object>> page, Czybm czybm);
	
	/**
	 * 判断操作员密码是否过期
	 * @param czybh 
	 * @return
	 */
	public boolean isPasswordExpired(String czybh);

    public Czybm findByCzybhOrPhone(String loginName);
    
    /**
	 * 获取个人权限，不包含角色权限
	 * @param czybh 
	 * @return
	 */
	public List<Long> getPersonalAuthIdsByCzybh(String czybh);
	
	/**
	 * 操作员权限明细
	 * @param page
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlAuthDetail(Page<Map<String,Object>> page, Czybm czybm);
	
	/**
	 * 操作员权限明细
	 * @param page
	 * @param ptbh
	 * @param czybm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlPlatformAuth(Page<Map<String,Object>> page, String ptbh, String czybh);
	
	/**
	 * 根据编码获得多个操作员
	 * @param czybhs
	 * @return
	 */
	public List<Map<String, Object>> getCzyByIds(String czybhs);
	
	public boolean checkIsWhiteIp(Long ip);
}
