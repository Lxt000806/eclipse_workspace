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
	<title>工程进度停工标记</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
	$(function(){
	$("#updateStatus").on("click",function(){
		art.dialog({
			content:"是否确定修改为新状态",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
		    	var param= Global.JqGrid.selectToJson("dataTable");
				Global.Form.submit("page_form","${ctx}/admin/prjProg/doUpdateCustStatus",param,function(ret){
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
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	});
		//初始化表格	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjProg/goUpdateStopJqGrid",
			//postData:{custCode:'${prjProg.custCode}',prjJobType:'3,4,5,6'},
			height:$(document).height()-$("#content-list").offset().top-50,
			rowNum:10000000,
			multiselect: true,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "CustCode", index: "CustCode", width: 75, label: "客户编号", sortable: true, align: "left"},
				{name: "Address", index: "Address", width: 160, label: "楼盘地址", sortable: true, align: "left"},
				{name: "CustTypeDescr", index: "CustTypeDescr", width: 75, label: "客户类型", sortable: true, align: "left",},
				{name: "SignDate", index: "SignDate", width: 80, label: "签单时间", sortable: true, align: "left",formatter:formatDate},
				{name: "BeginDate", index: "BeginDate", width:100, label: "实际开工时间", sortable: true, align: "left",formatter:formatDate},
				{name: "constadate", index: "constadate", width:100, label: "状态标记时间", sortable: true, align: "left",formatter:formatDate},
				{name: "OldStatus", index: "OldStatus", width: 85, label: "原施工状态", sortable: true, align: "left"},
				{name: "NewStatus", index: "NewStatus", width: 85, label: "新施工状态", sortable: true, align: "left"},
				{name: "Status", index: "Status", width: 75, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "ContractFee", index: "ContractFee", width: 75, label: "合同费用", sortable: true, align: "right"},
				{name: "HasPay", index: "HasPay", width: 75, label: "已付款", sortable: true, align: "right"},
				{name: "BusinessManDescr", index: "BusinessManDescr", width: 75, label: "业务员", sortable: true, align: "left"},
				{name: "BusinessDept2", index: "delaydayss", width: 75, label: "业务部", sortable: true, align: "left"},
				{name: "DesignManDescr", index: "DesignManDescr", width: 75, label: "设计师", sortable: true, align: "left"},
				{name: "DesignDept2", index: "DesignDept2", width: 75, label: "设计部", sortable: true, align: "left"},
				{name: "ProjectMan", index: "ProjectMan", width: 75, label: "项目经理", sortable: true, align: "left"},
				{name: "ProjectManDept", index: "ProjectManDept", width: 75, label: "工程部", sortable: true, align: "left"},
			],
		});
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="pageContent">
				<div class="panel panel-system">
					<div class="panel-body">
						<div class="btn-group-xs">
							<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
							<button type="button" class="btn btn-system" id="updateStatus">
								<span>修改为新状态</span>
							</button>
							<button type="button" class="btn btn-system "  onclick="doExcelNow('停工标记表')" title="导出检索条件数据">
								<span>导出excel</span>
							</button>	
						</div>
					</div>
				</div>
					<div class="panel panel-info" hidden="true">  
						<div class="panel-body">
							<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
								<input type="hidden" name="jsonString" value="" />
							</form>
						</div>
					</div>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
			</div>
		</div>
	</body>	
</html>
