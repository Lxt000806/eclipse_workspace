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
	var dateFrom = $.trim($("#dateFrom").val());
	var dateTo = $.trim($("#dateTo").val());
	
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/custWorker/goViewSignJqGrid",
		postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val()},
		height:$(document).height()-$("#content-list").offset().top-72,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",},
			{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种类型", sortable: true, align: "left"},
			{name: "workerdescr", index: "workerdescr", width: 75, label: "工人名称", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 120, label: "签到时间", sortable: true, align: "left",formatter: formatTime},
			{name: "prjitem2descr", index: "prjitem2descr", width: 80, label: "施工阶段", sortable: true, align: "left"},
			{name: "isend", index: "isend", width: 80, label: "是否完成", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "no", sortable: true, align: "left",hidden:true},
			{name: "photonum", index: "photonum", width: 80, label: "图片数量", sortable: true, align: "right"},
			{name: "custscore", index: "custscore", width: 80, label: "客户评分", sortable: true, align: "right"},
			{name: "custremarks", index: "custremarks", width: 220, label: "客户评价", sortable: true, align: "right"},
			{name: "custcode", index: "custcode", width: 80, label: "custcode", sortable: true, align: "left",hidden:true},
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
	
	$("#viewAll").on("click",function(){
		var ret = selectDataTableRow("dataTable");
       	  if (ret) {	
           	Global.Dialog.showDialog("ViewAllGDYS",{ 
            	  title:"图片审核",
            	  url:"${ctx}/admin/custWorker/goViewAllPic",
            	  postData:{no:ret.no,custCode:ret.custcode},
            	  height: 800,
            	  width:1200,
            	  returnFun:goto_query
            	});
          } else {
          	art.dialog({
      			content: "请选择一条记录"
      		});
          }
	});

	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/custWorker/goViewSignJqGrid"}).trigger("reloadGrid");
	}
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
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
							<li>
								<label>工种</label>
								<house:dict id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  a.expired ='F' and (
								(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
								or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
									select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
									(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
									 )  " sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
							</li>
							<li>
								<label>工人</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" />
							</li>
							<li>
								<label>签到时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.dateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.dateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>工人分类</label>
								<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"/>
							</li>
							<li >
								<label>分组</label>
								<house:DictMulitSelect id="workType12Dept" dictCode="" sql="select Code, Descr from tWorkType12Dept where expired='F'"
								sqlLableKey="descr" sqlValueKey="code"></house:DictMulitSelect>
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
				<button type="button" class="btn btn-system " id="viewAll" style="float:left">
					<span>查看图片</span>
				</button>	
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</body>	
</html>
