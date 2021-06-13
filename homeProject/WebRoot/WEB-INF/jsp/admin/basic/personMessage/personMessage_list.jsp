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
		$("#rcvCzy").openComponent_employee();
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/personMessage/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "消息编号", sortable: true, align: "left"},
				{name: "msgtype", index: "msgtype", width: 90, label: "消息类型", sortable: true, align: "left",hidden:true},
				{name: "bmsgtype", index: "msgtype", width: 90, label: "消息类型", sortable: true, align: "left"},
				{name: "msgtext", index: "msgtext", width: 150, label: "消息内容", sortable: true, align: "left"},
				{name: "msgrelcustcode", index: "msgrelcustcode", width: 120, label: "消息相关客户号", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 150, label: "生成日期", sortable: true, align: "left",formatter:formatTime},
				{name: "senddate", index: "senddate", width: 150, label: "发送日期", sortable: true, align: "left",formatter:formatTime},
				{name: "rcvtype", index: "rcvtype", width: 150, label: "接收人类型", sortable: true, align: "left",hidden:true },
				{name: "brcvtype", index: "rcvtype", width: 150, label: "接收人类型", sortable: true, align: "left",},
				{name: "rcvczy", index: "rcvczy", width: 90, label: "接收人", sortable: true, align: "left",hidden:true},
				{name: "brcvczy", index: "rcvczy", width: 90, label: "接收人", sortable: true, align: "left"},
				{name: "rcvdate", index: "rcvdate", width: 150, label: "接收日期", sortable: true, align: "left",formatter: formatTime },
				{name: "rcvstatus", index: "rcvstatus", width: 80, label: "接收状态", sortable: true, align: "left",hidden:true},
				{name: "brcvstatus", index: "brcvstatus", width: 80, label: "接收状态", sortable: true, align: "left" },
				{name: "ispush", index: "ispush", width: 80, label: "是否推送", sortable: true, align: "left",hidden:true },
				{name: "bispush", index: "ispush", width: 80, label: "是否推送", sortable: true, align: "left" },
				{name: "pushstatus", index: "pushstatus", width: 80, label: "推送标志", sortable: true, align: "left",hidden:true },
				{name: "bpushstatus", index: "pushstatus", width: 80, label: "推送标志", sortable: true, align: "left" },
				{name: "progmsgtype", index: "progmsgtype", width: 80, label: "施工消息类型", sortable: true, align: "left",hidden:true },
				{name: "progmsgtypedescr", index: "progmsgtype", width: 80, label: "施工消息类型", sortable: true, align: "left" },
				{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left",hidden:true },
				{name: "itemtype1descr", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left" },
				{name: "itemtype2", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left",hidden:true },
				{name: "itemtype2descr", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left" },
				{name: "worktype12", index: "worktype12", width: 80, label: "工种分类12", sortable: true, align: "left",hidden:true },
				{name: "worktype12descr", index: "worktype12", width: 80, label: "工种分类12", sortable: true, align: "left" },
				{name: "prjitem", index: "prjitem", width: 80, label: "施工项目", sortable: true, align: "left",hidden:true },
				{name: "prjitemdescr", index: "prjitemdescr", width: 80, label: "施工项目", sortable: true, align: "left" },
				{name: "jobtype", index: "jobtype", width: 80, label: "任务类型", sortable: true, align: "left",hidden:true },
				{name: "jobtypedescr", index: "jobtypedescr", width: 80, label: "任务类型", sortable: true, align: "left" },
				{name: "plandealdate", index: "plandealdate", width: 80, label: "计划处理时间", sortable: true, align: "left",formatter: formatTime },
				{name: "planarrdate", index: "planarrdate", width: 80, label: "计划进场时间", sortable: true, align: "left",formatter: formatTime },
				{name: "remarks", index: "remarks", width: 80, label: "处理说明", sortable: true, align: "left",},
				{name: "title", index: "title", width: 80, label: "标题", sortable: true, align: "left" },
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
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	function doExecl(){
		var url = "${ctx}/admin/personMessage/doExcel";
		doExcelAll(url);
	}
	function doDelete(){
		var url = "${ctx}/admin/personMessage/doDelete";
		beforeDel(url,"pk");
		returnFun: goto_query;	
	}
	function view(){
		var selectedRow = selectDataTableRow('dataTable');
		if(!selectedRow){
    		art.dialog({content: "请选择一条记录！",width: 200});
    		return false;
    	}
			
		Global.Dialog.showDialog("View",{
			title:"个人消息管理——查看",
			url:"${ctx}/admin/personMessage/goView",
			postData:{
				pk:selectedRow.pk
			},
			height:580,
			width:800,
			returnFun:goto_query
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
							<house:xtdm id="rcvType" dictCode="PERSRCVTYPE" value="${personMessage.rcvType}"></house:xtdm>
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
					<house:authorize authCode="PERSONMESSAGE_VIEW">
							<button type="button" class="btn btn-system" onclick="view()">查看</button>
					</house:authorize>
					<button type="button" class="btn btn-system" onclick="doDelete()">删除</button>
					<button type="button" class="btn btn-system" onclick="doExecl()">导出excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>	
</html>
