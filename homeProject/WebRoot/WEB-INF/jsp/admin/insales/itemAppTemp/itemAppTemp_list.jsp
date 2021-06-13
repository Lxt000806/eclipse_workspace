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
	<title>领料申请模板</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript" defer>/*defer 做到一边下载JS(还是只能同时下载两个JS)，一边解析HTML。*/
function save(){
	Global.Dialog.showDialog("save",{
		title:"领料申请模板——增加",
		url:"${ctx}/admin/itemAppTemp/goSave",
		height: 315,
		width:425,
		returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      	Global.Dialog.showDialog("update",{
			title:"领料申请模板——编辑",
			url:"${ctx}/admin/itemAppTemp/goUpdate",
			postData:{no:ret.no},
			height:350,
			width:425,
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
      	Global.Dialog.showDialog("view",{
			title:"领料申请模板——查看",
			url:"${ctx}/admin/itemAppTemp/goView",
			postData:{no:ret.no,m_umState:"V"},
			height:350,
			width:425
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
// 面积区间管理
function area(){
	var ret = selectDataTableRow();
    if (ret) {
      	Global.Dialog.showDialog("area",{
			title:"领料申请模板——面积区间管理",
			url:"${ctx}/admin/itemAppTemp/goArea",
			postData:{no:ret.no,m_umState:"V"},
			height:700,
			width:800
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//导出EXCEL
function doExcel(){
	doExcelAll("${ctx}/admin/itemAppTemp/doExcel");
}

var postData = $("#page_form").jsonForm();
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemAppTemp/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: "Bootstrap",
		colModel : [
		  {name : "no",index : "no",width : 80,label:"模板编号",sortable : true,align : "left",key : true},
	      {name : "itemtype1descr",index : "itemtype1descr",width : 80,label:"材料类型1",sortable : true,align : "left"},
	      {name : "remarks",index : "remarks",width : 200,label:"备注",sortable : true,align : "left"},
	      {name : "worktype12descr",index : "worktype12descr",width : 90,label:"工种分类1-2",sortable : true,align : "left"},
	      {name : "lastupdate",index : "lastupdate",width : 120,label:"最后修改时间",sortable : true,align : "left", formatter:formatTime},
	      {name : "lastupdatedby",index : "lastupdatedby",width : 90,label:"修改人",sortable : true,align : "left"},
	      {name : "expired",index : "expired",width : 70,label:"过期标志",sortable : true,align : "left"},
	      {name : "actionlog",index : "actionlog",width : 70,label:"修改操作",sortable : true,align : "left"}
        ],
        ondblClickRow: function(){
			view();
		},
	});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${itemAppTemp.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>模板编号</label> 
						<input type="text" id="no" name="no" style="width:160px;"/>
					</li>
					<li>
						<label>材料类型1</label> 
						<house:dict id="itemType1" dictCode="" sql="select Code,Code+' '+Descr cd from tItemType1 where Expired != 'T'" 
							sqlValueKey="Code" sqlLableKey="cd"/>
					</li>
					<li>
						<label>备注</label> 
						<input type="text" id="remarks" name="remarks" style="width:160px;"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${itemAppTemp.expired}" 
							onclick="hideExpired(this)" ${itemAppTemp.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save" onclick="save()">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update" onclick="update()">
					<span>编辑</span>
				</button>
				<button type="button" class="btn btn-system" id="area" onclick="area()">
					<span>面积区间管理</span>
				</button>
				<house:authorize authCode="ITEMAPPTEMP_VIEW">
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
</html>
