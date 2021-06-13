<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料列表</title>
<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
<link href="${resourceRoot}/css/tab.css" rel="stylesheet" />
<link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
<link rel="stylesheet" href="${resourceRoot}/zTree/3.5.18/css/zTreeStyle/zTreeStyle.css" type="text/css">
<%-- <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  /> --%>
<link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
<link href="${resourceRoot}/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/ui.jqgrid-bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bsmain.css" rel="stylesheet" />
<script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.jqGrid.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.form.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/artDialog/source/artDialog.js" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
var itemType1='${item.itemType1}'.trim();
$(function(){
        //初始化表格
        var prePlanAreaPk=window.parent.window.parent.document.getElementById("prePlanAreaPK").value;
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custTypeItem/goItemSelectJqGrid?sqlCode=${item.sqlCode}&descr=${item.descr}&code=${item.code}&itemType1=${item.itemType1}&itemType2=${item.itemType2}&custType=${item.custType}&custCode=${item.custCode}&prePlanAreaPk=${item.prePlanAreaPk}",
			height:225,
			styleUI: 'Bootstrap',
			colModel : [
			{name: "custtypeitempk", index: "custtypeitempk", width: 85, label: "套餐材料信息编号", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "itemtype2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "itemtype2descr", sortable: true, align: "left", hidden: true},
			{name: "itemtype3", index: "itemtype3", width: 50, label: "itemtype3", sortable: true, align: "left", hidden: true},
			{name: "descr", index: "descr", width: 250, label: "材料名称1", sortable: true, align: "left"},
			{name: "price", index: "price", width: 50, label: "单价", sortable: true, align: "right"},
			{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "itemremark", index: "itemremark", width: 150, label: "材料描述", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 150, label: "升级描述", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 85, label: "品牌", sortable: true, align: "left", hidden: true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 90, label: "材料类型3", sortable: true, align: "left"},
			{name: "supplcodedescr", index: "supplcodedescr", width: 65, label: "供应商", sortable: true, align: "left"},
			{name: "model", index: "model", width: 90, label: "型号", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 70, label: "规格说明", sortable: true, align: "left"},
			{name: "color", index: "color", width: 40, label: "颜色", sortable: true, align: "left"},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 85, label: "是否固定价", sortable: true, align: "left"},
			{name: "fixareatypedescr", index: "fixareatypedescr", width: 85, label: "适用区域", sortable: true, align: "left"},
			{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "fixareatype", index: "fixareatype", width: 84, label: "适用装修区域", sortable: true, align: "left", hidden: true},
			{name: "fixareatypedescr", index: "fixareatypedescr", width: 84, label: "适用装修区域", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "code", index: "code", width: 20, label: "材料编号", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
			],
             ondblClickRow: function (rowid, status) {
             	var fixAreaPk=window.parent.window.parent.document.getElementById("fixAreaPk").value;
             	var fixAreaDescr=window.parent.window.parent.document.getElementById("fixAreaDescr").value;
             	var intProdPk=window.parent.window.parent.document.getElementById("intProdPk").value;
             	var intProdDescr=window.parent.window.parent.document.getElementById("intProdDescr").value;
             	var rowNo=window.parent.window.parent.document.getElementById("rowNo").value;
	    		var prePlanAreaPk=window.parent.window.parent.document.getElementById("prePlanAreaPK").value;
	            var prePlanAreaDescr=window.parent.window.parent.document.getElementById("prePlanAreaDescr").value;
            	 var rowData = $("#dataTable").jqGrid('getRowData',rowid);
				if(prePlanAreaPk == ""){
					prePlanAreaPk = -1;
				}
            	 
            	 
            	 if(!fixAreaDescr){
                  	art.dialog({
     				content: "请先选择区域!"
     				});
                  	return;
                  	}
                  	if(!intProdDescr&&itemType1=='JC'){
                  	art.dialog({
     				content: "请先选择集成成品!"
     				});
                  	return;
                  	}
	            $.ajax({
	            	url: '${ctx}/admin/custTypeItem/checkFixAreaType',
	            	data:{custType:"${item.custType}",prePlanAreaPk:prePlanAreaPk,itemCode:rowData.code},
	            	type: "post",
	    			dataType: "json",
	            	cache: false,
	            	success: function (obj) {
	                	if (obj) {
	                		rowData.fixareapk=fixAreaPk;
	                    	 rowData.fixareadescr=fixAreaDescr;
	                    	 rowData.intprodpk=intProdPk;
	                    	 rowData.intproddescr=intProdDescr;
	                    	 rowData.itemdescr=rowData.descr;             	
	                    	 rowData.qty=0;
	                    	 rowData.unitprice=rowData.price;
	                    	 rowData.beflineamount=0;
	                    	 rowData.markup=100;
	                    	 rowData.tmplineamount=0;
	                    	 rowData.processcost=0;
	                    	 rowData.lineamount=0;
	                    	 rowData.projectqty=0;
	                    	 rowData.itemcode=rowData.code;
	                    	 rowData.remarks=rowData.remark;
	                    	 //默认套餐材料
	                    	 rowData.isoutsetdescr="否";
	       	    		 	rowData.isoutset="0";
	       	    		 	rowData.preplanareapk=prePlanAreaPk;
	       	             	rowData.preplanareadescr=prePlanAreaDescr;
	       	             	rowData.algorithmdeduct=0;
	       	          		rowData.algorithmper=1.0;
	                    	 $(window.parent.window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo, rowData, "last");
	                    	  window.parent.window.parent.document.getElementById("rowNo").value++;
	                    	
	                  	} 
	                	else{
	                		art.dialog({
	             				content: "该材料适用类型为："+rowData.fixareatypedescr,
	             			});
	                		return;
	                	}
	                }
	            });
	            
            }
		});
});
</script>
</head>
    
<body>
	<div >
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
	</div>
</body>
</html>
