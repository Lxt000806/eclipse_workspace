<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>工地问题分析</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
		
			function getTable() {
				var tableId = "dataTable";
				if ($("#statistcsMethod").val() == "1") {		
					tableId = "dataTable";
				} else {
					tableId = "dataTableWaitDeal";
				} 
				return tableId;	
			}
			
			function change() {
				goto_query();
				if ($("#statistcsMethod").val() == "1") {	
					$("#content-list").show();  		
					$("#content-list-waitDeal").hide();  
				} else {
					$("#content-list").hide();  	
					$("#content-list-waitDeal").show();  
				}
			}
			
			function doExcel(){
				var url = "${ctx}/admin/prjProblemAnaly/doExcel";
				doExcelAll(url, getTable());
			}    
		
			function goto_query(){
				$("#"+getTable()).jqGrid("setGridParam",{url:"${ctx}/admin/prjProblemAnaly/goJqGrid",datatype: "json",postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
			}
					
			/**初始化表格*/
			$(function(){
				Global.JqGrid.initJqGrid("dataTable",{
					height:415,
					styleUI: "Bootstrap",
					rowNum: 10000,
					colModel : [
						{name: "department2", index: "department2", width: 100, label: "工程部", sortable: true, align: "left"},
						{name: "department2code", index: "department2code", width: 100, label: "工程部", sortable: true, align: "left", hidden: true},
						{name: "namechi", index: "namechi", width: 100, label: "工程部经理", sortable: true, align: "left"},
						{name: "count", index: "count", width: 100, label: "待确认单数", sortable: true, align: "right", sum: true},
						{name: "morethan24", index: "morethan24", width: 100, label: "超24小时单数", sortable: true, align: "right", sum: true}
					],	               	 			         
				});
 				Global.JqGrid.initJqGrid("dataTableWaitDeal",{   		
					height:415,
					styleUI: 'Bootstrap',
					colModel : [
						{name: "promdept", index: "promdept", width: 100, label: "责任部门", sortable: true, align: "left"},
						{name: "promdeptcode", index: "promdeptcode", width: 100, label: "责任部门", sortable: true, align: "left", hidden: true},
						{name: "department2", index: "department2", width: 100, label: "二级部门", sortable: true, align: "left"},
						{name: "department2code", index: "department2code", width: 100, label: "二级部门", sortable: true, align: "left", hidden: true},
						{name: "count", index: "count", width: 100, label: "待处理单数", sortable: true, align: "right", sum: true},
						{name: "morethan24nofeed", index: "morethan24nofeed", width: 120, label: "超24小时未反馈数", sortable: true, align: "right", sum: true},
						{name: "morethan36nodeal", index: "morethan36nodeal", width: 120, label: "超36小时未处理", sortable: true, align: "right", sum: true}
					],              
					rowNum:100000
				}); 
				$("#content-list-waitDeal").hide();  
			});
			
			function view(){
				var ret = selectDataTableRow(getTable());
				if (ret) {
					if (!ret.promdeptcode) {
						ret.promdeptcode = '';
					}
					Global.Dialog.showDialog("prjProblemAnalyView",{
						title:"工地问题分析——查看",
						url:"${ctx}/admin/prjProblemAnaly/goView",
						postData:{
							prjDepartment2: ret.department2code,
							promDeptCode: ret.promdeptcode,
							statistcsMethod: $("#statistcsMethod").val()
						},
						height:600,
						width:1100
					}); 
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			}	
		</script>
	</head>
	<body>
		<div class="body-box-list">
		
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">				
						<li> 
							<label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="change()">								
								<option value="1">待确认问题</option>
								<option value="2">待处理问题</option>
							</select>
						</li>	
						
						<li id="loadMore" >
							<button type="button"  class="btn btn-sm btn-system " onclick="goto_query()">查询</button>
						</li>	
					</ul>
				</form>
			</div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="GIFTTOKEN_ADD">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="GIFTTOKEN_ADD">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>  
					<div id="dataTablePager"></div>
				</div>
	
				<div id="content-list-waitDeal">
					<table id= "dataTableWaitDeal"></table> 
					<div id="dataTableWaitDealPager"></div>
				</div>
			</div> 
			
		</div>
	</body>
</html>


