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
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
				 		<li class="form-validate">
							<label>预算金额</label>
							<input type="text" id="planFee" name="planFee" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>增减金额</label>
							<input type="text" id="chgFee" name="chgFee" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>套内销售额</label>
							<input type="text" id="saleAmount_Set" name="saleAmount_Set" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>累计销售额</label>
							<input type="text" id="saleAmount" name="saleAmount" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>领料成本</label>
							<input type="text" id="itemAppCost" name="itemAppCost" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>安装费成本</label>
							<input type="text" id="installCost" name="installCost" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>合计成本</label>
							<input type="text" id="cost" name="cost" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label>利润率</label>
							<input type="text" id="profPerNow" name="profPerNow" style="width:160px;" readonly="readonly">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custInvoice_save" data-toggle="tab">领料成本</a></li>  
			    <li><a href="#custPay_tabView" data-toggle="tab">安装费成本</a></li>
			</ul>
		    <div class="tab-content">  
				<div id="custInvoice_save"  class="tab-pane fade in active"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
				<div id="custPay_tabView"  class="tab-pane fade"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_installFee"></table>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	
	var resultJson = listStrToJson("${resultList}")[0];
	if("${isCupboard}"=="1"){
		$("#planFee").val(resultJson.CupPlanFee);
		$("#chgFee").val(resultJson.CupChgFee);
		$("#saleAmount_Set").val(resultJson.CupSaleAmount_Set);
		$("#saleAmount").val(resultJson.CupSaleAmount);
		$("#itemAppCost").val(resultJson.CupItemAppCost);
		$("#installCost").val(resultJson.CupInstallCost);
		$("#cost").val(resultJson.CupCost);
		$("#profPerNow").val(resultJson.CupProfPerNow);
	}else{
		$("#planFee").val(resultJson.IntPlanFee);
		$("#chgFee").val(resultJson.IntChgFee);
		$("#saleAmount_Set").val(resultJson.IntSaleAmount_Set);
		$("#saleAmount").val(resultJson.IntSaleAmount);
		$("#itemAppCost").val(resultJson.IntItemAppCost);
		$("#installCost").val(resultJson.IntInstallCost);
		$("#cost").val(resultJson.IntCost);
		$("#profPerNow").val(resultJson.IntProfPerNow);
	}	
	var gridOption = {
		url:"${ctx}/admin/itemPreApp/goLlcbJqGrid",
		postData:{custCode:"${custCode}",isCupboard:"${isCupboard}"},
		sortable: true,
		height : 230,
		rowNum : 10000000,
		colModel : [
			{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 70, label: "领料类型", sortable: true, align: "left"},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left"},
			{name: "othercost", index: "othercost", width: 70, label: "其他费用", sortable: true, align: "right",sum:true},
			{name: "othercostadj", index: "othercostadj", width: 90, label: "其他费用调整", sortable: true, align: "right",sum:true},
			{name: "totalamount", index: "totalamount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true},
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	var gridOption_cupInstallFee ={
		url:"${ctx}/admin/itemPreApp/goCgazcbJqGrid",
		postData:{custCode:"${custCode}"},
		height:230,
		styleUI:"Bootstrap",
		colModel : [
			{name: "cupuphigh", index: "cupuphigh", width: 120, label: "橱柜吊柜延米", sortable: true, align: "right"},
			{name: "cupdownhigh", index: "cupdownhigh", width: 120, label: "橱柜地柜延米", sortable: true, align: "right"},
			{name: "cupinsfee", index: "cupinsfee", width: 120, label: "橱柜每延米安装费", sortable: true, align: "right"},
			{name: "totalfee", index: "totalfee", width: 70, label: "安装费", sortable: true, align: "right",sum:true}
		]
	};
	var gridOption_intInstallFee ={
		url:"${ctx}/admin/itemPreApp/goYgazcbJqGrid",
		postData:{custCode:"${custCode}"},
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
	
	var gridOption_reqInstallFee ={
		url:"${ctx}/admin/itemPreApp/goXqazcbJqGrid",
		postData:{custCode:"${custCode}",isCupboard:"${isCupboard}"},
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
	
	if("${intInsCalTyp}" == "1" && "${isCupboard}" == "0"){
		Global.JqGrid.initJqGrid("dataTable_installFee",gridOption_intInstallFee);
	}else if("${cupInsCalTyp}" == "1" && "${isCupboard}" == "1"){
		Global.JqGrid.initJqGrid("dataTable_installFee",gridOption_cupInstallFee);
	}else{
		Global.JqGrid.initJqGrid("dataTable_installFee",gridOption_reqInstallFee);
	}
	
});

</script>
</html>
