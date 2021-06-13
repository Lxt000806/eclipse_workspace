<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>资产变动页签</title>
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
	Global.JqGrid.initJqGrid("dataTableAssetChg",{
		height:415,
		url:"${ctx}/admin/asset/getAssetChgByCode",
		postData:{code:"${asset.code}"},
		styleUI: "Bootstrap", 
		colModel : [
			{name : "assetcode",index : "assetcode",width : 75,label:"资产编号",sortable : true,align : "left",count:true},
			{name : "descr",index : "descr",width : 75,label:"资产名称",sortable : true,align : "left"},
			{name : "model",index : "model",width : 125,label:"规格型号",sortable : true,align : "left",	},
			{name : "typedescr",index : "typedescr",width : 75,label:"变动类型",sortable : true,align : "left",	},
			{name : "originalvalue",index : "originalvalue",width : 75,label:"原值",sortable : true,align : "right",sum:true},
			{name : "totaldepramount",index : "totaldepramount",width : 75,label:"累计折旧",sortable : true,align : "right",sum:true},
			{name : "netvalue",index : "netvalue",width : 75,label:"净值",sortable : true,align : "right",sum:true},
			{name : "begindate",index : "begindate",width : 95,label:"开始使用日期",sortable : true,align : "left",formatter:formatDate},
			{name : "chgamount",index : "chgamount",width : 75,label:"调整金额",sortable : true,align : "right",sum:true	},
			{name : "befvalue",index : "befvalue",width : 75,label:"变动前内容",sortable : true,align : "left"},
			{name : "aftvalue",index : "aftvalue",width : 75,label:"变动后内容",sortable : true,align : "left"},
			{name : "remarks",index : "remarks",width : 75,label:"变动原因",sortable : true,align : "left"},
			{name : "aftaddress",index : "aftaddress",width : 155,label:"新存放地址",sortable : true,align : "left"},
			{name : "chgdescr",index : "chgdescr",width : 85,label:"操作人",sortable : true,align : "left"},
		],
	});
});
</script>
</head>
	<body>
		<div class="pageContent">
			<div id="content-list" >
				<table id="dataTableAssetChg"></table>
				<div id="dataTableAssetChgPager"></div>
			</div>
		</div>
	</body>	
</html>
