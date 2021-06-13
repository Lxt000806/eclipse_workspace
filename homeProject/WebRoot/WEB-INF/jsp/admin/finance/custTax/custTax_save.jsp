<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="custDescr" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label for="custCode"><span class="required">*</span>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label for="address">楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" readonly="readonly">
						</li>
						<li>
							<label for="status">客户状态</label>
							<house:xtdm id="status" dictCode="CUSTOMERSTATUS"></house:xtdm>
						</li>
						<li>
							<label for="setDate">下定时间</label>
							<input type="text" id="setDate" name="setDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" disabled />
						</li>
						<li>
							<label for="signDate">签订时间</label>
							<input type="text" id="signDate" name="signDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" disabled />
						</li>
						<li>
							<label for="custCheckDate">客户结算时间</label>
							<input type="text" id="custCheckDate" name="custCheckDate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="" disabled />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#custTax_info" data-toggle="tab">基本信息</a></li>  
			    <li><a href="#custInvoice_laborInvoice" data-toggle="tab">劳务分包开票</a></li>
			    <li><a href="#custInvoice_save" data-toggle="tab">开票登记</a></li>
			    <li><a href="#custPay_tabView" data-toggle="tab">付款信息</a></li> 
			</ul>
		    <div class="tab-content">  
				<div id="custTax_info"  class="tab-pane fade in active"> 
					<div class="pageContent" style="height: 330px;">
	         			<form action="" method="post" id="page_form_1" class="form-search" style="padding-top: 25px;padding-left: 15px;">
							<ul class="ul-form">
								<li>
									<label for="documentNo">档案号(税务)</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;"/>
								</li>
								<li>
									<label for="payeeCode">收款单位</label>
									<house:dict id="payeeCode" dictCode="" 
										sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
										sqlLableKey="descr" sqlValueKey="code"></house:dict>
								</li>
								<li>
									<label for="laborCompny">劳务分包公司</label>
									<house:dict id="laborCompny" dictCode="" 
										sql="select rtrim(Code) Code,rtrim(Code)+' '+rtrim(Descr) cd from tLaborCompny where expired='F'" 
										sqlValueKey="Code" sqlLableKey="cd" value=""/>
								</li>
								<li>
									<label for="laborAmount">劳务分包金额</label>
									<input type="text" id="laborAmount" name="laborAmount" style="width:160px;"
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="0" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label for="isPubReturn"><span class="required">*</span>对公退款</label>
									<house:xtdm id="isPubReturn" dictCode="YESNO"></house:xtdm>
								</li>
								<li>
									<label for="returnAmount">退款金额</label>
									<input type="text" id="returnAmount" name="returnAmount" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="0" />
								</li>
								<li>
									<label class="control-textarea" for="remarks">备注</label>
									<textarea id="remarks" name="remarks" rows="2" 
										style="height: 50px;"></textarea>
								</li>
							</ul>
						</form>
					</div>
				</div>
				<div id="custInvoice_laborInvoice"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="laborSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="laborUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="laborDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="laborView" onclick="laborView()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_laborInvoice"></table>
						</div>
					</div>
				</div> 
				<div id="custInvoice_save"  class="tab-pane fade"> 
					<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="invoiceSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="invoiceUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view" onclick="view()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
				<div id="custPay_tabView"  class="tab-pane fade"> 
					<div class="pageContent" >
						<div id="content-list">
							<table id="dataTable_custPay"></table>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
	$("#custCode").openComponent_customer({
		callBack:setCustData,
		condition:{
			status:"3,4,5"
		}
	});

	$("#status").prop("disabled",true);

	var originalData = $("#page_form_1").serialize();

	var gridOption = {
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 280,
		rowNum : 10000000,
		loadonce: true, 
	    pager :'1',
		colModel : [
			{name: "PK", index: "PK", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "CustCode", index: "CustCode", width: 70, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "Date", index: "Date", width: 100, label: "开票日期", sortable: true, align: "left", formatter: formatDate},
			{name: "InvoiceNo", index: "InvoiceNo", width: 70, label: "发票编号", sortable: true, align: "left"},
			{name: "InvoiceCode", index: "InvoiceCode", width: 70, label: "发票代码", sortable: true, align: "left"},
			{name: "TaxService", index: "TaxService", width: 95, label: "应税服务名称", sortable: true, align: "left"},
			{name: "Buyer", index: "Buyer", width: 70, label: "购买方", sortable: true, align: "left"},
			{name: "Amount", index: "Amount", width: 70, label: "开票金额", sortable: true, align: "right"},
			{name: "TaxPer", index: "TaxPer", width: 70, label: "税率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*100,1)+"%";}},
			{name: "NoTaxAmount", index: "NoTaxAmount", width: 80, label: "不含税金额", sortable: true, align: "right"},
			{name: "TaxAmount", index: "TaxAmount", width: 70, label: "税额", sortable: true, align: "right"},
			{name: "Remarks", index: "Remarks", width: 120, label: "备注", sortable: true, align: "left"},
			{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "LastUpdate", index: "LastUpdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "ActionLog", index: "ActionLog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "Expired", index: "Expired", width: 70, label: "过期标志", sortable: true, align: "left"}
		],
		ondblClickRow: function(){
			view();
		}
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	var postData = $("#page_form").jsonForm();
	var gridOption_custPay ={
		url:"${ctx}/admin/custTax/goCustPayJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 240,
		rowNum : 10000000,
		loadonce: true, 
	    pager :'1',
		styleUI:"Bootstrap",
		colModel : [
			{name: "adddate", index: "adddate", width: 125, label: "登记日期", sortable: true, align: "left", formatter: formatTime},
			{name: "date", index: "date", width: 125, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
			{name: "amount", index: "amount", width: 70, label: "付款金额", sortable: true, align: "right"},
			{name: "rcvact", index: "rcvact", width: 70, label: "收款账号code", sortable: true, align: "left", hidden:true},
			{name: "rcvactdescr", index: "rcvactdescr", width: 70, label: "收款账号", sortable: true, align: "left"},
			{name: "poscode", index: "poscode", width: 70, label: "POS机code", sortable: true, align: "left", hidden:true},
			{name: "posdescr", index: "posdescr", width: 120, label: "POS机", sortable: true, align: "left"},
			{name: "procedurefee", index: "procedurefee", width: 60, label: "手续费", sortable: true, align: "right"},
			{name: "payno", index: "payno", width: 90, label: "收款单号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
		]
	};

	Global.JqGrid.initJqGrid("dataTable_custPay",gridOption_custPay);
	
	var gridOption_laborInvoice ={
		url:"${ctx}/admin/custTax/goLaborJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 300,
		rowNum : 10000000,
		styleUI:"Bootstrap",
	    loadonce: true, 
	    pager :'1',
		colModel : [
			{name: "date", index: "date", width: 125, label: "劳务开票日期", sortable: true, align: "left", formatter: formatDate},
			{name: "amount", index: "amount", width: 125, label: "劳务开票金额", sortable: true, align: "right",sum:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"}
		],
		loadComplete: function(){
		  	var sumAmount=parseFloat($("#dataTable_laborInvoice").getCol('amount',false,'sum'));
		  	$("#laborAmount").val(myRound(sumAmount,2));
		}
	};

	Global.JqGrid.initJqGrid("dataTable_laborInvoice",gridOption_laborInvoice);
	
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		// excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置
		excluded:[":disabled"],// 关键配置，表示只对于禁用域不进行验证，其他的表单元素都要验证
		fields: {  
			openComponent_customer_custCode:{
				validators: {  
					notEmpty: {  
						message: "客户编号不允许为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});

	// 子表单验证
	$("#page_form_1").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		excluded:[":disabled"],
		fields: {  
			isPubReturn:{  
				validators: {  
					notEmpty: {  
						message: "对公退款不允许为空"  
					}
				}  
			}
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	/* 关闭按钮绑定数据是否更改校验 */
	$("#closeBut").on("click",function(){
		var newData = $("#page_form_1").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		
		if (param.datas.length != 0||newData != originalData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
	});
	
	/* 保存 */
	$("#saveBtn").on("click",function(){
		doSave();
	});
	
	$("#invoiceSave").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		var dataTable_json = Global.JqGrid.allToJson("dataTable");
		Global.Dialog.showDialog("invoiceSave",{
			title:"开票登记——新增",
			url:"${ctx}/admin/custTax/goInvoiceWin",
			postData:{
				m_umState:"A",
				keys:dataTable_json.detailJson,
				custDescr:$("#custDescr").val()
			},
			height:450,
			width:700,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							CustCode : $("#custCode").val(),
							Date : v.date,
							InvoiceNo : v.invoiceNo,
							Amount : v.amount,
							TaxPer : v.taxPer,
							NoTaxAmount : v.noTaxAmount,
							TaxAmount : v.taxAmount,
							Remarks : v.remarks,
							LastUpdatedBy : v.lastUpdatedBy,
							LastUpdate : new Date(),
							ActionLog : "ADD",
							Expired : "F",
							InvoiceCode:v.invoiceCode,
							TaxService:v.taxService,
							Buyer:v.buyer
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable").getDataIDs();
						$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
						if (ids.length != 0) {
							// 禁用搜索输入框和按钮
							$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
						}
					});
				}
			}
		});
	});
	
	$("#invoiceUpdate").on("click",function(){
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		var dataTable_json = Global.JqGrid.allToJson("dataTable");
		var json_d = dataTable_json.datas;
		var index = -1;
		for (var i = 0; i < json_d.length; i++) {
			if (json_d[i].InvoiceNo == ret.InvoiceNo && 
				json_d[i].TaxService == ret.TaxService) {
				index = i;
			}
		}
		if (index > -1) json_d.splice(index, 1);
		var keys = JSON.stringify(json_d);
		Global.Dialog.showDialog("invoiceUpdate",{
			title:"开票登记——编辑",
			url:"${ctx}/admin/custTax/goInvoiceWin",
			postData:{
				date:ret.Date,
				invoiceNo:ret.InvoiceNo,
				amount : ret.Amount,
				taxPer : parseFloat(ret.TaxPer.substring(0,ret.TaxPer.length-1))/100,//税率百分比转化update by cjg
				noTaxAmount : ret.NoTaxAmount,
				taxAmount : ret.TaxAmount,
				remarks : ret.Remarks,
				m_umState:"M",
				keys:keys,
				invoiceCode:ret.InvoiceCode,
				taxService:ret.TaxService,
				buyer:ret.Buyer
			},
			height:450,
			width:700,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							CustCode : $("#custCode").val(),
							Date : v.date,
							InvoiceNo : v.invoiceNo,
							Amount : v.amount,
							Remarks : v.remarks,
							TaxPer : v.taxPer,
							NoTaxAmount : v.noTaxAmount,
							TaxAmount : v.taxAmount,
							LastUpdatedBy : v.lastUpdatedBy,
							LastUpdate : new Date(),
							ActionLog : "ADD",
							Expired : "F",
							InvoiceCode:v.invoiceCode,
							TaxService:v.taxService,
							Buyer:v.buyer
					  	};
					   	$("#dataTable").setRowData(rowId,json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable").getDataIDs();
						$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
						if (ids.length != 0) {
							$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
						}
					});
				}
			}
		});
	});
	
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				/* 判断是否还有数据，对code进行操作 */
				var ids = $("#dataTable").getDataIDs();
				$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
				if (ids.length != 0) {
					$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
				}
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	$("#laborDelete").on("click",function(){
		var id = $("#dataTable_laborInvoice").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable_laborInvoice",id);
				/* 判断是否还有数据，对code进行操作 */
				var ids = $("#dataTable_laborInvoice").getDataIDs();
				$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
				if (ids.length != 0) {
					$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
				}
				totalLaborAmount();
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	$("#laborSave").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		Global.Dialog.showDialog("laborSave",{
			title:"劳务分包开票——新增",
			url:"${ctx}/admin/custTax/goLaborAdd",
			postData:{
				m_umState:"A",
			},
			height:300,
			width:400,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							date:v.date,
							amount:v.amount,
							lastupdate:new Date().getTime(),
							lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
							actionlog:"ADD",
							expired:"F"
					  	};
					  	Global.JqGrid.addRowData("dataTable_laborInvoice",json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable_laborInvoice").getDataIDs();
						$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
						if (ids.length != 0) {
							// 禁用搜索输入框和按钮
							$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
						}
					});
					totalLaborAmount();
				}
			}
		});
	});
	$("#laborUpdate").on("click",function(){
		var ret=selectDataTableRow("dataTable_laborInvoice");
		var rowId = $("#dataTable_laborInvoice").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		Global.Dialog.showDialog("invoiceUpdate",{
			title:"劳务分包开票——编辑",
			url:"${ctx}/admin/custTax/goLaborAdd",
			postData:{
				m_umState:"M",
				date:ret.date,
				amount:ret.amount
			},
			height:300,
			width:400,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							date:v.date,
							amount:v.amount,
							lastupdate:new Date().getTime(),
							lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
							actionlog:"EDIT",
							expired:"F"
					  	};
					   	$("#dataTable_laborInvoice").setRowData(rowId,json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable_laborInvoice").getDataIDs();
						$("#openComponent_customer_custCode").prop("readonly",false).next().prop("disabled",false);
						if (ids.length != 0) {
							$("#openComponent_customer_custCode").prop("readonly",true).next().prop("disabled",true);
						}
					});
				}
				totalLaborAmount();
			}
		});
	});
});

