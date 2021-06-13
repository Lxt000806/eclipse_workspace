<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>FixArea列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("fixAreaAdd",{
		  title:"新增装修区域",
		  url:"${ctx}/admin/fixArea/goSave",
		  height:500,
		  width:400,
		  returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("fixAreaUpdate",{
		  title:"修改装修区域",
		  url:"${ctx}/admin/fixArea/goUpdate?id="+ret.PK,
		  height:500,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("fixAreaCopy",{
		  title:"复制装修区域",
		  url:"${ctx}/admin/fixArea/goCopy?id="+ret.PK,
		  height:500,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("fixAreaView",{
		  title:"查看装修区域",
		  url:"${ctx}/admin/fixArea/goDetail?id="+ret.PK,
		  height:500,
		  width:400,
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/fixArea/doDelete";
	beforeDel(url);
}

function doExcel(){
	var url = "${ctx}/admin/fixArea/doExcel"; 
	doExcelAll(url);
}
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/fixArea/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
/**初始化表格*/
$(function(){
		$("#custCode").openComponent_customer();
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			    {name : 'PK',index : 'PK',width : 100,label:'PK',sortable : true,align : "left", hidden: true},
				{name : 'Descr',index : 'Descr',width : 100,label:'区域名称',sortable : true,align : "left"},
				{name: 'CustCode', index: 'CustCode', width: 80, label: '客户编号', sortable: true, align: "left"},
				{name: 'custdescr', index: 'custdescr', width: 80, label: '客户名称', sortable: true, align: "left"},
				{name: 'address', index: 'address', width: 191, label: '楼盘', sortable: true, align: "left"},
				{name: 'preplanareapk', index: 'preplanareapk', width: 100, label: '空间pk', sortable: true, align: 'left', hidden: true},
			  	{name: 'preplanareadescr', index: 'preplanareadescr', width: 100, label: '空间名称', sortable: true, align: 'left'},	
		        {name : 'ItemType1',index : 'ItemType1',width : 100,label:'材料类型1',sortable : true,align : 'left', hidden: true},
		        {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : 'left'},
		        {name : 'IsService',index : 'IsService',width : 100,label:'服务性产品',sortable : true,align : 'left'},
		        {name : 'IsServiceDescr',index : 'IsServiceDescr',width : 100,label:'服务性产品',sortable : true,align : 'left', hidden: true},
            	{name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'最后更新时间',sortable : true,align : 'left', formatter: formatTime},
		        {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'最后更新人',sortable : true,align : "left"},
		        {name : 'Expired',index : 'Expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		        {name : 'ActionLog',index : 'ActionLog',width : 100,label:'操作日志',sortable : true,align : "left"},
		        {name : 'DispSeq',index : 'DispSeq',width : 100,label:'显示顺序',sortable : true,align : "left"},
            ]
		});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> 
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" />
					</li>
					<li><label>区域</label> <input type="text" id="descr" name="descr" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired" name="expired"
						onclick="hideExpired(this)" ${custLoan.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="FIXAREA_ADD">
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
				</house:authorize>
				<house:authorize authCode="FIXAREA_COPY">
					<button type="button" class="btn btn-system " onclick="copy()">复制</button>
				</house:authorize>
				<house:authorize authCode="FIXAREA_UPDATE">
					<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="FIXAREA_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="	FIXAREA_EXCEL">
					<button type="btnExcel" class="btn btn-system " onclick="doExcel()">输出到Excel</button>
				</house:authorize>
			
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>





 
