<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>巡检覆盖率分析</title>
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
			function doExcel(){
				doExcelNow("巡检覆盖率分析", "dataTable");
			}
			function view(){
				var ret = selectDataTableRow("dataTable");
				if(ret){
					if($("#dateFrom").val() == ""){
						art.dialog({
							content:"请选择开始时间"
						});
						return;
					}
					if($("#dateTo").val() == ""){
						art.dialog({
							content:"请选择结束时间"
						});
						return;
					}
					var dateFrom = new Date($("#dateFrom").val());
					var dateTo = new Date($("#dateTo").val());
					if(dateFrom.getTime()-dateTo.getTime() > 0){
						art.dialog({
							content:"开始时间必须小于结束时间"
						});
						return;
					}
					
		        	Global.Dialog.showDialog("progCheckCoverageView",{
		        	  title:"巡检覆盖率分析--查看",
		        	  url:"${ctx}/admin/progCheckCoverage/goView",
		        	  postData:{
		        	  	type:ret.type,
		        	  	dateFrom:$("#dateFrom").val(),
		        	  	dateTo:$("#dateTo").val(),
		        	  	isCheckDept:$("#isCheckDept").val()
		        	  },
		        	  height: 550,
		        	  width: 800,
		        	});
				}else{
					art.dialog({
						content:"请选择一条记录"
					});
				}
			}
			
			function goto_query(){
				if($("#dateFrom").val() == ""){
					art.dialog({
						content:"请选择开始时间"
					});
					return;
				}
				if($("#dateTo").val() == ""){
					art.dialog({
						content:"请选择结束时间"
					});
					return;
				}
				var dateFrom = new Date($("#dateFrom").val());
				var dateTo = new Date($("#dateTo").val());
				if(dateFrom.getTime()-dateTo.getTime() > 0){
					art.dialog({
						content:"开始时间必须小于结束时间"
					});
					return;
				}
				
				$("#dataTable").jqGrid("setGridParam",{
					postData:$("#page_form").jsonForm(),
					page:1,
					sortname:''
				}).trigger("reloadGrid");
			}
			$(function(){
				Global.JqGrid.initJqGrid("dataTable", {
					url:"${ctx}/admin/progCheckCoverage/goJqGrid",
					postData:$("#page_form").jsonForm(),
					height:$(document).height()-$("#content-list").offset().top-75,
					colModel : [		
						{name: "type", index: "type", width: 200, label: "巡检次数type", sortable: true, align: "left", hidden: true},	
						{name: "progCheckNum", index: "progCheckNum", width: 200, label: "巡检次数", sortable: true, align: "left"},
						{name: "allBuilds", index: "allBuilds", width: 200, label: "合计套数", sortable: true, align: "right", sum:true},
						{name: "proportion", index: "proportion", width: 200, label: "占比", sortable: true, align: "right"},
		            ],
		            gridComplete:function(){
		            	var sumAllBuilds = $("#dataTable").jqGrid("getCol", "allBuilds", false, "sum");
		            	var ids = $("#dataTable").jqGrid("getDataIDs");
		            	
		            	$.each(ids, function(index, value){
		            		var allBuilds = $("#dataTable").jqGrid("getCell", value, "allBuilds");
		            		var data = sumAllBuilds > 0 ? myRound(parseFloat(allBuilds)/sumAllBuilds, 3)*100 : 0;
		            		$("#dataTable").jqGrid("setCell", value, "proportion", data.toFixed(1)+"%");
		            	});
		            }
				});
			});
			
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						
						<li>
							<label >巡检日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label >至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date"  
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>巡检部巡检</label>
							<house:xtdm id="isCheckDept" dictCode="YESNO"></house:xtdm>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>	
					</ul>		
				</form>
			</div>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="PROGCHECKCOVERAGE_VIEW">
						<button type="button" id="btnview" class="btn btn-system"  onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="PROGCHECKCOVERAGE_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div> 
			<div id="content-list">
				<table id= "dataTable"></table>  
				<div id= "dataTablePager"></div>  
			</div>
		</div>
	</body>
</html>


