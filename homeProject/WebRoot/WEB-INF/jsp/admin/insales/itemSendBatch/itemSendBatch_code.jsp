<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ItemSendBatch列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
				var height = 350;
				if("${data.requestPage}"=="itemApp_connectSendBatch"){
					height = 220;
					$("#returnDetailDiv").removeAttr("hidden");
					Global.JqGrid.initJqGrid("returnDetailDataTable",{
						height:200,
						styleUI: 'Bootstrap',   
						rowNum: 10000,
						colModel : [
							{name: "no", index: "no", width: 106, label: "退货单号", sortable: true, align: "left"},
							{name: "appczydescr", index: "appczydescr", width: 68, label: "申请人", sortable: true, align: "left"},
							{name: "phone", index: "phone", width: 102, label: "手机", sortable: true, align: "left"},
							{name: "custcode", index: "custcode", width: 82, label: "客户编号", sortable: true, align: "left"},
							{name: "date", index: "date", width: 94, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
							{name: "statusdescr", index: "statusdescr", width: 74, label: "退货状态", sortable: true, align: "left"},
							{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
							{name: "itemtype1descr", index: "itemtype1descr", width: 117, label: "材料类型1", sortable: true, align: "left"},
							{name: "remarks", index: "remarks", width: 126, label: "备注", sortable: true, align: "left"}
						]
					});
				}
		        //初始化表格
				Global.JqGrid.initJqGrid("itemSendBatchDataTable",{
					url:"${ctx}/admin/itemSendBatch/goJqGrid",
					height:height,
					styleUI: 'Bootstrap',   
					postData:$("#page_form").jsonForm(),
					rowNum: 10000,
					colModel : [
						{name: "no", index: "no", width: 113, label: "批次编号", sortable: true, align: "left"},
						{name: "appczydesc", index: "appczydesc", width: 97, label: "申请人员", sortable: true, align: "left"},
						{name: "drivername", index: "drivername", width: 125, label: "司机", sortable: true, align: "left"},
						{name: "statusdesc", index: "statusdesc", width: 105, label: "状态", sortable: true, align: "left"},
						{name: "date", index: "date", width: 121, label: "生成日期", sortable: true, align: "left", formatter: formatTime},
						{name: "remarks", index: "remarks", width: 228, label: "备注", sortable: true, align: "left"}
					],
					gridComplete:function(){
						$("#returnDetailDataTable").jqGrid("clearGridData");
						if("${data.requestPage}"=="itemApp_connectSendBatch"){
							var ret = selectDataTableRow("itemSendBatchDataTable");
							if(ret){
								$("#returnDetailDataTable").jqGrid("setGridParam",{
									url:"${ctx}/admin/itemSendBatch/goReturnDetailJqGrid",
									postData:{
										no:ret.no
									},
									page:1,
									sortname:''
								}).trigger("reloadGrid");
							}
						}
					},
					onSelectRow:function(rowId, status){
						if("${data.requestPage}"=="itemApp_connectSendBatch"){
							var ret = $("#itemSendBatchDataTable").jqGrid("getRowData", rowId);
							if(ret){
								$("#returnDetailDataTable").jqGrid("setGridParam",{
									url:"${ctx}/admin/itemSendBatch/goReturnDetailJqGrid",
									postData:{
										no:ret.no
									},
									page:1,
									sortname:''
								}).trigger("reloadGrid");
							}
						}
					},
		            ondblClickRow:function(rowid,iRow,iCol,e){
						var selectRow = $("#itemSendBatchDataTable").jqGrid("getRowData", rowid);
		            	Global.Dialog.returnData = selectRow;
		            	Global.Dialog.closeDialog();
		            }
				});
		});
		function goto_query(){
			$("#itemSendBatchDataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemSendBatch/goJqGrid",postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form"  class="form-search">
					<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }" />
					<input type="hidden" id="custCode" name="custCode" value="${data.custCode }" />
					<input type="hidden" id="requestPage" name="requestPage" value="${data.requestPage }" />
					<input type="hidden" id="expired" name="expired" value="F" />
					<ul class="ul-form">
						<li> 
							<label>批次号</label>
							<input type="text" id="no" name="no"  value="${data.no}" />
						</li>
						<li> 
							<label>司机</label>
							<input type="text" id="driverCodeDescr" name="driverCodeDescr"   value="${data.driverCodeDescr}" />
						</li>
						<li class="search-group-shrink" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>	
				</form>
			</div>
			
			<!--query-form-->
			<div class="pageContent">
			
				<div id="content-list">
					<table id= "itemSendBatchDataTable"></table>
				</div> 
				<div id="returnDetailDiv" hidden>
					<ul class="nav nav-tabs" >
					    <li class="active"><a href="#returnDetail" data-toggle="tab">退货明细</a></li>  
					</ul>  
				    <div class="tab-content">  
						<div id="returnDetail"  class="tab-pane fade in active"> 
							<table id = "returnDetailDataTable"></table>
						</div> 
					</div>	
				</div>
			</div>
		</div>	
	</body>
</html>


