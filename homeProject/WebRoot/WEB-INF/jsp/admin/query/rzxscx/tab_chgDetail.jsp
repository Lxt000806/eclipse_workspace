<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();
	var gridOption = {
		url:"${ctx}/admin/rzxscx/goChgDetailGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "no", index: "no", width: 111, label: "增减单号", sortable: true, align: "left"},
			{name: "address", index: "address", width: 145, label: "楼盘", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 75, label: "业务员", sortable: true, align: "left"},
			{name: "businessmandepartment", index: "businessmandepartment", width: 75, label: "业务部门", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 75, label: "设计师", sortable: true, align: "left"},
			{name: "designmandepartment", index: "designmandepartment", width: 75, label: "设计部门", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 85, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left",hidden:'JC'!='${itemChg.itemType1}'?true:false},
			{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right", sum: true},
			{name: "uom", index: "uom", width: 55, label: "单位", sortable: true, align: "left"},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 90, label: "套餐外材料", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 65, label: "成本", sortable: true, align: "right"},
			{name: "beflineamount", index: "beflineamount", width: 85, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "right",},
			{name: "tmplineamount", index: "tmplineamount", width: 65, label: "小计", sortable: true, align: "right"},
			{name: "processcost", index: "processcost", width: 75, label: "其它费用", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 65, label: "总价", sortable: true, align: "right", sum: true},
			{name: "totalcost", index: "totalcost", width: 65, label: "总成本", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 130, label: "备注", sortable: true, align: "left"},
			{name: "supplierdescr", index: "supplierdescr", width: 90, label: "供应商", sortable: true, align: "left"}
		],
		gridComplete:function(){
			var total=0;
			var markup = Global.JqGrid.allToJson("dataTable_chgDetail","markup");
			arr = markup.fieldJson.split(",");	
			for(var i = 0;i < arr.length; i++){
				total=total+parseInt(arr[i]);
			}
			if(total>=0){
				$("#dataTable_chgDetail").footerData("set",{"markup":total/arr.length+"%"},false);
			}else{
				$("#dataTable_chgDetail").footerData("set",{"unitprice":""},false);
			}
		}
	};
	Global.JqGrid.initJqGrid("dataTable_chgDetail",gridOption);
	function DiyFmatter (cellvalue, options, rowObject){ 
	    return cellvalue+"%";
	}
});
</script>
<div class="body-box-list" style="margin-top:0px;">
	<div id="content-list">
		<table id="dataTable_chgDetail"></table>
		<table id="dataTable_chgDetailPager"></table>
	</div>
</div>




