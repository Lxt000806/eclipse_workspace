<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>材料结算分析-主材明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">

function goto_query(){
	if ($("#custCheckDateFrom").val() == "") {
		art.dialog({content: "请输入结算开始时间！", width: 200});
		return false;
	}
	
	if ($("#custCheckDateTo").val() == "") {
		art.dialog({content: "请输入结算截至时间！", width: 200});
		return false;
	}
	
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/customerCljsfx/goJqGrid",
		postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
}

$(function(){
	$("#mainBusinessMan").openComponent_employee();

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		postData:{custType:"1"},
		styleUI: 'Bootstrap',
		colModel : [
			{name: "dept1descr", index: "dept1descr", width: 80, label: "事业部", sortable: true, align: "left", count: true},
			{name: "dept2descr", index: "dept2descr", width: 80, label: "设计部门", sortable: true, align: "left"},
			{name: "address", index: "address", width: 160, label: "楼盘地址", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
			{name: "custcheckdate", index: "custcheckdate", width: 80, label: "结算日期", sortable: true, align: "left",formatter:formatDate},
			{name: "mainplandescr", index: "mainplandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "平方数", sortable: true, align: "right", sum: true},
			{name: "mainfee", index: "mainfee", width: 90, label: "主材合同额", sortable: true, align: "right", sum: true},
			{name: "mainchg", index: "mainchg", width: 85, label: "主材增减", sortable: true, align: "right", sum: true},
			{name: "maincheck", index: "maincheck", width: 85, label: "主材结算", sortable: true, align: "right", sum: true},
			{name: "tileamount", index: "tileamount", width: 85, label: "瓷砖", sortable: true, align: "right", sum: true},
			{name: "flooramount", index: "flooramount", width: 85, label: "地板", sortable: true, align: "right", sum: true},
			{name: "bathamount", index: "bathamount", width: 85, label: "卫浴", sortable: true, align: "right", sum: true},
			{name: "dooramount", index: "dooramount", width: 85, label: "门", sortable: true, align: "right", sum: true},
			{name: "ceilingamount", index: "ceilingamount", width: 85, label: "集成吊顶", sortable: true, align: "right", sum: true},
			{name: "switchamount", index: "switchamount", width: 85, label: "开关", sortable: true, align: "right", sum: true},
			{name: "otheramount", index: "otheramount", width: 85, label: "其他", sortable: true, align: "right", sum: true},
			{name: "maincost", index: "maincost", width: 85, label: "主材成本", sortable: true, align: "right", sum: true},
			{name: "maingiftamount", index: "maingiftamount", width: 100, label: "主材赠送金额", sortable: true, align: "right", sum: true, title: "赠送项目定义：单价 * 折扣 < 成本"},
			{name: "mainprofit", index: "mainprofit", width: 120, label: "主材每平方毛利", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainprofitperdescr", index: "mainprofitperdescr", width: 100, label: "主材毛利率", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainnetprofit", index: "mainnetprofit", width: 120, label: "主材每平方净利", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainprofitratedescr", index: "mainprofitratedescr", width: 120, label: "主材实际净利率", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainnet", index: "mainnet", width: 100, label: "主材净利额", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "maincommiamount", index: "maincommiamount", width: 100, label: "主材提成", sortable: true, align: "right", sum: true},
			{name: "mainservfee", index: "mainservfee", width: 120, label: "服务性产品预算", sortable: true, align: "right", sum: true},
			{name: "servchg", index: "servchg", width: 120, label: "服务性产品增减", sortable: true, align: "right", sum: true},
			{name: "servcheck", index: "servcheck", width: 120, label: "服务性产品结算", sortable: true, align: "right", sum: true},
			{name: "servswitchamount", index: "servswitchamount", width: 90, label: "服务性开关", sortable: true, align: "right", sum: true},
			{name: "servstonamount", index: "servstonamount", width: 90, label: "服务性石材", sortable: true, align: "right", sum: true},
			{name: "servotheramount", index: "servotheramount", width: 90, label: "服务性其他", sortable: true, align: "right", sum: true},
			{name: "servcost", index: "servcost", width: 110, label: "服务性产品成本", sortable: true, align: "right", sum: true},
			{name: "servgiftamount", index: "servgiftamount", width: 110, label: "服务性赠送金额", sortable: true, align: "right", sum: true, title: "赠送项目定义：单价 * 折扣 < 成本"},
			{name: "servprofit", index: "servprofit", width: 150, label: "服务性产品每平方毛利", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servprofitperdescr", index: "servprofitperdescr", width: 150, label: "服务性产品毛利率", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servnetprofit", index: "servnetprofit", width: 150, label: "服务性产品每平方净利", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servprofitratedescr", index: "servprofitratedescr", width: 150, label: "服务性产实际净利率", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servnet", index: "servnet", width: 120, label: "服务性产品净利额", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servcommiamount", index: "servcommiamount", width: 110, label: "服务性产品提成", sortable: true, align: "right", sum: true},
			{name: "commiamount", index: "commiamount", width: 80, label: "总提成", sortable: true, align: "right", sum: true},
			{name: "grossprofitratedescr", index: "grossprofitratedescr", width: 100, label: "实际净利率", sortable: true, align: "right"},
			{name: "insetcheckcost", index: "commiamount", width: 100, label: "套内结算成本", sortable: true, align: "right", sum: true},
			{name: "insetitemcost", index: "insetitemcost", width: 100, label: "套内材料成本", sortable: true, align: "right", sum: true},
			{name: "outsetsaleamount", index: "outsetsaleamount", width: 100, label: "套外销售金额", sortable: true, align: "right", sum: true},
			{name: "outsetitemcost", index: "outsetitemcost", width: 100, label: "套外材料成本", sortable: true, align: "right", sum: true},
	     ],
	     rowNum:100000, 
	     pager :'1',   
	     gridComplete:function(){
	     	//如果没有毛利查看权限，隐藏每平方净利、实际净利率、毛利额、实际毛利率这几列，包括主材和服务性产品
	     	if ("${canViewProfit}" == "false") {
	     		$("#dataTable").jqGrid("hideCol", "mainprofit")
	     					   .jqGrid("hideCol", "servprofit")
	     					   .jqGrid("hideCol", "mainprofitratedescr")
	     					   .jqGrid("hideCol", "maingrossprofit")
	     					   .jqGrid("hideCol", "servgrossprofit")
	     					   .jqGrid("hideCol", "grossprofitratedescr");
	     	}
	     }
	});
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customerCljsfx/doExcel";
	doExcelAll(url);
} 

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
						</li>
						<li>
						   <label>结算时间</label>
						   <input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>主材管家</label>
							<input type="text" id="mainBusinessMan" name="mainBusinessMan" value="${customer.mainBusinessMan }" />
						</li>
						<li id="loadMore" >
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
		          <li>
				     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				  </li>
			  </div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
</body>
</html>


