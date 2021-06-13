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
	<title>薪酬计算方案适用人员</title>
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
		height:$(document).height()-$("#content-list").offset().top-120,
		rowNum:50,
		multiselect:true,
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
		postData:{pk:$.trim($("#salaryScheme").val())}
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

function addSchemeEmp(){
	var count = $("#dataTable").getGridParam("reccount")
	var salaryScheme = $.trim($("#salaryScheme").val());
	
	var rowDatas=$("#dataTable").jqGrid("getRowData");
	var empCodes = "";
	
	if(rowDatas.length>0){
		for(var i = 0; i < rowDatas.length; i++){
			if(empCodes == ""){
				empCodes = rowDatas[i].empcode+",";
			} else {
				empCodes += rowDatas[i].empcode+",";
			}
		}
	}
	if(empCodes.length > 0){
		empCodes = empCodes.substring(0, empCodes.length-1); 
	}
	
	Global.Dialog.showDialog("addSchemeEmp",{
		title:"方案适用人员——新增",
		postData:{salaryScheme: salaryScheme, empCodes: empCodes},
		url:"${ctx}/admin/salaryCalc/goAddSchemeEmp",
		height:690,
		width:1020,
        resizable: true,
		returnFun:function(data){
			if(data){
				$.each(data,function(k,v){
					v.cmpdescr = v.consigncmpdescr,
					v.dept1descr = v.department1descr,
					v.dept2descr = v.department2descr
					$("#dataTable").addRowData(myRound(count)+myRound(k)+1, v, "last");
				});
			}
		}
	});	
}

function doSave(){
	var param= Global.JqGrid.allToJson("dataTable");
	console.log(param);
	Global.Form.submit("dataForm","${ctx}/admin/salaryCalc/doSchemeEmpSave",param,function(ret){
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

function delRow(){
	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	
	if(!ids.length > 0){
		art.dialog({
			content:"请选择要删除的数据！"
		});
		return;
	}
	console.log(ids);
	
	var delIds = [];
	var rowNum = $("#dataTable").jqGrid("getGridParam", "records");
	
	for(var i = 0; i < ids.length; i++){
		delIds[i] = myRound(ids[i]);
	}
	delIds = getDescendingOrder(delIds);
	
	if (ids && ids.length>0) {
		art.dialog({
			content: "删除所选记录？",
			lock: true,
			ok: function () {
				for(var i = 0; i < delIds.length; i++){
					$("#dataTable").delRowData(myRound(delIds[i]));
				}
			},
			cancel: function () {
				return true;
			}
		});

	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}

function getDescendingOrder(ids){
   	if(!ids && !ids.length > 0 ){
   		return ids;
   	}
	for(var i = 0; i < ids.length; i++){
			ids[i] = myRound(ids[i]);
		for(var j = i + 1; j < ids.length; j++){
			ids[j] = myRound(ids[j]);
			if(myRound(ids[i]) < myRound(ids[j])){
				var x = 0;
				x = myRound(ids[i]);
				ids[i] = myRound(ids[j]);
				ids[j] = myRound(x);
			} else{
				ids[i] = myRound(ids[i]);
			}
		}
	} 
	return ids;
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBut" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<li class="form-validate">
								<label>薪酬方案</label>
								<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
									 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }" disabled="true"></house:dict>
							</li>
							<li class="form-validate">
								<label>方案类型</label>
								<house:dict id="salarySchemeType" dictCode="" sql="select Code+' '+descr descr,code from tSalarySchemeType where expired = 'F'" 
											sqlValueKey="code" sqlLableKey="descr" value="${salaryData.salarySchemeType }" disabled="true"></house:dict>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">适用人员</a></li>
			    </ul> 
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system">
								<div class="panel-body">
									<div class="btn-group-xs">
										<button type="button" class="btn btn-system" onclick="addSchemeEmp()">
											<span>新增</span>
										</button>
										<button type="button" class="btn btn-system" id="delRow" onclick="delRow()">
											<span>删除</span>
										</button>
									</div>
								</div>
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
