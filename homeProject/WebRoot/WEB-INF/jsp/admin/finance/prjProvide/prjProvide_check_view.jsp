<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目经理提成领取--新增</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script>
$(function(){
	$("#appCZY").openComponent_employee({showValue:"${prjProvide.appCZY}",showLabel:"${appDescr}",readonly:true });
	$("#confirmCZY").openComponent_employee({showValue:"${confirmDescr}",readonly:true });
});
$(function(){
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-55,
		rowNum:10000000,
		url:"/admin/employee",
		postData: "1",
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "number", index: "number", width: 79, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "company", index: "company", width: 172, label: "公司", sortable: true, align: "left"},
			{name: "position", index: "position", width: 79, label: "职位", sortable: true, align: "left"},
			{name: "salary", index: "salary", width: 76, label: "工薪", sortable: true, align: "left"},
			{name: "leaversn", index: "leaversn", width: 161, label: "离职原因", sortable: true, align: "left"},
			{name: "begindate", index: "begindate", width: 88, label: "开始时间", sortable: true, align: "left", formatter: formatDate},
			{name: "enddate", index: "enddate", width: 81, label: "结束时间", sortable: true, align: "left", formatter: formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label: "最后修改人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 124, label: "最后修改时间", sortable: true, align: "left", formatter: formatDate}
			],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	//项目经理结算新增
	$("#savePrj").on("click",function(){
		Global.Dialog.showDialog("savePrj",{
			title:"项目经理结算——新增",
			url:"${ctx}/admin/prjPovide/goSavePrj",
			//postData:{},
			height: 480,
			width:750,
		    returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							company:v.company,
							position:v.position,
							salary:v.salary,
							begindate:v.begindate,
							enddate:v.enddate,
							leaversn:v.leaversn,
							expired:v.expired,
							actionlog:v.actionlog,
							lastupdate:v.lastupdate,
							lastupdatedby:v.lastupdatedby
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					changeStatus();
				}
			} 
		});
	});
	//删除
	$("#deletePrj").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		art.dialog({
			content:"是删除该记录？",
			lock: true,
			width: 100,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				changeStatus();
			},
			cancel: function () {
				return true;
			}
		});
	});
	//查看
	$("#viewPrj").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var param =$("#dataTable").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("viewPrj",{
			title:"项目经理结算——查看",
		  	url:"${ctx}/admin/prjProvide/goPrjView",
		  	postData:param,
		  	height: 480,
			width:750,
		});
	});
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="saveBtn">审核通过</button>
				<button type="button" class="btn btn-system "  id="saveBtn">审核取消</button>
				<button type="button" class="btn btn-system "  id="saveBtn">反审核</button>
				<button type="button" class="btn btn-system "  id="saveBtn">输出到excel</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="" id="" value=""/>
				<ul class="ul-form">	
					<div class="validate-group">		  
						<li class="form-validate">
							<label>结算流水</label>
							<input type="text" id="no" name="no" value="${prjProvide.no}" placeholder="保存自动生成" readonly="readonly"/>
						</li>
						<li>	
							<label>申请日期</label>
							<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProvide.date}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>开单人</label>
							<input type="text" id="appCZY" name="appCZY" value="${prjProvide.appCZY }"/>
						</li>
						<li>
							<label>审核日期</label>
							<input type="text" id="joinDate" name="joinDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjProvide.confirmDate}' pattern='yyyy-MM-dd'/>" disabled="disabled"/>
						</li>
						<li>
							<label>审核人员</label>
							<input type="text" id="confirmCZY" name="confirmCZY" value="${prjProvide.confirmCZY }"/>
						</li>
						<li>	
							<label>状态</label>
							<house:xtdm id="status" dictCode="WHChkOutStatus" value="${prjProvide.status}" disabled="true"></house:xtdm>
						</li>
					</div>
					<div class="validate-group">
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${prjProvide.remarks}</textarea>
						</li>
					</div>	
				</ul>				
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">主项目</a></li>  
	    </ul>  
	    <div class="tab-content">  
	    	<div id="tab_prjProvide" class="tab-pane fade in active">
	    		<div class="pageContent">
					<div class="body-box-form">
						<div class="panel panel-system" >
						    <div class="panel-body">
						      	<div class="btn-group-xs" >
									<button type="submit" class="btn btn-system " id="savePrj">
										<span>新增</span>
									</button>
									<button type="button" class="btn btn-system" id="deletePrj">
										<span>删除</span>
									</button>
									<button type="button" class="btn btn-system" id="viewPrj">
										<span>查看</span>
									</button>
								</div>
							</div>
						</div>
					</div>
	    		</div>
	    		<div class="panel panel-info">  
					<table id= "dataTable"></table>					
				</div>
			</div>
		</div>
	</div>
</div>		
</body>
</html>
