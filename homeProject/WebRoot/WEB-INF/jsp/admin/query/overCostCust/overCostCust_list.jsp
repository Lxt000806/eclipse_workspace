<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>成本超额工地查询</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">

//导出EXCEL
function doExcel(){
	//导出EXCEL
	doExcelNow("成本超额查询","dataTable","page_form");
}
function goto_query(){
	if($.trim($("#itemType1").val())==""){
		art.dialog({content:"材料类型1不能为空",width:200});
		return false;
	} 
	if($.trim($("#itemType1").val())=="ZC"&&$.trim($("#isServiceItem").val())==""){
		art.dialog({content:"是否服务性产品不能为空",width:200});
		return false;
	}  
	if($.trim($("#itemType1").val())=="RZ"&&$.trim($("#itemType12").val())==""){
		art.dialog({content:"材料分类不能为空",width:200});
		return false;
	}  		
	if($.trim($("#monthNum").val())==''){
		art.dialog({content:"统计月份不能为空",width:200});
		return false;
	} 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/overCostCust/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}
function itemType1Change(){
	if($.trim($("#itemType1").val())=="ZC"){
		$("#isServiceItem_show").show(); 	
	}else{
		$("#isServiceItem_show").hide();	
		$("#isServiceItem").val("");
	}
	if($.trim($("#itemType1").val())=="RZ"){
		$("#itemType12_show").show(); 	
	}else{
		$("#itemType12_show").hide();	
		$("#itemType12").val("");
	}
}				
/**初始化表格*/
$(function(){
	$("#isServiceItem_show").hide();
	$("#itemType12_show").hide();
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1"); 
    var optionSelect=$("#itemType1 option");
    var sValue=""
    optionSelect.each(function (i,e) {
    	sValue= $(e).text().replace(/[^a-z]+/ig,"");
        if(sValue!="ZC" &&sValue!="JC"&&sValue!="RZ"){
            $(this).hide();
        }
    });
	var postData=$("#page_form").jsonForm();
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "address", index:"address", width: 140, label:"楼盘", sortable: true, align: "left", count:true},
			{name: "custcheckdate", index:"custcheckdate", width: 80, label:"结算日期", sortable: true, align: "left",formatter: formatDate}, 
		 	{name: "custtypedescr", index:"custtypedescr", width: 80, label:"客户类型", sortable: true, align: "left"},
			{name: "itemtype1descr", index:"itemtype1descr", width: 80, label:"材料类型1", sortable: true, align: "left"},
			{name: "isservicedescr", index: "isservicedescr", width: 110, label:"是否服务性产品", sortable: true, align: "left"},
			{name: "itemtype12descr", index: "itemtype12descr", width: 75, label:"材料分类", sortable: true, align: "left"},
			{name: "planamount", index: "planamount", width: 85, label:"预算金额", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "chgamount", index: "chgamount", width: 85, label:"增减金额", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "allamount", index: "allamount", width: 85, label:"总额", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "contrlineamount", index: "contrlineamount", width: 85, label:"控制线", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "lastmonthpayout", index: "lastmonthpayout", width: 110, label:"截止上月总支出", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "lastmonthbalance", index: "lastmonthbalance", width: 85, label:"上月余额", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "thismonthpayout", index: "thismonthpayout", width: 85, label:"本月支出", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "thismonthbalance", index: "lhismonthbalance", width: 85, label:"本月超出", sortable: true,sorttype:"float", align: "right", sum: true},
			{name: "overcost", index: "overcost", width: 85, label:"总超出", sortable: true,sorttype:"float", align: "right", sum: true},
	    ],
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',   
	    gridComplete:function(){
	        if ($.trim($("#itemType1").val())== "ZC") {
	        	$("#dataTable").jqGrid('showCol', "isservicedescr");
	        	$("#dataTable").jqGrid('hideCol', "itemtype12descr"); 
			}else if ($.trim($("#itemType1").val()) =="RZ") {
	        	$("#dataTable").jqGrid('hideCol', "isservicedescr");
	        	$("#dataTable").jqGrid('showCol', "itemtype12descr"); 
			}else{
				$("#dataTable").jqGrid('hideCol', "isservicedescr");
	        	$("#dataTable").jqGrid('hideCol', "itemtype12descr");
			}
		}
       	
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> <input type="hidden" name="jsonString"
					value="" />
				<ul class="ul-form">
					<li><label>材料类型1</label> <select id="itemType1" name="itemType1" onchange="itemType1Change()"></select>
					</li>
					<li>
					<li id="isServiceItem_show"><label>是否服务性产品</label> <house:xtdm id="isServiceItem" dictCode="YESNO"></house:xtdm>
					<li>
					<li id="itemType12_show"><label>材料分类</label> <select id="itemType12" name="itemType12">
							<option value="">请选择</option>
							<option value="01">窗帘</option>
							<option value="02">家具</option>
							<option value="03">软装其他</option>
					</select>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li><label>统计月份</label> <input type="text" id="monthNum" name="monthNum" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})"
						value="<fmt:formatDate value="${customer.monthNum}" pattern='yyyy-MM'/>" />
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="OVERCOSTCUST_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


