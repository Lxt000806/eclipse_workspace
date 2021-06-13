<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--机构印章</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
	td{
		vertical-align:middle !important;
	}
</style>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/orgSeal/goJqGrid",
			postData:{orgId:"${orgSeal.orgId}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"center",hidden:true}, 
				{name:"url", index:"url", label:"印章", width:100, sortable: true, align:"center",formatter:sealBtn}, 
				{name:"sealid", index:"sealid", label:"印章Id", width:250, sortable: true, align:"center"},
				{name:"type", index:"type", label:"模板类型", width:90, sortable: true, align:"center"},
				{name:"central", index:"central", label:"中心图案类型", width:90, sortable: true, align:"center"},
				{name:"color", index:"color", label:"颜色", width:70, sortable: true, align:"center"},
				{name:"qtext", index:"qtext", label:"下玄文", width:80, sortable: true, align:"center"},
				{name:"htext", index:"htext", label:"横向文", width:80, sortable: true, align:"center"},
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:120, align:"center",formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"center"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"center"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"center"}, 
			],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }  
		});
});

function sealBtn(v,x,r){
	return "<img style='cursor:pointer' title='点击查看大图' width='80px' height='80px' src='"
		+v+"' onclick='viewSealPic("+x.rowId+")'></img>";
}  

function viewSealPic(id){
	var ret = $("#dataTable").jqGrid('getRowData', id);
	Global.Dialog.showDialog("viewSealPic",{ 
  		title:"查看印章图片",
  		url:"${ctx}/admin/taxPayeeESign/goViewSealPic",
  		postData: {
  		    orgId:"${orgSeal.orgId}",sealId:ret.sealid
  		},
  		height:600,
  		width:600,
  		returnFun:goto_query
    });	
}
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


