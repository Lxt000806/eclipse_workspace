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
	<title>团队客户管理修改创建人</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#crtCzy").openComponent_employee({condition:{"empAuthority":"1"}});	
	$("#newCrtCzy").openComponent_employee({condition:{"empAuthority":"1","status":"ACT"}});	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goUpdateCrtJqGrid",
		multiselect: true,
		height:400,
		styleUI: "Bootstrap", 
		colModel : [
			{name:"code",	index:"code",	width:90,	label:"资源客户编号",	sortable:true,align:"left",hidden:true},
			{name:"crtczydescr",	index:"crtczydescr",	width:70,	label:"创建人",	sortable:true,align:"left",},
			{name:"crtdate",	index:"crtdate",	width:120,	label:"创建时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"crtczydept",	index:"crtczydept",	width:90,	label:"创建人部门",	sortable:true,align:"left",},
			{name:"custresstat",index:"custresstat",width:95,	label:"客户资源状态",	sortable:true,align:"left",},
			{name:"address",index:"address",width:150,	label:"楼盘",	sortable:true,align:"left",},
			{name:"descr",	index:"descr",	width:80,	label:"客户名称",	sortable:true,align:"left",},
			{name:"custaddress",index:"custaddress",width:150,	label:"意向客户楼盘",	sortable:true,align:"left",},
			{name:"custstatus",index:"custstatus",width:95,	label:"意向客户状态",	sortable:true,align:"left",},
			{name:"custcrtdate",index:"custcrtdate",width:120,	label:"转意向时间",	sortable:true,align:"left",formatter: formatTime},
		],
	});
	
	
	$("#saveBtn").on("click",function(){
		var crtCzy = $.trim($("#crtCzy").val());
	    var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		var json = Global.JqGrid.selectToJson("dataTable");
		var codes="";
	 	$.each(ids,function(i,id){
			var rowData = $("#dataTable").getRowData(id);
			codes+=rowData['code']+",";
		});
		
		if(ids==''||ids==null){
	    	art.dialog({
	    		content:'请选择一条或多条数据',
	    	});
	    	return false;
	    }
	    if($("#newCrtCzy").val()==""){
	   		art.dialog({
	    		content:'请选择新创建人',
	    	});
	    	return false;
	    }
	    $.ajax({
			url:"${ctx}/admin/ResrCust/doUpdateCrtCzy",
			type:'post', 
			data:{codes:codes,crtCzy:$("#newCrtCzy").val()},
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
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="showType" id="showType" value="2" />
					<ul class="ul-form">
						<li>
							<label>新创建人</label>
							<input type="text" id="newCrtCzy" name="newCrtCzy" style="width:160px;"/>
						</label>
						</li>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#queryResult" data-toggle="tab">查询结果</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="queryResult"  class="tab-pane fade in active"> 
					<div class="pageContent">
	         			<div class="body-box-list" style="margin-top: 5px;">
							<div class="query-form" style="border: 0px;">
								<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
									<input type="hidden" name="jsonString" value="" />
									<input type="hidden" name="showType" id="showType" value="2" />
									<ul class="ul-form">
										<li>
											<label>原创建人</label>
											<input type="text" id="crtCzy" name="crtCzy" style="width:160px;"/>
										</li>
										<li>
											<label>客户资源状态</label>
											<house:xtdmMulit id="custResStat" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTRESSTAT' and CBM in ('3','0','1')  " selectedValue="0,1,3"></house:xtdmMulit>
										</li>
										<li>
											<label>客户状态</label>
											<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and CBM in ('3','2','1')  " ></house:xtdmMulit>
										</li>
										<li>
											<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
										</li>
									</ul>	
								</form>
							</div>
					<div id="content-list">
						<table id= "dataTable"></table>
						<div id="dataTablePager"></div>
					</div> 
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
	</body>	
</html>
