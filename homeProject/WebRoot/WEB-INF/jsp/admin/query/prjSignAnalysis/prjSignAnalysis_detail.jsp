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
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_clzj_mx",{
			height:180,
			rowNum: 10000,
			colModel : [
				{name: "crtdate", index: "crtdate", width: 120, label: "签到日期", sortable: true, align: "left", formatter: formatTime},
				{name: "signintype2descr", index: "signintype2descr", width: 80, label: "服务类型", sortable: true, align: "left"},
			    {name: "ispassdescr", index: "ispassdescr", width: 70, label: "初检通过", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_clzj",{
			url:"${ctx}/admin/prjSignAnalysis/goDetailJqGrid",
			postData:{
				projectMan:'${customer.projectMan}',
				dateFrom:$("#dateFrom").val(),
				dateTo:$("#dateTo").val()
			},
			height:180,
			rowNum: 10000,
			colModel : [
				{name: "CustCode", index: "CustCode", width: 120, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "Address", index: "Address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "PrjItem", index: "PrjItem", width: 90, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "PrjItemDescr", index: "PrjItemDescr", width: 90, label: "施工项目", sortable: true, align: "left"},
				{name: "BeginDate", index: "BeginDate", width: 120, label: "开始日期", sortable: true, align: "left", formatter: formatTime},
				{name: "EndDate", index: "EndDate", width: 120, label: "完工日期", sortable: true, align: "left", formatter: formatTime},
				{name: "SignTimes", index: "SignTimes", width: 100, label: "应签到天数", sortable: true, align: "right", sum: true},
				{name: "SignDays", index: "SignDays", width: 110, label: "实际签到天数", sortable: true, align: "right", sum: true},
				{name: "ValidSignDays", index: "ValidSignDays", width: 110, label: "<span  title='实际签到天数大于应签到天数，取应签到天数，否则取实际签到天数'>有效签到天数</span>", sortable: true, align: "right", sum: true},
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_clzj");
            	$("#dataTable_clzj_mx").jqGrid('setGridParam',{url : "${ctx}/admin/prjSignAnalysis/goDetailDetailJqGrid?code="+row.CustCode+"&prjItem="+row.PrjItem});
            	$("#dataTable_clzj_mx").jqGrid().trigger('reloadGrid');
            },
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<form type="hidden" role="form" class="form-search" id="dataForm"
			action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" /> <input
				type="hidden" id="dateFrom" name="dateFrom"
				value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>" />
			<input type="hidden" id="dateTo" name="dateTo"
				value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>" />
		</form>
		<div class="pageContent"
			style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
			<table id="dataTable_clzj"></table>
		</div>
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					签到明细&nbsp;&nbsp;
					<button type="button" class="btn btn-system"
						onclick="doExcelNow('签到明细','dataTable_clzj_mx','dataForm')">导出excel</button>
				</div>
			</div>
		</div>
		<div class="pageContent"
			style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
			<table id="dataTable_clzj_mx"></table>
		</div>
	</div>
</body>
</html>


