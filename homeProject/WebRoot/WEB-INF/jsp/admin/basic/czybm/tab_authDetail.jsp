<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>权限明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/czybm/goAuthDetailJqGrid", 
			postData:{czybh:$.trim($("#czybh").val())},
			height:380,
		    pager :'1',
		    rowNum:100000, 
			colModel : [
				{name: "menu1", index: "menu1", width: 100, label: "一级目录", sortable: true, align: "left"},
				{name: "menu2", index: "menu2", width: 100, label: "二级目录", sortable: true, align: "left"},
				{name: "mkmc", index: "mkmc", width: 100, label: "模块名称", sortable: true, align: "left"},
				{name: "gnmc", index: "gnmc", width: 800, label: "功能名称", sortable: true, align: "left"},
            ],   
 		};
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_authDetail",gridOption);
});
 </script>
</head>
<body>
	<div id="tab" class="tab_content" style="display: block; ">
		<form role="form" class="form-search" action="" method="post" 
			id="dataForm">
			<ul class="ul-form">
				<li><label style="width：60px;">角色</label> <house:roleMulit id="userRole2"
						selectedValue="${czybm.userRole }" sysCode="${czybm.czylb }"
						width="200px"></house:roleMulit></li>
			</ul>
		</form>
		<div class="clear_float"></div>
		<div class="body-box-list"  style="margin-top: 0px;">
			<table id="dataTable_authDetail" style="overflow: auto;"></table>
		</div>
	</div>
</div>
</body>
</html>
