package com.house.framework.commons.cache;

import java.util.List;

import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;
import com.house.framework.entity.Menu;
import com.house.framework.entity.Role;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.BuilderGroup;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Position;
import com.house.home.entity.basic.Xtdm;

/**
 * 缓存工具类
 */
public class CacheUtils {
	
	/**
	 * 获取权限缓存
	 * @param czybh
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Authority> getAuthority(String czybh){
		AuthorityCacheManager authorityCacheManager = 
				(AuthorityCacheManager)SpringContextHolder.getBean("authorityCacheManager");
		return  (List<Authority>) authorityCacheManager.get(czybh);
	}

	/**
	 * 获取项目名称对象缓存
	 * @param code
	 * @return
	 */
	public static Builder getBuilder(String code){
		BuilderCacheManager builderCacheManager = 
				(BuilderCacheManager)SpringContextHolder.getBean("builderCacheManager");
		return (Builder) builderCacheManager.get(code);
	}
	
	/**
	 * 获取项目名称缓存
	 * @param code
	 * @return
	 */
	public static String getBuilderDescr(String code){
		BuilderCacheManager builderCacheManager = 
				(BuilderCacheManager)SpringContextHolder.getBean("builderCacheManager");
		Builder builder = (Builder) builderCacheManager.get(code);
		if (builder!=null){
			return builder.getDescr();
		}
		return null;
	}
	
	/**
	 * 获取项目大类缓存
	 * @param code
	 * @return
	 */
	public static BuilderGroup getBuilderGroup(String code){
		BuilderGroupCacheManager builderGroupCacheManager = 
				(BuilderGroupCacheManager)SpringContextHolder.getBean("builderGroupCacheManager");
		return (BuilderGroup) builderGroupCacheManager.get(code);
	}
	
	/**
	 * 获取项目大类名称缓存
	 * @param code
	 * @return
	 */
	public static String getBuilderGroupDescr(String code){
		BuilderGroupCacheManager builderGroupCacheManager = 
				(BuilderGroupCacheManager)SpringContextHolder.getBean("builderGroupCacheManager");
		BuilderGroup builderGroup = (BuilderGroup) builderGroupCacheManager.get(code);
		if (builderGroup!=null){
			return builderGroup.getDescr();
		}
		return null;
	}
	
	/**
	 * 获取部门1列表缓存
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Department1> getDepartment1List(){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		return (List<Department1>) departmentCacheManager.get("department1");
	}
	
	/**
	 * 获取部门1的下级部门列表缓存
	 * @param code：部门1编号
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Department2> getDepartment2ListByCode(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department1_"+code+"_list";
		return (List<Department2>) departmentCacheManager.get(key);
	}
	
	/**
	 * 获取部门1缓存
	 * @param code: 部门1编号
	 * @return 
	 * @return
	 */
	public static Department1 getDepartment1(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department1_"+code;
		return (Department1) departmentCacheManager.get(key);
	}
	
	/**
	 * 获取部门1名称缓存
	 * @param code: 部门1编号
	 * @return 
	 * @return
	 */
	public static String getDepartment1Desc2(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department1_"+code;
		Department1 department1 = (Department1) departmentCacheManager.get(key);
		if (department1!=null){
			return department1.getDesc2();
		}
		return null;
	}
	
	/**
	 * 获取部门2列表缓存
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Department2> getDepartment2List(){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		return (List<Department2>) departmentCacheManager.get("department2");
	}
	
	/**
	 * 获取部门2的下级部门列表缓存
	 * @param code：部门2编号
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Department3> getDepartment3ListByCode(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department2_"+code+"_list";
		return (List<Department3>) departmentCacheManager.get(key);
	}
	
	/**
	 * 获取部门2缓存
	 * @param code: 部门2编号
	 * @return 
	 * @return
	 */
	public static Department2 getDepartment2(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department2_"+code;
		return (Department2) departmentCacheManager.get(key);
	}
	
