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
		url:"${ctx}/admin/prjStageDetail/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		loadonce: true,
		rowNum:100000,
		colModel : [
			{name:'楼盘',	index:'楼盘',	width:120,	label:"楼盘",	sortable:true,align:"left" },
			{name:'项目经理',	index:'项目经理',	width:70,	label:"项目经理",	sortable:true,align:"left" },
			{name:'客户类型',	index:'客户类型',	width:70,	label:"客户类型",	sortable:true,align:"left"  },
			{name:'面积',	index:'面积',	width:60,	label:"面积",	sortable:true,align:"right"},
			{name:'施工阶段',	index:'施工阶段',	width:70,	label:"施工阶段",	sortable:true,align:"left" },
			{name:'阶段开始日期',	index:'阶段开始日期',	width:90,	label:"阶段开始日期",	sortable:true,align:"left", formatter: formatDate  },
			{name:'阶段结束日期',	index:'阶段结束日期',	width:90,	label:"阶段结束日期",	sortable:true,align:"left", formatter: formatDate  },
			{name:'施工天数',	index:'施工天数',	width:80,	label:"施工天数",	sortable:true,align:"right"  },
			{name:'阶段考核工期',	index:'阶段考核工期',	width:90,	label:"阶段考核工期",	sortable:true,align:"right"  },
			{name:'65天工期',	index:'65天工期',	width:80,	label:"65天工期",	sortable:true,align:"right"  },
			{name:'考核延误',	index:'考核延误',	width:80,	label:"考核延误",	sortable:true,align:"right"  },
			{name:'提报停工问题',	index:'提报停工问题',	width:90,	label:"提报停工问题",	sortable:true,align:"left"  },
			{name:'罚款金额',	index:'罚款金额',	width:70,	label:"罚款金额",	sortable:true,align:"right"  },
			{name:'65天延误',	index:'65天延误',	width:80,	label:"65天延误",	sortable:true,align:"right"  },
			{name:'最后签到工种',	index:'最后签到工种',	width:90,	label:"最后签到工种",	sortable:true,align:"left"  },
			{name:'最后签到时间',	index:'最后签到时间',	width:90,	label:"最后签到时间",	sortable:true,align:"left", formatter: formatDate },
			{name:'工程部',	index:'工程部',	width:80,	label:"工程部",	sortable:true,align:"left"  },
		],
	});
	
	window.goto_query = function(){
		if($("#beginDateFrom").val()=="" || $("#beginDateTo").val() ==""){
			art.dialog({
				content:"请填写完整时间段",
			});
			return;
		}
		var dateFrom=new Date($.trim($("#dateFrom").val()));
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/prjStageDetail/goJqGrid",}).trigger("reloadGrid");
	}
});

function doExcel(){
	var url = "${ctx}/admin/prjStageDetail/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department1").val('');
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	$("#department2").val('');
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>阶段开始时间从</label>
							<input type="text" id="beginDateFrom" name="beginDateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"
									value="<fmt:formatDate value='${customer.dateFrom }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="beginDateTo" name="beginDateTo" class="i-date" style="width:160px;"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${customer.dateTo }'  pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>阶段完成时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;"/>
						</li>
						<li>
	                        <label>一级部门</label>
	                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
	                            sqlLableKey="desc1" sqlValueKey="code" ></house:DictMulitSelect>
	                    </li>
						<li>
							<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where  deptype='3' and Expired='F' order By dispSeq  " 
							sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
						</li>
						<li>
							<label>阶段完成</label>
							<house:xtdm id="isComplete" dictCode="YESNO"></house:xtdm>
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
					<house:authorize authCode="PRJSTAGEDETAIL_EXCEL">
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
