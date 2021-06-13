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
	<title>货好登记</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.body-box-form .panel.panel-info {
			margin-bottom: 10px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBut">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="m_umState" name="m_umState" value="${intReplenish.m_umState}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" id="status" name="status" value="${intReplenish.status}"/>
					<input type="hidden" id="custCode" name="custCode" value="${intReplenish.custCode}"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li>
								<label>单号</label>
								<input type="text" id="no" name="no" style="width:160px;"
									placeholder="保存自动生成" readonly value="${intReplenish.no}" />
							</li>
							<li>
								<label for="address">楼盘</label>
								<input type="text" id="address" name="address"
									value="${intReplenish.address}" readonly="true">
							</li>
							<li class="form-validate">
								<label for="isCupboard">是否橱柜</label>
								<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" 
									value="${intReplenish.isCupboard}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>货好时间</label> 
								<input type="text" id="boardOKDate" name="boardOKDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.boardOKDate}' pattern='yyyy-MM-dd'/>" disabled="true" />
							</li>
						</div>
						<div class="row">
							<li class="form-validate" style="max-height: 120px;">
								<label class="control-textarea">
									货好备注
								</label>
								<textarea id="boardOKRemarks" name="boardOKRemarks" 
									style="height: 70px;">${intReplenish.boardOKRemarks}</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tab_info" class="tab-pane fade in active">
						<div id="content-list">
								<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		var originalData, originalDataTable, m_umState="${intReplenish.m_umState}";
		$(function(){
			$("#isCupboard option[value='']").remove();
			$("#isCupboard").prop("disabled", "true");
			/*$("#custCode").openComponent_customer({
				showLabel: "${intReplenish.custDescr}",
				showValue: "${intReplenish.custCode}",
				readonly: true
			});*/
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					boardOKDate:{ 
						validators: {  
							notEmpty: {  
								message: "货好时间不允许为空"  
							},
						}  
					},
					/*remarks: {
						validators: {
							notEmpty: {
								message: "说明不允许为空"
							}
						}
					}*/
				}
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initEditJqGrid("dataTable", {
				url : "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData : {no: postData.no},
				height : $(document).height()-$("#content-list").offset().top-36,
				styleUI : 'Bootstrap',
				rowNum : -1,
				loadonce: true,
				colModel : [
					{name:"pk", index:"pk", width:80, label:"PK", sortable:true, align:"left", hidden: true},
					{name:"no", index:"no", width:80, label:"补货单号", sortable:true, align:"left"},
					{name:"intspl", index:"intspl", width:80, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:80, label:"供应商", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"prearrivdate", index:"prearrivdate", width:100, label:"预计到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
					{name:"okdatedescr", index:"okdatedescr", width:120, label:"货好时间", align:"left", formatter:timeBtn,},
					{name:"iano", index:"iano", width:160, label:"领料单号", sortable:true, align:"left"},
					{name:"okdate", index:"okdate", width:100, label:"货好时间", sortable:true, align:"left", formatter:formatDate,hidden:true},
					{name:"arrivedate", index:"arrivedate", width:100, label:"实际到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"docdescr", index:"docdescr", width:100, label:"CAD文件名称", sortable:true, align:"left"},
					{name:"docname", index:"docname", width:80, label:"存放地址", sortable:true, align:"left"},
					{name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime, hidden: true},
					{name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left", hidden: true},
					{name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left", hidden: true},
					{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left", hidden: true}
				],
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
					initOkDate();
				}
			});
			$("#dataTable")
				.hideCol("no").hideCol("arrivedate").hideCol("docdescr").hideCol("docname")
				.trigger("reloadGrid");
			originalData = $("#page_form").serialize();
			$("#closeBut").on("click",function(){
				doClose();
			});
			replaceCloseEvt("intReplenishSign", doClose);
			$("#saveBut").on("click", function() {
				doSave();
			});
			
		});
		function validateRefresh_local() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_employee_resPart", "NOT_VALIDATED", null)
				.validateField("openComponent_employee_resPart");
		}
		function doSave(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var param= Global.JqGrid.allToJson("dataTable");
			$.extend(param, datas);/* 将datas（json）合并到param（json）中 */
			$.ajax({
				url:"${ctx}/admin/intReplenish/doSign",
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
			/*
			弃用
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
						closeWin(false);
					}
				});
			} else {
				closeWin(false);
			}*/
			closeWin(false);
		}
		
		function timeBtn(v,x,r){
			return "<input id='dataTableOkDate"+x.rowId+"' type='text' style='border:0px' onchange='changeOkDate("+x.rowId+")' class='i-date' onFocus='WdatePicker({skin:\"whyGreen\",dateFmt:\"yyyy-MM-dd\"})' />";
		}
		
		function changeOkDate (id) {
			console.log($("#dataTableOkDate"+id).val());
			$("#dataTable").jqGrid('setCell',id,"okdate",$("#dataTableOkDate"+id).val()); 
			var date = $("#boardOKDate").val();
		    var ids = $("#dataTable").getDataIDs();//返回数据表的ID数组["66","39"..]  
		    var len = ids.length; 
		    for(var i=0; i<len; i++){ 
		        var getRow = $('#dataTable').getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.okdate;
				if(date == null){
					date = colVal;
				}else if(colVal > date){
					date = colVal;
				}
			}
			$("#boardOKDate").val(date);
		}
		
		function initOkDate (){
			var ids = $("#dataTable").getDataIDs();//返回数据表的ID数组["66","39"..]  
		    var len = ids.length; 
		    for(var i=0; i<len; i++){ 
		        var getRow = $('#dataTable').getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.okdate;
		        $("#dataTableOkDate"+ids[i]).val(colVal);
			}
		}
	</script>
</body>
</html>
