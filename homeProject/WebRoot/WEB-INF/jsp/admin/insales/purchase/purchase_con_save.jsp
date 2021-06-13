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
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
  			<div class="panelBar">
  				<ul>
  					<li>
  						<a href="javascript:void(0)" class="a1" id="saveBtn">
  							<span>保存</span>
  						</a>
  					</li>
  					<li id="closeBut" onclick="closeWin(false)">
  						<a href="javescript:void(0)" class="a2">
  							<span>关闭</span>
  						</a>		
  					</li>
  					<li class="line"></li>
  				</ul>
  				<div class="clear_float"></div>
  			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="edit-form" id="edit-form">
  				<form action="" method="post" id="dataForm">
  					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<table cellspacing="0" cellpadding="0" width="100%">
						<col width="72"/>
						<col width="150"/>
						<col width="72"/>
						<col width="150"/>
						<tbody>
								<tr >
								<td class="td-label"><span class="required">*</span>采购单号</td>
								<td class="td-value">
									<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${purchase.no }" readonly="readonly"/>                                             
								</td> 
								<td class="td-label">采购类型</td>
								<td class="td-value">
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
								</td>
							</tr>
							<tr>
								<td class="td-label">单据状态</td>
								<td class="td-value">
									<house:xtdmMulit id="status" dictCode="PURCHSTATUS" selectedValue="${purchase.status}"></house:xtdmMulit>                     
								</td>
								<td class="td-label">下单员</td>
								<td class="td-value">
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" /> 
								</td>
							</tr>
							<tr>
								<td class="td-label">采购日期</td>
								<td class="td-value">
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" />
								</td>
								<td class="td-label">到货日期</td>
								<td>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${purchase.arriveDate }" />
								</td>
							</tr>
							<tr>
								<td class="td-label">材料类型1</td>
								<td class="td-value">
									<select id="itemType1" name="itemType1" style="width: 166px;" value="${purchase.itemType1 }">
									</select>
								</td>
								<td class="td-label">材料总价</td>
								<td class="td-value">
									<input type="text" id="amount" name="amount"  style="width:160px;" 
											onblur="changeAmount()"  value="${purchase.amount }" readonly="readonly" />
								</td>
								
							</tr>
							<tr>
								<td class="td-label">客户编号</td>
								<td class="td-value" > 
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</td>
								<td class="td-label">其他费用</td>
								<td class="td-value">
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost } "readonly= 'readonl' />
								</td>
								
							</tr>
							<tr>
								<td class="td-label">供应商编号</td>
								<td class="td-value"> 
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</td>
								<td class="td-label">其他费用调整</td>
								<td class="td-value">
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
								</td>
							</tr>
							<tr>
								<td class="td-label">仓库编号</td>
								<td class="td-value" > 
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</td>
								<td class="td-label">配送地点</td>
								<td class="td-value">
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="${ purchase.delivType}" disabled='true'></house:xtdm>                     

								</td>
							</tr>
							<tr>
								<td class="td-label">是否结账</td>
								<td class="td-value">
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="${purchase.isCheckOut}" disabled='true'></house:xtdm>                     
								</td>
								
								<td class="td-label">已付定金</td>
								<td class="td-value">
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">结账单号</td>
								<td class="td-value" > 
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;"  placeholder="结账时生成" value="${purchase.checkOutNo }" readonly='readonly'/>
								</td>
								<td class="td-label">已付到货款</td>
								<td class="td-value" > 
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">销售单号</td>
								<td class="td-value" > 
									<input type="text" id="SINo" name="SINo" style="width:160px;" value="${purchase.sino }"/>
								</td>
								<td class="td-label"> 应收余款</td>
								<td class="td-value">
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;"
									 		onblur="changeAmount()" value="${purchase.remainAmount }"  readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">备注</td>
								<td class="td-value" colspan="1">
								<textarea id="remarks" name="remarks" rows="3">${purchase.remarks }</textarea>
								</td>
							</tr>
						</tbody>
					</table>
  				</form>
  			</div><!-- edit-form end -->
			<div id="tabs">
			<ul>
				<li><a href="#tabs-1">采购明细单</a></li>
			</ul>	
			<div class="panelBar">
		 			   <ul>
		                	<li>
								<a href="javascript:void(0)" class="a1" id="add">
									<span>新增</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" class="a1" id="fastAdd">
									<span>快速新增</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" class="a1" id="update">
									<span>编辑</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" class="a2" id="delDetail">
									<span>删除</span>
								</a>
							<li>
								<a href="javascript:void(0)" class="a1" id="view">
									<span>查看</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" class="a1" id="import">
									<span>从预算导入</span>
								</a>
							</li>
							<li>
								<a href="javascript:void(0)" class="a1" id="importSale">
									<span>从销售导入</span>
								</a>
							</li>							
							<li>
								<a href="javascript:void(0)" class="a1" onclick="doExcelNow('基础增减明细','dataTable','page_form_excel')">导出excel</a>
							</li>
                   			
		                </ul>		   
			</div>	
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
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
var unitprice;
var qtycal;
var amount=0;
var y;
$(function(){
	
	function getData(data){
		if (!data) return;
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:data.itemtype1,
			secondSelect:"itemType2",
			secondValue:data.itemtype2,
		};
			Global.LinkSelect.setSelect(dataSet);
	}
	
	$("#SINo").openComponent_salesInvoice();
	$("#applyMan").openComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}' ,readonly:true});
	$("#supplier").openComponent_supplier({callBack: getData});
	$("#custCode").openComponent_customer();
	$("#whcode").openComponent_wareHouse({condition:{delXNCK:'1',readonly:true}});
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	$("#openComponent_wareHouse_whcode").attr("readonly",true);
	//初始化表格  材料编号、材料名称 品牌、颜色、当前库存量、采购数量、已到货、本次到货、单位，备注
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
			{name:'puno', index:'puno', width:80, label:'puno', sortable:true ,align:"left",hidden:true},
			{name:'itcode', index:'itcode', width:80, label:'产品编号', sortable:true ,align:"left"},
			{name:'itdescr', index:'itdescr', width:250, label:'材料名称', sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr', index:'sqlcodedescr', width:80, label:'品牌', sortable:true ,align:"left"},
			{name:'color', index:'color', width:80, label:'颜色', sortable:true ,align:"left"},	
			{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"left",sum:true},
			{name:'qtycal',	index:'qtycal',width:60, label:'采购数量', 	editable:true,	editrules: {number:true,required:true},sortable : true,align : "left",sum:true},
			{name:'unidescr', index:'unidescr', width:40, label:'单位', sortable:true ,align:"left"},
			{name:'unitprice', index:'unitprice', width:40, label:'单价', editable:true,	editrules: {number:true,required:true},sortable : true,align : "left"},
			{name:'amount', index:'amount', width:60, label:'总价', sortable:true ,align:"left",sum:true},
			{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left"},
		],
			gridComplete:function(){
				  var a=myRound($("#dataTable").getCol('amount',false,'sum'),4); 
				   if(a)  $("#amount").val(a);
	             		  $("#remainAmount").val(a);
	               },
	         beforeSaveCell:function(rowId,name,val,iRow,iCol){
 				var rowData = $("#dataTable").jqGrid("getRowData",rowId);
 				var xxx= rowData.qtycal;
		    	if(val<=0){
		    		art.dialog({
						content:"采购数量和单价必须为正数，请重新输入",
					});
					return String(1);
		    	}
			},      
			afterSaveCell:function(id,name,val,iRow,iCol){
				var rowData = $("#dataTable").jqGrid("getRowData",id);
					switch (name){
	                	case 'qtycal':
	                		$("#dataTable").setCell(id,'amount',val*rowData.unitprice);
	                	break;
	                	case 'unitprice':
	                		$("#dataTable").setCell(id,'amount',val*rowData.qtycal);
	                	break;
                	}
					 var a=myRound($("#dataTable").getCol('amount',false,'sum'),4); 
				  	 if(a)  $("#amount").val(a);
	             		    $("#remainAmount").val(a);
			},
	};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	//新增
	$("#add").on("click",function(){
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
			  title:"采购入库-新增",
			  url:"${ctx}/admin/purchase/goAdd",
			  postData:{itemtype1:item1},
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
							amount:v.amount,
							remarks:v.remarks,
							unidescr:v.uniDescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  		$("#itemType1").attr("disabled","disabled");//dateFmt:'yyyy-MM-dd'
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
			  postData:{itemType1:item1,supplier:supplier},
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
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"查看采购信息",
				url:"${ctx}/admin/purchase/goAddUpdate",
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr},
				height:700,
				width:1000,
				returnFun:function(data){
					 var id = $("#dataTable").jqGrid("getGridParam","selrow");
						Global.JqGrid.delRowData("dataTable",id);
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.itCode,
							itdescr:v.itdescr,
							sqlcodedescr:v.sqlCodeDescr,
							color:v.color,
							allqty:v.allqty,
							qtycal:v.qtyCal,
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
				postData:{itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr},
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
		var countqtycal = Global.JqGrid.allToJson("dataTable","qtycal");
			arry = countqtycal.fieldJson.split(",");	
			var x = 1;
				for(var i = 0;i < arry.length; i++){
					x = x * parseFloat(arry[i]);
				}
			if(x==0){
				art.dialog({
            		content: "存在采购数量为零的采购明细单，不允许保存"
            	});
            	return;
			}
			//var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
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
	//从预算导入
	$("#import").on("click",function(){
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
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("import",{
			  title:"预算导入",
			  url:"${ctx}/admin/purchase/goImport",
			  postData:{custCode:custCode,itemType1:item1},
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
							amount:v.beflineamount,
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
		if(item1 ==''){
			art.dialog({content: "请先选择材料类型",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable","no");
		Global.Dialog.showDialog("importSale",{
			  title:"销售单导入",
			  url:"${ctx}/admin/purchase/goImportSale",
			  postData:{itemType1:item1,sqlcode:supplier},
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
							unitprice:v.unitprice,
							amount:v.beflineamount,
							remarks:v.remarks,
							unidescr:v.uomdescr,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				 		$("#itemType1").attr("disabled","disabled");
				  		$("#arriveDate").val($("#arriveDate").val()==""?new Date().format("yyyy-MM-dd"):$("#arriveDate").val());
				  }
			  } 
		 });
	});
});

</script>
  </body>
</html>

