	/**
	 * 获取部门2名称缓存
	 * @param code: 部门2编号
	 * @return 
	 * @return
	 */
	public static String getDepartment2Desc2(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department2_"+code;
		Department2 department2 = (Department2) departmentCacheManager.get(key);
		if (department2!=null){
			return department2.getDesc2();
		}
		return null;
	}
	
	/**
	 * 获取部门3列表缓存
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Department3> getDepartment3List(){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		return (List<Department3>) departmentCacheManager.get("department3");
	}
	
	/**
	 * 获取部门3缓存
	 * @param code: 部门3编号
	 * @return 
	 * @return
	 */
	public static Department3 getDepartment3(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department3_"+code;
		return (Department3) departmentCacheManager.get(key);
	}
	
	/**
	 * 获取部门3名称缓存
	 * @param code: 部门3编号
	 * @return 
	 * @return
	 */
	public static String getDepartment3Desc2(String code){
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		String key = "department3_"+code;
		Department3 department3 = (Department3) departmentCacheManager.get(key);
		if (department3!=null){
			return department3.getDesc2();
		}
		return null;
	}
	
	/**
	 * 获取字典缓存
	 * @param dictCode
	 * @return
	 */
	public static Dict getDict(String dictCode){
		DictCacheManager dictCacheManager = 
				(DictCacheManager)SpringContextHolder.getBean("dictCacheManager");
		return (Dict) dictCacheManager.get(dictCode);
	}
	
	/**
	 * 获取字典名称缓存
	 * @param dictCode
	 * @return
	 */
	public static String getDictName(String dictCode){
		DictCacheManager dictCacheManager = 
				(DictCacheManager)SpringContextHolder.getBean("dictCacheManager");
		Dict dict = (Dict) dictCacheManager.get(dictCode);
		if (dict!=null){
			return dict.getDictName();
		}
		return null;
	}
	
	/**
	 * 获取字典元素列表缓存
	 * @param dictCode
	 * @return
	 */
	public static List<DictItem> getDictItemList(String dictCode){
		DictCacheManager dictCacheManager = 
				(DictCacheManager)SpringContextHolder.getBean("dictCacheManager");
		Dict dict = (Dict) dictCacheManager.get(dictCode);
		if (dict!=null){
			return dict.getDictItems();
		}
		return null;
	}
	
	/**
	 * 获取字典元素名称缓存
	 * @param key
	 * @return
	 */
	public static String getDictItemLabel(String dictCode, String itemCode){
		List<DictItem> list = getDictItemList(dictCode);
		if(list == null || list.size() < 1)
			return null;
		for(DictItem dictItem : list){
			if(itemCode.trim().equals(dictItem.getItemCode()))
				return dictItem.getItemLabel();
		}
		return null;
	}
	
	/**
	 * 获取菜单列表缓存
	 * @param czybh
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> getMenuList(String czybh){
		MenuCacheManager menuCacheManager = 
				(MenuCacheManager)SpringContextHolder.getBean("menuCacheManager");
		return (List<Menu>) menuCacheManager.get(czybh);
	}
	
	/**
	 * 获取消息缓存
	 * @param key
	 * @return
	 */
	public static String getMessage(String key){
		MessageCacheManager messageCacheManager = 
				(MessageCacheManager)SpringContextHolder.getBean("messageCacheManager");
		return (String) messageCacheManager.get(key);
	}
	
	/**
	 * 获取职位缓存
	 * @param code
	 * @return
	 */
	public static Position getPosition(String code){
		PositionCacheManager positionCacheManager = 
				(PositionCacheManager)SpringContextHolder.getBean("positionCacheManager");
		return (Position) positionCacheManager.get(code);
	}
	
	/**
	 * 获取职位名称缓存
	 * @param code
	 * @return
	 */
	public static String getPositionDesc2(String code){
		PositionCacheManager positionCacheManager = 
				(PositionCacheManager)SpringContextHolder.getBean("positionCacheManager");
		Position position = (Position) positionCacheManager.get(code);
		if (position!=null){
			return position.getDesc2();
		}
		return null;
	}
	
