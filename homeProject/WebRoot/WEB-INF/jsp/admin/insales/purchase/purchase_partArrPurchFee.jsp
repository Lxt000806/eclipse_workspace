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
	<title>部分到货</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
	.panelBar{background: url("");}
	.query-form .ul-form li, .edit-form .ul-form li{width: 288px;}
	.table tr td.success{background-color: rgb(25, 142, 222);}
</style>
<style type="text/css">
     .SelectBG{
         background-color:#198ede!important;
           color:rgb(255,255,255)
           }
     .SelectBG_yellow{
         background-color:yellow;
        }
</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<!-- <button type="button" class="btn btn-system " id="apportionFee" >
						<span>费用分摊</span>
					</button> -->
				</div>
			</div>
		</div>
  		<div class="panel panel-info" hidden="true" >  
			<div class="panel-body">
			  	<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="P"/>
					<input type="hidden" name="existsPurchFee" id="existsPurchFee" value="${existsPurchFee }"/>
					<input type="hidden" name="purchFeeNo" id="purchFeeNo" value=""/>
					<ul class="ul-form">
						<div class="validate-group row" hidden="true">
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
								<house:xtdm id="status" dictCode="PURCHSTATUS" value="${purchase.status}" disabled="true"></house:xtdm>	
							</li>
							<li>
							<label>下单员</label>
								<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
							</li>
						</div>
						<div class="validate-group row" hidden="true">
							<li>
							<label>采购日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
							</li>
							<li>
							<label>到货日期</label>
								<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
							<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;" value="${purchase.itemType1 }" disabled="disabled">
								</select>
							</li>
							<li>
							<label>材料总价</label>
								<input type="text" id="amount" name="amount"  style="width:160px;" 
										onblur="changeAmount()"  value="${purchase.amount }" readonly="readonly" />
							</li>
						</div>
						<div class="validate-group row" hidden="true">	
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
						</div>
						<div class="validate-group row" hidden="true">	
							<li class="form-validate">
							<label><span class="required">*</span> 仓库编号</label>
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
						</div>
						<div class="validate-group row" hidden="true">	
							<li>
							<label>结账单号</label>
								<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"   value="${purchase.checkOutNo }" readonly='readonly'/>
							</li>
							<li>
							<label>已付到货款</label>
								<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
							</li>
							<li>
							<label> 应收余款</label>
								<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;"
								 		onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
							</li>
						</div>
						<div class="validate-group row" hidden="true">	
							<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="3" readonly="true">${purchase.remarks }</textarea>
							</li>
							<li>
							<label class="control-textarea">到货备注</label>
							<textarea id="arriveRemark" name="arriveRemark" rows="3">${purchase.arriveRemark }</textarea>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" >
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail1" data-toggle="tab">采购费用单</a></li>
		    </ul>   
			<div id="content-list">
				<table id="dataTablePurchFee"></table>
			</div>
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购明细单</a></li>
		    </ul> 
			<div id="content-list">
				<table id="dataTableDetail"></table>
			</div>
		</div>
	</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		
	
<script type="text/javascript">
$(function(){
	
})

