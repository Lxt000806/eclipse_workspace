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
	<title>薪酬项目新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true},
			{name:"filterformula", index:"filterformula", width:80, label:"考核人员编号", sortable:true ,align:"left",},
			{name:"namechi", index:"namechi", width:80, label:"考核人员", sortable:true ,align:"left",},
			{name:"filterremarks", index:"filterremarks", width:200, label:"筛选条件说明", sortable:true ,align:"left",},
			{name:"filterlevel", index:"filterlevel", width:60, label:"优先级", sortable:true ,align:"right",},
			{name:"formulashow", index:"formulashow", width:180, label:"条件显示", sortable:true ,align:"left",},
			{name:"formula", index:"formula", width:180, label:"条件公式", sortable:true ,align:"left",},
			{name:"beginmon", index:"beginmon", width:80, label:"开始月份", sortable:true ,align:"left",},
			{name:"endmon", index:"endmon", width:80, label:"截止月份", sortable:true ,align:"left",},
			{name:"calcmodedescr", index:"calcmodedescr", width:80, label:"计算方式", sortable:true ,align:"left",},
			{name:"remarks", index:"remarks", width:200, label:"详情", sortable:true ,align:"left",},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最近修改人", sortable:true ,align:"left",},
			{name:"lastupdate", index:"lastupdate", width:80, label:"最后修改时间", sortable:true ,align:"left",formatter:formatTime},
		],
	}; 
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryItem/goCategoryDefindJqGrid",
		postData:{code:$.trim($("#code").val()),queryCondition:" = 0"}
	});
	Global.JqGrid.initJqGrid("dataTablePerson",gridOption);
});

function defineAddPerson(){
	Global.Dialog.showDialog("DefineAdd",{
		title:"个人定义——新增",
		postData:{code:$("#code").val()},
		url:"${ctx}/admin/salaryItem/goPersonalDefineAdd",
		height:690,
		width:920,
        resizable: true,
		returnFun:query
	});	
}

function defineUpdatePerson(){
	var ret = selectDataTableRow("dataTablePerson");
	
	if(!ret){
		art.dialog({
			content:"请选择一条记录！"
		});
		return;
	}
	
	Global.Dialog.showDialog("DefineUpdate",{
		title:"个人定义——编辑",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/salaryItem/goPersonalDefineUpdate",
		height:690,
		width:920,
        resizable: true,
		returnFun:query
	});	
}
		 
function defineViewPerson(){
	var ret = selectDataTableRow("dataTablePerson");
	
	if(!ret){
		art.dialog({
			content:"请选择一条记录！"
		});
		return;
	}
	
	Global.Dialog.showDialog("DefineView",{
		title:"个人定义——查看",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/salaryItem/goPersonalDefineView",
		height:500,
		width:850,
        resizable: true,
		returnFun:query
	});	
}

function query(){
	$("#dataTablePerson").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
}

function defineDelPerson(){
	var ret = selectDataTableRow("dataTablePerson");
	if(!ret){
		art.dialog({
			content:"请选择需要删除的数据",
		});
		return;
	}
	art.dialog({
		content:"是否确定删除该定义？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/salaryItem/doDelDefine",
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
					query();
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
	<body>
		<div class="body-box-form">
			<div class="container-fluid" >  
				<div id="content-list">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="defineAddPerson()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="defineUpdatePerson()">
										<span>编辑 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system " onclick="defineDelPerson()">
										<span>删除 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system " onclick="defineViewPerson()">
										<span>查看 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTablePerson"></table>
							<table id="dataTablePersonPager"></table>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
