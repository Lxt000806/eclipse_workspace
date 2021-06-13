<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>明日施工计划</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function del(){
	var url = "${ctx}/admin/builderRep/doDelete";
	beforeDel(url,"pk");	
}
function dealRemark(){
	ret=selectDataTableRow();
	if (ret) {    	
      Global.Dialog.showDialog("DealRemark",{
		  title:"明日施工计划——录入处理备注",
		  url:"${ctx}/admin/builderRep/goDealRemark?pk="+ret.pk,
		  height: 350,
		  width:650,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

/**初始化表格*/
$(function(){
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/builderRep/goJqGrid",
			postData:{expired:"F"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 70,label:'主键',sortable : true,align : "left",formatter:cutStr,hidden:true},
			  {name : 'custcode',index : 'custcode',width : 70,label:'客户编号',sortable : true,align : "left",formatter:cutStr},		      
		      {name : 'address',index : 'address',width : 140,label:'楼盘地址',sortable : true,align : "left",frozen: true},
		      {name : 'projectmandescr',index : 'projectmandescr',width : 70,label:'项目经理',sortable : true,align : "left",frozen: true},
		      {name : 'projectphone',index : 'projectphone',width : 95,label:'项目经理电话',sortable : true,align : "left",frozen: true},
		      {name : 'department2',index : 'department2',width : 70,label:'工程部',sortable : true,align : "left",frozen: true},		
		      {name : 'nowspeeddescr',index : 'nowspeeddescr',width : 70,label:'当前进度',sortable : true,align : "left",frozen: true},	
		      {name : 'begindate',index : 'begindate',width : 85	,label:'日期',sortable : true,align : "left",formatter: formatDate},
		      {name : 'typedescr',index : 'typedescr',width : 70,label:'类型',sortable : true,align : "left",formatter:cutStr,hidden:true},
		      {name : 'buildstatusdescr',index : 'buildstatusdescr',width : 90,label:'计划类型',sortable : true,align : "left",formatter:cutStr},	    			  
		      {name : 'enddate',index : 'enddate',width : 85,label:'结束时间',sortable : true,align : "left",formatter: formatDate,hidden:true},
		      {name : 'remark',index : 'remark',width : 150,label:'备注',sortable : true,align : "left",frozen: true},
		      {name : 'dealremark',index : 'dealremark',width : 150,label:'处理备注',sortable : true,align : "left",frozen: true},
		      {name : 'intdept2descr',index : 'intdept2descr',width : 90,label:'集成部',sortable : true,align : "left",formatter:cutStr},
			  {name : 'intdesigndescr',index : 'intdesigndescr',width : 90,label:'集成设计师',sortable : true,align : "left",formatter:cutStr},
			  {name : 'zchousekeeper',index : 'zchousekeeper',width : 90,label:'主材管家',sortable : true,align : "left",formatter:cutStr},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ]
		});
	});  
	$("#custCode").openComponent_customer({showValue:"${builderRep.custCode}",condition:{'status':'4'}});
	$("#ProjectManDescr").openComponent_employee();
	$("#zcHousekeeper").openComponent_employee();
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#custcode").val('');
	$("#Department2").val('');
	$("#page_form input[type='text']").val('');
	$("#type").val('');
	$.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);
	$("#buildStatus").val('');
	$.fn.zTree.getZTreeObj("tree_buildStatus").checkAllNodes(false);
} 

function check(){
	var expired_show=document.getElementById("expired_show");
	expired_show.checked;
}
function doExcel() {
	var url = "${ctx}/admin/builderRep/doExcel";
	doExcelAll(url);
}
</script>
</head>
<body onload=check()>
		<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${builderrep.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<li>
					<label>楼盘地址</label>
					<input type="text"
						id="address" name="address" 
						value="${builderrep.address}" />
				</li>
				<li>
					<label>工程部</label>
					<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
				</li>
				<li>
					<label>项目经理</label>
					<input type="text" id="ProjectManDescr" name="ProjectManDescr"  value="${builderrep.projectManDescr}" />
				</li>
				
				<li hidden="true">
					<label>类型</label>
						<house:xtdmMulit id="type" dictCode="BLDREPTYPE" selectedValue="${builderrep.Type}"></house:xtdmMulit>
				</li>
				<li>	
					<label>集成部</label>
						<house:department2 id="Department2jc" dictCode="15"    ></house:department2>
				</li>
				<li>	
					<label>计划类型</label>
						<house:xtdmMulit id="buildStatus" dictCode="BUILDSTATUS" selectedValue="${builderrep.BuildStatus}"></house:xtdmMulit>
				</li>
				<li>	
					<label>计划时间从</label>
					<input type="text" id="beginDate" name="beginDate" class="i-date"  
					 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>	
					<label>至</label>
					<input type="text" id="endDate" name="endDate" 	 class="i-date"  
					 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
				</li>
				<li>
					<label>主材管家</label>
					<input type="text" id="zcHousekeeper" name="zcHousekeeper"  value="${builderrep.zcHousekeeper}" />
				</li>
				<li>
					<input type="checkbox"  id="expired_show" name="expired_show" value="${builderrep.expired }" onclick="hideExpired(this)" ${builderrep.expired!='T'?'checked':'' } />只显示当前数据&nbsp
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
				</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
		    		<house:authorize authCode="BUILDERREP_DEALREMARK">             
						<button type="button" class="btn btn-system " onclick="dealRemark()">录入处理备注</button>
					</house:authorize>
					<house:authorize authCode="builderRep_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


