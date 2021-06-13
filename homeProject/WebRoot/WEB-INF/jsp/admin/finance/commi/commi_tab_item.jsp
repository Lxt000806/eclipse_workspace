<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#itemDataTablePager{width:auto!important}
</style>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/commi/goItemJqGrid",
			postData:{no:"${no}"},
			height:350,
			//rowNum:10000000,
			colModel : [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "designman", index: "designman", width: 70, label: "设计师", sortable: true, align: "left",hidden:true},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "designmandept", index: "designmandept", width: 80, label: "设计师部门", sortable: true, align: "left",hidden:true},
				{name: "designmandeptdescr", index: "designmandeptdescr", width: 80, label: "设计师部门", sortable: true, align: "left"},
				{name: "scenedesignman", index: "scenedesignman", width: 80, label: "现场设计师", sortable: true, align: "left",hidden:true},
				{name: "scenedesignmandescr", index: "scenedesignmandescr", width: 80, label: "现场设计师", sortable: true, align: "left"},
				{name: "scenedesignmandept", index: "scenedesignmandept", width: 105, label: "现场设计师部门", sortable: true, align: "left",hidden:true},
				{name: "scenedesignmandeptdescr", index: "scenedesignmandeptdescr", width: 105, label: "现场设计师部门", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 70, label: "区域名称", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left",hidden:true},
				{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2", index: "itemtype2", width: 70, label: "材料类型2", sortable: true, align: "left",hidden:true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 70, label: "材料类型2", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right"},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left",hidden:true},
				{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right"},
				{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right"},
				{name: "costamount", index: "costamount", width: 70, label: "成本总价", sortable: true, align: "right",summaryType:'sum',sum:true},
				{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right"},
				{name: "markup", index: "markup", width: 70, label: "折扣", sortable: true, align: "right"},
				{name: "tmplineamount", index: "tmplineamount", width: 70, label: "小计", sortable: true, align: "right",summaryType:'sum',sum:true},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right",summaryType:'sum',sum:true},
				{name: "remark", index: "remark", width: 200, label: "材料描述", sortable: true, align: "left"},
				{name: "profit", index: "profit", width: 70, label: "毛利", sortable: true, align: "right"},
				{name: "profitper", index: "profitper", width: 70, label: "毛利率", sortable: true, align: "right",formatter:function(cellvalue, options, rowObject){return myRound(cellvalue*100,2)+"%";}},
				{name: "commitypedescr", index: "commitypedescr", width: 90, label: "提成类型", sortable: true, align: "left"},
				{name: "commitype", index: "commitype", width: 70, label: "提成类型", sortable: true, align: "left",hidden:true},
				{name: "commiperc", index: "commiperc", width: 70, label: "提成点", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成额", sortable: true, align: "right",sum:true},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
			],
	/* 		grouping : true , // 是否分组,默认为false
			groupingView : {
				groupField : [ 'address'], // 按照哪一列进行分组
				groupColumnShow : [ false], // 是否显示分组列
				groupText : ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
				groupCollapse : true , // 加载数据时是否只显示分组的组信息
				groupDataSorted : false , // 分组中的数据是否排序
				groupOrder : [ 'asc'], // 分组后的排序
				groupSummary : [ true], // 是否显示汇总.如果为true需要在colModel中进行配置
				summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
				summaryTpl : '<b>Max: {0}</b>' ,
				showSummaryOnHide : false //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
			}, */
			ondblClickRow:function(){
				view();
	        },
	  /*    gridComplete:function(){
	         	//修复分组合计js小数计算异常
	        	$(".jqfoot").each(function(i){
	        		var sumCostAmount= myRound($(this).children("td[aria-describedby=itemDataTable_costamount]").text(),2);
	        		var sumTmpLineAmount= myRound($(this).children("td[aria-describedby=itemDataTable_tmplineamount]").text(),2);
	        		var sumLineAmount= myRound($(this).children("td[aria-describedby=itemDataTable_lineamount]").text(),2);
	        		$(this).children("td[aria-describedby=itemDataTable_costamount]").text(sumCostAmount);
	        		$(this).children("td[aria-describedby=itemDataTable_tmplineamount]").text(sumTmpLineAmount);
	        		$(this).children("td[aria-describedby=itemDataTable_lineamount]").text(sumLineAmount);
	        	});
	        } */	 
 		};
	   Global.JqGrid.initJqGrid("itemDataTable",gridOption);
});


</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="itemDataTable"></table>
		<div id="itemDataTablePager"></div>
	</div>
</div>



