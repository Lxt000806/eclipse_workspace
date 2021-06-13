<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>流程数据分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
	<script src="${resourceRoot}/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${resourceRoot}/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${resourceRoot}/js/workflow.js" type="text/javascript"></script>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
		.SelectBlack{
			background-color:white!important;
			color:black!important;
		}
	</style>
<script type="text/javascript">
function doExcel(){
	var url = "${ctx}/admin/wfProcInst/doAllWfProcExcel";
	doExcelAll(url);
}
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getAllListByJqgrid",
		postData:{status:"1"},
		height:$(document).height()-$("#content-list").offset().top-80,
		colModel : [
			{name: "no", index: "no", width:240, label: "单据标题", sortable: true, align: "center"
				,formatter:function(cellvalue, options, rowObject){
	        		if(rowObject==null || !rowObject.no){
	          			return '';
	          		}
	        		return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"view('"
	        			+rowObject.actprocinstid+"','"+rowObject.title+"')\">"+rowObject.title+"</a>";
		    	}
		    },
		  	{name: "title", index: "title", width: 200, label: "单据标题", sortable: true, align: "left",hidden:true}, 
			{name: "summary", index: "summary", width:400, label: "单据摘要", sortable: true, align: "left", formatter:function(cellvalue, options, rowObject){return cellvalue.replace(/\<br\/\>/g, ";").replace(/\<none\/\>/g, "");}}, 
			{name: "starttime", index: "starttime", width: 126, label: "发起时间", sortable: true, align: "left", formatter: formatTime},
			{name: "endtime", index: "endtime", width: 126, label: "完成时间", sortable: true, align: "left", formatter: formatTime}, 
			{name: "statusdescr", index: "statusdescr", width:120, label: "状态", sortable: true, align: "left"},
			{name: "taskname", index: "taskname", width:120, label: "任务名称", sortable: true, align: "left",hidden: true},
			{name: "id", index: "id", width:120, label: "流程实例ID", sortable: true, align: "left", hidden: true},
			{name: "taskid", index: "taskid", width:120, label: "任务ID", sortable: true, align: "left",hidden:true },
			{name: "actprocinstid", index: "actprocinstid", width:120, label: "actprocinstid", sortable: true, align: "left",hidden:true },
			{name: "operdescr", index: "operdescr", width:120, label: "执行人", sortable: true, align: "left", },
	    ],
		/*onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBlack");
		},*/
	});
});

function view(actprocinstid,title) {
    Global.Dialog.showDialog("view", {
		title: title,
		url: "${ctx }/admin/wfProcInst/goView?taskId=-1" + "&processInstanceId=" + actprocinstid + "&type=-1" +"&m_umState=V",
		height: 750,
		width: 1320,
		// returnFun: goto_query
    });
}

function goTransfer(){
	var ret = selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条流程进行操作",
		});
		return;
	}else{
		if($.trim(ret.endtime) != ""){
			art.dialog({
				content:"该审批单已完成,无法进行转交操作",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"流程转交",
			url:"${ctx}/admin/wfProcInst/goTransfer",
			postData:{
				processInstanceId:ret.actprocinstid,
				taskId:ret.taskid,
				title:ret.title,
				operDescr:ret.operdescr
			},
			height: 240,
			width:780,
			returnFun: goto_query 
		});
	}
}

function expenseAdvance(){
	Global.Dialog.showDialog("update",{
		title:"借款余额",
		url:"${ctx}/admin/wfProcInst/goExpenseAdvance",
		postData:{
		},
		height: 740,
		width:1080,
		returnFun: goto_query
	});
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	
	$("#wfProcNo_NAME").val('');
	$("#wfProcNo").val('');
	$("#dateFrom").val('');
	$("#dateTo").val('');
	$("#endDateFrom").val('');
	$("#endDateTo").val('');
	$.fn.zTree.getZTreeObj("tree_wfProcNo").checkAllNodes(false);
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
						<label>摘要</label>
						<input type="text" id="summary" name="summary" style="width:160px;"/>
					</li>
					<li>
						<label>单据类型</label>
						<house:DictMulitSelect id="wfProcNo" dictCode="" 
						sql="select a.no code, a.descr descr from tWfProcess a 
								where exists (select 1 from tWfGroupAuthority ina 
										left join ACT_ID_MEMBERSHIP inb on inb.GROUP_ID_ = ina.GroupId
										where inb.USER_ID_ = '${czybh }' and a.No = ina.WfProcNo
									) " 
						sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
					</li>
					<li>
						<label>发起时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
					</li>
					<li>
						<label>完成时间从</label>
						<input type="text" id="endTimeFrom" name="endTimeFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="endTimeTo" name="endTimeTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
					</li>
					<li>
						<label>状态</label>
						<house:DictMulitSelect id="status" dictCode="" 
						sql="select cbm Code,note Descr from tXTDM where id='WFPROCINSTSTAT'" 
						sqlValueKey="Code" sqlLableKey="Descr" selectedValue="1" ></house:DictMulitSelect>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WFPROCINST_ALLLIST_TRANSFER">
					<button type="button" class="btn btn-system" onclick="goTransfer()">转交</button>
				</house:authorize>
				<house:authorize authCode="WFPROCINST_ALLLIST_ADVANCE">
					<button type="button" class="btn btn-system" onclick="expenseAdvance()">借款余额查询</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


