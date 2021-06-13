<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>供应商结算分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/custCheckAnalysis/doExcel";
		doExcelAll(url);
	}
	function goto_query(){
		if($.trim($("#confirmDateFrom").val())==''){
				art.dialog({content: "结算开始日期不能为空",width: 200});
				return false;
		} 
		if($.trim($("#confirmDateTo").val())==''){
				art.dialog({content: "结算结束日期不能为空",width: 200});
				return false;
		}
	     var dateStart = Date.parse($.trim($("#confirmDateFrom").val()));
	     var dateEnd = Date.parse($.trim($("#confirmDateTo").val()));
	     if(dateStart>dateEnd){
	    	 art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
				return false;
	     } 
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1");
      //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/splCheckAnalysis/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			postData: {
				confirmDateFrom: $("#confirmDateFrom").val(),
				confirmDateTo: $("#confirmDateTo").val(),
				isSetItem: $("#isSetItem").val(),
				isIncludePurchIn: $("#isIncludePurchIn").val()
			},
			colModel : [
				{name: "splcode", index: "splcode", width: 100, label: "splCode", sortable: true, align: "left",hidden: true},
				{name: "splcodedescr", index: "splcodedescr", width: 100, label: "供应商", sortable: true, align: "left"},
				{name: "sumamount", index: "sumamount", width: 100, label: "结算额", sortable: true, align: "right", sum: true},
				{name: "sumsaleamount", index: "sumsaleamount", width: 100, label: "销售额", sortable: true, align: "right", sum: true},
				{name: "giftamount", index: "giftamount", width: 100, label: "赠送金额", sortable: true, align: "right", sum: true, title: "赠送项目定义：单价 * 折扣 < 成本"},
				{name: "rate", index: "rate", width: 100, label: "毛利率", sortable: true, align: "right", formatter:function(cellvalue, options, rowObject){return Math.round(myRound(cellvalue,2)*100)+"%";}, title: "毛利率定义：（销售额 + 赠送金额 - 结算额） / （销售额 + 赠送金额）"},
			],
			gridComplete: function(){					
				var sumamount = parseFloat($("#dataTable").footerData("get","sumamount").sumamount);
				var sumsaleamount = parseFloat($("#dataTable").footerData("get","sumsaleamount").sumsaleamount);
				var giftAmount = parseFloat($("#dataTable").footerData("get","giftamount").giftamount);
				var result = 0;
				if(sumsaleamount != 0){
					result = (sumsaleamount + giftAmount - sumamount) / (sumsaleamount + giftAmount);
				}
				$("#dataTable").footerData("set",{rate: result});
			} 	
		});
		
		$("#splCode").openComponent_supplier();
	});
	function goView(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		if($.trim($("#confirmDateFrom").val())==''){
				art.dialog({content: "结算开始日期不能为空",width: 200});
				return false;
		} 
		if($.trim($("#confirmDateTo").val())==''){
				art.dialog({content: "结算结束日期不能为空",width: 200});
				return false;
		}
		var dateStart = Date.parse($.trim($("#confirmDateFrom").val()));
		var dateEnd = Date.parse($.trim($("#confirmDateTo").val()));
		if(dateStart>dateEnd){
			art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
			return false;
		}
		Global.Dialog.showDialog("splCheckAnalysisView", {
	   	  title: "供应商结算分析—查看",
	   	  url: "${ctx}/admin/splCheckAnalysis/goView",
	   	  height: 550,
	   	  width: 1000,
	   	  postData: {
	   	  	confirmDateFrom: $("#confirmDateFrom").val(),
	   	  	confirmDateTo: $("#confirmDateTo").val(),
			splCode: row.splcode,
			isSetItem: $("#isSetItem").val(),
			isIncludePurchIn: $("#isIncludePurchIn").val(),
			isService: $("#isService").val()
	   	  }
	   	}); 	
	}
	
	function doExcel(){
		var url = "${ctx}/admin/splCheckAnalysis/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>结算日期从</label>
						<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${data.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${data.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>供应商</label>
						<input type="text" id="splCode" name="splCode"/>
					</li>
					<li>
						<label>是否套餐</label>
						<house:xtdm id="isSetItem" dictCode="YESNO"></house:xtdm>
					</li>
					<li>
                        <label>是否服务性产品</label>
                        <house:xtdm id="isService" dictCode="YESNO"/>
                    </li>
					<li>
						<label>包含采购入库</label>
						<house:xtdm id="isIncludePurchIn" dictCode="YESNO" headerLabel="" value="0"></house:xtdm>
					</li>
					<li >
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>	
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="SPLCHECKANALYSIS_VIEW">
						<button type="button" class="btn btn-system " onclick="goView()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SPLCHECKANALYSIS_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</house:authorize>
				</div>
			</div>	
			<!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


