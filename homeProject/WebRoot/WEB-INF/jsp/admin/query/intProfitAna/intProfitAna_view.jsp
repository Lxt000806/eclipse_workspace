<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li class="form-validate"><label>衣柜预算金额</label> <input
							type="text" id="intPlanFee" name="intPlanFee"
							style="width:160px;" readonly="readonly"
							value="${map.intPlanFee}" />
						</li>
						<li><label>衣柜增减金额</label> <input type="text" id="intChgFee"
							name="intChgFee" style="width:160px;" readonly="readonly"
							value="${map.intChgFee}">
						</li>
						<li><label>衣柜套内销售额</label> <input type="text"
							id="intSaleAmount_Set" name="intSaleAmount_Set"
							style="width:160px;" readonly="readonly" value="${map.intSaleAmount_Set}">
						</li>
						<li><label>衣柜累计销售额</label> <input type="text"
							id="intSaleAmount" name="intSaleAmount" style="width:160px;"
							readonly="readonly" value="${map.intSaleFee}">
						</li>
						<li><label>衣柜已下单成本</label> <input type="text"
							id="intItemAppCost" name="intItemAppCost" style="width:160px;"
							readonly="readonly" value="${map.intItemAppCost}">
						</li>
						<li><label>衣柜安装费成本</label> <input type="text"
							id="intInstallCost" name="intInstallCost" style="width:160px;"
							readonly="readonly" value="${map.intInstallCost}">
						</li>
						<li><label>衣柜合计成本</label> <input type="text" id="intCost"
							name="intCost" style="width:160px;" readonly="readonly" value="${map.intCost}">
						</li>
						<li><label>衣柜利润率</label> <input type="text" id="intPerf"
							name="intPerf" style="width:160px;" readonly="readonly" value="${map.intPerf}">
						</li>

						<li class="form-validate"><label>橱柜预算金额</label> <input
							type="text" id="cupPlanFee" name="cupPlanFee"
							style="width:160px;" readonly="readonly" value="${map.cupPlanFee}"/>
						</li>
						<li><label>橱柜增减金额</label> <input type="text" id="cupChgFee"
							name="cupChgFee" style="width:160px;" readonly="readonly" value="${map.cupChgFee}">
						</li>
						<li><label>橱柜套内销售额</label> <input type="text"
							id="cupSaleAmount_Set" name="cupSaleAmount_Set"
							style="width:160px;" readonly="readonly" value="${map.cupSaleAmount_Set}">
						</li>
						<li><label>橱柜累计销售额</label> <input type="text"
							id="cupSaleAmount" name="cupSaleAmount" style="width:160px;"
							readonly="readonly" value="${map.cupSaleFee}">
						</li>
						<li><label>橱柜已下单成本</label> <input type="text"
							id="cupItemAppCost" name="cupItemAppCost" style="width:160px;"
							readonly="readonly" value="${map.cupItemAppCost}">
						</li>
						<li><label>橱柜安装费成本</label> <input type="text"
							id="cupInstallCost" name="cupInstallCost" style="width:160px;"
							readonly="readonly" value="${map.cupInstallCost}">
						</li>
						<li><label>橱柜合计成本</label> <input type="text" id="cupCost"
							name="cupCost" style="width:160px;" readonly="readonly" value="${map.cupCost}">
						</li>
						<li><label>橱柜利润率</label> <input type="text" id="cupPerf"
							name="cupPerf" style="width:160px;" readonly="readonly" value="${map.cupPerf}">
						</li>
						<li><label>实际人工费用</label> <input type="text" id="realOfferFee"
							name="realOfferFee" style="width:160px;" readonly="readonly" value="${map.realOfferFee}">
						</li>
						<li><label>总体利润率</label> <input type="text" id="totalPerf"
							name="totalPerf" style="width:160px;" readonly="readonly" value="${map.totalPerf}">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#tab_intItemAppCost" data-toggle="tab">衣柜领料成本</a></li>
			    <li><a href="#tab_intInstallCost" data-toggle="tab">衣柜安装费成本</a></li>
			    <li><a href="#tab_cupItemAppCost" data-toggle="tab">橱柜领料成本</a></li>
			    <li><a href="#tab_cupInstallCost" data-toggle="tab">橱柜安装费成本</a></li>
			</ul>
		    <div class="tab-content">  
				<div id="tab_intItemAppCost"  class="tab-pane fade in active"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_intItemAppCost"></table>
						</div>
					</div>
				</div>
				<div id="tab_intInstallCost"  class="tab-pane fade"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_intInstallCost"></table>
						</div>
					</div>
				</div> 
				<div id="tab_cupItemAppCost"  class="tab-pane fade"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_cupItemAppCost"></table>
						</div>
					</div>
				</div> 
				<div id="tab_cupInstallCost"  class="tab-pane fade"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_cupInstallCost"></table>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	var gridOption1 = {
		url:"${ctx}/admin/itemPreApp/goLlcbJqGrid",
		postData:{custCode:"${map.custcode}",isCupboard:"0"},
		sortable: true,
		height : 270,
		rowNum : 10000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 70, label: "领料类型", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "领料成本", sortable: true, align: "right",sum:true},
			{name: "othercost", index: "othercost", width: 70, label: "其他费用", sortable: true, align: "right",sum:true},
			{name: "othercostadj", index: "othercostadj", width: 90, label: "其他费用调整", sortable: true, align: "right",sum:true},
			{name: "totalamount", index: "totalamount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "operation", index: "operation", width: 70, label: "操作", sortable: true, align: "right", formatter:viewBtn},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_intItemAppCost",gridOption1);
	var gridOption2 = {
		url:"${ctx}/admin/itemPreApp/goLlcbJqGrid",
		postData:{custCode:"${map.custcode}",isCupboard:"1"},
		sortable: true,
		height : 270,
		rowNum : 10000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 70, label: "领料类型", sortable: true, align: "left"},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "领料成本", sortable: true, align: "right",sum:true},
			{name: "othercost", index: "othercost", width: 70, label: "其他费用", sortable: true, align: "right",sum:true},
			{name: "othercostadj", index: "othercostadj", width: 90, label: "其他费用调整", sortable: true, align: "right",sum:true},
			{name: "totalamount", index: "totalamount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "operation", index: "operation", width: 70, label: "操作", sortable: true, align: "right", formatter:viewBtn},
		],
	};
	Global.JqGrid.initJqGrid("dataTable_cupItemAppCost",gridOption2);
	
	var gridOption_cupInstallFee ={
		url:"${ctx}/admin/itemPreApp/goCgazcbJqGrid",
		postData:{custCode:"${map.custcode}"},
		height:270,
		styleUI:"Bootstrap",
		colModel : [
			{name: "cupuphigh", index: "cupuphigh", width: 120, label: "橱柜吊柜延米", sortable: true, align: "right"},
			{name: "cupdownhigh", index: "cupdownhigh", width: 120, label: "橱柜地柜延米", sortable: true, align: "right"},
			{name: "cupinsfee", index: "cupinsfee", width: 120, label: "橱柜每延米安装费", sortable: true, align: "right"},
			{name: "totalfee", index: "totalfee", width: 70, label: "安装费", sortable: true, align: "right",sum:true}
		]
	};
	var gridOption_reqCupInstallFee ={
		url:"${ctx}/admin/itemPreApp/goXqazcbJqGrid",
		postData:{custCode:"${map.custcode}",isCupboard:"1"},
		height:230,
		styleUI:"Bootstrap",
		colModel : [
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "板材类型", sortable: true, align: "left"},
			{name: "intinstallfee", index: "intinstallfee", width: 70, label: "单价", sortable: true, align: "right"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
			{name: "totalfee", index: "totalfee", width: 70, label: "安装费", sortable: true, align: "right",sum:true}
		]
	};
	if("${cupInsCalTyp}" == "1"){
		Global.JqGrid.initJqGrid("dataTable_cupInstallCost",gridOption_cupInstallFee);
	}else{
		Global.JqGrid.initJqGrid("dataTable_cupInstallCost",gridOption_reqCupInstallFee);
	}
	
	var gridOption_intInstallFee ={
		url:"${ctx}/admin/itemPreApp/goYgazcbJqGrid",
		postData:{custCode:"${map.custcode}"},
		height:270,
		styleUI:"Bootstrap",
		colModel : [
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "板材类型", sortable: true, align: "left"},
			{name: "intinstallfee", index: "intinstallfee", width: 70, label: "单价", sortable: true, align: "right"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right",sum:true},
			{name: "totalfee", index: "totalfee", width: 70, label: "安装费", sortable: true, align: "right",sum:true}
		]
	};
	var gridOption_reqIntInstallFee ={
		url:"${ctx}/admin/itemPreApp/goXqazcbJqGrid",
		postData:{custCode:"${map.custcode}",isCupboard:"0"},
		height:230,
		styleUI:"Bootstrap",
		colModel : [
			{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "板材类型", sortable: true, align: "left"},
			{name: "intinstallfee", index: "intinstallfee", width: 70, label: "单价", sortable: true, align: "right"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
			{name: "totalfee", index: "totalfee", width: 70, label: "安装费", sortable: true, align: "right",sum:true}
		]
	};
	if("${intInsCalTyp}" == "1"){
		Global.JqGrid.initJqGrid("dataTable_intInstallCost",gridOption_intInstallFee);
	}else{
		Global.JqGrid.initJqGrid("dataTable_intInstallCost",gridOption_reqIntInstallFee);
	}
	
	var gridOption_detail ={
		url:"${ctx}/admin/itemPreApp/goIntProDetailJqGrid",
		postData:{custCode:"${map.custcode}"},
		height:270,
		styleUI:"Bootstrap",
		colModel : [
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 120, label: "装修区域", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 70, label: "集成产品", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"}
		]
	};
	Global.JqGrid.initJqGrid("dataTable_detail",gridOption_detail);
});
function viewBtn(v,x,r){
	return "<button type='button' class='btn btn-system btn-xs' onclick='viewDetail(\""+r.no+"\")'>查看明细</button>";
}
function viewDetail(no){
	Global.Dialog.showDialog("viewDetail",{
		title:"集成利润率分析--查看",
		url:"${ctx}/admin/intProfitAna/goViewDetail?appNo="+no,
		height:500,
		width:680
	});
}   
</script>
</html>
