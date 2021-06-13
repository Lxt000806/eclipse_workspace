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
	<title>工程部工人安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#workerCode").openComponent_worker();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CustWorkerApp/goWorkerDetailJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "workercode", index: "workercode", width: 80, label: "工人", sortable: true, align: "left",hidden:true},
			{name: "workercodedescr", index: "workercodedescr", width: 80, label: "工人", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种", sortable: true, align: "left"},
			{name: "issigndescr", index: "issigndescr", width: 75, label: "签约类型", sortable: true, align: "left"},
			{name: "zjts", index: "zjts", width: 80, label: "在建套数", sortable: true, align: "right"},
			{name: "efficiency", index: "efficiency", width: 80, label: "工作效率", sortable: true, align: "right"},
			{name: "num", index: "num", width: 80, label: "班组人数", sortable: true, align: "right"},
			{name: "level", index: "level", width: 80, label: "工人等级", sortable: true, align: "left"},
			{name: "monthts", index: "monthts", width: 100, label: "本月完成套数", sortable: true, align: "right"},
			{name: "yearts", index: "yearts", width: 100, label: "本年完成套数", sortable: true, align: "right"}
		],
		ondblClickRow:function(rowid,iRow,iCol,e){
			var ret = selectDataTableRow();
			if(ret){
				Global.Dialog.showDialog("View",{
					title:"在建工地——查看",
					url:"${ctx}/admin/worker/goViewOnDoDetail",
					postData:{code:ret.workercode},
					height:600,
					width:850,
					returnFun:goto_query
				});
			}else{
				art.dialog({
					content:"请选择一条数据",
				});
			}
        }
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/worker/goViewOnDoDetail",
				postData:{code:ret.workercode},
				height:600,
				width:850,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#workType12_NAME").val('');
	$("#department1_NAME").val('');
	$("#workType12").val('');
	$("#department1").val('');
	
	$("#dateTo").val('');
	$("#dateFrom").val('');
	$("#address").val('');
	$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
} 


</script>
</head>
	<body>
		<div class="body-box-list">
<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
        <div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label>工种</label>
								<house:DictMulitSelect id="workType12" dictCode="" sql="select code,descr from tWorkType12 where 1=1 " 
								sqlLableKey="descr" sqlValueKey="code" selectedValue="${custWorkerApp.workType12 }" ></house:DictMulitSelect>
							</li>
							<li>
								<label>楼盘片区</label>
								<house:dict id="region" dictCode="" sql="select Code,rtrim(Code)+'  '+Descr  Descr from tregion where 1=1  " 
								sqlValueKey="code" sqlLableKey="descr"  value="${custWorkerApp.region }"></house:dict>
							</li>
							<li>
								<label>工人</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorkerApp.workerCode }" />
							</li>
							<li>
								<label>在建数小于</label>
								<input type="text" id="onDo" name="onDo" style="width:160px;" value="${custWorkerApp.onDo}" />
							</li>
							<li>
								<label>工人等级</label>
								<house:xtdm  id="workerLevel" dictCode="WORKERLEVEL"   style="width:px;" value="${custWorkerApp.workerLevel}"></house:xtdm>
							</li>
							<li>
								<label>签约类型</label>
								<house:xtdm  id="isSign" dictCode="WSIGNTYPE"   style="width:px;" value="${custWorkerApp.isSign}"></house:xtdm>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
				</div>
				</div>
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
    			  <!-- <button type="button" class="btn btn-system "  id="view"><span>查看</span> 
								</button> -->
							<button type="button" class="btn btn-system " onclick="doExcelNow('工人申请班组总汇')" title="导出当前excel数据" >
								<span>导出excel</span>
				</div>	
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</body>	
</html>
