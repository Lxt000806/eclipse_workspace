<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>采购退回确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-list">
		<div class="content-form">
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
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="P"/>
							<ul class="ul-form">
								<li>
									<label><span class="required">*</span> 采购订单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
								</li>	
								<li>
								<label>采购类型</label>
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
								</li>	
								<li>
								<label>单据状态</label>
									<house:xtdm id="status" dictCode="PURCHSTATUS"  value="CONFIRMED" disabled='true'></house:xtdm>                     
								</li>	
								<li>
								<label>下单员</label>
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
								</li>	
								<li>
								<label>采购日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="disable"/>
								</li>	
								<li>
								<label>到货日期</label>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>" disabled="disable"/>
								</li>	
								<li>
								<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;" disabled="disabled" value="${purchase.itemType1 }"></select>
									</select>
								</li>	
								<li>
								<label>材料总价</label>
									<input type="text" id="amount" name="amount"  style="width:160px;" 
											onblur="changeAmount()"  value="${purchase.amount }" readonly="readonly" />
								</li>	
								<li>
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</li>	
								<li>
								<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost } "readonly= 'readonl' />
								</li>	
								<li>
								<label>供应商编号</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</li>	
								<li>
								<label>其他费用调整</label>
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
								</li>	
								<li>
								<label>仓库编号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</li>	
								<li>
								<label>配送地点</label>
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="${ purchase.delivType}" disabled='true'></house:xtdm>                     
								</li>	
								<li>
								<label>是否结账</label>
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="${purchase.isCheckOut}" disabled='true'></house:xtdm>                     
								</li>	
								<li>
								<label>已付定金</label>
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</li>	
								<li>
								<label>结账单号</label>
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"  value="${purchase.checkOutNo }" readonly='readonly'/>
								</li>	
								<li>
								<label>已付到货款</label>
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</li>	
								<li>
								<label>销售单号</label>
									<input type="text" id="sino" name="sino" style="width:160px;" value="${purchase.sino }"/>
								</li>	
								<li>
								<label> 应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;"
									 		onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
								</li>	
								<li>
								<label> 原采购单号</label>
									<input type="text" id="oldPUNo" name="oldPUNo"  style="width:160px;"
									 		 value="${purchase.oldPUNo }"  readonly='readonly'/>
								</li>	
								<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
								</li>
							</ul>	
				</form>
				</div>
			</div>
			<div>
			
			</div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="view1">
									<span>查看</span></button>
						<button type="button" class="btn btn-system " id="Excel" title="导出检索条件数据">
									<span>导出excel</span></button>
				</div>	
			</div>
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购单明细</a></li>
		    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$("#tabs").tabs();
	$(function(){
		
	
		$("#custCode").openComponent_customer({showValue:'${purchase.custCode}',showLabel:'${purchase.address}',readonly: true});
		$("#whcode").openComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}',readonly: true});
		$("#applyMan").openComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
		$("#supplier").openComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
		$("#sino").openComponent_salesInvoice({showValue:'${purchase.sino}',showLabel:'',readonly: true});
		$("#oldPUNo").openComponent_purchase({showValue:'${purchase.oldPUNo}',readonly: true});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
		disabled:'true'
	};
	Global.LinkSelect.setSelect(dataSet);
	//初始化表格  
	
	var lastCellRowId;
	var gridOption = {	
			url:"${ctx}/admin/purchase/goPurchJqGrid",
			postData:{puno:'${purchase.no }',whCode:'${purchase.whcode }'},
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			colModel : [
				{name:'pk', 			index:'pk', 			width:80, 	label:'材料编号', 	sortable:true ,align:"left",hidden:true},
				{name:'puno', 			index:'puno', 			width:80, 	label:'材料编号', 	sortable:true ,align:"left",hidden:true},
				{name:'type', 			index:'type', 			width:80, 	label:'type', 	sortable:true ,align:"left",hidden:true},
				{name:'status', 		index:'status', 		width:80, 	label:'status', 	sortable:true ,align:"left",hidden:true},
				{name:'delivtype',		index:'delivtype',		width:197, 	label:'delivtype', 	sortable:true,align:"left",hidden:true},
				{name:'itcode', 		index:'itcode', 		width:80, 	label:'材料编号', 	sortable:true ,align:"left"},
				{name:'itdescr', 		index:'itdescr', 		width:250, 	label:'材料名称', 	sortable:true ,align:"left",count:true},
				{name:'sqlcodedescr',	index:'sqlcodedescr', 	width:60, 	label:'品牌', 	sortable:true ,align:"left"},
				{name:'color',			index:'color', 			width:60,	label:'颜色', 	sortable:true ,align:"left"},	
				{name:'allqty', 		index:'allqty',			width:70, 	label:'当前库存',	sortable:true ,align:"left",},
				{name:'unitprice',		index:'unitprice',		width:50, 	label:'单价', 	sortable:true,align:"left",hidden:true,sum:true},
				{name:'qtycal',			index:'qtycal',			width:66, 	label:'退回数量', 	sortable:true,align:"left",sum:true},
				{name:'arrivqty', 		index:'arrivqty', 		width:50, 	label:'已到货',	sortable:true ,align:"left",sum:true},
				{name:'unitprice', 		index:'unitprice', 		width:40, 	label:'单价', 	sortable:true ,align:"left"},
				{name:'unidescr', 		index:'unidescr', 		width:40, 	label:'单位', 	sortable:true ,align:"left"},
				{name:'amount', 		index:'amount', 		width:40, 	label:'总价', 	sortable:true ,align:"left"},
				{name:'remarks',		index:'remarks',		width:197, 	label:'备注', 	sortable:true,align:"left"},
				],
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);
			if('${costRight}'=='0'){
				jQuery("#dataTable").setGridParam().hideCol("unitprice").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
			}
			if('${costRight}'=='0'){
				document.getElementById('amount').setAttribute('type','password') ;
				document.getElementById('firstAmount').setAttribute('type','password') ;
				document.getElementById('secondAmount').setAttribute('type','password') ;
				document.getElementById('remainAmount').setAttribute('type','password') ;
			}
			//初始化采购单明细		
			if("${purchase.m_umState}"=="R"){
				jQuery("#dataTable").setGridParam().hideCol("arrivqty").trigger("reloadGrid");
			}
			//保存操作	
			$("#saveBtn").on("click",function(){
			var whCode= $.trim($("#whcode").val());
			var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
			var param= Global.JqGrid.allToJson("dataTable");
			var Ids =$("#dataTable").getDataIDs();
			if(Ids==null||Ids==''){
				art.dialog({
					content:'采购表无明细，进行操作',
				});
				return false;
			}
			$.ajax({
				url:'${ctx}/admin/purchase/whRight',
				type: 'post',
				data: {whCode:whCode},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
					if(obj){
						Global.Form.submit("dataForm","${ctx}/admin/purchase/doReturnCheckOut",param,function(ret){
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
					}else{
						art.dialog({
							content:'您没有该仓库权限'
						});
					}
			    }
			 });
		});
		
		//查看
	$("#view1").on("click",function(){
		var ret = selectDataTableRow();
	 	if(ret){
		Global.Dialog.showDialog("PurchaseView",{
			title:"查看采购信息",
			url:"${ctx}/admin/purchase/goPurchView?id=" + ret.pk,
			height:700,
			width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});  
	//输出到Excel
	$("#Excel").on("click",function(){
		Global.JqGrid.exportExcel("dataTable","${ctx}/admin/supplierPurchase/export","采购订单明细","targetForm");
	});
});


	</script>
</body>
</html>




