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
	<title>搜寻——工种分类2</title>
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
			Global.JqGrid.initJqGrid("dataTable", {
				url:"${ctx}/admin/workType2/goJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				colModel : [
					{name:"code",index:"code",width:60,label:"编号",sortable:true,align:"left"},
					{name:"descr",index:"descr",width:100,label:"中文名称",sortable:true,align:"left"},
					{name:"worktype1",index:"worktype1",width:80,label:"工种分类1",sortable:true,align:"left",hidden: true},
					{name:"worktype1descr",index:"worktype1descr",width:80,label:"工种分类1",sortable:true,align:"left"}
				],
				ondblClickRow:function(rowid, iRow, iCol, e){
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
					<input type="hidden" id="expired" name="expired" value="${workType2.expired != 'T' ? 'F': 'T'}">
					<ul class="ul-form">
						<li> 
							<label>名称</label>
							<input type="text" id="descr" name="descr"  value="${workType2.descr }"/>
						</li>
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${workType2.expired != 'T' ? 'F': 'T'}" onclick="hideExpired(this)"
									${workType2.expired != 'T' ? 'checked': ''}/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
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
