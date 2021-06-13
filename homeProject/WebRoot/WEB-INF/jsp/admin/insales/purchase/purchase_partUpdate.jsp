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
	<title>编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>


</head>
<body>
	<div class="body-box-form">
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
			<div class="infoBox" id="infoBoxDiv"></div>
			<div class="edit-form" id="edit-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
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
									<house:xtdm id="status" dictCode="PURCHSTATUS"  value="OPEN" disabled='true'></house:xtdm>                     
								</li>
								<li>
								<label>下单员</label>
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
								</li>
								<li>
								<label>采购日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="diabled"/>
								</li>
								<li>
								<label>到货日期</label>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${purchase.arriveDate }" disabled="diabled"/>
								</li>
								<li>
								<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 166px;" disabled="disabled"  >
									</select>
								</li>
								<li>
								<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost }" readonly="true"/>
								</li>
								<li>
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</li>
								<li>
								<label>其他费用调整</label>
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonly' />
								</li>
								<li>
								<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${purchase.address }" readonly="true"/>
								</li>
								<li>
								<label>档案号</label>
									<input type="text" id=documentNo name="documentNo"  style="width:160px;" value="${purchase.documentNo } "readonly= 'readonly' />
								</li>
								<li>
								<label>供应商编号</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</li>
								<li>
								<label>材料总价</label>
									<input type="text" id="amount" name="amount"  style="width:160px;" value="${purchase.amount }" readonly= 'readonly' />
								</li>
								<li>
								<label>仓库编号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</li>
								<li>
								<label>配送地点</label>
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="1" disabled='true'></house:xtdm>                     
								</li>
								<li>
								<label>是否结账</label>
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="0" disabled='true'></house:xtdm>                     
								</li>
								<li>
								<label>已付定金</label>
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</li>
								<li>
								<label>结账单号</label>
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" value="${purchase.checkOutNo }" readonly='readonly'/>
								</li>
								<li>
								<label>已付到货款</label>
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</li>
								<li>
								<label>销售单号</label>
									<input type="text" id="sino" name="sino" style="width:160px;" value="${purchase.sino }" readonly="true"/>
								</li>
								<li>
								<label> 应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;" value="${purchase.remainAmount }"  readonly='readonly'/>
								</li>
								<li>
								<label>原采购单号</label>
									<input type="text" id="oldPUNo" name="oldPUNo" style="width:160px;" value="${purchase.oldPUNo }" readonly="true"/>
								</li>
								<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
								</li>
							</ul>	
				</form>
			</div>
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购单明细</a></li>
		    </ul> 
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="view">
							<span>查看</span></button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('采购明细单')" title="导出当前excel数据" >
							<span>导出excel</span></button>
				</div>	
			</div>	
		
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			
		</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){
	if(parseInt(secondAmount)!=0||parseInt(firstAmount)!=0){//remainAmount,firstAmount,amount
		var firstAmount=$.trim($("#firstAmount").val());
		var amount=$.trim($("#amount").val());
		$("#add").attr("disabled","disabled");
		$("#fastAdd").attr("disabled","disabled");
		$("#delDetail").attr("disabled","disabled");
		$("#update").attr("disabled","disabled");
		$("#import").attr("disabled","disabled");
		$("#importSale").attr("disabled","disabled");
		$("#otherCost").attr("disabled","disabled");
		$("#remainAmount").val(parseFloat(amount)-parseFloat(firstAmount))
		$("#supplier").openComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
		$("#custCode").openComponent_customer({callBack:getCust,showValue:'${purchase.custCode}',showLabel:'${purchase.custDescr}',readonly:true});
	}


	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('firstAmount').setAttribute('type','password') ;
		document.getElementById('secondAmount').setAttribute('type','password') ;
		document.getElementById('remainAmount').setAttribute('type','password') ;
	}
	if('${purchase.type}'=='S'){
			$("tbody tr:eq(11) td:lt(2)").css("display","none");
		}

	function getCust(datas){
		if (!datas) return;
		$('#address').val(datas.address);
		$('#documentNo').val(datas.documentno);
	}

	$("#whcode").openComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}',condition:{delXNCK:'1'}});
	$("#applyMan").openComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
	$("#supplier").openComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	$("#custCode").openComponent_customer({callBack:getCust,showValue:'${purchase.custCode}',showLabel:'${purchase.custDescr}',readonly:true});
	$("#sino").openComponent_salesInvoice({showValue:'${purchase.sino}',readonly:true});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);
	
	//初始化表格  
	var lastCellRowId;
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
			colModel : [
				{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
				{name:'puno', index:'puno', width:80, label:'puno', sortable:true ,align:"left",hidden:true},
				{name:'itcode', index:'itcode', width:70, label:'材料编号', sortable:true ,align:"left"},
				{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left",count:true},
				{name:'sqlcodedescr', index:'sqlcodedescr', width:60, label:'品牌', sortable:true ,align:"left"},
				{name:'color', index:'color', width:50, label:'颜色', sortable:true ,align:"left"},	
				{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"left",sum:true},
				{name:'qtycal',	index:'qtycal',width:60, label:'采购数量',  sortable : true,align : "left",sum:true},
				{name:'useqty',	index:'useqty',width:60, label:'可用量', sortable : true,align : "left",sum:true},
				{name:'purqty',	index:'purqty',width:60, label:'在途采购量',sortable : true,align : "left",sum:true},
				{name:'arrivqty',	index:'arrivqty',width:60, label:'已到货数量',sortable : true,align : "left",sum:true},
				{name:'unidescr', index:'unidescr', width:40, label:'单位', sortable:true ,align:"left"},
				{name:'unitprice', index:'unitprice', width:40, label:'单价',  sortable : true,align : "left"},
				{name:'amount', index:'amount', width:60, label:'总价', sortable:true ,align:"left",sum:true},
				{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left",editable:true,},
				{name:'sino',index:'sino',width:150, label:'sino', 	sortable:true,align:"left",hidden:true},
				{name:'custdescr',index:'custdescr',width:150, label:'custdescr', 	sortable:true,align:"left",hidden:true},
				{name:'address',index:'address',width:150, label:'address', 	sortable:true,align:"left",hidden:true},
				{name:'mobile',index:'mobile',width:150, label:'mobile', 	sortable:true,align:"left",hidden:true},
		 ],
			gridComplete:function(){
			var selectedIds = $("#dataTable").jqGrid("getGridParam", "selarrrow");
				 var a= $("#dataTable").getCol('amount',false,'sum') ;
				 var sino=$("#dataTable").getCol('sino',false) ;
				 var custdescr=$("#dataTable").getCol('custdescr',false) ;
				 var address=$("#dataTable").getCol('address',false) ;
				 var mobile=$("#dataTable").getCol('mobile',false) ;
				 var otherCost = $.trim($("#otherCost").val());
				var firstAmount = $.trim($("#firstAmount").val());
					   $("#amount").val(a);
	             	     $("#remainAmount").val(a+parseFloat(otherCost)-parseFloat(firstAmount));
	             if(sino!=null) 
	             		$("#sino").setComponent_salesInvoice({showValue:sino[sino.length-1]});
	             if(custdescr!=''){
	             	for(var i=0;i<custdescr.length;i++){
		             	if(custdescr[i]!='' && custdescr[i]!= custdescr[i-1]){
		             		var remark = document.getElementById('remarks');
	    	         		        remark.innerHTML +="销售编号："+sino[i]+",客户："+custdescr[i]+",地址："+address[i]+",电话："+mobile[i]; 
	        	     	}
	             	}
	             }
	               },
				
	         beforeSaveCell:function(rowId,name,val,iRow,iCol){
 				var rowData = $("#dataTable").jqGrid("getRowData",rowId);
 				var xxx= rowData.qtycal;
		    	if(val<=0){
		    		art.dialog({
						content:"采购数量和单价必须为正数，请重新输入",
					});
					//return String(1);
		    	}
			},      
			afterSaveCell:function(id,name,val,iRow,iCol){
				var rowData = $("#dataTable").jqGrid("getRowData",id);
					switch (name){
	                	case 'qtycal':
	                		$("#dataTable").setCell(id,'amount',(val*rowData.unitprice).toFixed(2));
	                	break;
	                	case 'unitprice':
	                		$("#dataTable").setCell(id,'amount',(val*rowData.qtycal).toFixed(2));
	                	break;
                	}
					 var a=$("#dataTable").getCol('amount',false,'sum'); 
				  	 if(a)  $("#amount").val(a.toFixed(2));
	             		    $("#remainAmount").val(a.toFixed(2));
			},
			beforeSelectRow:function(id){
         	 	setTimeout(function(){
	          	 relocate(id);
	          },10)
         	 }
		}; 
	 	var detailJson = Global.JqGrid.allToJson("dataTable","puno");
		if($.trim($("#no").val())!=''){
			$.extend(gridOption,{
				url:"${ctx}/admin/purchaseDetail/goJqGrid",
				postData:{puno:$.trim($("#no").val())}
			});
		}
			Global.JqGrid.initEditJqGrid("dataTable",gridOption);
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("unitprice").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
		}

	//新增	
	$("#add").on("click",function(){
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		var oldPUNo = $.trim($("#oldPUNo").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(supplier == ''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			  title:"采购入库-新增",
			  url:"${ctx}/admin/purchase/goAdd",
			  postData:{itemtype1:item1,puno:oldPUNo},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.itcode,
							itdescr:v.itdescr,
							sqlcodedescr:v.sqlCodeDescr,
							color:v.color,
							allqty:v.allqty,
							qtycal:v.qtyCal,
							unitprice:v.unitPrice,
							useqty:v.useqty,
							purqty:v.purqty,
							amount:v.amount,
							remarks:v.remarks,
							unidescr:v.uniDescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  		$("#itemType1").attr("disabled","disabled");
				  		$("#arriveDate").val($("#arriveDate").val()==""?new Date().format("yyyy-MM-dd"):$("#arriveDate").val());
				  }
			  } 
		 });
	});
	
	//快速新增
	$("#fastAdd").on("click",function(){
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		var oldPUNo = $.trim($("#oldPUNo").val());
		var puno = $.trim($("#puno").val());
			if(puno==''){
				oldPUNo='1';
			}
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(supplier == ''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			  title:"采购入库-新增",
			  url:"${ctx}/admin/purchase/goFastAdd",
			  postData:{itemType1:item1,supplier:'${purchase.supplier}',no:'${purchase.no}'},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.code,
							itdescr:v.descr,
							sqlcodedescr:v.sqldescr,
							color:v.color,
							allqty:v.allqty,
							qtycal:0,
							unitprice:v.price,
							useqty:v.useqty,
							purqty:v.purqty,
							amount:0,
							remarks:v.remark,
							unidescr:v.uomdescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				 		$("#itemType1").attr("disabled","disabled");//dateFmt:'yyyy-MM-dd'
				  		$("#arriveDate").val($("#arriveDate").val()==""?new Date().format("yyyy-MM-dd"):$("#arriveDate").val());
				  }
			  } 
		 });
	});
	 
	//编辑
	$("#update").on("click",function(){
		var item1 = $.trim($("#itemType1").val());
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"采购单编辑",
				url:"${ctx}/admin/purchase/goAddUpdate",
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,sqlCodeDescr:ret.sqlcodedescr,itemtype1:item1,
							remarks:ret.remarks,amount:ret.amount,itdescr:ret.itdescr,puno:ret.puno,color:ret.color,uniDescr:ret.unidescr},
				height:700,
				width:1000,
				returnFun:function(data){
					 var id = $("#dataTable").jqGrid("getGridParam","selrow");
						Global.JqGrid.delRowData("dataTable",id);
				  if(data){
					  $.each(data,function(k,v){
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
							remarks:v.remarks,
							unidescr:v.uniDescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
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
		Global.JqGrid.delRowData("dataTable",id);
	});	
	
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"查看采购信息",
				url:"${ctx}/admin/purchase/goAddView",
				postData:{puno:ret.puno,itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr,remarks:ret.remarks},
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});
	
	//从预算导入
	$("#import").on("click",function(){
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		var oldPUNo = $.trim($("#oldPUNo").val());
		
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});
			return false;
		}else if(custCode == ''){
			art.dialog({content: "请先选择客户",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("import",{
			  title:"预算导入",
			  url:"${ctx}/admin/purchase/goImport",
			  postData:{custCode:custCode,itemType1:item1,oldNo:oldPUNo},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.itemcode,
							itdescr:v.itemdescr,
							sqlcodedescr:v.suppldescr,
							color:v.color,
							allqty:v.allqty,
							qtycal:v.qty,
							unitprice:v.price,
							useqty:v.useqty,
							purqty:v.purqty,
							amount:(v.qty*v.price),
							remarks:v.remark,
							unidescr:v.uomdescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  		$("#itemType1").attr("disabled","disabled");//dateFmt:'yyyy-MM-dd'
				  		$("#arriveDate").val($("#arriveDate").val()==""?new Date().format("yyyy-MM-dd"):$("#arriveDate").val());
				  }
			  } 
		 });
	});
	
	//从销售单导入
	$("#importSale").on("click",function(){
		var item1 = $.trim($("#itemType1").val());
		var supplier = $.trim($("#supplier").val());
		var custCode = $.trim($("#custCode").val());
		var oldPUNo = $.trim($("#oldPUNo").val());
		if(item1 ==''){
			art.dialog({content: "请先选择材料类型",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("importSale",{
			  title:"销售单导入",
			  url:"${ctx}/admin/purchase/goImportSale",
			  postData:{itemType1:item1,supplier:supplier,oldNo:oldPUNo},
			  height: 680,
			  width:1000,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
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
							remarks:v.remarks,
							unidescr:v.uomdescr,
							sino:v.sino,
							custdescr:v.custdescr,
							address:v.address,
							mobile:v.mobile1,
							
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				 		$("#itemType1").attr("disabled","disabled");//dateFmt:'yyyy-MM-dd'
				  		$("#arriveDate").val($("#arriveDate").val()==""?new Date().format("yyyy-MM-dd"):$("#arriveDate").val());
				  }
			  } 
		 });
	});
	
	
	//保存
	$("#saveBtn").on("click",function(){
			var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/purchase/doPartUpdate',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
	 	});
	});
	//改变标题	
	if('${purchase.type}'=='R'){
		document.getElementById('jqgh_dataTable_qtycal').innerHTML="退回数量";
	}
	
});  
</script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){

});				

</script>
