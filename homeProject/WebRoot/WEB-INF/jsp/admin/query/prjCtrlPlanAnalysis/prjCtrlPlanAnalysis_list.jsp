<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<title>工地发包预算分析</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.panelBar {
	height: 26px;
}
</style>
<script type="text/javascript">
 $(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		rowNum:100000,  
		pager :'1',
		colModel : [
			{name: "address", index: "address", width: 140, label: "楼盘地址", sortable: true, align: "left",frozen : true,count:true, },
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name :"signdate",index : "signdate",width : 70,label:"签单时间",sortable : true,align : "left",formatter: formatDate},
			{name: "projectmandescr", index: "projectmandescr", width: 65, label: "项目经理", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "layoutdescr", index: "layoutdescr", width: 55, label: "户型", sortable: true, align: "left"},
			{name: "innerarea", index: "innerarea", width: 65, label: "套内面积", sortable: true, align: "right"},
			{name: "basectrlamt", index: "basectrlamt", width: 65, label: "总发包额", sortable: true, align: "right", sum: true},
			{name: "setaddctrl", index: "setaddctrl", width: 75, label: "套餐外发包", sortable: true, align: "right", sum: true},
			{name: "basequotaprice", index: "basequotaprice", width: 90, label: "套内发包单价", sortable: true, align: "right"},
			{name: "costfix", index: "costfix", width: 65, label: "成本定额", sortable: true, align: "right", sum: true},
			{name: "ctrlcostper", index: "ctrlcostper", width: 80, label: "发包成本比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalPlaces: 0,suffix: "%"}},
			{name: "cost", index: "cost", width: 80, label: "实际已支出", sortable: true, align: "right",sum: true},
			{name: "setincostper", index: "setincostper", width: 88, label: "套内成本单价", sortable: true, align: "right"},
			{name: "plancommi", index: "plancommi", width: 65, label: "预计提成", sortable: true, align: "right"},
			{name: "plancommiper", index: "plancommiper", width: 100, label: "预计每平米提成", sortable: true, align: "right"},
			{name: "projectctrladj", index: "projectctrladj", width: 65, label: "发包补贴", sortable: true, align: "right", hidden:true},
			{name: "costfixper", index: "costfixper", width: 88, label: "成本定额单价", sortable: true, align: "right"},
        ], 
        loadComplete: function(){
              	$('.ui-jqgrid-bdiv').scrollTop(0);
              	frozenHeightReset("dataTable");
         },         
	 });
	 $("#dataTable").jqGrid("setFrozenColumns");
	 $("#projectMan").openComponent_employee();
});
function goto_query(){
	if($.trim($("#signDateFrom").val())==''){
			art.dialog({content: "签单开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#signDateTo").val())==''){
			art.dialog({content: "签单结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#signDateFrom").val()));
     var dateEnd = Date.parse($.trim($("#signDateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "签单开始日期不能大于结束日期",width: 200});
			return false;
     } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjCtrlPlanAnalysis/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}  
//导出EXCEL
function doExcel(){
  	var url = "${ctx}/admin/prjCtrlPlanAnalysis/doExcel";
  	doExcelAll(url);
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#checkStatus").val('');
	$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
}
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired"
					 />
				<ul class="ul-form">
					<li><label>客户类型</label> <house:DictMulitSelect id="custType" dictCode=""
							sql="select Code,  Desc1 Descr from tcustType  where IsAddAllInfo='1' order by  dispSeq " sqlValueKey="Code"
							sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;" />
					</li>
					<li>
						<label>签单日期</label>
						<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="signDateTo" name="signDateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label></label> 
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

</html>


