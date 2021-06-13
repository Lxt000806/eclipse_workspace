<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--预支单</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var postData=$("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getAdvanceNoByJqgrid",
		postData:{czybh:"${czybh }",actName:$("#actName").val()},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',   
		colModel : [
		  		  {name : 'no',index : 'no',width : 80,label:'流程编号',sortable : true,align : "left",},
				  {name : 'zwxm',index : 'zwxm',width : 80,label:'发起人',sortable : true,align : "left",},
				  {name : 'statusdescr',index : 'statusdescr',width : 80,label:'状态',sortable : true,align : "left",},
				  {name : 'amount',index : 'amount',width : 80,label:'预支额',sortable : true,align : "left",},
				  {name : 'nodeductionamount',index : 'nodeductionamount',width : 80,label:'未抵扣金额',sortable : true,align : "left",},
				  {name : 'actname',index : 'actname',width : 80,label:'户名',sortable : true,align : "left",},
				  {name : 'cardid',index : 'cardid',width : 180,label:'卡号',sortable : true,align : "left",},
				  {name : 'starttime',index : 'starttime',width : 140,label:'发起时间',sortable : true,align : "left",formatter:formatTime},
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
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="czybh" name="czybh" value="${czybh }" />
				<ul class="ul-form" id="detail_ul">
					<div class="validate-group row" >
						<li>		
							<label>户名</label>
							<input type="text" id="actName" name="actName"/>
						</li>
						<li class="search-group" >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>					
					</div>
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


