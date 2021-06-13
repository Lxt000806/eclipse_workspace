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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工伤缴费参数管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				  <input type="hidden" id="expired" name="expired"/>
				<ul class="ul-form">
					<li>
						<label>签约公司</label>
						<house:dict id="conSignCmp" dictCode=""
							sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
							sqlLableKey="descr" sqlValueKey="code" />
					</li>
					<li>
					 	<input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
					 	<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="INJURYINSURPARAM_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="INJURYINSURPARAM_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="INJURYINSURPARAM_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="INJURYINSURPARAM_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>输出到Excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/injuryInsurParam/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
		 		{name: "consigncmp", index: "consigncmp", width: 50, label: "consigncmp", sortable: true, align: "left",hidden:true},
                {name: "consigncmpdescr", index: "consigncmpdescr", width: 80, label: "公司", sortable: true, align: "left",  },
                {name: "injuryinsurbasemin", index: "injuryinsurbasemin", width: 120, label: "工伤保险最低基数", sortable: true, align: "right",  },
                {name: "injuryinsurrate", index: "injuryinsurrate", width: 95, label: "工伤保险费率", sortable: true, align: "right",  },
                {name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "工伤缴费参数管理——新增",
			url : "${ctx}/admin/injuryInsurParam/goSave",
			height : 280,
			width : 450,
			postData:{
				dialogId:"save"
			},
			returnFun : goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"工伤缴费参数管理——编辑",
			url:"${ctx}/admin/injuryInsurParam/goUpdate",
			postData:{
				id:ret.consigncmp,
				dialogId:"update"
			},
			height : 280,
			width : 450,
			returnFun:goto_query
		});
	});

});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"工伤缴费参数管理——查看",
		url:"${ctx}/admin/injuryInsurParam/goView",
		postData:{
			id:ret.consigncmp,
			dialogId:"view"
		},
		height : 280,
		width : 450,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/injuryInsurParam/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
} 

</script>
</body>
</html>
