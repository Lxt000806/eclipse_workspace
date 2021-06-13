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
var clickFlag=false;
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/baseItem/goItemSelectJqGrid?baseItemType1=${baseItem.baseItemType1}&baseItemType2=${baseItem.baseItemType2}&descr=${baseItem.descr}&isOutSet=${baseItem.isOutSet}"
			    +"&custType=${baseItem.custType}&canUseComBaseItem=${baseItem.canUseComBaseItem}",
			height:225,
			colModel : [
				{name: "code", index: "code", width: 84, label: "项目材料编号", sortable: true, align: "left", hidden: true},
			    {name: "baseitemtype1", index: "baseitemtype1", width: 84, label: "baseitemtype1", sortable: true, align: "left", hidden: true},
				{name: "baseitemtype2", index: "baseitemtype2", width: 84, label: "baseitemtype2", sortable: true, align: "left", hidden: true},
				{name: "descr", index: "descr", width: 207, label: "基装项目名称", sortable: true, align: "left"},
				{name: "categorydescr", index: "categorydescr", width: 87, label: "项目分类", sortable: true, align: "left", hidden: true},
				{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left",hidden:true},
				{name: "marketprice", index: "marketprice", width: 78, label: "市场价", sortable: true, align: "right"},
				{name: "offerpri", index: "offerpri", width: 80, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right"},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 90, label: "成本", sortable: true, align: "left", hidden: true},
           		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
           		{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
           		{name: "isbaseitemsetdescr", index: "isbaseitemsetdescr", width: 60, label: "套餐包", sortable: true, align: "left"},
           		{name: "isbaseitemset", index: "isbaseitemset", width: 60, label: "套餐包", sortable: true, align: "left", hidden: true},
           		{name: "fixareatypedescr", index: "fixareatypedescr", width: 68, label: "空间类型", sortable: true, align: "left"},
           		{name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
            ],
             ondblClickRow: function (rowid, status) {
             	if (clickFlag) {
        			return false;
    			}
			    clickFlag = true;
           		var rowId=  $(window.parent.window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
               	var rowData =$(window.parent.window.parent.document.getElementById("dataTable")).jqGrid('getRowData',rowId);
               	//var rowNo=$(window.parent.window.parent.document.getElementById("baseItemPlanDetailDataTable")).jqGrid('getGridParam','records')+1;
               	var rowNo=parseInt(window.parent.window.parent.document.getElementById("rowNo").value);
             	var fixAreaPk=rowData.PK;
             	var fixAreaDescr=rowData.Descr;
             	var prePlanAreaPk=rowData.PrePlanAreaPK;
	            var prePlanAreaDescr=rowData.preplanaredescr ;
             	if(!fixAreaDescr){
	             	art.dialog({
						content: "请先选择区域!"
					});
	             	return;
             	}
             	var rowData = $("#dataTable").jqGrid('getRowData',rowid);
             	if(rowData.isbaseitemset=="1"){
             		$.ajax({
						url: "${ctx}/admin/itemPlan/doBaseItemSetAdd",
						type:"post",
				        dataType:"json",
				        data: {custCode:"${baseItem.custCode}", fixAreaPk:fixAreaPk,baseItemCode:rowData.code},
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
							clickFlag = false;
					    },
					    success: function(obj){
						    if(obj.rs){
						        var datas=JSON.parse(obj.datas);
						        rowData.fixareapk=fixAreaPk;
				             	rowData.preplanareadescr=prePlanAreaDescr;
				             	rowData.preplanareapk=prePlanAreaPk;
					            rowData.fixareadescr=fixAreaDescr;
				             	rowData.baseitemdescr=rowData.descr         	
				             	rowData.qty=0;
				             	rowData.cost=rowData.cost;
				             	rowData.unitprice=rowData.offerpri;
				             	rowData.lineamount=0;
				             	rowData.baseitemcode=rowData.code;
				             	rowData.iscalmangefee=rowData.iscalmangefee;
				             	if(datas){
				             		rowData.baseitemsetno=datas[0].baseitemsetno;
				             	}
				             	rowData.ismainitem='1';
				             	rowData.ismainitemdescr='是';
				             	//rowData.lineamount=myRound(rowData.offerpri*1)+myRound(rowData.material*1)
				             	$(window.parent.window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo, rowData, "last");
	                			window.parent.window.parent.document.getElementById("rowNo").value=rowNo+1;
							    $.each(datas,function(k,v){
							    	 rowNo=parseInt(window.parent.window.parent.document.getElementById("rowNo").value);
						  			 $(window.parent.window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo, v, "last");
	                				 window.parent.window.parent.document.getElementById("rowNo").value=rowNo+1;
						  		})
						  		clickFlag = false;
					  		}else{
					  			art.dialog({
									content: obj.msg
								});
								clickFlag = false;
					  		}
						 }
				 	});	  
				} else{
		        	rowData.fixareapk=fixAreaPk;
	             	rowData.fixareadescr=fixAreaDescr;
	             	rowData.preplanareadescr=prePlanAreaDescr;
	             	rowData.preplanareapk=prePlanAreaPk;
	             	rowData.baseitemdescr=rowData.descr         	
	             	rowData.qty=0;
	             	rowData.cost=rowData.cost;
	             	rowData.unitprice=rowData.offerpri;
	             	rowData.lineamount=0;
	             	rowData.baseitemcode=rowData.code;
	             	rowData.iscalmangefee=rowData.iscalmangefee;
	             	rowData.ismainitem='1';
	             	rowData.ismainitemdescr='是';
	             	$(window.parent.window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo, rowData, "last");
	                window.parent.window.parent.document.getElementById("rowNo").value=rowNo+1;
	                clickFlag = false;
		        }
            },
			
		});
		if('${baseItem.isOutSet}'=='2')  $("#dataTable").setGridParam().showCol("categorydescr").trigger("reloadGrid");
});
</script>
</head>
<body>
	<div>
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>	
	</div>
</body>
</html>
