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
	<title>工种施工情况分析表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_prjRegion.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var Department2="";
function doExcel(){
	var url = "${ctx}/admin/workTypeConstructDetail/doExcel";
	doExcelAll(url);
}

$(function(){
 	var dateFrom =$.trim($("#dateFrom").val());
 	var dateTo =$.trim($("#dateTo").val());
	var postData = $("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/workTypeConstructDetail/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'WorkType12',	index:'WorkType12',	width:90,	label:'WorkType12',	sortable:true,align:"left" ,hidden:true,frozen: true},
			{name:'workerType12Descr',	index:'workerType12Descr',	width:80,label:'工种类型',	sortable:true,align:"left" ,count:true,frozen: true},
			{name:'ArrNum',	index:'ArrNum',	width:90,	label:'安排工人套数',	sortable:true,align:"right" ,sum:true},
			{name:'arrworkload',index:'arrworkload',	width:90,	label:'安排工资额',	sortable:true,align:"right" ,sum:true},
			{name:'inTimeRate',	index:'inTimeRate',	width:60,	label:'及时率',	sortable:true,align:"right" ,},
			{name:'AvgDelayDay',	index:'AvgDelayDay',	width:90,	label:'平均拖延天数',	sortable:true,align:"right" ,},
			{name:'FirstSignInTimeNum',	index:'FirstSignInTimeNum',	width:90,	label:'及时签到套数',	sortable:true,align:"right",sum:true},
			{name:'FirstSignInTimeRate',	index:'FirstSignInTimeRate',	width:90,	label:'及时签到率',	sortable:true,align:"right" ,},
			{name:'SignRate',	index:'SignRate',	width:90,	label:'签到率',	sortable:true,align:"right" ,},
			{name:'UsedRate',	index:'UsedRate',	width:90,	label:'使用率',	sortable:true,align:"right" ,},
			{name:'ConfirmNum',	index:'ConfirmNum',	width:70,	label:' 验收套数',	sortable:true,align:"right" ,sum:true},
			{name:'ConInTimeRate',	index:'ConInTimeRate',	width:75,	label:'验收及时率',	sortable:true,align:"right" ,},
			{name:'ConPass',	index:'ConPass',	width:90,	label:'验收通过套数',	sortable:true,align:"right" ,sum:true},
			{name:'LevelOneAvg',	index:'LevelOneAvg',	width:90,	label:'一级工地占比',	sortable:true,align:"right" ,},
			{name:'CompleteNum',	index:'CompleteNum',	width:90,	label:'完工套数',	sortable:true,align:"right" ,sum:true},
			{name:'AvgConsDay',	index:'AvgConsDay',	width:90,	label:'平均施工天数',	sortable:true,align:"right" ,},
			{name:'InTimeConfirm',	index:'InTimeConfirm',	width:90,	label:'按时完工套数',	sortable:true,align:"right" ,sum:true},
			{name:'AvgInTimeDays',	index:'AvgInTimeDays',	width:90,	label:'按时平均天数',	sortable:true,align:"right" },
			{name:'NotSignNum',	index:'NotSignNum',	width:90,	label:'未签到套数',	sortable:true,align:"right" ,sum:true},
			{name:'InTimeConfirmRate',	index:'InTimeConfirmRate',	width:90,	label:'按时完工率',	sortable:true,align:"right" ,},
			{name:'BuilderRepDay',	index:'BuilderRepDay',	width:90,	label:'停工报备天数',	sortable:true,align:"right" ,sum:true},
			{name:'WorkerCost',	index:'WorkerCost',	width:78,	label:'工资发放额',	sortable:true,align:"right" ,sum:true},
			{name:'IsSignNum',	index:'IsSignNum',	width:75,	label:'班组数',	sortable:true,align:"right" ,sum:true},
			{name:'WithOutWorkerRate',	index:'WithOutWorkerRate',	width:75,	label:'无工地占比',	sortable:true,align:"right" ,},
			{name:'SignNum',	index:'SignNum',	width:75,	label:'本日签到人数',	sortable:true,align:"right" ,},
			{name:'OnConsNum',	index:'OnConsNum',	width:75,	label:'在建工地数',	sortable:true,align:"right" ,sum:true},
			{name:'OverPlanEndCons',	index:'OverPlanEndCons',	width:75,	label:'超期工地数',	sortable:true,align:"right" ,sum:true},
			{name:'MaxOnCons',	index:'MaxOnCons',	width:100,	label:'工人最大在建数',	sortable:true,align:"right" ,},
			{name:'WaitArrNum',	index:'WaitArrNum',	width:70,	label:'待安排数',	sortable:true,align:"right" ,sum:true},
			{name:'WaitConNum',	index:'WaitConNum',	width:80,	label:'待验收套数',	sortable:true,align:"right" ,sum:true},

		],
		gridComplete:function(){
        	$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
        },
	});
	 $("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'ArrNum', numberOfColumns: 8, titleText: '工人安排'},
						{startColumnName: 'ConfirmNum', numberOfColumns: 12, titleText: '施工与验收'},
						{startColumnName: 'IsSignNum', numberOfColumns: 8, titleText: '当前情况'}
		],
	});
	$("#dataTable").jqGrid('setFrozenColumns');

	$("#viewHasArr").on("click",function(){
		var ret = selectDataTableRow();
		var dateFrom =$.trim($("#dateFrom").val());
		var dateTo =$.trim($("#dateTo").val());
		var custType =$.trim($("#custType").val());
		var isSign =$.trim($("#isSign").val());
		var prjRegionCode =$.trim($("#prjRegionCode").val());
		var workType12Dept =$.trim($("#workType12Dept").val());
		var workerClassify =$.trim($("#workerClassify").val());
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"工种施工情况——已安排工人列表",
				url:"${ctx}/admin/workTypeConstructDetail/goViewHasArr",
				postData:{workType12:ret.WorkType12,dateFrom:dateFrom,dateTo:dateTo,
					custType:custType,isSign:isSign,department2:Department2,prjRegionCode:prjRegionCode,
					workType12Dept:workType12Dept,workerClassify:workerClassify},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewComplete").on("click",function(){
		var ret = selectDataTableRow();
		var dateFrom =$.trim($("#dateFrom").val());
		var dateTo =$.trim($("#dateTo").val());
		var custType =$.trim($("#custType").val());
		var isSign =$.trim($("#isSign").val());
		var prjRegionCode =$.trim($("#prjRegionCode").val());
		var workType12Dept =$.trim($("#workType12Dept").val());
		var workerClassify =$.trim($("#workerClassify").val());
		if(ret){
			Global.Dialog.showDialog("viewComplete",{
				title:"工种施工情况——完成情况",
				url:"${ctx}/admin/workTypeConstructDetail/goViewComplete",
				postData:{workType12:ret.WorkType12,dateFrom:dateFrom,dateTo:dateTo,
					custType:custType,isSign:isSign,department2:Department2,prjRegionCode:prjRegionCode,
					workType12Dept:workType12Dept,workerClassify:workerClassify},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewHasConf").on("click",function(){
		var ret = selectDataTableRow();
		var dateFrom =$.trim($("#dateFrom").val());
		var dateTo =$.trim($("#dateTo").val());
		var custType =$.trim($("#custType").val());
		var isSign =$.trim($("#isSign").val());
		var prjRegionCode =$.trim($("#prjRegionCode").val());
		var workType12Dept =$.trim($("#workType12Dept").val());
		var workerClassify =$.trim($("#workerClassify").val());
		if(ret){
			Global.Dialog.showDialog("viewHasConf",{
				title:"工种施工情况——验收明细",
				url:"${ctx}/admin/workTypeConstructDetail/goViewHasConf",
				postData:{workType12:ret.WorkType12,dateFrom:dateFrom,dateTo:dateTo,
				custType:custType,isSign:isSign,department2:Department2,prjRegionCode:prjRegionCode,
				workType12Dept:workType12Dept,workerClassify:workerClassify},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewOndo").on("click",function(){
		var ret = selectDataTableRow();
		var dateFrom =$.trim($("#dateFrom").val());
		var dateTo =$.trim($("#dateTo").val());
		var custType =$.trim($("#custType").val());
		var isSign =$.trim($("#isSign").val());
		var prjRegionCode =$.trim($("#prjRegionCode").val());
		var workType12Dept =$.trim($("#workType12Dept").val());
		var workerClassify =$.trim($("#workerClassify").val());
		if(ret){
			Global.Dialog.showDialog("viewOndo",{
				title:"工种施工情况——班组在建工地",
				url:"${ctx}/admin/workTypeConstructDetail/goViewOndo",
				postData:{workType12:ret.WorkType12,dateFrom:dateFrom,dateTo:dateTo,
					custType:custType,isSign:isSign,department2:Department2,prjRegionCode:prjRegionCode,
					workType12Dept:workType12Dept,workerClassify:workerClassify},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewBuilderRep").on("click",function(){
		var ret = selectDataTableRow();
		var dateFrom =$.trim($("#dateFrom").val());
		var dateTo =$.trim($("#dateTo").val());
		var custType =$.trim($("#custType").val());
		var isSign =$.trim($("#isSign").val());
		var prjRegionCode =$.trim($("#prjRegionCode").val());
		var workType12Dept =$.trim($("#workType12Dept").val());
		var workerClassify =$.trim($("#workerClassify").val());
		if(ret){
			Global.Dialog.showDialog("ViewBuilderRep",{
				title:"工种施工情况——停工报备",
				url:"${ctx}/admin/workTypeConstructDetail/goViewBuilderRep",
				postData:{workType12:ret.WorkType12,dateFrom:dateFrom,dateTo:dateTo,custType:custType,
					isSign:isSign,department2:Department2,prjRegionCode:prjRegionCode,
					workType12Dept:workType12Dept,workerClassify:workerClassify},
				height:700,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewChart").on("click",function(){
		var tableData = Global.JqGrid.allToJson("dataTable");
		Global.Dialog.showDialog("ViewBuilderRep",{
			title:"工种施工情况——图表展示",
			url:"${ctx}/admin/workTypeConstructDetail/goViewChart",
			postData:{tableData:tableData.detailJson},
			height:550,
			width:1250,
			returnFun:goto_query
		});
	});
	
	
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	
	$("#prjRegionCode_NAME").val('');
	$("#custType_NAME").val('');
	$("#isSign_NAME").val('');
	$("#workType12Dept_NAME").val("");
	$("#workType12Dept").val("");
	$.fn.zTree.getZTreeObj("tree_isSign").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_prjRegionCode").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_workType12Dept").checkAllNodes(false);
}
 
function goto_query(){
	Department2=$.trim($("#department2").val());
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" value="${customer.expired}" />
					<ul class="ul-form">
						<li>
							<label>日期从</label> 
							<input type="text" id="dateFrom"
								name="dateFrom" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
								value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>至</label> 
							<input type="text" id="dateTo"
								name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
								value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>客户类型</label> 
							<house:xtdmMulit id="custType"
								dictCode="CUSTTYPE" selectedValue="${customer.custType }"></house:xtdmMulit>
						</li>
						<li>
							<label>二级部门</label> 
							<house:DictMulitSelect id="department2" dictCode="" 
								sql="select * from tDepartment2 where DepType='3' and expired='F'  " 
								sqlValueKey="code" sqlLableKey="desc1"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>工种类型1</label>
							<house:DictMulitSelect id="workType1" dictCode="" 
								sql="select rTrim(Code) Code,Descr from tWorkType1 where Expired = 'F' order by dispSeq " 
								sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>签约类型</label>
							<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
							sqlValueKey="Code" sqlLableKey="Descr"   selectedValue='0,1,2'></house:xtdmMulit>
						</li>
						<li>
							<label>工程大区</label>
							<house:DictMulitSelect id="prjRegionCode" dictCode="" 
								sql="select a.Code,a.Descr from tPrjRegion a  " 
								sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>工人分类</label>
							<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"></house:xtdmMulit>                     
						</li>
						<li>							
							<label>工种分组</label>
							<house:DictMulitSelect id="workType12Dept" dictCode="" sql="SELECT Code,Descr FROM  dbo.tWorkType12Dept WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li>
							<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" 
								onclick="hideExpired(this)" ${customer.expired!='T' ?'checked':'' }/>
							<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
    			<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="viewHasArr"><span>已安排工人列表</span> </button>
					<button type="button" class="btn btn-system" id="viewComplete"><span>工种施工完成情况</span> </button>
					<button type="button" class="btn btn-system" id="viewHasConf"><span>验收明细</span> </button>
					<button type="button" class="btn btn-system" id="viewOndo"><span>班组在建工地</span> </button>
					<button type="button" class="btn btn-system" id="viewBuilderRep"><span>停工报备列表</span> </button>
					<button type="button" class="btn btn-system" id="viewChart"><span>图表展示</span> </button>
					<button type="button" class="btn btn-system"  onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div> 
	</body>	
</html>
