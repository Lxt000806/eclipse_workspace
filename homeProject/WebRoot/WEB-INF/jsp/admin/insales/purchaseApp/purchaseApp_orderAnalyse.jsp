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
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${purchaseApp.itemType1}',
		};
		Global.LinkSelect.setSelect(dataSet);
		$("#itCode").openComponent_item();	
		$("#whCode").openComponent_wareHouse({showValue:"${purchaseApp.whCode}",showLabel:"${wareHouse.desc1}"});	
		
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/itemWHBal/goOrderAnalysisQuery",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: "Bootstrap",
			loadonce:true, 
			multiselect:true,
			colModel : [
				{name: "itcode", index: "itcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 72, label: "材料类型2", sortable: true, align: "left"},
				{name: "itdescr", index: "itdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "totalqty", index: "totalqty", width: 70, label: "库存数量", sortable: true, align: "right"},
				{name: "purchonway", index: "purchonway", width: 100, label: "未到货采购量", sortable: true, align: "right"},
				{name: "allqty", index: "allqty", width: 70, label: "库存合计", sortable: true, align: "right"},
				{name: "invwarnqty", index: "invwarnqty", width: 80, label: "库存预警线", sortable: true, align: "right"},
				{name: "sugorderqty", index: "sugorderqty", width: 80, label: "应订货数量", sortable: true, align: "right"},
				{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
				{name: "recentsendqty", index: "recentsendqty", width: 105, label: "近期发货量", sortable: true, align: "right"},
				{name: "yqrfhqty", index: "yqrfhqty", width: 105, label: "已确认需求量", sortable: true, align: "right"},
				{name: "ddqrwfhqty", index: "ddqrwfhqty", width: 110, label: "到点确认需求量", sortable: true, align: "right"},
				{name: "avgsendday", index: "avgsendday", width: 94, label: "平均发货天数", sortable: true, align: "right"},
				{name: "avgdaysendqty", index: "avgdaysendqty", width: 80, label: "发货日销量", sortable: true, align: "right"},
				{name: "avgreqdaysend", index: "avgreqdaysend", width: 80, label: "需求日销量", sortable: true, align: "right"},
				{name: "ordercycle", index: "ordercycle", width: 70, label: "订货周期", sortable: true, align: "right"},
				{name: "safeinvctrl", index: "safeinvctrl", width: 90, label: "库存安全系数", sortable: true, align: "right"},
				{name: "avgdaysale", index: "avgdaysale", width: 80, label: "平均日销量", sortable: true, align: "right",hidden:true},
				{name: "send7", index: "send7", width: 100, label: "近7天发货", sortable: true, align: "right"},
				{name: "send30", index: "send30", width: 100, label: "近30天发货", sortable: true, align: "right"},
				{name: "send60", index: "send60", width: 100, label: "近60天发货", sortable: true, align: "right"},
				{name: "useqty", index: "useqty", width: 100, label: "可用数量", sortable: true, align: "right"},
			],
		});
				
		window.goto_query = function(){
			var itemType1 = $("#itemType1").val();
			var whCode = $("#whCode").val();
			var dateFrom = $("#dateFrom").val();
			var dateTo = $("#dateTo").val();
			if(itemType1 =="" && whCode =="" ){
				art.dialog({
					content:"仓库编号和材料类型1不能同时为空！"
				});
				return;
			}
			
			if(dateFrom == "" || dateTo == ""){
				art.dialog({
					content:"请填写完整时间段",
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
		$("#page_form select:not([id$='itemType1'])").val("");
		$("#openComponent_item_itCode").val("");
		$("#openComponent_wareHouse_whCode").val("");
		$("#itCode").val("");
		$("#whCode").val("");
	} 
	
	function isClearInvChg(obj){
		if ($(obj).is(':checked')){
			$('#isClearInv').val('1');
		}else{
			$('#isClearInv').val('0');
		}
	} 
	
	function save(){
       	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
       	if(ids.length==0){
       		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
       		return;
       	}
       	var selectRows = [];
   		$.each(ids,function(k,id){
   			var row = $("#dataTable").jqGrid('getRowData', id);
   			selectRows.push(row);
   		});
   		Global.Dialog.returnData = selectRows;
   		closeWin();
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBut" onclick="save()">
						<span>保存</span>
					</button>
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
					<input type="hidden" id="purchOrderAnalyse" name="purchOrderAnalyse" value="1"/>
					<input type="hidden" id="itemCodes" name="itemCodes" value="${purchaseApp.itemCodes }"/>
					<ul class="ul-form">
						<li>
							<label>仓库编号</label>
							<input type="text" id="whCode" name="whCode"/>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" disabled="true"></select>
						</li>
						<li>
							<label>材料类型2</label>
							<house:DictMulitSelect id="itemType2" dictCode="" sql=" select code, descr from titemType2 where Expired='F' and itemType1='${purchaseApp.itemType1}'"
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
		                           value="<fmt:formatDate value='${purchaseApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
		                </li>
		                <li>
		                    <label>到</label>
		                    <input type="text" id="dateTo" name="dateTo" class="i-date"
		                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"
		                           value="<fmt:formatDate value='${purchaseApp.dateTo}' pattern='yyyy-MM-dd'/>"/>
		                </li>
						<li>
							<label></label>
							<input type="checkbox" id="isClearInvCheck" name="isClearInvCheck" onclick="isClearInvChg(this)" value="0"/>包含清库存材料
						</li>
						<li >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div id="content-list">
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>	
</html>
