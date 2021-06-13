<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>工地问题列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function(){
		$("#custSceneDesi").openComponent_employee();	
		$("#designMan").openComponent_employee();	
		$("#empCode").openComponent_employee();
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjProblem/goJqGrid",
			postData:{status:"1,2,3"},
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",frozen:true },
				{name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left",},
				{name: "custstatusdescr", index: "custstatusdescr", width: 80, label: "客户状态", sortable: true, align: "left",},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "问题单状态", sortable: true, align: "left",},
				{name: "appdescr", index: "appdescr", width: 80, label: "提报人", sortable: true, align: "left"},
				{name: "projectphone", index: "projectphone", width: 90, label: "项目经理电话", sortable: true, align: "left"},
				{name: "prjdepartment2descr", index: "prjdepartment2descr", width: 80, label: "工程部", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 80, label: "提报时间", sortable: true, align: "left",formatter: formatDate},
				{name: "deptdescr", index: "deptdescr", width: 80, label: "责任部门", sortable: true, align: "left"},
				{name: "promtypedescr", index: "promtypedescr", width: 80, label: "责任分类", sortable: true, align: "left"},
				{name: "prompropdescr", index: "prompropdescr", width: 80, label: "问题性质", sortable: true, align: "left"},
				{name: "isbringstopdescr", index: "isbringstopdescr", width: 80, label: "导致停工", sortable: true, align: "left"},
				{name: "stopdays", index: "stopdays", width: 80, label: "停工天数", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 130, label: "问题描述", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left"},
				{name: "designdepartment2descr", index: "designdepartment2descr", width: 80, label: "设计部", sortable: true, align: "left"},
				{name: "maindescr", index: "maindescr", width: 80, label: "主材管家", sortable: true, align: "left"},
				{name: "jcdesignmandescr", index: "jcdesignmandescr", width: 80, label: "集成设计师", sortable: true, align: "left"}, 
				{name: "jcdepartment2descr", index: "jcdepartment2descr", width: 80, label: "集成部", sortable: true, align: "left"}, 
				{name: "cgdesignmandescr", index: "cgdesignmandescr", width: 80, label: "橱柜设计师", sortable: true, align: "left"},
				{name: "confirmdescr", index: "confirmdescr", width: 80, label: "确认人", sortable: true, align: "left"},
				{name: "confirmczy", index: "confirmczy", width: 80, label: "确认人", sortable: true, align: "left", hidden: true},
				{name: "confirmdate", index: "confirmdate", width: 80, label: "确认时间", sortable: true, align: "left",formatter: formatDate},
				{name: "feedbackdescr", index: "feedbackdescr", width: 80, label: "反馈人", sortable: true, align: "left"},
				{name: "feedbackdate", index: "feedbackdate", width: 80, label: "反馈时间", sortable: true, align: "left",formatter: formatDate},
				{name: "plandealdate", index: "plandealdate", width: 93, label: "预计处理时间", sortable: true, align: "left",formatter: formatDate},
				{name: "dealremarks", index: "dealremarks", width: 130, label: "解决方案", sortable: true, align: "left"},
				{name: "dealdescr", index: "dealdescr", width: 80, label: "处理人", sortable: true, align: "left"},
				{name: "dealdate", index: "dealdate", width: 80, label: "处理时间", sortable: true, align: "left",formatter: formatDate},
				{name: "fixdutymandescr", index: "fixdutymandescr", width: 80, label: "定责人", sortable: true, align: "left"},
				{name: "fixdutydate", index: "fixdutydate", width: 80, label: "定责时间", sortable: true, align: "left",formatter: formatDate},
				{name: "isdealdescr", index: "isdealdescr", width: 80, label: "是否解决", sortable: true, align: "left"},
				{name: "canceldescr", index: "canceldescr", width: 80, label: "撤销人", sortable: true, align: "left"},
				{name: "canceldate", index: "canceldate", width: 80, label: "撤销时间", sortable: true, align: "left",formatter: formatDate},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后更新人员", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 93, label: "最后更新时间", sortable: true, align: "left",formatter: formatDate},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "status", index: "status", width: 80, label: "状态编码", sortable: true, align: "left",hidden:true},
				{name: "picnum", index: "picnum", width: 80, label: "图片数", sortable: true, align: "left"}
			],
			gridComplete:function(){
	           	$(".ui-jqgrid-bdiv").scrollTop(0);
	        	frozenHeightReset("dataTable");
	        },
		});
		$("#dataTable").jqGrid("setFrozenColumns");
		
		//修改处理结果
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(ret.statusdescr!='已反馈'){
				art.dialog({
	       			content: "请选择已反馈的问题",
	       		});
			}else{
				Global.Dialog.showDialog("Update",{
					title:"工地问题管理——修改解决方案",
					url:"${ctx}/admin/prjProblem/goUpdate",
					postData:{no:ret.no,dealRemarks:ret.dealremarks},
				   	height:220,
				    width:740,
					returnFun:goto_query
				});
			}
		});
		
		//确认
		$("#confirm").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="1"){
				art.dialog({
	       			content: "只有已提报的才能进行确认",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("confirm",{
				title:"工地问题管理——确认",
				url:"${ctx}/admin/prjProblem/goConfirm",
				postData:{no:ret.no},
				height:620,
				width:750,
				returnFun:goto_query
			});
		});
		//反馈
		$("#feedback").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="2"){
				art.dialog({
	       			content: "只有已确认的的才能进行反馈",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("feedback",{
				title:"工地问题管理——反馈",
				url:"${ctx}/admin/prjProblem/goFeedback",
				postData:{no:ret.no},
				height:620,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#deal").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="3" && $.trim(ret.status)!="2"){
				art.dialog({
	       			content: "只有已反馈、已确认的才能进行处理",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("deal",{
				title:"工地问题管理——处理",
				url:"${ctx}/admin/prjProblem/goDeal",
				postData:{no:ret.no},
				height:620,
				width:750,
				returnFun:goto_query
			});
		});
		
		//取消确认
		$("#cancelConfirm").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="2"){
				art.dialog({
	       			content: "只有已确认状态才能撤回确认",
	       		});
	       		return;
			}
			art.dialog({
				content:"是否撤回确认",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjProblem/doSaveCancelConfirm",
						type: "post",
						data:{no:ret.no},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
				},
				cancel: function () {
					return true;
				}
			});
		});
		//取消反馈
		$("#cancelFeedback").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="3"){
				art.dialog({
	       			content: "只有已反馈状态才能撤回反馈",
	       		});
	       		return;
			}
			art.dialog({
				content:"是否撤回反馈",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjProblem/doSaveCancelFeedback",
						type: "post",
						data:{no:ret.no},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
				},
				cancel: function () {
					return true;
				}
			});
		});
		//取消处理
		$("#cancelDeal").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="4"){
				art.dialog({
	       			content: "只有已处理状态才能撤回处理",
	       		});
	       		return;
			}
			art.dialog({
				content:"是否撤回处理",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjProblem/doSaveCancelDeal",
						type: "post",
						data:{no:ret.no},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
				},
				cancel: function () {
					return true;
				}
			});
		});
		//撤销
		$("#doCancel").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="1" && $.trim(ret.status)!="2" && $.trim(ret.status)!="3"){
				art.dialog({
	       			content: "只有已提报，已确认，已反馈状态才能撤销问题",
	       		});
	       		return;
			}
			art.dialog({
				content:"是否撤销",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjProblem/doSaveCancel",
						type: "post",
						data:{no:ret.no},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
				},
				cancel: function () {
					return true;
				}
			});
		});

		
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"工地问题管理——查看",
				url:"${ctx}/admin/prjProblem/goView",
				postData:{no:ret.no,picNum:ret.picnum},
				height:700,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#fixDuty").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if($.trim(ret.status)!="4"){
				art.dialog({
	       			content: "已处理状态的才允许定责",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("fixDuty",{
				title:"工地问题管理——定责",
				url:"${ctx}/admin/prjProblem/goFixDuty",
				postData:{no:ret.no},
				height:700,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#save").on("click",function(){
			Global.Dialog.showDialog("save",{
				title:"工地问题管理——新增",
				url:"${ctx}/admin/prjProblem/goSave",
				height:620,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#modify").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if("${hasManageRight}"!="true" && ($.trim(ret.confirmczy)!=$.trim("${czybh}") || ret.status!="2")){
				art.dialog({
	       			content: "只能编辑确认人为本人的已确认的记录",
	       		});
	       		return;
			}
			if(ret.status=="2" || ret.status=="3" || ret.status=="4"){
				art.dialog({
	       			content: "已确认、已反馈、已处理状态不允许编辑",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("modify",{
				title:"工地问题管理——编辑",
				url:"${ctx}/admin/prjProblem/goModify",
				postData:{no:ret.no},
				height:620,
				width:750,
				returnFun:goto_query
			});
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/prjProblem/doExcel";
		doExcelAll(url);
	}
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_promDeptCode").checkAllNodes(false);
		$("#promDeptCode").val("");
		$.fn.zTree.getZTreeObj("tree_promTypeCode").checkAllNodes(false);
		$("#promTypeCode").val("");
		$.fn.zTree.getZTreeObj("tree_promPropCode").checkAllNodes(false);
		$("#promPropCode").val("");
	} 
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"/>
						</li>
						<li>
							<label>责任部门</label>
							<house:DictMulitSelect id="promDeptCode" dictCode="" 
							sql="select code,descr  from tPrjPromDept where expired='F' " onCheck="checkPromDept()"
							sqlLableKey="descr" sqlValueKey="code" selectedValue="${prjProblem.promDeptCode }"></house:DictMulitSelect>
						</li>
						<li>
							<label>责任分类</label>
							<house:DictMulitSelect id="promTypeCode" dictCode="" sql="select code,descr from tPrjPromType where expired='F' and 1=2 " 
							sqlLableKey="descr" sqlValueKey="code" selectedValue="${prjProblem.promTypeCode }"></house:DictMulitSelect>
						</li>	
						<li>
							<label>责任性质</label>
							<house:xtdmMulit id="promPropCode" dictCode="PRJPROMPROP" selectedValue="${prjProblem.promPropCode}"></house:xtdmMulit>                     
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="PRJPROMSTATUS" selectedValue="1,2,3,"></house:xtdmMulit>                     
						</li>
						<li>
							<label>是否处理</label>
							<house:xtdm id="isDeal" dictCode="YESNO"></house:xtdm>                     
						</li>
						<li>	
							<label>提报时间从</label>
							<input type="text" id="appDateFrom" name="appDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="appDateTo" name="appDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>	
							<label>确认时间从</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>	
							<label>反馈时间从</label>
							<input type="text" id="feedbackDateFrom" name="feedbackDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="feedbackDateTo" name="feedbackDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>	
							<label>预计处理时间从</label>
							<input type="text" id="planDealDateFrom" name="planDealDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="planDealDateTo" name="planDealDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>	
							<label>处理时间从</label>
							<input type="text" id="dealDateFrom" name="dealDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dealDateTo" name="dealDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
						</li>
						<li>
							<label>工程部</label>
							<house:dict id="prjDepartment2" dictCode="" 
								sql="select a.Code,a.code+' '+a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
										 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
								sqlValueKey="Code" sqlLableKey="Desc1"  >
							</house:dict>
						</li>

						<li>
							<label>集成部</label>
							<house:department2 id="jcDepartment2" dictCode="15"  depType=""  ></house:department2>
						</li>
						<li>
							<label>导致停工</label>
							<house:xtdm id="isBringStop" dictCode="YESNO"></house:xtdm>                     
						</li>
						<li>
							<label>干系人编号</label> 
							<input type="text" id="empCode" name="empCode" style="width:160px;" />
						</li>
						<li>  
                            <label>定责时间从</label>
                            <input type="text" id="fixDutyDateFrom" name="fixDutyDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
                        </li>
                        <li>
                            <label>至</label>
                            <input type="text" id="fixDutyDateTo" name="fixDutyDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value=""/>
                        </li>
						<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
 			<div class="btn-group-sm">
 				<house:authorize authCode="PRJPROBLEM_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_UPDATE">
					<button type="button" class="btn btn-system" id="modify">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_CONFIRM">
					<button type="button" class="btn btn-system" id="confirm">
						<span>确认</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_FEEDBACK">
					<button type="button" class="btn btn-system" id="feedback">
						<span>反馈</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_DEAL">
					<button type="button" class="btn btn-system" id="deal">
						<span>处理</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_CANCELCONFIRM">
					<button type="button" class="btn btn-system" id="cancelConfirm">
						<span>撤回确认</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_CANCELFEEDBACK">
					<button type="button" class="btn btn-system" id="cancelFeedback">
						<span>撤回反馈</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_CANCELDEAL">
					<button type="button" class="btn btn-system" id="cancelDeal">
						<span>撤回处理</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_CANCEL">
					<button type="button" class="btn btn-system" id="doCancel">
						<span>撤销</span>
					</button></house:authorize>
				<house:authorize authCode="PRJPROBLEM_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_DEALREMARK">
					<button type="button" class="btn btn-system" id="update">
						<span>修改解决方案</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_FIXDUTY">
					<button type="button" class="btn btn-system" id="fixDuty">
						<span>定责</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PRJPROBLEM_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
