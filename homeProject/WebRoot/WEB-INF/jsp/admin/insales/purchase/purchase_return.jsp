<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购退回</title>
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
  			<!-- edit-form -->
   			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
							<li>
								<label><span class="required">*</span>采购单号</label>
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" value="${purchase.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label>原采购单号</label>
									<input type="text" id="oldNo" name="oldNo" style="width:160px;" value="${purchase.oldNo }"/>
							</li>
							<li>
								<label>采购类型</label>
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label><span class="required">*</span>采购日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>到货日期</label>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${purchase.arriveDate }" />
							</li>
							<li>
								<label><span class="required">*</span>仓库编号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
							</li>
							<li>
								<label>单据状态</label>
									<house:xtdm id="status" dictCode="PURCHSTATUS"  value="OPEN" disabled='true'></house:xtdm>                     
								</li>
							<li>
								<label>下单员</label>
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/> 
							</li>
							<li>
								<label><span class="required">*</span>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;" disabled="true">
									</select>
							</li>
							<li>
								<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost } "readonly= 'readonl' />
							</li>
							<li>
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }" readonly="readonly"/>
							</li>
							<li>
								<label>其他费用调整</label>
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonly' />
								</li>
							<li>
								<label><span class="required">*</span>供应商编号</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier } "  readonly="true"/>
								</li>
							<li>
								<label>材料总价</label>
									<input type="text" id="amount" name="amount"  style="width:160px;" value="${purchase.amount }" readonly= "readlony" />
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
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"  placeholder="结账时自动生成" value="${purchase.checkOutNo }" readonly='readonly'/>
							</li>
							<li>
								<label>已付到货款</label>
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
							</li>
							<li>
								<label>应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;" 
									onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
  							</li>
  						</ul>	
  				</form>
  				</div>
  			</div><!-- edit-form end -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system " id="add">
									<span>新增</span></button>
								<button type="button" class="btn btn-system " id="fastAdd">
									<span>快速新增</span></button>
								<button type="button" class="btn btn-system " id="update">
									<span>编辑</span></button>
								<button type="button" class="btn btn-system " id="delDetail">
									<span>删除</span></button>
								<button type="button" class="btn btn-system " id="view">
									<span>查看</span></button>
								<button type="button" class="btn btn-system " id="import">
									<span>从预算导入</span></button>
								<button type="button" class="btn btn-system " id="importSale">
									<span>从销售导入</span></button>
								<button type="button" class="btn btn-system " onclick="doExcelNow('采购明细单')" title="导出当前excel数据" >
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
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){
	if('${costRight}'=='0'){
		document.getElementById('amount').setAttribute('type','password') ;
		document.getElementById('firstAmount').setAttribute('type','password') ;
		document.getElementById('secondAmount').setAttribute('type','password') ;
		document.getElementById('remainAmount').setAttribute('type','password') ;
	}
	
	 function getData(data){
		if (!data) return;
		if(data.itemtype1==null){
			var dataSet = {
				firstSelect:"itemType1",
				firstValue:data.itemType1,
				disabled:true
			}
		}else{
			var dataSet = {
				firstSelect:"itemType1",
				firstValue:data.itemtype1,
				disabled:true
			};
		}
		Global.LinkSelect.setSelect(dataSet);
		if(data.custcode==null){
			$("#custCode").openComponent_customer({showValue:data.custCode,disabled:true});
			$('#custCode').val(data.custCode);
		}else{
			$("#custCode").openComponent_customer({showValue:data.custcode,disabled:true});
			$('#custCode').val(data.custcode);
		}
		$('#supplier').val(data.supplier);
		$('#whcode').val(data.whcode);
		//$("#custCode").openComponent_customer({showValue:data.custcode,disabled:true});
		$("#supplier").openComponent_supplier({showValue:data.supplier,showLabel:data.supdescr,disabled:true});
		$("#whcode").setComponent_wareHouse({showValue:data.whcode,showLabel:data.whcodedescr});
	}
	$("#custCode").openComponent_customer({disabled:true});
	$("#supplier").openComponent_supplier({disabled:true});
	$("#whcode").openComponent_wareHouse({condition:{delXNCK:'1',czybh:'${czybh}'}});
	$("#applyMan").openComponent_employee();
	$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}' ,readonly:true});
	$("#oldNo").openComponent_purchase({callBack: getData,condition: {status:'CONFIRMED',type:'S'}});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	
	//初始化表格  材料编号、材料名称 品牌、颜色、当前库存量、采购数量、已到货、本次到货、单位，备注
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'itemType2', index:'itemType2', width:80, label:'itemType2', sortable:true ,align:"left",hidden:true},
			{name:'itcode', index:'itcode', width:80, label:'产品编号', sortable:true ,align:"left"},
			{name:'itdescr', index:'itdescr', width:220, label:'材料名称', sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr', index:'sqlcodedescr', width:80, label:'品牌', sortable:true ,align:"left"},
			{name:'color', index:'color', width:80, label:'颜色', sortable:true ,align:"left",},	
			{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"left",sum:true},
			{name:'qtycal',	index:'qtycal',width:75, label:'退回数量', 	editable:true,sum:true,	editrules: {number:true,required:true},sortable : true,align : "left"},
			{name:'useqty',	index:'useqty',width:60, label:'可用量', sortable : true,align : "left",sum:true},
			{name:'purqty',	index:'purqty',width:80, label:'在途采购量',sortable : true,align : "left",sum:true},
			{name:'unidescr', index:'unidescr', width:45, label:'单位', sortable:true ,align:"left"},
			{name:'unitprice', index:'unitprice', width:45, label:'单价', align : "left"},
			{name:'markup', index:'markup', width:45, label:'折扣', align : "left"},
			{name:'beflineamount', index:'beflineamount', width:90, label:'折扣前金额', align : "left"},
			{name:'beflineprice', index:'beflineprice', width:45, label:'折扣前单价', align : "left",},
			{name:'amount', index:'amount', width:60, label:'总价',sum:true, sortable:true ,align:"left"},
			{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left",editable:true,},
			{name:'importpk',index:'importpk',width:150, label:'importpk', 	sortable:true,align:"left",hidden:true},
		],
			gridComplete:function(){
				  var a=myRound($("#dataTable").getCol('amount',false,'sum'),4); 
				   if(a)  $("#amount").val(a);
	             		  $("#remainAmount").val(-a);
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
	                		$("#dataTable").setCell(id,'amount',myRound(val*rowData.unitprice,2));
	                		$("#dataTable").setCell(id,'beflineamount',myRound(val*rowData.beflineprice,2));
	                	break;
	                	case 'unitprice':
	                		$("#dataTable").setCell(id,'amount',myRound(val*rowData.qtycal,2));
	                	break;
                	}
					 var a=myRound($("#dataTable").getCol('amount',false,'sum'),4); 
				  	 if(a){  $("#amount").val(a);
	             		    $("#remainAmount").val(-a);}
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
			jQuery("#dataTable").setGridParam().hideCol("unitprice").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("beflineprice").trigger("reloadGrid");
		}
	//新增
	$("#add").on("click",function(){
		var item2=new Array();
		var item1 = $.trim($("#itemType1").val());
		var oldNo = $.trim($("#oldNo").val());
		if(oldNo ==''){
			art.dialog({content: "请选择原采购单号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			   title:"采购明细——增加",
			  url:"${ctx}/admin/purchase/goReturnAdd",
			  postData:{itemtype1:item1,puno:oldNo},
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
							useqty:v.useqty,
							purqty:v.purqty,
							unitprice:v.unitPrice,
							amount:v.amount,
							remarks:v.remarks,
							unidescr:v.uniDescr,
							itemType2:v.itemType2,
							markup:v.markup,
							beflineprice:v.befLinePrice,
							beflineamount:v.befLineAmount,
						  };
						  item2.push(v.itemType2);
						  Global.JqGrid.addRowData("dataTable",json);
					  });
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
						  		$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
						    }
						 });
				  }
			  } 
		 });
	}); 
	
	//快速新增
	$("#fastAdd").on("click",function(){
		var item2=new Array();
		var item1 = $.trim($("#itemType1").val());
		var oldNo = $.trim($("#oldNo").val());
		var supplier = $.trim($("#supplier").val());
		if(oldNo ==''){
			art.dialog({content: "请选择原采购单号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("save",{
			  title:"采购明细——增加",
			  url:"${ctx}/admin/purchase/goReturnFastAdd",
			  postData:{itemType1:item1,supplier:supplier,oldNo:oldNo},
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
							itemType2:v.itemtype2,
							markup:v.pumarkup,
							beflineprice:v.pubeflineprice,
							beflineamount:0,
						  };
						  item2.push(v.itemtype2);
						  Global.JqGrid.addRowData("dataTable",json);
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
						  		$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
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
		var oldNo = $.trim($("#oldNo").val());
		var custCode = $.trim($("#custCode").val());
		var itcode = Global.JqGrid.allToJson("dataTable","importpk");
		var arr = new Array();
			arr = itcode.fieldJson.split(",");
		if(oldNo ==''){
			art.dialog({content: "请选择原采购单号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("import",{
			  title:"领料明细——增加",
			  url:"${ctx}/admin/purchase/goReturnImport",
			  postData:{custCode:custCode,itemType1:item1,oldNo:oldNo,arr:arr},
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
							useqty:v.useqty,
							purqty:v.purqty,
							qtycal:v.qty,
							unitprice:v.cost,
							amount:(v.qty*v.cost),
							remarks:v.remarks,
							unidescr:v.uomdescr,
							itemType2:v.itemtype2,
							importpk:v.pk,
							markup:v.pumarkup,
							beflineprice:v.pubeflineprice,
							beflineamount:v.pubeflineprice * v.qty* v.pumarkup,
						  };
						  item2.push(v.itemtype2);
						  Global.JqGrid.addRowData("dataTable",json);
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
						  		$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
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
		var oldNo = $.trim($("#oldNo").val());
		if(oldNo ==''){
			art.dialog({content: "请选择原采购单号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("importSale",{
			  title:"销售明细——增加",
			  url:"${ctx}/admin/purchase/goImportSale1",
			  postData:{itemType1:item1,oldNo:oldNo},
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
							useqty:v.useqty,
							purqty:v.purqty,
							unitprice:v.cost,
							amount:(v.qty*v.cost),
							remarks:v.remarks,
							unidescr:v.uomdescr,
							itemType2:v.itemtype2,
							markup:1,
							beflineprice:v.cost,
							beflineamount:(v.qty*v.cost),
						  };
						  item2.push(v.itemtype2);
						  Global.JqGrid.addRowData("dataTable",json);
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
						  		$("#arriveDate").val($("#arriveDate").val()==""?addDate(obj.arriveDay):$("#arriveDate").val());
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
				url:"${ctx}/admin/purchase/goAddUpdateReturn",
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,uniDescr:ret.unidescr,befLinePrice:ret.beflineprice,
					unitPrice:ret.unitprice,amount:ret.amount,sqlCodeDescr:ret.sqlcodedescr,
					itdescr:ret.itdescr,color:ret.color,puno:$('#oldNo').val(),itemtype1:$('#itemType1').val()
					,markup:ret.markup},
				height:700,
				width:1000,
				returnFun:function(data){
					// var id = $("#dataTable").jqGrid("getGridParam","selrow");
					//	 Global.JqGrid.delRowData("dataTable",id);
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
							color:v.color,
							beflineprice:v.beflineprice,
							beflineamount:v.befLineAmount,
							markup:v.markup,
						  };
		   				$('#dataTable').setRowData(rowId,json);
					//	  Global.JqGrid.addRowData("dataTable",json);
					  });
					  var a=myRound($("#dataTable").getCol('amount',false,'sum'),4); 
				  	  if(a){  
				  	  	  $("#amount").val(a);
	             		  $("#remainAmount").val(-a);
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
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,remarks:ret.remarks,
								unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr,markup:ret.markup},
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
		if(!$("#dataForm").valid()) {return false;}//表单校验
		if($("#infoBoxDiv").css("display")!='none'){return false;}
		var countqtycal = Global.JqGrid.allToJson("dataTable","qtycal");
		var item1 = $.trim($("#itemType1").val());
			arry = countqtycal.fieldJson.split(",");	
			var x = 1;
			if($.trim($("#itemType1").val())==''&&$.trim($("#itemType1").val())==null){
				art.dialog({
            		content: "材料类型1为空，无法保存"
            	});
            	return;
			}
			for(var i = 0;i < arry.length; i++){
				if(parseFloat(arry[i])<0){
				art.dialog({
            		content: "存在采购数量为零的采购明细单，不允许保存"
            	});
            	return;
			}
					x = x * parseFloat(arry[i]);
				}
			if(x==0){
				art.dialog({
            		content: "存在采购数量为零的采购明细单，不允许保存"
            	});
            	return;
			}
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
		});
}); 
function addDate(days){ 
   var d=new Date(); 
   d.setDate(d.getDate()+days); 
   var m=d.getMonth()+1; 
   return d.getFullYear()+'-'+m+'-'+d.getDate(); 
} 
	
</script>
  </body>
</html>

















