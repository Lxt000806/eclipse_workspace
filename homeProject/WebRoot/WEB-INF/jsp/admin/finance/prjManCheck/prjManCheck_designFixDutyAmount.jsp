<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<title>设计定责金额--明细</title>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
.panelBar {
	height: 26px;
}
.dataTableTitle {
	font-size: 16px;
	margin-top: 5px;
	margin-bottom: 5px;
}
</style>
<script type="text/javascript">
 $(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable_fixDuty",{
		url:"${ctx}/admin/prjManCheck/goFixDutyJqGrid",
		postData:{custCode:"${prjCheck.custCode}"},
		height:120,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "no", index: "no", width: 70, label: "定责编号", sortable: true, align: "left"},
			{name: "appdate", index: "appdate", width: 100, label: "日期", sortable: true, align: "left", formatter: formatTime},
			{name: "appczydescr", index: "appczydescr", width: 70, label: "申请人", sortable: true, align: "left"},
			{name: "fixdutyremarks", index: "fixdutyremarks", width: 180, label: "备注", sortable: true, align:"left"},
			{name: "fixdutymandescr", index: "fixdutymandescr", width: 70, label: "责任人", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "定责金额", sortable: true, align: "right", sum: true},
			{name: "riskfund", index: "riskfund", width: 100, label: "风控基金承担", sortable: true, align: "right", sum: true},
			{name: "fixdutymanremarks", index: "fixdutymanremarks", width: 180, label: "定责人说明", sortable: true, align: "left"}
        ],
    });
    
    Global.JqGrid.initJqGrid("dataTable_fixDutyDetail",{
		url:"${ctx}/admin/prjManCheck/goFixDutyDetailJqGrid",
		postData:{custCode:"${prjCheck.custCode}"},
		height:100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "baseitemdescr", index: "baseitemdescr", width: 100, label: "基础项目", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
			{name: "offerpri", index: "offerpri", width: 100, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 100, label: "材料单价", sortable: true, align:"right"},
			{name: "totalamount", index: "totalamount", width: 100, label: "漏项总价", sortable: true, align: "right", sum: true},
			{name: "additionsqty", index: "additionsqty", width: 100, label: "增项数量", sortable: true, align: "right"},
			{name: "additionsamount", index: "additionsamount", width: 100, label: "增项总价", sortable: true, align: "right", sum: true}
        ],
    });
    
    Global.JqGrid.initJqGrid("dataTable_baseItemChgDetail",{
		url:"${ctx}/admin/prjManCheck/goBaseItemChgDetailJqGrid",
		postData:{custCode:"${prjCheck.custCode}"},
		height:100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "baseitemdescr", index: "baseitemdescr", width: 100, label: "基础项目", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 100, label: "增项数量", sortable: true, align: "right"},
			{name: "unitprice", index: "unitprice", width: 100, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 100, label: "材料单价", sortable: true, align:"right"},
			{name: "additionsamount", index: "additionsamount", width: 100, label: "增项总价", sortable: true, align: "right"}
        ],
    });
    
 });
        
</script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
				    <button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<div class="dataTableTitle"><b>定责金额</b></div>
			<table id="dataTable_fixDuty"></table>
		</div>
		<div id="content-list1">
			<div class="dataTableTitle"><b>漏项项目</b></div>
			<table id="dataTable_fixDutyDetail"></table>
		</div>
		<div id="content-list2">
			<div class="dataTableTitle"><b>增减项目</b></div>
			<table id="dataTable_baseItemChgDetail"></table>
		</div>
	</div>
</body>

</html>


