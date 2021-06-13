<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>集成解单管理——明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/specItemReq/doDetailQueryExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'JC',
			secondSelect:"itemType2",
			secondValue:'${specItemReq.itemType2}',
			thirdSelect:"itemType3",
			thirdValue:'${specItemReq.itemType3}'
		};
		Global.LinkSelect.setSelect(dataSet);
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/specItemReq/goDetailQueryJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
				{name: "intprodpkdescr", index: "intprodpkdescr", width: 80, label: "成品名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemtype3descr", index: "itemtype3descr", width: 80, label: "材料类型3", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right",sum:true,editable:true, editrules: {number:true, required:true}},
				{name: "price", index: "price", width: 60, label: "单价", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right",hidden:"${costRight}"=="1"?false:true},
				{name: "totalcost", index: "totalcost", width: 90, label: "成本总价", sortable: true, align: "right",sum:true,hidden:"${costRight}"=="1"?false:true},
				{name: "remark", index: "remark", width: 200, label: "描述", sortable: true, align: "left",editable:true},
				{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
	      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
	     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
	    		{name : "actionlog",index : "actionlog",width : 50,label:"操作",sortable : true,align : "left"},
	    		{name: "iaqty", index: "iaqty", width: 90, label: "领料数量", sortable: true, align: "right",hidden: true},
				{name: "intinstallfee", index: "intinstallfee", width: 80, label: "安装费单价", sortable: true, align: "right",hidden: true},
	    	],
			gridComplete:function(){
				if ($("#statistcsMethod").val() == '2'){
					$("#dataTable").jqGrid('hideCol', "intprodpkdescr");	
					$("#dataTable").jqGrid('hideCol', "itemcode");	
					$("#dataTable").jqGrid('hideCol', "itemdescr");	
					$("#dataTable").jqGrid('hideCol', "itemtype2descr");	
					$("#dataTable").jqGrid('hideCol', "price");	
					$("#dataTable").jqGrid('hideCol', "totalcost");
					$("#dataTable").jqGrid('hideCol', "cost");
					$("#dataTable").jqGrid('hideCol', "remark");	
					$("#dataTable").jqGrid('hideCol', "lastupdate");
					$("#dataTable").jqGrid('hideCol', "lastupdatedby");
					$("#dataTable").jqGrid('hideCol', "expired");
					$("#dataTable").jqGrid('hideCol', "actionlog");	
					$("#dataTable").jqGrid('showCol', "iaqty");
					$("#dataTable").jqGrid('showCol', "intinstallfee");	
				}else{
					$("#dataTable").jqGrid('showCol', "intprodpkdescr");	
					$("#dataTable").jqGrid('showCol', "itemcode");	
					$("#dataTable").jqGrid('showCol', "itemdescr");	
					$("#dataTable").jqGrid('showCol', "itemtype2descr");	
					$("#dataTable").jqGrid('showCol', "price");	
					$("#dataTable").jqGrid('showCol', "totalcost");
					$("#dataTable").jqGrid('showCol', "cost");
					$("#dataTable").jqGrid('showCol', "remark");	
					$("#dataTable").jqGrid('showCol', "lastupdate");
					$("#dataTable").jqGrid('showCol', "lastupdatedby");
					$("#dataTable").jqGrid('showCol', "expired");
					$("#dataTable").jqGrid('showCol', "actionlog");	
					$("#dataTable").jqGrid('hideCol', "iaqty");
					$("#dataTable").jqGrid('hideCol', "intinstallfee");
				}
				
			}
		});	
	});
	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-sm btn-system"
						onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li hidden="true"><label>材料类型1</label> <select id="itemType1"
						name="itemType1" style="width: 160px;" value="JC"></select>
					</li>
					<li><label>材料类型2</label> <select id="itemType2" name="itemType2"
						style="width: 160px;"></select>
					</li>
					<li hidden="true"><label>材料类型2</label> <input
						id="itemType2Descr" name="itemType2Descr" style="width: 160px;" />
					</li>
					<li><label>材料类型3</label> <select id="itemType3"
						name="itemType3" style="width: 160px;"></select>
					</li>
					<li>
						<label>是否橱柜</label>
						<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" value="${specItemReq.isCupboard}"/>
					</li>
					<li><label>修改日期从 </label> <input type="text"
						style="width:160px;" id="dateFrom" name="dateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" style="width:160px;"
						id="dateTo" name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li> 
					    <label>查询方式</label>
						<select id="statistcsMethod" name="statistcsMethod"  >
							<option value="1">明细查询</option>
							<option value="2">按材料类型3汇总</option>
						</select>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
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
