<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>项目名称列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("builderAdd",{
		  title:"添加项目名称",
		  url:"${ctx}/admin/builder/goSave",
		  height: 600,
		  width:750,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("builderUpdate",{
		  title:"修改项目名称",
		  url:"${ctx}/admin/builder/goUpdate?id="+ret.code,
		  height:600,
		  width:750,
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
      Global.Dialog.showDialog("builderCopy",{
		  title:"复制项目名称",
		  url:"${ctx}/admin/builder/goCopy?id="+ret.code,
		  height:600,
		  width:750,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function infoUpdate(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("infoUpdate",{
		  title:"工程信息编辑",
		  url:"${ctx}/admin/builder/goInfoUpdate?id="+ret.code,
		  height:435,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function regionUpdate(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("regionUpdate",{
		  title:"配送信息编辑",
		  url:"${ctx}/admin/builder/goRegionUpdate?id="+ret.code,
		  height:280,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function houseManage(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("houseManage",{
		  title:"交房批次管理",
		  url:"${ctx}/admin/builder/goHouseManage?id="+ret.code,
		  height:720,
		  width:870,
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
      Global.Dialog.showDialog("builderView",{
		  title:"查看项目名称",
		  url:"${ctx}/admin/builder/goDetail?id="+ret.code,
		  height:600,
		  width:750
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/builder/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/builder/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		$("#page_form").setTable();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/builder/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
			  	{name: "code", index: "code", width: 70, label: "项目编码", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 100, label: "项目名称", sortable: true, align: "left"},
				{name: "alias", index: "alias", width: 100, label: "项目别名", sortable: true, align: "left"},
				{name: "groupcodedescr", index: "groupcodedescr", width: 125, label: "项目大类", sortable: true, align: "left"},
				{name: "addresstypedescr", index: "addresstypedescr", width: 140, label: "地址类型", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
				{name: "dectstatusdescr", index: "dectstatusdescr", width: 80, label: "装修状况", sortable: true, align: "left"},
				{name: "adress", index: "adress", width: 110, label: "楼盘地址", sortable: true, align: "left"},
				{name: "housenum", index: "housenum", width: 57, label: "总户数", sortable: true, align: "right"},
				{name: "housetypedescr", index: "housetypedescr", width: 96, label: "楼盘性质", sortable: true, align: "left"},
				{name: "majorarea", index: "majorarea", width: 82, label: "主力户型", sortable: true, align: "left"},
				{name: "referprice", index: "referprice", width: 80, label: "参考价", sortable: true, align: "left"},
				{name: "property", index: "property", width: 100, label: "物业", sortable: true, align: "left"},
				{name: "developer", index: "developer", width: 105, label: "开发商", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "区域", sortable: true, align: "left"},
				{name: "region2descr", index: "region2descr", width: 80, label: "二级区域", sortable: true, align: "left"},
				{name: "delivdate", index: "delivdate", width: 80, label: "交房时间", sortable: true, align: "left", formatter: formatDate},
				{name: "buildertypedescr", index: "buildertypedescr", width: 73, label: "项目类型", sortable: true, align: "left"},
				{name: "longitude", index: "longitude", width: 80, label: "经度", sortable: true, align: "right"},
				{name: "latitude", index: "latitude", width: 80, label: "纬度", sortable: true, align: "right"},
				{name: "offset", index: "offset", width: 85, label: "签到偏移量", sortable: true, align: "right"},
				{name: "isprjspcdescr", index: "isprjspcdescr", width: 95, label: "是否工程专盘", sortable: true, align: "left"},
				{name: "prjleaderdescr", index: "prjleaderdescr", width: 95, label: "工程专盘队长", sortable: true, align: "left"},
				{name: "sendregiondescr", index: "sendregiondescr", width: 170, label: "配送区域", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "修改操作", sortable: true, align: "left"}
            ]
		});
		$("#prjLeader").openComponent_employee();
});
function change(e) {
	if ($(e).is(":checked")) $(e).val("1");
	else $(e).val("0");
}
</script>
<style type="text/css">
.panelBar{background: url('')}
</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form">
				 <input type="hidden"
					name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>项目名称编号</label> <input type="text" id="code"
						name="code" style="width:160px;" value="${builder.code}" />
					</li>
					<li><label>项目名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" value="${builder.descr}" />
					</li>
					<li><label>楼盘地址</label> <input type="text" id="adress"
						name="adress" style="width:160px;" value="${builder.adress}" />
					</li>
					<li><label>交房时间从</label> <input type="text" id="delivDateFrom"
						name="delivDateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${builder.delivDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="delivDateTo"
						name="delivDateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${builder.delivDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>是否工程专盘</label> <house:xtdm
							id="isPrjSpc" dictCode="YESNO" value="${builder.isPrjSpc }"
							onchange="changePrjSpc()"></house:xtdm></li>
					<li><label>工程专盘队长</label> <input
						type="text" id="prjLeader" name="prjLeader" style="width:160px;"
						value="${builder.prjLeader}" />
					</li>
					<li>
       					<input type="checkbox" id="onlyShowSendRegionNull" name="onlyShowSendRegionNull"
              				   value="0" onclick="change(this)"/>只显示配送区域为空
       				</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${builder.expired}"
						onclick="hideExpired(this)" ${builder.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="panelBar">
				<ul>
					<house:authorize authCode="BUILDER_ADD">
						<li><a href="javascript:void(0)" class="a1" onclick="add()">
								<span>添加</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_UPDATE">
						<li><a href="javascript:void(0)" class="a1"
							onclick="update()"> <span>编辑</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_COPY">
						<li><a href="javascript:void(0)" class="a1" onclick="copy()">
								<span>复制</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_HOUSEMANAGE">
						<li><a href="javascript:void(0)" class="a1"
							onclick="houseManage()"> <span>交房批次管理</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_INFOUPDATE">
						<li><a href="javascript:void(0)" class="a1"
							onclick="infoUpdate()"> <span>工程信息编辑</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_UPDATESENDREGION">
						<li><a href="javascript:void(0)" class="a1"
							onclick="regionUpdate()"> <span>配送区域编辑</span> </a>
						</li>
					</house:authorize>
					<house:authorize authCode="BUILDER_VIEW">
						<li><a href="javascript:void(0)" class="a1" onclick="view()">
								<span>查看</span> </a>
						</li>
					</house:authorize>
					<li><a href="javascript:void(0)" class="a3"
						onclick="doExcel()" title="导出检索条件数据"> <span>导出excel</span> </a>
					</li>
					<li class="line"></li>
				</ul>
				<div class="clear_float"></div>
			</div>
			<!--panelBar end-->
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


