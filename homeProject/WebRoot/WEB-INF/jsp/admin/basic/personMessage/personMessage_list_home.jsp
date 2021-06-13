<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>个人消息管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<!-- 客户窗体组件导入 -->
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		// 单击客户查询按钮事件
		$("#custCode").openComponent_customer();
		$("#rcvCzy").openComponent_employee({
			showValue: "${personMessage.rcvCzy}",
			showLabel: "${personMessage.rcvCzyDescr}",
			readonly: true
		});
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/personMessage/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "消息编号", sortable: true, align: "left", hidden:true},
				{name: "msgtype", index: "msgtype", width: 90, label: "消息类型", sortable: true, align: "left",hidden:true},
				{name: "bmsgtype", index: "msgtype", width: 90, label: "消息类型", sortable: true, align: "left"},
				{name: "msgtext", index: "msgtext", width: 350, label: "消息内容", sortable: true, align: "left"},
				{name: "senddate", index: "senddate", width: 150, label: "发送日期", sortable: true, align: "left",formatter:formatTime},
				{name: "rcvstatus", index: "rcvstatus", width: 80, label: "接收状态", sortable: true, align: "left",hidden:true},
				{name: "brcvstatus", index: "brcvstatus", width: 80, label: "接收状态", sortable: true, align: "left" },
				{name: "progmsgtype", index: "progmsgtype", width: 80, label: "施工消息类型", sortable: true, align: "left",hidden:true },
				{name: "progmsgtypedescr", index: "progmsgtype", width: 80, label: "施工消息类型", sortable: true, align: "left" },
				{name: "title", index: "title", width: 80, label: "标题", sortable: true, align: "left" },
			],
			ondblClickRow:function(){
				view();
			}
		});

	});
	function clearForm(){
		$("#page_form input[type='text']:not(input[id*=rcvCzy])").val('');
		$("#rcvStatus").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	function goViewFromHome() {
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
		Global.Dialog.showDialog("personMessage_detail",{
			title:"查看消息",
			url:"${ctx}/admin/personMessage/goViewFromHome",
			postData: {
				pk:selectedRow.pk
			},
			height:350,
			width:800,
			returnFun: goto_query
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
							<label>生成日期</label>
							<input type="text" id="crtDateFrom" name="crtDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${personMessage.crtDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="crtDateTo" name="crtDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${personMessage.crtDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>接收人类型</label>
							<house:xtdm id="rcvType" dictCode="PERSRCVTYPE" value="${personMessage.rcvType}" disabled="true"></house:xtdm>
                    	</li>
						<li>
							<label>消息相关客户号</label>
							<input type="text" id="msgRelCustCode" name="msgRelCustCode" style="width:160px;" value="${personMessage.msgRelCustCode }"/>
						</li>
						<li>
							<label>发送日期</label>
							<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${personMessage.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="sendDateTo" name="sendDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${personMessage.sendDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>接收人</label>
							<input type="text" id="rcvCzy" name="rcvCzy" style="width:160px;" />
						</li>
						<li>
							<label>接收状态</label>
							<house:xtdm id="rcvStatus" dictCode="PERSRCVSTATUS" value="${personMessage.rcvStatus}"></house:xtdm>                                             
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
					<button type="button" class="btn btn-system" onclick="goViewFromHome()">查看</button>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>	
</html>
