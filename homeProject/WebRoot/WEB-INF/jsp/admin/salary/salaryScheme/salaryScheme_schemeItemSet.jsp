<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>薪酬项目设置</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
		.SelectBlack{
			background-color:white!important;
			color:black!important;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	var detailJson = ${salaryScheme.detailJson};
	// 未选项目
	var gridOption = {	
			height:$(document).height()-$("#content-list").offset().top-100,
			rowNum:1000,
			loadonce:true,
			colModel : [
				{name:"code", index:"code", width:80, label:"项目编号", sortable:true ,align:"left",},
				{name:"descr", index:"descr", width:120, label:"项目名称", sortable:true ,align:"left",},
				{name:"isshow", index:"isshow", width:120, label:"isshow", sortable:true ,align:"left",formatter:getIsShow,hidden:true},
				{name:"addbtn", index:"addbtn", width:60, label:"操作", title:"操作",sortable:true ,align:"center",formatter:getAddBtn},
			],
			gridComplete:function(){
				$("#dataTable").find("td").addClass("SelectBlack");
			},
			onCellSelect: function(id,iCol,cellParam,e){
				var ids = $("#dataTable").jqGrid("getDataIDs");  
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$("#"+ids[i]).find("td").removeClass("SelectBG");
					}
				}
				$("#"+ids[id-1]).find("td").addClass("SelectBlack");
			},
		}; 
	
	// 已选项目
	var gridOptionSelected = {	
			height:$(document).height()-$("#content-list").offset().top-100,
			rowNum:1000,
			loadonce:true,
			colModel : [
				{name:"code", index:"code", width:80, label:"项目编号", sortable:true ,align:"left",},
				{name:"descr", index:"descr", width:120, label:"项目名称", sortable:true ,align:"left",},
				{name:"isshow", index:"isshow", width:120, label:"isshow", sortable:true ,align:"left",hidden:true,},
				{name:"isrptshow", index:"isrptshow", width:80, label:"工资单显示", sortable:false,align:"left", hidden:true},
				{name:"rptdispseq", index:"rptdispseq", width:80, label:"工资单序号", sortable:false ,align:"right",hidden:true},
				{name:"delbtn", index:"delbtn", width:60, label:"操作",title:"操作", sortable:true ,align:"center",formatter:getDelBtn},
			],
			gridComplete:function(){
				$("#dataTableSelected").find("td").addClass("SelectBlack");
			},
			onCellSelect: function(id,iCol,cellParam,e){
				var ids = $("#dataTableSelected").jqGrid("getDataIDs");  
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$("#dataTableSelected").find("#"+ids[i]).find("td").removeClass("SelectBG");
					}
				}
				$("#dataTableSelected").find("#"+ids[id-1]).find("td").addClass("SelectBlack");
			},
		}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryScheme/goSalaryItemJqgrid",
		postData:{salaryItemCodes:$("#salaryItemCodes").val(),},
	});
	
	Global.JqGrid.initJqGrid("dataTableSelected",gridOptionSelected);
	if(detailJson.length>0){
		console.log(detailJson);
		$.each(detailJson,function(k,v){
			v.code = v.salaryitem;
			v.descr = v.salaryitemdescr;
			v.isshow = v.isshow;
			v.isrptshow = v.isrptshow;
			v.rptdispseq = v.rptdispseq;
			$("#dataTableSelected").addRowData(myRound(k)+1, v, "last");
		});
	}
	/* $.extend(gridOptionSelected,{
		url:"${ctx}/admin/salaryScheme/goSalaryItemJqgrid",
		postData:{selItemCodes:$.trim($("#salaryItemCodes").val())==""?"-1":$.trim($("#salaryItemCodes").val())},
	}); */
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	
	$("#dataTableSelected").jqGrid("sortableRows", {
		items :".jqgrow:not(.unsortable)",
	});
	
	function getIsShow(cellvalue, options, rowObject){ 
		if(cellvalue){
			return cellvalue;
		}
		return"1";
	}
	
	function getAddBtn(cellvalue, options, rowObject){ 

		return"<button type=\"button\" class=\"btn btn-system btn-xs\" onclick=\"updateRows(" + options.rowId +",'dataTable'"+",'dataTableSelected'"+");\"> → </button>";
	}
	
	function getDelBtn(cellvalue, options, rowObject){ 
		
		return"<button type=\"button\" class=\"btn btn-system btn-xs\" onclick=\"updateRows(" + options.rowId +",'dataTableSelected'"+",'dataTable'"+");\"> × </button>";
	}
	
});

function updateRows(id, fromTable, toTable){
	
    var addData = $("#" + fromTable).jqGrid("getRowData", id);
    var rowNum = $("#"+ toTable).jqGrid("getGridParam","records");
    
	$("#"+fromTable).delRowData(id);
	$("#"+toTable).addRowData(myRound(rowNum)+1, addData,"last");
}

function doSave(){
	var rowDatas=$("#dataTableSelected").jqGrid("getRowData");
	
	Global.Dialog.returnData = rowDatas;
    closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" hidden="true">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="salaryItemCodes" name="salaryItemCodes" value="${salaryScheme.salaryItemCodes }"/>
					</form>
				</div>
			</div>
			<div id="content-list" style="width:47%;float:left">
				<label style="padding-top:10px">待选项目</label>
				<table id="dataTable"></table>
			</div>
			<div id="content-list1" style="width:47%;float:right">
				<label style="padding-top:10px">已选项目</label>
				<table id="dataTableSelected"></table>
			</div>
			
		</div>
	</body>	
</html>
