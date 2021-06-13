package com.house.framework.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.entity.Authority;

@SuppressWarnings("serial")
@Repository
public class AuthorityDao extends BaseDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AuthorityDao.class);

	/**
	 * 分页列表
	 * 
	 * @param page
	 * @param authority
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPage(Page page, Authority authority) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select t.*," + " (select m.menu_name "
				+ "		from tsys_menu m "
				+ "	where m.menu_id = t.menu_id ) as MENU_NAME"
				+ " from tsys_authority t" + " where 1 = 1 ";

		if (StringUtils.isNotBlank(authority.getAuthName())) {
			sql += " and t.auth_name like ? ";
			list.add("%" + authority.getAuthName().trim() + "%");
		}
		if (StringUtils.isNotBlank(authority.getAuthCode())) {
			sql += " and upper(t.AUTH_CODE) like ? ";
			list.add("%" + authority.getAuthCode().trim().toUpperCase() + "%");
		}
		if (authority.getMenuId() != null) {
			sql += " and t.menu_id in (select * from fGetChildList(?))"; // fGetChildList为函数
			list.add(authority.getMenuId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.auth_code";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findAll(Authority authority) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select t.*," + " (select m.menu_name "
				+ "		from tsys_menu m "
				+ "	where m.menu_id = t.menu_id ) as MENU_NAME"
				+ " from tsys_authority t" + " where 1 = 1 ";

		if (StringUtils.isNotBlank(authority.getAuthName())) {
			sql += " and t.auth_name like ? ";
			list.add("%" + authority.getAuthName().trim() + "%");
		}
		if (StringUtils.isNotBlank(authority.getUrlStr())) {
			sql += " and t.url_str like ? ";
			list.add("%" + authority.getUrlStr().trim() + "%");
		}
		if (authority.getMenuId() != null) {
			sql += " and t.menu_id in (select * from fGetChildList(?))"; // fGetChildList为函数

			list.add(authority.getMenuId());
		}
		sql += " order by t.auth_code";

		return findListsByNativeSQL(sql, list.toArray());
	}

	/**
	 * 获取分配给用户的所有权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<Authority> getByCzybh(String czybh) {
//		String sql = "select t.* from tsys_authority t, "
//				+ "       (select distinct(t3.authority_id) as authority_id"
//				+ "          from tsys_role_user t2, tsys_role_authority t3"
//				+ "         where t2.czybh = ?"
//				+ "           and t3.role_id = t2.role_id) t1 "
//				+ " where t.authority_id = t1.authority_id "
//				+ " order by t.auth_code";
		
		String sql = "select t.* from tsys_authority t,(select a.authority_id "
				+"from TSYS_ROLE_AUTHORITY a where a.role_Id in (select b.role_Id from TSYS_ROLE_USER b where b.czybh=?) "
				+"union select authority_id from TSYS_CZYBM_AUTHORITY where CZYBH=?) m where t.authority_id=m.authority_id";

		return this.mapper(this.findBySql(sql, new Object[] { czybh, czybh }));
	}

	/**
	 * 获取分配到角色上的所有权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Authority> getByRoleId(Long roleId) {
		String sql = "select a.* from tsys_role_authority ra, tsys_authority a"
				+ " where ra.role_id = ?"
				+ "   and ra.authority_id = a.authority_id"
				+ " order by a.auth_code";
		return this.mapper(this.findBySql(sql, new Object[] { roleId }));
	}

	/**
	 * 根据角色ID列表获取所有的权限
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<Authority> getByRoleIds(List<Long> roleIdList) {
		if (roleIdList == null || roleIdList.size() < 1)
			return null;
		String sql = "select t.*  from tsys_authority t, "
				+ "       (select distinct(t1.authority_id) as authority_id  from tsys_role_authority t1"
				+ "         where t1.role_id in (";
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < roleIdList.size(); i++) {
			Long roleId = roleIdList.get(i);
			if (roleId != null) {
				sql += "?";
				list.add(roleId);
			}
			if (i != roleIdList.size() - 1) {
				sql += ",";
			}
		}
		sql += ") )t2 " + " where t.authority_id = t2.authority_id "
				+ " order by t.auth_code";
		return this.mapper(this.findBySql(sql, list.toArray()));
	}

	/**
	 * 根据权限名称获取权限
	 * 
	 * @param authName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Authority getByAuthName(String authName) {
		String hql = "from Authority a where a.authName = ?";
		List<Authority> list = this.find(hql, new Object[] { authName });
		if (null == list || list.size() < 1)
			return null;
		return list.get(0);
	}

	/**
	 * 根据权限编码获取权限
	 * 
	 * @param authCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Authority getByAuthCode(String authCode) {
		String hql = "from Authority a where a.authCode = ?";
		List<Authority> list = this.find(hql, new Object[] { authCode });
		if (null == list || list.size() < 1)
			return null;
		return list.get(0);
	}

	/**
	 * 转换方法
	 * 
	 * @param list
	 * @return
	 */
	public List<Authority> mapper(List<Map<String, Object>> list) {
		if (null == list || list.size() < 1)
			return null;
		List<Authority> rs = new ArrayList<Authority>(list.size());
		Authority auth = null;
		for (Map<String, Object> map : list) {
			auth = this.mapper(map);
			if (auth != null)
				rs.add(auth);
		}
		return rs;
	}

	public Authority mapper(Map<String, Object> map) {
		if (null == map)
			return null;
		Authority authority = new Authority();
		Long authId = null;
		Object auth = map.get("AUTHORITY_ID");
		if (auth != null) {
			Integer bd = (Integer) auth;
			authId = bd.longValue();
		}

		authority.setAuthorityId(authId);
		authority.setAuthName((String) map.get("AUTH_NAME"));
		authority.setAuthCode((String) map.get("AUTH_CODE"));
		authority.setGenTime(DateUtil.timeStampToDate((Timestamp) map
				.get("GEN_TIME")));
		authority.setJavaFun((String) map.get("JAVA_FUN"));

		Long menuId = null;
		Object menu = map.get("MENU_ID");
		if (menu != null) {
			Integer bd = (Integer) menu;
			menuId = bd.longValue();
		}
		authority.setMenuId(menuId);

		Long orderNo = 0L;
		Object order = map.get("ORDER_NO");
		if (order != null) {
			Integer bd = (Integer) order;
			orderNo = bd.longValue();
		}
		authority.setOrderNo(orderNo);
		authority.setRemark((String) map.get("REMARK"));
		authority.setUpdateTime(DateUtil.timeStampToDate((Timestamp) map
				.get("UPDATE_TIME")));
		authority.setUrlStr((String) map.get("URL_STR"));

		return authority;
	}

	public Map<String, List<Authority>> getAllUserAuth() {

//		String sql = "select t.*,t1.czybh czybh"
//				+ "  from tsys_authority t, "
//				+ "       (select distinct(t3.authority_id) as authority_id,t2.czybh"
//				+ "          from tsys_role_user t2, tsys_role_authority t3"
//				+ "         where  t3.role_id = t2.role_id) t1 "
//				+ " where t.authority_id = t1.authority_id "
//				+ " order by t1.czybh desc";
		
		String sql = "select t.*,m.czybh from tsys_authority t,(select distinct b.CZYBH,a.AUTHORITY_ID "
				+"from TSYS_ROLE_AUTHORITY a,TSYS_ROLE_USER b "
				+"where a.ROLE_ID=b.ROLE_ID "
				+"union select czybh,authority_id from TSYS_CZYBM_AUTHORITY) m where t.authority_id=m.authority_id";

		return getAuthUrlMap(this.findBySql(sql));

	}

	/**
	 * 
	 * 功能说明:将list转成权限id为key，url列表为值的map
	 * 
	 * @param list
	 * @return Map<String,List<String>>key为权限id，value为url列表
	 * 
	 */
	private Map<String, List<Authority>> getAuthUrlMap(
			List<Map<String, Object>> list) {
		Map<String, List<Authority>> map = new HashMap<String, List<Authority>>();
		String userID = "";
		List<Authority> authorityList = new ArrayList<Authority>();
		int i = 0;

		for (Map<String, Object> mapObj : list) {
			String id = null;
			Object auth = mapObj.get("czybh");
			if (auth != null) {
				id = String.valueOf(auth);
			}

			if (i == 0) {
				if (StringUtils.isNotBlank(id)) {
					userID = id;
				}
				authorityList.add(this.mapper(mapObj));
			} else {
				if (StringUtils.isNotBlank(id) && userID.equals(id)) {
					authorityList.add(this.mapper(mapObj));
				} else {

					map.put(userID, authorityList);
					authorityList = new ArrayList<Authority>();
					if (StringUtils.isNotBlank(id)) {
						userID = id;
					}
					authorityList.add(this.mapper(mapObj));
				}
			}
			i++;
		}
		map.put(userID, authorityList);
		authorityList = null;
		return map;
	}
	
	// 20200415 mark by xzp
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Page getByCzybhForApp(Page page,String czybh) {
//		String sql = "select GNMC from tCZYGNQX  where CZYBH=? and MKDM='0820' ";
//		return this.findPageBySql(page, sql, new Object[] {czybh});
//	}
	
	// 20200415 mark by xzp
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Page getCZYGNQX(Page page,String czybh, String mkdm) {
//		String sql = "select distinct GNMC from ( "
//				   + "	select GNMC from tCZYGNQX  where CZYBH=? and MKDM=?  "
//				   + "	union "
//				   + "	select GNMC from tRoleGNQX a where exists( "
//				   + "		select 1 from TSYS_ROLE_USER in_a where in_a.CZYBH=? and in_a.ROLE_ID = a.ROLE_ID "
//				   + "	) and a.MKDM=? "
//				   + ") a ";
//		return this.findPageBySql(page, sql, new Object[] {czybh,mkdm,czybh,mkdm});
//	}
	
	// 20200415 mark by xzp
//	/**
//	 * 判断某个操作员是否有某个权限
//	 * @param czybh 操作员编号
//	 * @param id 权限id
//	 * @return
//	 */
//	public boolean hasAuthForCzy(String czybh,int id){
//		String sql = "select AUTHORITY_ID from TSYS_CZYBM_AUTHORITY  where CZYBH=? and AUTHORITY_ID=? ";
//		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh,id});
//		if (list!=null && list.size()>0){
//			return true;
//		}
//		return false;
//	}
	
	// 20200415 mark by xzp
//	public List<Map<String,Object>> getAuthorityList(String czybh,String mkdm) {
//		String sql = "select GNMC from tCZYGNQX  where CZYBH=? and MKDM=? ";
//		return this.findBySql(sql, new Object[] {czybh,mkdm});
//	}
}
