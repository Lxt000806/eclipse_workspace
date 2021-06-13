package com.house.framework.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

/**
 * 下拉选项树控件
 *
 */
public class ZTreeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIC_TRUE = "true";
	
	/** 文本输入框的id, 树对象: zTree_id */
	private String id;
	/** 树节点数据 */
	private String nodes = "[]";
	/** 已经选中的节点，回显作用 多个值与","分隔 */
	private String value = "";
	/** 父节点是否可选 true 可选，false 不可选 */
	private String canSelectParent = "true";
	/** 对象ID与树ID 名称转换 */
	private String nodeId = "id";
	/** 对象父ID与树父ID 名称转换 */
	private String nodePId = "pId";
	/** 对象名称与树节点 名称转换 */
	private String nodeName = "name";
	/** 下拉框宽度 */
	private String width = "160px";
	/** 下拉框高度 */
	private String height = "250px";
	/** 是否多选 */
	private String mulitSel = "false";
	/** 点击树节点前事件 */
	private String beforeClick = "";
	/** 点击树节点事件 */
	private String onClick = "";
	/** 点击选择框前事件 */
	private String beforeCheck = "";
	/** 点击选择框事件 */
	private String onCheck = "";
	/** 树加载前调用事件 */
	private String beforeLoadTree = "";
	/** 树加载显示完后调用事件 */
	private String afterLoadTree = "";
	
	
	public int doStartTag() throws JspException {
		boolean mulSel = ZTreeTag.LOGIC_TRUE.equals(mulitSel);
		boolean pCanSel = ZTreeTag.LOGIC_TRUE.equals(canSelectParent);
		
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		
		StringBuilder strb = new StringBuilder();
		strb.append(this.bulidHtml(mulSel))
			.append("\n<script type='text/javascript'>\n")
			.append(this.bulidTreeSetting(mulSel))
			.append("var zNodes_"+id+" = ").append(nodes).append(";\n\n\n")
			.append(this.bulidBeforeClick(pCanSel))
			.append(this.bulidOnClick(mulSel));
		if(mulSel){
			strb.append(this.bulidBeforeCheck())
				.append(this.bulidOnCheck(pCanSel));
		}else{
			strb.append(this.bulidClearFun());
		}
		strb.append(this.bulidMenuDisplay())
			.append(this.bulidOnReady(mulSel))
			.append("</script>\n\n");
		try {
			pageContext.getOut().print(strb.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	private String bulidTreeSetting(boolean mulSel){
		String strb = "";
		strb += "var zTree_"+id+" = null;\n" +
				"var setting_"+id+" = {\n";
		if(mulSel){
			strb += "	check: {\n" +
					"		enable: true,\n" +
					"		chkboxType: {\"Y\":\"ps\", \"N\":\"ps\"}\n" +
					"	},\n";
		}
		strb += "	view: {\n" +
				"		dblClickExpand: false\n" +
				"	},\n" +
				"	data: {\n" +
				"		simpleData: {\n" +
				"			enable: true,\n" +
				"			idKey: \""+nodeId+"\",\n" +
				"			pIdKey: \""+nodePId+"\"\n" +
				"		},\n" +
				"		key:{ \n"+
				"			name: \""+nodeName+"\"\n" +
				"		}\n" +
				"	},\n" +
				"	callback: {\n";
		if(mulSel){
			strb += "		beforeCheck: beforeCheck_"+id+",\n" +
					"		onCheck: onCheck_"+id+",\n";
		}
		strb += "		beforeClick: beforeClick_"+id+",\n" +
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
	
	private String bulidOnClick(boolean mulSel){
		String strb = "";
		strb += "function onClick_"+id+"(e, treeId, treeNode) {\n" +
				"	if(!"+mulSel+") {\n" +
				"		$(\"#"+id+"\").val(treeNode['"+nodeId+"']);\n" +
				"		$(\"#"+id+"_NAME\").val(treeNode['"+nodeName+"']);\n" +
				"		hideMenu_"+id+"();\n" +
				"	}\n";
		
		if(StringUtils.isNotBlank(onClick)){
			strb += "	if(typeof("+onClick+") == 'function') {\n" +
					"		" +onClick+"(e, treeId, treeNode);\n" +
					"	}\n";
			}
		strb += "}\n\n";
		return strb;
	}
	
	private String bulidBeforeCheck(){
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
	
	private String bulidOnCheck(boolean pCanSel){
		String strb = "";
		strb += "function onCheck_"+id+"(e, treeId, treeNode) {\n" +
				"	var zTree = zTree_"+id+";\n" +
				"	var ids = [];\n" +
				"	var names = [];\n" +
				"	var nodes = zTree.getCheckedNodes(true);\n" +
				"	for (var i=0, l=nodes.length; i<l; i++) {\n" +
				"		if(!"+pCanSel+" && nodes[i].isParent){\n" +
				"			continue;\n" +
				"		}\n" +
				"		ids.push(nodes[i]['"+nodeId+"']);\n" +
				"		names.push(nodes[i]['"+nodeName+"']);\n" +
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
	
	private String bulidOnReady(boolean mulSel){
		String strb = "";
		strb += "$(document).ready(function(){\n" +
				"	document.execCommand(\"BackgroundImageCache\",false,true);\n\n";
		
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
				"	zTree_"+id+" = $.fn.zTree.getZTreeObj(\"tree_"+id+"\");\n";
		if(mulSel && StringUtils.isNotBlank(value)){
		strb += "	var idArr = \""+value+"\".split(',');\n" +
				"	var nameArr = [];\n" +
				"	for(var i=0; i<idArr.length; i++){\n" +
				"		var nodes = zTree_"+id+".getNodesByParam(\""+nodeId+"\", idArr[i], null);\n" +
				"		if(nodes.length > 0){\n" +
				"			zTree_"+id+".checkNode(nodes[0], true, true);\n" +
				"			nameArr.push(nodes[0]['"+nodeName+"']);\n" +
				"		}\n" +
				"	}\n" +
				"	$(\"#"+id+"\").val(\""+value+"\");\n" +
				"	$(\"#"+id+"_NAME\").val(nameArr.join(', '));\n";
		}else{
		strb += "	var nodes = zTree_"+id+".getNodesByParam(\""+nodeId+"\", \""+value+"\", null);\n" +
				"	if(nodes.length > 0){\n" +
				"		zTree_"+id+".selectNode(nodes[0]);\n"+
				"		$(\"#"+id+"\").val(nodes[0]['"+nodeId+"']);\n" +
				"		$(\"#"+id+"_NAME\").val(nodes[0]['"+nodeName+"']);\n" +
				"	}\n";
		}
		if(StringUtils.isNotBlank(afterLoadTree)){
		strb += "	if(typeof("+afterLoadTree+") == 'function'){\n" +
				"		"+afterLoadTree+"(zTree_"+id+");\n" +
				"	}\n";
		}
		strb += "});\n\n";
		return strb;
	}
	
	private String bulidHtml(boolean mulSel){
		String strb = "";
		strb += "<input type='text' readonly = 'readonly' id='"+id+"_NAME' name='"+id+"_NAME' onclick=\"showMenu_"+id+"(); return false;\" style=\"width: "+width+";\" />\n";
		if(mulSel){
		strb += "<a href='#' onclick='clear_"+id+"();' style='position:absolute;right:-30px;top:5px'>清空</a>\n" ;
		}
		strb += "<input type='hidden' id='"+id+"' name='"+id+"' />\n";
				
		return strb;
	}
	
	private String bulidClearFun(){
		String strb = "";
		strb += "	function clear_"+id+"(){\n" +
				"		$(\"#"+id+"\").val('');\n" +
				"		$(\"#"+id+"_NAME\").val('');\n" +
				"	}\n";
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
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodePId() {
		return nodePId;
	}

	public void setNodePId(String nodePId) {
		this.nodePId = nodePId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public String getMulitSel() {
		return mulitSel;
	}

	public void setMulitSel(String mulitSel) {
		this.mulitSel = mulitSel;
	}

	public String getAfterLoadTree() {
		return afterLoadTree;
	}

	public void setAfterLoadTree(String afterLoadTree) {
		this.afterLoadTree = afterLoadTree;
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

	public String getCanSelectParent() {
		return canSelectParent;
	}

	public void setCanSelectParent(String canSelectParent) {
		this.canSelectParent = canSelectParent;
	}

	public String getBeforeLoadTree() {
		return beforeLoadTree;
	}

	public void setBeforeLoadTree(String beforeLoadTree) {
		this.beforeLoadTree = beforeLoadTree;
	}

}
