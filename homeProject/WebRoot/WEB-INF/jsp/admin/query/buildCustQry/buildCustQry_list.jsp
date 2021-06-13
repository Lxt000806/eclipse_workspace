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
	<title>材料报价模板</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/buildCustQry/doExcel";
		doExcelAll(url);
	}
	
	$(function(){
		$("#builderCode").openComponent_builder();
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/buildCustQry/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "code", index: "code", width: 90, label: "编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 144, label: "楼盘", sortable: true, align: "left"},
				{name: "area", index: "area", width: 70, label: "面积", sortable: true, align: "right"},
				{name: "prjdescr", index: "prjdescr", width: 90, label: "当前进度", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 90, label: "客户类型", sortable: true, align: "left",},
				{name: "typedescr", index: "typedescr", width: 90, label: "施工方式", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 90, label: "合同总价", sortable: true, align: "right"},
				{name: "designdescr", index: "designdescr", width: 90, label: "设计师", sortable: true, align: "left"},
				{name: "designdesp2", index: "designdesp2", width: 90, label: "设计部", sortable: true, align: "left",},
				{name: "projectmandescr", index: "projectmandescr", width: 90, label: "项目经理", sortable: true, align: "left",},
				{name: "projectmandesp2", index: "projectmandesp2", width: 90, label: "工程部", sortable: true, align: "left",},
				{name: "statusdescr", index: "statusdescr", width: 90, label: "客户状态", sortable: true, align: "left",},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		function DiyFmatter (cellvalue, options, rowObject){ 
		    return cellvalue.substr(0,cellvalue.length-4)+'**'+cellvalue.substr(cellvalue.length-2);
		}
		
		$("#view").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/buildCustQry/goView",
				postData:{id:ret.code},
				height: 745,
				width:900,
				returnFun:goto_query
			});
		});
	});
	
	function query(){
		if($.trim($("#builderCode").val())==""){
			art.dialog({
				content:"项目名称必填",
			});
			return;
		} 
		
		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/buildCustQry/goJqGrid",postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
	}
	function includEndFun(obj){
		if ($(obj).is(':checked')){
			$('#includEnd').val('1');
		}else{
			$('#includEnd').val('0');
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="includEnd" name="includEnd" value="0"/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode"/>
							</li>
							<li>
								<label>楼栋号</label>
								<input type="text" id="builderNum" name="builderNum"/>
							</li>							
							<li class="search-group" >
								<input type="checkbox"  id="includEnd_cb" name="includEnd_cb" onclick="includEndFun(this)"/><p>包含完工</p>
								<button type="button" class="btn btn-sm btn-system" onclick="query();">查询</button>
								<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</div>	
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
  			<div class="btn-group-sm">
				<house:authorize authCode="BUILDCUSTQRY_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
			</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
