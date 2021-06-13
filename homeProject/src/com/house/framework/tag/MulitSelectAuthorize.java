package com.house.framework.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.service.AuthorityService;
import com.house.framework.service.MenuService;
import com.house.home.entity.basic.Czybm;


/**
 * 下拉选项树控件
 *
 */
public class MulitSelectAuthorize extends CommonMulitTag {
	private static final long serialVersionUID = 1L;
	private String czylb=""; //操作员类别

	public int doStartTag() throws JspException {
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		StringBuilder strb = new StringBuilder();
		strb.append(this.bulidHtml())
			.append("\n<script type='text/javascript'>\n")
			.append(this.bulidTreeSetting())
			.append("var zNodes_"+id+" = ").append(this.bulidNodes()).append(";\n\n\n")
			.append(this.bulidBeforeCheck())
			.append(this.bulidOnCheck())
			.append(this.bulidMenuDisplay())
			.append(this.bulidOnReady())
			.append(appendAllDom == true ? "setTimeout(function(){appendAllDom(\""+id+"\");}, 200);" : "")
			.append("</script>\n\n");
		try {
			pageContext.getOut().print(strb.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	@SuppressWarnings("unchecked")
	protected String bulidNodes(){
		MenuService menuService=(MenuService) SpringContextHolder.getBean("menuServiceImpl");
		AuthorityService authorityService=(AuthorityService) SpringContextHolder.getBean("authorityServiceImpl");
		List<Menu> menuList = menuService.getBySysCode(czylb);
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//虚拟权限根节点
		nodes.add(newVirtualRootNode());
		List<Authority> authList = authorityService.getAll();
		if (menuList!=null && menuList.size()>0){
			List<Long> list = new ArrayList<Long>();
			for (Menu menu : menuList){
				list.add(menu.getMenuId());
			}
			//树菜单节点JSON格式
			nodes.addAll(this.treeNodeMapper(menuList,null,list));
			//树权限节点JSON格式
			nodes.addAll(this.treeNodeMapper(authList,null,list));
		}
		return JSON.toJSONString(nodes);
	}
	/**
	 * 虚拟权限根节点
	 * @return
	 */
	private Map<String, Object> newVirtualRootNode(){
		Map<String, Object> virtualRoot = new HashMap<String, Object>();
		virtualRoot.put("id", "0");
		virtualRoot.put("pId", "-1");
		virtualRoot.put("name", "权限");
		virtualRoot.put("isParent", true);
		virtualRoot.put("open", true);
		virtualRoot.put("nodeType", "department");
		return virtualRoot;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map<String, Object>> treeNodeMapper(List list,List<Long> selIds,List<Long> menuList){
		List<Map<String, Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(list != null && list.size()>0){
			rsList = new ArrayList<Map<String,Object>>(list.size());
			for(Object obj : list){
				if(obj instanceof Authority){
					map = this.authorityNodeMapper((Authority)obj,selIds,menuList);
				}else{
					map = this.menuNodeMapper((Menu)obj,menuList);
				}
				if(map != null && map.size()>0){
					rsList.add(map);
				}
			}
		}
		return rsList;
	}
	private Map<String,Object> authorityNodeMapper(Authority authority,List<Long> selIds,List<Long> menuList){
		if(null == authority)
			return null;
		Map<String, Object> map = new HashMap<String,Object>();
		if (menuList.indexOf(authority.getMenuId()) > 0){
			map.put("id", authority.getAuthorityId());
			map.put("name", authority.getAuthName());
			map.put("pId", authority.getMenuId()+"_menu");
			map.put("isParent", false);
			map.put("nodeType", "auth");
			
			if(selIds != null){
				for(Long id : selIds){
					if(id.longValue() == authority.getAuthorityId().longValue()){
						map.put("checked", true);
						break;
					}
				}
			}
		}
		return map;
	}

	private Map<String,Object> menuNodeMapper(Menu menu,List<Long> menuList){
		if(menu == null)
			return null;
		boolean flag = false;
		Long pid = menu.getParentId();
		if (pid==null){
			pid = 0L;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(menuList != null){
			if (pid.longValue()==0){
				flag = true;
			}else{
				for(Long id : menuList){
					if(pid.longValue() == id.longValue()){
						flag = true;
						break;
					}
				}
			}
		}
		if (flag){
			map = new HashMap<String,Object>();
			map.put("id", menu.getMenuId()+"_menu");
			map.put("name", menu.getMenuName());
			map.put("pId", (menu.getParentId() == null || menu.getParentId() == 0) ? "0" : menu.getParentId()+"_menu");
			map.put("isParent", true);
			map.put("open", false);
			map.put("nodeType", "menu");
		}
		
		return map;
	}

	public String getCzylb() {
		return czylb;
	}

	public void setCzylb(String czylb) {
		this.czylb = czylb;
	}
	
}
