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
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text'][id!='type']").val("");
		$("select").val("");
	}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/workCostDetail/goCheckOutJqGrid",
			postData:{"type":"1"},
			height:$(document).height()-$("#content-list").offset().top-90,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
					{name: "workermemname", index: "workermemname", width: 100, label: "户名", sortable: true, align: "left"},
					{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种", sortable: true, align: "left"},
					{name: "cardid", index: "cardid", width: 150, label: "卡号", sortable: true, align: "left"},
					{name: "bank", index: "bank", width: 100, label: "发卡行", sortable: true, align: "left", },
					{name: "idnum", index: "idnum", width: 120, label: "身份证号", sortable: true, align: "left", },
					{name: "laborcmpdescr", index: "laborcmpdescr", width: 80, label: "劳务公司", sortable: true, align: "left"},
					{name: "cmpamount", index: "cmpamount", width: 90, label: "公司发放金额", sortable: true, align: "right",sum:true},
					{name: "cashamount", index: "cashamount", width: 90, label: "现金发放金额", sortable: true, align: "right",sum:true},
					{name: "groupleadername", index: "groupleadername", width: 80, label: "班组长姓名", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审批日期", sortable: true, align: "left", formatter: formatTime,hidden:true},
					{name: "paydate", index: "paydate", width: 90, label: "签字日期", sortable: true, align: "left", formatter: formatDate,hidden:true},
					{name: "no", index: "no", width: 80, label: "申请单号", sortable: true, align: "left",hidden:true},
					{name: "documentno", index: "documentno", width: 95, label: "凭证号", sortable: true, align: "left",hidden:true},
				],
			});
		});
		
		function changeType(){
			if($("#type").val()=="1"){
				$("#dataTable").setGridParam().hideCol("confirmdate");
			 	$("#dataTable").setGridParam().hideCol("paydate");
			 	$("#dataTable").setGridParam().hideCol("no");
			 	$("#dataTable").setGridParam().hideCol("documentno");
			}else{
				$("#dataTable").setGridParam().showCol("confirmdate");
			 	$("#dataTable").setGridParam().showCol("paydate");
			 	$("#dataTable").setGridParam().showCol("no");
			 	$("#dataTable").setGridParam().showCol("documentno");
			}
		}
		
		function doExcel(){
			var url = "${ctx}/admin/workCostDetail/doCheckOutExcel";
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
					<li>		
						<label>统计方式</label>
						<select id="type" name="type" onchange="changeType()">
							<option value="1" selected>按汇总</option>
							<option value="2">按明细</option>
						</select>
					</li>
					<li><label>凭证号</label> <input type="text" id="documentNo"
						name="documentNo" style="width:160px;"/>
					</li>
					<li><label>申请编号</label> <input type="text" id="no"
						name="no" style="width:160px;"/>
					</li>
					<li><label>劳务公司</label> <house:dict id="laborCmpCode" dictCode=""
							sql="select rtrim(Code)+' '+Descr fd,Code from tLaborCompny order by Code"
							sqlValueKey="Code" sqlLableKey="fd">
						</house:dict>
					</li>
					<li><label>审批日期从</label> <input type="text" id="confirmDateFrom"
						name="confirmDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="confirmDateTo"
						name="confirmDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>出纳签字日期从</label> <input type="text" id="payDateFrom"
						name="payDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="payDateTo"
						name="payDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
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
