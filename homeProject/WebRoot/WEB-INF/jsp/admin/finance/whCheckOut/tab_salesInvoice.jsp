<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	/**初始化表格*/
$(document).ready(function(){
	 	if( ($.trim($("#m_umState").val())=="C")||($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){
		 	$("#addSalesInvoice").css("color","gray");
			$("#delSalesInvoice").css("color","gray");
		}  	
		Global.JqGrid.initJqGrid("dataTable_salesInvoice",{
			 url:"${ctx}/admin/whCheckOut/goJqGrid_salesInvoiceDetail",
			 postData:{no:$.trim($("#no").val())},
		    rowNum:10000000,
			height:280,
        	styleUI: 'Bootstrap',
			colModel : [
			   {name: "ischeckout", index: "ischeckout", width: 90, label: "是否记账", sortable: true, align: "left", count: true, hidden: true},
			   {name: "no", index: "no", width: 90, label: "销售单号", sortable: true, align: "left"},
               {name: "custname", index: "custname", width: 90, label: "客户名称", sortable: true, align: "left"},
               {name: "address", index: "address", width: 164, label: "楼盘", sortable: true, align: "left"},
               {name: "salestypedescr", index: "salestypedescr", width: 100, label: "销售单类型", sortable: true, align: "left"},
               {name: "getitemdate", index: "getitemdate", width: 140, label: "取货日期", sortable: true, align: "left", formatter: formatTime},
               {name: "totalmaterialcost", index: "totalmaterialcost", width: 80, label: "材料成本", sortable: true, align: "right", sum: true},
               {name: "totaladditionalcost", index: "totaladditionalcost", width: 80, label: "附加成本", sortable: true, align: "right", sum: true},
               {name: "amount", index: "amount", width: 80, label: "销售额", sortable: true, align: "right", sum: true},
               {name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"}
            ],
            loadonce: true,
		});
});

 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="addSalesInvoice">新增</button>
				<button type="button" class="btn btn-system "  id="delSalesInvoice">删除</button>
				<button type="button" class="btn btn-system "  id="salesInvoiceExcel" >输出到Excel</button>

			</div>
		</div>
	</div>
	<table id="dataTable_salesInvoice" style="overflow: auto;"></table>
</div>




