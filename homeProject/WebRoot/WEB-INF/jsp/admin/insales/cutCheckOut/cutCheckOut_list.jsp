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
<title>瓷砖加工管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	Global.Dialog.showDialog("save",{
		title:"瓷砖加工管理--新增",
	  	url:"${ctx}/admin/cutCheckOut/goSave",
	  	height:700,
		width:1350,
	 	returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
  	if (ret) {	
  		if(ret.status=='4' ){
  			art.dialog({
				content: "取消状态不可编辑"
			});
			return;
  		}
		Global.Dialog.showDialog("save",{
			title:"瓷砖加工管理--编辑",
		  	url:"${ctx}/admin/cutCheckOut/goUpdate",
		  	height:700,
			width:1350,
			postData:{id:ret.no},
		 	returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
  	if (ret) {	
		Global.Dialog.showDialog("save",{
			title:"瓷砖加工管理--查看",
		  	url:"${ctx}/admin/cutCheckOut/goView",
		  	height:700,
			width:1350,
			postData:{id:ret.no},
		 	returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function cutCheckIn(){
	var ret = selectDataTableRow();
  	if (ret) {	
  		if(ret.status!='1' && ret.status!='2'){
  			art.dialog({
				content: "只有状态为发出加工和部分加工入库的可操作"
			});
			return;
  		}	
		Global.Dialog.showDialog("checkIn",{
			title:"瓷砖加工管理--加工入库",
		  	url:"${ctx}/admin/cutCheckOut/goCheckIn",
		  	height:700,
			width:1200,
			postData:{id:ret.no},
		 	returnFun: goto_query
		});
	}else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function checkOut(){
	var ret = selectDataTableRow();
  	if (ret) {	
  		if(ret.status!='0'){
  			art.dialog({
				content: "只有状态为申请的可操作"
			});
			return;
  		}	
  		art.dialog({
       		content: '是否确认进行加工出库操作？',
	        lock: true,
	        ok: function () {
	       		$.ajax({
					url:'${ctx}/admin/cutCheckOut/doCheckOut',
					type: 'post',
					data: {no:ret.no},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
				    				goto_query();
							    }
							});
				    	}else{
				    		$("#_form_token_uniq_id").val(obj.token.token);
				    		art.dialog({
								content: obj.msg,
								width: 200
							});
				    	}
				    }
				 });
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function checkOutReturn(){
	var ret = selectDataTableRow();
  	if (ret) {
  		if(ret.status!='1'){
  			art.dialog({
				content: "只有状态为加工出库的可操作"
			});
			return;
  		}	
  		art.dialog({
       		content: '是否确认进行出库退回操作？',
	        lock: true,
	        ok: function () {
	       		$.ajax({
					url:'${ctx}/admin/cutCheckOut/doCheckOutReturn',
					type: 'post',
					data: {no:ret.no},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
				    				goto_query();
							    }
							});
				    	}else{
				    		$("#_form_token_uniq_id").val(obj.token.token);
				    		art.dialog({
								content: obj.msg,
								width: 200
							});
				    	}
				    }
				 });
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function cancel(){
	var ret = selectDataTableRow();
  	if (ret) {
  		if(ret.status!='0'){
  			art.dialog({
				content: "只有状态为申请的可操作"
			});
			return;
  		}	
  		art.dialog({
       		content: '是否确认进行取消操作？',
	        lock: true,
	        ok: function () {
	       		$.ajax({
					url:'${ctx}/admin/cutCheckOut/doCancel',
					type: 'post',
					data: {no:ret.no},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
				    				goto_query();
							    }
							});
				    	}else{
				    		$("#_form_token_uniq_id").val(obj.token.token);
				    		art.dialog({
								content: obj.msg,
								width: 200
							});
				    	}
				    }
				 });
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/cutCheckOut/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-80,
		postData:{status:"0,1,2"},
		colModel : [
		    {name: "no", index: "no", width: 85, label: "加工批次号", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 90, label: "创建日期", sortable: true, align: "left", formatter: formatDate},
			{name: "crtczydescr", index: "crtczydescr", width: 70, label: "创建人", sortable: true, align: "left"},
			{name: "submitdate", index: "submitdate", width: 120, label: "出库时间", sortable: true, align: "left", formatter: formatTime},
		    {name: "status", index: "status", width: 90, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 90, label: "状态", sortable: true, align: "left"},
			{name: "completedate", index: "completedate", width: 120, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
          ],
	});
	$("#custCode").openComponent_customer();
});

function print(){
	var ret = selectDataTableRow();
   	var reportName = "cutCheckOut.jasper";
   	Global.Print.showPrint(reportName, {
   		No:ret.no,
   		Date:formatDate(new Date),
		LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
		SUBREPORT_DIR: "<%=jasperPath%>" 
	});
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/cutCheckOut/doExcel";
	doExcelAll(url);
}

function clearForm(){
 	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val("");
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
} 

function detailQuery(){
	Global.Dialog.showDialog("detailQuery",{
		title:"瓷砖加工管理--明细查询",
	  	url:"${ctx}/admin/cutCheckOut/goDetailQuery",
	  	height:700,
		width:1200,
	});
}
</script>
</head>

<body>
	<div class="query-form">
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" id="expired" name="expired" value="" /> 
			<input type="hidden" name="jsonString" value="" />
			<ul class="ul-form">
				<li>
					<label>加工批次号</label> 
					<input type="text" id="no" name="no"  />
				</li>
				<li>
					<label>状态</label>
					<house:xtdmMulit id="status" dictCode="CUTCHECKOUTSTAT" selectedValue="0,1,2"></house:xtdmMulit>
				</li>
				<li>
					<label>创建日期从</label> 
					<input type="text" id="crtDateFrom" name="crtDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>至</label> 
					<input type="text" id="crtDateTo" name="crtDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>
					<label>完成日期从</label> 
					<input type="text" id="completeDateFrom" name="completeDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>至</label> 
					<input type="text" id="completeDateTo" name="completeDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>
					<label>楼盘</label> 
					<input type="text" id="address" name="address"  />
				</li>
				<li>
					<label>领料单号</label> 
					<input type="text" id="iano" name="iano"  />
				</li>
				<li class="search-group-shrink">
					<button type="button" class="btn  btn-system btn-sm"
						onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-system btn-sm"
						onclick="clearForm();">清空</button>
				</li>
			</ul>
		</form>
	</div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="CUTCHECKOUT_SAVE">
				<button type="button" class="btn btn-system " onclick="save()">新增</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_UPDATE">
				<button type="button" class="btn btn-system " onclick="update()">编辑</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_VIEW">
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_CHECKOUT">
				<button type="button" class="btn btn-system " onclick="checkOut()">加工出库</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_CHECKOUTRETURN">
				<button type="button" class="btn btn-system " onclick="checkOutReturn()">出库退回</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_CUTCHECKIN">
				<button type="button" class="btn btn-system " onclick="cutCheckIn()">加工入库</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_CANCEL">
				<button type="button" class="btn btn-system " onclick="cancel()">取消</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_PRINT">
				<button type="button" class="btn btn-system " onclick="print()">打印</button>
			</house:authorize>
			<house:authorize authCode="CUTCHECKOUT_EXCEL">
				<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
			</house:authorize>
			<button type="button" class="btn btn-system " onclick="detailQuery()">明细查询</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>


