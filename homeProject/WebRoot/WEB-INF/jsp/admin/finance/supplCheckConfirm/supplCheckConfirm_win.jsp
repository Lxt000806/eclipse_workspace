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
	<%@ include file="/commons/jsp/common.jsp"%>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system view" id="passBtn">
							<span>审核通过</span>
						</button>
						<button type="button" class="btn btn-system view" id="retCheckBtn">
							<span>反审核</span>
						</button>
						<button type="button" class="btn btn-system view" id="rebackBtn">
							<span>退回</span>
						</button>
						<button type="button" class="btn btn-system view" id="genSupplOtherFee">
							<span>计算其它费用</span>
						</button>
						<button type="button" class="btn btn-system view" id="itemView">
							<span>查看领料单</span>
						</button>
						<button type="button" class="btn btn-system view" id="itemReqView">
							<span>查看材料需求</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="status" name="status" value="${supplCheckConfirm.status}">
					<input type="hidden" id="firstAmount" name="firstAmount" value="${supplCheckConfirm.firstamount}">
					<input type="hidden" id="secondAmount" name="secondAmount" value="${supplCheckConfirm.secondamount}">
					<input type="hidden" id="type" name="type" value="${supplCheckConfirm.type}">
					<input type="hidden" id="custCode" name="custCode" value="${supplCheckConfirm.custcode }">
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-sm-4">
								<li class="form-validate">
									<label>采购单号</label> 
									<input type="text" id="no" name="no" style="width:160px;" value="${supplCheckConfirm.no}" 
										readonly/>
								</li>
								<li class="form-validate" hidden="true">
									<label>领料单号</label> 
									<input type="text" id="IANo" name="IANo" style="width:160px;" value="${supplCheckConfirm.iano}" 
										readonly/>
								</li>
								<li class="form-validate">
									<label>楼盘</label> 
									<input type="text" id="address" name="address" style="width:160px;" 
										value="${supplCheckConfirm.address}" readonly />
										<span color:black >&nbsp &nbsp &nbsp &nbsp${supplCheckConfirm.refaddress}</span>	
								</li>
								<li class="form-validate">
									<label>面积</label> 
									<input type="text" id="area" name="area" style="width:160px;" 
										value="${supplCheckConfirm.area}" readonly/>
								</li>
								<li class="form-validate">
									<label>材料类型1</label> 
									<house:dict id="itemType1" dictCode="" 
										sql="select Code,Code+' '+Descr Descr from tItemType1 where Expired<>'T' order by DispSeq" 
										sqlValueKey="Code" sqlLableKey="Descr"/>
								</li>
								<li>
									<label>是否橱柜</label>
									<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" value="${supplCheckConfirm.iscupboard}"/>
								</li>
								<li class="form-validate">
									<label>供应商</label> 
									<input type="text" id="supplier" name="supplier" style="width:160px;" 
										value="${supplCheckConfirm.supplier}|${supplCheckConfirm.supplierdescr}" readonly/>
								</li>
								<li class="form-validate">
									<label>预算搬楼层数</label> 
									<input type="text" id="carryFloor" name="carryFloor" style="width:160px;"
										value="${supplCheckConfirm.carryfloor}" />
								</li>
							</div>
							<div class="col-sm-4">
								<li class="form-validate">
									<label>材料金额</label> 
									<input type="text" id="amount" name="amount" style="width:160px;" 
										value="${supplCheckConfirm.amount}" readonly/>
								</li>
								<li class="form-validate">
									<label>对账金额</label> 
									<input type="text" id="splAmount" name="splAmount" style="width:160px;" 
										value="${supplCheckConfirm.splamount}" readonly/>
								</li>
								<li class="form-validate">
									<label>差额</label> 
									<input type="text" id="diffAmount" name="diffAmount" style="width:160px;" 
										value="${supplCheckConfirm.diffamount}" readonly/>
								</li>
								<li class="form-validate">
									<label>项目经理结算价</label> 
									<input type="text" id="projectAmount" name="projectAmount" style="width:160px;" 
										value="${supplCheckConfirm.projectamount}" readonly/>
								</li>
								<li class="form-validate">
									<label>项目经理调整</label> 
									<input type="text" id="projectOtherCost" name="projectOtherCost" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="${supplCheckConfirm.projectothercost}"/>
								</li>
								<li>
									<label>申请结算时间</label>
									<input type="text" id="appCheckDate" name="appCheckDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplCheckConfirm.appcheckdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
								<li class="form-validate">
									<label>发货日期</label> 
									<input type="text" id="sendDate" name="sendDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplCheckConfirm.senddate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
							</div>
							<div class="col-sm-4">
								<li class="form-validate">
									<label>其它费用</label> 
									<input type="text" id="otherCost" name="otherCost" style="width:160px;" 
										value="${supplCheckConfirm.othercost}" readonly/>
								</li>
								<li class="form-validate">
									<label>系统计算其它费用</label> 
									<input type="text" id="erpOtherCost" name="erpOtherCost" style="width:160px;" 
										value="${supplCheckConfirm.erpothercost}" readonly/>
								</li>
								<li class="form-validate">
									<label>差额</label> 
									<input type="text" id="diffCost" name="diffCost" style="width:160px;" 
										value="${supplCheckConfirm.diffcost}" readonly/>
								</li>
								<li class="form-validate">
									<label>其它费用调整</label> 
									<input type="text" id="otherCostAdj" name="otherCostAdj" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="${supplCheckConfirm.othercostadj}"  onchange="changeRemainAmount()"/>
								</li>
								<li class="form-validate">
									<label>应付金额</label> 
									<input type="text" id="remainAmount" name="remainAmount" style="width:160px;" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
										value="${supplCheckConfirm.remainamount}" />
								</li>
								<li>
									<label>结算审核时间</label>
									<input type="text" id="checkConfirmDate" name="checkConfirmDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplCheckConfirm.checkconfirmdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
								<li>
									<label>提交审核时间</label>
									<input type="text" id="toCheckConfirmDate" name=""toCheckConfirmDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${supplCheckConfirm.tocheckconfirmdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
										disabled/>
								</li>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -20px;">记账说明</label>
									<textarea id="payRemark" name="payRemark" 
										style="height: 40px;width:310px">${supplCheckConfirm.payremark}</textarea>
								</li>
							</div>
							<div class="col-sm-6">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -20px;">结算审核说明</label>
									<textarea id="checkConfirmRemarks" name="checkConfirmRemarks" 
										style="height: 40px;width:330px">${supplCheckConfirm.checkconfirmremarks}</textarea>
								</li>
							</div>
							<div class="col-sm-6">
								<li style="max-height: 120px;">
									<label class="control-textarea" style="top: -20px;">系统审核说明</label>
									<textarea id="sysconfirmremarks" name="sysconfirmremarks" 
										style="height: 40px;width:310px">${supplCheckConfirm.sysconfirmremarks}</textarea>
								</li>
							</div>
							<div class="col-sm-6">
                                <li style="max-height: 120px;">
                                    <label class="control-textarea" style="top: -20px;">领料单备注</label>
                                    <textarea id="itemAppRemarks" name="itemAppRemarks" 
                                        style="height: 40px;width:330px">${supplCheckConfirm.itemappremarks}</textarea>
                                </li>
                            </div>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#tabView_feeDetail" data-toggle="tab">采购费用明细</a>
					</li>
					<li>
						<a href="#tabView_purchaseDetail" data-toggle="tab">采购单明细</a>
					</li>
					<li>
						<a href="#tabView_itemAppDetail" data-toggle="tab">领料单明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_feeDetail" class="tab-pane fade in active">
						<div id="content-list-1">
							<table id="dataTable"></table>
						</div>
					</div>
					<div id="tabView_purchaseDetail" class="tab-pane fade">
						<jsp:include page="tabView_purchaseDetail.jsp"></jsp:include>
					</div>
					<div id="tabView_itemAppDetail" class="tab-pane fade">
						<jsp:include page="tabView_itemAppDetail.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		var m_umState = "${m_umState}", itemAppType="${supplCheckConfirm.itemapptype}";
		if ("R" == itemAppType) {
			$("#id_detail a[href='#tabView_purchaseDetail']").tab("show");
			$("#id_detail a[href='#tabView_feeDetail']").hide();
			$("#genSupplOtherFee").remove();
		}
		switch (m_umState) {
			case "C":
				$("#retCheckBtn").remove();
				break;
			case "R":
				$("#passBtn").remove();
				$("#rebackBtn").remove();
				$("#genSupplOtherFee").remove();
				disabledForm();
				break;
			default:
				$("#passBtn").remove();
				$("#retCheckBtn").remove();
				$("#rebackBtn").remove();
				$("#genSupplOtherFee").remove();
				disabledForm();
				break;
		}
		$("#itemType1").val($.trim("${supplCheckConfirm.itemtype1}"));
		$(function() {
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/supplCheckConfirm/goPurFeeDetailJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list-1").offset().top-62,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
				colModel : [
					{name: "supplfeetype", index: "supplfeetype", width: 80, label: "费用类型", sortable: true, align: "left", hidden: true},
					{name: "supplfeetypedescr", index: "supplfeetypedescr", width: 80, label: "费用类型", sortable: true, align: "left"},
					{name: "generatetype", index: "generatetype", width: 80, label: "生成方式", sortable: true, align: "left", hidden: true},
					{name: "generatetypedescr", index: "generatetypedescr", width: 80, label: "生成方式", sortable: true, align: "left"},
					{name: "amount", index: "amount", width: 70, label: "金额", sortable: true, align: "right", sum: true},
					{name: "carryfloor", index: "carryfloor", width: 70, label: "搬楼层数", sortable: true, align: "right"},
					{name: "generatetype2", index: "generatetype2", width: 80, label: "生成方式", sortable: true, align: "left", hidden: true},
					{name: "generatetype2descr", index: "generatetype2descr", width: 80, label: "生成方式", sortable: true, align: "left"},
					{name: "amount2", index: "amount2", width: 70, label: "金额", sortable: true, align: "right", sum: true},
					{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter:formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"}
				],
			});
			$("#otherCostAdj").on("blur", function () {
				var val = $(this).val();
				if (null == val || "" == val) {
					$(this).val(0);
				}
			});
			$("#projectOtherCost").on("blur", function () {
				var val = $(this).val();
				if (null == val || "" == val) {
					$(this).val(0);
				}
			});
			$("#closeBtn").on("click",function(){
				doClose();
			});
			$("#passBtn").on("click", function() {
				doSave("P");
			});
			$("#rebackBtn").on("click", function() {
				doSave("B");
			});
			$("#retCheckBtn").on("click", function() { //反审核
				doSave("R");
			});
			$("#genSupplOtherFee").on("click", function() { //计算其它费用
				if ("C" != m_umState) return;
				Global.Form.submit("page_form", "${ctx}/admin/supplCheckConfirm/genSupplOtherFee", null, function(ret){
					if(ret.rs==true){
						var data = ret.datas;
						$("#erpOtherCost").val(data.erpothercost);
						$("#diffCost").val(data.diffcost);
						$("#dataTable").jqGrid(
							"setGridParam",
							{
								postData:$("#page_form").jsonForm(),
								page:1,
								sortname:''
							}
						).trigger("reloadGrid");
						art.dialog({
							content: ret.msg,
							time: 500,
						});
						
						genOtherCostAdj();
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
						art.dialog({
							content: ret.msg,
							width: 200
						});
					}
				});
			});
			$("#itemView").on("click",function(){
				Global.Dialog.showDialog("itemView",{
					title:"领料单——查看",
					url:"${ctx}/admin/supplCheckConfirm/goItemView",
					postData:{
						custCode:$("#custCode").val()
					},
					height:711,
					width:1048,
					returnFun:goto_query
				});
			});
			
			$("#itemReqView").on("click",function(){
				Global.Dialog.showDialog("itemReqView",{
					title:"材料需求——查看",
					url:"${ctx}/admin/supplCheckConfirm/goItemReqView",
					postData:{
						custCode:$("#custCode").val()
					},
					height:711,
					width:1248,
					returnFun:goto_query
				});
			});
			// 其他费用为0时才重新生成
			if(parseFloat("${supplCheckConfirm.othercostadj}")==0.0){
				genOtherCostAdj();	
			}
			
		});
		function doSave(m_umState_s){
			if ("V" == m_umState) return;
			Global.Form.submit("page_form", "${ctx}/admin/supplCheckConfirm/doSave", {m_umState:m_umState_s}, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
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
		function doClose() {
			closeWin();
		}
		function changeRemainAmount() {
			 var amount=parseFloat($("#amount").val());
			 var otherCostAdj=0;
			 if($("#otherCostAdj").val()!=""){
				otherCostAdj=parseFloat($("#otherCostAdj").val());
			 }
			 var remainAmount=myRound(amount+parseFloat($("#otherCost").val())+otherCostAdj
			 				  -parseFloat($("#firstAmount").val())-parseFloat($("#secondAmount").val()),2) ;
			 $("#remainAmount").val(remainAmount);
		}
		
		// 生成其它费用调整,默认等于两个差额相加， 差额>0时，以负数计算，差额<=0时，以0计算
		function genOtherCostAdj(){
			if("C"=="${m_umState}"){
				var diffAmountSum = myRound(parseFloat($("#diffAmount").val()) + parseFloat($("#diffCost").val()),2);
				if( diffAmountSum > 0){
					$("#otherCostAdj").val(-diffAmountSum);	
				}else{
					$("#otherCostAdj").val(0);	
				}
				changeRemainAmount();
			}
		}
		
	</script>
</html>
