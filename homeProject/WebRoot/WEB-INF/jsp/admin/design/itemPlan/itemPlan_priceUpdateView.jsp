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
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
if(top.$("#iframe_itemPlan_jcys")[0]){
    var topFrame="#iframe_itemPlan_jcys";
}else if(top.$("#iframe_itemPlan_jcysTC")[0]){
    var topFrame="#iframe_itemPlan_jcysTC";
}else if(top.$("#iframe_itemPlan_jcys_confirm")[0]){
    var topFrame="#iframe_itemPlan_jcys_confirm";
}else if(top.$("#iframe_itemPlan_jcysTC_confirm")[0]){
    var topFrame="#iframe_itemPlan_jcysTC_confirm";
}

$(function(){
	
	 Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "baseitemcode", index: "baseitemcode", width: 110, label: "基础项目编号", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 195, label: "基础项目名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 71, label: "数量", sortable: true, align: "right"},
				{name: "tempunitprice", index: "oldunitprice", width: 93, label: "原人工单价", sortable: true, align: "right"},
				{name: "unitprice", index: "unitprice", width: 78, label: "人工单价", sortable: true, align: "right"},
				{name: "tempmaterial", index: "oldmaterial", width: 90, label: "原材料单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 78, label: "材料单价", sortable: true, align: "right"}
            ]  
	});
	Global.JqGrid.initJqGrid("qtyUpdateDataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name: "fixareadescr", index: "fixareadescr", width: 153, label: "区域名称", sortable: true, align: "left"},
				{name: "baseitemcode", index: "baseitemcode", width: 110, label: "基础项目编号", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 195, label: "基础项目名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 71, label: "数量", sortable: true, align: "right"},
				{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable: true, align: "right"},
	       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
	       		{name: "isoutsetdescr", index: "isoutsetdescr", width: 78, label: "套外材料", sortable: true, align: "left"},
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
	if (arr.length>0){
		$.each(arr,function(k,v){
			if(v.count){
				$("#dataTable").addRowData(k+1, v, "last");
			}
			if(v.qtyupdatecount){
				$("#qtyUpdateDataTable").addRowData(k+1, v, "last");
			}
		})
		if('${arryPrePlanAreaDescr}'){
			var difFlag=true;
			var arryPrePlanAreaDescr =JSON.parse('${arryPrePlanAreaDescr}');
			if(arryPrePlanAreaDescr.length>0){
				for(var i=0;i<arryPrePlanAreaDescr.length;i++){
					$.each(arr,function(k,v){
						  if(arryPrePlanAreaDescr[i] ==v.fixareadescr){
					            difFlag=false;
					           return false;
					      }
					});
					if (difFlag){
						 Global.JqGrid.addRowData("prePlanAreaDataTable",{ preplanareadescr:arryPrePlanAreaDescr[i]});
					}else{
						difFlag=true;
					}				
			    };    
		    }
	 	 }
	}	
}

function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
	    doExcelNow('预算明细单价修改','dataTable')
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
	     doExcelNow('预算明细数量修改','qtyUpdateDataTable')
	}
}
</script>
</head>
<body onload="init()">
<div class="body-box-form">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "  onclick="doExcel()">导出excel</button>
      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>
	 <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
		    <input type="hidden" name="jsonString" value="" />
		   </form>
		   <div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >单价修改明细</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab" >数量修改明细</a></li>
		        <li id="tab3_li"  class=""><a href="#tab3" data-toggle="tab" >无报价空间</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "dataTable" ></table>
						<div id="dataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "qtyUpdateDataTable" ></table>
					   <div id="qtyUpdateDataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade " > 
					<div id="content-list3">
					  <table id= "prePlanAreaDataTable" ></table>
					   <div id="PrePlanAreaTablePager"></div>
				   </div> 
				</div>

			</div>	
		</div>
	</div>
</body>
</html>


