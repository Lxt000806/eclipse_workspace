<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goCustComplainJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:390,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "Source", index: "Source", width: 100, label: "问题来源", sortable: true, align: "left",} 	,		
				{name: "statusdescr", index: "statusdescr", width: 100, label: "状态", sortable: true, align: "left",} 	,		
				{name: "Remarks", index: "statusdescr", width: 300, label: "投诉内容", sortable: true, align: "left",} 	,		
				{name: "CrtDate", index: "CrtDate", width: 100, label: "投诉时间", sortable: true, align: "left",formatter:formatDate} 	,		
				{name: "crtczydescr", index: "crtczydescr", width: 100, label: "登记人", sortable: true, align: "left",} 	,		
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_custComplain",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable_custComplain"></table>
				</div>
		</div>



