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
	<title>搜寻——销售单号</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_saleCust.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript" defer> 
$(function(){
	function getData(data){
		$('#itCodeDescr').val(data.descr);
	}

	$("#custCode").openComponent_saleCust();
	$("#itCode").openComponent_item({showValue:'${salesInvoice.itCode}',showValue:"",callBack: getData,valueOnly:'1'});
	
	postData = $("#page_form").jsonForm();

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/salesInvoice/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "no", index: "no", width: 100, label: "销售单号", sortable: true, align: "left"},
			{name: "itcode", index: "itcode", width: 100, label: "itcode", sortable: true, align: "left",hidden:true},
			{name: "itdescr", index: "itdescr", width: 100, label: "itdescr", sortable: true, align: "left",hidden:true},
			{name: "type", index: "type", width: 100, label: "销售类型", sortable: true, align: "left",hidden:true},
			{name: "typedescr", index: "typedescr", width: 100, label: "销售类型", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 86, label: "客户编号", sortable: true, align: "left"},
			{name: "custname", index: "custname", width: 103, label: "客户名称", sortable: true, align: "left"},
			{name: "mobile1",  index:"mobile1",	width:100, 	label:"手机1", sortable: true,align:"left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 120, label: "销售单状态", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
			{name: "date", index: "date", width: 100, label: "销售日期", sortable: true, align: "left", formatter: formatTime},
			{name: "getitemdate", index: "getitemdate", width: 100, label: "取货日期", sortable: true, align: "left", formatter: formatTime},
			{name: "amount", index: "amount", width: 100, label: "实际总价", sortable: true, align: "left"},
			{name: "warehouse", index: "warehouse", width: 100, label: "仓库名称", sortable: true, align: "left"},
			{name: "whcode", index: "whcode", width: 100, label: "仓库编号", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
		],
	  	ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});

});
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<input type="hidden" id="expired"  name="expired" value="${salesInvoice.expired }"/>
					<input type="hidden" id="type"  name="type" value="${salesInvoice.type}"/>
					<input type="hidden" id="status"  name="status" value="${salesInvoice.status}"/>
					<ul class="ul-form">
						<li> 
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${salesInvoice.custCode }"/>
						</li>
						<li> 
							<label>材料编号</label>
							<input type="text" id="itCode" name="itCode"  value="${salesInvoice.itCode }"/>
						</li>
						<li>
						 	<label>材料名称</label>
							<input type="text" id="itCodeDescr" name="itCodeDescr"  value="${salesInvoice.itCodeDescr }"/>
						</li>
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
						</li>
					</ul>	
				</form>
			</div>
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
		</div>	
	</body>	
</html>
