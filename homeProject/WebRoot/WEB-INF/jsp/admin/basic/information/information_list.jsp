<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>公告消息管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<!-- 客户窗体组件导入 -->
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		// 单击客户查询按钮事件
		$("#SendCZY").openComponent_employee();
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/information/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "number", index: "number", width: 80, label: "消息编号", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "department3", index: "department3", width: 90, label: "三级部门", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 90, label: "发布人", sortable: true, align: "left"},
				{name: "senddate", index: "senddate", width: 150, label: "申请日期", sortable: true, align: "left",formatter:formatTime},
				{name: "confirmdate", index: "confirmdate", width: 150, label: "发布日期", sortable: true, align: "left",formatter:formatTime},
				{name: "status", index: "status", width: 90, label: "消息状态", sortable: true, align: "left",hidden:true},
				{name: "statusdesc", index: "statusdesc", width: 90, label: "消息状态", sortable: true, align: "left"},
				{name: "infocatadesc", index: "infocatadesc", width: 150, label: "消息类别", sortable: true, align: "left",},
				{name: "infotype", index: "infotype", width: 90, label: "消息类型", sortable: true, align: "left",hidden:true},
				{name: "infotypedesc", index: "infotypedesc", width: 90, label: "消息类型", sortable: true, align: "left"},
				{name: "infotitle", index: "infotitle", width: 150, label: "主题", sortable: true, align: "left"},
				{name: "infotext", index: "infotext", width: 150, label: "消息内容", sortable: true, align: "left" }
			],
			ondblClickRow:function(){
				view();
			}
		});

	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#rcvType").val("");
		$("#rcvStatus").val("");
		$("#infoType").val("");
		$("#infoCata").val("");
		$.fn.zTree.getZTreeObj("tree_infoCata").checkAllNodes(false);
	} 
	function doExecl(){
		var url = "${ctx}/admin/personMessage/doExcel";
		doExcelAll(url);
	}
	function doDelete(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
		Global.Dialog.showDialog("Delete",{
			title:"个人消息管理——删除",
			url:"${ctx}/admin/information/goView",
			postData:{
				number:selectedRow.number,
				m_umState:"D"
			},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}
	function view(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
			
		Global.Dialog.showDialog("View",{
			title:"个人消息管理——查看",
			url:"${ctx}/admin/information/goView",
			postData:{
				number:selectedRow.number,
				m_umState:"V"
			},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}
	function commit(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
		if(selectedRow.status=='1'){	
			Global.Dialog.showDialog("Commit",{
				title:"个人消息管理——提交",
				url:"${ctx}/admin/information/goView",
				postData:{
					number:selectedRow.number,
					m_umState:"S"
				},
				height:800,
				width:1300,
				returnFun:goto_query
			});
		}else{
			art.dialog({content: "只有消息状态为草稿的记录才能进行提交！",width: 200});
    		return false;
		}
	}
	function confirm(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
		if(selectedRow.infotype=='1'&&selectedRow.status=='2'){
			Global.Dialog.showDialog("Confirm",{
				title:"个人消息管理——审核",
				url:"${ctx}/admin/information/goView",
				postData:{
					number:selectedRow.number,
					m_umState:"P"
				},
				height:800,
				width:1300,
				returnFun:goto_query
			});
		}else{
			art.dialog({content: "只有消息类型为公告消息，并且消息状态为申请的记录才能进行审核！",width: 200});
    		return false;
		}
		
	}
	function goInfoRead(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
    	console.log(selectedRow)
    	if(selectedRow.infotype!="2"){
    		art.dialog({content: "只有消息类型为个人消息的记录才能查看接收明细！",width: 200});
    		return false;
    	}
			
		Global.Dialog.showDialog("InfoRead",{
			title:"公告消息——接收明细",
			url:"${ctx}/admin/information/goInfoRead",
			postData:{number:selectedRow.number},
			height:580,
			width:800,
			returnFun:goto_query
		});
	}
	
	
	function add(){
		Global.Dialog.showDialog("Add",{
			title:"公告消息管理——新增",
			url:"${ctx}/admin/information/goSave",
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}
	
	function update(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
    	if(selectedRow.status!="1"){
    		art.dialog({content: "只有消息状态为草稿的记录才能进行编辑！",width: 200});
    		return false;
    	}
    	Global.Dialog.showDialog("Update",{
			title:"公告消息——编辑",
			url:"${ctx}/admin/information/goSave",
			postData:{number:selectedRow.number,m_umState:"M"},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}
	
	function copy(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
    	Global.Dialog.showDialog("Copy",{
			title:"公告消息——编辑",
			url:"${ctx}/admin/information/goSave",
			postData:{number:selectedRow.number,m_umState:"C"},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}
	
	function moreClickCmpNotice(){
		Global.Dialog.showDialog("customerView",{
			title:"主页查询",
			url:"${ctx}/frame/goQuery",
			height:650,
			width:1000,
		});
	}	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>发布日期从</label>
							<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${information.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="sendDateTo" name="sendDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${information.sendDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>发布人</label>
							<input type="text" id="SendCZY" name="SendCZY" style="width:160px;" value="${information.SendCzy }"/>
                    	</li>
						<li>
							<label>主题</label>
							<input type="text" id="infoTitle" name="infoTitle" style="width:160px;" value="${information.infoTitle }"/>
						</li>
						<li>
							<label>消息内容</label>
							<input type="text" id="infoText" name="infoText" style="width:160px " value="${information.infoText}" />
						</li>
						<li>
							<label>消息类别</label>
							<house:xtdmMulit id="infoCata" dictCode="INFOCATA" ></house:xtdmMulit>
						</li>
						<li>
							<label>消息类型</label>
							<house:xtdm id="infoType" dictCode="INFOTYPE" onchange="changeType()"></house:xtdm>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
		</div>
		<div class="clear_float"></div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="INFORMATION_ADD">
							<button type="button" class="btn btn-system" onclick="add()">新增</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_COPY">
							<button type="button" class="btn btn-system" onclick="copy()">复制</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_UPDATE">
							<button type="button" class="btn btn-system" onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_VIEW">
							<button type="button" class="btn btn-system" onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_COMMIT">
							<button type="button" class="btn btn-system" onclick="commit()">提交</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_CONFIRM">
							<button type="button" class="btn btn-system" onclick="confirm()">审核</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_DELETE">
							<button type="button" class="btn btn-system" onclick="doDelete()">删除</button>
					</house:authorize>
					<house:authorize authCode="INFORMATION_VIEW">
							<button type="button" class="btn btn-system" onclick="goInfoRead()">接收明细</button>
					</house:authorize>
					<!-- <button type="button" class="btn btn-system" onclick="moreClickCmpNotice()">测试</button> -->
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>	
</html>
