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
	<title>搜寻——采购单号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',
							   });
		var postData = $("#page_form").jsonForm();
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/autoArr/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'PK',	index:'PK',	width:90,label:'批次号',sortable:true,align:"left",},
			{name:'LastUpdate',	index:'LastUpdate',	width:90,label:'最后修改时间',sortable:true,align:"left",formatter:formatTime},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:90,label:'最后修改人',sortable:true,align:"left",},
			{name:'ActionLog',	index:'ActionLog',	width:90,label:'操作代码',sortable:true,align:"left",},
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
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<ul class="ul-form">
						<li> 
							<label>批次号</label>
								<input type="text" id="pk" name="pk"  value="${autoArr.pk}"/>
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
