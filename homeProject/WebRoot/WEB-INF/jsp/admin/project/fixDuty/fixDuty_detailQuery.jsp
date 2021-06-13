<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>工人定责管理——明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}"type="text/javascript"></script>
	<script type="text/javascript">
	var selectTableId="dataTable_dutyMan";
	function doExcel(){
		var url = "${ctx}/admin/fixDuty/doDetailQueryExcel";
		if(selectTableId == "dataTable_detail"){
			url = "${ctx}/admin/fixDuty/doDetailExcel";
		}
		doExcelAll(url,selectTableId);
	}
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable_dutyMan",{
			url:"${ctx}/admin/fixDuty/goDetailQueryJqGrid",
			// postData:{faultType:'1'},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name: "no", index: "no", width: 88, label: "定责申请单号", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "定责状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 149, label: "楼盘", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 96, label: "客户编号", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种", sortable: true, align: "left"},
				{name: "workername", index: "workername", width: 75, label: "工人", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 149, label: "工人卡号", sortable: true, align: "left"},
				{name: "dutymannamechi", index: "dutymannamechi", width: 80, label: "判责人", sortable: true, align: "left"},
				{name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 200, label: "申请说明", sortable: true, align: "left"},
				{name: "faulttypedescr", index: "faulttypedescr", width: 100, label: "责任人类型", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right"},
				{name: "riskfund", index: "riskfund", width: 80, label: "风险金支付", sortable: true, align: "right"},
				{name: "paidamount", index: "paidamount", width: 70, label: "已支付", sortable: true, align: "right"},
				{name: "empnamechi", index: "empnamechi", width: 70, label: "员工姓名", sortable: true, align: "left"},
				{name: "positiontypedescr", index: "positiontypedescr", width: 70, label: "职位类型", sortable: true, align: "left"},
				{name: "workernamechi", index: "workernamechi", width: 70, label: "工人姓名", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
				{name: "issalarydescr", index: "issalarydescr", width: 70, label: "正常工资", sortable: true, align: "left"},
			],
		});
		Global.JqGrid.initJqGrid("dataTable_detail",{
			url:"${ctx}/admin/fixDuty/goDetailJqGrid",
			postData:{type:'1'},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name: "no", index: "no", width: 88, label: "定责申请单号", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "定责状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "dutymandescr", index: "dutymandescr", width: 70, label: "判责人", sortable: true, align: "left"},
				{name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
				{name: "appdate", index: "appdate", width: 120, label: "申请时间", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 150, label: "申请说明", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 70, label: "项目编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 80, label: "项目名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right"},
				{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
				{name: "cfmqty", index: "cfmqty", width: 90, label: "专员确认数量", sortable: true, align: "right"},
				{name: "offerpri", index: "offerpri", width: 70, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 70, label: "材料单价", sortable: true, align: "right"},
				{name: "remark", index: "remark", width: 150, label: "描述", sortable: true, align: "left"},
				{name: "providedate", index: "providedate", width: 120, label: "发放时间", sortable: true, align: "left", formatter: formatTime},
				{name: "totalamount", index: "totalamount", width: 80, label: "合计总价", sortable: true, align: "right"},
				{name: "designmanname", index: "designmanname", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "designmandepartment1descr", index: "designmandepartment1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "designerbearamount", index: "designerbearamount", width: 80, label: "设计师承担金额", sortable: true, align: "right"},
				{name: "designerriskfund", index: "designerriskfund", width: 80, label: "使用风控基金", sortable: true, align: "right"},
				{name: "totaldiscbalance", index: "totaldiscbalance", width: 80, label: "总优惠余额", sortable: true, align: "right"},
			],
		});
		$("#empCode").openComponent_employee();
		$("#dutyMan").openComponent_employee();
		$("#workerCode").openComponent_worker();
		$("#supplCode").openComponent_supplier();
		
		$("#dataTable_detailPager").width("auto");
	});
	
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
		$("#faultType").val("");
		$.fn.zTree.getZTreeObj("tree_faultType").checkAllNodes(false);
	}
	
	function changeTab(tableId){
		selectTableId = tableId;
		if(tableId == "dataTable_dutyMan"){
			$(".dutyManOnly").removeClass("hidden");
			$(".detailOnly").addClass("hidden");
		}else{
			$(".dutyManOnly").addClass("hidden");
			$(".detailOnly").removeClass("hidden");
		}
	}
	
	function doQuery(){
		if($("#type").val() == "" && selectTableId == "dataTable_detail"){
			art.dialog({
				content	:"定责来源必填",			
			});
			return;
		}
		goto_query(selectTableId);
	}
	
	</script>
</head>
<body>
	<div class="body-box-list">
				<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-sm btn-system" onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
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
						<label for="status">定责状态</label>
						<house:xtdm id="status" dictCode="FIXSTATUS" style="width:160px;" value=""/>
					</li>
					<li><label>判责人</label> <input type="text" id="dutyMan"
						name="dutyMan" />
					</li>
					<li>
						<label>判责时间从 </label>
						<input type="text" style="width:160px;" id="dutyDateFrom" name="dutyDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="dutyDateTo" name="dutyDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					
					<li class="detailOnly hidden">
						<label>申请时间从 </label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li class="detailOnly hidden">
						<label>至</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li class="detailOnly hidden">
						<label>发放时间从 </label>
						<input type="text" style="width:160px;" id="provideDateFrom" name="provideDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li class="detailOnly hidden">
						<label>至</label>
						<input type="text" style="width:160px;" id="provideDateTo" name="provideDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					
					<li class="dutyManOnly"><label>责任人类型</label> <house:xtdmMulit id="faultType" 
						dictCode="FAULTTYPE" selectedValue=""></house:xtdmMulit>
					</li>
					<li class="dutyManOnly"><label>员工</label> <input type="text" id="empCode"
						name="empCode" />
					</li>
					<li class="dutyManOnly"><label>工人</label> <input type="text"
						id="workerCode" name="workerCode" />
					</li>
					<li class="dutyManOnly"><label>供应商</label> <input type="text"
						id="supplCode" name="supplCode" />
					</li>
					<li><label>定责来源</label> 
						<house:xtdm id="type" dictCode="FIXTYPE" style="width:160px;" value="1"/>
					</li>
					<li>
						<label>楼盘地址</label> 
						<input type="text" id="address" name="address" />
					</li>
					
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="doQuery();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<ul class="nav nav-tabs" role="tablist">
		    <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab" onclick="changeTab('dataTable_dutyMan')">定责人员</a></li>
		    <li role="presentation"><a href="#second" aria-controls="second" role="tab" data-toggle="tab" onclick="changeTab('dataTable_detail')">定责项目</a></li>
		</ul>
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="first">
				<div class="pageContent">
					<div id="content-list">
						<table id="dataTable_dutyMan"></table>
						<div id="dataTable_dutyManPager"></div>
					</div>
				</div> 
			</div>
			<div role="tabpanel" class="tab-pane" id="second">
				<div class="pageContent">
					<div id="content-list">
						<table id="dataTable_detail"></table>
						<div id="dataTable_detailPager"></div>
					</div>
				</div> 
			</div>
		</div> 
	</div>
</body>
</html>
