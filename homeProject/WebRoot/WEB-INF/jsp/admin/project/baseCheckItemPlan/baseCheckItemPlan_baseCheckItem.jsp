<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>基装类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <link href="${resourceRoot}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <link href="${resourceRoot}/css/tab.css" rel="stylesheet" />
    <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="${resourceRoot}/jgrowl/jquery.jgrowl.css" rel="stylesheet" type="text/css" />
    <link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
    <link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
<%-- 	<script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script> --%>
	<script src="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.js" type="text/javascript"></script>
    <script src="${resourceRoot}/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js" type="text/javascript"></script>
    <script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
    <script type="text/javascript" src="${resourceRoot}/artDialog/source/artDialog.js"></script>
    <script type="text/javascript" src="${resourceRoot}/jgrowl/jquery.jgrowl.js"></script>
    <script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clickTree(treeNode){
	if(treeNode.parentIdStr){
		window.document.getElementById("listFrame_q").src="${ctx}/admin/baseCheckItem/goItemSelect?baseItemType1="+treeNode.parentIdStr+"&baseItemType2="+treeNode.menuIdStr;
	}else{
		window.document.getElementById("listFrame_q").src="${ctx}/admin/baseCheckItem/goItemSelect?baseItemType1="+treeNode.menuIdStr;
	}
}
</script>
  <frameset cols="200,*" rows="*"  border="0" framespacing="1" frameborder="yes" bordercolor="#B2D9FF" style="border-style: hidden;"  >
  		<frame id="treeFrame_q" name="treeFrame_q" src="${ctx}/admin/baseItemType1/goCheckTree?baseItemType1=${baseCheckItemPlan.baseItemType1}" frameborder="0" border="1" noresize="noresize" >
  		<frame id="listFrame_q" name="listFrame_q" src="${ctx}/admin/baseCheckItem/goItemSelect" frameborder="0" border="1" noresize="noresize" >
  </frameset>
  </head>
<body>
</body>
</html>
