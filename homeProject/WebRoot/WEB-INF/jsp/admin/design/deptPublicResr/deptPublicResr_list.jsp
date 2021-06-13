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
	<title>资源客户公海列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#businessMan").openComponent_employee();	
	$("#crtCzy").openComponent_employee();	
	$("#builderCode").openComponent_builder();	
	var postData =$("#page_form").jsonForm();
	postData.custKind="0,1,2,3";
 	var gridOption={
		url:"${ctx}/admin/deptPublicResr/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap',
		multiselect:true, 
		colModel : [
			{name:'code',	index:'code',	width:80,	label:'code',	sortable:true,align:"left",hidden: true},
 			{name:'address',	index:'address',	width:180,	label:'楼盘地址',	sortable:true,align:"left"},
			{name:'descr',	index:'descr',	width:90,	label:'客户名称',	sortable:true,align:"left"},
			{name:'recentlycondatedescr',	index:'recentlycondate',	width:120,	label:'最近联系时间',	sortable:true,align:"left"},
			{name:'crtdate',	index:'crtdate',	width:120,	label:'创建日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'custkinddescr',	index:'custkinddescr',	width:70,	label:'客户分类',	sortable:true,align:"left"},
			{name:'constructtypedescr',	index:'constructtypedescr',	width:70,	label:'客户类别',	sortable:true,align:"left"},
			{name:'custresstatdescr',	index:'custresstatdescr',	width:95,	label:'资源客户状态',	sortable:true,align:"left"},
			{name:'regiondescr',	index:'regiondescr',	width:60,	label:'区域',	sortable:true,align:"left"},
			{name:'remark',	index:'remark',	width:150,	label:'备注',	sortable:true,align:"left"},
			{name:'remarks',    index:'remarks',    width:300,  label:'跟踪内容',   sortable:true,align:"left"},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后操作人员',	sortable:true,align:"left"},
			{name:'lastupdate',	index:'lastupdate',	width:150,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
			{name:'expired',	index:'expired',	width:60,	label:'过期',	sortable:true,align:"left",},
			{name:'actionlog',	index:'actionlog',	width:80,	label:'操作日志',	sortable:true,align:"left"},
			{name:'custresstat',	index:'custresstat',	width:90,	label:'custresstat',	sortable:true,align:"left",hidden:true},
			{name:'businessman',	index:'businessman',	width:90,	label:'businessman',	sortable:true,align:"left",hidden:true},
		],
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#receive").on("click",function(){
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
			if(ret.custresstat=="3"){
				art.dialog({
					content:"存在【转意向】状态的记录，不能进行领用",
				});
				return false;
			}
		}
		if(ids.length>0){
			$.ajax({
				url:"${ctx}/admin/deptPublicResr/getCanReceive?codes=" + codes,
				type:'post',
				data:{},
				dataType:'json',
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					if (obj==true){
						Global.Dialog.showDialog("receive",{
							title:"资源客户公海——领用",
							url:"${ctx}/admin/deptPublicResr/goReceive",
							postData:{codes:codes},
							height:600,
							width:800,
							returnFun:goto_query
						});
					} else {
						art.dialog({
							content:"存在已有跟单员的资源，无法领取",
						});
						return;
					}
				}
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#conLog").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("conLog",{
				title:"资源客户公海——跟踪日志",
				url:"${ctx}/admin/deptPublicResr/goConLog",
				postData:{code:ret.code},
				height:600,
				width:1050,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
});

function doExcel(){
	var url = "${ctx}/admin/deptPublicResr/doExcel";
	doExcelAll(url);
}

function clearFormFn(){
	clearForm();
	$("#resrCustPoolNo").val("");
	$("#custKind").val("");
	$.fn.zTree.getZTreeObj("tree_resrCustPoolNo").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_custKind").checkAllNodes(false);
}

</script>
</head>
	<body>
		<div class="body-box-list " style="overflow-x:hidden">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="czybh" value="${resrCust.czybh}" />
					<input type="hidden" id="expired" name="expired" value="${resrCust.expired }" />
					<input type="hidden" name="custRight" value="${resrCust.custRight}" /> 
					<input type="hidden" name="type" value="3" /> 
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
							<li>
								<label>创建日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" />
							</li>
							<li>
								<label>资源客户状态</label>
								<house:xtdm id="custResStat" dictCode="CUSTRESSTAT" ></house:xtdm>                     
							</li>
							<li>
								<label>客户类别</label>
								<house:xtdm id="constructType" dictCode="CUSTCLASS" ></house:xtdm>                     
							</li>
							<li>
								<label>客户分类</label>
								<house:xtdmMulit id="custKind" dictCode="CUSTKIND" selectedValue="0,1,2,3"></house:xtdmMulit>                     
							</li>
							<li>
								<label>线索池</label>
								<house:DictMulitSelect id="resrCustPoolNo" dictCode="" 
								sql="select a.No Code,a.Descr from tResrCustPool a
									   	 where (exists (select 1 from tResrcustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = '${resrCust.czybh }')
									   	 or exists (select 1 from tCZYBM in_b where in_b.DefaultPoolNo = a.No and in_b.CZYBH = '${resrCust.czybh }')
									     or a.Descr = '默认线索池') and expired = 'F' or (a.ReceiveRule='0' and expired = 'F')"
								sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
							</li>
							<li class="search-group" >
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearFormFn();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
   			    <div class="btn-group-sm"  >
  			  		<house:authorize authCode="DEPTPUBLICRESR_RECEIVE">
						<button type="button" class="btn btn-system " id="receive"><span>领取</span> 
					</house:authorize>
					<house:authorize authCode="DEPTPUBLICRESR_CONLOG">
						<button type="button" class="btn btn-system " id="conLog"><span>跟踪日志</span> 
					</house:authorize>
					<house:authorize authCode="DEPTPUBLICRESR_EXCEL">
						<button type="button" class="btn btn-system "  onclick="doExcel()" ><span>导出excel</span>
					</house:authorize>
				</div>
			</div>
					<!-- panelBar End -->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
