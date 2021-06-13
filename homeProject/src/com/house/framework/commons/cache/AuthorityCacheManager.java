package com.house.framework.commons.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.entity.Authority;
import com.house.framework.service.AuthorityService;
import com.house.home.entity.basic.Czybm;
import com.house.home.service.basic.CzybmService;

/**
 * 角色组权限集合缓存管理器
 */
@Component
public class AuthorityCacheManager extends SimpleCacheManager {

	private static final Logger logger = LoggerFactory.getLogger(AuthorityCacheManager.class);

	private static final String ID_SUFFIX = "_ID";
	private static final String URL_SUFFIX = "_URL";
	private static Map<String, List<String>> authUrlMap;
//	private static Map<String, List<Authority>> userAuthMap;
	private static List<String> superAdminIDList;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private CzybmService czybmService;
	// 注意线程安全
	private List<String> allUrls;

	/**
	 * 从数据库获取数据一次性设置所有角色组权限缓存
	 */
	public void initCacheData() {
		System.out.println("###### 初始化 系统权限 数据开始 ######");

		// 获取系统所有受管理的URL链接资源
		allUrls = this.authorityService.getAllUrls();
		if (allUrls == null || allUrls.size() < 1) {
			System.out.println(" 系统权限管理未设置 权限对应匹配的URL ");
			logger.warn("系统权限管理未设置 权限对应匹配的URL");
		}

		List<Czybm> userList = this.czybmService.loadAll(Czybm.class);
		if (userList == null || userList.size() < 1) {
			System.out.println("!!!!!!!!!! 系统未设置任何用户 !!!!!!!!!!");
			logger.warn("系统未设置任何用户");
			return;
		}
		authUrlMap = authorityService.getAllResources();
//		userAuthMap = authorityService.getAllUserAuth();
		superAdminIDList = czybmService.getAllSuperAdminID();
		System.out.println("###### 初始化 系统权限 数据完成 ######");
	}

	
	@SuppressWarnings(value = "unchecked")
	public void put(Object key, Object value) {
		if (key == null) {
			return;
		}
		String userId = String.valueOf(key);
		List<Authority> authList = (List<Authority>) value;

		if (this.isSuperAdmin(userId)) {
			this.springCache.put(userId + ID_SUFFIX, this.bulidAuthCodeStrs(this.authorityService.getAll()));
			this.springCache.put(userId + URL_SUFFIX, null);
			return;
		}

		this.springCache.put(userId + ID_SUFFIX, this.bulidAuthCodeStrs(authList));

		List<String> allUrlsCopy = null;

		if (allUrls != null && allUrls.size() > 0) {
			allUrlsCopy = new ArrayList<String>();
			allUrlsCopy.addAll(allUrls);
		}
		this.springCache.put(userId + URL_SUFFIX, this.forbidVisitUrlList(authList, allUrlsCopy, authUrlMap));
	}

	/**
	 * 根据roleID 组合KEY，从重缓存中获取权限代码字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getAuthCodeStrsFormCache(Object key) {
		Assert.notNull(key, "key 不能为空");
		Object value = this.get(key);
		if (value == null)
			return null;
		return value.toString();
	}

	/**
	 * 根据roleID 组合KEY，从缓存中获取访问方法集合
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUrlStrsFromCache(Object key) {
		Assert.notNull(key, "key 不能为空");
		Object value = this.get(key);
		if (value == null)
			return null;
		return (List<String>) value;
	}

	/**
	 * 驱逐 KEY 的缓存记录
	 * 
	 * @param key
	 */
	
	public void evict(Object keyObj) {
		String key = null;
		if (keyObj == null || StringUtils.isBlank(key = keyObj.toString().trim())) {
			throw new RuntimeException("保存到缓存中，key不能为空");
		}
		String idKey = key + AuthorityCacheManager.ID_SUFFIX;
		String urlKey = key + AuthorityCacheManager.URL_SUFFIX;
		springCache.evict(idKey);
		springCache.evict(urlKey);
	}

	
	public String getCacheKey() {
		return CommonConstant.CACHE_AUTHORITY_KEY;
	}

	/**
	 * 生成权限字符串集合
	 * 
	 * @param authList
	 * @return
	 */
	private String bulidAuthCodeStrs(List<Authority> authList) {
		if (authList == null || authList.size() < 1) {
			return ",";
		}
		StringBuilder strb = new StringBuilder(",");
		for (Authority auth : authList) {
			if (auth != null) {
				String authCode = auth.getAuthCode().trim();
				if (authCode.indexOf(",") != -1) {
					logger.error("权限[" + auth.getAuthName() + "] 的编码 [" + auth.getAuthCode() + "] 存在逗号");
					throw new RuntimeException("权限[" + auth.getAuthName() + "] 编码 [" + auth.getAuthCode() + "]存在逗号");
				}
				strb.append(auth.getAuthCode().trim()).append(",");
			}
		}
		System.out.println("权限编码：" + strb);
		return strb.toString();
	}

	/**
	 * 禁止反问url列表
	 * 
	 * @param authList
	 * @param allUrlStrs
	 * @return
	 */
	private List<String> forbidVisitUrlList(List<Authority> authList, List<String> allUrlStrs,
			Map<String, List<String>> authUrlMap) {
		if (authList == null || authList.size() < 1 || allUrlStrs == null || allUrlStrs.size() < 1)
			return null;
		List<String> authUrls = null;
		for (Authority auth : authList) {
			if (auth != null) {
				authUrls = authUrlMap.get(auth.getAuthorityId() + "");
				if (authUrls != null && authUrls.size() > 0)
					allUrlStrs.removeAll(authUrls);
			}
		}
		System.out.println("禁止访问列表：" + allUrlStrs);
		return allUrlStrs;
	}

	/**
	 * 
	 * 功能说明:判断用户是不是超级管理员
	 * 
	 * @param userid
	 * @return boolean
	 * 
	 */
	private boolean isSuperAdmin(String userid) {
		boolean flag = false;
		for (String id : superAdminIDList) {
			if (StringUtils.isNotBlank(userid) && userid.equals(id)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
