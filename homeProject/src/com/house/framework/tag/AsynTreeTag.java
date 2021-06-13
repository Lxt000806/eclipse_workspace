package com.house.framework.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

/**
 * 异步下拉单选树
 * 
 */
public class AsynTreeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIC_TRUE = "true";
	
	//是否需要rootId属性
	
	/** 文本输入框的id, 树对象: zTree_id */
	private String id;
	/** 树节点数据 */
	private String nodes = "[]";
	/** 已经选中的节点，回显作用 多个值与","分隔 */
	private String value = "";
	/** 加载节点请求链接地址 */
	private String url;
	/** 自动参数，多个逗号分隔 */
	private String autoParam = "'id'";
	/** 其他需要传递的参数，可以是数组也可以是对象类型，*/
	private String otherParam;
	/** ajax调用数据用户定义过滤方法*/
	private String dataFilter;
	/** 父节点是否可选 true 可选，false 不可选 */
	private String canSelectParent = "true";
	
	/** 下拉框宽度 */
	private String width = "160px";
	/** 下拉框高度 */
	private String height = "250px";
	
	/** 点击树节点前事件 */
	private String beforeClick = "";
	/** 点击树节点事件 */
	private String onClick = "";
	/** 树加载前调用事件 */
	private String beforeLoadTree = "";
	/** 树加载显示完后调用事件 */
	private String afterLoadTree = "";
	
	
	public int doStartTag() throws JspException {
		boolean pCanSel = AsynTreeTag.LOGIC_TRUE.equals(canSelectParent);
		
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		
		StringBuilder strb = new StringBuilder();
		strb.append(this.bulidHtml())
			.append("\n<script type='text/javascript'>\n")
			.append(this.bulidTreeSetting())
			.append("var zNodes_"+id+" = ").append(nodes).append(";\n\n\n")
			.append(this.bulidBeforeClick(pCanSel))
			.append(this.bulidOnClick());
		strb.append(this.bulidMenuDisplay())
			.append(this.bulidOnReady())
			.append("</script>\n\n");
		try {
			pageContext.getOut().print(strb.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	private String bulidTreeSetting(){
		String strb = "";
		strb += "var zTree_"+id+" = null;\n" +
				"var setting_"+id+" = {\n" +
				"	view: {\n" +
				"		dblClickExpand: false\n" +
				"	},\n" +
				"	data: {\n" +
				"		simpleData: {\n" +
				"			enable: true,\n" +
				"		}\n" +
				"	},\n" +
				"	async:{\n" +
				"		url : '"+url.trim()+"',\n";
		if(StringUtils.isNotBlank(dataFilter)){
		strb += "		dataFilter: "+dataFilter.trim()+",\n"; //注意没有单引号
		}
		if(StringUtils.isNotBlank(otherParam)){
		strb += "		otherParam: "+otherParam.trim()+",\n"; //注意没有单引号
		}
		strb += "		enable : true,\n" +
				"		autoParam: ["+autoParam+"]\n" +
				"	}," +
				"	callback: {\n" +
				"		beforeClick: beforeClick_"+id+",\n" +
				"		onClick: onClick_"+id+"\n" +
				"	}\n" +
				"};\n\n";
		return strb;
	}
	
	private String bulidBeforeClick(boolean pCanSel){
		String strb = "";
		strb += "function beforeClick_"+id+"(treeId, treeNode) {\n";
		strb += "	if(treeNode.isParent){\n" +
				"		if(!"+pCanSel+")\n" +
				"			zTree_"+id+".expandNode(treeNode, !treeNode.open);\n" +
				"	}\n";
		if(StringUtils.isNotBlank(beforeClick)){
		strb += "	if(typeof("+beforeClick+") == 'function') {\n" +
				"		if(!"+beforeClick+"(treeId, treeNode)){\n" +
				"			return false;\n" +
				"		}\n" +
				"	}\n";
		}
		strb += "	if(treeNode.isParent){\n" +
				"		return "+pCanSel+";\n" +
				"	}\n" +
				"	return true;\n" +
				"}\n\n";
		return strb;
	}
	
	private String bulidOnClick(){
		String strb = "";
		strb += "function onClick_"+id+"(e, treeId, treeNode) {\n" +
				"		$(\"#"+id+"\").val(treeNode['id']);\n" +
				"		$(\"#"+id+"_NAME\").val(treeNode['name']);\n" +
				"		hideMenu_"+id+"();\n";
		
		if(StringUtils.isNotBlank(onClick)){
		strb += "	if(typeof("+onClick+") == 'function') {\n" +
				"		" +onClick+"(e, treeId, treeNode);\n" +
				"	}\n";
		}
		strb += "}\n\n";
		return strb;
	}
	
	
	private String bulidMenuDisplay(){
		String strb = "";
		strb += "function showMenu_"+id+"() {\n" +
				"	var voffset = $(\"#"+id+"_NAME\").offset();\n" +
				"	var bodyHeight = parseInt(window.document.body.clientHeight);\n" +
				"	var realHeight = voffset.top+parseInt('"+this.height+"')+$(\"#"+id+"_NAME\").outerHeight();\n" +
				"	if(realHeight > bodyHeight){\n" +
				"		$(\"body\").height(realHeight+5);\n" +
				"	}\n" +
				"	$(\"#menuContent_"+id+"\").css({left:voffset.left + \"px\", top:voffset.top + $(\"#"+id+"_NAME\").outerHeight() + \"px\"}).slideDown(\"fast\");\n" +
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
	
	private String bulidOnReady(){
		String strb = "";
		strb += "$(document).ready(function(){\n";
		
		if(StringUtils.isNotBlank(beforeLoadTree)){
		strb += "	if(typeof("+beforeLoadTree+") == 'function'){\n" +
				"		"+beforeLoadTree+"();\n" +
				"	}\n";
		}
		String maxWidth = width;
		if(Integer.parseInt(maxWidth.substring(0,maxWidth.length()-2)) > 200){
			maxWidth = "200px";
		}
	
		strb+="//for(var i=0; i<zNodes_"+id+".length; i++){alert(zNodes_"+id+"[i].itemCode+\"  \"+zNodes_"+id+"[i].itemLabel+\"  \"+zNodes_"+id+"[i].parentId)}\n";
		strb += "	$(\"body\").append('<div id=\"menuContent_"+id+"\" style=\"display:none; position: absolute;z-index:9999;\"><ul id=\"tree_"+id+"\" class=\"ztree\" style=\"margin-top: 0px;border: 1px solid #617775;background: #f0f6e4;overflow-y:auto;overflow-x:auto; width: "+maxWidth+";height: "+height+";\"></ul></div>');\n\n" +
				"	$.fn.zTree.init($(\"#tree_"+id+"\"), setting_"+id+", zNodes_"+id+");\n" +
				"	zTree_"+id+" = $.fn.zTree.getZTreeObj(\"tree_"+id+"\");\n" +
				"	var nodes = zTree_"+id+".getNodesByParam(\"id\", \""+value+"\", null);\n" +
				"	if(nodes.length > 0){\n" +
				"		zTree_"+id+".selectNode(nodes[0]);\n"+
				"		$(\"#"+id+"\").val(nodes[0]['id']);\n" +
				"		$(\"#"+id+"_NAME\").val(nodes[0]['name']);\n" +
				"	}\n";
		if(StringUtils.isNotBlank(afterLoadTree)){
		strb += "	if(typeof("+afterLoadTree+") == 'function'){\n" +
				"		"+afterLoadTree+"(zTree_"+id+");\n" +
				"	}\n";
		}
		strb += "});\n\n";
		return strb;
	}
	
	private String bulidHtml(){
		String strb = "";
		strb += "<input type='text' readonly = 'readonly' id='"+id+"_NAME' onclick=\"showMenu_"+id+"(); return false;\" style=\"width: "+width+";\" />\n"+
				"<input type='hidden' id='"+id+"' name='"+id+"' />\n";
				
		return strb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAutoParam() {
		return autoParam;
	}

	public void setAutoParam(String autoParam) {
		this.autoParam = autoParam;
	}

	public String getCanSelectParent() {
		return canSelectParent;
	}

	public void setCanSelectParent(String canSelectParent) {
		this.canSelectParent = canSelectParent;
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

	public String getBeforeClick() {
		return beforeClick;
	}

	public void setBeforeClick(String beforeClick) {
		this.beforeClick = beforeClick;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
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

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

	public String getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(String dataFilter) {
		this.dataFilter = dataFilter;
	}
	
}
