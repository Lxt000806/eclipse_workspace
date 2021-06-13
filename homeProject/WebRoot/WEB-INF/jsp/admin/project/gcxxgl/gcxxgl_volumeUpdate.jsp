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
	<title>工程信息管理编辑</title>
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
	$("#checkMan").openComponent_employee();
	$("#chengeCheckMan").openComponent_employee();
	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/gcxxgl/goDetailJqGrid",
		multiselect: true,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "code", index: "code", width: 75, label: "客户编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "genderdescr", index: "genderdescr", width: 40, label: "性别", sortable: true, align: "left"},
			{name: "sourcedescr", index: "sourcedescr", width: 65, label: "客户来源", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 60, label: "客户状态", sortable: true, align: "left"},
			{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "checkman", index: "checkman", width: 96, label: "巡检员", sortable: true, align: "left",hidden:true},
			{name: "checkmandescr", index: "checkmandescr", width: 50, label: "巡检员", sortable: true, align: "left"},
			{name: "layoutdescr", index: "layoutdescr", width: 40, label: "户型", sortable: true, align: "left"},
			{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right"},
			{name: "designstyledescr", index: "designstyledescr", width: 65, label: "设计风格", sortable: true, align: "left"},
			{name: "crtdate", index: "crtdate", width: 130, label: "创建时间", sortable: true, align: "left", formatter: formatTime},
			{name: "designmandescr", index: "designmandescr", width: 65, label: "设计师", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 65, label: "业务员", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 60, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 40, label: "操作", sortable: true, align: "left"}
		],
	});
	
	//全选
	$("#selAll").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",true);
	});
	//全不选
	$("#selNone").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",false);
	});
	
	$("#saveBtn").on("click",function(){
		var chengeCheckMan = $.trim($("#chengeCheckMan").val());
	    var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	    var code=new Array();
	    if(ids==''||ids==null){
	    	art.dialog({
	    		content:'请选择一条或多条数据',
	    	});
	    	return false;
	    }
	    if(chengeCheckMan==''||chengeCheckMan==null){
	    	art.dialog({
	    		content:'请选择改后巡检员',
	    	});
	    	return false;
	    }
	    
	    $.each(ids,function(k,id){
    		var row = $("#dataTable").jqGrid('getRowData', id);
    		code.push(row.code);
    	});
    	 	$.ajax({
					url:"${ctx}/admin/gcxxgl/doChengeCheckMan",
					type:'post',
					data:{code:JSON.stringify(code),chengeCheckMan:chengeCheckMan},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
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
						<ul class="ul-form">
							<li>
								<label>巡检员</label>
								<input type="text" id="checkMan" name="checkMan" style="width:160px;" value="${customer.checkMan }"/>
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and (CBM='4')  " selectedValue="4"></house:xtdmMulit>
							</li>
							<li>
								<label>施工状态</label>
								<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS"  selectedValue="${customer.constructStatus }"></house:xtdmMulit>
							</li>
							<li>
								<label>改后巡检员</label>
								<input type="text" id="chengeCheckMan" name="chengeCheckMan" style="width:160px;" value="${customer.chengeCheckMan }"/>
							</label>
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>	
				</form>
				</div>
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
