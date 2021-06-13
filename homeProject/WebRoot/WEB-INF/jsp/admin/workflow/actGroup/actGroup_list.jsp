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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>OA角色管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${actGroup.expired}"/>
				<input type="hidden" name="jsonString"/>
				<ul class="ul-form">
					<li>
						<label>角色名称</label>
						<input type="text" id="name" name="name" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="ACTGROUP_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" id="roleMember">
					<span>角色成员</span>
				</button>
				<button type="button" class="btn btn-system" id="roleAuthrity">
					<span>角色权限</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/actGroup/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"ID_", index:"ID_", width:150, label:"角色编号", sortable:true, align:"left"},
					{name:"NAME_", index:"NAME_", width:180, label:"角色名称", sortable:true, align:"left"},
					{name:"TYPE_", index:"TYPE_", width:500, label:"相关流程", sortable:true, align:"left"},
				],
				ondblClickRow: function(){
					roleMember();
				}
			});
			$("#save").on("click", function() {
				Global.Dialog.showDialog("save", {
					title : "OA角色管理——新增",
					url : "${ctx}/admin/actGroup/goSave",
					height : 268,
					width : 700,
					returnFun : goto_query
				});
			});
			
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("update",{
					title:"OA角色管理——编辑",
					url:"${ctx}/admin/actGroup/goUpdate",
					postData:{
						m_umState:"M",
						id:ret.ID_,
					},
					height:270,
					width:700,
					returnFun:goto_query
				});
			});
			
			$("#view").on("click", function () {
				view();
			});
			
			$("#roleMember").on("click",function () {
				roleMember();
			})
			
			$("#roleAuthrity").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("authority",{
					title:"OA角色管理——设置权限",
					url:"${ctx}/admin/actGroup/goAuthority",
					postData:{
						id:ret.ID_,
					},
					height:611,
					width:741,
					returnFun:goto_query
				});
			})
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"OA角色管理——查看",
				url:"${ctx}/admin/actGroup/goView",
				postData:{
					m_umState:"V",
					id:ret.ID_,
				},
				height:270,
				width:700,
				returnFun:goto_query
			});
		}
		function roleMember() {
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"OA角色管理——角色管理",
				url:"${ctx}/admin/actGroup/goRoleMember",
				postData:{
					id:ret.ID_,
				},
				height:611,
				width:741,
				returnFun:goto_query
			});
		}
		/*function doExcel(){
			var url = "${ctx}/admin/actGroup/doExcel";
			doExcelAll(url);
		}*/
	</script>
</body>
</html>
