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
	<title>补货明细录入</title>
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
						<button type="button" class="btn btn-system" id="completeBut">
							<span>下单完成</span>
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
								<label for="isCupboard"><span class="required">*</span>是否橱柜</label>
								<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;" 
									value="${intReplenish.isCupboard}"/>
							</li>
							<li class="form-validate">
								<label for="source"><span class="required">*</span>补货来源</label>
								<house:xtdm id="source" dictCode="IntRepSource" style="width:160px;"
									value="${intReplenish.source}"/>
							</li>
							<li class="form-validate">
								<label for="resPart">责任人</label>
								<house:xtdm id="resPart" dictCode="IntRepResPart" style="width:160px;"
									value="${intReplenish.resPart}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate" style="max-height: 120px;">
								<label class="control-textarea">
									说明
								</label>
								<textarea id="remarks" name="remarks" style="height: 70px;">${intReplenish.remarks}</textarea>
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
								<button type="button" class="btn btn-system " id="addItem">新增</button>
								<button type="button" class="btn btn-system " id="updateItem">编辑</button>
								<button type="button" class="btn btn-system " id="deleteItem">删除</button>
								<button type="button" class="btn btn-system " id="uploadCAD">上传CAD文件</button>
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
		/*
		delCADList：准备删除CAD的列表，在保存时操作
		newCADList：新建CAD的列表，在关闭时操作
		*/
		var originalData, originalDataTable,
		    m_umState="${intReplenish.m_umState}",
			delCADList = [],newCADList = [];
			
		$(function(){
			$("#isCupboard option[value='']").remove();
			$("#source option[value='']").remove();
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: { 
					isCupBoard:{ 
						validators: {  
							notEmpty: {  
								message: "是否橱柜不允许为空"  
							},
						}  
					},
					source:{ 
						validators: {  
							notEmpty: {  
								message: "补货来源不允许为空"  
							},
						}  
					},
					/*resPart:{
						validators: {
							notEmpty: {
								message:"责任人不允许为空"
							}
						}
					},*/
				}
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", {
				url : "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData : {no: postData.no},
				height : $(document).height()-$("#content-list").offset().top-36,
				styleUI : 'Bootstrap',
				rowNum : 100000,
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
					{name:"docdescr", index:"docdescr", width:150, label:"CAD文件名称", sortable:true, align:"left"},
					{name:"docname", index:"docname", width:80, label:"存放地址", sortable:true, align:"left"},
					{name:"respart", index:"respart", width:80, label:"责任人", sortable:true, align:"left", hidden:true},
					{name:"respartdescr", index:"respartdescr", width:80, label:"责任人", sortable:true, align:"left"},
					{name:"undertaker", index:"undertaker", width:80, label:"承担方", sortable:true, align:"left", hidden:true},
					{name:"undertakerdescr", index:"undertakerdescr", width:80, label:"承担方", sortable:true, align:"left"},
					{name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime, hidden: true},
					{name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left", hidden: true},
					{name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left", hidden: true},
					{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left", hidden: true}
				],
				ondblClickRow: function(){
					doUpdate();
				},
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				}
			});
			$("#dataTable")
				.hideCol("no").hideCol("arrivedate").hideCol("docname")
				.trigger("reloadGrid");
			originalData = $("#page_form").serialize();
			$("#closeBut").on("click",function(){
				doClose();
			});
			replaceCloseEvt("intReplenishInput", doClose);
			$("#saveBut").on("click", function() {
				doSave();
			});
			$("#completeBut").on("click", function() {
				$("#status").val("2");//下单完成，状态改成“2”
				doSave();
			});
			// 明细新增
			$("#addItem").on("click",function(){
				Global.Dialog.showDialog("addItem",{
					title:"补货明细——新增",
					url:"${ctx}/admin/intReplenish/goItem",
					postData:{m_umState:"A"/*,keys:keys*/},
					height:325,
					width:725,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								$.extend(v,{
									arrivedate:"",
									lastupdate:new Date(),
									lastupdatedby:$("#lastUpdatedBy").val(),
									actionlog:"ADD",
									expired:"F"
								});
							  	Global.JqGrid.addRowData("dataTable",v);
							});
						}
					}
				});
			});
			
			$("#uploadCAD").on("click", function() {
				var ret=selectDataTableRow();
				var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!ret){
					art.dialog({content: "请选择一条记录进行CAD上传操作",width: 220});
					return false;
				}
				Global.Dialog.showDialog("uploadCAD",{
					title:"补货明细——CAD编辑",
					url:"${ctx}/admin/intReplenish/goCAD",
					postData:{
						CustCode: $("#custCode").val(),
						docDescr: ret.docdescr,
						docName: ret.docname,
					},
					height:375,
					width:694,
					returnFun: function(data) {
						if (data) {
							
							newCADList.push.apply(newCADList, data.newCADList)
							delCADList.push.apply(delCADList, data.delCADList)
														
							$.each(data.selectRows, function(k, v){
								$.extend(v, {
									lastupdate:new Date(),
									lastupdatedby:$("#lastUpdatedBy").val(),
									actionlog:"ADD",
									expired:"F"
								});
								$("#dataTable").setRowData(rowId, v);
							});
						}
					}
				});
			});
			
			// 编辑
			$("#updateItem").on("click",function(){
				doUpdate();
			});
			
			/* 明细删除 */
			$("#deleteItem").on("click",function(){
				var ret=selectDataTableRow();
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
						if (ret.docname) {
							var delCAD = {custCode:$("#custCode").val(),path:ret.docname};
							delCADList.push(delCAD);
						}
						Global.JqGrid.delRowData("dataTable",id);
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
			});
		});
		
		function doUpdate(){
			var ret=selectDataTableRow();
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
			if(!ret){
				art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
				return false;
			}
			/*var users = Global.JqGrid.allToJson("dataTable","id_");
			var keys = users.keys;//获取user的数组
			var index = keys.indexOf(ret.id_);
			if (index > -1) keys.splice(index, 1); //去掉选择的user*/
			Global.Dialog.showDialog("updateItem",{
				title:"补货明细——编辑",
				url:"${ctx}/admin/intReplenish/goItem",
				postData:{
					m_umState:"M", /*keys:keys*/
					intSpl:ret.intspl,
					intSplDescr:ret.intspldescr,
					type:ret.type,
					date:ret.date,
					remarks:ret.remarks,
					resPart:ret.respart,
					undertaker:ret.undertaker
				},
				height:325,
				width:725,
				returnFun : function(data){
					if(data){
						$.each(data,function(k,v){
							$.extend(v,{
								lastupdate:new Date(),
								lastupdatedby:$("#lastUpdatedBy").val(),
								actionlog:"ADD",
								expired:"F"
							});
							$("#dataTable").setRowData(rowId,v);
						});
					}
				}
			});
		}
		
		function doSave(){
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var datas = $("#page_form").jsonForm();
			var param= Global.JqGrid.allToJson("dataTable");
			if(param.datas.length == 0){
				art.dialog({content: "请输入集成补货明细",width: 220});
				return;
			}
			$.extend(param, datas);/* 将datas（json）合并到param（json）中 */
			$.ajax({
				url:"${ctx}/admin/intReplenish/doInput",
				type: "post",
				data: param,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
			    },
			    success: function(obj){
			    	if(obj.rs){
		    			delCADAjax(true);
			    		art.dialog({
							content: obj.msg,
							width: 260,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 260
						});
			    	}
		    	}
			});
		}
		
		function doClose() {
			
			delCADAjax(false);
			
			// 要设定一点延时再关闭窗口，因为删除文件的请求是异步的
			// 直接关闭窗口，删除文件的请求可能会发送失败
			// 张海洋 20200730
			setTimeout(closeWin, 500, false)
		}
		
		// 批量删除CAD文件
		function delCADAjax(isSave) {
			var list = isSave ? delCADList : newCADList
						
			if (list.length) {
				$.each(list, function(i, v){
					$.ajax({
						url: '${ctx}/admin/intReplenish/deleteDocNew',
						type: 'post',
						data: {docName: v.path},
						dataType: 'json',
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错'});
						},
						success: function(obj){
							console.log(obj)
						}
					})
				})
				
			}
		}
	</script>
</body>
</html>