function validateRefresh_choice(){
 	$('#dataForm').data('bootstrapValidator')
                .updateStatus('openComponent_wareHouse_whcode', 'NOT_VALIDATED',null)  
                .validateField('openComponent_wareHouse_whcode');                    
}
$(function() {
	$("#whcode").openComponent_wareHouse({callBack:getData,showValue:'${purchase.whcode}',condition: {czybh:'${czybh}',delXNCK:'1'}});
	$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}'});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			openComponent_wareHouse_whcode:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '仓库编号不能为空'  
		            }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	function getData(data){
		if (!data) return;
	  	validateRefresh_choice();
	}
});
$(function(){
	var gridOptionPurchFee = {	
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		height:200,
		url:"${ctx}/admin/purchase/getPurchFeeList",
		postData:{no:"${purchase.no}",status:"1"},
			colModel : [
				{name:'no', index:'no', width:80, label:'费用单号', sortable:true ,align:"left",},
				{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",},
				{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left",},
				{name:'amount', index:'amount', width:80, label:'总价', sortable:true ,align:"left",},
				{name:'whcode', index:'whcode', width:80, label:'柜号名称', sortable:true ,align:"left",},
				{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
				{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
			],
			onCellSelect: function(id,iCol,cellParam,e){
				var ids = $("#dataTablePurchFee").jqGrid("getDataIDs");  
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$('#'+ids[i]).find("td").removeClass("SelectBG");
					}
				}
				$('#'+ids[id-1]).find("td").addClass("SelectBG");
				
				/*  计算采购费  按材料价格占比  采购费 x (本次到货 x 单价 /sum(本次到货x单价) )*/
				countPurchFee(id);
			},
		}; 
	 	Global.JqGrid.initEditJqGrid("dataTablePurchFee",gridOptionPurchFee);


	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('firstAmount').setAttribute('type','password') ;
		document.getElementById('secondAmount').setAttribute('type','password') ;
		document.getElementById('remainAmount').setAttribute('type','password') ;
	}
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	$("#custCode").openComponent_customer({showValue:'${purchase.custCode}',showLabel:'${purchase.address}',readonly: true});
	//$("#whcode").openComponent_wareHouse({showValue:'${purchase.whcode}',condition:{czybh:'${czybh}',delXNCK:'1'}});
	//$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}'});
	$("#applyMan").openComponent_employee();
	$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
	$("#supplier").openComponent_supplier();
	$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	$("#sino").openComponent_salesInvoice({showValue:'${purchase.sino}',showLabel:'',readonly: true});
	$("#openComponent_wareHouse_whcode").attr("readonly",true);
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
		height:200,
			colModel : [
			{name:'pk',				index:'pk',				width:80,	label:'PK',		sortable:true,align:"left",hidden:true},
			{name:'itcode', 		index:'itcode', 		width:80, 	label:'材料编号', 	sortable:true ,align:"left"},
			{name:'itdescr', 		index:'itdescr', 		width:210, 	label:'材料名称', 	sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr',	index:'sqlcodedescr', 	width:60, 	label:'品牌', 	sortable:true ,align:"left"},
			{name:'color',			index:'color', 			width:50,	label:'颜色', 	sortable:true ,align:"left"},	
			{name:'allqty', 		index:'allqty',			width:50, 	label:'当前库存',	sortable:true ,align:"left",},
			{name:'qtycal',			index:'qtycal',			width:50, 	label:'采购数量', 	sortable:true,align:"left",sum:true},
			{name:'arrivqty', 		index:'arrivqty', 		width:50, 	label:'已到货',	sortable:true ,align:"left",sum:true},
			{name:'thearrivqty',	index : 'thearrivqty',	width:60,	label:'本次到货',	sortable : true,align : "left"},
			{name:'unitprice',		index:'unitprice',		width:50, 	label:'单价', 	sortable:true,align:"left",},
			{name:'amount',		index:'amount',		width:50, 	label:'总价', 	sortable:true,align:"left",},
			{name:'purchfee',		index:'purchfee',		width:80, 	label:'费用分摊金额', sortable:true,align:"left",sum:true},
			{name:'unidescr', 		index:'unidescr', 		width:40, 	label:'单位', 	sortable:true ,align:"left"},
			{name:'remarks',		index:'remarks',		width:150, 	label:'备注', 	sortable:true,align:"left"},
			],
			loadComplete:function(){
				console.log(11);
			},
			onCellSelect: function(id,iCol,cellParam,e){
				var ids = $("#dataTableDetail").jqGrid("getDataIDs");  
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$('#'+ids[i]).find("td").removeClass("SelectBG");
					}
				}
				$("#dataTableDetail").find('#'+ids[myRound(id.split("N_1")[1])]).find("td").addClass("SelectBG");
			},
		}; 
	 	Global.JqGrid.initEditJqGrid("dataTableDetail",gridOption);
		 $.each(
			${jsonObject },
			function(k,v){
				Global.JqGrid.addRowData("dataTableDetail",v);
		  	}
	  	);
		//保存操作	
		$("#saveBtn").on("click",function(){
			var ret = selectDataTableRow("dataTablePurchFee");
			var whCode= $.trim($("#whcode").val());
			var thearriveqty = Global.JqGrid.allToJson("dataTableDetail","thearrivqty");
			var ids = $("#dataTableDetail").jqGrid('getGridParam','selarrrow');
			arry = thearriveqty.fieldJson.split(",");	
			var x = 0;
				for(var i = 0;i < arry.length; i++){
					x = x + parseFloat(arry[i]);
				}	
			if(whCode==''){
				art.dialog({
					content:'请选择仓库编号',
				});
				return false;
			}
			if(!ret){
				art.dialog({
					content:"请选择一条采购费用单！"
				});
			}
			var purchfee = $("#dataTableDetail").jqGrid('getCol','purchfee');	
			if(!purchfee || purchfee.length <= 0) {
				art.dialog({
					content:"采购明细加载中，请稍后再试！"
				});
				return false;
			}
			if(ret.amount!=0){
				for(i=0;i<purchfee.length;i++){
					if(parseFloat(purchfee[i])==0 || isNaN(parseFloat(purchfee[i])) || !purchfee[i] || purchfee[i] == ""){
						art.dialog({
							content:"总价与总分摊金额不同，请重新操作！"
						});
						return false;
					}
				}
			}
			$("#purchFeeNo").val(ret.no);
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
						art.dialog({
							content:"本次到货件数【"+x+"】件，是否确认？",
							lock: true,
							width: 200,
							height: 100,
							ok: function () {
						    	var param= Global.JqGrid.allToJson("dataTableDetail");
								Global.Form.submit("dataForm","${ctx}/admin/supplierPurchase/doSave",param,function(ret){
									if(ret.rs==true){
										art.dialog({
											content:ret.msg,
											time:1000,
											beforeunload:function(){
												Global.Dialog.returnData =ret;
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
						    	return true;
							},
							cancel: function () {
								return true;
							}
						});
					}else{
						var wCode= $.trim($("#whcode").val());
						if(wCode==''){
							art.dialog({
								content:'请选择仓库编号'
							});
						}else{
							art.dialog({
								content:'您没有该仓库权限'
							});
						}
					}
		    	}
		 	});		
		});
	$("#jqgh_dataTable_thearrivqty").css("background-color","yellow");
	$("#jqgh_dataTable_thearrivqty").css("color","red");
});  
function countPurchFee(id){
	var detailSum=0;
	var rowData = $("#dataTablePurchFee").jqGrid('getRowData',id);
	var purchFee = rowData.amount; 									//采购费
		$("#dataTableDetail").footerData('set', { "purchfee": rowData.amount});   
	
	var detailJson = Global.JqGrid.allToJson("dataTableDetail");
	$.each(detailJson.datas,function(k,v){   //明细表本次到货的总价
		detailSum += v.thearrivqty*v.unitprice; 
    });
    $.each(detailJson.datas,function(m,n){
		var avgFee =0;
		if(detailSum != 0){
			avgFee = myRound(purchFee*(n.thearrivqty*n.unitprice)/detailSum,2);
		}
		$("#dataTableDetail").jqGrid('setCell',"N_"+(100000+m),"purchfee",avgFee);
    });
}
</script>
</body>
</html>




