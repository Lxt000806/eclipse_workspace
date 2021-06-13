<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户档案</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
 function hideexpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val(false);
	}else{
		$('#expired').val(true);
	}
} 
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customerDa/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/customerDa/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
        styleUI: 'Bootstrap',
		colModel : [
		  {name : 'documentno',index : 'documentno',width : 95,label:'档案编号',sortable : true,align : "left"} ,
	      {name : 'code',index : 'code',width : 95,label:'客户编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 95,label:'客户姓名',sortable : true,align : "left"},
	      {name : 'genderdescr',index : 'genderdescr',width : 75,label:'性别',sortable : true,align : "left"},
	      {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
	      {name : 'businessmandescr',index : 'businessmandescr',width : 85,label:'业务员',sortable : true,align : "left"},
	      {name : 'businessmandesc1',index : 'businessmandesc1',width : 140,label:'业务员一级部门',sortable : true,align : "left"},
	      {name : 'businessmandesc2',index : 'businessmandesc2',width : 140,label:'业务员二级部门',sortable : true,align : "left"},
	      {name : 'businessmandesc3',index : 'businessmandesc3',width : 140,label:'业务员三级部门',sortable : true,align : "left"},          
	      {name : 'designmandescr',index : 'designmandescr',width : 85,label:'设计师',sortable : true,align : "left"},
	      {name : 'designmandesc1',index : 'designmandesc1',width : 140,label:'设计师一级部门',sortable : true,align : "left"},
	      {name : 'designmandesc2',index : 'designmandesc2',width : 140,label:'设计师二级部门',sortable : true,align : "left"},
	      {name : 'designmandesc3',index : 'designmandesc3',width : 140,label:'设计师三级部门',sortable : true,align : "left"},
	      {name : 'projectmandescr',index : 'projectmandescr',width : 100,label:'项目经理',sortable : true,align : "left"},
	      {name : 'projectmandesc1',index : 'projectmandesc1',width : 140,label:'项目经理一级部门',sortable : true,align : "left"},
	      {name : 'projectmandesc2',index : 'projectmandesc2',width : 140,label:'项目经理二级部门',sortable : true,align : "left"},
	      {name : 'projectmandesc3',index : 'projectmandesc3',width : 140,label:'项目经理三级部门',sortable : true,align : "left"},
	      {name : 'layoutdescr',index : 'layoutdescr',width : 75,label:'户型',sortable : false,align : "left"},
	      {name : 'area',index : 'area',width : 75,label:'面积',sortable : false,align : "left"},
	      {name : 'signdate',index : 'signdate',width : 95,label:'签订日期',sortable : false,align : "left",formatter:formatDate},
	      {name : 'statusdescr',index : 'statusdescr',width : 95,label:'客户状态',sortable : false,align : "left"},
	      {name : 'endcodedescr',index : 'endcodedescr',width : 95,label:'结束代码',sortable : false,align : "left"},
	      {name : 'designstyledescr',index : 'designstyledescr',width : 95,label:'设计风格',sortable : false,align : "left"},
	      {name : 'constructtypedescr',index : 'constructtypedescr',width : 95,label:'施工方式',sortable : false,align : "left"},
	      {name : 'contractfee',index : 'contractfee',width : 95,label:'工程造价',sortable : false,align : "left"},
	      {name : 'designfee',index : 'designfee',width : 95,label:'设计费用',sortable : false,align : "left"}
        ]
	});
    $("#designMan").openComponent_employee();
    $("#businessMan").openComponent_employee();
    $("#projectMan").openComponent_employee();
    $("#builderCode").openComponent_builder();	
    onCollapse(44);
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" value="${customer.address }" />
					</li>
					<li>
						<label>设计师编号</label>
						<input type="text" id="designMan" name="designMan" value="${customer.designMan}" />
					</li>
					<li>
						<label>业务员编号</label>
						<input type="text" id="businessMan" name="businessMan" value="${customer.businessMan}" />
					</li>
					<li>
					    <label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" value="${customer.projectMan}"/>	
					</li>
					<li>
		                <label>项目名称</label>
						<input type="text" id="builderCode" name="builderCode" value="${customer.builderCode}"/>
					</li>
					<li>		
						<label>客户状态</label>
						<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and CBM in('4','5')" selectedValue="4,5"></house:xtdmMulit>
					</li>
					<li id="loadMore" >
						<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
					
					<div  class="collapse " id="collapse" >
						<ul>
							<li>
								<label>签订时间从</label>
								<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label>至</label>
								<input type="text" id="signDateTo" name="signDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>开工时间从</label>
								<input type="text" id="confirmBeginFrom" name="confirmBeginFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.confirmBegin}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label>至</label>
								<input type="text" id="confirmBeginTo" name="confirmBeginTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.confirmBegin}' pattern='yyyy-MM-dd'/>" />
							</li>

							<li class="search-group-shrink">
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>								
						
								<input type="checkbox" id="expired_show" name="expired_show"
												value="${customer.expired}" onclick="hideExpired(this)"
												${customer.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
					</div>
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
            	<house:authorize authCode="CUSTOMERDA_EXCEL">
 					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button> 
				 </house:authorize>
			</div>
		</div> 

		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
