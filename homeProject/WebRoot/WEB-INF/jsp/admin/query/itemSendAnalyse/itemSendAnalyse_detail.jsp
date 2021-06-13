<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<title>材料发货明细列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
    /**初始化表格*/
    $(function () {
      //初始化表格
        Global.JqGrid.initJqGrid("dataTable", {
	        url: "${ctx}/admin/itemSendAnalyse/goDetailJqGrid",
	        height: $(document).height() - $("#content-list").offset().top - 70,
	        postData: $("#page_form").jsonForm(),
	        rowNum: 10000,
			colModel : [
			  	{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
			  	{name: "address", index: "address", width: 172, label: "楼盘", sortable: true, align: "left"},
			  	{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right"},
			  	{name: "senddate", index: "senddate", width: 70, label: "送货日期", sortable: true, align: "left", formatter: formatTime},
			  	{name: "itemcode", index: "itemcode", width: 78, label: "材料编号", sortable: true, align: "left"},
			  	{name: "descr", index: "descr", width: 175, label: "材料名称", sortable: true, align: "left"},
			  	{name: "qty", index: "qty", width: 40, label: "数量", sortable: true, align: "right", sum: true},
			  	{name: "unitprice", index: "unitprice", width: 79, label: "销售单价", sortable: true, align: "right"},
			  	{name: "saleamount", index: "saleamount", width: 95, label: "销售金额", sortable: true, align: "right", sum: true},
			  	{name: "cost", index: "cost", width: 77, label: "结算单价", sortable: true, align: "right"},
			  	{name: "purchaseamount", index: "purchaseamount", width: 90, label: "结算金额", sortable: true, align: "right", sum: true},
			  	{name: "profit", index: "profit", width: 89, label: "利润", sortable: true, align: "right", sum: true}
			]
	
		});
    });
	</script>
	</head>
	<body>
		<div class="body-box-list">
  			<div class="panel panel-system">
    			<div class="panel-body">
      				<div class="btn-group-xs">
        				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      				</div>
    			</div>
  			</div>
  			<div >
    			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      				<input type="hidden" id="sendDateFrom" name="sendDateFrom" class="i-date"
				           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				           value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>"/>
			      	<input type="hidden" id="sendDateTo" name="sendDateTo" class="i-date"
			               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
			               value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>"/>
			        <input type="hidden" id="custCode" name="custCode" value="${itemApp.custCode}"/>
			        <input type="hidden" id="groupType" name="groupType" value="${itemApp.groupType}"/>
			        <input type="hidden" id="itemType1" name="itemType1" value="${itemApp.itemType1}"/>
			        <input type="hidden" id="itemType2" name="itemType2" value="${itemApp.itemType2}"/>
			        <input type="hidden" id="itemType3" name="itemType3" value="${itemApp.itemType3}"/>
			        <input type="hidden" id="itemCode" name="itemCode" value="${itemApp.itemCode}"/>
			        <input type="hidden" id="sqlCode" name="sqlCode" value="${itemApp.sqlCode}"/>
			        <input type="hidden" id="supplCode" name="supplCode" value="${itemApp.supplCode}"/>
			        <input type="hidden" id="sendCzy" name="sendCzy" value="${itemApp.sendCzy}"/>
			        <input type="hidden" id="department2" name="department2" value="${itemApp.department2}"/>
			        <input type="hidden" id="containCmpCust" name="containCmpCust" value="${itemApp.containCmpCust}"/>
			        <input type="hidden" id="custType" name="custType" value="${itemApp.custType}"/>
			        <input type="hidden" id="isSetItem" name="isSetItem" value="${itemApp.isSetItem}"/>
			        <input type="hidden" id="sendType" name="sendType" value="${itemApp.sendType}"/> 
    			</form>
			</div>
  			<div id="content-list">
    			<table id="dataTable"></table>

  			</div>
		</div>

	</body>
</html>


