<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_purchaseDetail",{
		url:"${ctx}/admin/purchaseDetail/goViewJqGrid",
		postData:{puno:$.trim($("#no").val()),status:$.trim($("#status").val())},
	    rowNum: 10000000,
	    sortable: true,
		height: 155,
		colModel : [
			{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
			{name:'no', index:'no', width:80, label:'no', sortable:true ,align:"left",hidden:true},
			{name:'puno', index:'puno', width:80, label:'puno', sortable:true ,align:"left",hidden:true},
			{name:'itcode', index:'itcode', width:80, label:'材料编号', sortable:true ,align:"left"},
			{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left",count:true},
			{name:'sqlcodedescr', index:'sqlcodedescr', width:60, label:'品牌', sortable:true ,align:"left"},
			{name:'color', index:'color', width:60, label:'颜色', sortable:true ,align:"left"},	
			{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"right",sum:true},
			{name:'qtycal',	index:'qtycal',width:80, label:'采购数量',sortable : true,align : "right",sum:true},
			//{name:'useqty',	index:'useqty',width:60, label:'可用量', sortable : true,align : "right", },
			//{name:'purqty',	index:'purqty',width:60, label:'在途采购量',sortable : true,align : "right",sum:true},
			{name:'arrivqty',	index:'arrivqty',width:80, label:'已到货数量',sortable : true,align : "right",sum:true},
			{name:'unidescr', index:'unidescr', width:60, label:'单位', sortable:true ,align:"left"},
			{name:'unitprice', index:'unitprice', width:60, label:'单价', sortable : true,align : "right"},
			{name:'markup', index:'markup', width:60, label:'折扣',sortable : true,align : "left"},
			{name:'beflineprice', index:'beflineprice', width:80, label:'折扣前单价', sortable : true,align : "left",hidden:true},
			{name:'beflineamount', index:'beflineamount', width:80, label:'折扣前金额', sortable:true ,align:"left",sum:true},
			{name:'amount', index:'amount', width:60, label:'总价', sortable:true ,align:"right",sum:true},
			{name:'projectcost', index:'projectcost', width:120, label:'项目经理结算价', sortable:true ,align:"right",sum:true},
			{name:'remarks',index:'remarks',width:180, label:'备注', sortable:true,align:"left"},
			{name:'sino',index:'sino',width:150, label:'sino', 	sortable:true,align:"left",hidden:true},
	  ],
	});
	if('${costRight}'=='0'){
		jQuery("#dataTable_purchaseDetail").setGridParam().hideCol("unitprice").trigger("reloadGrid");
		jQuery("#dataTable_purchaseDetail").setGridParam().hideCol("amount").trigger("reloadGrid");
		jQuery("#dataTable_purchaseDetail").setGridParam().hideCol("projectcost").trigger("reloadGrid");
	}
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable_purchaseDetail');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"查看采购信息",
				url:"${ctx}/admin/purchase/goAddView",
				postData:{puno:ret.puno,itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr,remarks:ret.remarks,fromPage:"${purchase.fromPage}",type:"${purchase.type}"},
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});
});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" class="btn btn-system " id="view">
					<span>查看</span>
				</button>
				<!-- <button type="button" class="btn btn-system "
					onclick="doExcelNow('采购订单明细')" title="导出当前excel数据">
					<span>导出excel</span>
				</button> -->
			</div>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable_purchaseDetail" style="overflow: auto;"></table>
	</div>
</div>
