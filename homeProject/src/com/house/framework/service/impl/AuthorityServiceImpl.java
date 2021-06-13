package com.house.framework.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.google.common.collect.Lists;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.dao.AuthorityDao;
import com.house.framework.dao.ResourcesDao;
import com.house.framework.dao.RoleAuthorityDao;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Resources;
import com.house.framework.service.AuthorityService;
import com.house.framework.web.form.AuthorityForm;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);

	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	@Autowired
	private ResourcesDao resourcesDao;
	
	//@LoggerAnnotation(okLog = "添加 ID=$args[0].authorityId authName=$args[0].authName 成功", errLog="添加 authName=$args[0].authName 失败",  modelCode = LogModuleConstant.AUTHORITY_MODULE )
	public void save(Authority authority) {
		Assert.notNull(authority, "权限不能为空");
		Assert.notNull(authority.getAuthCode(), "权限编码不能为空");
		Assert.notNull(authority.getUrlStr(), "权限链接不能为空");
		authority.setGenTime(new Date());
		if(authority.getOrderNo() == null)
			authority.setOrderNo(0L);
		if(this.authorityDao.getByAuthCode(authority.getAuthCode()) != null){
			throw new RuntimeException("编码已存在");
		}
		Reflections.trim(authority);
		
		this.authorityDao.save(authority);
	}
	
	//@LoggerAnnotation(okLog = "添加 ID=$args[0].authorityId authName=$args[0].authName 成功", errLog="添加 authName=$args[0].authName 失败",  modelCode = LogModuleConstant.AUTHORITY_MODULE )
	public void save(Authority authority, List<Resources> resourcesList){
		this.save(authority);
		if(resourcesList != null && resourcesList.size() > 0){
			for(Resources resources : resourcesList){
				if(resources != null){
					Assert.notNull(resources.getUrlStr());
					formatUrl(resources);
					resources.setAuthorityId(authority.getAuthorityId());
					resources.setGenTime(new Date());
					this.resourcesDao.save(resources);
				}
			}
		}
	}
	
	//@LoggerAnnotation(okLog = "更新 ID=$args[0].authorityId authName=$args[0].authName 成功", errLog="更新 ID=$args[0].authorityId authName=$args[0].authName 失败",  modelCode = LogModuleConstant.AUTHORITY_MODULE )
	public void update(Authority authority) {
		Assert.notNull(authority,"权限不能为空");
		if(authority.getOrderNo() == null)
			authority.setOrderNo(0L);
		authority.setUpdateTime(new Date());
		Reflections.trim(authority);
		this.authorityDao.update(authority);
	}
	
	//@LoggerAnnotation(okLog = "更新 ID=$args[0].authorityId authName=$args[0].authName 成功", errLog="更新 ID=$args[0].authorityId authName=$args[0].authName 失败",  modelCode = LogModuleConstant.AUTHORITY_MODULE )
	public void update(Authority authority, List<Resources> resourcesList){
		this.update(authority);
		this.resourcesDao.clearByAuthorityId(authority.getAuthorityId());
		if(resourcesList != null && resourcesList.size() > 0){
			for(Resources resources : resourcesList){
				if(resources != null){
					Assert.notNull(resources.getUrlStr());
					formatUrl(resources);
					resources.setAuthorityId(authority.getAuthorityId());
					resources.setGenTime(new Date());
					this.resourcesDao.save(resources);
				}
			}
		}
	}

	//@LoggerAnnotation(okLog = "删除 ID=$args[0] 成功", errLog="删除 ID=$args[0] 失败",  modelCode = LogModuleConstant.AUTHORITY_MODULE )
	public void delete(List<Long> authorityIds) {
		if(authorityIds == null || authorityIds.size()<1)
			return ;
		for(Long authorityId : authorityIds){
			if(authorityId != null){
				logger.debug("删除权限 ID={}，同时清除角色权限分配表相关记录", authorityId);
				this.authorityDao.delete(this.get(authorityId));
				this.roleAuthorityDao.clearByAuthorityId(authorityId);
				this.resourcesDao.clearByAuthorityId(authorityId);
			}
		}
	}
	
	public Authority get(Long authorityId) {
		Assert.notNull(authorityId,"权限ID不能为空");
		Authority authority = this.authorityDao.get(Authority.class, authorityId);
		if(authority == null)
			return null;
		List<Resources> resources = this.resourcesDao.getByAuthorityId(authorityId);
		authority.setResourcesList(resources);
		return authority;
	}

	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, Authority authority) {
		Assert.notNull(authority, "权限对象不能为空");
		return this.authorityDao.findPage(page, authority);
	}

	@SuppressWarnings(value="unchecked")
	public List<Authority> findByMenuId(Long menuId) {
		String hql = "from Authority where menuId = ?";
		return this.authorityDao.find(hql, menuId);
	}

	@SuppressWarnings(value="unchecked")
	public List<Authority> getAll() {
		String hql = "from Authority a order by a.orderNo";
		return this.authorityDao.find(hql);
	}

	public List<Authority> getByCzybh(String czybh){
		if(StringUtils.isBlank(czybh))
			throw new RuntimeException("操作员编号不能为空");
		return this.authorityDao.getByCzybh(czybh);
	}

	public Authority getByAuthCode(String authCode) {
		if(StringUtils.isBlank(authCode)){
			logger.warn("权限编码为空");
			return null;
		}
		return this.authorityDao.getByAuthCode(authCode);
	}

	public Authority getByAuthName(String authName) {
		if(StringUtils.isBlank(authName))
			return null;
		return this.authorityDao.getByAuthName(authName);
	}

	public List<Authority> getByRoleIds(List<Long> roleIdList) {
		if(roleIdList == null || roleIdList.size() < 1)
			return null;
		return this.authorityDao.getByRoleIds(roleIdList);
	}
	
	public List<String> getAllUrls(){
		return this.resourcesDao.getAllUrls();
	}
	
	public List<String> getUrlsByAuthorityId(Long authorityId){
		if(authorityId == null)
			return null;
		return this.resourcesDao.getUrlsByAuthorityId(authorityId);
	}

	public List<Map<String, Object>> findAll(Authority authority) {
		return authorityDao.findAll(authority);
	}
	
	private void formatUrl(Resources resources){
		String url = resources.getUrlStr().trim();
		if(!url.startsWith("/")){
			resources.setUrlStr("/"+url);
		}
		//去掉最末尾 /
		while(url.endsWith("/")){
			url = url.substring(0, url.length()-2).trim();
		}
		resources.setUrlStr(url);
	}

	public List<AuthorityForm> convertPageList2ExcelFormList(List<Map<String, Object>> mapList) {
		//完成List<Map<String, Object>>适配到List<ExcelForm>, 建议在service层重构该段语句
		List<AuthorityForm> excelList = Lists.newArrayList();
		for(int i=0; i<mapList.size(); i++){
			Map<String, Object> map = (Map<String, Object>)mapList.get(i);
			AuthorityForm excelForm = new AuthorityForm();
			
			excelForm.setIndex(new Long(i + 1));
			excelForm.setAuthCode((String)map.get("AUTH_CODE"));
			excelForm.setAuthName((String)map.get("AUTH_NAME"));
			excelForm.setMenuName((String)map.get("MENU_NAME"));
			excelForm.setGenTime(DateUtil.timeStampToDate((Timestamp)map.get("GEN_TIME")));
			excelForm.setUpdateTime(DateUtil.timeStampToDate((Timestamp)map.get("UPDATE_TIME")));
			excelForm.setMenuId(((Integer)map.get("MENU_ID")).longValue());
			excelForm.setAuthorityId(((Integer)map.get("AUTHORITY_ID")).longValue());
			excelList.add(excelForm);
		}
		return excelList;
	}

	
	public Map<String, List<String>> getAllResources() {
		return resourcesDao.getAllResources();
	}

	
	public Map<String, List<Authority>> getAllUserAuth() {
		return authorityDao.getAllUserAuth();
	}

	// 20200415 mark by xzp
//	@SuppressWarnings("rawtypes")
//	public Page getByCzybhForApp(Page page,String czybh) {
//		return authorityDao.getByCzybhForApp(page,czybh);
//	}
	
	// 20200415 mark by xzp
//	@SuppressWarnings("rawtypes")
//	public Page getCZYGNQX(Page page,String czybh, String mkdm) {
//		return authorityDao.getCZYGNQX(page,czybh, mkdm);
//	}
	
	// 20200415 mark by xzp
//	public boolean hasAuthForCzy(String czybh,int id){
//		return authorityDao.hasAuthForCzy(czybh, id);
//	}
	
	// 20200415 mark by xzp
//	public List<Map<String,Object>> getAuthorityList(String czybh,String mkdm){
//		return authorityDao.getAuthorityList(czybh,mkdm);
//	}

}
