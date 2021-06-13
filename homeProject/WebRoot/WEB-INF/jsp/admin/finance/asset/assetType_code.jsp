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
	<title>搜寻——资产类别</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/assetType/getAssetTypeBySql",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap", 
		colModel : [
			{name:"code", index:"code", width:90, label:"类别编号", sortable:true,align:"left",},
			{name:"descr", index:"descr", width:75, label:"类别名称", sortable:true,align:"left",},
			{name:"remcode", index:"remcode", width:75,	label:"助记码", sortable:true,align:"left",},		
			{name:"typedescr", index:"typedescr", width:75,	label:"折旧方法", sortable:true,align:"left",},		
			{name:"deprtype", index:"deprtype", width:75,	label:"折旧方法编号", sortable:true,align:"left",hidden:true},		
		],
		  ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
           }
	});
});

</script>
</head>
	<body >
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li> 
							<label>类别名称</label>
							<input type="text" id="descr" name="descr"/>
						</li>
						<li> 
							<label>助记码</label>
							<input type="text" id="remCode" name="remCode"/>
						</li>
						<li>
							<label></label>
							<input type="checkbox" id="expired_show" name="expired_show"
										value="${asset.expired}" onclick="hideExpired(this)"
										${asset.expired!='T' ?'checked':'' }/>隐藏过期记录&nbsp; 
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>
	</body>	
</html>
