<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购入库</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <script type="text/javascript">
  $(function() {
  	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
	};
		Global.LinkSelect.setSelect(dataSet);
  });
</script>
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
					</div>
				</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<div class="validate-group row" >
							<li>
								<label>采购单号</label>
									<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${purchase.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label><span class="required">*</span>单据状态</label>
									<house:xtdm id="status" dictCode="PURCHSTATUS"  value="OPEN" disabled='true'></house:xtdm>                     
							</li>
							<li>
								<label><span class="required">*</span>采购类型</label>
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>下单员</label>
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
							<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width:160px"onclick="getItemType1()"style="width: 166px;" >
									</select>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>供应商编号</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
							</li>
							<li>
								<label><span class="required">*</span>采购日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd hh:MM:ss'/>" />
							</li>
								<li>
									<label>到货日期</label>
										<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>"  />
								</li>
								</div>
							<div class="validate-group row" >
								<li>
									<label><span class="required">*</span>仓库编号</label>
										<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</li>
								<li>
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" onblur="doChange()" value="${purchase.custCode }"/>
							</li>
							<li>
								<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${purchase.address }" readonly="true"/>
							</li>
							<li>
								<label>档案号</label>
									<input type="text" id=documentNo name="documentNo"  style="width:160px;" value="${purchase.documentNo } "readonly= 'readonl' />
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost } "readonly= 'readonl' />
							</li>
							<li>
								<label>其他费用调整</label>
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
							</li>
							<li>
								<label>材料总价</label>
									<input type="text" id="amount" name="amount"  style="width:160px;" 
											onblur="changeAmount()"  value="${purchase.amount }" readonly="readonly" />
							</li>
							<li>
								<label>已付定金</label>
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>已付到货款</label>
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
							</li>
							<li>
								<label> 应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;"
									 		onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
							</li>
							<li>
								<label>是否结账</label>
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="${purchase.isCheckOut}" disabled='true'></house:xtdm>                     
							</li>
							<li>
								<label>结账单号</label>
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"  placeholder="结账时生成" value="${purchase.checkOutNo }" readonly='readonly'/>
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>销售单号</label>
									<input type="text" id="sino" name="sino" style="width:160px;" value="${purchase.sino }"/>
							</li>
							<li>
								<label>配送地点</label>
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="${ purchase.delivType}" disabled='true'></house:xtdm>                     
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
  							</li>
  							</div>
  						</ul>	
  				</form>
  			</div>
  			</div>	
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="add" >
									<span>新增</span></button>
						<button type="button" class="btn btn-system " id="fastAdd">
									<span>快速新增</span></button>
						<button type="button" class="btn btn-system " id="update">
									<span>编辑</span></button>
						<button type="button" class="btn btn-system " id="delDetail">
									<span>删除</span></button>
						<button type="button" class="btn btn-system " id="view">
									<span>查看</span></button>
						<button type="button" class="btn btn-system " id="importFromPurAppsBtn"><span>从申请导入</span></button>
						<button type="button" class="btn btn-system " id="import">
									<span>从预算导入</span></button>
						<button type="button" class="btn btn-system " id="importSale">
									<span>从销售导入</span></button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('采购明细单', null, 'dataForm')" title="导出当前excel数据" >
									<span>导出excel</span></button>
				</div>
			</div>	
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购单明细</a></li>
		    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>	
  	</div> 
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
var qtycal;
var amount=0;
var y;
var itemtype1;
var projectCost=0;
var isNeedPlanEndDate="";
var constructstatus="";
$(function() {
	
	if ('${purchase.itemType1}') {
		$("#supplier").openComponent_supplier();
	} else {
		$("#supplier").openComponent_supplier({callBack:getSupplierData,readonly:true});
	}
	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			itemType1:{  
				validators: {  
					notEmpty: {  
						message: '材料类型1不能为空'  
					}
				}  
			},
			openComponent_supplier_supplier:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '供应商编号不能为空'  
		            }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function getSupplierData(data) {
    if (!data) return;
    var dataSet = {
        firstSelect: "itemType1",
        firstValue: data.itemtype1,
        secondSelect: "itemType2",
        secondValue: data.itemtype2,
    };
    $('#dataForm').data('bootstrapValidator')
            .updateStatus('openComponent_supplier_supplier', 'NOT_VALIDATED', null)
            .validateField('openComponent_supplier_supplier');
    Global.LinkSelect.setSelect(dataSet);
    
    var arriveDate = $("#arriveDate");
    arriveDate.val(arriveDate.val() || addDate(parseInt(data.PreOrderDay)));
}

$(function(){
	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('firstAmount').setAttribute('type','password') ;
		document.getElementById('secondAmount').setAttribute('type','password') ;
		document.getElementById('remainAmount').setAttribute('type','password') ;
	}
		
	
	
    function getCust(datas){
		var item1 = $.trim($("#itemType1").val());
		if (!datas) return;
		$('#address').val(datas.address);
		$('#documentNo').val(datas.documentno);
		constructstatus=$.trim(datas.constructstatus);
		if(item1=='RZ'){
			$("#add").attr("disabled","true");
			$("#fastAdd").attr("disabled","true");
			$("#importSale").attr("disabled","true");
		}else{
			$("#add").removeAttr("disabled","true");
			$("#fastAdd").removeAttr("disabled","true");
			$("#importSale").removeAttr("disabled","true");
		}
		$.ajax({
			url: '${ctx}/admin/customer/isNeedPlanEndDate',
			type: 'post',
			data: {custCode:datas.code},
			dataType: 'json',
			cache: false,
			error: function (obj) {
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				},
			success: function (obj) {
		  		var ids = $("#dataTable").jqGrid("getDataIDs");
				var itemType2 =$("#dataTable").jqGrid("getRowData",ids[0]).itemType2;
				isNeedPlanEndDate=obj;
				if($.trim(obj)=="true"){
					$("#arriveDate").val("");
					
				}else{
					if($.trim($("#itemType1").val())=="RZ"&&ids.length>=1){
						$.ajax({
							url:'${ctx}/admin/purchase/getAjaxArriveDay',
							type: 'post',
							data: {code:itemType2},
							dataType: 'json',
							cache: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
						    },
						    success: function(obj){
						    	if($.trim(isNeedPlanEndDate)!="true" && (custCode=="" ||constructstatus=="7")){
						  			$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
						  		}
						    }
						 });
					}
				}
			}
		});
	}	
	
	$("#sino").openComponent_salesInvoice();
	$("#applyMan").openComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}' ,readonly:true});
	$("#custCode").openComponent_customer({callBack:getCust,condition:{purchStatus:"1"}});
	if('${purchase.delivType}'==1){
		$("#whcode").openComponent_wareHouse({condition:{delXNCK:'1',czybh:'${czybh}'}});
	}else{
		$("#whcode").openComponent_wareHouse({condition:{delXNCK:'1'},czybh:'${czybh}',readonly:true});//删除虚拟仓库
	}
	//Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	//$("#openComponent_wareHouse_whcode").attr("readonly",true);

	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'itemType2', index:'itemType2', width:80, label:'itemType2', sortable:true ,align:"left",hidden:true},
			{name:'puno', index:'puno', width:80, label:'puno', sortable:true ,align:"left",hidden:true},
			{name:'itcode', index:'itcode', width:80, label:'材料编号', sortable:true ,align:"left"},
			{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr', index:'sqlcodedescr', width:80, label:'品牌', sortable:true ,align:"left"},
			{name:'color', index:'color', width:80, label:'颜色', sortable:true ,align:"left"},	
			{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"left",sum:true},
			{name:'qtycal',	index:'qtycal',width:80, label:'采购数量', 	editable:true,	editrules: {number:true,required:true},sortable : true,align : "left",sum:true},
			{name:'useqty',	index:'useqty',width:60, label:'可用量', sortable : true,align : "left",sum:true},
			{name:'purqty',	index:'purqty',width:85, label:'在途采购量',sortable : true,align : "left",sum:true},
			{name:'unidescr', index:'unidescr', width:45, label:'单位', sortable:true ,align:"left"},
			{name:'unitprice', index:'unitprice', width:45, label:'单价', sortable : true,align : "left",editable:true,	editrules: {number:true,required:true},},
			{name:'markup', index:'markup', width:60, label:'折扣',editable:true,	editrules: {number:true,required:true}, sortable:true ,align:"left",sum:true},
			{name:'beflineamount', index:'beflineamount', width:90, label:'折扣前金额', sortable:true ,align:"left",sum:true},
			{name:'amount', index:'amount', width:60, label:'总价', sortable:true ,align:"left",sum:true},
			{name:'beflineprice', index:'beflineprice', width:90, label:'折扣前单价', sortable:true ,align:"left",},
			{name:'projectcost',index:'projectcost',width:120, label:'项目经理结算单价', 	sortable:true,align:"left",},
			{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left",editable:true},
			{name:'sino',index:'sino',width:150, label:'sino', 	sortable:true,align:"left",hidden:true},
			{name:'importpk',index:'importpk',width:150, label:'importpk', 	sortable:true,align:"left",hidden:true},
		    {name:'purchappdtpk',index:'purchappdtpk',width:150, label:'采购申请明细PK', sortable:true,align:"left",hidden:true},
		],
			gridComplete:function(){
				 var a= $("#dataTable").getCol('amount',false,'sum') ;
				 var sino=$("#dataTable").getCol('sino',false) ;
					   $("#amount").val(a.toFixed(2));
	             	     $("#remainAmount").val(a.toFixed(2));
	             if(sino!=null)  
	             		$("#sino").setComponent_salesInvoice({showValue:sino[sino.length-1]});
	               },
	         beforeSaveCell:function(rowId,name,val,iRow,iCol){
 				var rowData = $("#dataTable").jqGrid("getRowData",rowId);
 				var xxx= rowData.qtycal;
		    	if(val<=0){
		    		art.dialog({
						content:"采购数量、折扣、单价必须为正数，请重新输入",
					});
		    	}
			},      
			afterSaveCell:function(id,name,val,iRow,iCol){
				var rowData = $("#dataTable").jqGrid("getRowData",id);
				switch (name){
                	case 'qtycal':
                		$("#dataTable").setCell(id,'amount',(val*rowData.unitprice).toFixed(2));
                		$("#dataTable").setCell(id,'beflineamount',(val*rowData.beflineprice).toFixed(2));
                	break;
                	case 'unitprice':
               			$("#dataTable").setCell(id,'amount',(val*rowData.qtycal).toFixed(2));
                		if(rowData.beflineprice!=0){
	                		$("#dataTable").setCell(id,'markup',(val/rowData.beflineprice).toFixed(4));
                		}
                	break;
                	case 'markup':
						$("#dataTable").setCell(id,'unitprice',(val*rowData.beflineprice).toFixed(2));
						$("#dataTable").setCell(id,'amount',(rowData.qtycal*(val*rowData.beflineprice).toFixed(2)).toFixed(2));
                	break;
               	}
				var a=$("#dataTable").getCol('amount',false,'sum'); 
			  	if(a) { $("#amount").val(a.toFixed(2));
             	    $("#remainAmount").val(a.toFixed(2));
             	}
                var amount=$("#dataTable").getCol('amount',false,'sum');   
             	$("#dataTable").footerData('set', { "amount": amount }); 
	             		    
			},
			  beforeSelectRow:function(id){
         	 	setTimeout(function(){
	          	 relocate(id);
	          },10)
         	 }
	};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("beflineprice").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("unitprice").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("projectcost").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
		}
	//新增
	$("#add").on("click",function(){
	    var item2=new Array();
	    var days=0;
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(supplier == ''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			  title:"采购明细——增加",
			  url:"${ctx}/admin/purchase/goAdd",
			  postData:{itemtype1:item1},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  		if (obj){
									  var json = {
										itcode:v.itcode,
										itdescr:v.itdescr,
										sqlcodedescr:v.sqlCodeDescr,
										color:v.color,
										allqty:v.allqty,
										qtycal:v.qtyCal,
										useqty:v.useqty,
										purqty:v.purqty,
										unitprice:v.unitPrice,
										amount:v.amount,
										projectcost:v.projectCost,
										remarks:v.remarks,
										unidescr:v.uniDescr,
										itemType2:v.itemType2,
										markup:v.markup,
										beflineamount:v.befLineAmount,
										beflineprice:v.befLinePrice
									  };
						 			 Global.JqGrid.addRowData("dataTable",json);
								}
						  item2.push(v.itemType2);
					  });
				  		$("#itemType1").attr("disabled","disabled");
					  		$.ajax({
								url:'${ctx}/admin/purchase/getAjaxArriveDay',
								type: 'post',
								data: {code:item2[0]},
								dataType: 'json',
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
							    },
							    success: function(obj){
							    	if($.trim(isNeedPlanEndDate)!="true" && (custCode=="" ||constructstatus=="7")){
							  			$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
							  		}
							    }
							 });
				  }
			  } 
		 });
	});
	//快速新增
	$("#fastAdd").on("click",function(){
		var item2 =new Array();
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(supplier == ''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			  title:"采购明细——增加",
			  url:"${ctx}/admin/purchase/goFastAdd",
			  postData:{itemType1:item1,supplier:supplier},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
								if (obj){
								  var json = {
									itcode:v.code,
									itdescr:v.descr,
									sqlcodedescr:v.sqldescr,
									color:v.color,
									allqty:v.allqty,
									qtycal:0,
									useqty:v.useqty,
									purqty:v.purqty,
									unitprice:v.cost,
									amount:0,
									projectcost:v.projectcost,
									remarks:v.remark,
									unidescr:v.uomdescr,
									itemType2:v.itemtype2,
									markup:1,
									beflineamount:0,
									beflineprice:v.cost
								  };
								  Global.JqGrid.addRowData("dataTable",json);
								}
						  item2.push(v.itemtype2);
					  });
				 		$("#itemType1").attr("disabled","disabled");//dateFmt:'yyyy-MM-dd'
							$.ajax({
								url:'${ctx}/admin/purchase/getAjaxArriveDay',
								type: 'post',
								data: {code:item2[0]},
								dataType: 'json',
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
							    },
							    success: function(obj){
							    	if($.trim(isNeedPlanEndDate)!="true" && (custCode=="" ||constructstatus=="7")){
							  			$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
							  		}
							    }
							 });
					}
			  } 
		 });
	});
	//编辑
	$("#update").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"采购明细——修改",
				url:"${ctx}/admin/purchase/goAddUpdate",
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,sqlCodeDescr:ret.sqlcodedescr,projectCost:ret.projectcost,
						unitPrice:ret.unitprice,amount:ret.amount,uniDescr:ret.unidescr,
						itdescr:ret.itdescr,remarks:ret.remarks,color:ret.color,itemtype1:$('#itemType1').val(),
						markup:ret.markup},
				height:700,
				width:1000,
				returnFun:function(data){
					 var id = $("#dataTable").jqGrid("getGridParam","selrow");
						//Global.JqGrid.delRowData("dataTable",id);
				  if(data){
					  $.each(data,function(k,v){
							if (obj){
								  var json = {
									itcode:v.itcode,
									itdescr:v.itdescr,
									sqlcodedescr:v.sqlCodeDescr,
									color:v.color,
									allqty:v.allqty,
									qtycal:v.qtyCal,
									useqty:v.useqty,
									purqty:v.purqty,
									unitprice:v.unitPrice,
									amount:v.amount,
									projectcost:v.projectCost,
									remarks:v.remarks,
									unidescr:v.uniDescr,
									color:v.color,
									markup:v.markup,
									beflineprice:v.beflineprice,
									beflineamount:v.beflineamount,
								  };
			   					$('#dataTable').setRowData(rowId,json);
							}
						//  Global.JqGrid.addRowData("dataTable",json);
					  });
					  
					  var a=$("#dataTable").getCol('amount',false,'sum'); 
					  if(a) { 
					  	  $("#amount").val(a.toFixed(2));
		              	  $("#remainAmount").val(a.toFixed(2));
		              }
		              var amount=$("#dataTable").getCol('amount',false,'sum');   
		              $("#dataTable").footerData('set', { "amount": amount }); 
				  }
			  } 
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	}); 
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		
		art.dialog({
				 content:"是删除该记录？",
				 lock: true,
				 width: 100,
				 height: 80,
				 ok: function () {
					Global.JqGrid.delRowData("dataTable",id);
				},
				cancel: function () {
					return true;
				}
			});
	});
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"采购明细——查看",
				url:"${ctx}/admin/purchase/goAddView",
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr,remarks:ret.remarks},
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	}); 
	//保存
	$("#saveBtn").on("click",function(){
		var custCode = $.trim($("#custCode").val());
		if(custCode ==""){
			save();
		}else{
			$.ajax({
              url: '${ctx}/admin/customer/isNeedPlanEndDate',
              type: 'post',
              data: {custCode:custCode},
              dataType: 'json',
              cache: false,
              error: function (obj) {
                showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
              },
              success: function (obj) {
              	isNeedPlanEndDate=obj;
                if($.trim(obj)=="true" && $.trim($("#arriveDate").val())==""){
                	art.dialog({
                		content:"到货日期必填",
                	});
                	return;
                }else{
                	save();
                }
              }
            });
		}
	});
	
	// 从采购申请导入
	$("#importFromPurAppsBtn").on("click", function() {
	    var itemType1 = $("#itemType1").val();
	    var supplier = $("#supplier").val();
	    var itemType2s = [];
	    
	    if (!itemType1) {
	        art.dialog({content: "请选择材料类型1！"});
	        return;
	    }
	    
	    var grid = $("#dataTable");
	    var rows = grid.getRowData();
	    var savedAppDetailPks = [];
	    
	    for (var i = 0; i < rows.length; i++) {
	        var purchAppDtPk = rows[i].purchappdtpk;
	        if (purchAppDtPk) savedAppDetailPks.push(purchAppDtPk);
	    }
	    
	    Global.Dialog.showDialog("importFromPurAppsDialog", {
            title:"采购申请明细——导入",
            url:"${ctx}/admin/purchase/goImportFromPurApps",
            postData: {
                itemType1: itemType1,
                supplier: supplier,
                savedAppDetailPks: savedAppDetailPks.join(",")
            },
            height: 680,
            width: 1000,
            returnFun: function(rows) {
                $.each(rows, function(index, ele) {
					Global.JqGrid.addRowData("dataTable", {
					    itcode: ele.itemcode,
					    itdescr: ele.itemdescr,
					    sqlcodedescr: ele.branddescr,
					    color: ele.color,
					    allqty: ele.allqty,
					    qtycal: ele.qty,
					    useqty: ele.useqty,
					    purqty: ele.purqty,
					    unitprice: ele.cost,
					    amount: myRound(ele.qty * ele.cost,2),
					    projectcost: ele.projectcost,
					    remarks: ele.remark,
					    unidescr: ele.unitdescr,
					    itemType2: ele.itemtype2,
					    markup: 1,
					    beflineprice: ele.cost,
					    beflineamount: myRound(ele.qty * ele.cost,2),
					    purchappdtpk: ele.pk,
					});
					
					itemType2s.push(ele.itemtype2);
                });
                
                if (rows.length > 0) {
	                $("#itemType1").attr("disabled", true);
	                $.ajax({
	                    url: '${ctx}/admin/purchase/getAjaxArriveDay',
	                    type: 'post',
	                    data: {code: itemType2s[0]},
	                    dataType: 'json',
	                    cache: false,
	                    error: function (obj) {
	                        showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	                    },
	                    success: function (obj) {
	                        if ($.trim(isNeedPlanEndDate) != "true" && (custCode == "" || constructstatus == "7")) {
	                            $("#arriveDate").val($("#arriveDate").val() == "" ? addDate(obj.arriveDay) : $("#arriveDate").val());
	                        }
	                    }
	                });
                }
            }
        });
	});
	
	//从预算导入
	$("#import").on("click",function(){
		var item2=new Array();
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(custCode == ''){
			art.dialog({content: "请先选择客户",width: 200});
			return false;
		}
		var itcode = Global.JqGrid.allToJson("dataTable","importpk");
		var arr = new Array();
			arr = itcode.fieldJson.split(",");
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("import",{
			  title:"领料明细——增加",
			  url:"${ctx}/admin/purchase/goImport",
			  postData:{custCode:custCode,itemType1:item1,arr:arr},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  if (obj){
								  var json = {
									itcode:v.itemcode,
									itdescr:v.itemdescr,
									sqlcodedescr:v.suppldescr,
									color:v.color,
									allqty:v.allqty,
									qtycal:v.qty-v.sendqty,
									useqty:v.useqty,
									purqty:v.purqty,
									unitprice:v.cost,
									amount:((v.qty-v.sendqty)*v.cost),
									projectcost:v.projectcost,
									remarks:v.remarks,
									unidescr:v.uomdescr,
									itemType2:v.itemtype2,
									importpk:v.pk,
									markup:1,
									beflineprice:v.cost,
									beflineamount:((v.qty-v.sendqty)*v.cost),
								  };
								  Global.JqGrid.addRowData("dataTable",json);
								}
						  item2.push(v.itemtype2);
					  });
				  		$("#itemType1").attr("disabled","disabled");
							$.ajax({
								url:'${ctx}/admin/purchase/getAjaxArriveDay',
								type: 'post',
								data: {code:item2[0]},
								dataType: 'json',
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
							    },
							    success: function(obj){
							    	if($.trim(isNeedPlanEndDate)!="true" && (custCode=="" ||constructstatus=="7")){
								  		$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
							    	}
							    }
							 });
				  }
			  } 
		 });
	});
	
	//从销售单导入
	$("#importSale").on("click",function(){
		var item2=new Array();
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		if(item1 ==''){
			art.dialog({content: "请先选择材料类型",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("importSale",{
			  title:"销售明细——增加",
			  url:"${ctx}/admin/purchase/goImportSale",
			  postData:{itemType1:item1,sqlcode:supplier},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
					  	if (obj){
								  var json = {
									itcode:v.itcode,
									itdescr:v.itemdescr,
									sqlcodedescr:v.sqlcodedescr,
									color:v.color,
									allqty:v.allqty,
									qtycal:v.qty,
									unitprice:v.cost,
									useqty:v.useqty,
									purqty:v.purqty,
									amount:(v.qty*v.cost),
									projectcost:projectCost,
									remarks:v.remarks,
									projectcost:v.projectcost,
									unidescr:v.uomdescr,
									sino:v.sino,
									itemType2:v.itemtype2,
									markup:1,
									beflineprice:v.cost,
									beflineamount:(v.qty*v.cost),
								  };
								  Global.JqGrid.addRowData("dataTable",json);
								}
						  item2.push(v.itemtype2);
					  });
				 		$("#itemType1").attr("disabled","disabled");
							$.ajax({
								url:'${ctx}/admin/purchase/getAjaxArriveDay',
								type: 'post',
								data: {code:item2[0]},
								dataType: 'json',
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
							    },
							    success: function(obj){
							    	if($.trim(isNeedPlanEndDate)!="true" && (custCode=="" ||constructstatus=="7")){
							  			$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
							  		}
							    }
							 });
				  }
			  } 
		 });
	});
	
});

