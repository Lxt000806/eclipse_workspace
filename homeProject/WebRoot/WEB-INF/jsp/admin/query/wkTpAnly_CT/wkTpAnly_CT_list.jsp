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
	<title>工种施工分析按工地类型</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: 'Bootstrap', 
			sortable: true,
			colModel : [
				{name: "ConstructType", index: "ConstructType", width: 80, label: "工地类型", sortable: true, align: "left",hidden:true},
				{name: "工地类型", index: "工地类型", width: 80, label: "工地类型", sortable: true, align: "left",},
				{name: "户型分类", index: "户型分类", width: 80, label: "户型分类", sortable: true, align: "left"},
				{name: "面积", index: "面积", width: 77, label: "面积", sortable: true, align: "left"},
				{name: "施工套数", index: "施工套数", width: 73, label: "施工套数", sortable: true, align: "right", sum: true},
				{name: "平均施工天数", index: "平均施工天数", width: 100, label: "平均施工天数", sortable: true, align: "right"},
				{name: "按时完成套数", index: "按时完成套数", width: 100, label: "按时完成套数", sortable: true, align: "right", sum: true},
				{name: "按时完成率", index: "按时完成率", width: 100, label: "按时完成率", sortable: true, align: "right",formatter : DiyFmatter},
				{name: "超10天", index: "超10天", width: 87, label: "超10天以下", sortable: true, align: "right",sum: true},
				{name: "超20天", index: "超20天", width: 90, label: "超20天以下", sortable: true, align: "right", sum: true},
				{name: "超30天", index: "超30天", width: 97, label: "超30天以下", sortable: true, align: "right", sum: true},
				{name: "超30天以上", index: "超30天以上", width: 80, label: "超30天以上", sortable: true, align: "right",sum: true},
			],
			    loadonce: true,
		};
		function DiyFmatter (cellvalue, options, rowObject){ 
		    return myRound(cellvalue*100,2)+"%";
		}
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		window.goto_query = function(){
			var dateFrom=$.trim($("#dateFrom").val());
			var dateTo=$.trim($("#dateTo").val());
			if(dateFrom==''||dateTo==''){
				art.dialog({
					content:"查询时间不能为空",
				});
				return;
			}
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/WkTpAnly_CT/goJqGrid",}).trigger("reloadGrid");
		}
		
		$("#view").on("click",function(){
			var ret = selectDataTableRow();
			postData["constructType"]=ret.ConstructType;
			postData["dateFrom"]=$("#dateFrom").val();
			postData["dateTo"]=$("#dateTo").val();
			postData["code"]=$("#code").val();
			postData["layOut"]=ret.户型分类;
			postData["area"]=ret.面积;
			if (ret) {	
				Global.Dialog.showDialog("view",{ 
		       		title:"工种施工分析——按工地类型——"+ret.工地类型,
		       		url : "${ctx}/admin/WkTpAnly_CT/goView",
		       		postData:postData,
		       		height: 700,
		       		width:1150,
		      	});
			}else{
				art.dialog({
			    	content: "请选择一条记录"
			    });
		   	}
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/WkTpAnly_CT/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li class="form-validate">
							<label>工种</label>
							<house:dict id="code" dictCode="" 
							sql="select a.* from tWorkType12 a where a.expired='F' " sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
						</li>
						<li>	
							<label>统计开始从</label>
							<input type="text" id="dateFrom" name="dateFrom" onchange="datefc()" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${workType12.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${workType12.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>签约类型</label>
							<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
							sqlValueKey="Code" sqlLableKey="Descr"   selectedValue='1'></house:xtdmMulit>
						</li> 
						<li class="search-group">					
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>
						
					</ul>
			</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
							<button type="button" class="btn btn-system" onclick="doExcel()">
								<span>导出excel</span>
							</button>
							<button type="button" class="btn btn-system" id="view">
								<span>查看</span>
							</button>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
					
				</div> 
	</body>	
</html>
