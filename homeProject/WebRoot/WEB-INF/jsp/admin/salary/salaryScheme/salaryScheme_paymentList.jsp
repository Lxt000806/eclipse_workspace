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
	<title>薪酬方案分账定义</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-120,
		rowNum:50,
		loadonce:true,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true},
			{name:"salaryscheme", index:"salaryscheme", width:80, label:"薪酬项目", sortable:true ,align:"left",hidden:true},
			{name:"descr", index:"descr", width:80, label:"分账名称", sortable:true ,align:"left",},
			{name:"paymode", index:"paymode", width:80, label:"发放方式", sortable:true ,align:"left",hidden:true},
			{name:"paymodedescr", index:"paymodedescr", width:80, label:"发放方式", sortable:true ,align:"left",},
			{name:"seqno", index:"seqno", width:80, label:"分账序号", sortable:true ,align:"left",},
			{name:"remarks", index:"remarks", width:180, label:"备注", sortable:true ,align:"left",},
			{name:"filterformula", index:"filterformula", width:180, label:"计算公式", sortable:true ,align:"left",},
			{name:"filterformulashow", index:"filterformulashow", width:180, label:"计算公式显示", sortable:true ,align:"left",},
			{name:"realpaysalaryitem", index:"realpaysalaryitem", width:80, label:"实发项目", sortable:true ,align:"left",hidden:true},
			{name:"salaryitemdescr", index:"salaryitemdescr", width:80, label:"实发项目", sortable:true ,align:"left",},
			{name:"salaryitemlist", index:"salaryitemlist", width:150, label:"项目列表", sortable:true ,align:"left",},
			{name:"lastupdate", index:"lastupdate", width:80, label:"最后修改时间", sortable:true ,align:"left",formatter:formatDate},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人员", sortable:true ,align:"left"},
			{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true ,align:"left",},
			{name:"actionlog", index:"actionlog", width:80, label:"操作标记", sortable:true ,align:"left",},
		],
	}; 
	
	$.extend(gridOption,{
		url:"${ctx}/admin/salaryScheme/goPaymentListJqgrid",
		postData:{pk:$.trim($("#pk").val())}
	});
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

function addPayment(){
	var count = $("#dataTable").getGridParam("reccount");
	var pk = $("#pk").val();
	Global.Dialog.showDialog("addPayment",{
		title:"分账定义——新增",
		postData:{pk: pk},
		url:"${ctx}/admin/salaryScheme/goAddPayment",
		height:540,
		width:960,
        resizable: true,
		returnFun:function(data){
			if(data){
				console.log(data);
				var colData = {
					descr: data.descr,
					salaryscheme: "${salaryScheme.pk }",
					paymode: data.payMode,
					paymodedescr: data.payModeDescr,
					seqno: data.seqNo,
					remarks: data.remarks,
					filterformula: data.filterFormula,
					filterformulashow: data.filterFormulaShow,
					realpaysalaryitem: data.realPaySalaryItem,
					salaryitemdescr: data.realPaySalaryItemDescr,
					salaryitemlist: data.salaryItemList,
					lastupdate: new Date(),
					lastupdatedby: "1",//"${salaryScheme.lastUpdatedBy}",
					expired: "F",
					actionlog:"ADD"
				};
				$("#dataTable").addRowData(myRound(count)+1, colData, "last");
			}
		}
	});	
}

