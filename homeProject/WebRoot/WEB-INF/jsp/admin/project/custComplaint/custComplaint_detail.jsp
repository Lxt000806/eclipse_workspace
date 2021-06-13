<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>客户投诉管理查看明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/custComplaint/doComplaintDetailExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/custProblem/promType","promType1","promType2");
		Global.LinkSelect.initSelect("${ctx}/admin/custProblem/promRsn","promType1","promRsn");	
		$("#crtCZY").openComponent_employee();
		
		$("#supplCode").openComponent_supplier();	
		$("#openComponent_supplier_supplCode").attr("readonly",true);
		
		$("#empCode").openComponent_employee();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "source", index: "source", width: 84, label: "问题来源", sortable: true, align: "left"},
				{name: "tsdate", index: "tsdate", width: 84, label: "投诉日期", sortable: true, align: "left", formatter: formatTime},
				{name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left"},
				{name: "desidpt1", index: "desidpt1", width: 73, label: "一级部门", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 76, label: "设计师", sortable: true, align: "left"},
				{name: "projectmandpt2", index: "projectmandpt2", width: 94, label: "工程部", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 111, label: "项目经理", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 192, label: "投诉内容", sortable: true, align: "left"},
				{name: "dealremarks", index: "dealremarks", width: 215, label: "处理结果", sortable: true, align: "left"},
				{name: "dealczydescr", index: "dealczydescr", width: 76, label: "接收人", sortable: true, align: "left"},
				{name: "dealdate", index: "dealdate", width: 78, label: "实际处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "statusdescr", index: "statusdescr", width: 86, label: "是否处理", sortable: true, align: "left"},
				{name: "compltypedescr", index: "compltypedescr", width: 75, label: "问题类型", sortable: true, align: "left"},
				{name: "promtype1descr", index: "promtype1descr", width: 97, label: "问题分类", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 90, label: "客户类型", sortable: true, align: "left"},
				{name: "promtype2descr", index: "promtype2descr", width: 91, label: "材料分类", sortable: true, align: "left"},
				{name: "promrsndescr", index: "promrsndescr", width: 90, label: "原因", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 126, label: "供应商", sortable: true, align: "left"},
				{name: "infodate", index: "infodate", width: 78, label: "通知时间", sortable: true, align: "left", formatter: formatDate},
				{name: "plandealdate", index: "plandealdate", width: 78, label: "计划处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "desidpt2", index: "desidpt2", width: 75, label: "设计部", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 78, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "custcheckdate", index: "custcheckdate", width: 101, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 147, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 94, label: "操作标志", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left", hidden: true}
			],
			ondblClickRow: function(){
				view();
			}
		});
		
		window.goto_query = function(){
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',url:"${ctx}/admin/custComplaint/goComplaintDetailJqGrid",postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
		}
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li>
							<label>工程部</label>
							<house:department2 id="department2" dictCode="03"></house:department2>
						</li>
						<li>
							<label>状态</label>
							<house:xtdm id="status" dictCode="COMPSTATUS"  style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>问题来源</label>
							<input type="text" id="source" name="source" style="width:160px;"/>
						</li>
						<li>
							<label>投诉时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
							<label>供应商</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"/>
						</li>
						<li>
							<label>问题分类	</label>
							<select type="text" id="promType1" name="promType1" style="width:160px;"></select>
						</li>
						<li>
							<label>材料分类</label>
							<select type="text" id="promType2" name="promType2" style="width:160px;"></select>
						</li>
						<li>
							<label>问题原因</label>
							<select type="text" id="promRsn" name="promRsn" style="width:160px;"></select>
						</li>
						<li>
							<label>问题类型</label>
							<house:xtdm id="complType" dictCode="COMPLTYPE" style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>登记人</label>
							<input type="text" id="crtCZY" name="crtCZY" style="width:160px;"/>
						</li>
						<li class="search-group">					
							<input type="checkbox"  id="expired_show" name="expired_show"
							 value="${custComplaint.expired }" onclick="hideExpired(this)" 
							 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
