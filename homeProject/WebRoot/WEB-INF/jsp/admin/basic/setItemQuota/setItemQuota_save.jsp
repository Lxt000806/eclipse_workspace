<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>套餐材料定额--新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system " >保存</button>
		      	<button type="button" id="doExcel" class="btn btn-system " onclick="doExcel()">导出Excel</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
				<input type="hidden" id="No" name="No" value="${setItemQuota.no}"/>
				<input type="hidden" id="expired" name="expired" value="${setItemQuota.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<div class="validate-group" >
						<li>
							<label>套餐材料定额编号</label>
							<input type="text" id="no" name="no" value="${setItemQuota.no}" readonly="true" placeholder="保存时生成"/>
						</li>
						<li class="form-validate">
							<label>客户类型</label>
							<house:dict id="custType" dictCode="" sql=" select code,desc1 from tCusttype where expired='F' " 
		                                   sqlValueKey="code" sqlLableKey="desc1"  value="${setItemQuota.custType}"/>
						</li>	
					</div>
					<div class="validate-group" >
						<li class="form-validate">
							<label >面积从</label>
							<input type="text" id="fromArea" name="fromArea" value="${setItemQuota.fromArea}" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" />
						</li>
						<li class="form-validate">
							<label >面积到</label>
							<input type="text" id="toArea" name="toArea" value="${setItemQuota.toArea}" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
						</li>
					</div>
					<div class="validate-group" >
						<li>
							<label class="control-textarea">备注</label> 
							<textarea id="remark" name="remark" rows="4">${setItemQuota.remark}</textarea>
						</li>
					</div>
					<div class="validate-group row" id="chekbox_show">
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${setItemQuota.expired }",
								onclick="checkExpired(this)" ${setItemQuota.expired=="T"?"checked":"" }/>
						</li>
					</div>	
				</ul>
			</form>
		</div>
	</div>
	<div class="container-fluid" >  
		<ul class="nav nav-tabs" >
			<li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">详情</a></li>  
		</ul>
	    <div class="tab-content">  
			<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
	         	<div class="body-box-list">
					<div class="btn-panel">
						<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system " id="add" >
								<span>新增</span>
							</button>
							<button type="button" class="btn btn-system " id="update" >
								<span>编辑</span>
							</button>
							<button type="button" class="btn btn-system " id="delete">
								<span>删除</span>
							</button>
							<button type="button" class="btn btn-system " id="view" >
								<span>查看</span>
							</button>
						</div>
					</div>
					<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
				</div>
			</div> 
		</div>	
	</div>
</div>	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
	var postData = $("#page_form").jsonForm();
	$(function() {
		var originalData = $("#page_form").serialize();
		
			Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/setItemQuota/goDetailJqGrid",
			postData:postData,
			sortable: true,
			height : $(document).height()-$("#content-list").offset().top - 80,
			rowNum : 10000000,
			colModel : [
				{name: "no", index: "no", width: 70, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left",hidden:true},
				{name: "itemcodedescr", index: "itemcodedescr", width: 300, label: "材料名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "left"},
				{name: "judgesend", index: "judgesend", width: 150, label: "已发货判断方式编号", sortable: true, align: "left",hidden:true},
				{name: "judgesenddescr", index: "judgesenddescr", width: 150, label: "已发货判断方式", sortable: true, align: "left"},
				{name: "fixarea", index: "fixarea", width: 200, label: "区域", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 93, label: "最后更新人员", sortable: true, align: "left", hidden: true},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left", hidden: true}
				]
			});
		
		if("V"=="${setItemQuota.m_umState}"){
			$("#saveBtn").hide();
			$("#add").hide();
			$("#update").hide();
			$("#delete").hide();
			disabledForm("page_form");
		}else if("A"=="${setItemQuota.m_umState}"){
			$("#chekbox_show").hide();
		}
	
	
		
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				custType:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				fromArea:{
					validators:{
						digits : {
						    message : "字段必须是正整数"
						}	
					}
				},
				toArea:{
					validators:{
						digits : {
						    message : "字段必须是正整数"
						}	
					}
				}
			}
		});
	});
	
	$("#add").on("click",function(){
		Global.Dialog.showDialog("setItemQuotaDetailAdd",{			
			title:"材料详情--增加",
			url:"${ctx}/admin/setItemQuota/goSetItemQuotaDetail",
			height:400,
			width:400,
			returnFun: function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							no: v.no,
							itemcode : v.itemCode,
							itemcodedescr : v.descr,
							qty : v.qty,
							judgesend:v.judgeSend,
							judgesenddescr:v.judgeSendDescr,
							fixarea:v.fixArea,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "ADD",
							expired : "F"
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					});
				}
			}
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		Global.Dialog.showDialog("setItemQuotaDetailUpdate",{			
			title:"材料详情--编辑",
			url:"${ctx}/admin/setItemQuota/goSetItemQuotaDetail",
			postData:{
				m_umState:"M",
				qty:ret.qty,
				itemCode:ret.itemcode,
				descr:ret.itemcodedescr,
				judgeSend:ret.judgesend,
				fixArea:ret.fixarea
			},
			height:400,
			width:400,
			returnFun: function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							no : v.no,
							itemcode : v.itemCode,
							itemcodedescr : v.descr,
							qty : v.qty,
							judgesend:v.judgeSend,
							judgesenddescr:v.judgeSendDescr,
							fixarea:v.fixArea,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "EDIT",
							expired : "F"
					  	};
					  	$("#dataTable").setRowData(rowId,json);
					});
				}
			}
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		Global.Dialog.showDialog("setItemQuotaDetailView",{			
			title:"材料详情--查看",
			url:"${ctx}/admin/setItemQuota/goSetItemQuotaDetail",
			postData:{
				m_umState:"V",
				qty:ret.qty,
				itemCode:ret.itemcode,
				judgeSend:ret.judgesend,
				fixArea:ret.fixarea
			},
			height:400,
			width:400
		});
	});
	
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if (!id) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	
	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator("validate");
		if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
		var datas = $("#page_form").jsonForm();
		var param= Global.JqGrid.allToJson("dataTable");
		if(param.datas.length == 0){
			art.dialog({content: "请先增加套餐材料定额信息",width: 220});
			return;
		}
		/* 将datas（json）合并到param（json）中 */
		$.extend(param,datas);
		//console.log(param);
		
		$.ajax({
			url:"${ctx}/admin/setItemQuota/doSave",
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
		
	});
	
	function doExcel(){
		var url = "${ctx}/admin/setItemQuota/doDetailExcel";
		doExcelAll(url);
	}
	
</script>	
</body>
</html>


