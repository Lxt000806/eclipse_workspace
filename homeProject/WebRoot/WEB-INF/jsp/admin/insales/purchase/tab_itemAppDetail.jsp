<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemAppDetail/goPurchJqGrid",
			postData:{puno:'${purchase.no }'},
			rowNum:10000000,
			height:130,
			colModel : [
				{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
				{name: "puno", index: "puno", width: 50, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "itemdescr", index: "itemdescr", width: 205, label: "材料名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 75, label: "领料数量", sortable: true, align: "right"},
				{name: "declareqty", index: "declareqty", width: 75, label: "申报数量", sortable: true, align: "right",hidden: true},
				{name: "uomdescr", index: "unitdescr", width: 45, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 45, label: "单价", sortable: true, align: "right"},
				{name: "markup", index: "markup", width: 60, label: "折扣(%)", sortable: true, align: "right"},
				{name: "processcost", index: "processcost", width: 70, label: "其它费用", sortable: true, align: "right", sum:true},
				{name: "lineamount", index: "lineamount", width: 60, label: "总价", sortable: true, align: "right", sum: true},
				{name: "diffpor", index: "diffpor", width: 75, label: "差异占比", sortable: true, align: "right",hidden: true, formatter:function(cellvalue, options, rowObject){return myRound(parseFloat(cellvalue)*100, 2)+"%";}},
				{name: "remarks", index: "remarks", width: 244, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
				{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true}
			],  
			gridComplete:function(){
				if("${purchase.fromPage}" == "supplierCheck" && "${purchase.itemType1}" && "${purchase.itemType1}".trim() == "JC"){
					var declareQtys = $("#dataTable1").jqGrid("getCol", "declareqty");
					var diffPors = $("#dataTable1").jqGrid("getCol", "diffpor");
					
					var sumDeclareQty = 0.0;
					var sumDiffQty = 0.0;
					for(var i = 0;i < declareQtys.length;i++){
						sumDeclareQty += parseFloat(declareQtys[i]);
						sumDiffQty += parseFloat(declareQtys[i]) * (parseFloat(diffPors[i].substring(0, diffPors[i].length - 1)) / 100.0);
					}
					$("#dataTable1").footerData("set", {
						diffpor : myRound((sumDiffQty/sumDeclareQty), 4)
					});
				}	
			},
			beforeSelectRow:function(rowId, e){
				setTimeout(function(){
					relocate(rowId,"dataTable1");
				},10);
			}
 		};
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable1",gridOption);
	
	//来自供应商结算模块查看集成采购单显示对应列   
	if("${purchase.fromPage}" == "supplierCheck" && "${purchase.itemType1}" && "${purchase.itemType1}".trim() == "JC"){
		$("#dataTable1").jqGrid("showCol", "declareqty");
		$("#dataTable1").jqGrid("showCol", "diffpor");
	}		

});
 </script>
<div class="body-box-list" style="margin-top: 0px;border:1px solid #ddd;">
	<div class="pageContent">
		<table id="dataTable1" style="overflow: auto;"></table>
	</div>
</div>




