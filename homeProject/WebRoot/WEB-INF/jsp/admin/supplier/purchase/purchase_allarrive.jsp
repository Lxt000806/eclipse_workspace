<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>全部到货</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function doExcel(){
	var url = "${ctx}/admin/supplierPurchase/doExcel";
	doExcelAll(url);
}
</script>

</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!-- panelBar -->
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
         <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<ul class="ul-form">
						<li>
							<label>采购订单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
						</li>
						<li>
							<label>采购类型</label>
								<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>采购单状态</label>
								<house:xtdm id="status" dictCode="PURCHSTATUS" value="${purchase.status}" disabled="true" ></house:xtdm>	
						</li>
						<li>
							<label>下单员</label>
								<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/> 
						</li>
						<li>
							<label>采购日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="true"/>
						</li>
						<li>
							<label>到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>"  disabled="true" />
						</li>
						<li>
							<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: px;" disabled="disabled" value="${purchase.itemType1 }"></select>
						</li>
						<li>
							<label>供应商编号</label>
								<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier } " readonly="readonly" />
						</li>
						<li>
							<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }">
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="2" readonly="readonly">${purchase.remarks }</textarea>
						</li>
					</ul>
				</form>
				</div>
				</div>
			<div>
			
			</div>
		<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">采购明细单</a></li>
	   	 </ul>

				<div id="content-list">
					<table id="dataTable"></table>
				</div>
	</div>
</div>
<script type="text/javascript">
	$("#tabs").tabs();
	
	$(function(){
	
		$("#whcode").openComponent_wareHouse({showValue:'${purchase.whcode}',condition: {czybh:'${czybh}',delXNCK:'1'}});
		$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}'});
		$("#applyMan").openComponent_employee();
		$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
	};
	Global.LinkSelect.setSelect(dataSet);
	
	//初始化表格  
	
	var lastCellRowId;
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
			colModel : [
			//{name:'puno',			index:'puno',			width:80,	label:'采购单号',	sortable:true,align:"label"},
			{name:'pk',				index:'pk',				width:80,	label:'PK',		sortable:true,align:"left",hidden:true},
			{name:'itcode', 		index:'itcode', 		width:80, 	label:'材料编号', 	sortable:true ,align:"left"},
			{name:'itdescr', 		index:'itdescr', 		width:210, 	label:'材料名称', 	sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr',	index:'sqlcodedescr', 	width:60, 	label:'品牌', 	sortable:true ,align:"left"},
			{name:'color',			index:'color', 			width:60,	label:'颜色', 	sortable:true ,align:"left"},	
			{name:'allqty', 		index:'allqty',			width:80, 	label:'当前库存',	sortable:true ,align:"left",},
			{name:'qtycal',			index:'qtycal',			width:80, 	label:'采购数量', 	sortable:true,align:"left",sum:true},
			{name:'unitprice',		index:'unitprice',		width:60, 	label:'单价', 	sortable:true,align:"left",hidden:true,sum:true},
			{name:'arrivqty', 		index:'arrivqty', 		width:60, 	label:'已到货',	sortable:true ,align:"left",sum:true},
			{name:'thearrivqty',	index:'thearrivqty',	width:80,	label:'本次到货',	sortable:true, align:"left",sum:true},
			{name:'unidescr', 		index:'unidescr', 		width:50, 	label:'单位', 	sortable:true ,align:"left"},
			{name:'remarks',		index:'remarks',		width:170, 	label:'备注', 	sortable:true,align:"left"},
				],
			};
	 		var detailJson = Global.JqGrid.allToJson("dataTable","puno");
			if($.trim($("#no").val())!=''){
				$.extend(gridOption,{
				url:"${ctx}/admin/supplierPurchase/goPurchJqGrid",
				postData:{puno:$.trim($("#no").val()),unSelected: detailJson["fieldJson"]}
				});
			}
			//初始化采购单明细		
			Global.JqGrid.initJqGrid("dataTable",gridOption);
			
			//保存操作	
			$("#saveBtn").on("click",function(){
			var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
			var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("dataForm","${ctx}/admin/supplierPurchase/doSave",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		});
			$("#jqgh_dataTable_thearrivqty").css("background-color","yellow");
			$("#jqgh_dataTable_thearrivqty").css("color","red");
	});  
    </script>
</body>
</html>




