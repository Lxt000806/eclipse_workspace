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
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>库位调整</title>
	<%@ include file="/commons/jsp/common.jsp"%>

	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHousePosi.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label for="code">仓库</label>
						<input type="text" id="code" name="code" style="width:160px;" value=""/>
					</li>
					<li>
						<label for="pk">库位</label>
						<input type="text" id="pk" name="pk" style="width: 160px;"/>
					</li>
					<li>
						<label for="itCode">材料编号</label>
						<input type="text" id="itCode" name="itCode" style="width: 160px;"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${whPosiBal.expired}" 
							onclick="hideExpired(this)" ${whPosiBal.expired!='T' ?'checked':''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WHPOSIBAL_UP">
					<button type="button" class="btn btn-system" id="up">
						<span>上架</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WHPOSIBAL_DOWN">
					<button type="button" class="btn btn-system" id="down">
						<span>下架</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WHPOSIBAL_MOVELIST">
					<button type="button" class="btn btn-system" id="moveList">
						<span>批量移动</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
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

<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	$("#code").openComponent_wareHouse({
		condition:{
			czybh:"${sessionScope.USER_CONTEXT_KEY.czybh}"
		}
	});
	$("#pk").openComponent_wareHousePosi();
	$("#itCode").openComponent_item();

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,/* 列重排 */
		url: "${ctx}/admin/whPosiBal/goJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "code", index: "code", width: 80, label: "仓库编号", sortable: true, align: "left", hidden:true},
			{name: "whdescr", index: "whdescr", width: 100, label: "仓库名称", sortable: true, align: "left"},
			{name: "whpdescr", index: "whpdescr", width: 80, label: "库位名称", sortable: true, align: "left"},
			{name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 240, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "", width: 80, label: "材料类型2", sortable: true, align: "left"},
			{name: "qtycal", index: "qtycal", width: 90, label: "已上架数量", sortable: true, align: "right"},
			{name: "pk", index: "pk", width: 90, label: "仓库库位pk", sortable: true, align: "left", hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left", hidden:true},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left", hidden:true},
			{name: "qtyno", index: "qtyno", width: 90, label: "未上架数量", sortable: true, align: "right"},
			{name: "qtyall", index: "qtyall", width: 80, label: "产品余额", sortable: true, align: "right", hidden:true},
		]
	});
	
	$("#up").on("click", function() {
		var ret=selectDataTableRow();
		var postData={
			m_umState:"U"
		};
		if (ret) {
			postData={
				pk:ret.pk, 
				whpDescr:ret.whpdescr, 
				code:ret.code, 
				desc1:ret.whdescr, 
				itCode:ret.itcode,
				itemDescr:ret.itemdescr,
				m_umState:"U"
			};
		}
		Global.Dialog.showDialog("up", {
			title : "库位调整--上架",
			url : "${ctx}/admin/whPosiBal/goUp",
			postData:postData,
			height : 370,
			width : 460,
			returnFun : goto_query
		});
	});
	
	$("#down").on("click",function(){
		var ret=selectDataTableRow();
		var postData={
			m_umState:"D"
		};
		if (ret) {
			postData={
				pk:ret.pk, 
				whpDescr:ret.whpdescr, 
				code:ret.code, 
				desc1:ret.whdescr, 
				itCode:ret.itcode,
				itemDescr:ret.itemdescr,
				m_umState:"D"
			};
		}
		Global.Dialog.showDialog("down",{
			title:"库位调整--下架",
			url:"${ctx}/admin/whPosiBal/goDown",
			postData:postData,
			height : 370,
			width : 460,
			returnFun:goto_query
		});
	});

	$("#moveList").on("click",function(){
		var ret=selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("moveList",{
				title:"库位调整——批量移动",
				url:"${ctx}/admin/whPosiBal/goMoveList",
				postData:{
					pk:ret.pk, 
					whpDescr:ret.whpdescr, 
					code:ret.code, 
					desc1:ret.whdescr, 
					itCode:ret.itcode,
					itemDescr:ret.itemdescr,
					qtyAll:ret.qtyall,
					m_umState:"M"
				},
				height : 600,
				width : 800,
				returnFun:goto_query
			});
		} else {
			Global.Dialog.showDialog("moveList",{
				title:"库位调整——批量移动",
				url:"${ctx}/admin/whPosiBal/goMoveList",
				postData:{
					m_umState:"M"
				},
				height : 600,
				width : 800,
				returnFun:goto_query
			});
		}
	});

});

function doExcel(){
	var url = "${ctx}/admin/whPosiBal/doExcel";
	doExcelAll(url);
}

</script>
</html>
