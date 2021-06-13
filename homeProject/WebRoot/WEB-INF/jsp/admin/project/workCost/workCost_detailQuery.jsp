<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础人工成本明细查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_worker.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_workCard.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#salaryType").val("");
			$("#workType1").val("");
			$("#workType2").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/preWorkCostDetail/workTypeByAuthority","workType1","workType2");
		Global.LinkSelect.setSelect({firstSelect:'workType1',
							firstValue:'${workCostDetail.workType1}',
							secondSelect:'workType2',
							secondValue:'${workCostDetail.workType2}'
							});	
		Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workCostDetail/goJqGrid2",
		//postData:{status:"0,2"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		onSortColEndFlag:true,
		colModel : [
				{name: "cmpdescr", index: "cmpdescr", width: 100, label: "分公司", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 100, label: "申请明细ID", sortable: true, align: "left", hidden: true},
				{name: "no", index: "no", width: 80, label: "申请编号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 75, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "refaddress", index: "refaddress", width: 120, label: "关联楼盘", sortable: true, align: "left"},
				{name: "applymandescr", index: "applymandescr", width: 55, label: "申请人", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 106, label: "申请人二级部门", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 45, label: "状态", sortable: true, align: "left"},
				{name: "salarytypedescr", index: "salarytypedescr", width: 95, label: "工人工资类型", sortable: true, align: "left"},
				{name: "worktype1descr", index: "worktype1descr", width: 98, label: "申请工种分类1", sortable: true, align: "left"},
				{name: "worktype2descr", index: "worktype2descr", width: 98, label: "申请工种分类2", sortable: true, align: "left"},
				{name: "appamount", index: "appamount", width: 80, label: "申请金额", sortable: true, align: "right", sum: true},
				{name: "confirmamount", index: "confirmamount", width: 80, label: "审核金额", sortable: true, align: "right", sum: true},
				{name: "qualityfee", index: "qualityfee", width: 75, label: "质保金", sortable: true, align: "right", sum: true},
				{name: "realamount", index: "realamount", width: 80, label: "实发金额", sortable: true, align: "right", sum: true},
				{name: "issigndescr", index: "issigndescr", width: 80, label: "是否签约", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 85, label: "凭证号", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 55, label: "审核人", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 140, label: "请款说明", sortable: true, align: "left"},
				{name: "confirmremark", index: "confirmremark", width: 140, label: "审核说明", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "actname", index: "actname", width: 50, label: "户名", sortable: true, align: "left"},
				{name: "paydate", index: "paydate", width: 90, label: "签字日期", sortable: true, align: "left", formatter: formatDate}
		],
			});
		$("#applyMan").openComponent_employee();
		$("#workerCode").openComponent_worker();
		$("#actName").openComponent_workCard();
		});
		
		function doExcel(){
			var url = "${ctx}/admin/workCostDetail/doExcel";
			doExcelAll(url);
		}
	</script>
</head>
<body >
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" value="${workCostDetail.address}" />
					</li>
					<li><label>关联楼盘</label> <input type="text" id="refAddress"
						name="refAddress" value="${workCostDetail.refAddress}" />
					</li>
					<li><label>申请人</label> <input type="text" id="applyMan"
						name="applyMan" style="width:160px;"
						value="${workCostDetail.applyMan }" />
					</li>
					<li><label>工资类型</label> <house:dict id="salaryType"
							dictCode=""
							sql="select rtrim(Code)+' '+Descr fd,Code from tSalaryType order by Code"
							sqlValueKey="Code" sqlLableKey="fd"
							value="${workCostDetail.salaryType}">
						</house:dict>
					</li>
					<li><label>申请工种1</label> <select id="workType1"
						name="workType1"></select>
					</li>
					<li><label>申请工种2</label> <select id="workType2"
						name="workType2"></select>
					</li>
					<li><label>户名</label> <input type="text" id="actName"
						name="actName" style="width:160px;"
						value="${workCostDetail.actName }" />
					</li>
					<li><label>申请日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>工人编号</label> <input type="text" id="workerCode"
						name="workerCode" style="width:160px;"
						value="${workCostDetail.workerCode }" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="WorkCostStatus"
							selectedValue="${workCostDetail.status}"></house:xtdmMulit>
					</li>
					<li><label>请款说明</label> <input type="text" id="remarks"
						name="remarks" style="width:160px;"
						value="${workCostDetail.remarks }" />
					</li>
					<li><label>申请编号</label> <input type="text" id="no"
						name="no" style="width:160px;"/>
					</li>
					<li><label>凭证号</label> <input type="text" id="documentNo"
						name="documentNo" style="width:160px;"/>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm" >
				<button type="button" class="btn btn-system" id="excel"
					onclick="doExcel()">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager" style="height:25px;width:1000px"></div>
		</div>
	</div>
</body>
</html>
