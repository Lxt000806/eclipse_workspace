<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>基础项目</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/baseItem/doExcel";
		doExcelAll(url);
	}
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
	
		$("#designMan").openComponent_employee();	
		$("#builderCode").openComponent_builder();
		var postData = $("#page_form").jsonForm();
		var gridOption ={
			url:"${ctx}/admin/baseItem/goListJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "code", index: "code", width: 90, label: "基装项目编号", sortable: true, align: "right"},
				{name: "descr", index: "descr", width: 220, label: "基装项目名称", sortable: true, align: "left"},
				{name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
				{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 120, label: "基装类型1", sortable: true, align: "left"},
				{name: "baseitemtype2descr", index: "baseitemtype2descr", width: 120, label: "基础类型2", sortable: true, align: "left"},
				{name: "categorydescr", index: "categorydescr", width: 100, label: "项目分类", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 70, label: "成本", sortable: true, align: "right"},
				{name: "marketprice", index: "marketprice", width: 70, label: "市场价", sortable: true, align: "right"},
				{name: "offerpri", index: "offerpri", width: 74, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 75, label: "材料单价", sortable: true, align: "right"},
				{name: "projectprice", index: "projectprice", width: 113, label: "项目经理结算价", sortable: true, align: "right"},
				{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right"},
				{name: "remark", index: "remark", width: 170, label: "描述", sortable: true, align: "left"},
				{name: "prjtypedescr", index: "prjtypedescr", width: 89, label: "施工类型", sortable: true, align: "left"},
				{name: "isfixpricedescr", index: "isfixpricedescr", width: 94, label: "是否固定价", sortable: true, align: "left"},
				{name: "perfper", index: "perfper", width: 94, label: "业绩比例", sortable: true, align: "right"},
				{name: "prjctrltypedescr", index: "prjctrltypedescr", width: 95, label: "发包方式", sortable: true, align: "left"},
				{name: "offerctrl", index: "offerctrl", width: 93, label: "人工发包", sortable: true, align: "right"},
				{name: "materialctrl", index: "materialctrl", width: 92, label: "材料发包", sortable: true, align: "right"},
				{name: "iscalmangefeedescr", index: "iscalmangefeedescr", width: 105, label: "是否计算管理费", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 90, label: "客户类型", sortable: true, align: "left"},
				{name: "allowpricerisedescr", index: "allowpricerisedescr", width: 105, label: "允许价格上浮", sortable: true, align: "left"},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 105, label: "套餐外项目", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"},
			],
			ondblClickRow: function(){
				view();
			}
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"基础项目——新增",
				url:"${ctx}/admin/baseItem/goSave",
				height:730,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#copy").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("Copy",{
				title:"基础项目——复制",
				url:"${ctx}/admin/baseItem/goCopy",
				postData:{code:ret.code},
				height:730,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#update").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("Update",{
				title:"基础项目——编辑",
				url:"${ctx}/admin/baseItem/goUpdate",
				postData:{code:ret.code},
				height:730,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#updatePercentage").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("UpdatePercentage",{
				title:"基础项目——修改业绩比例",
				url:"${ctx}/admin/baseItem/goUpdatePercentage",
				postData:{code:ret.code},
				height:340,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#updatePrice").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("UpdatePrice",{
				title:"基础项目——修改价格",
				url:"${ctx}/admin/baseItem/goUpdatePrice",
				postData:{code:ret.code},
				height:540,
				width:830,
				returnFun:goto_query
			});
		});
		$("#view").on("click",function(){
			var ret = selectDataTableRow();

			Global.Dialog.showDialog("View",{
				title:"基础项目——查看",
				url:"${ctx}/admin/baseItem/goView",
				postData:{code:ret.code},
				height:730,
				width:750,
				returnFun:goto_query
			});
		});
	});
	
	function view(){
		var ret = selectDataTableRow();

		Global.Dialog.showDialog("View",{
			title:"基础项目——查看",
			url:"${ctx}/admin/baseItem/goView",
			postData:{code:ret.code},
			height:730,
			width:750,
			returnFun:goto_query
		});
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired"  name="expired" value="${baseItem.expired }"/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>基础项目编号</label>
								<input type="text" id="code" name="code"/>
							</li>
							<li>
								<label>基础项目名称</label>
								<input type="text" id="descr" name="descr"/>
							</li>
							<li class="form-validate">
								<label>是否业绩折扣</label>
								<house:xtdm id="isPrefpre" dictCode="YESNO"  style="width:160px;"></house:xtdm>
							</li>
							<li class="form-validate">
								<label>基装类型1</label>
								<select id="baseItemType1" name="baseItemType1"></select>
							</li>
							</div>
							<div class="validate-group row">
							<li class="form-validate">
								<label>基装类型2</label>
								<select id="baseItemType2" name="baseItemType2"></select>
							</li>
							<li>
								<label>描述</label>
								<input type="text" id="remark" name="remark"/>
							</li>
							<li>
								<label>助记码</label>
								<input type="text" id="remCode" name="remCode"/>
							</li>	
							<li class="form-validate">
								<label>施工类型</label>
								<house:xtdm id="prjType" dictCode="BASEITEMPRJTYPE"  style="width:160px;"></house:xtdm>
							</li>
							</div>
							<div class="validate-group row">
							<li class="search-group" >
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${baseItem.expired }" onclick="hideExpired(this)" 
								 ${baseItem!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
							</div>	
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
  			<div class="btn-group-sm">
				<house:authorize authCode="JCXM_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="JCXM_COPY">
					<button type="button" class="btn btn-system" id="copy">
						<span>复制</span>
					</button>
				</house:authorize>
				<house:authorize authCode="JCXM_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="JCXM_UPDATEPRICE">
					<button type="button" class="btn btn-system" id="updatePrice">
						<span>修改价格</span>
					</button>
				</house:authorize>
				<house:authorize authCode="JCXM_UPDATEPERCENTAGE">
					<button type="button" class="btn btn-system" id="updatePercentage">
						<span>修改业绩比例</span>
					</button>
				</house:authorize>
				<house:authorize authCode="JCXM_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
					<span>导出excel</span>
				</button>
			</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
