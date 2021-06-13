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
	<title>基础项目与结算项目映射管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${baseItemToCheckItem.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>基础项目</label> 
						<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;" value=""/>
					</li>
					<li>
						<label>基础结算项目</label> 
						<input type="text" id="baseCheckItemCode" name="baseCheckItemCode" style="width:160px;" value=""/>
					</li>
					<li>
						<label>映射算法</label> 
						<house:xtdm id="calType" dictCode="CKITEMCALTYPE" style="width:160px;" value=""/>
					</li>
					<li>
						<label>基装类型1</label> 
						<select id="baseItemType1" name="baseItemType1" style="width:160px;" value=""></select>
					</li>
					<li>
						<label>基装类型2</label> 
						<select id="baseItemType2" name="baseItemType2" style="width:160px;" value=""></select>
					</li>
					<li>
						<!-- <input type="checkbox" id="expired_show" name="expired_show" value="${baseItemToCheckItem.expired}" 
							onclick="hideExpired(this)" ${baseItemToCheckItem.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label> -->
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
				<house:authorize authCode="BASEITEMTOCHECKITEM_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="BASEITEMTOCHECKITEM_COPY">
					<button type="button" class="btn btn-system" id="copy">
						<span>复制</span>
					</button>
				</house:authorize>
				<house:authorize authCode="BASEITEMTOCHECKITEM_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="BASEITEMTOCHECKITEM_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="BASEITEMTOCHECKITEM_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="BASEITEMTOCHECKITEM_EXCEL">
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
	$("#baseItemCode").openComponent_baseItem();
	$("#baseCheckItemCode").openComponent_baseCheckItem();

	Global.LinkSelect.initSelect("${ctx}/admin/baseItem/baseItemType","baseItemType1","baseItemType2");

	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/baseItemToCheckItem/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"pk", index:"pk", width:60, label:"pk", sortable:true, align:"left",hidden:true},
			{name:"baseitemcode", index:"baseitemcode", width:120, label:"基础项目编号", sortable:true, align:"left",hidden:true},
			{name:"baseitemdescr", index:"baseitemdescr", width:130, label:"基础项目", sortable:true, align:"left"},
			{name:"basecheckitemcode", index:"basecheckitemcode", width:120, label:"基础结算项目编号", sortable:true, align:"left",hidden:true},
			{name:"basecheckitemdescr", index:"basecheckitemdescr", width:130, label:"基础结算项目", sortable:true, align:"left"},
			{name:"caltype", index:"caltype", width:80, label:"映射算法id", sortable:true, align:"left",hidden:true},
			{name:"caltypedescr", index:"caltypedescr", width:80, label:"映射算法", sortable:true, align:"left"},
			{name:"qty", index:"qty", width:60, label:"数量", sortable:true, align:"right"},
    		{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "基础项目与结算项目映射管理——新增",
			url : "${ctx}/admin/baseItemToCheckItem/goSave",
			height : 268,
			width : 445,
			returnFun : goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("copy",{
			title:"基础项目与结算项目映射管理——复制",
			url:"${ctx}/admin/baseItemToCheckItem/goCopy",
			postData:{
				m_umState:"C",
				pk:ret.pk,
				baseItemCode:$.trim(ret.baseitemcode),
				baseItemDescr:ret.baseitemdescr,
				baseCheckItemCode:$.trim(ret.basecheckitemcode),
				baseCheckItemDescr:ret.basecheckitemdescr,
				calType:$.trim(ret.caltype),
				qty:ret.qty,
			},
			height:268,
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
			title:"基础项目与结算项目映射管理——编辑",
			url:"${ctx}/admin/baseItemToCheckItem/goUpdate",
			postData:{
				m_umState:"M",
				pk:ret.pk,
				baseItemCode:$.trim(ret.baseitemcode),
				baseItemDescr:ret.baseitemdescr,
				baseCheckItemCode:$.trim(ret.basecheckitemcode),
				baseCheckItemDescr:ret.basecheckitemdescr,
				calType:$.trim(ret.caltype),
				qty:ret.qty,
			},
			height:268,
			width:445,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var url = "${ctx}/admin/baseItemToCheckItem/doDelete";
		beforeDel(url,"pk","删除该信息");
		returnFun: goto_query;
		return true;
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
		title:"基础项目与结算项目映射管理——查看",
		url:"${ctx}/admin/baseItemToCheckItem/goView",
		postData:{
			m_umState:"V",
			baseItemCode:$.trim(ret.baseitemcode),
			baseItemDescr:ret.baseitemdescr,
			baseCheckItemCode:$.trim(ret.basecheckitemcode),
			baseCheckItemDescr:ret.basecheckitemdescr,
			calType:$.trim(ret.caltype),
			qty:ret.qty,
		},
		height:268,
		width:445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/baseItemToCheckItem/doExcel";
	doExcelAll(url);
}
</script>
</body>
</html>
