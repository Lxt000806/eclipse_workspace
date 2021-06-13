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
	<title>离职人员客户管理修改业务员</title>
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
	$("#stakeholder").openComponent_employee({condition:{"empAuthority":"1"}});	
	$("#newStakeholder").openComponent_employee({condition:{"empAuthority":"1","status":"ACT"}});	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/leaveEmpCustManage/goJqGrid",
		postData:{showType:""} ,
		multiselect: true,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: "Bootstrap", 
		colModel : [
			{name:"custcode",	index:"custcode",	width:180,	label:"客户编号",	sortable:true,align:"left",hidden:true},
			{name:"address",	index:"address",	width:180,	label:"楼盘地址",	sortable:true,align:"left",},
			{name:"statusdescr",	index:"statusdescr",	width:90,	label:"客户状态",	sortable:true,align:"left" ,},
			{name:"layoutdescr",	index:"layoutdescr",	width:90,	label:"户型",	sortable:true,align:"left",},
			{name:"area",	index:"area",	width:70,	label:"面积",	sortable:true,align:"right",},
			{name:"businessmandescr",	index:"businessmandescr",	width:90,	label:"业务员",	sortable:true,align:"left",},
			{name:"businessdept",	index:"businessdept",	width:90,	label:"业务部",	sortable:true,align:"left",},
			{name:"againmandescr",	index:"againmandescr",	width:90,	label:"翻单员",	sortable:true,align:"left",},
			{name:"designmandescr",	index:"designmandescr",	width:110,	label:"设计师",	sortable:true,align:"left",},
			{name:"designdept",	index:"designdept",	width:70,	label:"设计部",	sortable:true,align:"left",},
			{name:"endcodedescr",	index:"endcodedescr",	width:90,	label:"结束代码",	sortable:true,align:"left",},
			{name:"sourcedescr",	index:"sourcedescr",	width:90,	label:"来源",	sortable:true,align:"left",},
			{name:"endremark",	index:"endremark",	width:200,	label:"备注",	sortable:true,align:"left",},
			{name:"crtdate",	index:"crtdate",	width:150,	label:"创建时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"setdate",	index:"setdate",	width:150,	label:"下定时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"signdate",	index:"signdate",	width:150,	label:"签订时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"dpk",	index:"dpk",	width:150,	label:"设计干系人pk",	sortable:true,align:"left",hidden:true},
			{name:"bpk",	index:"bpk",	width:150,	label:"业务干系人pk",	sortable:true,align:"left",hidden:true},
		
		],
	});
	
	
	$("#saveBtn").on("click",function(){
		var chengeCheckMan = $.trim($("#chengeCheckMan").val());
	    var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		var json = Global.JqGrid.selectToJson("dataTable");
		if(ids==''||ids==null){
	    	art.dialog({
	    		content:'请选择一条或多条数据',
	    	});
	    	return false;
	    }
	    if($("#newStakeholder").val()==""){
	   		art.dialog({
	    		content:'请选择改后业务员',
	    	});
	    	return false;
	    }
	    $.ajax({
			url:"${ctx}/admin/leaveEmpCustManage/doChgStakeholder",
			type:'post', 
			data:{dataStering:json.detailJson,showType:"2",stakeholder:$("#newStakeholder").val()},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if(obj.rs){
					art.dialog({
						content:obj.msg,
						time:1000,
					});	
				}else{
					art.dialog({
						content:obj.msg,
						time:1000,
					});
				}
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
			}
		}); 

	});
});


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel-body">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="showType" id="showType" value="2" />
					<ul class="ul-form">
						<li hidden="true">
							<label>离职日期从</label> 
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
								value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li hidden="true">
							<label>至</label> 
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" 
								value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>业务员</label>
							<input type="text" id="stakeholder" name="stakeholder" style="width:160px;"/>
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and CBM in ('2','1')  " selectedValue="1,2"></house:xtdmMulit>
						</li>
						<li>
							<label>改后业务员</label>
							<input type="text" id="newStakeholder" name="newStakeholder" style="width:160px;"/>
						</label>
						</li>
						<li>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
	</body>	
</html>
