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
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true},
			{name:"filterformula", index:"filterformula", width:180, label:"筛选条件", sortable:true ,align:"left",},
			{name:"filterformulashow", index:"filterformulashow", width:180, label:"筛选条件显示", sortable:true ,align:"left",},
			{name:"filterremarks", index:"filterremarks", width:200, label:"筛选条件说明", sortable:true ,align:"left",},
			{name:"filterlevel", index:"filterlevel", width:80, label:"优先级", sortable:true ,align:"right",},
			{name:"formulashow", index:"formulashow", width:180, label:"计算公式", sortable:true ,align:"left",},
			{name:"formula", index:"formula", width:180, label:"条件公式", sortable:true ,align:"left",hidden:true},
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
		postData:{code:$.trim($("#code").val()),queryCondition:" > 0"}
	});
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

function defineAdd(){
	Global.Dialog.showDialog("DefineAdd",{
		title:"分类定义——新增",
		postData:{code:$("#code").val()},
		url:"${ctx}/admin/salaryItem/goCategoryDefineAdd",
		height:780,
		width:920,
        resizable: true,
		returnFun:goto_query
	});	
}

function defineUpdate(){
	var ret = selectDataTableRow();
	
	if(!ret){
		art.dialog({
			content:"请选择一条记录！"
		});
		return;
	}
	
	Global.Dialog.showDialog("DefineUpdate",{
		title:"分类定义——编辑",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/salaryItem/goCategoryDefineUpdate",
		height:780,
		width:920,
        resizable: true,
		returnFun:goto_query
	});	
}

function defineView(){
	var ret = selectDataTableRow();
	
	if(!ret){
		art.dialog({
			content:"请选择一条记录！"
		});
		return;
	}
	
	Global.Dialog.showDialog("DefineUpdate",{
		title:"分类定义——编辑",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/salaryItem/goCategoryDefineView",
		height:600,
		width:850,
        resizable: true,
		returnFun:goto_query
	});	
}

function defineDel(){
	var ret = selectDataTableRow();
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
					$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>薪酬项目代码</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${salaryItem.code }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>薪酬项目名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${salaryItem.descr }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>系统保留项目</label>
									<house:xtdm  id="isSysRetention" dictCode="YESNO" style="width:160px;" value="${salaryItem.isSysRetention}" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>支持手动修改</label>
									<house:xtdm  id="isAdjustable" dictCode="YESNO" style="width:160px;" value="${salaryItem.isAdjustable}" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>计算优先级</label>
									<house:dict id="itemLevel" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 1 and 13 " 
												sqlValueKey="code" sqlLableKey="descr" value="${salaryItem.itemLevel }" disabled="true"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬项目分组</label>
									<house:xtdm  id="itemGroup" dictCode="SALITEMGROUP" style="width:160px;" value="${salaryItem.itemGroup}" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="${salaryItem.status}" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label class="control-textarea">薪酬说明</label>
									<textarea id="remarks" name="remarks" rows="3" readonly="true">${salaryItem.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分类定义</a></li>
			      	<li class="">
			      		<a href="#tab_PersonDetail" data-toggle="tab">个人定义</a>
			      	</li>
			    </ul> 
			   	<div class="tab-content">  
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system">
								<div class="panel-body">
									<div class="btn-group-xs">
										<button style="align:left" type="button"
											class="btn btn-system viewFlag" onclick="defineAdd()">
											<span>新增 </span>
										</button>
										<button style="align:left" type="button"
											class="btn btn-system viewFlag" onclick="defineUpdate()">
											<span>编辑 </span>
										</button>
										<button style="align:left" type="button"
											class="btn btn-system " onclick="defineDel()">
											<span>删除 </span>
										</button>
										<button style="align:left" type="button"
											class="btn btn-system " onclick="defineView()">
											<span>查看 </span>
										</button>
									</div>
								</div>
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
								<table id="dataTablePager"></table>
							</div>
						</div>
					</div>
					<div id="tab_PersonDetail" class="tab-pane fade">
		         		<jsp:include page="salaryItem_personalDefine.jsp"></jsp:include>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
