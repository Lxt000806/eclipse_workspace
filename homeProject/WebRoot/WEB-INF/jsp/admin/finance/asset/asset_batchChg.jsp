<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>固定资产管理——批量减少</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
	<script type="text/javascript">
	function onEmployeeSelect(json) {
      var rowid = $("#dataTable").getGridParam("selrow");
      $("#dataTable").setRowData(rowid, {'newusemandescr': json.namechi,'newuseman': json.number});
    }
    
    function onDepartmentSelect(json) {
      var rowid = $("#dataTable").getGridParam("selrow");
      $("#dataTable").setRowData(rowid, {'newdept': json.code,'newdeptdescr': json.desc2});
    }
    
	$(function(){
		$("#department").openComponent_department();	
		var postData = $("#page_form").jsonForm();
		/**初始化表格*/
		var jqGridOption = {
	   		//url:"${ctx}/admin/asset/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-120,
	        cellEdit: true,
	        cellsubmit: 'clientArray',
	        styleUI: 'Bootstrap',
	        rowNum: 10000,
			colModel : [
			  	{name: "code", index: "code", width: 65, label: "资产编号", sortable: true, align: "left",},
				{name: "descr", index: "descr", width: 100, label: "资产名称", sortable: true, align: "left"},
				{name: "model", index: "model", width: 120, label: "规格型号", sortable: true, align: "left"},
				{name: "dept", index: "dept", width: 60, label: "原部门编号", sortable: true, align: "left",hidden:true},
				{name: "deptdescr", index: "deptdescr", width: 100, label: "变动前部门", sortable: true, align: "left",},
				{name: "usemandescr", index: "usemandescr", width: 100, label: "变动前使用人", sortable: true, align: "left",},
				{name: "newdeptdescr", index: "newdeptdescr", width: 100, label: "变动后部门", sortable: true, align: "left",
					editable:true,edittype:'text',
	      			editoptions:{ 	dataEvents:[
		      							{
		      								type:'focus',
		      							 	fn:function(e){
		      							 		$(e.target).openComponent_departmentDescr({condition:{},onSelect:onDepartmentSelect});
		      								}
		      							}
	      							]
	      						}
				},
				{name: "newdept", index: "newdept", width: 100, label: "变动后部门编号", sortable: true, align: "left",hidden:true},
				{name: "useman", index: "useman", width: 100, label: "使用人编号", sortable: true, align: "left",hidden:true},
				{name: "newusemandescr", index: "newusemandescr", width: 100, label: "变动后使用人", sortable: true, align: "left",
					editable:true,edittype:'text',
	      			editoptions:{ 	dataEvents:[
		      							{
		      								type:'focus',
		      							 	fn:function(e){
		      							 		$(e.target).openComponent_employeeDescr({condition:{},onSelect:onEmployeeSelect});
		      								}
		      							}
	      							]
	      						}
				},
				{name: "aftaddress", index: "aftaddress", width: 150, label: "新存放地址", sortable: true, align: "left",editable:true},
				{name: "chgremarks", index: "chgremarks", width: 150, label: "变动原因", sortable: true, align: "left",editable:true},
				{name: "newuseman", index: "newuseman", width: 100, label: "变动后使用人编号", sortable: true, align: "left",hidden:true},
			],
			onCellSelect: function(id,iCol,cellParam,e){
				
				var ele = $("#dataTable").find("tr");
				for(var i = 1; i < ele.length; i++){
					$(ele[i]).attr("id",i);
				}
			},
			onSelectRow: function(rowId,status){
				var element = $("#dataTable").find("tr");
				setRowColor($(element[rowId]));
				return;
			}
		};
		Global.JqGrid.initEditJqGrid("dataTable", jqGridOption);
	});
	
	function goto_query(){
		var department = $("#department").val();
		if(department == ""){
			art.dialog({
				content:"请选择部门",
			});
			return;
		}
		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/asset/goJqGrid", postData:$("#page_form").jsonForm(), page:1, sortname:''}).trigger("reloadGrid");
	}
	
	function deleteRow(){
		var rowid = $("#dataTable").getGridParam("selrow");
		if(!rowid){
			art.dialog({
				content:"请选择需要删除的记录",
			});
			return;
		}
		
		$('#dataTable').delRowData(rowid);
		
	}
	
	function doBatchSave(){
		var param= Global.JqGrid.allToJson("dataTable");
		if(param.datas.length==0){
			art.dialog({
				content:"请先加载数据",
			});
			return;
		}
		
		Global.Form.submit("page_form","${ctx}/admin/asset/doBatchChg",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						$("#_form_token_uniq_id").val(ret.token.token);
						$("#dataTable").jqGrid("clearGridData");
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
	
	function setRowColor(e){
		console.log(e);	
		$("#dataTable").find("tr").find("td").removeClass("SelectBG");
		
		e.find("td").addClass("SelectBG")
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn" onclick="doBatchSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					
					<li>
						<label>类型</label>
						<house:xtdm id="chgType" dictCode="ASSETCHGTYPE" value="5" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>部门编号</label>
						<input type="text" id="department" name="department" style="width:160px;" value=""/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm" >
					<button type="button" class="btn btn-system" id="deleteRow" onclick="deleteRow()">
						<span>删除</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
