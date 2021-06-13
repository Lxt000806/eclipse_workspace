<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>领料审核规则管理--列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
});
//初始化领料审核规则管理
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemAppConfRule/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 81, label: "材料类型1", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
			{name: "paytypedescr", index: "paytypedescr", width: 100, label: "客户付款类型", sortable: true, align: "left"},
			{name: "paynum", index: "paynum", width: 90, label: "付款期数", sortable: true, align: "left"},
			{name: "payper", index: "payper", width: 90, label: "付款比例", sortable: true, align: "right"},
			{name: "prior", index: "prior", width: 90, label: "优先级", sortable: true, align: "right"},
			{name: "diffamount", index: "diffamount", width: 90, label: "允许差额", sortable: true, align: "right"},
			{name: "itemcost", index: "itemcost", width: 81, label: "材料成本", sortable: true, align: "right"},
			{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 152, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 90, label: "操作日志", sortable: true, align: "left"}
		 ],
		 ondblClickRow:function(){
		 	view();
		  }
	});
});
//新增
function add(){
	Global.Dialog.showDialog("itemAppConfRule",{
		title:"领料审核规则管理--新增",
		url:"${ctx}/admin/itemAppConfRule/goSave",
		width:1100,
		height:700,
		returnFun:goto_query
	});
}
//编辑 
function update(){
	var ret = selectDataTableRow();
	if (ret){
			Global.Dialog.showDialog("itemAppConfRule",{
				url:"${ctx}/admin/itemAppConfRule/goUpdate?id="+ret.no,
				title:"领料审核规则管理--编辑 ",
				width:1100,
				height:700,
				returnFun:goto_query	
			});	
	}else{
	 	art.dialog({
	 		content:"请选择一条记录"
	 	});
	}
}
function view(){
	var ret = selectDataTableRow();
	Global.Dialog.showDialog("itemAppConfRule",{
		url:"${ctx}/admin/itemAppConfRule/goView?id="+ret.no,
		title:"领料审核规则管理--查看",
		height:700,
		width:1100,
	});
}
//导出execel
function doExcel() {
	var url = "${ctx}/admin/itemAppConfRule/doExcel";
	doExcelAll(url);
} 	

//复制
function copy(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("itemAppConfRule",{
			url:"${ctx}/admin/itemAppConfRule/goCopy?id="+ret.no,
			title:"领料审核规则管理--复制 ",
			width:1100,
			height:700,
			returnFun:goto_query	
		});	
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
</script>
</head>    
<body>
	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="expired" id="expired">
				<ul class="ul-form">
					<li>
						<label>批次号</label>
						<input type="text" id="no" name="no" />
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
				   	<li>
						<label>客户类型</label>
						<house:dict id="custType" dictCode="" sql="select convert(int,code) code,code+' '+desc1 fd from tCusttype a  order by a.code" 
											 sqlValueKey="code" sqlLableKey="fd"></house:dict>	
					</li>
					<li>
						<label>付款类型</label>
						<house:xtdm id="payType" dictCode="TIMEPAYTYPE"></house:xtdm>
					</li>
					<li class="search-group-shrink">
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" checked="checked"/>   
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
					<house:authorize authCode="ITEMAPPCONFRULE_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMAPPCONFRULE_COPY">
						<button type="button" class="btn btn-system " onclick="copy()">
							<span>复制</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ITEMAPPCONFRULE_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ItemAppConfRule_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
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




