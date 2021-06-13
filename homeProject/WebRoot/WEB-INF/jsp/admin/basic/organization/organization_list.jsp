<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>电子合同机构管理--机构管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/organization/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/organization/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"left", hidden:true}, 
				{name:"orgid", index:"orgid", label:"机构账号", width:120, sortable: true, align:"left"}, 
				{name:"name", index:"name", label:"机构名称", width:150, sortable: true, align:"left"}, 
				{name:"issilencesigndescr", index:"issilencesigndescr", label:"是否静默签署", width:90, sortable: true, align:"left"},
				{name:"thirdpartyuserid", index:"thirdpartyuserid", label:"第三方账号", width:100, sortable: true, align:"left"}, 
				{name:"idnumber", index:"idnumber", label:"机构证件号", width:100, sortable: true, align:"left"}, 
				{name:"orglegalname", index:"orglegalname", label:"法人", width:70, sortable: true, align:"left"}, 
				{name:"orglegalidnumber", index:"orglegalidnumber", label:"法人证件号", width:100, sortable: true, align:"left"}, 
				{name:"flowid", index:"flowid", label:"流程Id", width:100, sortable: true, align:"left"}, 
				{name:"isidentifieddescr", index:"isidentifieddescr", label:"是否认证", width:70, sortable: true, align:"left"}, 
				{name:"identifyurl", index:"identifyurl", label:"认证Url", width:150, sortable: true, align:"left",formatter:urlBtn}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:120, align:"left",formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function urlBtn(v,x,r){
		return v == null ? "" : "<a target='_blank' href='"+v+"'>"+v+"</a>";
	}  
	
	
	function goSave(){
		Global.Dialog.showDialog("organizationSave",{
			title:"机构管理——新增",
			url:"${ctx}/admin/organization/goSave",
			height:325,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goUpdate(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		} 
		Global.Dialog.showDialog("organizationUpdate",{
			title:"机构管理——编辑",
			postData:{id: ret.pk},
			url:"${ctx}/admin/organization/goUpdate",
			height:325,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goView(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		} 
		Global.Dialog.showDialog("organizationView",{
			title:"机构管理——查看",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/organization/goView",
			height:325,
			width:758,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function doIdentity(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		} 
		
		art.dialog({
			content:"是否确定进行刷新认证Url？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/organization/doIdentity",
					type:"post",
					data:{pk:ret.pk},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});
		
	}
	
	function doRefreshIdentity(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		} 
		
		art.dialog({
			content:"是否确定进行刷新认证状态？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/organization/doRefreshIdentity",
					type:"post",
					data:{pk:ret.pk},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});
		
	}
	
	function goSealManage(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		} 
		Global.Dialog.showDialog("organizationSealManage",{
			title:"机构管理——印章管理",
			postData:{orgId: ret.orgid},
			url:"${ctx}/admin/orgSeal/goList",
			height:600,
			width:850,
	        resizable: true,
			returnFun:goto_query
		});	
	}

	function doDelete(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行删除操作",
			});
			return;
		} 
		art.dialog({
			content:"是否确定删除该记录？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/organization/doDelete?pk="+ret.pk,
					type:"post",
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});
	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system" onclick="goSave()">
					新增
				</button>
				<button type="button" class="btn btn-system" onclick="goUpdate()">
					编辑
				</button>
				<button type="button" class="btn btn-system" onclick="goView()">
					查看
				</button>
				<button type="button" class="btn btn-system" onclick="doDelete()">
					删除
				</button>
				<button type="button" class="btn btn-system" onclick="doIdentity()">
					刷新认证Url
				</button>
				<button type="button" class="btn btn-system" onclick="doRefreshIdentity()">
					刷新认证状态
				</button>
				<button type="button" class="btn btn-system" onclick="goSealManage()">
					印章管理
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					关闭
				</button>
			</div>
		</div>	
	</div>
	<div class="body-box-list">
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
