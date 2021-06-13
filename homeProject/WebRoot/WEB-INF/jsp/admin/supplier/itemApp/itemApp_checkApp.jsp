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
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridPuFeeDetail?puno=${itemApp.puno}",
		height:$(document).height()-$("#content-list").offset().top-65,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "pk",index : "pk",width : 100,label:"pk",align : "left",hidden:true},
			{name: "supplfeetypedescr",index : "supplfeetypedescr",width : 80,label:"费用类型",align : "left"},
			{name: "carryfloor",index : "carryfloor",width : 80,label:"搬楼层数",align : "right"},
			{name: "amount",index : "amount",width : 70,label:"金额",align : "right",sum:true},
			{name: "remarks",index : "remarks",width : 120,label:"说明",align : "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "supplfeetype",index : "supplfeetype",width : 100,label:"费用类型",align : "left",hidden:true},
        ],
	});
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

function save(m_umState){
	var sumAmount=$("#dataTable").getCol('amount',false,'sum');
	var splAmount=$("#splAmount").val();
	var rets = $("#dataTable").jqGrid("getRowData");
	if(splAmount==""){
		art.dialog({
			content: "对账金额不能为空",
		});
		return;
	}
	if(m_umState=="T" && splAmount==0 && (!rets || sumAmount==0)){
		art.dialog({
			content: "账单对账金额=0，其他费用=0，请确定",
		});
		return;
	}
	var params = {"purchaseFeeDetailJson": JSON.stringify(rets),m_umState:m_umState};
	Global.Form.submit("dataForm","${ctx}/admin/supplierItemApp/doCheckApp",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1500,
				beforeunload: function () {
    				closeWin();
			    }
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
    		art.dialog({
				content: ret.msg,
				width: 200
			});
		}
		
	});
}
function d_add(){
	var ids=$("#dataTable").jqGrid("getDataIDs");
	var newId=1;
	if(ids.length>0){
		newId=parseInt(ids[ids.length-1],0)+1;
	} 
	Global.Dialog.showDialog("save", {
		title : "采购费用明细--增加",
		url : "${ctx}/admin/supplierItemApp/goAddCheckApp",
		postData:{m_umState:"A"},
	    height:350,
	    width:400,
		returnFun : function(v) {
			console.log(v);
			var json = {
				supplfeetypedescr:v.supplfeetypedescr,amount:v.amount,generatetypedescr:v.generatetypedescr,
				supplfeetype:v.supplfeetype,generatetype:v.generatetype,pk:(ids.length+1)*-1,remarks:v.remarks,
				expired:"F",actionlog:"ADD",lastupdate:new Date(),lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
				carryfloor:v.carryfloor
			};
			$("#dataTable").jqGrid("addRowData", newId, json);
		}
	});
}
function d_update(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	var ret = $("#dataTable").jqGrid('getRowData',id);
	if(!id){
		art.dialog({
			content: "请选择一条记录！",
			width: 200
		});
		return false;
	} 
	Global.Dialog.showDialog("update", {
		title : "采购费用明细--编辑",
		url : "${ctx}/admin/supplierItemApp/goUpdateCheckApp",
		postData:{map:JSON.stringify(ret),m_umState:"M"},
	    height:350,
	    width:400,
		returnFun : function(v) {
			var json = {
				supplfeetypedescr:v.supplfeetypedescr,amount:v.amount,generatetypedescr:v.generatetypedescr,
				supplfeetype:v.supplfeetype,generatetype:v.generatetype,pk:v.pk,remarks:v.remarks,
				expired:"F",actionlog:"EDIT",lastupdate:new Date(),lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
				carryfloor:v.carryfloor
			};
			$("#dataTable").setRowData(id, json);
		}
	});
}
function d_view(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	var ret = $("#dataTable").jqGrid('getRowData',id);
	if(!id){
		art.dialog({
			content: "请选择一条记录！",
			width: 200
		});
		return false;
	} 
	Global.Dialog.showDialog("view", {
		title : "采购费用明细--查看",
		url : "${ctx}/admin/supplierItemApp/goViewCheckApp",
		postData:{map:JSON.stringify(ret),m_umState:"V"},
	    height:350,
	    width:400,
	});
}
function d_del(){
 	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({
			content: "请选择一条记录进行删除操作！"
		});
		return false;
	}
	art.dialog({
		 content:"是否确认要删除？",
		 lock: true,
		 ok: function () {
			Global.JqGrid.delRowData("dataTable",id);
		},
		cancel: function () {
				return true;
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
					<c:if test="${!hasAuthToCheckComfirm}">
						<house:authorize authCode="	SUPPLIER_ITEMAPP_CHECKAPP">
							<button type="button" class="btn btn-system onlyView" id="saveBut"
							onclick="save('A')">保存</button>
						</house:authorize>
					</c:if>
					<house:authorize authCode="SUPPLIER_ITEMAPP_TOCHECKCOMFIRM">
						<button type="button" class="btn btn-system onlyView" id="saveBut"
						onclick="save('T')">提交审核</button>
					</house:authorize>
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
									name="custAddress" value="${itemApp.custAddress}" readonly="readonly" />
								</li>
							</div>
						</div>
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>面积</label> <input type="text" id="custArea"
									name="custArea" value="${itemApp.custArea}" readonly="readonly" />
								</li>
							</div>
							<div class="col-sm-6">
								<li><label>供应商结算状态</label> <house:xtdm id="puSplStatus"
										dictCode="PuSplStatus" value="${itemApp.puSplStatus}" disabled="true"></house:xtdm>
								</li>
							</div>
						</div>
						<div class="validate-group row">
							<div class="col-sm-6">
								<li><label>对账金额 </label> <input type="text" id="splAmount"
									name="splAmount" class="onlyView"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${itemApp.splAmount}" />
								</li>
							</div>
							<c:if test="${itemApp.itemType1.trim()=='JC' }">
								<div class="col-sm-6">
									<li><label>材料金额 </label> <input type="text" id="amount"
											name="amount" class="onlyView"
											onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
											value="${itemApp.amount}" />
									</li>
								</div>
							</c:if>
						</div>
						<div  class="validate-group row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">结算审核说明</label>
								<textarea id="checkConfirmRemarks" name="checkConfirmRemarks" readonly 
									style="height: 80px;">${itemApp.checkConfirmRemarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">采购费用明细</a>
				</li>
				<li class=""><a href="#tab_item" data-toggle="tab">材料明细</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system onlyView" onclick="d_add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system onlyView" onclick="d_update()">
										<span>编辑 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system onlyView" onclick="d_del()">
										<span>删除 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system " onclick="d_view()">
										<span>查看 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
				<div id="tab_item" class="tab-pane fade">
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
