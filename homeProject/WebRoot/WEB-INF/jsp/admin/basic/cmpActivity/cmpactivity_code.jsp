<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>giftApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	      //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/cmpActivity/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		postData: $("#page_form").jsonForm(),
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'no',index : 'no',width : 50,label:'编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 100,label:'活动名称',sortable : true,align : "left"},
	      {name : 'begindate',index : 'begindate',width : 100,label:'活动开始时间',sortable : true,align : "left",formatter:formatDate},			      
	      {name : 'enddate',index : 'enddate',width : 100,label:'活动结束时间',sortable : true,align : "left",formatter:formatDate},
	      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left",},
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
				<input type="hidden" id="expired" name="expired" value="F" />
				<ul class="ul-form">
						<li>
							<label>编号</label>
							<input type="text" id="no" name="no" style="width:160px;"  value="${cmpactivity.no }" />
						</li>
						<li>
							<label>名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;"  value="${cmpactivity.descr }" />
						</li>
						<li></li>
						<li>	
							<label>开始时间从</label>
		                    <input type="text" id="beginDate" name="beginDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
								<input type="text" id="endDate" name="endDate" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li id="loadMore" >
								<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					   </li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>
</html>


