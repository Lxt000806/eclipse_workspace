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
		colModel : [
			{name:"cmpdescr", index:"cmpdescr", width:80, label:"公司", sortable:true ,align:"left",},
			{name:"dept1descr", index:"dept1descr", width:80, label:"一级部门", sortable:true ,align:"left",},
			{name:"dept2descr", index:"dept2descr", width:80, label:"二级部门", sortable:true ,align:"left",},
			{name:"empname", index:"empname", width:80, label:"姓名", sortable:true ,align:"left",},
			{name:"empcode", index:"empcode", width:80, label:"工号", sortable:true ,align:"left",},
			{name:"posiclassdescr", index:"posiclassdescr", width:80, label:"职位类别", sortable:true ,align:"left",},
			{name:"joindate", index:"joindate", width:80, label:"入职时间", sortable:true ,align:"left",formatter:formatDate},
			{name:"leavedate", index:"leavedate", width:80, label:"离职时间", sortable:true ,align:"left",formatter:formatDate},
		],
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryScheme/goEmpListJqgrid",
		postData:{pk:$.trim($("#pk").val())}
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
				<input type="hidden" name="pk" id="pk" value="${salaryScheme.pk }"/>
				<ul class="ul-form">
					<li>
                        <label style="width:100px">一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
					<li>
                        <label style="width:100px">二级部门</label>
                        <house:DictMulitSelect id="department2" dictCode="" sql="select code, desc1 from tDepartment2 where 1=2" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment2()"></house:DictMulitSelect>
                    </li>
                    <li hidden="true">
                        <label style="width:100px">三级部门</label>
                        <house:DictMulitSelect id="department3" dictCode="" sql="select code, desc1 from tDepartment3 where 1=2"
                           sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
                    </li>
					<li>
						<label style="width:100px">查询条件</label>
						<input type="text" id="queryCondition" name="queryCondition"style="width:160px;" placeholder="姓名/工号/身份证/财务编码"/>
					</li>

					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
				</ul>
			</form>
		</div>
			<div class="container-fluid">  
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
