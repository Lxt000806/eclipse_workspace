<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>搜寻——周期编号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/perfCycle/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "no", index: "no", width: 100, label: "统计周期编号", sortable: true, align: "left"},
				{name: "y", index: "y", width: 80, label: "归属年份", sortable: true, align: "right"},
				{name: "m", index: "m", width: 80, label: "归属月份", sortable: true, align: "right"},
				{name: "season", index: "season", width: 80, label: "归属季度", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 90, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
		  ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
	});
	$("#y,#m,#season").append($("<option/>").text("请选择...").attr("value",""));
		for(var i=2014;i<=2100;i++){
			$("#y").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=14;i++){
			$("#m").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=6;i++){
			$("#season").append($("<option/>").text(i).attr("value",i));
		}
});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li><label>归属年份</label> <select id="y" name="y"></select>
						</li>
						<li><label>归属月份</label> <select id="m" name="m"></select>
						</li>
						<li><label>归属季度</label> <select id="season" name="season"></select>
						</li>
						<li><label>统计周期编号</label> <input type="text" id="no" name="no"
							style="width:160px;" />
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div>
			<!--query-form-->
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>
	</body>	
</html>
