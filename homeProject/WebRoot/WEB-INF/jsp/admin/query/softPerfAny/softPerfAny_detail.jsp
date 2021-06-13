<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>查看明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/softPerfAny/goDetailJqGrid",
			postData:{
				empCode:"${customer.empCode}",
				department1:"${customer.department1}",
				department2:"${customer.department3}",
				dateFrom:formatDate("${customer.dateFrom}"),
				dateTo:formatDate("${customer.dateTo}"),
				statistcsMethod:"${customer.statistcsMethod}",
				role: "${customer.role}",
				custType:"${customer.custType}"
			},
			height:500,
			colModel : [
		       {name: "address",index : "address",width : 200,label:"楼盘",sortable : true,align : "left"},
		       {name: "custtypedescr",index : "custtypedescr",width : 80,label:"客户类型",sortable : true,align : "left"},
		       {name: "chgamount", index: "chgamount", width: 73, label: "增减金额", sortable: true, align: "right",sum:true},
			   {name: "chgdiscamount", index: "chgdiscamount", width: 80, label: "优惠金额", sortable: true, align: "right",sum:true},
			   {name: "chgdisccost", index: "chgdisccost", width: 80, label: "产品优惠", sortable: true, align: "right",sum:true},
			   {name: "planfee", index: "planfee", width: 70, label: "预算", sortable: true, align: "right",sum:true},
			   {name: "plandisc", index: "plandisc", width: 80, label: "预算优惠", sortable: true, align: "right",sum:true},
			   {name: "amount", index: "amount", width: 80, label: "业绩金额", sortable: true, align: "right",sum:true},
            ] ,
            rowNum:100000,  
	   		pager :'1',
		}); 
});

</script>
</head>
    
<body>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
			<button type="button" class="btn btn-system "
					onclick="doExcelNow('软装部业绩排名明细','dataTable', 'page_form')">导出excel</button>
			<button type="button" class="btn btn-system "
				onclick="closeWin(false)">关闭</button>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" name="jsonString" value="" />
		</form>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>
</html>


