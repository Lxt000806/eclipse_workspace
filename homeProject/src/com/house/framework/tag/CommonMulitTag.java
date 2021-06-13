package com.house.framework.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.cache.XtdmCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtdm;

/**
 * 下拉多选树
 *
 */
public class CommonMulitTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	protected static final String SQL_VALUE_KEY = "SQL_VALUE_KEY";
	protected static final String SQL_LABEL_KEY = "SQL_LABEL_KEY";
	
	/** 文本输入框的id, 树对象: zTree_id */
	protected String id;
	/** 系统代码编码 */
	protected String dictCode;
	/** 已经选中的节点，回显作用 多个值与","分隔 */
	protected String selectedValue = "";
	/** 虚拟根节点文本 */
	protected String virtualRootLabel = "请选择";
	/** 虚拟根节点ID */
	protected String virtualRootId = "_VIRTUAL_RO0T_ID_";
	/** sql查询语句 */
	protected String sql;
	/** sql对应返回值得key 为空sqlValue */
	protected String sqlValueKey = CommonMulitTag.SQL_VALUE_KEY;
	/** sql对应返回值 为空sqlLable*/
	protected String sqlLableKey = CommonMulitTag.SQL_LABEL_KEY;
	
	/** 宽度 */
	protected String width = "160px";
	/** 下拉框宽度 */
	protected String dropWidth = "200px";
	/** 下拉框高度 */
	protected String height = "250px";
	/** 点击选择框前事件 */
	protected String beforeCheck = "";
	/** 点击选择框事件 */
	protected String onCheck = "";
	/** 树加载前调用事件 */
	protected String beforeLoadTree = "";
	/** 树加载显示完后调用事件 */
	protected String afterLoadTree = "";
	
	/** 不显示的值(多个逗号分隔)  add by zzr 2018/02/24*/
	protected String unShowValue;
	/** 首次加载设置所有子元素 */
	protected boolean appendAllDom;
	
	protected String isDisabled="";
	
	/** 点击树节点事件 */
	private String onClick = "";
	/** 是否多选 */
	protected boolean mulitSel = true;

	protected String bulidTreeSetting(){
		String strb = "";
		strb += "var zTree_"+id+" = null;\n" +
				"var setting_"+id+" = {\n";
		if(mulitSel){
			strb +="    check: {\n" +
				   "		enable: true,\n" +
				   "		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
				   "	},\n" ;
			
		}
     	strb += "	view: {\n" +
				"		dblClickExpand: false\n" +
				"	},\n" +
				"	data: {\n" +
				"		simpleData: {\n" +
				"			enable: true,\n" +
				"		}\n" +
				"	},\n" +
				"	callback: {\n" +  
			    "		beforeCheck: beforeCheck_"+id+",\n" ;
		if(mulitSel){
			strb += "		onCheck: onCheck_"+id+"\n" ;
			
		}else{
			strb += "		onClick: onClick_"+id+"\n" ;
		}	
     	strb += "	}\n" +
				"};\n\n";
		return strb;
	}
	
	
	protected String bulidBeforeCheck(){
		String strb = "";
		strb += "function beforeCheck_"+id+"(treeId, treeNode) {\n";
		
		if(StringUtils.isNotBlank(beforeCheck)){
		strb += "	if(typeof("+beforeCheck+") == 'function') {\n" +
				"		if(!"+beforeCheck+"(treeId, treeNode)){\n" +
				"			return false;\n" +
				"		}\n" +
				"	}\n";
		}
		strb += "	return true;\n" +
				"}\n\n";
		return strb;
	}
	
	protected String bulidOnCheck(){
		String strb = "";
		strb += "function onCheck_"+id+"(e, treeId, treeNode) {\n" +
				"	var zTree = zTree_"+id+";\n" +
				"	var ids = [];\n" +
				"	var names = [];\n" +
				"	var nodes = zTree.getCheckedNodes(true);\n" +
				"	for (var i=0, l=nodes.length; i<l; i++) {\n" +
				"		if(nodes[i].isParent){\n" +
				"			continue;\n" +
				"		}\n" +
				"		ids.push(nodes[i]['id']);\n" +
				"		names.push(nodes[i]['name']);\n" +
				"	}\n" +
				"	$(\"#"+id+"\").val(ids.join(','));\n" +
				"	$(\"#"+id+"_NAME\").val(names.join(','));\n";
		
		if(StringUtils.isNotBlank(onCheck)){
			strb += "	if(typeof("+onCheck+") == 'function') {\n" +
					"		" +onCheck+"(e, treeId, treeNode);\n" +
					"	}\n";
		}
		strb += "}\n\n";
		return strb;
	}
	
	protected String bulidOnClick(){ 
		String strb = "";
		strb += "function onClick_"+id+"(e, treeId, treeNode) {\n" +
				"		$(\"#"+id+"\").val(treeNode.id);\n" +
				"		$(\"#"+id+"_NAME\").val(treeNode.name);\n" +
				"		hideMenu_"+id+"();\n" ;
		
		if(StringUtils.isNotBlank(onClick)){
			strb += "	if(typeof("+onClick+") == 'function') {\n" +
					"		" +onClick+"(e, treeId, treeNode);\n" +
					"	}\n";
			}
		strb += "}\n\n";
		return strb;
	}
	
	/**
	 * 根据位置显示下拉窗口
	 * @author	modify by zb
	 * @date	2019-5-3--下午4:57:09
	 * @return
	 */
	protected String bulidMenuDisplay(){
		String strb = "";
		strb += "function showMenu_"+id+"() {\n" +
				"  var bottomHeight=$(window).height()-$(\"#"+id+"_NAME\").offset().top-$(\"#"+id+"_NAME\").height(); " +
				"	var voffset = $(\"#"+id+"_NAME\").offset();\n" +
				"	var bodyHeight = parseInt(window.document.body.clientHeight);\n" +
				"	var realHeight = voffset.top+parseInt('"+this.height+"')+$(\"#"+id+"_NAME\").outerHeight();\n" +
				"	if(realHeight > bodyHeight){\n" +
				"		$(\"body\").height(realHeight+5);\n" +
				"	}\n " +
				"  	if(bottomHeight< 300 && $(\"#"+id+"_NAME\").offset().top>300 ){" +
				"		$(\"#menuContent_"+id+"\").css({left:voffset.left + \"px\", top:voffset.top + $(\"#"+id+"_NAME\").outerHeight() + \"px\",margin:\"-296px 0 0 0\"}).slideDown(\"fast\");\n" +
				"	} " +
				"  else{" +
				"		$(\"#menuContent_"+id+"\").css({left:voffset.left + \"px\", top:voffset.top + $(\"#"+id+"_NAME\").outerHeight() + \"px\"}).slideDown(\"fast\");\n" +
				"	}" +
				"	$(\"body\").bind(\"mousedown\", onBodyDown_"+id+");\n" +
				"}\n" +
				"\n" +
				"function hideMenu_"+id+"() {\n" +
				"	$(\"#menuContent_"+id+"\").fadeOut(\"fast\");\n" +
				"	$(\"body\").unbind(\"mousedown\", onBodyDown_"+id+");\n" +
				"}\n" +
				"\n" +
				"function onBodyDown_"+id+"(event) {\n" +
				"	if (!(event.target.id == \"menuBtn\" || event.target.id == \"menuContent_"+id+"\" || $(event.target).parents(\"#menuContent_"+id+"\").length>0)) {\n" +
				"		hideMenu_"+id+"();\n" +
				"	}\n" +
				"}\n\n";
		return strb;
	}
	
	protected String bulidOnReady(){
		String strb = "";
		strb += "$(document).ready(function(){\n" +
				"	document.execCommand(\"BackgroundImageCache\",false,true);\n\n";
		
		if(StringUtils.isNotBlank(beforeLoadTree)){
		strb += "	if(typeof("+beforeLoadTree+") == 'function'){\n" +
				"		"+beforeLoadTree+"();\n" +
				"	}\n";
		}
		String minDropWidth = dropWidth.replace("px", "")+"px";
		
		strb += "	$(\"body\").append('<div id=\"menuContent_"+id+"\" style=\"display:none; position: absolute;z-index:9999;\">"+
				"<input type=\"text\" placeholder=\"搜索...\" style=\"width:100%;height:22px;border-radius:3px;\" value=\"\" onkeyup=\"treeKeyup(this,\\'"+id+"\\', event)\"/>"+
				"<ul id=\"tree_"+id+"\" class=\"ztree\" style=\"margin-top: 0px;border: 1px solid #617775;background: white;overflow-y:auto;overflow-x:auto; min-width: "+minDropWidth+";height: "+height+";\"></ul></div>');\n\n" +
				"	$.fn.zTree.init($(\"#tree_"+id+"\"), setting_"+id+", zNodes_"+id+");\n" +
				"	$(\"#tree_"+id+" li ul\").css(\"padding\",\"0px\");\n" + //子节点相对于父节点，取消缩进--add by zb
				"	zTree_"+id+" = $.fn.zTree.getZTreeObj(\"tree_"+id+"\");\n";
		if(StringUtils.isNotBlank(selectedValue)){
		selectedValue = selectedValue.trim();
		strb += "	var idArr = \""+selectedValue+"\".split(',');\n" +
				"	var nameArr = [];\n" +
				"	for(var i=0; i<idArr.length; i++){\n" +
				"		var nodes = zTree_"+id+".getNodesByParam(\"id\", idArr[i], null);\n" +
				"		if(nodes.length > 0){\n" +
				"			zTree_"+id+".checkNode(nodes[0], true, true);\n" +
				"			nameArr.push(nodes[0]['name']);\n" +
				"		}\n" +
				"	}\n" +
				"	$(\"#"+id+"\").val(\""+selectedValue+"\");\n" +
				"	$(\"#"+id+"_NAME\").val(nameArr.join(', '));\n";
		}
		
		if(StringUtils.isNotBlank(afterLoadTree)){
		strb += "	if(typeof("+afterLoadTree+") == 'function'){\n" +
				"		"+afterLoadTree+"(zTree_"+id+");\n" +
				"	}\n";
		}
		strb += "});\n\n";
		
		return strb;
	}
	
	protected String bulidHtml(){
		String strb = "";
		strb += "<input type='text' readonly = 'readonly'";
		if(StringUtils.isNotEmpty(isDisabled)){
			strb+="disabled='disabled'";
		}
		strb +="id='"+id+"_NAME' name='"+id+"_NAME' onclick=\"showMenu_"+id+"(); return false;\" style=\"width: "+width+";\" />\n<span class='caret' ></span>"+
				"<input type='hidden' id='"+id+"' name='"+id+"' />\n";
				
		return strb;
	}
	
	@SuppressWarnings("unchecked")
	protected String bulidNodes(){
		XtdmCacheManager xtdmCacheManager = (XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", this.virtualRootId);
		item.put("pId", "");
		item.put("name", this.virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		if(StringUtils.isBlank(dictCode)){
			if(StringUtils.isBlank(sql))
				return "[]";
			JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if(list != null && list.size() > 0){
				for(Map<String, Object> map : list){
					item = new HashMap<String, Object>();
					item.put("id", map.get(sqlValueKey));
					item.put("pId", this.virtualRootId);
					item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
					rsList.add(item);
				}
			}
		}else{
			List<Xtdm> list = (List<Xtdm>) xtdmCacheManager.get(dictCode+"list");
			if(list != null && list.size() > 0){
				Collections.sort(list, new Comparator<Xtdm>() {
		            public int compare(Xtdm arg0, Xtdm arg1) {
		                return arg0.getDispSeq().compareTo(arg1.getDispSeq());
		            }
		        });
				
				for(Xtdm xtdm : list){
					if(xtdm != null && !isUnShowValue(xtdm.getCbm())){
						item = new HashMap<String, Object>();
						item.put("id", xtdm.getCbm());
						item.put("pId", this.virtualRootId);
						item.put("name", xtdm.getCbm()+" "+xtdm.getNote());
						rsList.add(item);
					}
				}
			}
		}
		return JSON.toJSONString(rsList);
	}
	/**
	 * 判断是否为不显示的选项
	 * @param aaa102
	 * @return
	 * add by zzr 2018/02/24
	 */
	protected boolean isUnShowValue(String str) {
		if (StringUtils.isNotBlank(unShowValue)) {
			String[] svalues = unShowValue.split(",");
			if (svalues.length > 0) {
				for (String svalue : svalues) {
					if (svalue.equals(str)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSqlValueKey() {
		return sqlValueKey;
	}

	public void setSqlValueKey(String sqlValueKey) {
		this.sqlValueKey = sqlValueKey;
	}

	public String getSqlLableKey() {
		return sqlLableKey;
	}

	public void setSqlLableKey(String sqlLableKey) {
		this.sqlLableKey = sqlLableKey;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBeforeCheck() {
		return beforeCheck;
	}

	public void setBeforeCheck(String beforeCheck) {
		this.beforeCheck = beforeCheck;
	}

	public String getOnCheck() {
		return onCheck;
	}

	public void setOnCheck(String onCheck) {
		this.onCheck = onCheck;
	}

	public String getBeforeLoadTree() {
		return beforeLoadTree;
	}

	public void setBeforeLoadTree(String beforeLoadTree) {
		this.beforeLoadTree = beforeLoadTree;
	}

	public String getAfterLoadTree() {
		return afterLoadTree;
	}

	public void setAfterLoadTree(String afterLoadTree) {
		this.afterLoadTree = afterLoadTree;
	}

	public String getVirtualRootLabel() {
		return virtualRootLabel;
	}

	public void setVirtualRootLabel(String virtualRootLabel) {
		this.virtualRootLabel = virtualRootLabel;
	}

	public String getVirtualRootId() {
		return virtualRootId;
	}

	public void setVirtualRootId(String virtualRootId) {
		this.virtualRootId = virtualRootId;
	}

	public String getUnShowValue() {
		return unShowValue;
	}

	public void setUnShowValue(String unShowValue) {
		this.unShowValue = unShowValue;
	}

	public String getDropWidth() {
		return dropWidth;
	}

	public void setDropWidth(String dropWidth) {
		this.dropWidth = dropWidth;
	}


	public boolean isAppendAllDom() {
		return appendAllDom;
	}


	public void setAppendAllDom(boolean appendAllDom) {
		this.appendAllDom = appendAllDom;
	}


	public String getIsDisabled() {
		return isDisabled;
	}


	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}


	public boolean isMulitSel() {
		return mulitSel;
	}


	public void setMulitSel(boolean mulitSel) {
		this.mulitSel = mulitSel;
	}
	


}
