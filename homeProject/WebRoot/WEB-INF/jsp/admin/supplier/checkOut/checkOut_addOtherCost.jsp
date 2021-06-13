<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>发货详情</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">	
	$(function(){
		var isEdit=true
		if("${purchase.splStatus}"=="2"||"${purchase.m_umState}"=="V"){
			 isEdit=false;
			 $("#saveBtn").attr("disabled",true);
		}
		/**初始化表格*/
		Global.JqGrid.initEditJqGrid("dataTable",{
			url:"${ctx}/admin/supplierCheckOut/goPurchaseFeeDetailGrid",
			postData:{no:"${purchase.no}",splcode:"${purchase.splcode}"},
			cellEdit:isEdit,
			cellsubmit:'clientArray', 
			height:260,
			rowNum:10000,
			colModel : [
				{name: "pk", index: "pk", width: 84, label: "pk",  align: "left", hidden: true},
				{name: "puno", index: "puno", width: 117, label: "采购单号", align: "left", hidden: true},
				{name: "supplfeetypedescr", index: "supplfeetypedescr", width: 117, label: "费用类型",  align: "left"},
				{name: "supplfeetype", index: "supplfeetype", width: 117, label: "费用类型",  align: "left", hidden: true},
				{name: "amount", index: "amount", width: 80, label: "金额",editable: true,editrules: {number:true,required:true}, align: "right",sum :true},
				{name: "remarks", index: "remarks", width: 400, label: "备注", align: "left",editable:true,edittype:'textarea'}
            ],
		});
		 //保存
		$("#saveBtn").on("click",function(){
			$("#page_form").bootstrapValidator('validate');
			if(!$("#page_form").data('bootstrapValidator').isValid()) return;
			var ids = $("#dataTable").getDataIDs();
			if((!ids || ids.length == 0)){
				art.dialog({content: "无费用明细，无需保存！",width: 200});
				return false;
			}
			var sumAmount = $("#dataTable").getCol("amount",false,"sum");
			var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("page_form","${ctx}/admin/supplierCheckOut/doSaveOtherCost",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							Global.Dialog.returnData =JSON.parse(sumAmount);
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
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="page_form" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>采购单号</label>
								<input type="text" id="no" name="no" value="${purchase.no}"  readonly="readonly"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="no" name="address" value="${purchase.address}"  readonly="readonly"/>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">其他费用明细</a></li>
		    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>	
