<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ItemApp明细查询</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		var parentWin=window.opener;
		$(function(){
			$document = $(document);
			$("div.panelBar", $document).panelBar();
			
			//初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				height:$(document).height()-$("#content-list").offset().top-105,
				styleUI: "Bootstrap",
				colModel : [
					{name : "address",index : "address",width : 160,label:"楼盘",align : "left"},
					{name : "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name : "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
					{name : "itemcodedescr",index : "itemcodedescr",width : 200,label:"材料名称",align : "left"},
					{name : "size",index : "size",width : 80,label:"规格",align : "left"},
					{name : "model",index : "model",width : 160,label:"型号",align : "left"},
					{name : "qty",index : "qty",width : 80,label:"数量",align : "right"}
				]
			});
		});
		
		function goto_query(){
			var tableId = "dataTable";	
			$("#"+tableId).jqGrid("setGridParam",{
				url:"${ctx}/admin/supplierItemApp/goJqGridDishesSend",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/supplierItemApp/doExcelDishesSend";
			doExcelAll(url);
		} 
		
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#page_form select").val("");
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}
			
		</script>
	
	</head>
	    
	<body onunload="closeWin()">
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
			      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			      	</div>
			   </div>
			</div>
			
		    <div class="query-form">
		    	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
		    		<input type="hidden" id="expired" name="expired" value="${itemApp.expired }"/>
		            <input type="hidden" id="module" name="module" value="SupplierItemApp"/>
					<input type="hidden" name="jsonString" value="" />
					
					<ul class="ul-form">
					
						<li>
							<label>楼盘</label>
							<input type="text" id="custAddress" name="custAddress" style="width:160px;"/>
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="date" name="date" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${itemApp.confirmDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>领料单状态</label>
							<house:xtdmMulit id="status" dictCode="" 
								sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMAPPSTATUS' and cbm in ('CONFIRMED','OPEN','CONRETURN','SEND')" 
								selectedValue="CONFIRMED,OPEN,CONRETURN">
							</house:xtdmMulit>
						</li>	
						<li id="loadMore" >
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query()" >查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm()" >清空</button>
						</li>
					</ul>
		    	</form>
		    </div>
		
		    <div id="content-list">
		        <table id="dataTable"></table>
		        <div id="dataTablePager"></div>
		    </div>
		</div>
	</body>
</html>
