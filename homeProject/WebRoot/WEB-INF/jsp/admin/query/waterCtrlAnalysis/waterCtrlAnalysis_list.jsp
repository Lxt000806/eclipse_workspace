<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>水电发包分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/waterCtrlAnalysis/doExcel";
		var tableId="dataTable";
		doExcelAll(url, tableId);
	}    
				
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{    					     
			height:$(document).height()-$("#content-list").offset().top-100,			
			styleUI: "Bootstrap",
			rowNum:10000000,
			colModel : [
				{name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 121, label: "楼盘地址", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "workername", index: "workername", width: 80, label: "工人姓名", sortable: true, align: "left"},
				{name: "innerarea", index: "innerarea", width: 95, label: "测算套内面积", sortable: true, align: "right",sum:true},
				{name: "realinnerarea", index: "realinnerarea", width: 95, label: "实际套内面积", sortable: true, align: "right",sum:true},
				{name: "confirmbegin", index: "confirmbegin", width: 90, label: "开工时间", sortable: true, align: "left",formatter: formatDate},
				{name: "workerplanoffer", index: "workerplanoffer", width: 95, label: "工人预算人工", sortable: true, align: "right",sum:true},
				{name: "appamount", index: "appamount", width: 80, label: "申请人工", sortable: true, align: "right",sum:true},
				{name: "confirmamount", index: "confirmamount", width: 80, label: "实发人工", sortable: true, align: "right",sum:true},
				{name: "fixamount", index: "fixamount", width: 80, label: "定责金额", sortable: true, align: "right",sum:true},
				{name: "realamount", index: "realamount", width: 80, label: "实收金额", sortable: true, align: "right",sum:true},
				{name: "workerplanmaterial", index: "workerplanmaterial", width: 95, label: "工人预算材料", sortable: true, align: "right",sum:true},
				{name: "clamount", index: "clamount", width: 95, label: "水电实际材料", sortable: true, align: "right",sum:true},
				{name: "actualothercost", index: "actualothercost", width: 95, label: "实际其他费用", sortable: true, align: "right",sum:true},
				{name: "dfcb", index: "dfcb", width: 95, label: "单方成本", sortable: true, align: "right",sum:true},
				{name: "clamountjc", index: "clamountjc", width: 95, label: "水电材料奖惩", sortable: true, align: "right",sum:true},
				{name: "cfmclamountjc", index: "cfmclamountjc", width: 120, label: "实发水电材料奖惩", sortable: true, align: "right",sum:true},
				{name: "projectplanmaterial", index: "projectplanmaterial", width: 120, label: "项目经理预算材料", sortable: true, align: "right",sum:true},
			],
			gridComplete:function(){
		        if ($("#statistcsMethod").val() == '1') {
		        	$("#dataTable").jqGrid('showCol', "code");
		       	 	$("#dataTable").jqGrid('showCol', "address");
		       	 	$("#dataTable").jqGrid('showCol', "custtypedescr");
		       		$("#dataTable").jqGrid('showCol', "confirmbegin");
				}else{	
		        	$("#dataTable").jqGrid('hideCol', "code");
		       	 	$("#dataTable").jqGrid('hideCol', "address");
		       	 	$("#dataTable").jqGrid('hideCol', "custtypedescr");
		       		$("#dataTable").jqGrid('hideCol', "confirmbegin");
				}
			  var sumDfcb=0;
		      var sumClamount=$("#dataTable").getCol('clamount',false,'sum'); 
		      var sumRealinnerarea=$("#dataTable").getCol('realinnerarea',false,'sum');
		      if (sumRealinnerarea!=0){
		      	sumDfcb=myRound(sumClamount/sumRealinnerarea,2);
		      }
			  $("#dataTable").footerData('set', {'dfcb': sumDfcb});	
			}
		});
		window.goto_query = function(){	
		    $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/waterCtrlAnalysis/goJqGrid",datatype:"json",postData:$("#page_form").jsonForm(),
		   	}).trigger("reloadGrid");
		};
		$("#workerCode").openComponent_worker();
	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#custType").val('');
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	}
	</script>	
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>水电完工时间从</label>
						<input type="text" id="endDateFrom" name="endDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${dateInfo.dateFrom }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="endDateTo" name="endDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${dateInfo.dateTo }' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label style="width:130px">水电工资发放时间从</label>
						<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						/>
					</li>
					<li>
						<label >套内面积从</label>
						<input type="text" id="areaFrom" name="areaFrom" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="areaTo" name="areaTo"/>
					</li>
					<li>
						<label  style="width:130px">客户类型</label>
						<house:custTypeMulit id="custType"></house:custTypeMulit>
					</li>
					<li>
						<label>工人</label>
						<input type="text" id="workerCode" name="workerCode"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address"/>
					</li>
					<li>
					 <label>统计方式</label>
						<select id="statistcsMethod" name="statistcsMethod"  >
							<option value="1">明细</option>
							<option value="2">工人汇总</option>
						</select>
					</li>
										
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();" >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
			<div id="content-list">               
				<table id= "dataTable"></table>  
			</div>			
		</div> 
	</div>
</body>
</html>


