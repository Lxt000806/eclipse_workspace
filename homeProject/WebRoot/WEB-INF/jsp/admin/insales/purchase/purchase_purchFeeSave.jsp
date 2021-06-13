<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购费用</title>
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
  			<div class="panel panel-info" >  
				<div class="panel-body">
			  		<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="m_umState" id="m_umState" value="${purchaseFee.m_umState }"/>
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >	
								<li>
									<label>编号</label>
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" value="${purchaseFee.no }" readonly="readonly"/>                                             
								</li>
								<li>
									<label><span class="required">*</span>发票状态</label>
									<house:xtdm id="billStatus" dictCode="BILLSTATUS" value="${purchaseFee.billStatus }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row" >	
								<li>
									<label>采购单号</label>
									<input type="text" id="puno" name="puno" style="width:160px;" value="${purchaseFee.puno }"/>                                             
								</li>
								<li>
									<label><span class="required">*</span>柜号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchaseFee.whcode }"/>                                             
								</li>
							</div>
							<div class="validate-group row" >	
								<li>
									<label>总费用</label>
									<input type="text" id="amount" name="amount" style="width:160px;" value="${purchaseFee.amount }" readonly="true"/>                                             
								</li>
								<li>
									<label>到货单号</label>
									<input type="text" id="arriveNo" name="arriveNo" style="width:160px;" value="${purchaseFee.arriveNo }" readonly="true"/>                                             
								</li>
							</div>
							<div class="validate-group row" >	
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${purchaseFee.remarks}</textarea>
	  							</li>
							</div>
						</ul>
  					</form>
  				</div>
  			</div>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="update" >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="delDetail">
						<span>删除</span>
					</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
		      	<li class="active">
		      		<a data-toggle="tab">采购费用明细</a>
		      	</li>
		   	 </ul>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function(){
	$("#puno").openComponent_purchase({showValue:"${purchaseFee.puno}",readonly:true});	

	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		url:"${ctx}/admin/purchase/getPurchFeeDetail",
		postData:{no:"${purchaseFee.no }"},
		rowNum:10000000,
		styleUI: 'Bootstrap',
		loadonce:true,
		colModel : [
			{name:'puno', index:'puno', width:80, label:'采购单号', sortable:true ,align:"left",hidden:true},
			{name:'no', index:'no', width:80, label:'采购单号', sortable:true ,align:"left",hidden:true},
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'feetype', index:'feetype', width:80, label:'费用类型', sortable:true ,align:"left",hidden:true},
			{name:'feetypedescr', index:'feetypedescr', width:80, label:'费用类型', sortable:true ,align:"left",},
			{name:'generatetype', index:'generatetype', width:80, label:'生成方式', sortable:true ,align:"left",hidden:true},
			{name:'generatetypedescr', index:'generatetypedescr', width:80, label:'生成方式', sortable:true ,align:"left",hidden:true},
			{name:'amount', index:'amount', width:180, label:'金额', sortable:true ,align:"left",},
			{name:'remarks', index:'remarks', width:80, label:'备注', sortable:true ,align:"left",},
			{name:'lastupdate', index:'lastupdate', width:80, label:'最后修改时间', sortable:true ,align:"left",formatter:formatDate},
			{name:'lastupdatedby', index:'lastupdatedby', width:80, label:'最后修改人', sortable:true ,align:"left",},
			{name:'actionlog', index:'actionlog', width:80, label:'actionlog', sortable:true ,align:"left",hidden:true},
		],
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	//新增
	$("#add").on("click",function(){
		Global.Dialog.showDialog("save",{
			title:"新增费用明细",
			url:"${ctx}/admin/purchase/goAddPurchFee",
			postData:{},
			height: 320,
			width: 680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							amount:v.amount,
							feetype:v.feeType,
							feetypedescr:v.feeTypeDescr,
							generatetype:"",
							generatetypedescr:"",
							remarks:v.remarks,
							lastupdatedby:"${purchaseFee.lastUpdatedBy}",
							lastupdate:new Date(),
							actionlog:"ADD",
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
				  	});
				  	console.log($("#dataTable").getCol("amount",false,"sum"))
				  	$("#amount").val($("#dataTable").getCol("amount",false,"sum"));
			  	}
		  	} 
	 	});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({
				content:"请选择一条费用明细",
			});
		}
		Global.Dialog.showDialog("save",{
			title:"费用明细修改",
			url:"${ctx}/admin/purchase/goAddPurchFee",
			postData:{feeType:ret.feetype,amount:ret.amount,remarks:ret.remarks},
			height: 320,
			width: 680,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							amount:v.amount,
							feetype:v.feeType,
							feetypedescr:v.feeTypeDescr,
							generatetype:"",
							generatetypedescr:"",
							remarks:v.remarks,
							lastupdatedby:"${purchaseFee.lastUpdatedBy}",
							lastupdate:new Date(),
							actionlog:"ADD",
					  	};
			   			$('#dataTable').setRowData(rowId,json);
				  	});
				  	$("#amount").val($("#dataTable").getCol("amount",false,"sum"));
			  	}
		  	} 
	 	});
	});
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);
		$("#amount").val($("#dataTable").getCol("amount",false,"sum"));
	});
	//保存
	$("#saveBtn").on("click",function(){
		var param= Global.JqGrid.allToJson("dataTable");
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==''){
			art.dialog({
				content:'费用明细表为空，不能保存',
			});
			return false;
		}
		if($.trim($("#whcode").val())==""){
			art.dialog({
				content:"请输入柜号",
			});
			return;
		}
		if($.trim($("#billStatus").val())==""){
			art.dialog({
				content:"请选择发票状态",
			});
			return;
		}
		
		Global.Form.submit("page_form","${ctx}/admin/purchase/doPurchFeeSave",param,function(ret){
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
</script>
  </body>
</html>

















