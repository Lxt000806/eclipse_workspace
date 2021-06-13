<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>单价修改列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
var topFrame="#iframe_itemPlan_ys";
$(function(){
	 Global.JqGrid.initJqGrid("qtyModifyDataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 110, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 195, label: "材料名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 71, label: "数量", sortable: true, align: "right"},
				{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable: true, align: "right"},
	       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 78, label: "套外材料", sortable: true, align: "left"},
			]  
	});
	Global.JqGrid.initJqGrid("markupModifyDataTable",{
			height:$(document).height()-$("#content-list2").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 110, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 195, label: "材料名称", sortable: true, align: "left"},
				{name: "markup", index: "markup", width: 71, label: "折扣", sortable: true, align: "right"}
            ]  
	});
	Global.JqGrid.initJqGrid("qtyModifyServiceDataTable",{
			height:$(document).height()-$("#content-list3").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 110, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 195, label: "材料名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 71, label: "数量", sortable: true, align: "right"},
				{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable: true, align: "right"},
	       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 78, label: "套外材料", sortable: true, align: "left"},
            ]  
	});
	Global.JqGrid.initJqGrid("markupModifyServiceDataTable",{
			height:$(document).height()-$("#content-list4").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 110, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 195, label: "材料名称", sortable: true, align: "left"},
				{name: "markup", index: "markup", width: 71, label: "折扣", sortable: true, align: "right"}
            ]  
	});
	 Global.JqGrid.initJqGrid("prePlanAreaDataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "preplanareadescr", index: "preplanareadescr", width: 153, label: "空间名称", sortable: true, align: "left"},
            ]  
	});
})
	
function init(){
	var arr=$(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
	 $.each(arr,function(k,v){
		if(v.qtymodifycount){
			$("#qtyModifyDataTable").addRowData(k+1, v, "last");
		}
		if(v.markupmodifycount){
			$("#markupModifyDataTable").addRowData(k+1, v, "last");
		}
		
	});
	var arr2=$(top.$(topFrame)[0].contentWindow.document.getElementById("serviceDataTable")).jqGrid("getRowData");
	$.each(arr2,function(k,v){
		if(v.qtymodifycount){
			$("#qtyModifyServiceDataTable").addRowData(k+1, v, "last");
		}
		if(v.markupmodifycount){
			$("#markupModifyServiceDataTable").addRowData(k+1, v, "last");
		}
		
	});
	if (arr||arr2){
		var flagDif=true;
		if('${arryPrePlanAreaDescr}'){
			var arryPrePlanAreaDescr =JSON.parse('${arryPrePlanAreaDescr}');
			if(arryPrePlanAreaDescr.length>0){
				for(var i=0;i<arryPrePlanAreaDescr.length;i++){
					if (arr){
						$.each(arr,function(k,v){
							if(arryPrePlanAreaDescr[i] == v.preplanareadescr){ 
					           flagDif=false;
					           return false;
					      	}
						});
					}
					if (arr2){
						if(!flagDif){
							$.each(arr2,function(k,v){
								if(arryPrePlanAreaDescr[i] == v.preplanareadescr){
							         flagDif=false;
							        return false;
								 }
							}); 
						}
					}
					if(flagDif){ 
						Global.JqGrid.addRowData("prePlanAreaDataTable",{ preplanareadescr:arryPrePlanAreaDescr[i]}); 	
					}else{
						flagDif=true;
					}		
			    };  
			}
		 }
	}
}	
function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
	    doExcelNow('主材预算数量修改','qtyModifyDataTable');
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
	     doExcelNow('主材预算折扣修改','markupModifyDataTable');
	}else if  ($("ul li a[href='#tab3']").parent().hasClass("active")){
	     doExcelNow('服务性产品预算数量修改','qtyModifyServiceDataTable');
	}else if  ($("ul li a[href='#tab4']").parent().hasClass("active")){
	     doExcelNow('服务性产品预算折扣修改','markupModifyServiceDataTable');
	}
}
</script>
</head>
<body onload="init()">
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="doExcel()">导出excel</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
		</form>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab">主材数量修改</a></li>
				<li id="tab2_li" class=""><a href="#tab2" data-toggle="tab">主材折扣修改</a></li>
				<li id="tab3_li" class=""><a href="#tab3" data-toggle="tab">服务性产品数量修改</a></li>
				<li id="tab4_li" class=""><a href="#tab4" data-toggle="tab">服务性产品折扣修改</a></li>
				<li id="tab5_li" class=""><a href="#tab5" data-toggle="tab">无报价空间</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active">
					<div id="content-list">
						<table id="qtyModifyDataTable"></table>
						<div id="qtyModifyDataTablePager"></div>
					</div>
				</div>
				<div id="tab2" class="tab-pane fade ">
					<div id="content-list2">
						<table id="markupModifyDataTable"></table>
						<div id="markupModifyDataTablePager"></div>
					</div>
				</div>
				<div id="tab3" class="tab-pane fade ">
					<div id="content-list3">
						<table id="qtyModifyServiceDataTable"></table>
						<div id="qtyModifyServiceDataTablePager"></div>
					</div>
				</div>
				<div id="tab4" class="tab-pane fade ">
					<div id="content-list4">
						<table id="markupModifyServiceDataTable"></table>
						<div id="markupModifyServiceDataTablePager"></div>
					</div>
				</div>
				<div id="tab5" class="tab-pane fade ">
					<div id="content-list5">
						<table id="prePlanAreaDataTable"></table>
						<div id="prePlanAreaDataTable"></div>
					</div>
				</div>
				
			</div>
		</div>

	</div>
</body>
</html>


