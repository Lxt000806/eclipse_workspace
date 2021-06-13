package com.house.framework.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.entity.Resources;

@SuppressWarnings("serial")
@Repository
public class ResourcesDao extends BaseDao{

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AuthorityDao.class);
	
	/**
	 * 获取系统所有的
	 * @return
	 */
	public List<String> getAllUrls(){
		List<Resources> list = this.loadAll(Resources.class);
		return pickOutUrl(list);
	}
	
	/**
	 * 根据权限ID获取资源列表
	 * @param authorityId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Resources> getByAuthorityId(Long authorityId){
		if(authorityId == null)
			return null;
		String hql = "from Resources r where r.authorityId = ?";
		return this.find(hql, authorityId);
	}
	
	/**
	 * 获取权限所拥有的URL列表
	 * @param authorityId
	 * @return
	 */
	public List<String> getUrlsByAuthorityId(Long authorityId){
		List<Resources> list = this.getByAuthorityId(authorityId);
		return pickOutUrl(list);
	}
	
	/**
	 * 清除权限所有URL
	 * @param authorityId
	 */
	public void clearByAuthorityId(Long authorityId){
		if(authorityId == null)
			return ;
		String hql = "delete from Resources where authorityId = ?";
		this.executeUpdate(hql, authorityId);
	}
	
	public List<String> pickOutUrl(List<Resources> list){
		if(list == null || list.size() < 1)
			return null;
		List<String> urlList = new ArrayList<String>(list.size());
		String url = null;
		for(Resources resources : list){
			if(resources != null && StringUtils.isNotBlank(resources.getUrlStr())){
				url = resources.getUrlStr().trim();
				//防止重复URL
				if(urlList.indexOf(url) == -1)
					urlList.add(url);
			}
		}
		return urlList;
	}
	
	/**
	 * 
	 *功能说明:获取全部的权限地址
	 *@return Map<String,List<String>>,key为权限id，value为url列表
	 *
	 */
	@SuppressWarnings("unchecked")
	public Map<String,List<String>> getAllResources(){
		String hql = "from Resources r order by r.authorityId desc";
		//获取所有权限
		return getAuthUrlMap(this.find(hql));
		
	}
	
	/**
	 * 
	 *功能说明:将list转成权限id为key，url列表为值的map
	 *@param list
	 *@return Map<String,List<String>>key为权限id，value为url列表
	 *
	 */
	private Map<String,List<String>>getAuthUrlMap(List<Resources> list){
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		long authID = -1;
		List<Resources> resouercesList = new ArrayList<Resources>();
		int i = 0;
		for (Resources r : list) {
			if (i == 0) {
				authID = r.getAuthorityId();
				resouercesList.add(list.get(0));
			} else {
				if (r.getAuthorityId() == authID) {
					resouercesList.add(r);
				} else {
					map.put(authID + "", pickOutUrl(resouercesList));
					
					resouercesList = new ArrayList<Resources>();
					authID = r.getAuthorityId();
					resouercesList.add(r);
				}
			}
			i++;
		}
		map.put(authID+"", pickOutUrl(resouercesList));
		resouercesList = null;
		return map;
	}
}
