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
	<title>工种施工情况分析表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
	function doExcel(){
		var url = "${ctx}/admin/waterCtrlRep/doExcel";
		doExcelAll(url);
	}
	var oldStatisticalMethods="1";
	var colModelByAddress = [
		{name:'workerdescr',index:'workerdescr',width:90,label:'工人',sortable:true,align:"left" ,},
//		{name:'CustCode',index:'CustCode',width:90,label:'客户编号',sortable:true,align:"left" ,},
		{name:'documentno',index:'documentno',width:90,label:'档案号',sortable:true,align:"left" ,},
		{name:'address',index:'address',width:150,label:'楼盘',sortable:true,align:"left" ,},
		{name:'regiondescr',index:'regiondescr',width:60,label:'片区',sortable:true,align:"left" ,},
		{name:'waterctrlnum',index:'waterctrlnum',width:90,label:'已发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlnotsendnum',index:'waterctrlnotsendnum',width:90,label:'未发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlflaxnum',index:'waterctrlnum',width:140,label:'高柔性材料已发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlnotsendflaxnum',index:'waterctrlnotsendnum',width:140,label:'高柔性材料未发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlarea',index:'waterctrlarea',width:90,label:'防水施工面积',sortable:true,align:"right",sum:true},
		{name:'waterctrlflaxarea',index:'waterctrlflaxarea',width:120,label:'高柔性防水施工面积',sortable:true,align:"right",sum:true},
		{name:'waterarea',index:'waterarea',width:90,label:'工人申报面积',sortable:true,align:"right",sum:true},
		{name:'isconfirmed',index:'isconfirmed',width:90,label:'质检确认',sortable:true,align:"left",},
	];
	var colModelByWorker = [
		{name:'workercode',index:'workercode',width:90,label:'工人姓名',sortable:true,align:"left",hidden:true},
		{name:'workerdescr',index:'workerdescr',width:90,label:'工人姓名',sortable:true,align:"left"},
		{name:'waterctrlnum',index:'waterctrlnum',width:100,label:'已发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlnotsendnum',index:'waterctrlnotsendnum',width:100,label:'未发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlflaxnum',index:'waterctrlflaxnum',width:120,label:'高柔性材料已发货数量',sortable:true,align:"right",sum:true},
		{name:'waterctrlnotsendflaxnum',index:'waterctrlnotsendflaxnum',width:120,label:'高柔性材料未发货数量',sortable:true,align:"right",sum:true},
		{name:'waterarea',index:'waterarea',width:100,label:'工人申报面积',sortable:true,align:"right",sum:true},
		{name:'waterctrlarea',index:'waterctrlarea',width:100,label:'防水施工面积',sortable:true,align:"right",sum:true},
		{name:'waterctrlflaxarea',index:'waterctrlflaxarea',width:120,label:'高柔性防水施工面积',sortable:true,align:"right",sum:true},
		{name:'standardmum',index:'standardmum',width:100,label:'防水标准组数',sortable:true,align:"right",sum:true},
		{name:'wateraward',index:'wateraward',width:100,label:'防水节省金额',sortable:true,align:"right",sum:true},
		{name:'flxstandardmum',index:'flxstandardmum',width:120,label:'柔性防水标准组数',sortable:true,align:"right",sum:true},
		{name:'flxwateraward',index:'flxwateraward',width:120,label:'柔性防水节省金额',sortable:true,align:"right",sum:true},
	];
	var jqGridOption = {
		url:"${ctx}/admin/waterCtrlRep/goJqGrid",
		styleUI: 'Bootstrap', 
	};
	$(function(){
		$("#workerCode").openComponent_worker();
	 	var dateFrom =$.trim($("#dateFrom").val());
	 	var dateTo =$.trim($("#dateTo").val());
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",$.extend(jqGridOption,{
			colModel:colModelByAddress,
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-100,
			page:1,
		}));

		$("#clear").on("click", function () {
			$("#statisticalMethods").val("1");	
		});
	});
	function goto_query(tableId){
		if (!tableId || typeof(tableId)!="string"){
			tableId = "dataTable";
		}
		var postDataNew = $("#page_form").jsonForm(),
			colModel = colModelByAddress;
		if ("" == postDataNew.statisticalMethods) {
			art.dialog({
				content: "统计方式不允许为空",
				time: 1200,
			});
			return false;
		}
		$(".s-ico").css("display","none");
		//根据统计方式更换统计列
		if (postDataNew.statisticalMethods == oldStatisticalMethods) {
			$("#"+tableId).jqGrid("setGridParam",{
				postData:postDataNew,
				page:1,
				sortname:''
			}).trigger("reloadGrid");
		} else {
			if ("2" == postDataNew.statisticalMethods) {
				colModel = colModelByWorker;
			} else {
				colModel = colModelByAddress;
			}
			$.jgrid.gridUnload(tableId);
			Global.JqGrid.initJqGrid(tableId,$.extend(jqGridOption,{
				colModel:colModel,
				postData:postDataNew,
				height:$(document).height()-$("#content-list").offset().top-100,
				page:1,
				sortname:''
			}));
			oldStatisticalMethods = postDataNew.statisticalMethods;
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>进场时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${custWorker.dateFrom}' pattern='yyyy-MM-dd '/>" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${custWorker.dateTo}' pattern='yyyy-MM-dd '/>" />
							</li>
							<li>
								<label for="workCode">工人编号</label>
								<input type="text" id="workerCode" name="workerCode">
							</li>
							<li>
								<label for="statisticalMethods">统计方式</label>
								<select name="statisticalMethods" id="statisticalMethods">
									<option value="1">按楼盘</option>
									<option value="2">按工人</option>
								</select>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " 
									onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " 
									id="clear" onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
				<!-- panelBar -->
				<div class="btn-panel" >
				  	<div class="btn-group-sm"  >
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</div>
				</div>
				<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
