<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>集成利润率分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function goto_query(){
	var address=$("#address").val();
	var sendDateFrom=$("#sendDateFrom").val();
	var sendDateTo=$("#sendDateTo").val();
	var custCheckDateFrom=$("#custCheckDateFrom").val();
	var custCheckDateTo=$("#custCheckDateTo").val();
	var isCupboard=$("#isCupboard").val();
	
	if(address==""&&sendDateFrom==""&&sendDateTo==""&&custCheckDateFrom==""&&custCheckDateTo==""){
		art.dialog({content: "楼盘、发货日期、结算日期最少要选择一个！"});
		return false;
	}
	if(sendDateFrom=="" && sendDateTo=="" && isCupboard!=""){
		art.dialog({content: "请先选择发货日期才能选择是否橱柜！"});
		return false;
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/intProfitAna/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
    $("#custType").val('');
    $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}
//查看
function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({
	       	content: "请选择一条记录",
	    });
	    return;
	}
	Global.Dialog.showDialog("view",{
		title:"集成利润率分析--查看",
		url:"${ctx}/admin/intProfitAna/goView?custCode="+ret.custcode,
		height:700,
		width:1250,
		returnFun:goto_query
	});
}
//导出excel
function doExcel(){
	var url = "${ctx}/admin/intProfitAna/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			rowNum:10000000,
			colModel : [
			  {name : 'custcheckdate',index : 'custcheckdate',width : 80,label:'结算日期',sortable : true,align : 'left',formatter:formatTime,hidden:true} ,
			  {name : 'custcode',index : 'custcode',width : 65,label:'客户编号',sortable : true,align : 'left'} ,
			  {name : 'custdescr',index : 'custdescr',width : 60,label:'客户',sortable : true,align : 'left'} ,
			  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align :'left'},
			  {name : 'custtypedescr',index : 'custtypedescr',width : 65,label:'客户类型',sortable : true,align : 'left'},
		      {name : 'custstatusdescr',index : 'custstatusdescr',width : 70,label:'客户状态',sortable : true,align : 'left'},
		      {name : 'intsalefee',index : 'intsalefee',width : 85,label:'衣柜销售额',sortable : true,align : 'right',sum:true},
		      {name : 'intitemappcost', index: 'intitemappcost', width: 110, label: '衣柜已下单成本', sortable: true, align: 'right',sum:true},
		      {name : 'intinstallcost',index : 'intinstallcost',width : 90,label:'衣柜安装成本',sortable : true,align : 'right',sum:true},
		      {name : 'intperf', index: 'intperf', width:80 , label: '衣柜利润率', sortable: true, align: 'right',sum:true},
		      {name : 'cupsalefee',index : 'cupsalefee',width : 80,label:'橱柜销售额',sortable : true,align : 'right',sum:true},
		      {name : 'cupitemappcost', index: 'cupitemappcost', width: 110, label: '橱柜已下单成本', sortable: true, align: 'right',sum:true},
		      {name : 'cupinstallcost',index : 'cupinstallcost',width : 90,label:'橱柜安装成本',sortable : true,align : 'right',sum:true},
		      {name : 'cupperf', index: 'cupperf', width:80 , label: '橱柜利润率', sortable: true, align: 'right',sum:true},
		      {name : 'realofferfee', index: 'realofferfee', width:80 , label: '实发人工费', sortable: true, align: 'right',sum:true},
		      {name : 'totalperf', index: 'totalperf', width:80 , label: '总体利润率', sortable: true, align: 'right',sum:true},
		      {name : 'intplanfee',index : 'intplanfee',width : 80,label:'衣柜预算额',sortable : true,align : 'right',sum:true} ,
		      {name : 'integratedisc',index : 'integratedisc',width : 80,label:'衣柜优惠额',sortable : true,align : 'right',sum:true} ,
		      {name : 'cupplanfee',index : 'cupplanfee',width : 80,label:'橱柜预算额',sortable : true,align : 'right',sum:true} ,
		      {name : 'cupboarddisc',index : 'cupboarddisc',width : 80,label:'橱柜优惠额',sortable : true,align : 'right',sum:true} ,
		      {name : 'designmandescr',index : 'designmandescr',width : 80,label:'设计师',sortable : true,align : 'left'} ,
		      {name : 'designmandept1descr',index : 'designmandept1descr',width : 120,label:'设计师部门一',sortable : true,align : 'left'} ,
		      {name : 'designmandept2descr',index : 'designmandept2descr',width : 120,label:'设计师部门二',sortable : true,align : 'left'} ,
		      {name : 'intdesignmandescr',index : 'intdesignmandescr',width : 120,label:'集成设计师',sortable : true,align : 'left'} ,
		      {name : 'cupdesignmandescr',index : 'cupdesignmandescr',width : 120,label:'橱柜设计师',sortable : true,align : 'left'} ,
		    ],
		    gridComplete:function(){
	        	var intsalefee = parseFloat($("#dataTable").getCol('intsalefee', false, 'sum'));
	       	    var intitemappcost = parseFloat($("#dataTable").getCol('intitemappcost', false, 'sum'));
	       	    var intinstallcost = parseFloat($("#dataTable").getCol('intinstallcost', false, 'sum'));
	       	    var intperf = myRound((intsalefee-intitemappcost-intinstallcost)/intsalefee*100, 2);
	       	    $("#dataTable").footerData('set', {"intperf": intperf+"%"});
	       	    
	       	    var cupsalefee = $("#dataTable").getCol('cupsalefee', false, 'sum');
	       	    var cupitemappcost = $("#dataTable").getCol('cupitemappcost', false, 'sum');
	       	    var cupinstallcost = $("#dataTable").getCol('cupinstallcost', false, 'sum');
	       	    var cupperf = myRound((cupsalefee-cupitemappcost-cupinstallcost)/cupsalefee*100, 2);
	       	    $("#dataTable").footerData('set', {"cupperf": cupperf+"%"});
	       	    
	       	    var totalperf = myRound((cupsalefee+intsalefee-cupitemappcost-cupinstallcost-intitemappcost-intinstallcost)/(cupsalefee+intsalefee)*100, 2);
	       	    $("#dataTable").footerData('set', {"totalperf": totalperf+"%"});
	        }
		});
});

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="F" /> <input
					type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  ></house:custTypeMulit>
					</li>
					<li><label>发货日期从</label> <input type="text" id="sendDateFrom"
						name="sendDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="sendDateTo"
						name="sendDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>客户结算日期从</label> <input type="text"
						style="width:160px;" id="custCheckDateFrom"
						name="custCheckDateFrom" class="i-date"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>到</label> <input type="text" style="width:160px;"
						id="custCheckDateTo" name="custCheckDateTo" class="i-date"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${customer.sendDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
							dictCode="YESNO" style="width:160px;"></house:xtdm>
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
				<house:authorize authCode="INTPROFITANA_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				<li class="line"></li>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


