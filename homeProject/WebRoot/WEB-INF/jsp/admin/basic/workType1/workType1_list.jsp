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
	<title>工种分类1管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${workType1.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>工种编码</label> 
						<input type="text" id="code" name="code" style="width:160px;" value=""/>
					</li>
					<li>
						<label>工种名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${workType1.expired}" 
							onclick="hideExpired(this)" ${workType1.expired!='T' ?'checked':'' }/>
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
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="copy">
					<span>复制</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="WORKTYPE1_VIEW">
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
</body>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/workType1/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name : "Code", index:"Code", width:100, label:"工种编号", sortable:true, align:"left"},
			{name : "Descr", index:"Descr", width:100, label:"工种名称", sortable:true, align:"left"},
			{name : "DispSeq", index:"DispSeq", width:70, label:"显示顺序", sortable:true, align:"right"},
			{name : "ItemAppCtrl", index:"ItemAppCtrl", width:100, label:"领料控制比例", sortable:true, align:"right"},
    		{name : "LastUpdate",index : "LastUpdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "LastUpdatedBy",index : "LastUpdatedBy",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "Expired",index : "Expired",width : 70,label:"是否过期",sortable : true,align : "left"},
    		{name : "ActionLog",index : "ActionLog",width : 50,label:"操作",sortable : true,align : "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "工种分类1——新增",
			url : "${ctx}/admin/workType1/goSave",
			postData:{
				dispSeq:0,
				itemAppCtrl: 1,
				m_umState:"A",
				expired:false
			},
			height : 350,
			width : 445,
			returnFun : goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret=selectDataTableRow();
		console.log(ret);
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("copy",{
			title:"工种分类1——复制",
			url:"${ctx}/admin/workType1/goSave",
			postData:{
				code:ret.Code.trim(),
				descr:ret.Descr,
				dispSeq:ret.DispSeq,
				expired:ret.Expired,
				itemAppCtrl: ret.ItemAppCtrl,
				m_umState:"C"
			},
			height:350,
			width:445,
			returnFun:goto_query
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
			title:"工种分类1——编辑",
			url:"${ctx}/admin/workType1/goSave",
			postData:{
				code:ret.Code.trim(),
				descr:ret.Descr,
				dispSeq:ret.DispSeq,
				expired:ret.Expired,
				itemAppCtrl: ret.ItemAppCtrl,
				m_umState:"M"
			},
			height:350,
			width:445,
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
		title:"工种分类1——查看",
		url:"${ctx}/admin/workType1/goSave",
		postData:{
			code:ret.Code.trim(),
			descr:ret.Descr,
			dispSeq:ret.DispSeq,
			expired:ret.Expired,
			itemAppCtrl: ret.ItemAppCtrl,
			m_umState:"V"
		},
		height:350,
		width:445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/workType1/doExcel";
	doExcelAll(url);
}
</script>
</html>
