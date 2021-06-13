<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料签单毛利分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function goto_query(){
	if($.trim($("#isServiceItem").val())==''){
			art.dialog({content: "是否服务性产品不能为空",width: 200});
			return false;
	} 
	if($.trim($("#itemType1").val())==''){
			art.dialog({content: "材料类型1不能为空",width: 200});
			return false;
	}
	if($.trim($("#itemType1").val())=='JC' && $.trim($("#isCupboard").val())==''){
			art.dialog({content: "是否橱柜不能为空",width: 200});
			return false;
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemProfitAnalysis/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
    $("#custType").val('');
    $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/itemProfitAnalysis/doExcel";
	doExcelAll(url);
}
   
function itemType1Change(){ 
   $("#itemCode").openComponent_item({condition:{itemType1:$.trim($("#itemType1").val())}});
}  

function fillData(data){
     $("#fixArea").openComponent_fixArea({condition:{custCode:data["code"]}});
	}
 
/**初始化表格*/
$(function(){
		$("#isServiceItem").attr("disabled",true);
		$("#isCupboard").attr("disabled",true);
        //初始化材料类型1，2，3三级联动
	    Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");	
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'documentno',index : 'documentno',width : 80,label:'档案号',sortable : true,align : 'left'} ,
			  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align :'left'},
			  {name : 'custtypedescr',index : 'custtypedescr',width : 75,label:'客户类型',sortable : true,align : 'left'},
		      {name : 'statusdescr',index : 'statusdescr',width : 75,label:'客户状态',sortable : true,align : 'left'},
		      {name : 'custtype',index : 'custtype',width : 75,label:'客户类型',sortable : true,align : 'left',hidden:true},
		      {name : 'status',index : 'status',width : 75,label:'客户状态',sortable : true,align : 'left',hidden:true},
		      {name : 'signdate', index: 'signdate', width: 75, label: '签订日期', sortable: true, align: 'left',formatter: formatDate},
		      {name : 'lineamount',index : 'lineamount',width : 75,label:'总价',sortable : true,align : 'right',sum:true},
		      {name : 'profit', index: 'profit', width:75 , label: '毛利', sortable: true, align: 'right',sum:true},
		      {name : 'profitper', index: 'profitper', width: 75, label: '毛利率', sortable: true, align: 'right'},
		    ],
		     gridComplete:function(){
	        	var lineamount = $("#dataTable").getCol('lineamount', false, 'sum');
	       	    var profit = $("#dataTable").getCol('profit', false, 'sum');
	       	    var profitper = myRound(profit/lineamount*100, 2);
	       	    $("#dataTable").footerData('set', {"profitper": profitper+"%"});
	         }
		});
});
function test(){
	if($.trim($("#itemType1").val())!='ZC'){
		$("#isServiceItem").val("0");
		$("#isServiceItem").attr("disabled",true);
	}else{
		$("#isServiceItem").removeAttr("disabled");
	} 
	
	if($.trim($("#itemType1").val())!='JC'){
		$("#isCupboard").val("");
		$("#isCupboard").attr("disabled",true);
	}else{
		$("#isCupboard").removeAttr("disabled");
	} 
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> <input
					type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" value="${custmer.itemType1}" onchange="test()"></select>
					</li>
					<li><label>是否服务性产品</label> <house:xtdm id="isServiceItem"
							dictCode="YESNO" style="width:160px;" 
							value="0"></house:xtdm>
					</li>
					<li><label>是否橱柜</label> <house:xtdm id="isCupboard" 
							dictCode="YESNO" style="width:160px;"
							></house:xtdm>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
					<li><label>客户状态</label> <house:xtdmMulit id="status"
							dictCode="CUSTOMERSTATUS" selectedValue="4"></house:xtdmMulit>
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;" />
					</li>
					<li><label>签订日期从</label> <input type="text" id="signDateFrom"
						name="signDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.signDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="signDateTo"
						name="signDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.signDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>套餐外材料</label> 
							<house:xtdm id="isOutSet" 
							dictCode="YESNO" style="width:160px;"
							></house:xtdm>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMPROFITANALYSIS_EXCEL">
					<li>
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</li>
				</house:authorize>
				<li class="line"></li>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


