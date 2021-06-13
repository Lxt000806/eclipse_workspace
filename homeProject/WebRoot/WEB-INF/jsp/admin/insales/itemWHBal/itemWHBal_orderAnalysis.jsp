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
	<title>订货分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
	function doExcel(){
		var url = "${ctx}/admin/itemWHBal/doOrderAnalysisQueryExcel";
		doExcelAll(url);
	}
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		$("#itCode").openComponent_item();	
		$("#whCode").openComponent_wareHouse();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/itemWHBal/goOrderAnalysisQuery",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: "Bootstrap",
			loadonce:true, 
			colModel : [
				{name: "itcode", index: "itcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 72, label: "材料类型2", sortable: true, align: "left"},
				{name: "itdescr", index: "itdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "totalqty", index: "totalqty", width: 70, label: "库存数量", sortable: true, align: "right"},
				{name: "purchonway", index: "purchonway", width: 100, label: "未到货采购量", sortable: true, align: "right"},
				{name: "allqty", index: "allqty", width: 70, label: "库存合计", sortable: true, align: "right"},
				{name: "invwarnqty", index: "invwarnqty", width: 80, label: "库存预警线", sortable: true, align: "right"},
				{name: "sugorderqty", index: "sugorderqty", width: 80, label: "应订货数量", sortable: true, align: "right"},
				{name: "recentsendqty", index: "recentsendqty", width: 105, label: "近期发货量", sortable: true, align: "right"},
				{name: "yqrfhqty", index: "yqrfhqty", width: 105, label: "已确认需求量", sortable: true, align: "right"},
				{name: "ddqrwfhqty", index: "ddqrwfhqty", width: 110, label: "到点确认需求量", sortable: true, align: "right"},
				{name: "avgsendday", index: "avgsendday", width: 94, label: "平均发货天数", sortable: true, align: "right"},
				{name: "avgdaysendqty", index: "avgdaysendqty", width: 80, label: "发货日销量", sortable: true, align: "right"},
				{name: "avgreqdaysend", index: "avgreqdaysend", width: 80, label: "需求日销量", sortable: true, align: "right"},
				{name: "ordercycle", index: "ordercycle", width: 70, label: "订货周期", sortable: true, align: "right"},
				{name: "safeinvctrl", index: "safeinvctrl", width: 90, label: "库存安全系数", sortable: true, align: "right"},
				{name: "avgdaysale", index: "avgdaysale", width: 80, label: "平均日销量", sortable: true, align: "right",hidden:true},
			],
		});
		window.goto_query = function(){
			var itemType1 = $("#itemType1").val();
			var whCode = $("#whCode").val();
			if(itemType1 =="" && whCode =="" ){
				art.dialog({
					content:"仓库编号和材料类型1不能同时为空！"
				});
				return;
			}
			var dateFrom=new Date($.trim($("#dateFrom").val()));
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
						url:"${ctx}/admin/itemWHBal/goOrderAnalysisQuery",}).trigger("reloadGrid");
		}
		
	});
	
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$("#openComponent_item_itCode").val("");
		$("#openComponent_wareHouse_whCode").val("");
		$("#itCode").val("");
		$("#whCode").val("");
		$("#itemType2_NAME").val('');
		$("#itemType2").val('');
		$.fn.zTree.getZTreeObj("tree_itemType2").checkAllNodes(false);
	} 
	
	function isClearInvChg(obj){
		if ($(obj).is(':checked')){
			$('#isClearInv').val('1');
		}else{
			$('#isClearInv').val('0');
		}
	} 
	
	function getItemType2(){
		
		var treeObj = $.fn.zTree.getZTreeObj("tree_itemType2"); //材料类型2树
		var nodes = treeObj.getNodes(); 
		var child = nodes[0]; 
		var itemType1 = $("#itemType1").val();
		
		// 获取材料类型1对应的材料类型2
		$.ajax({
			url:"${ctx}/admin/item/getAuthItemType2ByItemType1",
			type: "post",
			data: {itemType1:itemType1, expired: "F"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	// 材料类型1改变 清空原本材料类型2的值和选项
		    	$("#itemType2").val("");
		    	$("#itemType2_NAME").val("");
				// 更新下拉树
				child.children = obj;
				// 刷新树
				treeObj.refresh();
		    }
		});
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>	
		</div>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="isClearInv" name="isClearInv" value="0"/>
					<input type="hidden" id="purchOrderAnalyse" name="purchOrderAnalyse" value="0"/>
					<ul class="ul-form">
						<li>
							<label>仓库编号</label>
							<input type="text" id="whCode" name="whCode"/>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" onchange="getItemType2()"></select>
						</li>
						<li>
						<label>材料类型2</label>
						<house:DictMulitSelect id="itemType2" dictCode="" sql=" select code, descr from titemType2 where 1<>1" 
							sqlLableKey="descr" sqlValueKey="code"></house:DictMulitSelect>
						</li>
						<li id="itCodeLi">		
							<label>材料编号</label>
							<input type="text" id="itCode" name="itCode"/>
						</li>
						<li id="id_constructStatus">
							<label>施工状态</label>
							<house:xtdm id="constructStatus" dictCode="CONSTRUCTSTATUS" value='1'></house:xtdm>
						</li>
						<li>
		                    <label>日期从</label>
		                    <input type="text" id="dateFrom" name="dateFrom" class="i-date"
		                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"
		                           value="<fmt:formatDate value='${itemWHBal.dateFrom}' pattern='yyyy-MM-dd'/>"/>
		                </li>
		                <li>
		                    <label>到</label>
		                    <input type="text" id="dateTo" name="dateTo" class="i-date"
		                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"
		                           value="<fmt:formatDate value='${itemWHBal.dateTo}' pattern='yyyy-MM-dd'/>"/>
		                </li>
						<li>
							<label></label>
							<input type="checkbox" id="isClearInvCheck" name="isClearInvCheck" onclick="isClearInvChg(this)" value=""/>包含清库存材料
						</li>
						<li >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
			<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
				<span>导出excel</span>
			</button>
		</div>	
	</div>
	<div id="content-list">
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>	
</html>
