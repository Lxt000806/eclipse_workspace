<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>工地效率分析</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			/* 添加项目名称查询组件 */
			$("#builderCode").openComponent_builder();
		
			Global.JqGrid.initJqGrid("department2DataTable",{
				url:"${ctx}/admin/PrjEffAnly/goJqGrid",
				postData:$("#page_form").jsonForm(),
				height:$(document).height()-$("#content-list-department2").offset().top-100,
	        	styleUI: "Bootstrap",
				onSortColEndFlag:true,
				colModel : [
				  {name : "dept2code",index : "dept2code",width : 80,label:"dept2code",sortable : true,align : "left", hidden : true},
				  {name : "department2descr",index : "department2descr",width : 80,label:"部门",sortable : true,align : "left"},
				  {name : "confirmbuilds",index : "confirmbuilds",width : 70,label:"在建套数",sortable : true,align : "right", sum:true},
				  {name : "confirmstopbuilds",index : "confirmstopbuilds",width : 85,label:"在建停工数",sortable : true,align : "right", sum:true},
				  {name : "delaybuilds",index : "delaybuilds",width : 70,label:"拖期套数",sortable : true,align : "right", sum:true},
			      {name : "delaybuildspro",index : "delaybuildspro",width : 95,label:"拖期工地占比",sortable : true,align : "right", formatter:function(cellvalue, options, rowObject){return Math.round(myRound(cellvalue,2)*100)+"%";}},
			      {name : "delaybuilds030",index : "delaybuilds030",width : 95,label:"拖期0到30天",sortable : true,align : "right", sum:true},
			      {name : "delaybuilds3060",index : "delaybuilds3060",width : 95,label:"拖期30到60天",sortable : true,align : "right", sum:true},
			      {name : "delaybuildsgt60",index : "delaybuildsgt60",width : 95,label:"拖期大于60天",sortable : true,align : "right", sum:true},
			      {name : "sds",index : "sds",width : 50,label:"水电",sortable : true,align : "right", sum:true},
			      {name : "sdtqs",index : "sdtqs",width : 80,label:"水电拖期",sortable : true,align : "right", sum:true},
			      {name : "nssms",index : "nssms",width : 80,label:"泥水饰面",sortable : true,align : "right", sum:true},
			      {name : "nssmtqs",index : "nssmtqs",width : 100,label:"泥水饰面拖期",sortable : true,align : "right", sum:true},
			      {name : "gddms",index : "gddms",width : 80,label:"刮底打磨",sortable : true,align : "right", sum:true},
			      {name : "gddmtqs",index : "gddmtqs",width : 100,label:"刮底打磨拖期",sortable : true,align : "right", sum:true},
			      {name : "hqazs",index : "hqazs",width : 80,label:"后期安装",sortable : true,align : "right", sum:true},
			      {name : "hqaztqs",index : "hqaztqs",width : 100,label:"后期安装拖期",sortable : true,align : "right", sum:true},
			      {name : "checkcount",index : "checkcount",width : 80,label:"巡检次数",sortable : true,align : "right", sum:true},
			      {name : "modifycount",index : "modifycount",width : 80,label:"整改次数",sortable : true,align : "right", sum:true},
			      {name : "modifycompcount",index : "modifycompcount",width : 100,label:"整改完成次数",sortable : true,align : "right", sum:true},
			      {name : "modifycomppro",index : "modifycomppro",width : 90,label:"整改完成率",sortable : true,align : "right", formatter:function(cellvalue, options, rowObject){return Math.round(myRound(cellvalue,2)*100)+"%";}}
	            ],
	            gridComplete:function(){
					var confirmbuilds = $("#department2DataTable").footerData("get","confirmbuilds").confirmbuilds;
					var delaybuilds = $("#department2DataTable").footerData("get","delaybuilds").delaybuilds;
					$("#department2DataTable").footerData("set",{delaybuildspro:parseFloat(confirmbuilds)<=0?0:parseFloat(delaybuilds)/parseFloat(confirmbuilds)});
					
					var modifycount = $("#department2DataTable").footerData("get","modifycount").modifycount;
					var modifycompcount = $("#department2DataTable").footerData("get","modifycompcount").modifycompcount;
					$("#department2DataTable").footerData("set",{modifycomppro:parseFloat(modifycount)<=0?0:parseFloat(modifycompcount)/parseFloat(modifycount)});
	            },
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#department2DataTable").jqGrid("getRowData");
					$.each(rows, function(k,v){
						rows[k].delaybuildspro = parseInt(rows[k].delaybuildspro.substring(0, rows[k].delaybuildspro.length-1))/100.0;
						rows[k].modifycomppro = parseInt(rows[k].modifycomppro.substring(0, rows[k].modifycomppro.length-1))/100.0;
					});
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("department2DataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("department2DataTable", v);
					});
				}
			});
			Global.JqGrid.initJqGrid("custTypeDataTable",{
				url:"${ctx}/admin/PrjEffAnly/goJqGrid",
				postData:$("#page_form").jsonForm(),
				height:$(document).height()-$("#content-list-department2").offset().top-100,
	        	styleUI: "Bootstrap",
				onSortColEndFlag:true,
				colModel : [
				  {name : "custtype",index : "custtype",width : 80,label:"custtype",sortable : true,align : "left", hidden : true},
				  {name : "constructtype",index : "constructtype",width : 80,label:"constructtype",sortable : true,align : "left", hidden : true},
				  {name : "custtypedescr",index : "custtypedescr",width : 80,label:"工地类型",sortable : true,align : "left"},
				  {name : "confirmbuilds",index : "confirmbuilds",width : 70,label:"在建套数",sortable : true,align : "right", sum:true},
				  {name : "confirmstopbuilds",index : "confirmstopbuilds",width : 90,label:"在建停工数",sortable : true,align : "right", sum:true},
				  {name : "delaybuilds",index : "delaybuilds",width : 70,label:"拖期套数",sortable : true,align : "right", sum:true},
			      {name : "delaybuildspro",index : "delaybuildspro",width : 95,label:"拖期工地占比",sortable : true,align : "right", formatter:function(cellvalue, options, rowObject){return Math.round(myRound(cellvalue,2)*100)+"%";}},
			      {name : "delaybuilds030",index : "delaybuilds030",width : 95,label:"拖期0到30天",sortable : true,align : "right", sum:true},
			      {name : "delaybuilds3060",index : "delaybuilds3060",width : 95,label:"拖期30到60天",sortable : true,align : "right", sum:true},
			      {name : "delaybuildsgt60",index : "delaybuildsgt60",width : 95,label:"拖期大于60天",sortable : true,align : "right", sum:true},
			      {name : "sds",index : "sds",width : 50,label:"水电",sortable : true,align : "right", sum:true},
			      {name : "sdtqs",index : "sdtqs",width : 80,label:"水电拖期",sortable : true,align : "right", sum:true},
			      {name : "nssms",index : "nssms",width : 80,label:"泥水饰面",sortable : true,align : "right", sum:true},
			      {name : "nssmtqs",index : "nssmtqs",width : 100,label:"泥水饰面拖期",sortable : true,align : "right", sum:true},
			      {name : "gddms",index : "gddms",width : 80,label:"刮底打磨",sortable : true,align : "right", sum:true},
			      {name : "gddmtqs",index : "gddmtqs",width : 100,label:"刮底打磨拖期",sortable : true,align : "right", sum:true},
			      {name : "hqazs",index : "hqazs",width : 80,label:"后期安装",sortable : true,align : "right", sum:true},
			      {name : "hqaztqs",index : "hqaztqs",width : 100,label:"后期安装拖期",sortable : true,align : "right", sum:true},
			      {name : "checkcount",index : "checkcount",width : 80,label:"巡检次数",sortable : true,align : "right", sum:true},
			      {name : "modifycount",index : "modifycount",width : 80,label:"整改次数",sortable : true,align : "right", sum:true},
			      {name : "modifycompcount",index : "modifycompcount",width : 100,label:"整改完成次数",sortable : true,align : "right", sum:true},
			      {name : "modifycomppro",index : "modifycomppro",width : 90,label:"整改完成率",sortable : true,align : "right", formatter:function(cellvalue, options, rowObject){return Math.round(myRound(cellvalue,2)*100)+"%";}}
	            ],
	            gridComplete:function(){
					var confirmbuilds = $("#custTypeDataTable").footerData("get","confirmbuilds").confirmbuilds;
					var delaybuilds = $("#custTypeDataTable").footerData("get","delaybuilds").delaybuilds;
					$("#custTypeDataTable").footerData("set",{delaybuildspro:parseFloat(confirmbuilds)<=0?0:parseFloat(delaybuilds)/parseFloat(confirmbuilds)});
					
					var modifycount = $("#custTypeDataTable").footerData("get","modifycount").modifycount;
					var modifycompcount = $("#custTypeDataTable").footerData("get","modifycompcount").modifycompcount;
					$("#custTypeDataTable").footerData("set",{modifycomppro:parseFloat(modifycount)<=0?0:parseFloat(modifycompcount)/parseFloat(modifycount)});			
	            },
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#custTypeDataTable").jqGrid("getRowData");
					$.each(rows, function(k,v){
						rows[k].delaybuildspro = parseInt(rows[k].delaybuildspro.substring(0, rows[k].delaybuildspro.length-1))/100.0;
						rows[k].modifycomppro = parseInt(rows[k].modifycomppro.substring(0, rows[k].modifycomppro.length-1))/100.0;
					});
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("custTypeDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("custTypeDataTable", v);
					});
				}
			});
			$("#content-list-custType").hide();		
					
			$("#custTypeDataTable").jqGrid("setGroupHeaders", {
				useColSpanStyle: true, 
				groupHeaders:[
					{startColumnName: "delaybuilds", numberOfColumns:5, titleText:"工地拖期整体情况"},
					{startColumnName: "sds", numberOfColumns:8, titleText:"工地阶段拖期情况"},
					{startColumnName: "checkcount", numberOfColumns:4, titleText:"巡检情况"}
				] 
			});
			
			$("#department2DataTable").jqGrid("setGroupHeaders", {
				useColSpanStyle: true, 
				groupHeaders:[
					{startColumnName: "delaybuilds", numberOfColumns:5, titleText:"工地拖期整体情况"},
					{startColumnName: "sds", numberOfColumns:8, titleText:"工地阶段拖期情况"},
					{startColumnName: "checkcount", numberOfColumns:4, titleText:"巡检情况"}
				] 
			});
		});
		
		function goto_query(){
			if(!$("#dateFrom").val()){
				art.dialog({
					content:"请选择一个开始时间"
				});
				return ;
			}
			if(!$("#dateTo").val()){
				art.dialog({
					content:"请选择一个结束时间"
				});
				return ;
			}
			if($("#sType").val() == "1"){
				$("#content-list-custType").hide();
				$("#content-list-department2").show();
				$("#department2DataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
			}else{
				$("#content-list-custType").show();
				$("#content-list-department2").hide();
				$("#custTypeDataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
			}
		}
		function clearForm(){
			$("#page_form input[type='text']").val('');
			$("#page_form select[id!=sType]").val('');
			$("#custTypes").val("");
			$.fn.zTree.getZTreeObj("tree_custTypes").checkAllNodes(false);
		}
		function doExcel(){
			var dataTable = "";
			if($("#sType").val() == "1"){
				dataTable = "department2DataTable";
			}else{
				dataTable = "custTypeDataTable";
			}
			doExcelAll("${ctx}/admin/PrjEffAnly/doExcel", dataTable);
		}
		function view(){
			if(!$("#dateFrom").val()){
				art.dialog({
					content:"请选择一个开始时间"
				});
				return ;
			}
			if(!$("#dateTo").val()){
				art.dialog({
					content:"请选择一个结束时间"
				});
				return ;
			}
			var postData = $("#page_form").jsonForm();
			var dataTableName = "";
			if($("#sType").val() == "1"){
				dataTableName = "department2DataTable";
			}else{
				dataTableName = "custTypeDataTable";
			}
			var ret = selectDataTableRow(dataTableName);
			if(ret){
				$.extend(postData, {
					dept2Code:ret.dept2code,
					custType:ret.custtype,
					constructType:ret.constructtype
				});
	        	Global.Dialog.showDialog("prjEffAnlyView",{
	        		title:"工地效率分析详情页",
	        	  	url:"${ctx}/admin/PrjEffAnly/goView",
	        	  	postData:postData,
	        	  	height: 730,
	        	  	width:1400
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>工程部</label>
							<house:department2 id="department2s" dictCode="" depType="3"></house:department2>
						</li>
						<li>
							<label>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value=""/>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" ></house:custTypeMulit>
						</li>
						<li>							
							<label>日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.dateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>		
							<label>统计方式</label>
							<select id="sType" name="sType" >
								<option value="1">按工程部</option>
								<option value="2">按工地类型</option>
							</select>
						</li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>
			
			<div class="clear_float"> </div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="PRJEFFANLY_CT_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">查看</button>
	                </house:authorize>
	                <house:authorize authCode="PRJEFFANLY_CT_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">输出至Excel</button>
	                </house:authorize>
			    </div>
			</div>
			<div id="content-list-department2">
				<table id="department2DataTable"></table>
			</div>
			<div id="content-list-custType">
				<table id="custTypeDataTable"></table>
			</div>
		</div>
	</body>
</html>
