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
		<title>签单奖励管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/againAward/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-80,
	        	styleUI: "Bootstrap",
				colModel : [
				  {name : "no",index : "no",width : 70,label:"单号",sortable : true,align : "left"},
				  {name : "status",index : "status",width : 70,label:"status",sortable : true,align : "left", hidden:true},
				  {name : "documentno",index : "documentno",width : 100,label:"凭证号",sortable : true,align : "left"},
				  {name : "statusdescr",index : "statusdescr",width : 50,label:"状态",sortable : true,align : "left"},
			      {name : "appczydescr",index : "appczydescr",width : 80,label:"申请人",sortable : true,align : "left"},
			      {name : "date",index : "date",width : 130,label:"申请时间",sortable : true,align : "left",formatter:formatTime},
			      {name : "confirmczydescr",index : "confirmczydescr",width : 80,label:"审核人",sortable : true,align : "left"},
			      {name : "confirmdate",index : "confirmdate",width : 130,label:"审核时间",sortable : true,align : "left",formatter:formatTime},
			      {name : "remarks",index : "remarks",width : 160,label:"备注",sortable : true,align : "left"},
			      {name : "lastupdatedby",index : "lastupdatedby",width : 90,label:"最后修改人",sortable : true,align : "left"},
			      {name : "lastupdate",index : "lastupdate",width : 130,label:"最后修改时间",sortable : true,align : "left",formatter:formatTime},
			      {name : "expired",index : "expired",width : 80,label:"是否过期",sortable : true,align : "left"},
			      {name : "actionlog",index : "actionlog",width : 80,label:"操作日志",sortable : true,align : "left"}
	            ]
			});
		});
		function add(){
        	Global.Dialog.showDialog("againAwardAdd",{
        		title:"签单奖励管理--新增",
        	  	url:"${ctx}/admin/againAward/goAdd",
        	  	height: 650,
        	  	width:1300,
        	  	returnFun:goto_query
        	});
		}
		function update(){
			var ret = selectDataTableRow();
			if(ret){
				if(ret.status.trim() != "1"){
					art.dialog({
						content:"申请状态才可以编辑"
					});
					return;
				}
	        	Global.Dialog.showDialog("againAwardUpdate",{
	        		title:"签单奖励管理--编辑",
	        	  	url:"${ctx}/admin/againAward/goUpdate",
	        	  	postData:{
	        	  		no:ret.no
	        	  	},
	        	  	height: 650,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function confirm(){
			var ret = selectDataTableRow();
			if(ret){
				if(ret.status.trim() != "1"){
					art.dialog({
						content:"申请状态才可以审核"
					});
					return;
				}
	        	Global.Dialog.showDialog("againAwardCheck",{
	        		title:"签单奖励管理--审核",
	        	  	url:"${ctx}/admin/againAward/goCheck",
	        	  	postData:{
	        	  		no:ret.no
	        	  	},
	        	  	height: 650,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow();
			if(ret){
	        	Global.Dialog.showDialog("againAwardView",{
	        		title:"签单奖励管理--查看",
	        	  	url:"${ctx}/admin/againAward/goView",
	        	  	postData:{
	        	  		no:ret.no
	        	  	},
	        	  	height: 650,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function detailList(){
        	Global.Dialog.showDialog("againAwardDetailList",{
        		title:"签单奖励管理--明细查询",
        	  	url:"${ctx}/admin/againAward/goAgainAwardDetailList",
        	  	height: 650,
        	  	width:1300,
        	  	returnFun:goto_query
        	});
		}
		function print1(){
			var ret = selectDataTableRow();
			if(ret){
				Global.Print.showPrint("againAward.jasper", {
					No: ret.no,
					SUBREPORT_DIR: "<%=jasperPath%>" 
				});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function print(){
			var ret = selectDataTableRow();
			Global.Dialog.showDialog("print",{
				title:"签单奖励管理——打印",
				url:"${ctx}/admin/againAward/goPrint?no="+ret.no,
				height:340,
				width:500,
			});
		}
		function doExcel(){
			doExcelAll("${ctx}/admin/againAward/doExcel");
		}
	
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#page_form select").val("");
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
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
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${data.address }" />
						</li>
						<li>							
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="AGAINAWARDSTS" selectedValue="${data.status}"></house:xtdmMulit>
						</li>
						<li>							
							<label>申请日期从</label>
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
					<house:authorize authCode="AGAINAWARD_ADD">
						<button type="button" class="btn btn-system" onclick="add()">新增</button>
					</house:authorize>
	     			<house:authorize authCode="AGAINAWARD_UPDATE">
						<button type="button" class="btn btn-system" onclick="update()">编辑</button>
					</house:authorize>
	     			<house:authorize authCode="AGAINAWARD_CONFIRM">
						<button type="button" class="btn btn-system" onclick="confirm()">审核</button>
					</house:authorize>
					<house:authorize authCode="AGAINAWARD_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">查看</button>
	                </house:authorize>
	     			<house:authorize authCode="AGAINAWARD_DETAILLIST">
						<button type="button" class="btn btn-system" onclick="detailList()">明细查询</button>
					</house:authorize>
					<house:authorize authCode="AGAINAWARD_PRINT">
						<button type="button" class="btn btn-system" onclick="print()">打印</button>
	                </house:authorize>
	                <house:authorize authCode="AGAINAWARD_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">输出至Excel</button>
	                </house:authorize>
			    </div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>
</html>
