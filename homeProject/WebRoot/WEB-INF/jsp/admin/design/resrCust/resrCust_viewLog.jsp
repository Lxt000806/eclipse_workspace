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
	<title>资源客户操作日志</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
$("#tabs").tabs();
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}'});	
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goResrLogJqGrid",
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'PK',	index:'PK',	width:90,	label:'PK',	sortable:true,align:"left", hidden:true},
				{name:'ResrCode',	index:'ResrCode',	width:90,	label:'客户编号',	sortable:true,align:"left",},
				{name:'Descr',	index:'Descr',	width:90,	label:'客户名称',	sortable:true,align:"left",},
				{name:'Address',	index:'Address',	width:140,	label:'楼盘地址',	sortable:true,align:"left",},
				{name:'BuilderCode',	index:'BuilderCode',	width:90,	label:'项目编号',	sortable:true,align:"left", hidden:true},
				{name:'builderdescr',	index:'builderdescr',	width:90,	label:'项目编号',	sortable:true,align:"left",  },
				{name:'BusinessMan',	index:'BusinessMan',	width:90,	label:'所属人员',	sortable:true,align:"left",hidden:true},
				{name:'businessmandescr',	index:'businessmandescr',	width:90,	label:'所属人员',	sortable:true,align:"left",},
				{name:'Status',	index:'Status',	width:90,	label:'状态',	sortable:true,align:"left",hidden:true},
				{name:'statusdescr',	index:'statusdescr',	width:60,	label:'状态',	sortable:true,align:"left", },
				{name:'Gender',	index:'Gender',	width:50,	label:'性别',	sortable:true,align:"left",hidden:true},
				{name:'genderdescr',	index:'genderdescr',	width:50,	label:'性别',	sortable:true,align:"left"},
				{name:'Mobile1',	index:'Mobile1',	width:90,	label:'手机',	sortable:true,align:"left",},
				{name:'LastUpdate',	index:'LastUpdate',	width:140,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
				{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:90,	label:'最后修改人员',	sortable:true,align:"left",},
				{name:'ActionLog',	index:'ActionLog',	width:90,	label:'操作日志',	sortable:true,align:"left",},
		],
	});
});

</script>
</head>
	<body>
		<div class="body-box-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
				</div>
		<div class="panel panel-info" >  
			<div class="panel-body">			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="resrCode" name="resrCode" style="width:160px;" value="${resrCustLog.resrCode }"/>
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${resrCustLog.descr }"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${resrCustLog.address }"/>
							</li>
							<li>
								<label>手机号码</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${resrCustLog.mobile1 }"/>
							</li>
							<li>
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${resrCustLog.builderCode }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="RESRCUSTSTS" value="${resrCustLog.status}"></house:xtdm>                     
							</li>
							<li>
								<label>业务员</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${resrCustLog.businessMan }"/>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
				</div>
				</div>
				<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">修改日志</a></li>
	   	 </ul>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
