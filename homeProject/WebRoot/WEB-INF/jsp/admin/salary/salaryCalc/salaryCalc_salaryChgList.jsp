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
	<title>薪酬方案新增</title>
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
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		multiselect:true,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left", hidden:true},
			{name:"salaryemp", index:"salaryemp", width:80, label:"工号", sortable:true ,align:"left",},
			{name:"empname", index:"empname", width:80, label:"姓名", sortable:true ,align:"left",},
			{name:"idnum", index:"idnum", width:180, label:"身份证号", sortable:true ,align:"left",},
			{name:"itemdescr", index:"itemdescr", width:80, label:"薪酬项目", sortable:true ,align:"left",},
			{name:"schemedescr", index:"schemedescr", width:180, label:"薪酬方案", sortable:true ,align:"left",},
			{name:"adjustvalue", index:"adjustvalue", width:80, label:"调整金额", sortable:true ,align:"right",},
			{name:"remarks", index:"remarks", width:210, label:"备注", sortable:true ,align:"left",},
			{name:"lastupdate", index:"lastupdate", width:80, label:"最后修改时间", sortable:true ,align:"left",formatter:formatDate},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true ,align:"left",},
		],
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryCalc/goSalaryChgJqgrid",
		postData:{salaryMon:$("#salaryMon").val(),salaryScheme:$("#salaryScheme").val()}
	});
	
	function checkBtn(v,x,r){
		if(r.isshow == "1"){
			return "<input type='checkbox' checked onclick='checkRow("+x.rowId+",this)' />";
		} else {
			return "<input type='checkbox' onclick='checkRow("+x.rowId+",this)' />";
		}
	}
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

function checkRow(id,e){
	if($(e).is(':checked')){
		$("#dataTable").setCell(id, 'isshow', "1");
	} else {
		
		$("#dataTable").setCell(id, 'isshow', "0");
	}
}

function itemSet(){
	var rowDatas=$('#dataTable').jqGrid("getRowData");
	var salaryItemCodes = "";
	
	if(rowDatas.length>0){
		for(var i = 0; i < rowDatas.length; i++){
			if(salaryItemCodes == ""){
				salaryItemCodes = rowDatas[i].salaryitem+",";
			} else {
				salaryItemCodes += rowDatas[i].salaryitem+",";
			}
		}
	}
	
	if(salaryItemCodes.length > 0){
		salaryItemCodes =salaryItemCodes.substring(0, salaryItemCodes.length-1); 
	}
	
	Global.Dialog.showDialog("itemSet",{
		title:"项目配置",
		postData:{code:$("#code").val(),salaryItemCodes:salaryItemCodes},
		url:"${ctx}/admin/salaryScheme/goSchemeItemSet",
		height:690,
		width:920,
        resizable: true,
		returnFun:function(data){
			if(data){
				$("#dataTable").jqGrid("clearGridData");

				$.each(data,function(k,v){
					v.salaryitem = v.code;
					v.salaryitemdescr = v.descr;
					v.dispseq = myRound(k)+1;
					v.isshow = "1";
					$("#dataTable").addRowData(myRound(k)+1, v, "last");
				});
			}
		}
	});	
}

function doSave(){
	var param= Global.JqGrid.allToJson("dataTable");
	if(param.datas.length == 0){
		art.dialog({
			content:"请先配置薪酬项目",
		});
		return;
	}
	Global.Form.submit("dataForm","${ctx}/admin/salaryScheme/doUpdate",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();
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
}

function goSalaryChg(){
	var salaryScheme = $.trim($("#salaryScheme").val());
	var salaryMon = $.trim($("#salaryMon").val());

	Global.Dialog.showDialog("salaryChg",{
		title:"薪酬计算——薪酬调整",
		postData:{salaryScheme:salaryScheme,salaryMon:salaryMon},
		url:"${ctx}/admin/salaryCalc/goSalaryChg",
		height:430,
		width:780,
        resizable: true,
		returnFun:goto_query
	});	
}

function goSalaryChgUpdate(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择需要编辑的数据",
		});
		return;
	}

	var salaryScheme = $.trim($("#salaryScheme").val());
	var salaryMon = $.trim($("#salaryMon").val());

	Global.Dialog.showDialog("salaryChg",{
		title:"薪酬计算——薪酬调整",
		postData:{pk: ret.pk},
		url:"${ctx}/admin/salaryCalc/goSalaryChgUpdate",
		height:430,
		width:780,
        resizable: true,
		returnFun:goto_query
	});	
}

function goImport(){
	var salaryScheme = $.trim($("#salaryScheme").val());
	var salaryMon = $.trim($("#salaryMon").val());

	Global.Dialog.showDialog("salaryChgImport",{
		title:"薪酬计算——导入",
		postData:{salaryScheme:salaryScheme,salaryMon:salaryMon},
		url:"${ctx}/admin/salaryCalc/goSalaryChgImport",
		height:590,
		width:1000,
        resizable: true,
		returnFun:goto_query
	});	
}

function doDeleteChg(){
	var ids = Global.JqGrid.selectToJson("dataTable","pk");
	
	if(!ids.fieldJson){
		art.dialog({
			content:"请选择需要删除的记录",
		});
		return;
	}
	
	art.dialog({
		content:"是否确定删除所选记录？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/salaryCalc/doDelSalaryChg",
				type:"post",
				data:{pks:ids.fieldJson},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					art.dialog({
						content: obj.msg,
					});
					$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
				}
			});
		},
		cancel: function () {
			return true;
		}
	});	
}

function doExcel() {
	var url = "${ctx}/admin/salaryCalc/doSalaryChgExcel";
	doExcelAll(url);
} 

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label style="width:80px">计算月份</label>
							<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon where status <> '3'"  
								 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }" disabled="true"></house:dict>
						</li>
						<li class="form-validate">
							<label style="width:80px">薪酬方案</label>
							<house:dict id="salaryScheme" dictCode="" sql=" select pk, descr from tsalaryScheme "  
								 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }" disabled="true"></house:dict>
						</li>
						<li class="form-validate">
							<label style="width:80px">薪酬项目</label>
							<house:dict id="salaryItem" dictCode="" sql=" select code, descr from tSalaryItem where expired ='F' and IsAdjustable = '1' "  
								 sqlValueKey="code" sqlLableKey="descr" ></house:dict>
						</li>
						<li>
							<label style="width:80px">查询条件</label>
							<input type="text" id="queryCondition" name="queryCondition"style="width:160px;" placeholder="姓名/工号/身份证/财务编码"/>
						</li>
	
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					</ul>
				</form>
			</div>
			
			<div class="container-fluid">  
				<div class="btn-panel" >
    			  	<div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="goSalaryChg" onclick="goSalaryChg()">
							<span>新增</span>
						</button>
						<button type="button" class="btn btn-system " id="salaruChgUpdate" onclick="goSalaryChgUpdate()">
							<span>编辑</span>
						</button>
						<button type="button" class="btn btn-system " id="goImport" onclick="goImport()">
							<span>导入</span>
						</button>
						<button type="button" class="btn btn-system " id="deleteChg" onclick="doDeleteChg()">
							<span>删除</span>
						</button>
						<button type="button" class="btn btn-system " onclick="doExcel()">
							<span>导出Excel</span>
						</button>
					</div>
				</div>
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system" >
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
								<table id="dataTablePager"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
