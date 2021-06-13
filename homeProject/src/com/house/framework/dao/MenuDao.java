package com.house.framework.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;

@SuppressWarnings("serial")
@Repository
public class MenuDao extends BaseDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MenuDao.class);

	@SuppressWarnings("unchecked")
	public List<Menu> getByMenuType(String menuType) {
		String hql = "from Menu m ";
		if (StringUtils.isNotBlank(menuType)) {
			hql += " where m.menuType = ?";
		}
		hql += " order by m.orderNo";
		return this.find(hql, new Object[] { menuType });
	}

	/**
	 * 根据菜单名获取菜单对象
	 * 
	 * @param menuName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Menu getByMenuName(String menuName) {
		String hql = "from Menu m where m.menuName = ?";
		List<Menu> list = this.find(hql, menuName.trim());
		if (list == null || list.size() < 1)
			return null;
		return list.get(0);
	}

	/**
	 * 根据用户id查询菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> getByCzybh(String czybh) {
		String sql1 = "select distinct (t0.menu_id) MENU_ID from tsys_authority t0, "
				+ "  (select distinct (t3.authority_id) as authority_id from tsys_role_user t2, tsys_role_authority t3 where t2.czybh=? and t3.role_id = t2.role_id"
				+" union select authority_id from TSYS_CZYBM_AUTHORITY where CZYBH=?) t1 "
				+ "  where t0.authority_id = t1.authority_id";
		List<Map<String, Object>> menuIds = super.findBySql(sql1, new Object[]{czybh,czybh});
		if (menuIds != null && menuIds.size() > 0) {
			HashSet<Menu> menuList = new HashSet<Menu>();
			List<Menu> result = new ArrayList<Menu>();
			for (Map<String, Object> menuIdMap : menuIds) {
				Integer menuId = Integer.parseInt(menuIdMap.get("MENU_ID")
						.toString());
				List<Menu> list = mapper(getParentMenusByMenu(menuId
						.longValue()));
				if (list != null) {
					menuList.addAll(list);
				}
			}
			result.addAll(menuList);
			Collections.sort(result, new Comparator<Menu>() {
				/** 根据元注释order 排列顺序 */
				public int compare(Menu o1, Menu o2) {
					Long order1 = o1.getMenuId().longValue();
					Long order2 = o2.getMenuId().longValue();
					return order1.compareTo(order2);
				}
			});
			return result;
		}
		return new ArrayList<Menu>();
	}
	
	/**
	 * 根据操作员编号查询此操作员被允许访问的菜单
	 * 张海洋 20210604
	 * 
	 * @param czybh
	 * @return
	 */
	public List<Menu> findAllowedMenusByCzybh(String czybh) {
	    String sql = ""
	            + "with Query as ( "
	            + "    select c.MENU_ID, c.PARENT_ID "
	            + "    from TSYS_AUTHORITY a "
	            + "    inner join ( "
	            + "        select in_a.CZYBH, in_a.AUTHORITY_ID "
	            + "        from TSYS_CZYBM_AUTHORITY in_a "
	            + "        union "
	            + "        select in_b.CZYBH, in_c.AUTHORITY_ID "
	            + "        from TSYS_ROLE_USER in_b "
	            + "        inner join TSYS_ROLE_AUTHORITY in_c on in_c.ROLE_ID = in_b.ROLE_ID "
	            + "    ) b on b.AUTHORITY_ID = a.AUTHORITY_ID "
	            + "    inner join TSYS_MENU c on c.MENU_ID = a.MENU_ID "
	            + "    where b.CZYBH = ? "
	            + "     "
	            + "    union all "
	            + "     "
	            + "    select d.MENU_ID, d.PARENT_ID "
	            + "    from TSYS_MENU d "
	            + "    inner join Query on Query.PARENT_ID = d.MENU_ID "
	            + ") "
	            + "select M.* "
	            + "from TSYS_MENU M "
	            + "where exists(select 1 from Query Q where Q.MENU_ID = M.MENU_ID) ";
	    
	    return mapper(findBySql(sql, czybh));
	}
	
	/**
	 * 根据操作员编号查询此操作员被禁止访问的菜单
	 * 张海洋 
	 * 
	 * @param czybh
	 * @return
	 */
	public List<Menu> findForbiddenMenusByCzybh(String czybh) {
	    String sql = ""
	            + "with Query as ( "
	            + "    select c.MENU_ID, c.PARENT_ID "
	            + "    from TSYS_AUTHORITY a "
	            + "    inner join ( "
	            + "        select in_a.CZYBH, in_a.AUTHORITY_ID "
	            + "        from TSYS_CZYBM_AUTHORITY in_a "
	            + "        union "
	            + "        select in_b.CZYBH, in_c.AUTHORITY_ID "
	            + "        from TSYS_ROLE_USER in_b "
	            + "        inner join TSYS_ROLE_AUTHORITY in_c on in_c.ROLE_ID = in_b.ROLE_ID "
	            + "    ) b on b.AUTHORITY_ID = a.AUTHORITY_ID "
	            + "    inner join TSYS_MENU c on c.MENU_ID = a.MENU_ID "
	            + "    where b.CZYBH = ? "
	            + "     "
	            + "    union all "
	            + "     "
	            + "    select d.MENU_ID, d.PARENT_ID "
	            + "    from TSYS_MENU d "
	            + "    inner join Query on Query.PARENT_ID = d.MENU_ID "
	            + ") "
	            + "select M.* "
	            + "from TSYS_MENU M "
	            + "where not exists(select 1 from Query Q where Q.MENU_ID = M.MENU_ID) ";
	    
	    return mapper(findBySql(sql, czybh));
	}

	/**
	 * 由子菜单获取其根tab类型菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public Menu getTabMenuBySubMenu(Long menuId) {
		String sql = "select t.* from (select m.*  from tsys_menu m"
				+ " where m.menu_id in (select * from fGetParentMenuList(?))) t"
				+ " where t.menu_type = ?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				menuId, DictConstant.DICT_MENU_TYPE_TAB });
		if (list == null || list.size() < 1)
			return null;
		return this.mapper(list.get(0));
	}

	/**
	 * 根据当前用户ID, 和标签菜单ID, 查询所有叶子结点
	 * 
	 * @param userId
	 * @param menu_id
	 * @return
	 */
	public List<Map<String, Object>> getLeafMenusByUser(String czybh,
			Long menuId) {
		UserContext userContext = UserContextHolder.getUserContext();
		List<Map<String, Object>> menus = null;
		String SqlStr = "";

		if (userContext.isOneAdmin() || userContext.isSuperAdmin()) {
			SqlStr = " select *	 from tsys_menu m where 1 = 1 and m.menu_type = ?  and m.tab_menu_id = ? ";

			menus = this.findBySql(SqlStr, new Object[] {
					DictConstant.DICT_MENU_TYPE_URL, menuId });
		}

		else {
			SqlStr = " select m.* from tsys_menu m,									"
					+ "       (select distinct a.menu_id						"
					+ "          from tCZYBM           u,					"
					+ "               tsys_role_user      ru,					"
					+ "               tsys_role           r,					"
					+ "               tsys_role_authority ra,					"
					+ "               tsys_authority      a					"
					+ "         where 1 = 1									"
					+ "           and u.czybh=?								"
					+ "           and u.czybh = ru.czybh					"
					+ "           and ru.role_id = r.role_id					"
					+ "           and r.role_id = ra.role_id					"
					+ "           and ra.authority_id = a.authority_id) au1	"
					+ " where 1 = 1											"
					+ "   and m.menu_id = au1.menu_id							"
					+ "   and m.menu_type = ?									"
					+ "   and m.tab_menu_id = ?								";

			menus = this.findBySql(SqlStr, new Object[] { czybh,
					DictConstant.DICT_MENU_TYPE_URL, menuId });
		}

		return menus;
	}

	public List<Map<String, Object>> getLeafMenusByUser(String czybh) {
		List<Map<String, Object>> menus = null;
		String SqlStr = "";
		SqlStr = " select m.*											" + "  from tsys_menu m,									"
				+ "       (select distinct a.menu_id						"
				+ "          from tsys_user           u,					"
				+ "               tsys_role_user      ru,					"
				+ "               tsys_role           r,					"
				+ "               tsys_role_authority ra,					"
				+ "               tsys_authority      a					"
				+ "         where 1 = 1									"
				+ "           and u.user_id=?								"
				+ "           and u.user_id = ru.user_id					"
				+ "           and ru.role_id = r.role_id					"
				+ "           and r.role_id = ra.role_id					"
				+ "           and ra.authority_id = a.authority_id) au1	"
				+ " where 1 = 1											"
				+ "   and m.menu_id = au1.menu_id							"
				+ "   and m.menu_type = ?									";

		menus = this.findBySql(SqlStr, new Object[] { czybh,
				DictConstant.DICT_MENU_TYPE_URL });
		return menus;
	}

	/**
	 * 根据父菜单ID, 取得所有子菜单ID,及自身ID
	 * 
	 * @param menu_id
	 * @return
	 */
	public List<Map<String, Object>> getChildMenusByMenu(Long menuId) {
		String SqlStr = "select * from tsys_menu m where m.menu_id in (select * form fGetChildList(?)) order by m.menu_id";

		return findBySql(SqlStr, new Object[] { menuId });
	}

	/**
	 * 根据父菜单ID, 取得所有子菜单ID,及自身ID
	 * 
	 * @param menu_id
	 * @return
	 */
	public List<Map<String, Object>> getParentMenusByMenu(Long menuId) {
		String SqlStr = "select * from tsys_menu m where m.menu_id in (select * from fGetParentMenuList(?)) order by m.order_no";

		return findBySql(SqlStr, new Object[] { menuId });
	}

	/**
	 * 取得标签级菜单
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTabMenus() {

		String sqlStr = "select * from tsys_menu m	where m.menu_type = ? order by m.menu_id	";

		return findBySql(sqlStr, DictConstant.DICT_MENU_TYPE_TAB);
	}

	/**
	 * 
	 * @Title: getAllMenus
	 * @Description: 返回所有的菜单列表
	 * @return List<Map<String,Object>> 返回类型
	 * @throws
	 */
	public List<Map<String, Object>> getAllMenus() {
		String sqlStr = " select * from tsys_menu order by menu_id ";
		return this.findBySql(sqlStr);
	}

	/**
	 * 获取下一个排序号
	 * 
	 * @param parentId
	 * @return
	 */
	public int getNextNum(Long parentId) {
		if (parentId == null)
			parentId = 0L;
		int nextNum = 0;
		String sql = "select max(t.order_no) as NEXTNUM from tsys_menu t where t.parent_id = ?";
		List<Map<String, Object>> list = this.findBySql(sql, parentId);
		if (list == null || list.size() < 0)
			return nextNum;
		Object obj = list.get(0).get("NEXTNUM");
		if (obj == null)
			return nextNum;
		try {
			nextNum = Integer.parseInt(obj.toString()) + 1;
		} catch (NumberFormatException e) {
			nextNum = 0;
			e.printStackTrace();
		}
		return nextNum;
	}

	public List<Menu> mapper(List<Map<String, Object>> list) {
		if (list == null || list.size() < 1)
			return null;
		List<Menu> menuList = new ArrayList<Menu>(list.size());
		Menu menu = null;
		for (Map<String, Object> map : list) {
			menu = this.mapper(map);
			if (menu != null) {
				menuList.add(menu);
			}
		}
		return menuList;
	}

	public Menu mapper(Map<String, Object> map) {
		if (map == null)
			return null;

		Menu menu = new Menu();
		Long menuId = null;
		Object menuID = map.get("MENU_ID");
		if (menuID != null) {
			Integer bd = (Integer) menuID;
			menuId = bd.longValue();
		}
		menu.setMenuId(menuId);

		Long parentId = null;
		Object parentID = map.get("PARENT_ID");
		if (parentID != null) {
			Integer bd = (Integer) parentID;
			parentId = bd.longValue();
		}
		menu.setParentId(parentId);
		menu.setMenuName((String) map.get("MENU_NAME"));
		menu.setMenuUrl((String) map.get("MENU_URL"));
		menu.setMenuType((String) map.get("MENU_TYPE"));
		menu.setOpenIcon((String) map.get("OPEN_ICON"));
		menu.setCloseIcon((String) map.get("CLOSE_ICON"));

		Long orderNo = 0L;
		Object orderNoID = map.get("ORDER_NO");
		if (orderNoID != null) {
			Integer bd = (Integer) orderNoID;
			orderNo = bd.longValue();
		}
		menu.setOrderNo(orderNo);

		menu.setGenTime(DateUtil.timeStampToDate((Timestamp) map
				.get("GEN_TIME")));
		menu.setUpdateTime(DateUtil.timeStampToDate((Timestamp) map
				.get("UPDATE_TIME")));
		menu.setRemark((String) map.get("REMARK"));

		Long tabMenuId = null;
		Object tabMenuID = map.get("TAB_MENU_ID");
		if (tabMenuID != null) {
			Integer bd = (Integer) tabMenuID;
			tabMenuId = bd.longValue();
		}
		menu.setTabMenuId(tabMenuId);
		menu.setOpenType((String) map.get("OPEN_TYPE"));
		menu.setSysCode((String) map.get("SYS_CODE"));
		return menu;
	}

	/**
	 * 获取平台菜单，且菜单的父菜单为0或者菜单的父菜单在平台菜单中
	 * @param czylb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getBySysCode(String czylb) {
		String hql = "from Menu m where (m.sysCode=? or m.sysCode=?) "
		+"and exists(select n.menuId from Menu n "
		+"where (n.sysCode=? or n.sysCode=?) and (m.parentId='0' or m.parentId=n.menuId)) "
		+"order by m.parentId, m.orderNo, m.menuId";
		return this.find(hql, new Object[]{czylb,CommonConstant.PLATFORM_ALL,czylb,CommonConstant.PLATFORM_ALL});
	}
	/**
	 * 根据结果集返回自定义菜单列表
	 * @param list 结果集
	 * @param url 菜单链接
	 * @param menuId 菜单id
	 * @param preId  父菜单id
	 * @param menuName 菜单名
	 * @param preName  父菜单名 
	 * @param menuType 菜单类型  null表示使用默认值
	 * @param preType  父菜单类型 null表示使用默认值
	 * @param openType 打开类型   null表示使用默认值
	 * @return
	 */
	public List<Menu> getMenuListByList(List<Map<String,Object>> list,String url,String menuId,String preId,String menuName,String preName,String menuType,String preType,String openType){
		
		List<Menu> rlist=new ArrayList<Menu>();
		Set<String> set=new LinkedHashSet<String>();
		for(Map<String,Object> map:list){
			set.add(map.get(preId)+","+map.get(preName));
		}
		for(Map<String,Object> map:list){
			if(map.get(menuId)==null){
				continue;
			}
			Menu menu=new Menu();
			menu.setMenuIdStr( map.get(menuId).toString());
			menu.setMenuName((String) map.get(menuName));
			if(menuType==null) menu.setMenuType("DICT_MENU_TYPE_URL");
			else menu.setMenuType(menuType);
			menu.setMenuUrl(url);
			if(openType==null) menu.setOpenType("DICT_MENU_OPEN_INNER");
			else menu.setOpenType(openType);
			//menu.setTabMenuId(Long.parseLong( map.get("code").toString()));
			menu.setParentIdStr(map.get(preId).toString());
			rlist.add(menu);
		}
		for(String str:set){
			Menu menu=new Menu();
			menu.setMenuIdStr(str.substring(0, str.indexOf(",")));
			//menu.setTabMenuId(Long.parseLong(str.substring(0, str.indexOf(","))));
			menu.setMenuName(str.substring(str.indexOf(",")+1));
			if(preType==null) menu.setMenuType("DICT_MENU_TYPE_TAB");
			else menu.setMenuType(preType);
			if(openType==null) menu.setOpenType("DICT_MENU_OPEN_INNER");
			else menu.setOpenType(openType);
			rlist.add(menu);
		}
		return rlist;
	}

	@SuppressWarnings("unchecked")
	public Menu findByUrl(String id) {
		if (StringUtils.isBlank(id)){
			return null;
		}
		String hql = "from Menu where menuUrl like ?";
		String str = "/admin/"+id+"/%";
		List<Menu> list = this.find(hql,new Object[]{str});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Menu menu) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (SELECT replace(substring(a.menu_url,8,charindex('/',substring(a.menu_url,8,len(a.menu_url)))),'/','') menuUrl,a.menu_name menuName,a.menu_id "
				+"FROM tsys_menu a where len(a.menu_url)>0 ";

		if (StringUtils.isNotBlank(menu.getMenuName())) {
			sql += " and a.menu_name like ? ";
			list.add("%"+menu.getMenuName()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.menu_id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Menu> getAllMenus_czybh() {
		String sql = "select distinct d.CZYBH,c.* "
		+"from TSYS_ROLE_AUTHORITY a inner join TSYS_AUTHORITY b on a.AUTHORITY_ID=b.AUTHORITY_ID "
		+"inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID "
		+"inner join TSYS_ROLE_USER d on a.ROLE_ID=d.ROLE_ID "
		+"union select distinct a.CZYBH,c.* "
		+"from TSYS_CZYBM_AUTHORITY a inner join TSYS_AUTHORITY b on a.AUTHORITY_ID=b.AUTHORITY_ID "
		+"inner join TSYS_MENU c on b.MENU_ID=c.MENU_ID ";
		List<Map<String,Object>> list = this.findBySql(sql);
		return BeanConvertUtil.mapToBeanList(list, Menu.class);
	}
	
	public Map<String, Object> getMenuMsgByUrl(String url) {
		String sql = " select  MENU_ID MENUID,isnull(b.MKDM,'0') MKDM from TSYS_MENU  a "
					+ "	left join tMODULE b on b.URL=a.MENU_URL "
					+ "	where  MENU_URL=? " ;	
		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{url});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
