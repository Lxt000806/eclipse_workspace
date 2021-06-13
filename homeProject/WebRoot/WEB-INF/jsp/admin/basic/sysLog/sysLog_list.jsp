<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SysLog列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_menu.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("sysLogView",{
		  title:"查看日志",
		  url:"${ctx}/admin/sysLog/goDetail?id="+ret.id,
		  height:600,
		  width:700,
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/sysLog/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
        styleUI: 'Bootstrap',
		colModel : [
		  {name : 'id',index : 'id',width : 115,label:'操作日志编号',sortable : true,align : "left"},
	      {name : 'requestUrl',index : 'requestUrl',width : 290,label:'请求路径',sortable : true,align : "left"},
	      {name : 'method',index : 'method',width : 95,label:'请求方法',sortable : true,align : "left"},
	      {name : 'menuname',index : 'menuname',width : 95,label:'菜单名称',sortable : true,align : "left"},
	      {name : 'params',index : 'params',width : 150,label:'请求参数',sortable : true,align : "left"},
	      {name : 'description',index : 'description',width : 150,label:'日志内容',sortable : true,align : "left"},
	      {name : 'operId',index : 'operId',width : 100,label:'操作员',sortable : true,align : "left"},
	      {name : 'operDate',index : 'operDate',width : 150,label:'操作日期',sortable : true,align : "left",formatter:formatTime},
           ]
	});
	$("#operId").openComponent_czybm();
	$("#requestUrl").openComponent_menu();
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/sysLog/goJqGrid"}).trigger("reloadGrid");
	}
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>操作日志编号</label>
						<input type="text" id="id" name="id" value="${sysLog.id}" />
					</li>
					<li>	
						<label>请求参数</label>
						<input type="text" id="params" name="params" value="${sysLog.params}" />
					</li>
					<li>	
						<label>菜单编号</label>
						<input type="text" id="requestUrl" name="requestUrl" value="${sysLog.requestUrl}" />
					</li>
					<li>	
						<label>操作员编号</label>
						<input type="text" id="operId" name="operId" value="${sysLog.operId}" />
					</li>
					<li>	
						<label>从时间</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${sysLog.dateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>	
						<label>到时间</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${sysLog.dateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>	
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
            	<house:authorize authCode="SYSLOG_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcelNow('操作日志')">导出excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


