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
	<title>工程部阶段分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjStageProgAnaly/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		loadonce: true,
		rowNum:100000,
		colModel : [
			{name:'Code',	index:'Code',	width:90,	label:"Code",	sortable:true,align:"left" ,hidden:true},
			{name:'Descr',	index:'Descr',	width:80,	label:"施工阶段",	sortable:true,align:"left" },
			{name:'CompleteNum',	index:'CompleteNum',	width:80,	label:"完成套数",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1} },
			{name:'OnTimeNum',	index:'OnTimeNum',	width:80,	label:"及时套数",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1} },
			{name:'DelayNum',	index:'DelayNum',	width:80,	label:"拖期套数",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1} },
			{name:'OnTimeRate',	index:'OnTimeRate',	width:90,	label:"及时完成率",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
			{name:'AvgNum',	index:'AvgNum',	width:80,	label:"平均天数",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1} },
			{name:'DelayAmount',	index:'DelayAmount',	width:80,	label:"罚款金额",	sortable:true,align:"right" ,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1} },
			{name:'ConsInProgNum', index:'ConsInProgNum', width: 100, label:"当前在建套数", sortable:true, align:"right", formatter:'number', formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 1}},
			{name:'ConsInProgOverdueNum', index:'ConsInProgOverdueNum', width:100, label:"当前拖期套数", sortable:true, align:"right", formatter:'number', formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 1}},
			{name:'ConsToBeCompNum', index:'ConsToBeCompNum', width:100, label:"应完工套数", sortable:true, align:"right", formatter:'number', formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 1}},
		],
	});
	
	window.goto_query = function(){
		if($("#dateFrom").val()=="" || $("#dateTo").val() ==""){
			art.dialog({
				content:"请填写完整时间段",
			});
			return;
		}
		var dateFrom=new Date($.trim($("#dateFrom").val()));
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/prjStageProgAnaly/goJqGrid",}).trigger("reloadGrid");
	}
});

function doExcel(){
	var url = "${ctx}/admin/prjStageProgAnaly/doExcel";
	doExcelAll(url);
}

function view(){
	if(!$("#dateFrom").val()){
		art.dialog({
			content:"请选择一个开始时间"
		});
		return ;
	}
	if(!$("#dateTo").val()){
		art.dialog({
			content:"请选择一个结束时间"
		});
		return ;
	}
	var postData = $("#page_form").jsonForm();
	var ret = selectDataTableRow("dataTable");
	postData.progStage=ret.Code;
	if(ret){
       	Global.Dialog.showDialog("goDetail",{
       		title:"工程部阶段进度分析--查看明细",
       	  	url:"${ctx}/admin/prjStageProgAnaly/goDetail",
       	  	postData:postData,
       	  	height:window.screen.height-130,
		 	width:window.screen.width-40
       	});
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department1").val('');
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	$("#department2").val('');
	$.fn.zTree.getZTreeObj("tree_layout").checkAllNodes(false);
	$("#layout").val('');
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="status" value="1" />
					<ul class="ul-form">
						<li>
							<label>阶段开始时间从</label>
							<input type="text" id="beginDateFrom" name="beginDateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"
									value="<fmt:formatDate value='${customer.beginDateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="beginDateTo" name="beginDateTo" class="i-date" style="width:160px;"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.beginDateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>阶段完成时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"
									value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>开工日期从</label>
							<input type="text" id="confirmBeginFrom" name="confirmBeginFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${customer.confirmBeginFrom}'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmBeginTo" name="confirmBeginTo" class="i-date"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.confirmBeginTo}'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where  deptype='3' and Expired='F' order By dispSeq  " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>
							<label>施工阶段</label> <house:dict id="progStage"
							dictCode="" sql="select code,code+' '+descr descr from tProgStage where expired='F' "
							sqlLableKey="descr" sqlValueKey="code" ></house:dict>
						</li>
						<li>
							<label>户型</label> 
							<house:xtdmMulit id="layout" dictCode="LAYOUT" selectedValue="0,1"></house:xtdmMulit>
                        </li>
                        <li>
                        	<label>装修类型</label> 
                        	<house:xtdm id="isPartDecorate" dictCode="DECTYPE" value="0"></house:xtdm>
						</li>
						<li>
	                        <label>一级部门</label>
	                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
	                            sqlLableKey="desc1" sqlValueKey="code" ></house:DictMulitSelect>
	                    </li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="PRJSTAGEPROGANALY_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize> 
					<house:authorize authCode="PRJSTAGEPROGANALY_EXCEL">
						<button type="button" class="btn btn-system"  onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div>
	</body>	
</html>
