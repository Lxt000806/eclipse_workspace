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
	<title>售中安排</title>
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
		.is-had {
			display: none;
		}
	</style>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
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
						<span class="is-had" style="color: red;margin-left: 52%;">${hadSameCustIntReplenishTip }</span>
						<button type="button" class="btn btn-system is-had" id="checkBut">
							<span>查看明细</span>
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
								<label for="source">补货来源</label>
								<house:xtdm id="source" dictCode="IntRepSource" style="width:160px;"
									value="${intReplenish.source}"/>
							</li>
							<li class="form-validate">
								<label>货齐时间</label> 
								<input type="text" id="readyDate" name="readyDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.readyDate}' pattern='yyyy-MM-dd'/>"
									readonly="true"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>售中时间</label> 
								<input type="text" id="serviceDate" name="serviceDate" class="i-date"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									value="<fmt:formatDate value='${intReplenish.serviceDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li class="form-validate">
								<label for="serviceManType"><span class="required">*</span>售中人员类型</label>
								<house:xtdm id="serviceManType" dictCode="IntRepSerManTyp" style="width:160px;" 
									value="${intReplenish.serviceManType}" onchange="serviceManTypeChange()"/>
							</li>
							<li class="form-validate">
								<label for="serviceMan"><span class="required">*</span>售中人员</label>
								<input type="text" id="serviceMan" name="serviceMan">
							</li>
							<li class="form-validate">
								<label for="worker">安装师傅</label>
								<input type="text" id="worker" name="worker">
							</li>
							<li class="form-validate">
								<label>首次退场时间</label> 
								<input type="text" id="endDate" name="endDate" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})"
									value="<fmt:formatDate value='${intReplenish.endDate}' pattern='yyyy-MM-dd'/>" disabled="true" />
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
						<div class="btn-panel">
							<div class="btn-group-sm">
								<button type="button" class="btn btn-system " id="downloaCAD">下载CAD文件</button>
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
		var originalData, originalDataTable, m_umState="${intReplenish.m_umState}";
		$(function(){
			$("#isCupboard option[value='']").remove();
			$("#source option[value='']").remove();
			$("#isCupboard").prop("disabled",true);
			$("#source").prop("disabled",true);
			/*$("#custCode").openComponent_customer({
				showLabel: "${intReplenish.custDescr}",
				showValue: "${intReplenish.custCode}",
				readonly: true
			});*/
			$("#worker").openComponent_worker({
				showLabel: "${intReplenish.worker}",
				showValue: "${intReplenish.workerDescr}",
				readonly: true
			});
			$("#serviceMan").openComponent_worker({
				showLabel: "${intReplenish.serviceManDescr}",
				showValue: "${intReplenish.serviceMan}",
				callBack: validateRefresh_local
			});
			$("#serviceMan").openComponent_employee({
				showLabel: "${intReplenish.serviceManDescr}",
				showValue: "${intReplenish.serviceMan}",
				callBack: validateRefresh_local
			});
			switch ("${intReplenish.serviceManType}") {
			case "1":
				$("#openComponent_employee_serviceMan").parent().hide();
				break;
			case "2":
				$("#openComponent_worker_serviceMan").parent().hide();
				break;
			default:
				$("#openComponent_employee_serviceMan").parent().hide();
				break;
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					serviceDate:{ 
						validators: {  
							notEmpty: {  
								message: "售中时间不允许为空"  
							},
						}  
					},
					serviceManType:{ 
						validators: {  
							notEmpty: {  
								message: "售中人员类型不允许为空"  
							},
						}  
					},
					openComponent_employee_serviceMan:{
						validators: {
							notEmpty: {
								message:"售中人员不允许为空"
							}
						}
					},
					openComponent_worker_serviceMan:{
						validators: {
							notEmpty: {
								message:"售中人员不允许为空"
							}
						}
					},
				}
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", {
				url : "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData : {no: postData.no},
				height : $(document).height()-$("#content-list").offset().top-40,
				styleUI : 'Bootstrap',
				multiselect : true,
				rowNum : -1,
				loadonce: true,
				colModel : [
					{name:"pk", index:"pk", width:80, label:"PK", sortable:true, align:"left", hidden: true},
					{name:"no", index:"no", width:80, label:"补货单号", sortable:true, align:"left"},
					{name:"intspl", index:"intspl", width:80, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:80, label:"供应商", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
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
				}
			});
			$("#dataTable")
				.hideCol("no").hideCol("docname")
				.trigger("reloadGrid");
			originalData = $("#page_form").serialize();
			$("#closeBut").on("click",function(){
				doClose();
			});
			replaceCloseEvt("intReplenishArrange", doClose);
			$("#saveBut").on("click", function() {
				doSave();
			});
			//售中安排，存在同楼盘状态是1、2、3的补货单时需要进行提醒，并且可以查看这些单的补货明细。
			if ("1" == "${isHad}") {
				$(".is-had").css("display", "inline-block");
				//查看同楼盘明细
				$("#checkBut").on("click", function () {
					Global.Dialog.showDialog("intReplenishInput",{
						title: "同楼盘明细",
						url: "${ctx}/admin/intReplenish/goHadDetail",
						postData: {no: $("#no").val()},
						height: 670,
						width: 990,
						returnFun: goto_query
					});
				});
			} else {
				$(".is-had").remove();
			}
			
			$("#downloaCAD").on("click", function() {
                var custCode = $("#custCode").val()
                
                var grid = $("#dataTable")
                var ids = grid.jqGrid('getGridParam', 'selarrrow')
                var docNames = []
                var docDescrs = []
                
                for (var i = 0; i < ids.length; i++) {
                    var rowData = grid.getRowData(ids[i])
                    if (rowData.docname) {
                        docNames.push(rowData.docname)
                        docDescrs.push(encodeURIComponent(rowData.docdescr))
                    }
                }
                
                if (!docNames.length) {
                    art.dialog({content: "没有要下载的文档", width: 260})
                    return
                }
                
                window.open("${ctx}/admin/intReplenish/downloadNew" 
                    + "?custCode=" + custCode
                    + "&docNames=" + docNames.join(",")
                    + "&docDescrs=" + docDescrs.join(","))
			})
			
		})
		
		function validateRefresh_local() {
			switch ($("#serviceManType").val()) {
			case "1":
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_employee_serviceMan", "NOT_VALIDATED", null)
					.updateStatus("openComponent_worker_serviceMan", "NOT_VALIDATED", null)
					.validateField("openComponent_worker_serviceMan");
				break;
			case "2":
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_worker_serviceMan", "NOT_VALIDATED", null)
					.updateStatus("openComponent_employee_serviceMan", "NOT_VALIDATED", null)
					.validateField("openComponent_employee_serviceMan");
				break;
			default:
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_employee_serviceMan", "NOT_VALIDATED", null)
					.updateStatus("openComponent_worker_serviceMan", "NOT_VALIDATED", null)
					.validateField("openComponent_worker_serviceMan");
				break;
			}
		}
		function doSave(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			datas.serviceMan = $("#serviceMan").val();
			$.ajax({
				url:"${ctx}/admin/intReplenish/doArrange",
				type: "post",
				data: datas,
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
			/*弃用
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
		
		function serviceManTypeChange() {
			$("#serviceMan,#openComponent_employee_serviceMan,#openComponent_worker_serviceMan").val("");
			$("#openComponent_worker_serviceMan,#openComponent_employee_serviceMan").parent().show();
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_worker_serviceMan", "NOT_VALIDATED", null)
				.updateStatus("openComponent_employee_serviceMan", "NOT_VALIDATED", null);
			switch ($("#serviceManType").val()) {
			case "1":
				$("#openComponent_employee_serviceMan").parent().hide();
				break;
			case "2":
				$("#openComponent_worker_serviceMan").parent().hide();
				break;
			default:
				$("#openComponent_employee_serviceMan").parent().hide();
				break;
			}
		};
	</script>
</body>
</html>
