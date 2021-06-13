<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>预算差异分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
function doExcel(){
    if ($("ul li a[href='#tab1']").parent().hasClass("active")){
		doExcelNow('${customer.address}'+'-基础预算差异','baseDataTable', 'page_form');
	}else if  ($("ul li a[href='#tab2']").parent().hasClass("active")){
		doExcelNow('${customer.address}'+'-主材预算差异','mainDataTable', 'page_form');
	}else if  ($("ul li a[href='#tab3']").parent().hasClass("active")){
	    doExcelNow('${customer.address}'+'-服务性产品预算差异','serviceDataTable', 'page_form');
	}else if  ($("ul li a[href='#tab3']").parent().hasClass("active")){
	    doExcelNow('${customer.address}'+'-服务性产品预算差异','planAreaDataTable', 'page_form');
	}		
}
  
$(function(){ 
	
	Global.JqGrid.initJqGrid("baseDataTable",{
		height:550-$("#content-list").offset().top,
		rowNum:10000,
		pager :'1', 
		width:1230,
		colModel : [
			{name: "ordername", index: "ordername", width: 107, label: "排序名称", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 80, label: "区域名称", sortable: true, align: "left",count:true,},
			{name: "baseitemcode", index: "baseitemcode", width: 80, label: "基础项目编号", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 220, label: "基础项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "oldqty", index: "oldqty", width: 75, label: "原数量", sortable: true, align: "right"},
			{name: "qty", index: "qty", width: 75, label: "新数量", sortable: true, align: "right"},
			{name: "chgqty", index: "chgqty", width: 75, label: "变更数量", sortable: true, align: "right"},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 70, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 70, label: "材料单价", sortable: true, align: "right"},
			{name: "chglineamount", index: "chglineamount", width: 80, label: "变更金额", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "remark", index: "remark", width: 250, label: "备注", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
       	],
        grouping : true , // 是否分组,默认为false
		groupingView : {
			groupField : [ 'ordername' ], // 按照哪一列进行分组
			groupColumnShow : [ false ], // 是否显示分组列
			groupText : ['<b>{0}({1})</b>' ], // 表头显示的数据以及相应的数据量
			groupCollapse : false , // 加载数据时是否只显示分组的组信息
			groupDataSorted : true , // 分组中的数据是否排序
			groupOrder : [ 'asc' ], // 分组后的排序
			groupSummary : [ true ], // 是否显示汇总.如果为true需要在colModel中进行配置
			summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
			summaryTpl : '<b>Max: {0}</b>' ,
			showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		
		},
	});
	Global.JqGrid.initJqGrid("mainDataTable",{
			height:550-$("#content-list").offset().top,
			width:1230,
		    rowNum:100000,  
			pager :'1',    
			colModel : [
				{name: "fixareapk", index: "fixareapk", width: 110, label: "区域名称", sortable:false, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 120, label: "区域名称", sortable:false, align: "left"},
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable:false, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 170, label: "材料名称", sortable:false, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "oldprojectqty", index: "oldprojectqty", width: 90, label: "原预估施工量", sortable:false, align: "right",sum: true},
				{name: "projectqty", index: "projectqty", width: 90, label: "新预估施工量", sortable:false, align: "right",sum: true},
				{name: "oldqty", index: "oldqty", width: 70, label: "原数量", sortable:false, align: "right",sum: true},
				{name: "qty", index: "qty", width: 70, label: "新数量", sortable:false, align: "right",sum: true},
				{name: "chgqty", index: "chgqty", width: 70, label: "变更数量", sortable:false, align: "right",sum: true},
				{name: "uom", index: "uom", width: 50, label: "单位", sortable:false, align: "left"},
				{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable:false, align: "right",editable:true},
				{name: "chglineamount", index: "chglineamount", width: 80, label: "变更金额", sortable:false, align: "right", sum: true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable:false, align: "left", hidden: true},
				{name: "remark", index: "remark", width: 140, label: "材料描述", sortable:false, align: "left"},
				{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
				{name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable:false, align: "left",hidden:true},
            ]
	});
	 Global.JqGrid.initJqGrid("serviceDataTable",{
		postData:$("#page_form").jsonForm(),
		height:500-$("#content-list2").offset().top-60,
	    width:1230,
	    rowNum:100000,  
		pager :'1',   
		colModel : [
		  	{name: "fixareapk", index: "fixareapk", width: 110, label: "区域名称", sortable:false, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 120, label: "区域名称", sortable:false, align: "left"},
				{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable:false, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 170, label: "材料名称", sortable:false, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "oldprojectqty", index: "oldprojectqty", width: 90, label: "原预估施工量", sortable:false, align: "right",sum: true},
				{name: "projectqty", index: "projectqty", width: 90, label: "新预估施工量", sortable:false, align: "right",sum: true},
				{name: "oldqty", index: "oldqty", width: 70, label: "原数量", sortable:false, align: "right",sum: true},
				{name: "qty", index: "qty", width: 70, label: "新数量", sortable:false, align: "right",sum: true},
				{name: "chgqty", index: "chgqty", width: 70, label: "变更数量", sortable:false, align: "right",sum: true},
				{name: "uom", index: "uom", width: 50, label: "单位", sortable:false, align: "left"},
				{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable:false, align: "right",editable:true},
				{name: "chglineamount", index: "chglineamount", width: 80, label: "变更金额", sortable:false, align: "right", sum: true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable:false, align: "left", hidden: true},
				{name: "remark", index: "remark", width: 140, label: "材料描述", sortable:false, align: "left"},
				{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
				{name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable:false, align: "left",hidden:true},
         ]
	});
	 Global.JqGrid.initJqGrid("planAreaDataTable",{
	 	url:"${ctx}/admin/itemPlan/getAddPlanAreaJqgrid",
	 	postData:{custCode:"${customer.code }"},
		height:500-$("#content-list2").offset().top-60,
	    width:1230,
	    rowNum:100000,  
		pager :'1',   
		colModel : [
			{name: "p1pk", index: "p1pk", width: 94, label: "pk1", sortable: true, align: "left",hidden:true},
			{name: "p2pk", index: "p2pk", width: 94, label: "pk2", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "isdefaultarea", index: "isdefaultarea", width: 94, label: "isdefaultarea", sortable: true, align: "left",hidden:true},
			{name: "fixareatype", index: "fixareatype", width: 94, label: "fixareatype", sortable: true, align: "left",hidden:true},
			{name: "custcode", index: "custcode", width: 94, label: "custcode", sortable: true, align: "left",hidden:true},
			{name: "pavetype", index: "pavetype", width: 80, label: "铺贴类型", sortable: true, align: "left",hidden:true },
			{name: "fixareatypedescr", index: "fixareatypedescr", width: 80, label: "空间类型", sortable: true, align: "left",},
			{name: "preplanareadescr", index: "preplanareadescr", width: 80, label: "空间名称", sortable: true, align: "left", },
			{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
			{name: "oldarea", index: "oldarea", width: 50, label: "原面积", sortable: true, align: "right", sum:true,},
			{name: "perimeter", index: "perimeter", width: 50, label: "周长", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
			{name: "oldperimeter", index: "oldperimeter", width: 50, label: "原周长", sortable: true, align: "right", sum:true,},
			{name: "height", index: "height", width: 50, label: "高度", sortable: true, align: "right",editable:true,editrules:{number:true}},
			{name: "oldheight", index: "oldheight", width: 50, label: "原高度", sortable: true, align: "right", },
			{name: "length1", index: "length1", width: 50, label: "长", sortable: true, align: "right",editable:true ,editrules:{number:true}},
			{name: "oldlength1", index: "oldlength1", width: 50, label: "原长", sortable: true, align: "right",},
			{name: "height1", index: "height1", width: 50, label: "高", sortable: true, align: "right",editable:true ,editrules:{number:true}},
			{name: "oldheight1", index: "oldheight1", width: 50, label: "原高", sortable: true, align: "right",},
			{name: "length2", index: "length2", width: 50, label: "长", sortable: true, align: "right",editable:true ,editrules:{number:true}},
			{name: "oldlength2", index: "oldlength2", width: 50, label: "原长", sortable: true, align: "right",},
			{name: "height2", index: "height2", width: 50, label: "高", sortable: true, align: "right",editable:true ,editrules:{number:true}},
			{name: "oldheight2", index: "oldheight2", width: 50, label: "原高", sortable: true, align: "right",},
			{name: "pavetypedescr", index: "pavetypedescr", width: 70, label: "铺贴类型", sortable: true, align: "left", },
			{name: "beamlength", index: "beamlength", width: 70, label: "包梁长度", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
			{name: "showerlength", index: "showerlength", width: 70, label: "淋浴房长", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
			{name: "showerwidth", index: "showerwidth", width: 70, label: "淋浴房宽", sortable: true, align: "right", sum:true,editable:true,editrules:{number:true}},
			{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "right", },
		],
		gridComplete:function(){
			var rowData = $("#planAreaDataTable").jqGrid('getRowData');
			if(rowData){
				$.each(rowData,function(k,v){
					if(v.area==v.oldarea && v.perimeter==v.oldperimeter&& v.height==v.oldheight&& 
					   v.length1==v.oldlength1&& v.height1==v.oldheight1&& v.length2==v.oldlength2 && v.height2==v.oldheight2){
						$("#planAreaDataTable").setRowData(k+1,null,{display: 'none'});//隐藏一行
					}
				})
			}	
		},	
	});
	$("#planAreaDataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[{startColumnName: 'length1', numberOfColumns: 4, titleText: '门洞'},
					  {startColumnName: 'length2', numberOfColumns: 4, titleText: '窗洞'},					
		],
	});
	
	getBasePlanDiff();
	getItemPlanDiff();
    
});
function getBasePlanDiff(){
	//MainTempNo
	if('${customer.baseTempNo}'=="") return;
   	$.ajax({
		url:"${ctx}/admin/itemPlan/goBaseItemPlanDiff",
		type:"post",
		dataType:"json",
        data:{tempNo:'${customer.baseTempNo}',custCode:'${customer.code}'},
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
	    },
	    success: function(obj){
		    if(obj.rs){
		  		var params=obj.datas;
		  		$("#baseDataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk'},page:1}).trigger("reloadGrid");    
	  		}else{
	  			art.dialog({
					content: obj.msg
				});
	  		}
		 }
 	});  
} 
function getItemPlanDiff(){
	if('${customer.mainTempNo}'=="") return;
   	$.ajax({
		url:"${ctx}/admin/itemPlan/goItemPlanDiff",
		type:"post",
        dataType:"json",
        data:{tempNo:'${customer.mainTempNo}',custCode:'${customer.code}'},
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
	    },
	    success: function(obj){
		    if(obj.rs){
			    $('#mainDataTable').clearGridData();
			    $('#serviceDataTable').clearGridData();
			    $.each(JSON.parse(obj.datas),function(k,v){
		  			if(v.isservice==1) $('#serviceDataTable').addRowData($('#serviceDataTable').jqGrid('getGridParam','records')+1, v, "last");
		  			else $('#mainDataTable').addRowData($('#mainDataTable').jqGrid('getGridParam','records')+1, v, "last");
		  		})
		  		
	  		}else{
	  			art.dialog({
					content: obj.msg
				});
	  		}
		 }
 	});	  
} 

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="query-form" hidden>
			<form action="" method="post" id="page_form" >
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
		<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	     					<button type="button" class="btn btn-system "   onclick="doExcel()">导出excel</button>
							<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		  </div>
		<div  class="container-fluid" >  
		     <ul class="nav nav-tabs" >
		        <li id="tab1_li" class="active"><a href="#tab1" data-toggle="tab" >基础预算</a></li>  
		        <li id="tab2_li"  class=""><a href="#tab2" data-toggle="tab">主材预算</a></li>
		        <li id="tab3_li"  class=""><a href="#tab3" data-toggle="tab">服务性产品预算</a></li>
		        <li id="tab4_li"  class=""><a href="#tab4" data-toggle="tab">空间明细</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab1"  class="tab-pane fade in active"> 
					<div id="content-list">
						<table id= "baseDataTable" ></table>
						<div id="baseDataTablePager"></div>
					</div> 
				</div>  
	
				<div id="tab2"  class="tab-pane fade " > 
					<div id="content-list2">
					  <table id= "mainDataTable" ></table>
					   <div id="mainDataTablePager"></div>
				   </div> 
				</div>
				<div id="tab3"  class="tab-pane fade " > 
					<div id="content-list3">
					  <table id= "serviceDataTable" ></table>
					   <div id="serviceDataTablePager"></div>
				   </div> 
				</div>
				<div id="tab4"  class="tab-pane fade " > 
					<div id="content-list4">
					  <table id= "planAreaDataTable" ></table>
					   <div id="planAreaDataTablePager"></div>
				   </div> 
				</div>
			</div>	
		</div>
	</div>
</body>
</html>

