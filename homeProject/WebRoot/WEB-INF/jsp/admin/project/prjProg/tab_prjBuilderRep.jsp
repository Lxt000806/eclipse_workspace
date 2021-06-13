<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goBuilderRepJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:370,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "LastUpdate", index: "LastUpdate", width: 120, label: "报备日期", sortable: true, align: "left",formatter:formatTime} 	,		
				{name: "buildstatusdescr", index: "buildstatusdescr", width: 120, label: "停工原因", sortable: true, align: "left",} 	,		
				{name: "Remark", index: "Remark", width: 400, label: "说明", sortable: true, align: "left",} 	,		
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_builderRep",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable_builderRep"></table>
					<div id="dataTable_builderRepPager"></div>
					
				</div>
		</div>



