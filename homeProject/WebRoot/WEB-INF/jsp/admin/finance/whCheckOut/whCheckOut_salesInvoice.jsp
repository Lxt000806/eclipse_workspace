<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SalesInvoice列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system "  id="saveBtn">保存</button>
					<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
<!-- 		<div class="panel panel-info" >  
			<div class="panel-body"> -->
		<div>  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${salesInvoice.expired}" />
					<ul class="ul-form">
						<li>
						    <label>客户编号</label>
							<input type="text" id="custCode" name="custCode" />
						</li>
						<li>
							<label>销售单号</label>
							<input type="text" id="no" name="no" />
						</li>
						<li>
							<label>仓库</label>
							<input type="text" id="whcode" name="whcode" />
						</li>
						<li>
						    <label>材料类型1</label>
							<select id="itemType1" name="itemType1" ></select>
						</li>
						<li>
							<label>销售日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salesInvoice.date}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>			
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salesInvoice.date}' pattern='yyyy-MM-dd'/>" />																
						</li>
						<li class="search-group">			
							<input type="checkbox" id="expired_show" name="expired_show"
								value="${salesInvoice.expired}" onclick="hideExpired(this)"
								${item.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"> </div>

		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
		
	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>		
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>	
<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>	
<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
$("#whcode").openComponent_wareHouse({showValue:"${postParam.whCode}",showLabel:"${whCheckOut.whDescr}",readonly:true});
$("#custCode").openComponent_customer();
$("#no").openComponent_salesInvoice();
Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");

/**初始化表格*/
$(function(){
	//初始化查询条件
	var optionSelect=$("#itemType1 option");
    var sValue=""
    optionSelect.each(function (i,e) {
    	sValue= $(e).text().replace(/[^a-z]+/ig,"");
        if(sValue=="JZ"){
            $(this).attr("selected","selected");
        }   
    });
    var sItemType1=$("#itemType1").val();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
	    postData:{whcode:"${postParam.whCode}",whCheckOutNo:"${salesInvoice.whcheckOutNo}",itemType1:sItemType1,unSelected: "${salesInvoice.unSelected}"},
		url:"${ctx}/admin/whCheckOut/goWHCheckOutSalesInvoiceAddJqGrid",
		height:$(document).height()-$("#content-list").offset().top-120,
		multiselect:true,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "ischeckout", index: "ischeckout", width: 62, label: "是否记账", sortable: true, align: "left", count: true,hidden: true},
          {name: "no", index: "no", width: 80, label: "销售单号", sortable: true, align: "left"},
          {name: "salestypedescr", index: "salestypedescr", width: 80, label: "销售单类型", sortable: true, align: "left"},
          {name: "custname", index: "custname", width: 100, label: "客户名称", sortable: true, align: "left"},
          {name: "address", index: "address", width: 139, label: "楼盘", sortable: true, align: "left"},
          {name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
          {name: "date", index: "date", width: 75, label: "销售日期", sortable: true, align: "left", formatter: formatTime},
          {name: "totalmaterialcost", index: "totalmaterialcost", width: 80, label: "材料成本", sortable: true, align: "right"},
          {name: "totaladditionalcost", index: "totaladditionalcost", width: 80, label: "附加成本", sortable: true, align: "right"},
          {name: "amount", index: "amount", width: 80, label: "销售额", sortable: true, align: "right"},
		  {name: "remarks", index: "remarks", width: 280, label: "备注", sortable: true, align: "left"},
		  {name: "getitemdate", index: "getitemdate", width: 135, label: "取货日期", sortable: true, align: "left", formatter: formatTime,hidden: true}
       ]
	});
    
    //保存
    $("#saveBtn").on("click",function(){
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
    });

});
</script>		
</body>
</html>


