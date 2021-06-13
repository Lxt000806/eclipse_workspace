<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>材料签单统计-主材明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
       .SelectBG{
           background-color:red;
           }
</style>

<script type="text/javascript"> 
function goto_query(){
	/* if ($("#signDateFrom").val() == "") {
		art.dialog({content: "请输入签订开始时间！", width: 200});
		return false;
	}
	if ($("#signDateTo").val() == "") {
		art.dialog({content: "请输入签订截至时间！", width: 200});
		return false;
	} */
	if ($("#signDateFrom").val() == "" && $("#signDateTo").val() == "" && 
					$("#checkOutDateFrom").val() == "" && $("#checkOutDateTo").val() == "" ) {
		art.dialog({content: "请选择一个时间查询条件！", width: 200});
		return false;
	}
	
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/customerClqdtj/goJqGrid",
		postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
}

$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		showColumnTitle: true,
		colModel : [
			{name: "dept1descr", index: "dept1descr", width: 80, label: "事业部", sortable: true, align: "left", count: true},
			{name: "dept2descr", index: "dept2descr", width: 80, label: "设计部门", sortable: true, align: "left"},
			{name: "address", index: "address", width: 160, label: "楼盘地址", sortable: true, align: "left"},
			{name: "signdate", index: "signdate", width: 80, label: "签订日期", sortable: true, align: "left",formatter:formatDate},
			{name: "custcheckdate", index: "custcheckdate", width: 80, label: "结算日期", sortable: true, align: "left",formatter:formatDate},
			{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
			{name: "mainplandescr", index: "mainplandescr", width: 80, label: "主材管家", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "平方数", sortable: true, align: "right", sum: true},
			{name: "mainfee", index: "mainfee", width: 85, label: "主材合同额", sortable: true, align: "right", sum: true},
			{name: "tileamount", index: "tileamount", width: 85, label: "瓷砖", sortable: true, align: "right", sum: true},
			{name: "tilesupplsend", index: "tilesupplsend", width: 120, label: "瓷砖供应商发货", sortable: true, align: "right", sum: true},
			{name: "tilewhsend", index: "tilewhsend", width: 110, label: "瓷砖仓库发货", sortable: true, align: "right", sum: true},
			{name: "flooramount", index: "flooramount", width: 85, label: "地板", sortable: true, align: "right", sum: true},
			{name: "floorsupplsend", index: "floorsupplsend", width: 120, label: "地板供应商发货", sortable: true, align: "right", sum: true},
			{name: "floorwhsend", index: "floorwhsend", width: 120, label: "地板仓库发货", sortable: true, align: "right", sum: true},
			{name: "bathamount", index: "bathamount", width: 85, label: "卫浴", sortable: true, align: "right", sum: true},
			{name: "bathsupplsend", index: "bathsupplsend", width: 120, label: "卫浴供应商发货", sortable: true, align: "right", sum: true},
			{name: "bathwhsend", index: "bathwhsend", width: 110, label: "卫浴仓库发货", sortable: true, align: "right", sum: true},
			{name: "dooramount", index: "dooramount", width: 85, label: "门", sortable: true, align: "right", sum: true},
			{name: "ceilingamount", index: "ceilingamount", width: 85, label: "集成吊顶", sortable: true, align: "right", sum: true},
			{name: "switchamount", index: "switchamount", width: 85, label: "主材开关", sortable: true, align: "right", sum: true},
			{name: "otheramount", index: "otheramount", width: 85, label: "其他", sortable: true, align: "right", sum: true},
			{name: "maincost", index: "maincost", width: 85, label: "主材成本", sortable: true, align: "right", sum: true},
			{name: "maingiftamount", index: "maingiftamount", width: 100, label: "主材赠送金额", sortable: true, align: "right", sum: true, title: "赠送项目定义：单价 * 折扣 < 成本"},
			{name: "unitprofit", index: "unitprofit", width: 120, label: "主材每平方毛利", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainprofitratedescr", index: "mainprofitratedescr", width: 85, label: "主材毛利率", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "mainprofit", index: "mainprofit", width: 120, label: "主材毛利(扣费用)", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "grossprofit", index: "grossprofit", width: 110, label: "主材毛利润", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			{name: "servfee", index: "servfee", width: 115, label: "服务性产品预算", sortable: true, align: "right", sum: true},
	    	{name: "servswitchamount", index: "servswitchamount", width: 85, label: "服务性开关", sortable: true, align: "right", sum: true},
	     	{name: "stoneamount", index: "stoneamount", width: 85, label: "服务性石材", sortable: true, align: "right", sum: true},
	     	{name: "servotheramount", index: "servotheramount", width: 85, label: "服务性其他", sortable: true, align: "right", sum: true},
	     	{name: "servcost", index: "servcost", width: 85, label: "服务性成本", sortable: true, align: "right", sum: true},
	     	{name: "servgiftamount", index: "servgiftamount", width: 110, label: "服务性赠送金额", sortable: true, align: "right", sum: true, title: "赠送项目定义：单价 * 折扣 < 成本"},
	     	{name: "servunitprofit", index: "servunitprofit", width: 125, label: "服务性每平方毛利", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
	     	{name: "servprofitratedescr", index: "servprofitratedescr", width: 110, label: "服务性毛利率", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
	     	{name: "servprofit", index: "servprofit", width: 100, label: "服务性毛利额", sortable: true, align: "right", sum: true, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
	     	{name: "allprofitratedescr", index: "allprofitratedescr", width: 85, label: "总毛利率", sortable: true, align: "right", title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"}
	     			
	     ],
	      rowNum:100000,  
	      pager :'1',  
	     	gridComplete:function(){
				var ret = selectDataTableRow();
				if(ret){
					  var sumMainProfit=myRound($("#dataTable").getCol('mainprofit',false,'sum'));  
					  var sumServprofit=myRound($("#dataTable").getCol('servprofit',false,'sum'));  
		              var sumMainFee=myRound($("#dataTable").getCol('mainfee',false,'sum'));
		              var sumAllprofit=0;
		              if (sumMainFee>0){
		              	 var sumAllprofit=myRound((sumMainProfit+sumServprofit)*100/sumMainFee,2);
		              }   
		              $("#dataTable").footerData('set', {'allprofitratedescr': sumAllprofit+'%'});
				}			
	       },
	});
	
	/* var cmpCode = "${cmpCode }"; //公司代码 01.福州 02.厦门 03.长乐
	if (cmpCode == "02") { //02.厦门需分开统计统计铝合金门、木门，其他只需统计门
		$("#dataTable").jqGrid('hideCol', "dooramount"); //门
	} else {
		$("#dataTable").jqGrid('hideCol', "aluferdooramount"); //铝合金门
		$("#dataTable").jqGrid('hideCol', "wooddooramount"); //木门
	} */
	
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customerClqdtj/doExcel";
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
							<li>
								<label>客户类型</label>
								<house:xtdmMulit id="custType" dictCode="CUSTTYPE" selectedValue="${customer.custType}"/>
								<!-- 增加套餐客户的材料需求统计 modify by zb-->
								<!-- <input type="text" id="custTypeDescr" name="custTypeDescr" value="1 家装客户" readonly="readonly"/> -->
							</li>
							<li>
							   <label>签订时间</label>
							   <input type="text" id="signDateFrom"
							    name="signDateFrom"  class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="signDateTo" name="signDateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
							   <label>结算时间</label>
							   <input type="text" id="checkOutDateFrom" name="checkOutDateFrom"  
							   		class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.checkOutDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="checkOutDateTo" name="checkOutDateTo" 
									class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.checkOutDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>	
							<li>
								<label>是否套餐外材料</label>
								<house:xtdm id="isOutSet" dictCode="YESNO" style="width:160px;" value="1"/>
							</li>
							<li >
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>	
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				  </div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
