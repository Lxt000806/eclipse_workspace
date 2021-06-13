<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>项目经理签到分析</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function goto_query(){
	if($.trim($("#dateFrom").val())==""){
		art.dialog({
			content: "统计开始日期不能为空"
		});
		return false;
	} 
	if($.trim($("#dateTo").val())==""){
		art.dialog({
			content: "统计结束日期不能为空"
		});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
 		return false;
    } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjSignAnalysis/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "ProjectMan", index: "ProjectMan", width: 70, label: "项目经理", sortable: true, align: "left",hidden:true},
			{name: "ProjectManDescr", index: "ProjectManDescr", width: 70, label: "项目经理", sortable: true, align: "left"},
		 	{name: "CmpNum", index: "CmpNum", width: 80, label: "节点完工数", sortable: true, align: "right",sum:true},
			{name: "SignTimes", index: "SignTimes", width: 110, label: "节点应签到天数", sortable: true, align: "right",sum:true},
			{name: "RealSignDays", index: "RealSignDays", width: 120, label: "节点实际签到天数", sortable: true, align: "right",sum:true},
			{name: "ValidSignDays", index: "ValidSignDays", width: 120, label: "<span title='实际签到天数大于应签到天数，取应签到天数，否则取实际签到天数'>节点有效签到天数</span>", sortable: true, align: "right",sum:true, excelLabel: "节点有效签到天数"},
			{name: "SignRate", index: "SignRate", width: 80, label: "<span title='有效签到天数/应签到天数'>签到满足率</span>", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return (myRound(cellvalue*100,4))+"%";}, excelLabel: "签到满足率"},
	    ],    
	    rowNum:100000,  
	    pager :'1',
	    gridComplete:function(){
			var SignTimes = parseFloat($("#dataTable").getCol('SignTimes', false, 'sum'));
	  	    var ValidSignDays = parseFloat($("#dataTable").getCol('ValidSignDays', false, 'sum'));
	  	    var SignRate = myRound(SignTimes==0?0:ValidSignDays/SignTimes, 4);
	  	    $("#dataTable").footerData('set', {"SignRate": SignRate});
        },
	});
	$("#projectMan").openComponent_employee();
});

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("detail",{
		  title:"查看明细",
		  url:"${ctx}/admin/prjSignAnalysis/goDetail",
		  postData:{
		  	dateFrom:$("#dateFrom").val(),
		  	dateTo:$("#dateTo").val(),
		  	projectMan:ret.ProjectMan
		  },
		  height: 700,
		  width:1300,
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where  deptype='3' and Expired='F' order By dispSeq  " 
						sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
					</li>
					<li>
						<label>统计时间从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;" />
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="PRJSIGNANALYSIS_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="PRJSIGNANALYSIS_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcelNow('项目经理签到分析','dataTable', 'page_form')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


