<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>基础结算项目管理--列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
});
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url : "${ctx}/admin/baseCheckItem/goJqGrid",
		height : $(document).height()- $("#content-list").offset().top - 70,
		styleUI : "Bootstrap",
		colModel : [
			{name: "code", index: "code", width: 70, label: "编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 200, label: "项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 100, label: "基装类型1", sortable: true, align: "left"},
			{name: "baseitemtype2descr", index: "baseitemtype2descr", width: 100, label: "基装类型2", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 100, label: "工种分类12", sortable: true, align: "left"},
			{name: "offerpri", index: "offerpri", width: 120, label: "人工成本单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 100, label: "材料成本单价", sortable: true, align: "right"},
			{name: "prjofferpri", index: "prjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right"},
			{name: "prjmaterial", index: "prjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right"},
			{name: "uomdescr", index: "uomdescr", width: 70, label: "单位", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 100, label: "描述", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 100, label: "类型", sortable: true, align: "left"},
			{name: "isfixitem", index: "isfixitem", width: 100, label: "是否定责项目", sortable: true, align: "left", hidden: true},
			{name: "isfixitemdescr", index: "isfixitemdescr", width: 100, label: "是否定责项目", sortable: true, align: "left"},
			{name: "workerclassifydescr", index: "workerclassifydescr", width: 120, label: "工人分类", sortable: true, align: "left",},
			{name: "issubsidyitemdescr", index: "issubsidyitemdescr", width: 120, label: "是否发包补贴项目", sortable: true, align: "left",},
			{name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
		]
	  });
});
function add(){
	Global.Dialog.showDialog("baseCheckItem",{
		title:"基础结算项目管理--添加",
		url:"${ctx}/admin/baseCheckItem/goSave",
		width:800,
		height:500,
		returnFun:goto_query
	});
}
function copy() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseCheckItem", {
			title : "基础结算项目管理--复制",
			url : "${ctx}/admin/baseCheckItem/goCopy?id="+ ret.code,
			height : 500,
			width : 800,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
}
function update() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseCheckItem", {
			title : "基础结算项目管理--编辑",
			url : "${ctx}/admin/baseCheckItem/goUpdate?id="+ ret.code,
			height : 500,
			width : 800,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
	}	
function view() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("baseCheckItem", {
	    	title : "基础结算项目管理--查看",
			url : "${ctx}/admin/baseCheckItem/goDetail?id=" + ret.code,
		    height : 500,
			width : 800,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
	}	

function doExcel() {
	var url = "${ctx}/admin/baseCheckItem/doExcel";
	doExcelAll(url);
} 	
</script>
</head>
<body>
  	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired"  name="expired" value="F"/>
				<ul class="ul-form">
					<li><label>编号</label> <input type="text" id="code" name="code" /></li>
				    <li><label>项目名称</label> <input type="text" id="descr" name="descr" /></li>
					<li><label>基装类型1</label> <select type="text" id="baseItemType1" name="baseItemType1" ></select></li>
					<li>
						<label>基装类型2</label>
						<select type="text" id="baseItemType2" name="baseItemType2"></select>
					</li>
					<li>
						<label>工种分类12</label>
						<house:dict id="workType12" dictCode="" sql="select code,code+' '+ descr descr from tWorkType12"
						sqlLableKey="descr" sqlValueKey="code"></house:dict>
					</li>	
					<li><label>描述</label> <input type="text" id="remark" name="remark" /></li>		
				    <li class="search-group-shrink">
				    	<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" 
						     ${baseCheckItem.expired!='T'?'checked':''} />   
						 <P>隐藏过期</p>
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="BASECHECKITEM_ADD">
						<button type="button" class="btn btn-system " onclick="add()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="BASECHECKITEM_COPY">
					<button type="button" class="btn btn-system " onclick="copy()">
						<span>复制</span>
					</button>
					</house:authorize>
					<house:authorize authCode="BASECHECKITEM_UPDATE">
					<button type="button" class="btn btn-system " onclick="update()">
						<span>编辑</span>
					</button>
					</house:authorize>
					<house:authorize authCode="BASECHECKITEM_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">
						<span>查看</span>
					</button>
					</house:authorize>
					<house:authorize authCode="BASECHECKITEM_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
					</house:authorize>
				</div>
			</div>
		</div>		
  		<div id="content-list">
    		<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
  </body>
</html>
 
