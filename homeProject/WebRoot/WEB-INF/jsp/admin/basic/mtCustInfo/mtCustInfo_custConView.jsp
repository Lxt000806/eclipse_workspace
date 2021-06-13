<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
<script type="text/javascript"> 
$(function() {

	Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mtCustInfoAssign/goCustConJqGrid",
			postData:{custCode:"${mtCustInfo.custCode}"},
			height:$(document).height()-$("#content-list").offset().top-80,
			styleUI: "Bootstrap", 
			colModel : [
				{name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",key : true,hidden:true},
		    	{name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left"},
		    	{name : 'descr',index : 'descr',width : 100,label:'客户名称',sortable : true,align : "left"},
				{name : 'custaddress',index : 'custaddress',width : 200,label:'楼盘',sortable : true,align : "left"},
		    	{name : 'statusdescr',index : 'statusdescr',width : 100,label:'客户状态',sortable : true,align : "left"},
		    	{name : 'condate',index : 'condate',width : 150,label:'联系时间',sortable : true,align : "left",formatter: formatTime},
			    {name : 'conmandescr',index : 'conmandescr',width : 100,label:'联系人',sortable : true,align : "left"} ,
			    {name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"}  ,
			    {name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"} ,
		        {name : 'remarks',index : 'remarks',width : 150,label:'联系内容',sortable : true,align : "left"},
		        {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime} ,
		        {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		        {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		        {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
			],
		});
});

</script>
</html>