// 设置客户信息
function setCustData(data){
	if (!data) return;
	if (data.code) {
		$.ajax({
			url:"${ctx}/admin/custService/GetCustomerByCode",// 借用客户售后的GetCustomerByCode方法
			type:"post",
			data:{code:data.code},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
			},
			success:function(obj) {
				if (obj) {
					$("#address").val(obj.address);
					$("#status").val(obj.status);
					$("#setDate").val(formatDate(obj.setDate));// 格式化日期后放入setDate
					$("#signDate").val(formatDate(obj.signDate));
					$("#custCheckDate").val(formatDate(obj.custCheckDate));
					$("#custDescr").val(obj.descr);
					goto_query("dataTable_custPay");
				}
			}
		});
	}
	$("#page_form").data("bootstrapValidator")  
		.updateStatus("openComponent_customer_custCode", "NOT_VALIDATED", null)// 重置某一单一验证字段验证规则
		.validateField("openComponent_customer_custCode");// 触发指定字段的验证
}

function view(){
	var ret=selectDataTableRow();
	/* 选择数据的id */
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("invoiceUpdate",{
		title:"开票登记——查看",
		url:"${ctx}/admin/custTax/goInvoiceWin",
		postData:{
			date:ret.Date,
			invoiceNo:ret.InvoiceNo,
			amount : ret.Amount,
			taxPer : parseFloat(ret.TaxPer.substring(0,ret.TaxPer.length-1))/100,
			noTaxAmount : ret.NoTaxAmount,
			taxAmount : ret.TaxAmount,
			remarks : ret.Remarks,
			m_umState:"V",
			invoiceCode:ret.InvoiceCode,
			taxService:ret.TaxService,
			buyer:ret.Buyer
		},
		height:450,
		width:700
	});
}
function laborView(){
	var ret=selectDataTableRow("dataTable_laborInvoice");
	/* 选择数据的id */
	var rowId = $("#dataTable_laborInvoice").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("invoiceUpdate",{
		title:"劳务分包开票——查看",
		url:"${ctx}/admin/custTax/goLaborAdd",
		postData:{
			m_umState:"V",
			date:ret.date,
			amount:ret.amount
		},
		height:300,
		width:400,
	});
}
/* 主页面保存方法 */
function doSave(){
	$("#page_form").bootstrapValidator("validate");
	$("#page_form_1").bootstrapValidator("validate");
	var arr=$("#dataTable").jqGrid("getRowData");
	$.each(arr, function(i,r){
		r['TaxPer']=parseFloat(r.TaxPer.substring(0,r.TaxPer.length-1))/100;
		$("#dataTable").setRowData(i+1, r);
	});
	var param= Global.JqGrid.allToJson("dataTable");
	var rets = $("#dataTable_laborInvoice").jqGrid("getRowData");
	var param2 = {"laborJson": JSON.stringify(rets)};
	if(!$("#page_form").data("bootstrapValidator").isValid()) {
		return;
	}else if (!$("#page_form_1").data("bootstrapValidator").isValid()) {
		art.dialog({content: "请将基本信息填写完整",width: 220});
		return;
	}
	/*else if (param.datas.length == 0){
		art.dialog({content: "开票登记不能为空",width: 220});
		return;
	}*/

	var datas = $("#page_form").jsonForm();
	var datas_1 = $("#page_form_1").jsonForm();
	$.extend(datas,datas_1);
	/* 将datas（json）合并到param（json）中 */
	$.extend(param,datas);
	$.extend(param,param2);
	$.ajax({
		url:"${ctx}/admin/custTax/doSave",
		type: "post",
		data: param,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
	
}
function totalLaborAmount(){
	var sumAmount=parseFloat($("#dataTable_laborInvoice").getCol('amount',false,'sum'));
	$("#laborAmount").val(myRound(sumAmount,2));	
}

</script>
</html>
