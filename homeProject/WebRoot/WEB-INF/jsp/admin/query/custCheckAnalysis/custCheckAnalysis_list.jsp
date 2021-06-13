<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户结算分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/custCheckAnalysis/doExcel";
	doExcelAll(url);
}
function goto_query(){
	if($.trim($("#dateFrom").val())==''){
			art.dialog({content: "结算开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#dateTo").val())==''){
			art.dialog({content: "结算结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#dateFrom").val()));
     var dateEnd = Date.parse($.trim($("#dateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
			return false;
     } 
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custCheckAnalysis/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "depart1", index: "depart1", width: 100, label: "一级部门", sortable: true, align: "left",hidden: true},
				{name: "depart2", index: "depart2", width: 100, label: "二级部门", sortable: true, align: "left",hidden: true},
				{name: "depart1descr", index: "depart1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
				{name: "depart2descr", index: "depart2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
				{name: "custtype", index: "custtype", width: 100, label: "客户类型", sortable: true, align: "left",hidden: true},
				{name: "constructtype", index: "constructtype", width: 100, label: "constructtype", sortable: true, align: "left",hidden: true},
				{name: "iscontainsoft", index: "iscontainsoft", width: 100, label: "iscontainsoft", sortable: true, align: "left",hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 120, label: "客户类型", sortable: true, align: "left"},
				{name: "count", index: "count", width: 80, label: "套数", sortable: true, align: "right",sum: true},
				{name: "checkamount", index: "checkamount", width: 100, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 100, label: "签单合同额", sortable: true, align: "right", sum: true},
				{name: "sumchgamount", index: "sumchgamount", width: 100, label: "材料增减", sortable: true, align: "right", sum: true},
				{name: "sumbasechgamount", index: "sumbasechgamount", width: 100, label: "基础增减", sortable: true, align: "right", sum: true},
				{name: "returnordernum", index: "returnordernum", width: 100, label: "退单数", sortable: true, align: "right", sum: true},
				{name: "returnorderfee", index: "returnorderfee", width: 100, label: "退单金额", sortable: true, align: "right", sum: true},
				{name: "returnnum", index: "returnnum", width: 100, label: "退订数", sortable: true, align: "right", sum: true}
		    ],
	  	gridComplete:function(){
			    var tableId='dataTable';
		        if  ($("#statistcsMethod").val() == '1') {
		        	$("#"+tableId).jqGrid('showCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr");
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");			  
			   	} else if ($("#statistcsMethod").val() == '2') {
					$("#"+tableId).jqGrid('showCol', "depart1descr");
		        	$("#"+tableId).jqGrid('showCol', "depart2descr");
					$("#"+tableId).jqGrid('hideCol', "custtypedescr");		
				} else if ($("#statistcsMethod").val() == '3') {
					$("#"+tableId).jqGrid('hideCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr");
					$("#"+tableId).jqGrid('showCol', "custtypedescr");		
				} else if ($("#statistcsMethod").val() == '4') {
					$("#"+tableId).jqGrid('hideCol', "depart1descr");
		        	$("#"+tableId).jqGrid('hideCol', "depart2descr");
					$("#"+tableId).jqGrid('showCol', "custtypedescr");	
				 
		        }
        	
      }
        	
	  });
});
function goView(){
	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录！",width: 200});
		return false;
	}
	if($.trim($("#dateFrom").val())==''){
			art.dialog({content: "结算开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#dateTo").val())==''){
			art.dialog({content: "结算结束日期不能为空",width: 200});
			return false;
	}
	var dateStart = Date.parse($.trim($("#dateFrom").val()));
	var dateEnd = Date.parse($.trim($("#dateTo").val()));
	if(dateStart>dateEnd){
		art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
		return false;
	}
	var statistcsMethod = $("#statistcsMethod").val();
	Global.Dialog.showDialog("custCheckAnalysisView", {
   	  title: "客户结算分析—查看",
   	  url: "${ctx}/admin/custCheckAnalysis/goView",
   	  height: 550,
   	  width: 1000,
   	  postData: {
   	  	dateFrom: $("#dateFrom").val(),
   	  	dateTo: $("#dateTo").val(),
   	  	role: $("#role").val(),
   	  	statistcsMethod: $("#statistcsMethod").val(),
   	  	department1: statistcsMethod == "3" || statistcsMethod == "4" ? $("#department1").val() : row.depart1,
		department2: statistcsMethod == "3" || statistcsMethod == "4" ? $("#department2").val() : row.depart2,
		custtype: statistcsMethod == "1" || statistcsMethod == "2" ? $("#custType").val() : row.custtype,
		constructType: statistcsMethod == "3" || statistcsMethod == "4" ? row.constructtype : "",
		isContainSoft: statistcsMethod == "4" ? row.iscontainsoft : ""
   	  }
   	}); 	
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>一级部门</label>
							<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${employee.department1 }"></house:department1>
						</li>
						<li>
								<label>二级部门</label>
							<house:department2 id="department2" dictCode="${employee.department1 }" value="${employee.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
							</li>
						<li>
							<label>结算日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
						</li>
						<li>
							<label>角色</label>
							<select id="role" name="role" style="width:160px;">
								<option value="00">按设计师</option>
								<option value="21">按工程部经理</option>
							</select>
						</li>
						<li>
							<label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" style="width:160px;">
								<option value="1">按一级部门</option>
								<option value="2">按二级部门</option>
								<option value="3">按客户类型</option>
								<option value="4">按客户类型-软装</option>
							</select>
						</li>
						<li >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
                	<house:authorize authCode="CUSTCHECKANALYSIS_EXCEL">
							<button type="button" class="btn btn-system " onclick="doExcelNow('客户结算分析')" title="导出检索条件数据">
								<span>导出excel</span>
							</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="goView()">
						<span>查看</span>
					</button>
				</div>
			</div>	<!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


