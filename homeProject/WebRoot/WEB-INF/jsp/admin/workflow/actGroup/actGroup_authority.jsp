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
						<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin()">
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
					<li class="active"><a href="#tabView_act_user" data-toggle="tab">角色权限</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_act_user" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id=goSaveGroupAuth>
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="delAuth">
									<span>删除</span>
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
	$(function() {
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/actGroup/goRoleAuthorityJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-35,
			styleUI: "Bootstrap", 
			rowNum : 10000000,
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "wfprocno", index: "wfprocno", width: 100, label: "流程编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 180, label: "流程名称", sortable: true, align: "left",},
			],
		});

		$("#goSaveGroupAuth").on("click",function(){
			Global.Dialog.showDialog("userSave",{
				title:"角色权限——新增",
				url:"${ctx}/admin/actGroup/goWfProcessAdd",
				postData:{id:"${actGroup.id}"},
				height:438,
				width:450,
				returnFun:goto_query
			});
		});
		 
		$("#delAuth").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({content: "请选择一条记录进行删除操作",width: 220});
				return false;
			}
			art.dialog({
				content:"是否删除该记录？",
				lock: true,
				width: 200,
				height: 80,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/actGroup/doDelAuth",
						type:"post",
						data:{pk:ret.pk},
						dataType:"json",
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
						},
						success:function(obj){
							if(obj.rs){
								art.dialog({
									content: "删除成功",
									time: 1000,
								});
								$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							}else{
								art.dialog({
									content: obj.msg,
									time: 1000,
								});
							}
						}
					});
				},
				cancel: function () {
					return true;
				}
			});
			
		});
	});
	</script>
</html>
