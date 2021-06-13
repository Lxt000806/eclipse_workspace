<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>基础人工成本汇总</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
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
		url:"${ctx}/admin/workCost/goJqGrid2",
		//postData:{status:"0,2"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		onSortColEndFlag:true,
		multiselect: true,
		colModel : [
				//{name: "isselected", index: "isselected", width: 60, label: "选择", sortable: true, align: "left"},
				{name: "no", index: "no", width: 85, label: "申请编号", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 62, label: "类型", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 55, label: "开单人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 80, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
				{name: "statusdescr", index: "statusdescr", width: 65, label: "状态", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 55, label: "审批人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 125, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
				{name: "documentno", index: "documentno", width: 75, label: "凭证号", sortable: true, align: "left"},
				{name: "payczydescr", index: "payczydescr", width: 70, label: "出纳签字人", sortable: true, align: "left"},
				{name: "paydate", index: "paydate", width: 80, label: "签字日期", sortable: true, align: "left", formatter: formatDate},
				{name: "issyscrtdescr", index: "issyscrtdescr", width: 80, label: "是否系统生成", sortable: true, align: "left"},
				{name: "commino", index: "commino", width: 90, label: "提成领取单号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 177, label: "备注", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 75, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 125, label: "修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作类型", sortable: true, align: "left"}
		],
		onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
				}, 
	});
		//查看账号汇总
		$("#view").on("click",function(){
		var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length==0){
				art.dialog({
	       			content: "请先选择要汇总的申请单！",
	       		});
	       		return;
			}
			var nos="";
			$.each(ids,function(i,id){
				var rowData = $("#dataTable").jqGrid("getRowData", id);
				nos+=rowData['no']+",";
			});
			if(nos.length>0)
			nos=nos.substring(0, nos.length-1);//去掉最后一个逗号
			Global.Dialog.showDialog("view",{
				title:"基础人工成本--按账号汇总",
				url:"${ctx}/admin/workCost/goWorkCard",
				postData:{'nos':nos},
				height:600,
				width:1000,
				returnFun:goto_query
			});
		});
	});
		function doExcel(){
		var url = "${ctx}/admin/workCost/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>凭证号</label> <input type="text" id="documentNo"
						name="documentNo" value="${workCost.documentNo}" />
					</li>
					<li><label>类型</label> <house:xtdm id="type"
							dictCode="WorkCostType" value="${workCost.type}"></house:xtdm>
					</li>
					<li><label></label> 
					</li>
					<li><label>审批日期从</label> <input type="text"
						id="confirmDateFrom" name="confirmDateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					
					<li><label>至</label> <input type="text" id="confirmDateTo"
						name="confirmDateTo" class="i-date" style="width:160px;"
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
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="view">
					<span>查看汇总</span>
				</button>
				<button type="button" class="btn btn-system" id="close" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
