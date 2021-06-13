<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>设计费标准设置</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>  
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${designFeeSd.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>设计师级别</label> 
						<house:dict id="position" dictCode="" sql="select rtrim(Code)+' '+desc2 fd,Code from tPosition where type='4' order by Code" 
							sqlValueKey="Code" sqlLableKey="fd"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${designFeeSd.expired}" 
							onclick="hideExpired(this)" ${designFeeSd.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" id="clear" 
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="add">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
				</button>
				<house:authorize authCode="DESIGNFEESD_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript" defer>
$(function(){
	var postData = $("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/designFeeSd/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 68,
		sortable: true,
		styleUI: "Bootstrap",
		colModel : [
			{name: "pk", index: "pk", width: 60, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "position", index: "position", width: 60, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "positiondescr", index: "positiondescr", width: 80, label: "设计师级别", sortable: true, align: "left"},
			{name: "designfee", index: "designfee", width: 80, label: "收费标准", sortable: true, align: "left"},
			{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right"},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后修改人员", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
        ],
        ondblClickRow: function(){
			view();
		},
	});

	$("#add").on("click",function(){
		Global.Dialog.showDialog("add",{
			title:"设计费标准设置--新增",
			url:"${ctx}/admin/designFeeSd/goAdd",
			postData:{m_umState:"A"},
			width:535,
			height:266,
			returnFun: goto_query
		});
	});

	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
	  			content:"请选择一条记录",
	  		});
	  		return;
		}
		Global.Dialog.showDialog("update",{
			title:"设计费标准设置--编辑",
			url:"${ctx}/admin/designFeeSd/goAdd",
			postData:{
				m_umState:"M",
				pk:ret.pk,
				position:$.trim(ret.position),
				designFee:ret.designfee,
				custType:ret.custtype,
				dispSeq:ret.dispseq,
			},
			width:535,
			height:266,
			returnFun: goto_query
		});
	});

	$("#delete").on("click",function(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({content:"请选择一条记录",});
			return;
		}
		var url = "${ctx}/admin/designFeeSd/doDelete";
		beforeDel(url,"pk","删除该设计费标准");
		returnFun: goto_query;
		return true;
	});

});

function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({
			content:"请选择一条记录",
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"设计费标准设置--查看",
		url:"${ctx}/admin/designFeeSd/goView",
		postData:{
			m_umState:"V",
			pk:ret.pk,
			position:$.trim(ret.position),
			designFee:ret.designfee,
			custType:ret.custtype,
			dispSeq:ret.dispseq,
		},
		width:535,
		height:266,
		// returnFun: goto_query
	});
}

//导出EXCEL
function doExcel(){
	doExcelAll("${ctx}/admin/designFeeSd/doExcel");
}

</script>
</body>
</html>
