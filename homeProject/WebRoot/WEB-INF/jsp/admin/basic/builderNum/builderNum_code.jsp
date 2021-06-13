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
	<title>搜寻——楼栋编号</title>
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
		url:"${ctx}/admin/builderNum/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'PK',	index:'PK',	width:90, label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'BuilderNum',	index:'BuilderNum',	width:90, label:'楼栋号',	sortable:true,align:"left",},
			{name:'builderdescr',	index:'builderdescr',	width:90, label:'专盘名称',	sortable:true,align:"left",},
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
	<body >
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li> 
							<label>楼栋号</label>
							<input type="text" id="builderNum" name="builderNum"  value="${builderNum.builderNum }"/>
						</li>
						<li> 
							<label>项目编号</label>
							<input type="text" id="builderCode" name="builderCode"  value="${builderNum.builderCode }"/>
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div>
			
			
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
