<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<title>材料发货分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/materialSend/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap',
			colModel : [
		  		{name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  		{name : 'itemtype2descr',index : 'itemdescr',width : 100,label:'材料类型2',align : "left"},
				{name : 'itemdescr',index : 'itemdescr',width : 260,label:'材料名称',align : "left"},
		 		{name : 'sendqty',index : 'qty',width : 100,label:'发货数量',align : "right"},
		  		{name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',align : "left"},
		  		{name : 'yqrxqqty', index: 'yqrxqqty', width: 120, label: '已确认需求量', sortable: true, align: "right"},
		  		{name : 'allqty',index : 'qty',width : 120,label:'有家库存数量',align : "right"}
           ]
		});
		
		//输出到Excel
		$("#detailExcel").on("click",function(){
			Global.JqGrid.exportExcel("dataTable","${ctx}/admin/materialSend/export","材料发货分析","targetForm");
		});
		
		function getData(data){
			$('#sendDateFrom').val(data.sendDateFrom);
			$('#sendDateTo').val(data.sendDateTo);
		}
});

//刷新表格
function goto_query(dataTableId){
	var from = $('#sendDateFrom').val();
	var to = $('#sendDateTo').val();

	if (from == '') {
		art.dialog({content: '请选择起始发货日期！'});
		return;
	}

	if (to == '') {
		art.dialog({content: '请选择截止发货日期！'});
		return;
	}
	
	var aDate, oDate1, oDate2, iDays;
    aDate = from.split("-");
    oDate1 = new Date(from);  
    oDate2 = new Date(to);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
 
    if (iDays > 366) {
    	art.dialog({content: '检索的发货日期不能超过1年！'});
    	return;
    }

	var tableId = 'dataTable';
	if (dataTableId && dataTableId.length>0){
		tableId = dataTableId;
	}
	$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<ul class="ul-form">
					<li>
					<label>发货日期</label>
					<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>	
					<li>
					<label>至</label>
					<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li id="loadMore" >
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();" >清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " id="detailExcel">输出到Excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table> 
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


