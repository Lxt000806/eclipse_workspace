<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--机构</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/organization/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"left", hidden:true}, 
				{name:"orgid", index:"orgid", label:"机构账号", width:120, sortable: true, align:"left"}, 
				{name:"name", index:"name", label:"机构名称", width:120, sortable: true, align:"left"}, 
				{name:"issilencesigndescr", index:"issilencesigndescr", label:"是否静默签署", width:90, sortable: true, align:"left"},
				{name:"thirdpartyuserid", index:"thirdpartyuserid", label:"第三方账号", width:100, sortable: true, align:"left"}, 
				{name:"idnumber", index:"idnumber", label:"机构证件号", width:100, sortable: true, align:"left"}, 
				{name:"orglegalname", index:"orglegalname", label:"法人", width:70, sortable: true, align:"left"}, 
				{name:"orglegalidnumber", index:"orglegalidnumber", label:"法人证件号", width:100, sortable: true, align:"left"}, 
				{name:"flowid", index:"flowid", label:"流程Id", width:100, sortable: true, align:"left"}, 
				{name:"isidentifieddescr", index:"isidentifieddescr", label:"是否认证", width:70, sortable: true, align:"left"}, 
				{name:"identifyurl", index:"identifyurl", label:"认证Url", width:150, sortable: true, align:"left",}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:120, align:"left",formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }  
		});
});

</script>
</head>
    
<body>
	<div class="body-box-list">
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div> 
	</div>
</body>
</html>


