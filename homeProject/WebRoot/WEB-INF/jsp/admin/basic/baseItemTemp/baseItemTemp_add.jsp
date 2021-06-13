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
					<input type="hidden" id="expired" name="expired" value="${baseItemTemp.expired}"/>
					<input type="hidden" id="m_umState" name="m_umState" value="${baseItemTemp.m_umState}"/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>编号</label> 
								<input type="text" id="no" name="no" style="width:160px;"
									value="${baseItemTemp.no}" placeholder="保存自动生成" readonly/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>类别</label>
								<house:xtdm id="type" dictCode="BASETEMPTYPE" style="width:160px;" 
									value="${baseItemTemp.type}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>模板名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"
									value="${baseItemTemp.descr}"/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">备注</label>
								<textarea id="remark" name="remark" 
									style="height: 76px;width: 743px;">${baseItemTemp.remark}</textarea>
							</li>
						</div>
						<div class="row" id="expired_div">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="${baseItemTemp.expired}" 
									onclick="checkExpired(this)" ${baseItemTemp.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabView_detail" data-toggle="tab">主项目</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_detail" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="detailSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailQuicklySave">
									<span>快速新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="detailDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="detailView">
									<span>查看</span>
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
</body>
	<script type="text/javascript">
		var originalData, originalDataTable, maxDispSeq=0, m_umState = "${baseItemTemp.m_umState}";
		function sortDispSeq() {//设置DispSeq的最大值
			var dispseqs = Global.JqGrid.allToJson("dataTable","dispseq");
			maxDispSeq = 0;
			$.each(dispseqs.keys, function (i,v) {
				maxDispSeq = parseInt(v)>maxDispSeq?parseInt(v)+1:maxDispSeq;
			});
		}
		$(function() {
			if ("" == m_umState) {
				$("#expired_div").remove();
				$("#m_umState").val("A");
			} else if ("V" == m_umState) {
				$("#saveBtn").remove();
				$("#detailSave").remove();
				$("#detailQuicklySave").remove();
				$("#detailUpdate").remove();
				$("#detailDelete").remove();
			}
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					type:{ 
						validators: {  
							notEmpty: {  
								message: "类别不允许为空"  
							},
						}  
					},
					descr:{ 
						validators: {  
							notEmpty: {  
								message: "模板名称不允许为空"  
							},
						}  
					}
				}
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/baseItemTemp/goDetailJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-35,
				styleUI: "Bootstrap", 
				rowNum : 10000000,
				colModel : [
					{name: "pk", index: "pk", width: 60, label: "序号", sortable: true, align: "left", hidden: true},
					{name: "no", index: "no", width: 80, label: "模板主键", sortable: true, align: "left", hidden: true},
					{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基础项目编号", sortable: true, align: "left"},
					{name: "baseitemcodedescr", index: "baseitemcodedescr", width: 200, label: "基础项目名称", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right"},
					{name: "dispseq", index: "dispseq", width: 60, label: "显示顺序", sortable: true, align: "right", hidden: true},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left", hidden: true}
				],
				loadonce: true,
				ondblClickRow: function(){
					view();
				},
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			originalData = $("#page_form").serialize();
			$("#closeBtn").on("click",function(){
				doClose();
			});
			replaceCloseEvt("update", doClose);
			$("#saveBtn").on("click", function() {
				doSave();
			});
			// 明细新增
			$("#detailSave").on("click",function(){
				if ("V"==m_umState) return;
				sortDispSeq();
				Global.Dialog.showDialog("detailSave",{
					title:"基础报价模板明细——新增",
					url:"${ctx}/admin/baseItemTemp/goWin",
					postData:{m_umState:"A"},
					height:308,
					width:450,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var json = {
									no: "*",
									baseitemcode:v.baseItemCode,
									baseitemcodedescr:v.baseItemDescr,
									qty:v.qty,
									dispseq:maxDispSeq,
									lastupdate:new Date(),
									lastupdatedby:v.lastupdatedby,
									expired:"F",
									actionlog:"ADD"
							  	};
							  	Global.JqGrid.addRowData("dataTable",json);
							});
						}
					}
				});
			});
			//快速新增
			$("#detailQuicklySave").on("click",function(){
				if ("V"==m_umState) return;
				sortDispSeq();
				Global.Dialog.showDialog("detailSave",{
					title:"基础报价模板明细——快速新增",
					url:"${ctx}/admin/baseItemTemp/goWin",
					postData:{m_umState:"Q"},
					height:308,
					width:450,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var json = {
									no: "*",
									baseitemcode:v.baseItemCode,
									baseitemcodedescr:v.baseItemDescr,
									qty:v.qty,
									dispseq:maxDispSeq++,
									lastupdate:new Date(),
									lastupdatedby:v.lastupdatedby,
									expired:"F",
									actionlog:"ADD"
							  	};
							  	Global.JqGrid.addRowData("dataTable",json);
							});
						}
					}
				});
			});
			// 编辑
			$("#detailUpdate").on("click",function(){
				if ("V"==m_umState) return;
				var ret=selectDataTableRow();
				/* 选择数据的id */
				var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!ret){
					art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
					return false;
				}
				Global.Dialog.showDialog("detailUpdate",{
					title:"基础报价模板明细——编辑",
					url:"${ctx}/admin/baseItemTemp/goWin",
					postData:{m_umState:"M", baseItemCode:ret.baseitemcode, qty:ret.qty},
					height:308,
					width:450,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var json = {
									baseitemcode:v.baseItemCode,
									baseitemcodedescr:v.baseItemDescr,
									qty:v.qty,
									lastupdate:new Date(),
									lastupdatedby:v.lastupdatedby,
									expired:"F",
									actionlog:"Edit"
							  	};
							   	$("#dataTable").setRowData(rowId,json);
							});
						}
					}
				});
			});
			/* 明细删除 */
			$("#detailDelete").on("click",function(){
				if ("V"==m_umState) return;
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
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
				
			});
			$("#detailView").on("click", function () {//查看
				view();
			});
		});
		function view(){
			var ret=selectDataTableRow();
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");/* 选择数据的id */
			if(!ret){
				art.dialog({content: "请选择一条记录进行查看操作",width: 220});
				return false;
			}
			Global.Dialog.showDialog("detailView",{
				title:"基础报价模板明细——查看",
				url:"${ctx}/admin/baseItemTemp/goWin",
				postData:{m_umState:"V", baseItemCode:ret.baseitemcode, qty:ret.qty},
				height:308,
				width:450,
			});
		}
		function doSave(){
			if ("V"==m_umState) return;
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var param= Global.JqGrid.allToJson("dataTable");
			if(param.datas.length == 0){
				art.dialog({content: "明细表无数据保存无意义",width: 220});
				return;
			}
			$.extend(param,datas);/* 将datas（json）合并到param（json）中 */
			$.ajax({
				url:"${ctx}/admin/baseItemTemp/doSave",
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
</html>