	/**
	 * 获取角色缓存
	 * @param roleId
	 * @return
	 */
	public static Role getRole(Long roleId){
		RoleCacheManager roleCacheManager = 
				(RoleCacheManager)SpringContextHolder.getBean("roleCacheManager");
		return (Role) roleCacheManager.get(roleId);
	}
	
	/**
	 * 获取角色名称缓存
	 * @param roleId
	 * @return
	 */
	public static String getRoleName(Long roleId){
		RoleCacheManager roleCacheManager = 
				(RoleCacheManager)SpringContextHolder.getBean("roleCacheManager");
		Role role = (Role) roleCacheManager.get(roleId);
		if (role!=null){
			return role.getRoleName();
		}
		return null;
	}
	
	/**
	 * 获取系统代码缓存
	 * @param id
	 * @param cbm
	 * @return
	 */
	public static Xtdm getXtdm(String id,String cbm){
		XtdmCacheManager xtdmCacheManager = 
				(XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		String key = id + "_" + cbm;
		return (Xtdm) xtdmCacheManager.get(key);
	}
	
	/**
	 * 获取系统代码名称缓存
	 * @param id
	 * @param cbm
	 * @return
	 */
	public static String getXtdmNote(String id,String cbm){
		XtdmCacheManager xtdmCacheManager = 
				(XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		String key = id + "_" + cbm;
		Xtdm xtdm = (Xtdm) xtdmCacheManager.get(key);
		if (xtdm!=null){
			return xtdm.getNote();
		}
		return null;
	}
	
	/**
	 * 获取系统代码列表缓存
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Xtdm> getXtdmList(String id){
		XtdmCacheManager xtdmCacheManager = 
				(XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		String key = id + "list";
		return (List<Xtdm>) xtdmCacheManager.get(key);
	}
	
	/**
	 * 获取客户类型列表缓存
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<CustType> getCustTypeList(){
		CustTypeCacheManager custTypeCacheManager = 
				(CustTypeCacheManager)SpringContextHolder.getBean("custTypeCacheManager");
		return (List<CustType>) custTypeCacheManager.get("custType");
	}
	
	
	/**
	 * 刷新单个缓存
	 * @param cacheManager
	 */
	public static void refreshCache(ICacheManager cacheManager){
		cacheManager.refresh();
	}
	
	/**
	 * 刷新所有缓存
	 */
	public static void refreshAll(){
		AuthorityCacheManager authorityCacheManager = 
				(AuthorityCacheManager)SpringContextHolder.getBean("authorityCacheManager");
		BuilderCacheManager builderCacheManager = 
				(BuilderCacheManager)SpringContextHolder.getBean("builderCacheManager");
		BuilderGroupCacheManager builderGroupCacheManager = 
				(BuilderGroupCacheManager)SpringContextHolder.getBean("builderGroupCacheManager");
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		DictCacheManager dictCacheManager = 
				(DictCacheManager)SpringContextHolder.getBean("dictCacheManager");
		MenuCacheManager menuCacheManager = 
				(MenuCacheManager)SpringContextHolder.getBean("menuCacheManager");
		MessageCacheManager messageCacheManager = 
				(MessageCacheManager)SpringContextHolder.getBean("messageCacheManager");
		PositionCacheManager positionCacheManager = 
				(PositionCacheManager)SpringContextHolder.getBean("positionCacheManager");
		RoleCacheManager roleCacheManager = 
				(RoleCacheManager)SpringContextHolder.getBean("roleCacheManager");
		XtdmCacheManager xtdmCacheManager = 
				(XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		CustTypeCacheManager custTypeCacheManager = 
				(CustTypeCacheManager)SpringContextHolder.getBean("custTypeCacheManager");
		authorityCacheManager.refresh();
		builderCacheManager.refresh();
		builderGroupCacheManager.refresh();
		departmentCacheManager.refresh();
		dictCacheManager.refresh();
		menuCacheManager.refresh();
		messageCacheManager.refresh();
		positionCacheManager.refresh();
		roleCacheManager.refresh();
		xtdmCacheManager.refresh();
		custTypeCacheManager.refresh();
	}
}
