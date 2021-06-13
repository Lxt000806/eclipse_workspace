<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>结算申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("itemDataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridItemDetail?no=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "itemcode",index : "itemcode",width : 80,label:"材料编号",align : "left"},
			{name: "itemdescr",index : "itemdescr",width : 250,label:"材料名称",align : "left"},
			{name: "fixareadescr",index : "fixareadescr",width : 80,label:"区域",align : "left"},
			{name: "qty",index : "qty",width : 50,label:"数量",align : "right"},
			{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 250, label: "备注", sortable: true, align: "left"},
        ],
	});
	var puSplStatus="${itemApp.puSplStatus}";
	var checkStatus="${itemApp.checkStatus}";
	if(puSplStatus=="2" || checkStatus.trim()=="2"){
		$(".onlyView").attr("disabled",true);
	}
});

function save(){
	var splAmount=$("#splAmount").val();
	var amount=$("#amount").val();
	var canPass="1";
	var remarks="";
	if(splAmount==""){
		art.dialog({
			content: "对账金额不能为空！",
			width: 200
		});
		return;
	}
	var otherCost = $("#otherCost").val();

	if(isNaN(parseFloat(otherCost))){
		art.dialog({
			content: "其他费用输入不正确!"
		});
		return;
	}
	
	//是否满足自动审核通过
	if(parseFloat(splAmount) < parseFloat(amount)){
		canPass="0";
		remarks="材料对账金额不符";
	}
	if(parseFloat(otherCost) != 0){
		canPass="0";
		remarks="费用金额超标准";
	}
	
	var otherCostAdj=parseFloat(splAmount)-parseFloat(amount);
	
	$.ajax({
		url : "${ctx}/admin/supplierItemAppReturn/doCheckApp",
		type : "post",
		data : {
			puno:$("#puno").val(),splAmount:$("#splAmount").val()*-1,otherCost: otherCost,
			canPass:canPass,remarks:remarks,otherCostAdj:otherCostAdj*-1
		},
		dataType : "json",
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : "保存数据出错~"
			});
		},
		success : function(obj) {
			if (obj.rs) {
				art.dialog({
					content : obj.msg,
					time : 1000,
					beforeunload : function() {
						closeWin();
					}
				});
			} else {
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content : obj.msg,
					width : 200
				});
			}
		}
	});
}

</script>

</head>
    
<body onunload="closeWin()">
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system onlyView" id="saveBut"
						onclick="save()">提交审核</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>

		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" id="amount" name="amount" value="${itemApp.amount}"  />
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>领料单号 </label> <input type="text" id="no"
									name="no" value="${itemApp.no}" readonly="readonly" />
								</li>
							</div>
							<div class="col-sm-6">
								<li><label>单据状态 </label> <house:xtdm id="status"
										dictCode="ITEMAPPSTATUS" value="${itemApp.status}"
										disabled="true"></house:xtdm>
								</li>
							</div>
						</div>
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>采购单号 </label> <input type="text" id="puno"
									name="puno" value="${itemApp.puno}" readonly="readonly" />
								</li>
							</div>
							<div class="col-sm-6">
								<li><label>楼盘地址</label> <input type="text" id="custAddress"
									name="phone" value="${itemApp.custAddress}" readonly="readonly" />
								</li>
							</div>
						</div>
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>供应商结算状态</label> <house:xtdm id="puSplStatus"
										dictCode="PuSplStatus" value="${itemApp.puSplStatus}" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="col-sm-6">
								<li><label>退款金额 </label> <input type="text" id="splAmount"
									name="splAmount" class="onlyView"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${itemApp.amount>=0?itemApp.amount:itemApp.amount*-1}" />
								</li>
							</div>
						</div>
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>其他费用 </label> <input type="text" id="otherCost"
									name="otherCost" class="otherCost"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\-\d]/g,'');"
									value="${itemApp.otherCost}" />
								</li>
							</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_item" data-toggle="tab">材料明细</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_item" class="tab-pane fade in active">
					<div class="pageContent">
						<div id="content-list">
							<table id="itemDataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
