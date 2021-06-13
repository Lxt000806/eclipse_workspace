<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system view" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>编号</label> 
								<input type="text" id="id" name="id" style="width:160px;"
									value="${actGroup.id}" readonly />
							</li>
							<li class="form-validate">
								<label>名称</label> 
								<input type="text" id="name" name="name" style="width:160px;"
									value="${actGroup.name}" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label class="control-textarea">相关流程</label>
								<textarea id="type" name="type" rows="2" readonly="true">${actGroup.type }</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabView_act_user" data-toggle="tab">角色成员</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_act_user" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="userSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="userUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="userDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="userView">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		var originalData, originalDataTable;
		$(function() {
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/actGroup/goUserJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-35,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
				colModel : [
					{name: "id_", index: "id_", width: 100, label: "操作员编码", sortable: true, align: "left"},
					{name: "first_", index: "first_", width: 180, label: "用户名", sortable: true, align: "left", hidden: true},
					{name: "zwxm", index: "zwxm", width: 180, label: "中文姓名", sortable: true, align: "left"},
				],
				loadonce: true,
				ondblClickRow: function(){
					view();
				},
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			originalData = $("#page_form").serialize();
			$("#closeBtn").on("click",function(){
				doClose();
			});
			replaceCloseEvt("update", doClose);
			$("#saveBtn").on("click", function() {
				doSave();
			});
			// 明细新增
			$("#userSave").on("click",function(){
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				var ids = Global.JqGrid.allToJson("dataTable","id_");
				var keys = ids.keys;//获取user的数组
				Global.Dialog.showDialog("userSave",{
					title:"角色成员——新增",
					url:"${ctx}/admin/actGroup/goWin",
					postData:{m_umState:"A",keys:keys},
					height:308,
					width:450,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var json = {
									id_: v.id,
									first_:v.first,
									zwxm:v.first,
							  	};
							  	Global.JqGrid.addRowData("dataTable",json);
							});
						}
					}
				});
			});
			// 编辑
			$("#userUpdate").on("click",function(){
				var ret=selectDataTableRow();
				/* 选择数据的id */
				var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!ret){
					art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
					return false;
				}
				var users = Global.JqGrid.allToJson("dataTable","id_");
				var keys = users.keys;//获取user的数组
				var index = keys.indexOf(ret.id_);
				if (index > -1) keys.splice(index, 1); //去掉选择的user
				Global.Dialog.showDialog("userUpdate",{
					title:"角色成员——编辑",
					url:"${ctx}/admin/actGroup/goWin",
					postData:{id:ret.id_,first:ret.zwxm,last:ret.last_, m_umState:"M", keys:keys},
					height:308,
					width:450,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var json = {
									id_: v.id,
									first_:v.first,
									zwxm:v.first,
									last_:v.last
							  	};
							   	$("#dataTable").setRowData(rowId,json);
							});
						}
					}
				});
			});
			/* 明细删除 */
			$("#userDelete").on("click",function(){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!id){
					art.dialog({content: "请选择一条记录进行删除操作",width: 220});
					return false;
				}
				art.dialog({
					content:"是否删除该记录？",
					lock: true,
					width: 200,
					height: 80,
					ok: function () {
						Global.JqGrid.delRowData("dataTable",id);
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
				
			});
			$("#userView").on("click", function () {//查看
				view();
			});
		});
		function view(){
			var ret=selectDataTableRow();
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");/* 选择数据的id */
			if(!ret){
				art.dialog({content: "请选择一条记录进行查看操作",width: 220});
				return false;
			}
			Global.Dialog.showDialog("userView",{
				title:"角色成员——查看",
				url:"${ctx}/admin/actGroup/goWin",
				postData:{id:ret.id_,first:ret.zwxm,last:ret.last_, m_umState:"V"},
				height:308,
				width:450,
			});
		}
		function doSave(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var param= Global.JqGrid.allToJson("dataTable");
			if(param.datas.length == 0){
				art.dialog({content: "请输入角色成员",width: 220});
				return;
			}
			$.extend(param,datas);/* 将datas（json）合并到param（json）中 */
			$.ajax({
				url:"${ctx}/admin/actGroup/doUserSave",
				type: "post",
				data: param,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 700,
							beforeunload: function () {
			    				closeWin();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
		    	}
			});
		}
		function doClose() {
			var newData = $("#page_form").serialize();
			var param= Global.JqGrid.allToJson("dataTable");
			if (param.detailJson != originalDataTable || newData != originalData) {
				art.dialog({
					content: "数据已变动,是否保存？",
					width: 200,
					okValue: "确定",
					ok: function () {
						doSave();
					},
					cancelValue: "取消",
					cancel: function () {
						closeWin();
					}
				});
			} else {
				closeWin();
			}
		}
	</script>
</html>
