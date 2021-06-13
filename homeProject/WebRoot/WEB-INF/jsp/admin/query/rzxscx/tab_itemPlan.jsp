<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();
	var gridOption = {
		url:"${ctx}/admin/rzxscx/goItemPlanJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		rowNum:10000000,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "address", index: "address", width: 145, label: "楼盘", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 75, label: "业务员", sortable: true, align: "left"},
			{name: "businessmandepartment", index: "businessmandepartment", width: 75, label: "业务部门", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 75, label: "设计师", sortable: true, align: "left"},
			{name: "designmandepartment", index: "designmandepartment", width: 75, label: "设计部门", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 75, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 80, label: "集成成品", sortable: true, align: "left",hidden:'JC'!='${itemChg.itemType1}'?true:false},
			{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "projectqty", index: "projectqty", width: 75, label: "预估施工量", sortable: true, align: "right", sum: true},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 90, label: "套餐外材料", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right",avg:true},
			{name: "cost", index: "cost", width: 65, label: "成本", sortable: true, align: "right", hidden: true},
			{name: "beflineamount", index: "beflineamount", width: 85, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "tmplineamount", index: "tmplineamount", width: 85, label: "小计", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 65, label: "折扣", sortable: true, align: "right",formatter:DiyFmatter},
			{name: "processcost", index: "processcost", width: 65, label: "其他费用", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 65, label: "总价", sortable: true, align: "right", sum: true},
			{name: "totalcost", index: "totalcost", width: 65, label: "总成本", sortable: true, align: "right", sum: true},
			{name: "remark", index: "remark", width: 130, label: "材料描述", sortable: true, align: "left"},
			{name: "supplierdescr", index: "supplierdescr", width: 90, label: "供应商", sortable: true, align: "left"} 
		],
		gridComplete:function(){
			var total=0;
			var markup = Global.JqGrid.allToJson("dataTable_itemPlan","markup");
			arr = markup.fieldJson.split(",");	
			for(var i = 0;i < arr.length; i++){
				total=total+parseInt(arr[i]);
			}
			if(total>=0){
	   	   		$("#dataTable_itemPlan").footerData("set",{"markup":total/arr.length+"%"},false);
	   	   	}else{
	   	   		$("#dataTable_itemPlan").footerData("set",{"unitprice":" "},false);
	   	   	}
		}
	};
	Global.JqGrid.initJqGrid("dataTable_itemPlan",gridOption);
	function DiyFmatter (cellvalue, options, rowObject){ 
	    return cellvalue+"%";
	}
});
</script>
<div class="body-box-list" style="margin-top:0px;">
	<div id="content-list">
		<table id="dataTable_itemPlan"></table>
	</div>
</div>




