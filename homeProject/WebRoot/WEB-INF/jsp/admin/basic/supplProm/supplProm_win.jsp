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
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
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
						<button type="button" class="btn btn-system view" id="saveBtn">
							<span>保存</span>
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
					<input type="hidden" name="m_umState" value="${supplProm.m_umState}" />
					<input type="hidden" id="expired" name="expired" value="${supplProm.expired}"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>编号</label>
								<input type="text" id="no" name="no" style="width:160px;"
									value="${supplProm.no}" placeholder="保存自动生成" readonly/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>活动名称</label> 
								<input type="text" id="descr" name="descr" style="width:160px;"
									value="${supplProm.descr}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>供应商</label> 
								<input type="text" id="supplCode" name="supplCode"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>开始时间</label> 
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${supplProm.begindate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>结束时间</label> 
								<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${supplProm.enddate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">备注</label>
								<textarea id="remarks" name="remarks" style="height: 80px;">${supplProm.remarks}</textarea>
							</li>
							<li id="expired_li">
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${supplProm.expired}" 
									onclick="checkExpired(this)" ${supplProm.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#tabView_item" data-toggle="tab">材料信息</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_item" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="itemSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="itemUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="itemDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="itemExcel">
									<span>从Excel导入</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		var originalData, originalDataTable;
		var m_umState = "${supplProm.m_umState}";
		$(function() {
			switch (m_umState) {
			case "M":
				// $("#itemDelete").remove();
				$("#supplCode").openComponent_supplier({
					showValue: "${supplProm.supplcode}",
					showLabel: "${supplProm.suppldescr}",
					callBack: refreshSuppl
				});
				break;
			case "V":
				$("#supplCode").openComponent_supplier({
					showValue: "${supplProm.supplcode}",
					showLabel: "${supplProm.suppldescr}",
					callBack: refreshSuppl
				});
				$("#saveBtn").remove();
				$("#itemSave").remove();
				$("#itemUpdate").remove();
				$("#itemDelete").remove();
				$("#itemExcel").remove();
				disabledForm();
				break;
			default:
				$("#supplCode").openComponent_supplier({
					callBack: refreshSuppl
				});
				$("#expired_li").remove();
				replaceCloseEvt("add", doClose);
				break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "活动名称不允许为空"  
							},
						}  
					},
					openComponent_supplier_supplCode: {
						validators: {  
							notEmpty: {  
								message: "供应商不允许为空"  
							},
						} 
					},
					beginDate: {
						validators: {
							notEmpty: {
								message: "开始时间不允许为空"
							}
						}
					},
					endDate: {
						validators: {
							notEmpty: {
								message: "结束时间不允许为空"
							}
						}
					}
				}
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/supplProm/goItemJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-35,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
				loadonce: true,
				colModel : [
					{name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "no", index: "no", width: 50, label: "编码", sortable: true, align: "left", hidden: true},
					{name: "itemtype3", index: "itemtype3", width: 120, label: "品牌", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 80, label: "品名", sortable: true, align: "left"},
					{name: "model", index: "model", width: 80, label: "型号", sortable: true, align: "left"},
					{name: "uom", index: "uom", width: 80, label: "单位", sortable: true, align: "left"},
					{name: "itemsize", index: "itemsize", width: 80, label: "规格", sortable: true, align: "left"},
					{name: "unitprice", index: "unitprice", width: 80, label: "有家直供价", sortable: true, align: "right"},
					{name: "promprice", index: "promprice", width: 80, label: "活动价", sortable: true, align: "right"},
					{name: "cost", index: "cost", width: 80, label: "常规成本", sortable: true, align: "right"},
					{name: "promcost", index: "promcost", width: 80, label: "活动成本", sortable: true, align: "right"},
					{name: "grossprofit", index: "grossprofit", width: 80, label: "活动毛利", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate",index: "lastupdate",width: 120,label:"最后修改时间",sortable: true,align: "left",formatter: formatTime},
					{name: "lastupdatedby",index: "lastupdatedby",width: 70,label:"修改人",sortable: true,align: "left"},
					{name: "expired",index: "expired",width: 80,label:"过期标志",sortable: true,align: "left"},
					{name: "actionlog",index: "actionlog",width: 80,label:"操作日志",sortable: true,align: "left"}
				],
				ondblClickRow: function(){
					update();
				},
				loadComplete: function(){
					refreshGrossProfit();
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			originalData = $("#page_form").serialize();
			$("#closeBtn").on("click",function(){
				doClose();
			});
			$("#saveBtn").on("click", function() {
				doSave();
			});
			// 明细新增
			$("#itemSave").on("click",function(){
				if ("V" == m_umState) return;
				Global.Dialog.showDialog("itemSave",{
					title:"材料信息——新增",
					url:"${ctx}/admin/supplProm/goItem",
					postData:{m_umState:"A"},
					height:394,
					width:740,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								v.lastupdate = new Date();
								v.expired = "F";
								v.actionlog = "ADD";
							  	Global.JqGrid.addRowData("dataTable",v);
							});
						  	refreshGrossProfit();
						}
					}
				});
			});
			// 编辑
			$("#itemUpdate").on("click",function(){
				update();
			});
			/* 明细删除 */
			$("#itemDelete").on("click",function(){
				if ("V" == m_umState) return;
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
						if ("M" != m_umState) {
							Global.JqGrid.delRowData("dataTable",id);
							var rowIds = $("#dataTable").jqGrid("getDataIDs");
							$("#dataTable").jqGrid("setSelection",rowIds[0]);
						} else {
							var val = {expired: "T"};
							$("#dataTable").setRowData(id, val);
						}
					},
					cancel: function () {
						return true;
					}
				});
			});
			// 导入Excel
			$("#itemExcel").on("click",function(){
				if ("V" == m_umState) return;
				Global.Dialog.showDialog("itemExcel",{
					title:"材料信息——导入Excel",
					url:"${ctx}/admin/supplProm/goItemExcel",
					// postData:{},
					height:578,
					width:965,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								v.lastupdatedby = "${sessionScope.USER_CONTEXT_KEY.czybh}";
								v.lastupdate = new Date();
								v.expired = "F";
								v.actionlog = "ADD";
							  	Global.JqGrid.addRowData("dataTable",v);
							});
							refreshGrossProfit();
						}
					}
				});
			});
		});
		function refreshGrossProfit() { //刷新毛利
			var ids = $("#dataTable").getDataIDs();
			for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable").getRowData(ids[i]);
				var grossprofit = myRound(1-parseFloat(rowData.promcost)/parseFloat(rowData.promprice), 2);
				$("#dataTable").setCell(ids[i], "grossprofit", grossprofit);
			}
		}
		function refreshSuppl() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_supplier_supplCode", "NOT_VALIDATED", null)
				.validateField("openComponent_supplier_supplCode");
		}
		function update(){
			if ("V" == m_umState) return;
			var ret=selectDataTableRow();
			/* 选择数据的id */
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
			if(!ret){
				art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
				return false;
			}
			Global.Dialog.showDialog("userUpdate",{
				title:"材料信息——编辑",
				url:"${ctx}/admin/supplProm/goItem",
				postData:{
					itemType3: ret.itemtype3,
					itemDescr: ret.itemdescr,
					model: ret.model,
					uom: ret.uom,
					itemSize: ret.itemsize,
					unitPrice: ret.unitprice,
					promPrice: ret.promprice,
					cost: ret.cost,
					promCost: ret.promcost,
					remarks: ret.remarks,
					expired: ret.expired,
					m_umState: "M",
				},
				height:394,
				width:740,
				returnFun : function(data){
					if(data){
						$.each(data,function(k,v){
							v.lastupdate = new Date();
							v.actionlog = "Edit";
						   	$("#dataTable").setRowData(rowId, v);
						});
					   	refreshGrossProfit();
					}
				}
			});
		}
		function doSave(){
			if ("V" == m_umState) return;
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var param= Global.JqGrid.allToJson("dataTable");
			if(param.datas.length == 0){
				art.dialog({content: "请输入材料信息", width: 220});
				return;
			}
			$.extend(param, datas);/* 将datas（json）合并到param（json）中 */
			$.ajax({
				url: "${ctx}/admin/supplProm/doSave",
				type: "post",
				data: param,
				dataType: "json",
				cache: false,
				error: function(obj) {
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
				},
				success: function(obj) {
					if(obj.rs) {
						art.dialog({
							content: obj.msg,
							time: 700,
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
		function doClose() {
			var newData = $("#page_form").serialize();
			var param= Global.JqGrid.allToJson("dataTable");
			if (param.detailJson != originalDataTable || newData != originalData) {
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
		}
	</script>
</body>
</html>
