<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>资产折旧页签</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTableAssetDepr",{
		height:415,
		url:"${ctx}/admin/asset/getAssetDeprByCode",
		postData:{code:"${asset.code}"},
		styleUI: "Bootstrap", 
		colModel : [
			{name : "assetcode",index : "assetcode",width : 75,label:"资产编号",sortable : true,align : "left",count:true},
			{name : "descr",index : "descr",width : 75,label:"资产名称",sortable : true,align : "left"},
			{name : "period",index : "period",width : 75,label:"折旧周期",sortable : true,align : "right"},
			{name : "originalvalue",index : "originalvalue",width : 75,label:"原值",sortable : true,align : "right",sum:true	},
			{name : "deproriginalvalue",index : "deproriginalvalue",width : 75,label:"计提折旧原值",sortable : true,align : "right",sum:true},
			{name : "depramount",index : "depramount",width : 75,label:"本月折旧",sortable : true,align : "right",sum:true},
			{name : "totaldepramount",index : "totaldepramount",width : 75,label:"累计折旧额",sortable : true,align : "right",sum:true},
			{name : "deprrate",index : "deprrate",width : 75,label:"折旧率",sortable : true,align : "right"},
			{name : "deptdescr",index : "deptdescr",width : 75,label:"部门",sortable : true,align : "left"},
			{name : "assettypedescr",index : "assettypedescr",width : 75,label:"资产类别",sortable : true,align : "left"},
		],
	});
});
</script>
</head>
	<body>
		<div class="pageContent">
			<div id="content-list" >
				<table id="dataTableAssetDepr" ></table>
				<div id="dataTableAssetDeprPager"></div>
			</div>
		</div>
	</body>	
</html>
