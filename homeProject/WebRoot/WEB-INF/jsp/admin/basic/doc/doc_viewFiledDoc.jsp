<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>文档管理——查看归档文档</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	$("#department").openComponent_department();	
	
	var postData = $("#page_form").jsonForm();
	var url = "${ctx}/admin/doc/goJqGrid";
	if("${modal }" == "docQuery"){
		url = "${ctx}/admin/docQuery/goJqGrid";
	}
	
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable",{
		url : url,
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:100000,
		colModel : [
			{name: "pk", index: "pk", width: 130, label: "pk", sortable: true, align: "left", hidden:true},
			{name: "docname", index: "docname", width: 130, label: "文档名称", sortable: true, align: "left",},
			{name: "doccode", index: "doccode", width: 90, label: "文档编号", sortable: true, align: "left"},
			{name: "docversion", index: "docversion", width: 70, label: "版本号", sortable: true, align: "left"},
			{name: "foldername", index: "foldername", width: 60, label: "目录", sortable: true, align: "left",},
			{name: "attpks", index: "attpks", width: 200, label: "附件", sortable: true, align: "left",
				formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.docname){
          				return '';
          			}
        			return formatStr(rowObject);
    			},
    		},
			{name: "filedczydescr", index: "filedczydescr", width: 60, label: "归档人", sortable: true, align: "left"},
			{name: "fileddate", index: "fileddate", width: 80, label: "归档时间", sortable: true, align: "left", formatter:formatDate},
			{name: "isforregulardescr", index: "isforregulardescr", width: 70, label: "员工可看", sortable: true, align: "left"},
			{name: "enabledate", index: "enabledate", width: 75, label: "生效时间", sortable: true, align: "left",formatter:formatDate},
			{name: "expireddate", index: "expireddate", width: 75, label: "失效时间", sortable: true, align: "left",formatter:formatDate},
			{name: "drawupczydescr", index: "drawupczydescr", width: 60, label: "拟制人", sortable: true, align: "left",},
			{name: "drawupdate", index: "drawupdate", width: 75, label: "拟制时间", sortable: true, align: "left",formatter:formatDate},
			{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "审核人", sortable: true, align: "left",},
			{name: "confirmdate", index: "confirmdate", width: 75, label: "审核时间", sortable: true, align: "left",formatter:formatDate},
			{name: "approveczydescr", index: "approveczydescr", width: 60, label: "批准人", sortable: true, align: "left"},
			{name: "approvedate", index: "approvedate", width: 75, label: "批准时间", sortable: true, align: "left",formatter:formatDate},
			{name: "isneednoticedescr", index: "isneednoticedescr", width: 68, label: "消息通告", sortable: true, align: "left"},
			{name: "downloadcnt", index: "downloadcnt", width: 83, label: "下载次数", sortable: true, align: "right"},
		],
	});
});
	
function changeDateName(){
	if($.trim($("#dateType").val()) == ""){
		$("#dateName").html("最后修改时间从");		
	} else {
		$("#dateName").html($("#dateType option:selected").text()+"从");
	}
}

function formatStr(rowObject){
	var attpks;
	var formatStr = ""
	if(rowObject && rowObject.attpks && rowObject.attpks != ""){
		attpks = rowObject.attpks.split(",");
		for(var i = 0; i < attpks.length; i++){
			if(formatStr == ""){
				formatStr = "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"downloadFile('"
					+attpks[i]+"','"+rowObject.pk+"')\">"+attpks[i]+"</a>";
			} else {
				formatStr += ",<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"downloadFile('"
					+attpks[i]+"','"+rowObject.pk+"')\">"+attpks[i]+"</a>";
			}
		}
	}
	return formatStr;
}

function downloadFile(docName, pk){
	window.open("${ctx}/admin/doc/download?attName="+ docName +"&docPK="+pk);
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form" hidden="true">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="status" name="status" value="2"/>
				<input type="hidden" id="folderPK" name="folderPK" value="7"/>
				<input type="hidden" id="Subdirectory" name="Subdirectory" value="1"/>
				<input type="hidden" id="pageOrderBy" name="pageOrderBy" value="fileddate"/>
				<input type="hidden" id="docCode" name="docCode" value="${doc.docCode }"/>
				<ul class="ul-form">
						<li>
							<label>查询条件</label>
							<input type="text" id="queryCondition" name="queryCondition" placeholder="名称/编号/关键字"/>
						</li>
						<li>
							<label>时间类型</label>
							<select id="dateType" name="dateType" onchange="changeDateName()">
								<option value="enable" selected>生效时间</option>
								<option value="expired">失效时间</option>
								<option value="draw">拟制时间</option>
								<option value="confirm">审核时间</option>
								<option value="approve">批准时间</option>
								<option value="create">创建时间</option>
								<option value="filed">归档时间</option>
							</select>
						</li>
						<li>
							<label id="dateName">生效时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
