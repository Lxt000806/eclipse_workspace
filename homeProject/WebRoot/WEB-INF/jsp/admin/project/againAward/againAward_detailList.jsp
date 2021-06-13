<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>签单奖励管理-明细查询</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/againAward/doDetailListExcel";
		doExcelAll(url,'detailListDataTable','page_form');
	}
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("detailListDataTable",{
				url:"${ctx}/admin/againAward/goJqGridDetailList",
				height: 360,
	        	styleUI: "Bootstrap",
				colModel : [
					{name : "address",index : "address",width : 140,label:"楼盘",sortable : true,align : "left"},
				  	{name : "custdescr",index : "custdescr",width : 80,label:"客户名称",sortable : true,align : "left"},
				  	{name : "custstatusdescr",index : "custstatusdescr",width : 80,label:"客户状态",sortable : true,align : "left"},
			      	{name : "layoutdescr",index : "layoutdescr",width : 50,label:"户型",sortable : true,align : "left"},
			      	{name : "area",index : "area",width : 50,label:"面积",sortable : true,align : "right"},
                    {name : "roledescr",index : "roledescr",width : 80,label:"角色",sortable : true,align : "left"},
                    {name : "empname",index : "empname",width : 80,label:"员工姓名",sortable : true,align : "left"},
                    {name : "empdpt2descr",index : "empdpt2descr",width : 120,label:"二级部门",sortable : true,align : "left"},
			      	{name : "setdate",index : "setdate",width : 120,label:"下定日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "signdate",index : "signdate",width : 120,label:"签订日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "sourcedescr",index : "sourcedescr",width : 65,label:"客户来源",sortable : true,align : "left"},
			      	{name : "amount",index : "amount",width : 65,label:"奖励金额",sortable : true,align : "right", sum:true},
			      	{name : "remarks",index : "remarks",width : 120,label:"备注",sortable : true,align : "left"},
			      	{name : "statusdescr",index : "statusdescr",width : 45,label:"状态",sortable : true,align : "left"},
			      	{name : "confirmdate",index : "confirmdate",width : 120,label:"审核日期",sortable : true,align : "left",formatter:formatTime}
	            ]
			});
		});
		function goto_query(){
			$("#detailListDataTable").jqGrid("setGridParam", {
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>							
							<label>签订日期从</label>
							<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.signDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="signDateTo" name="signDateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.signDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>							
							<label>审核日期从</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.confirmDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.confirmDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${data.address }" />
						</li>
						<li>							
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="AGAINAWARDSTS" selectedValue="${data.status}"></house:xtdmMulit>
						</li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="doExcel()">导出excel</button>
						</li>
					</ul>
				</form>
			</div>
			<div id="content-list">
				<table id="detailListDataTable"></table>
				<div id="detailListDataTablePager"></div>
			</div>
		</div>
	</body>
</html>
