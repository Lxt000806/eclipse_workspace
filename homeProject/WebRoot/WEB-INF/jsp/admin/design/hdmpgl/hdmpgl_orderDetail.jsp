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
	<title>活动门票管理——下定明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/hdmpgl/doOrderDetailExcel";
	doExcelAll(url);
}
$(function(){
	var activityNo='';
	function changeActNo(data){
		activityNo=data.No;
		$("#supplCode").openComponent_supplier({condition:{isActSuppl:"1",actNo:data.no==null}});	
		
	}

	$("#actNo").openComponent_activity({callBack:changeActNo});	
	$("#provideCZY").openComponent_czybm();	
	$("#supplCode").openComponent_supplier({condition:{isActSuppl:"1",actNo:activityNo }});	
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
	//初始化表格
	var gridOption ={
		//url:"${ctx}/admin/hdmpgl/goOrderDetailJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		colModel : [
			{name:'actname',	index:'actname',	width:110,	label:'活动名称',	sortable:true,align:"left",},
			{name:'ticketno',	index:'ticketno',	width:90,	label:'门票号',	sortable:true,align:"left",},
			{name:'custdescr',	index:'custdescr',	width:90,	label:'客户姓名',	sortable:true,align:"left",},
			{name:'address',	index:'address',	width:110,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'suppldescr',	index:'suppldescr',	width:90,	label:'供应商名称',	sortable:true,align:"left",},
			{name:'suppltypedescr',	index:'suppltypedescr',	width:90,	label:'供应商类型',	sortable:true,align:"left",},
			{name:'providedescr',	index:'providedescr',	width:90,	label:'发放人员',	sortable:true,align:"left",},
			{name:'department1descr',	index:'department1descr',	width:90,	label:'发放人部门1',	sortable:true,align:"left",},
			{name:'department2descr',	index:'department2descr',	width:90,	label:'发放人部门2',	sortable:true,align:"left",},
			{name:'providetypedescr',	index:'providetypedescr',	width:90,	label:'发放类型',	sortable:true,align:"left",},
			{name:'providedate',	index:'providedate',	width:110,	label:'发放时间',	sortable:true,align:"left",formatter:formatTime},
			{name:'orderdate',	index:'orderdate',	width:110,	label:'最后修改时间',	sortable:true,align:"left",formatter:formatTime},
		],
	};
			Global.JqGrid.initJqGrid("dataTable",gridOption);
	
});

function query(){
	var actNo= $.trim($('#actNo').val());
	if(actNo==''||actNo==null){
		art.dialog({
			content:'请选择活动编号',
		});
		return false;
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/hdmpgl/goOrderDetailJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
							<button type="button" class="btn btn-sm btn-system  "  onclick="doExcel()" title="导出检索条件数据">导出Excel</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					
					</div>
				</div>
			</div>
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">	
							<li>
								<label>活动编号</label>
								<input type="text" id="actNo" name="actNo" style="width:160px;" value="${hdmpgl.actNo }"/>
							</li>
							<li>
								<label>发放人员</label>
								<input type="text" id="provideCZY" name="provideCZY" style="width:160px;" value="${hdmpgl.provideCZY }"/>
							</li>
							<li>
								<label>发放人部门1</label>
								<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${hdmpgl.department1 }"></house:department1>
							</li>
							<li>	
								<label>发放人部门2</label>
								<house:department2 id="department2" dictCode="${hdmpgl.department1 }" value="${hdmpgl.department2 }"></house:department2>
							</li>
							<li>
								<label>发放类型</label>
								<house:xtdm  id="provideType" dictCode="TICKETPROVIDE"   style="width:160px;" value="${hdmpgl.provideType}"></house:xtdm>
							</li>
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode" style="width:160px;" value="${hdmpgl.supplCode }"/>
							</li>
							<li class="search-group-shrink" >
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
				</form>
			</div>
		</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
		
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
