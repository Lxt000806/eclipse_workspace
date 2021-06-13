<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>领料预申请--批量新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

/**初始化表格*/
$(function(){
		console.log($("#fp__tWfCust_TravelExpenseClaim__0__Partner").val());
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-85,
			postData: $("#page_form").jsonForm(),
			styleUI: 'Bootstrap',
			multiselect:true,
			rowList: [50],
			colModel : [
			 	{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
				{name: "confitemtype", index: "confitemtype", width: 80, label: "施工材料分类", sortable: true, align: "left",hidden:true},
			 	{name: "descr", index: "descr", width: 100, label: "施工材料分类", sortable: true, align: "left"},
				{name: "shouldorderdate", index: "shouldorderdate", width: 90, label: "应下单时间", sortable: true, align: "left",formatter: formatDate},
				{name: "shouldordernode", index: "shouldordernode", width: 80, label: "应下单节点", sortable: true, align: "left"},
				{name: "isconfirmed", index: "isconfirmed", width: 90, label: "材料是否确认", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 100, label: "项目经理", sortable: true, align: "left"},
				{name: "prjdeptdescr", index: "prjdeptdescr", width: 100, label: "工程部", sortable: true, align: "left"},
				{name: "zcgjnamechi", index: "zcgjnamechi", width: 80, label: "主材管家", sortable: true, align: "left"},
				{name: "confirmremarks", index: "confirmremarks", width: 150, label: "材料确认说明", sortable: true, align: "left",hidden:true},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "材料确认时间", sortable: true, align: "left",formatter: formatTime,hidden:true},
				{name: "nodedatetype", index: "nodedatetype", width: 70, label: "节点类型", sortable: true, align: "left",hidden:true},
				{name: "nodetriggerdate", index: "nodetriggerdate", width: 90, label: "节点触发时间", sortable: true, align: "left",formatter: formatDate,hidden:true},
		   		{name: "paytype", index: "paytype", width: 80, label: "付款类型", sortable: true, align: "left",hidden:true},
            ],
            gridComplete: function () {
            	$("#saveBtn").removeAttr("disabled","disabled");
            }
		});
		
		$("#projectMan").openComponent_employee();
});
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url:"${ctx}/admin/itemPreApp/goItemShouldOrderJqGrid",
    	postData: postData,
    	page:1
  	}).trigger("reloadGrid");
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemPreApp/doBatchSaveExcel";
	doExcelAll(url);
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("detail",{
		  title:"查看明细",
		  url:"${ctx}/admin/itemShouldOrder/goDetail",
		  postData:{
		  	code:ret.custcode,
		  	confItemType:ret.confitemtype,
		  	viewAll:"0"
		  },
		  height:700,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doSave(){
	var param= Global.JqGrid.selectToJson("dataTable");
	var arr = Object.keys(param);
	if(arr.length == 0){
		art.dialog({
			content:"未选择数据,无法生成领料单",
		});
		return;
	}
		
	Global.Form.submit("page_form","${ctx}/admin/itemPreApp/doBatchSave",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					$("#saveBtn").attr("disabled","disabled");
					$("#_form_token_uniq_id").val(ret.token.token);
					goto_query();
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
};
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" onclick="doSave()" id="saveBtn">生成领料单</button>
					<button type="button" class="btn btn-system" onclick="view()">查看材料明细</button>
					<button type="button" class="btn btn-system" onclick="doExcel()">导出Excel</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li>
						<label>施工材料分类</label>
						<house:dict id="confItemType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    sql="select Code, Descr from tConfItemType where dispatchOrder = '1' and Expired ='F'"></house:dict>
					</li>
					<li>
						<label>应下单节点</label>
						<house:dict id="prjItem" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    sql="SELECT Code,Descr FROM tPrjItem1 a WHERE 1=1 and expired='F' 
								    	and exists (select 1 from tItemSendNodeDetail in_a where in_a.beginNode = a.Code )
								    "></house:dict>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>触发截止时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemPreApp.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${itemPreApp.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${customer.projectMan }" />
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
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


