<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>人工费用管理-编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/laborFee/doItemReqExcel";
		doExcelAll(url);
	}
	$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",
									firstValue:"${itemType1 }",
									disabled:"true"
								});
	
		var gridOption = {
			url:"${ctx}/admin/laborFee/goFindItemReqBySql",
			postData:{custCode:"${custCode}",itemType1:"${itemType1}"},	
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width:110, label: "材料名称", sortable: true, align: "left", },
				{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",sum:true},
				{name: "sendqty", index: "sendqty", width: 80, label: "已发货数量", sortable: true, align: "right",sum:true},
				{name: "processcost", index: "processcost", width: 75, label: "其他费用", sortable: true, align: "right"},
				{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 74, label: "材料类型2", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 113, label: "区域", sortable: true, align: "left", },
				{name: "lineamount", index: "lineamount", width: 75, label: "总价", sortable: true, align: "right",sum:true},
				{name: "disccost", index: "disccost", width: 75, label: "优惠分摊", sortable: true, align: "right",sum:true},
				{name: "aftdiscamount", index: "aftdiscamount", width: 85, label: "优惠后总价", sortable: true, align: "right",sum:true },
				{name: "remark", index: "remark", width: 160, label: "说明", sortable: true, align: "left"}	
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	});
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system "  onclick="doExcelNow('材料需求明细表')" title="导出检索条件数据">
								<span>导出excel</span>
						</button>	
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>材料名称</label>
							<input type="text" id="itemDescr" name="itemDescr" style="width:160px;"/>
						</li>
						<li>
							<label>区域</label>
							<input id="areaDescr" name="areaDescr" style="width: 160px;"></select>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;" disabled="true"></select>
						</li>
						<li>
							<label>材料类型2</label>
							<house:DictMulitSelect id="itemType2" dictCode="" sql=" select * from tItemType2 where itemType1 = '${itemType1 }'" 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
			</div>	
		</div>
	</div>
</body>
</html>
