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
	<title>品牌管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript" defer>
	function add(){
		Global.Dialog.showDialog("add",{
			title:"品牌管理--增加",
			url:"${ctx}/admin/brand/goAdd",
			postData:{
				m_umState:"A",
			},
			width:535,
			height:300,
			returnFun: goto_query
		});
	}

	function copy(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		Global.Dialog.showDialog("copy",{
			title:"品牌管理--复制",
			url:"${ctx}/admin/brand/goAdd",
			postData:{
				m_umState:"C",
				descr:ret.descr,
				itemType1:$.trim(ret.itemtype1),
				itemType2:$.trim(ret.itemtype2),
				expired:ret.expired,
			},
			width:535,
			height:300,
			returnFun: goto_query
		});
	}

	function update(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"品牌管理--编辑",
			url:"${ctx}/admin/brand/goAdd",
			postData:{
				m_umState:"M",
				code:ret.code,
				descr:ret.descr,
				itemType1:$.trim(ret.itemtype1),
				itemType2:$.trim(ret.itemtype2),
				expired:ret.expired,
			},
			width:535,
			height:300,
			returnFun: goto_query
		});
	}

	function view(){
		var ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"品牌管理--查看",
			url:"${ctx}/admin/brand/goAdd",
			postData:{
				m_umState:"V",
				code:ret.code,
				descr:ret.descr,
				itemType1:$.trim(ret.itemtype1),
				itemType2:$.trim(ret.itemtype2),
			},
			width:535,
			height:300,
			returnFun: goto_query
		});
	}

	function doExcel(){
		doExcelAll("${ctx}/admin/brand/doExcel");
	}
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");

		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/brand/goBrandJqGrid",
			height:$(document).height()-$("#content-list").offset().top-68,
			colModel : [
				{name: "code", index: "code", width: 102, label: "品牌编码", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 126, label: "名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 117, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype1", index: "itemtype1", width: 117, label: "材料类型1", sortable: true, align: "left", hidden: true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 119, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemtype2", index: "itemtype2", width: 119, label: "材料类型2", sortable: true, align: "left", hidden: true},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 115, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 102, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 98, label: "操作", sortable: true, align: "left"},
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
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label for="code">品牌编码</label>
						<input type="text" id="code" name="code" />
					</li>
					<li>
						<label for="descr">名称</label>
						<input type="text" id="descr" name="descr" />
					</li>
					<li>
						<label for="itemType1">材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label for="itemType2">材料类型2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${brand.expired }" 
							onclick="hideExpired(this)" ${brand.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="add" onclick="add()">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="copy" onclick="copy()">
					<span>复制</span>
				</button>
				<button type="button" class="btn btn-system" id="update" onclick="update()">
					<span>编辑</span>
				</button>
				<house:authorize authCode="BRAND_VIEW">
                	<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
                </house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel();">
					<span>输出到Excel</span>
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
