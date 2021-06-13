<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<%
	String fromFlag = request.getParameter("fromFlag"); 
	String no = request.getParameter("no"); 
	String supplCode = request.getParameter("supplCode"); 
%>

<script type="text/javascript">
$(function(){
	
	var postData={
		no:$("#no_applyList").val().trim()
	};

	if($("#fromFlag_applyList").val().trim() == "clhxd"){
		$.extend(postData,{
			supplCode:$("#supplCode_applyList").val().trim()
		});
	}

 	Global.JqGrid.initJqGrid("dataTable_applyList",{
		height:330,
		rowNum: 10000,
		url:"${ctx}/admin/itemPreApp/goApplyListJqGrid",
		postData:postData,
		styleUI:"Bootstrap",
		colModel:[
			{name:"itemcode", index:"itemcode", width:64.5, label:"材料编号", sortable:true, align:"left"} ,
			{name:"fixareadescr", index:"fixareadescr", width:90, label:"区域", sortable:true, align:"left"},
			{name:"itemdescr", index:"itemdescr", width:200, label:"材料名称", sortable:true, align:"left"},
			{name:"qty", index:"qty", width:50, label:"数量", sortable:true, align:"right"},
			{name:"unitdescr", index:"unitdescr", width:50, label:"单位", sortable:true, align:"left"},
			{name:"reqpk", index:"reqpk", width:100, label:"领料需求标识", sortable:true, align:"left"},
			{name:"remarks", index:"remarks", width:150, label:"备注", sortable:true, align:"left"},
			{name:"orderqty", index:"orderqty", width:74.5, label:"下单数量", sortable:true, align:"right"},
			{name:"itemappno", index:"itemappno", width:74.5, label:"领料单号", sortable:true, align:"left"},
			{name:"itemappstatus", index:"itemappstatus", width:80, label:"领料单状态", sortable:true, align:"left"},
			{name:"supplierdescr", index:"supplierdescr", width:90, label:"供应商", sortable:true, align:"left"},
			{name:"lastupdate", index:"lastupdate", width:90, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true, align:"left"},
			{name:"actionlog", index:"actionlog", width:70, label:"操作标志", sortable:true, align:"left"}			
		]
	}); 
	if($("#fromFlag_applyList").val().trim()=="clhxd"){
		$("#dataTable_applyList").jqGrid("hideCol", "lastupdate");
		$("#dataTable_applyList").jqGrid("hideCol", "lastupdatedby");
		$("#dataTable_applyList").jqGrid("hideCol", "actionlog");
	}
});
</script>
<input type="hidden" id="fromFlag_applyList" value="<%=fromFlag %>" />
<input type="hidden" id="no_applyList" value="<%=no %>" />
<input type="hidden" id="supplCode_applyList" value="<%=supplCode %>" />
<table id="dataTable_applyList"></table>
