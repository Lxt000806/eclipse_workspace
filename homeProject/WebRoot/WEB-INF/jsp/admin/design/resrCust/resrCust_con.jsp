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
	<title>跟踪记录</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goConJqGrid",
		postData:{code:"${resrCust.code}"},
		height:$(document).height()-$("#content-list").offset().top-82,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'pk',	index:'pk',	width:90,	label:'pk',	sortable:true,align:"left",hidden:true},
				{name:'custcode',	index:'custcode',	width:85,	label:'客户编号',	sortable:true,align:"left",},
				{name:'typedescr',	index:'typedescr',	width:90,	label:'跟踪类型',	sortable:true,align:"left",},
				{name:'conmandescr',	index:'conmandescr',	width:90,	label:'跟踪人',	sortable:true,align:"left",},
				{name:'condate',	index:'condate',	width:140,	label:'跟踪日期',	sortable:true,align:"left",formatter: formatTime},
				{name:'nextcondate',	index:'nextcondate',	width:95,	label:'下次联系时间',	sortable:true,align:"left",formatter: formatDate},
				{name:'conwaydescr',	index:'conwaydescr',	width:80,	label:'跟踪方式',	sortable:true,align:"left",},
				{name:'remarks',	index:'remarks',	width:400,	label:'跟踪说明',	sortable:true,align:"left",},
				{name:'callrecordpath',	index:'callrecordpath',	width:80,	label:'功能',	sortable:true,align:"left",formatter: formatBtns}
		],
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var today=formatDate(new Date());
		var condate=ret.condate.substring(0,10);
		if(today!=condate){
			art.dialog({
				content:"只能编辑跟踪日期是当天的记录！",
			});
			return ;
		}
		if(ret){
			Global.Dialog.showDialog("update",{
				title:"编辑跟踪记录",
				url:"${ctx}/admin/ResrCust/goUpdateCon",
				postData:{id:ret.pk},
	 			height: 340,
			    width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
});

function formatBtns(value, selectInfo) {
	if(value && value != "") {
		return "<a onclick=\"downloadFileView('" + selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">下载</a>&nbsp;&nbsp;<a onclick=\"listenCallRecoardView('" 
				+ selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">播放</a>";
	}
	return "";
}

function downloadFileView(dataTable, rowId, path) {
 	$("#downloadElem")[0].href = path;
	$("#downloadElem")[0].click(); 
}

function listenCallRecoardView(dataTable, rowId, path) {
	Global.Dialog.showDialog("CallRecord", {
		title:"录音",
		url: "${ctx}/admin/callRecord/goView",
		postData: {
			path: path
		},
		height:150,
		width:600,
	});
}

</script>
</head>
<body>
	<a id="downloadElem" download style="display: none"></a>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="update">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
</html>
