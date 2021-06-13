package com.house.framework.service;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Resources;
import com.house.framework.web.form.AuthorityForm;

public interface AuthorityService {
	
	public void save(Authority authority);
	
	public void save(Authority authority, List<Resources> resourcesList);
	
	public void update(Authority authority);
	
	public void update(Authority authority, List<Resources> resourcesList);

	public void delete(List<Long> authorityIds);
	
	public Authority get(Long authorityId);
	
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page,Authority authority);
	
	public List<Authority> getAll();
	
	public List<Authority> findByMenuId(Long menuId);
	
	public List<Authority> getByCzybh(String czybh);
	
	/**
	 * 根据权限编码获取权限
	 * @param authCode
	 * @return
	 */
	public Authority getByAuthCode(String authCode);
	
	/**
	 * 根据权限名称获取权限
	 * @param authName
	 * @return
	 */
	public Authority getByAuthName(String authName);
	
	/**
	 * 根据角色ID列表获取所有的权限
	 * @param roleIdList
	 * @return
	 */
	public List<Authority> getByRoleIds(List<Long> roleIdList);
	
	/**
	 * 获取所有的权限定义的java方法
	 * @return
	 */
	public List<String> getAllUrls();
	
	/**
	 * 获取权限所拥有的URL列表
	 * @param authorityId
	 * @return
	 */
	public List<String> getUrlsByAuthorityId(Long authorityId);
	
	/**
	 * 根据条件获取所有记录(查询指定字段名)
	 * @param authority
	 * @return
	 */
	public List<Map<String, Object>> findAll(Authority authority);
	
	/**
	 * 将SQL查询List<Map<String, Object>>转换为List<AuthorityForm>
	 * @param mapList
	 * @return
	 */
	public List<AuthorityForm> convertPageList2ExcelFormList(List<Map<String, Object>> mapList);
	
	/**
	 * 
	 *功能说明:获取所有权限和地址对应的map
	 *@return Map<String,List<String>>
	 *
	 */
	public  Map<String,List<String>> getAllResources();
	
	/**
	 * 
	 *功能说明:获取所有用户的权限列表
	 *@return Map<Long,List<Authority>>
	 *
	 */
	public Map<String,List<Authority>> getAllUserAuth();
	
	// 20200415 mark by xzp
//	@SuppressWarnings("rawtypes")
//	public Page getByCzybhForApp(Page page,String czybh);
	
	// 20200415 mark by xzp
//	public boolean hasAuthForCzy(String czybh,int id);
	
	// 20200415 mark by xzp
//	public List<Map<String,Object>> getAuthorityList(String czybh,String mkdm);
	
	// 20200415 mark by xzp
//	public Page getCZYGNQX(Page page,String czybh, String mkdm);
	
}
