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
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>客户售后管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width: 160px;"/>
					</li>
					<li>
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value=""/>
					</li>
					<li>
						<label>状态</label>
						<house:DictMulitSelect id="status" dictCode="" 
							sql=" select rtrim(cbm) fd1, rtrim(note) fd2 from txtdm where id='CUSTSRVSTATUS' " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>类型</label>
						<house:xtdm id="type" dictCode="CUSTSRVTYPE" style="width:160px;" value=""/>
					</li>
					<li>
						<label>报备日期从</label>
						<input type="text" id="repDateFrom" name="repDateFrom"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="repDateTo" name="repDateTo"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>处理日期从</label>
						<input type="text" id="dealDateFrom" name="dealDateFrom"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dealDateTo" name="dealDateTo"
							class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value=""/>
					</li>
					<li>
						<label for="serviceMan">售后跟踪人员</label>
						<input type="text" id="serviceMan" name="serviceMan" style="width:160px;">
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${custService.expired}" 
							onclick="hideExpired(this)" ${custService.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="CUSTSERVICE_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_CONFIRMAPPLY">
					<button type="button" class="btn btn-system" id="confirmApply">
						<span>确认申请</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_COMPLETE">
					<button type="button" class="btn btn-system" id="complete">
						<span>完成</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_VIEW">
					<button type="button" class="btn btn-system" id="viewBtn" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTSERVICE_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>输出到Excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	$("#custCode").openComponent_customer({
		condition:{
			status:"5",
			laborFeeCustStatus:"1,2,3"
		}
	});
	$("#serviceMan").openComponent_employee();

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/custService/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "no", index: "no", width: 50, label: "no", sortable: true, align: "left", hidden: true},
			{name: "typedescr", index: "typedescr", width: 50, label: "类型", sortable: true, align: "left"},
			{name: "address", index: "address", width: 130, label: "楼盘地址", sortable: true, align: "left"},
			{name: "consenddate", index: "consenddate", width: 80, label: "竣工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "custname", index: "custname", width: 70, label: "客户姓名", sortable: true, align: "left"},
			{name: "mobile1", index: "mobile1", width: 80, label: "联系方式", sortable: true, align: "left"},
			{name: "mobile2", index: "mobile2", width: 80, label: "联系方式2", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 420, label: "售后内容", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "phone", index: "phone", width: 95, label: "项目经理电话", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "repmandescr", index: "repmandescr", width: 70, label: "报备人", sortable: true, align: "left"},
			{name: "repdate", index: "repdate", width: 80, label: "报备日期", sortable: true, align: "left", formatter: formatDate},
			{name: "servicemandescr", index: "servicemandescr", width: 90, label: "售后跟踪人员", sortable: true, align: "left"},
			{name: "dealman", index: "dealman", width: 70, label: "处理人", sortable: true, align: "left"},
			{name: "dealdate", index: "dealdate", width: 80, label: "处理日期", sortable: true, align: "left", formatter: formatDate},
			{name: "status", index: "status", width: 50, label: "status", sortable: true, align: "left", hidden: true},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
			{name: "undertaker", index: "undertaker", width: 70, label: "承担方", sortable: true, align: "left", hidden:true},
			{name: "undertakerdescr", index: "undertakerdescr", width: 70, label: "承担方", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
		],
		ondblClickRow: function(){/* 双击触发事件 */
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "客户售后管理--新增",
			url : "${ctx}/admin/custService/goSave",
			width : 750,
			height : 510,
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
		if (1 != ret.status && 2 != ret.status && 3 != ret.status ) {
			art.dialog({
				content: "待处理、完成、暂不处理状态才可操作"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"客户售后管理--编辑",
			url:"${ctx}/admin/custService/goUpdate",
			postData:{no:ret.no,m_umState:"M"},
			width:750,
			height:648,
			returnFun:goto_query
		});
	});

	// 完成
	$("#complete").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if (1 != ret.status) {
			art.dialog({
				content: "状态为待处理才可操作"
			});
			return;
		}
		Global.Dialog.showDialog("complete",{
			title:"客户售后管理--完成",
			url:"${ctx}/admin/custService/goComplete",
			postData:{no:ret.no,m_umState:"C"},
			width:750,
			height:648,
			returnFun:goto_query
		});
	});

	$("#delete").on("click",function(){
		var url = "${ctx}/admin/custService/doDelete";
		beforeDel(url,"no","删除该客户售后的信息");
		returnFun: goto_query;
		return true;
	});

	/* 清空下拉选择树checked状态 */
	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	});
	
	$("#confirmApply").on("click", function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		if (0 != ret.status) {
			art.dialog({
				content: "状态为申请才可操作"
			});
			return;
		}
		
		art.dialog({
			content: "是否进行确认操作?",
			ok: function(){
		 	$.ajax({
				url: "${ctx}/admin/custService/doConfirmApply",
				type:"post",
		    	data: {
		    		no: ret.no
		    	},
				dataType:"json",
				cache:false,
				error:function(obj){			    		
					art.dialog({
						content:"访问出错,请重试",
						time:3000,
						beforeunload: function () {}
					});
				},
				success: function(ret){
					art.dialog({
						content: ret.msg,
						time:1000,
						timeCloseButtonEvent: "ok",
						ok: function(){
							if(ret.rs){
								goto_query();
							}
						}
					});
				}
			});	
			},
			cancel: function(){}
		});
		
	});
	
});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"客户售后管理——查看",
		url:"${ctx}/admin/custService/goView",
		postData:{no:ret.no,m_umState:"V"},
		width:750,
		height:648,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/custService/doExcel";
	doExcelAll(url);
}
</script>
</html>