function doSave(){
	var param= Global.JqGrid.allToJson("dataTable");
	console.log(param);
	Global.Form.submit("dataForm","${ctx}/admin/salaryScheme/doSavePayment",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
}

function delRow(){
	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	
	if(!ids.length > 0){
		art.dialog({
			content:"请选择要删除的数据！"
		});
		return;
	}
	
	var delIds = [];
	var rowNum = $("#dataTable").jqGrid("getGridParam", "records");
	
	for(var i = 0; i < ids.length; i++){
		delIds[i] = myRound(ids[i]);
	}
	delIds = getDescendingOrder(delIds);
	
	if (ids && ids.length>0) {
		art.dialog({
			content: "删除所选记录？",
			lock: true,
			ok: function () {
				for(var i = 0; i < delIds.length; i++){
					$("#dataTable").delRowData(myRound(delIds[i]));
				}
			},
			cancel: function () {
				return true;
			}
		});

	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}

function getDescendingOrder(ids){
   	if(!ids && !ids.length > 0 ){
   		return ids;
   	}
	for(var i = 0; i < ids.length; i++){
			ids[i] = myRound(ids[i]);
		for(var j = i + 1; j < ids.length; j++){
			ids[j] = myRound(ids[j]);
			if(myRound(ids[i]) < myRound(ids[j])){
				var x = 0;
				x = myRound(ids[i]);
				ids[i] = myRound(ids[j]);
				ids[j] = myRound(x);
			} else{
				ids[i] = myRound(ids[i]);
			}
		}
	} 
	return ids;
}

function updatePayment(){
	var count = $("#dataTable").getGridParam("reccount")
	var ret = selectDataTableRow();
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	
	console.log(ret);
	if(!ret){
		art.dialog({
			content:"请选择一条记录进行编辑！",
		});
		return;
	}
	
	var map = JSON.stringify(ret).replace(/'/g,"\\\'");
	console.log(map);
	Global.Dialog.showDialog("updatePayment",{
		title:"分账定义——编辑",
		url:"${ctx}/admin/salaryScheme/goUpdatePayment?map="+encodeURIComponent(JSON.stringify(ret)),
		height:540,
		width:960,
        resizable: true,
		returnFun:function(data){
			if(data){
				var colData = {
					descr: data.descr,
					paymode: data.payMode,
					paymodedescr: data.payModeDescr,
					seqno: data.seqNo,
					remarks: data.remarks,
					filterformula: data.filterFormula,
					filterformulashow: data.filterFormulaShow,
					realpaysalaryitem: data.realPaySalaryItem,
					salaryitemdescr: data.realPaySalaryItemDescr,
					salaryitemlist: data.salaryItemList,
					lastupdate: new Date(),
					lastupdatedby: "1",//"${salaryScheme.lastUpdatedBy}",
					expired: "F",
					actionlog:"ADD"
				};
				
			   	$("#dataTable").setRowData(rowId, colData);
			}
		}
	});	
}

function viewPayment(){
	var count = $("#dataTable").getGridParam("reccount")
	var ret = selectDataTableRow();
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	
	console.log(ret);
	if(!ret){
		art.dialog({
			content:"请选择一条记录进行编辑！",
		});
		return;
	}
	
	Global.Dialog.showDialog("viewPayment",{
		title:"分账定义——查看",
		url:"${ctx}/admin/salaryScheme/goViewPayment?map="+encodeURIComponent(JSON.stringify(ret)),
		//postData:{map:JSON.stringify(ret)},
		height:540,
		width:960,
        resizable: true,
	});	
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBut" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<li class="form-validate">
								<label>薪酬方案</label>
								<house:dict id="pk" dictCode="" sql="select pk,descr from tSalaryScheme"  
									 sqlValueKey="pk" sqlLableKey="descr" value="${salaryScheme.pk }" disabled="true"></house:dict>
							</li>
							<li class="form-validate">
								<label>方案类型</label>
								<house:dict id="salarySchemeType" dictCode="" sql="select Code+' '+descr descr,code from tSalarySchemeType where expired = 'F'" 
											sqlValueKey="code" sqlLableKey="descr" value="${salaryScheme.salarySchemeType }" disabled="true"></house:dict>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分账定义</a></li>
			    </ul> 
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system">
								<div class="panel-body">
									<div class="btn-group-xs">
										<button type="button" class="btn btn-system" onclick="addPayment()">
											<span>新建分账</span>
										</button>
										<button type="button" class="btn btn-system" id="updatePayment" onclick="updatePayment()">
											<span>编辑</span>
										</button>
										<button type="button" class="btn btn-system" id="viewPayment" onclick="viewPayment()">
											<span>查看</span>
										</button>
									</div>
								</div>
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
								<table id="dataTablePager"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
