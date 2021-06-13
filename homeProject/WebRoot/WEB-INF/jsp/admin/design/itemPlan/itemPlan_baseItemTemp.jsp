<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
    <title>基础模板</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_itemBatchHeader.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}
//tab分页
$(document).ready(function() {  
	var id_detailW=window.parent.document.getElementById("id_detail").style.width.substring(0,4);
	$("#temp").css('width',id_detailW);
    //初始化表格
	Global.JqGrid.initJqGrid("baseItemTempdataTable",{
		url:"${ctx}/admin/baseItemTemp/goJqGrid",
		height:218,
		colModel : [
			{name: "No", index: "No", width: 110, label: "模板编号", sortable: true, align: "left"},
			{name: "Descr", index: "Descr", width: 191, label: "基装项目模板名称", sortable: true, align: "left"},
			{name: "Remark", index: "Remark", width: 237, label: "备注", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 84, label: "类别", sortable: true, align: "left"}
        ],
        grouping : true , // 是否分组,默认为false
		groupingView : {
			groupField : [ 'typedescr' ], // 按照哪一列进行分组
			groupColumnShow : [ false ], // 是否显示分组列
			groupText : [ '<b>类别：{0}({1})</b>' ], // 表头显示的数据以及相应的数据量
			groupCollapse : false , // 加载数据时是否只显示分组的组信息
			groupDataSorted : true , // 分组中的数据是否排序
			groupOrder : [ 'asc' ], // 分组后的排序
			groupSummary : [ false ], // 是否显示汇总.如果为true需要在colModel中进行配置
			summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
			summaryTpl : '<b>Max: {0}</b>' ,
			showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		},
        ondblClickRow: function (rowid, status) {
       		var rowId=  $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
           	var rowData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',rowId);
 		  	//var rowNo=$(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam','records')+1; 
         	var fixAreaPk=rowData.PK;
         	var fixAreaDescr=rowData.Descr;
         	if(!fixAreaDescr){
	         	art.dialog({
					content: "请先选择区域!"
				});
	         	return;
         	}
         	var itemTempData =$("#baseItemTempdataTable").jqGrid('getRowData',rowid);
         	$("#baseItemTempDetaildataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseItemTempDetail/goJqGrid?no="+itemTempData.No+"&custType=${itemPlan.custType}&canUseComBaseItem=${itemPlan.canUseComBaseItem}",page:1}).trigger("reloadGrid");    
        },
		/* gridComplete:function (){
			dataTableCheckBox("baseItemTempdataTable","Descr");
		} */
	});
	Global.JqGrid.initJqGrid("baseItemTempDetaildataTable",{
		height:300,
		rowNum:10000,
		colModel : [
			{name: "baseitemcode", index: "baseitemcode", width: 98, label: "项目编号", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 98, label: "区域", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 331, label: "基础项目", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left",hidden:true},
			{name: "qty", index: "qty", width: 90, label: "数量", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 90, label: "市场价", sortable: true, align: "left",hidden:true},
			{name: "unitprice", index: "unitprice", width: 90, label: "人工单价", sortable: true, align: "left"},
			{name: "material", index: "material", width: 90, label: "材料单价", sortable: true, align: "left"},
			{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "left", sum: true},
			{name: "remark", index: "remark", width: 304, label: "备注", sortable: true, align: "left"},
			{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
			{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
			{name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left"},
        ],
       	gridComplete:function(){
       	 	 var rowId=  $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
           	 var rowData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',rowId);
           //	 var rowNo=$(window.parent.document.getElementById("baseItemPlanDetailDataTable")).jqGrid('getGridParam','records')+1;
           	 var rowNo=parseInt(window.parent.document.getElementById("rowNo").value);
          	 var fixAreaPk=rowData.PK;
          	 var fixAreaDescr=rowData.Descr;
          	 var prePlanAreaPk=rowData.PrePlanAreaPK;
          	 var prePlanAreaDescr=rowData.preplanareadescr;
          	 
     		 var rowData = $("#baseItemTempDetaildataTable").jqGrid("getRowData");
          	 $.each(rowData,function(i,v){
          	 	v.fixareapk=fixAreaPk;
          	    v.fixareadescr=fixAreaDescr; 
          	    v.preplanareapk=prePlanAreaPk;
          	    v.preplanareadescr=prePlanAreaDescr;
          	  	$(window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo+i, v, "last");
           	  	window.parent.document.getElementById("rowNo").value=rowNo+i+1;
          	 })	
         }
	});	
});
	
</script>
  </head>
<body>
	<div>
		<div id="temp" style="float: right;">
			<div>
				<div id="tab1" class="tab_content" style="display: block; ">
					<div class="clear_float"></div>
					<!--query-form-->
					<div class="pageContent">
						<!--panelBar-->
						<div id="content-list">
							<table id="baseItemTempdataTable"></table>
							<div id="baseItemTempdataTablePager"></div>
						</div>
						<div style="display:none">
							<table id="baseItemTempDetaildataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