function save(){
		$("#arriveDate").removeAttr("disabled");
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var countqtycal = Global.JqGrid.allToJson("dataTable","qtycal");
		var countunitprice = Global.JqGrid.allToJson("dataTable","unitprice");
			arry = countqtycal.fieldJson.split(",");	
			arryy = countunitprice.fieldJson.split(",");	
			var x = 1;
			var y = 1;
				for(var i = 0;i < arry.length; i++){
					if(parseFloat(arry[i])<0){
						art.dialog({
            			content: "采购单数量不能为负数"
            		});
            		return false;
					}
					x = x * parseFloat(arry[i]);
				}
				if(x==0){
					art.dialog({
            			content: "存在采购数量为零的采购明细单，不允许保存"
            		});
            		return;
				}
				for(var i = 0;i < arryy.length; i++){
					if(parseFloat(arryy[i])<0){
						art.dialog({
	            			content: "存在单价为负数，不允许保存"
	            	});
	            		return false;
	            	}
					y = y * parseFloat(arryy[i]);
				}
				if(y==0){
					art.dialog({
						content:"存在单价为 0 的材料，是否保存",
						lock: true,
						width: 100,
						height: 80,
						ok: function () {
							var param= Global.JqGrid.allToJson("dataTable");
							Global.Form.submit("dataForm","${ctx}/admin/purchase/doPurchaseSave",param,function(ret){
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
						},
						cancel: function () {
							return true;
						}
					});
				}else{
					var param= Global.JqGrid.allToJson("dataTable");
							Global.Form.submit("dataForm","${ctx}/admin/purchase/doPurchaseSave",param,function(ret){
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
				}
	}

function getItemType1(){
	 itemtype1 = $.trim($("#itemType1").val());
	 custCode = $.trim($("#custCode").val());
	 $("#supplier").openComponent_supplier({callBack:getSupplierData,condition:{itemType1:itemtype1,readonly:'1'}});
	 $("#openComponent_supplier_supplier").attr("readonly",false);
	 	
	if(itemtype1=='RZ'&&custCode!=''){
		$("#add").attr("disabled","true");
		$("#fastAdd").attr("disabled","true");
		$("#importSale").attr("disabled","true");
	}else{
		$("#add").removeAttr("disabled","true");
		$("#fastAdd").removeAttr("disabled","true");
		$("#importSale").removeAttr("disabled","true");
	}
}

function doChange(){
	var custCode=$.trim($("#custCode").val());
	var itemType1=$.trim($("#itemType1").val());
	if(custCode!=''&&itemType1=='RZ'){
		$("#add").attr("disabled","true");
		$("#fastAdd").attr("disabled","true");
		$("#importSale").attr("disabled","true");
	}else{
		$("#add").removeAttr("disabled","true");
		$("#fastAdd").removeAttr("disabled","true");
		$("#importSale").removeAttr("disabled","true");
	}
}

function addDate(days){ 
   var d=new Date(); 
   d.setDate(d.getDate()+days); 
   var m=d.getMonth()+1; 
   return d.getFullYear()+'-'+m+'-'+d.getDate(); 
}

function validateRefresh_choice(){
	$('#dataForm').data('bootstrapValidator')
			.updateStatus('openComponent_supplier_supplier', 'NOT_VALIDATED',null)  
			.validateField('openComponent_supplier_supplier');                    
}

</script>
  </body>
</html>

















