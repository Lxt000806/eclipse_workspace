<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
}
function doExcel(){
	var url = "${ctx}/admin/customer/doExcelForModPhoneQuery";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/customer/goModPhoneJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  {name : 'custcode',index : 'custcode',width : 120,label:'客户编号',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		      {name : 'signdate',index : 'signdate',width : 150,label:'签订时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'designmandescr',index : 'designmandescr',width : 60,label:'设计师',sortable : true,align : "left"},
		      {name : 'businessmandescr',index : 'businessmandescr',width : 60,label:'业务员',sortable : true,align : "left"},
		      {name : 'designdept2descr',index : 'designdept2descr',width : 150,label:'设计师二级部门',sortable : true,align : "left"},
		      {name : 'businessdept2descr',index : 'businessdept2descr',width : 150,label:'业务员二级部门',sortable : true,align : "left"},
		      {name : 'oldphone',index : 'oldphone',width : 120,label:'修改前手机号',sortable : true,align : "left"},
		      {name : 'newphone',index : 'newphone',width : 120,label:'修改后手机号',sortable : true,align : "left"},
		      {name : 'moduledescr',index : 'moduledescr',width:70,label:'修改模块',sortable : true,align :" left"},
		      {name : 'date',index : 'date',width : 150,label:'修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'czybh',index : 'czybh',width : 60,label:'修改人',sortable : true,align : "left"},
            ],
            loadComplete: function(){
            	frozenHeightReset("dataTable");
            }
		});
});
</script>
<style type="text/css">

</style>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		      	<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
		      	<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
		      </div>
		    </div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label >签订日期</label>
							<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.signDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label >至</label>
							<input type="text" id="signDateTo" name="signDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${customer.signDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>	
						<li>
							<label>修改模块</label>
							<house:xtdm id="module" dictCode="PhoneModModule" value="${customer.module }"></house:xtdm>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


