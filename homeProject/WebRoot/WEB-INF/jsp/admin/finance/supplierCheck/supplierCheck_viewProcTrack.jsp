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
	<title>采购预支流程审批情况</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " onclick="viewProc()">
							<span>查看</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			<div id="content-list">
				<table id="dataTable_detail"></table>
			</div>
		</div>
	</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_detail",{
		height:250,
		rowNum:10000000,
		colModel : [
		  {name : 'wfprocdescr',index : 'wfprocdescr',width : 150,label:'任务名称',sortable : true,align : "left"},
		  {name : 'namechidescr',index : 'namechidescr',width : 120,label:'操作人员',sortable : true,align : "left"},
		  {name : 'statusdescr',index : 'statusdescr',width : 90,label:'处理动作',sortable : true,align : "left"},
		  {name : 'toactivitydescr',index : 'toactivitydescr',width : 120,label:'发送到',sortable : true,align : "left"},
		  {name : 'remarks',index : 'remarks',width : 300,label:'说明',sortable : true,align : "left"},
		  {name : 'lastupdate',index : 'lastupdate',width : 130,label:'时间',sortable : true,align : "left",formatter:formatTime},
		  {name : 'fromactivityid',index : 'fromactivityid',width : 300,label:'fromactivityid',sortable : true,align : "left",hidden:true},
		  {name : 'taskdefkey',index : 'taskdefkey',width : 300,label:'taskdefkey',sortable : true,align : "left",hidden:true},
	    ],
	});
	//初始化表格  
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierCheck/goProcListJqGrid",
		postData:{no:"${splCheckOut.no }"},
		height:250,
		rowNum:10000000,
		colModel : [
			{name : 'wfprocinstno',index : 'wfprocinstno',width : 150,label:'wfprocinstno',sortable : true,align : "left", hidden: true},
		  	{name : 'empcode',index : 'empcode',width : 120,label:'申请人编号',sortable : true,align : "left", hidden: true},
		  	{name : 'empname',index : 'empname',width : 90,label:'申请人',sortable : true,align : "left"},
		  	{name : 'deptdescr',index : 'deptdescr',width : 200,label:'部门',sortable : true,align : "left",},
		  	{name : 'refno',index : 'refno',width : 120,label:'业务单号',sortable : true,align : "left", hidden: true},
		  	{name : 'applydate',index : 'applydate',width : 80,label:'申请日期',sortable : true,align : "left", formatter: formatTime},
		  	{name : 'itemtype1',index : 'itemtype1',width : 130,label:'材料类型1',sortable : true,align : "left",hidden: true},
		  	{name : 'type',index : 'type',width : 130,label:'预支类型',sortable : true,align : "left",hidden: true},
		  	{name : 'claimamount',index : 'claimamount',width : 70,label:'金额',sortable : true,align : "left",},
		  	{name : 'claimremarks',index : 'claimremarks',width : 200,label:'开支内容',sortable : true,align : "left",},
		  	{name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left",},
		  	{name : 'actprocdefid',index : 'actprocdefid',width : 200,label:'ActProcDefId',sortable : true,align : "left",hidden: true},
		],
		onSelectRow : function(id) {
        	var row = selectDataTableRow("dataTable");
        	var elMaps = {AdvanceAmount: $.trim(row.advanceamount),
        				  ItemType1:  $.trim(row.itemtype1),
        				  Type: $.trim(row.type)
        				};
        	
           	$("#dataTable_detail").jqGrid('setGridParam',{url : "${ctx}/admin/laborFee/goProcTrackJqGrid?wfProcInstNo="+row.wfprocinstno+"&actProcDefId="+row.actprocdefid+"&el="+encodeURIComponent(JSON.stringify(elMaps))});
           	$("#dataTable_detail").jqGrid().trigger('reloadGrid');
        }
	});
});  

function viewProc(){
	var row = selectDataTableRow("dataTable");
	Global.Dialog.showDialog("viewProc",{
		title:"采购报销单--查看",
		url:"${ctx}/admin/wfProcInst/goViewByWfProcInstNo",
		postData:{
			wfProcInstNo: row.wfprocinstno,
		},
		height:727,
		width:1280,
		returnFun: goto_query
	});
}
</script>
