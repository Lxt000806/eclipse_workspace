<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>supplJob列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplJob/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			postData:{status:"0,1"},
			styleUI: 'Bootstrap',
			colModel : [
			  {name: 'pk', index: 'pk', width: 100, label: 'pk', sortable: true, align: "left",hidden:true},
			  {name: 'status', index: 'status', width: 100, label: '处理状态', sortable: true, align: "left",hidden:true},
			  {name: 'no', index: 'no', width: 100, label: '任务单号', sortable: true, align: "left"},
		      {name: 'address', index: 'address', width: 160, label: '楼盘', sortable: true, align: "left"},
		      {name: 'jobtypedescr', index: 'jobtypedescr', width: 70, label: '任务类型', sortable: true, align: "left"},
		      {name: 'projectmandescr', index: 'projectmandescr', width: 70, label: '项目经理', sortable: true, align: "left"},
		      {name: 'projectmanphone', index: 'projectmanphone', width: 115, label: '项目经理电话', sortable: true, align: "left"},
		      {name: 'plandate', index: 'plandate', width: 95, label: '计划处理时间', sortable: true, align: "left", formatter: formatDate},
		      {name: 'note', index: 'note', width: 60, label: '状态', sortable: true, align: "left"},
		      {name: 'date', index: 'date', width: 125, label: '指派时间', sortable: true, align: "left", formatter: formatTime},
		      {name: 'recvdate', index: 'recvdate', width: 125, label: '接收时间', sortable: true, align: "left", formatter: formatTime},
		      {name: 'completedate', index: 'completedate', width: 125, label: '完成时间', sortable: true, align: "left", formatter: formatTime},
		      {name: 'rwremarks', index: 'rwremarks', width: 163, label: '任务备注', sortable: true, align: "left"},
		      {name: 'remarks', index: 'remarks', width: 164, label: '指派备注', sortable: true, align: "left"},
		      {name: 'supplremarks', index: 'supplremarks', width: 164, label: '供应商备注', sortable: true, align: "left"},
		      {name: 'custcode', index: 'custcode', width: 100, label: '客户编号', sortable: false, align: "left", hidden:true},
		      {name: 'custname', index: 'custname', width: 100, label: '客户姓名', sortable: false, align: "left", hidden:true},
		      {name: 'custmobile1', index: 'custmobile1', width: 100, label: '客户电话', sortable: false, align: "left", hidden:true}
            ]
		});
		
		//接收
		$("#recvSupplJob").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.note.trim()!='未接收' && ret.note.trim()!='接收退回'){
					art.dialog({
	       			content: "请选择未接收或接收退回的记录",
	       		});
			}else{
			  Global.Dialog.showDialog("Update",{
				  title:"供应商任务处理——接收",
				  url:"${ctx}/admin/supplJob/goRecv",
				  postData:{no:ret.no,address:ret.address,jobTypeDescr:ret.jobtypedescr,note:ret.note,date:ret.date,
				  recvDate:ret.recvdate,completeDate:ret.completedate,planDate:ret.plandate,supplRemarks:ret.supplremarks,
				  status:ret.status,pk:ret.pk,rwRemarks:ret.rwremarks,remarks:ret.remarks, custCode: ret.custcode},
				  height:450,
				  width:700,
				  returnFun:goto_query
			 });
			}
		});
		//退回
		$("#returnSupplJob").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.note.trim()!='已接收'){
					art.dialog({
	       			content: "请选择已接收的记录",
	       		});
			}else{
			  Global.Dialog.showDialog("Update",{
				  title:"供应商任务处理——退回",
				  url:"${ctx}/admin/supplJob/goReturn",
				  postData:{no:ret.no,address:ret.address,jobTypeDescr:ret.jobtypedescr,note:ret.note,date:ret.date,
				  recvDate:ret.recvdate,completeDate:ret.completedate,planDate:ret.plandate,supplRemarks:ret.supplremarks,
				  status:ret.status,pk:ret.pk},
				  height:340,
				  width:700,
				  returnFun:goto_query
			 });
			}
		});
		//完成
		$("#completeSupplJob").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.note.trim()!='已接收'){
					art.dialog({
	       			content: "请选择已接收的记录",
	       		});
			}else{
			  Global.Dialog.showDialog("Update",{
				  title:"供应商任务处理——完成",
				  url:"${ctx}/admin/supplJob/goComplete",
				  postData:{no:ret.no,address:ret.address,jobTypeDescr:ret.jobtypedescr,note:ret.note,date:ret.date,
				  recvDate:ret.recvdate,completeDate:ret.completedate,planDate:ret.plandate,supplRemarks:ret.supplremarks,
				  status:ret.status,pk:ret.pk},
		          height: 700,
		          width: 1350,
				  returnFun:goto_query
			 });
			}
		});
		
		//查看
		$("#viewSupplJob").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
	
			  Global.Dialog.showDialog("Update",{
				  title:"供应商任务处理——查看",
				  url:"${ctx}/admin/supplJob/goView",
				  postData:{
				      no:ret.no,address:ret.address,jobTypeDescr:ret.jobtypedescr,note:ret.note,date:ret.date,
				      recvDate:ret.recvdate,completeDate:ret.completedate,planDate:ret.plandate,supplRemarks:ret.supplremarks,
				      status:ret.status,pk:ret.pk,rwRemarks:ret.rwremarks,remarks:ret.remarks, custCode: ret.custcode,
				      custName: ret.custname, custMobile1: ret.custmobile1
				  },
				  height:500,
				  width:700,
				  returnFun:goto_query
			 });
		});
		//橱柜出货
		$("#cupboardSend").on("click",function(){
			  Global.Dialog.showDialog("cupboardSend",{
				  title:"供应商任务处理——橱柜出货",
				  url:"${ctx}/admin/supplJob/goCupboardSend",
				  height:600,
				  width:800,
				  returnFun:goto_query
			 });
		});
		//编辑
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.note.trim()!='已接收'){
					art.dialog({
	       			content: "请选择已接收的记录",
	       		});
			}else{
			  Global.Dialog.showDialog("Update",{
				  title:"供应商任务处理——编辑",
				  url:"${ctx}/admin/supplJob/goUpdate",
				  postData:{no:ret.no,address:ret.address,jobTypeDescr:ret.jobtypedescr,note:ret.note,date:ret.date,
				  recvDate:ret.recvdate,completeDate:ret.completedate,planDate:ret.plandate,supplRemarks:ret.supplremarks,
				  status:ret.status,pk:ret.pk,rwRemarks:ret.rwremarks,remarks:ret.remarks},
				  height:430,
				  width:700,
				  returnFun:goto_query
			 });
			}
		});
	});	
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#splStatus").val('');
	$.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/supplJob/doExcel";
	doExcelAll(url);
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden"name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${itemApp.expired }" />
				<input type="hidden" id="module" name="module" value="SupplierItemApp" />
				<input type="hidden" id="czybh" name="czybh" value="${itemApp.czybh}" />
				<ul class="ul-form">
					<li>
					<label>指派时间</label>
					<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>至</label>
					<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>接收时间</label>
					<input type="text" id="recvDateFrom" name="recvDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.recvDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
					<label>至</label>
					<input type="text" id="recvDateTo" name="recvDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${supplJob.recvDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="SUPPLJOBSTS" selectedValue="0,1"></house:xtdmMulit>
					</li>
					<li>
					<label>楼盘</label>
					<input type="text" id="address" name="address" value="${supplJob.note }"/>
					</li>
					<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SUPPLJOB_RECEIVE">
					<button type="button" class="btn btn-system " id="recvSupplJob">接收</button>
				</house:authorize>
				<house:authorize authCode="SUPPLJOB_RETURN">
					<button type="button" class="btn btn-system " id="returnSupplJob">退回</button>
				</house:authorize>
				<house:authorize authCode="SUPPLJOB_COMPLETE">
					<button type="button" class="btn btn-system " id="completeSupplJob">完成</button>
				</house:authorize>
				<button type="button" class="btn btn-system " id="update">编辑</button>
				<house:authorize authCode="SUPPLJOB_VIEW">
					<button type="button" class="btn btn-system " id="viewSupplJob">查看</button>
				</house:authorize>
				<house:authorize authCode="SUPPLJOB_CUPBOARDSEND">
					<button type="button" class="btn btn-system " id="cupboardSend">橱柜出货</button>
				</house:authorize>
				<house:authorize authCode="SUPPLJOB_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>	
				</house:authorize>
				
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table> 
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


