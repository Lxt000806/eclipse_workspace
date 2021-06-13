<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();

	var gridOption = {
		url:"${ctx}/admin/rzxscx/goItemReqJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		rowNum:10000000,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "address", index: "address", width: 145, label: "楼盘", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 75, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 80, label: "集成成品", sortable: true, align: "left",hidden:'JC'!='${itemChg.itemType1}'?true:false},
			{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "qty", index: "qty", width: 65, label: "数量", sortable: true, align: "right", sum: true},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套餐外材料", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right",avg: true},
			{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right", hidden: true},
			{name: "beflineamount", index: "beflineamount", width: 85, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "tmplineamount", index: "tmplineamount", width: 60, label: "小计", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 65, label: "折扣", sortable: true, align: "right"},
			{name: "processcost", index: "processcost", width: 65, label: "其他费用", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 65, label: "总价", sortable: true, align: "right", sum: true},
			{name: "totalcost", index: "totalcost", width: 65, label: "总成本", sortable: true, align: "right", sum: true},
			{name: "remark", index: "remark", width: 130, label: "材料描述", sortable: true, align: "left"}
		],
		gridComplete:function(){
			var total=0;
			var markup = Global.JqGrid.allToJson("dataTable_itemReq","markup");
			arr = markup.fieldJson.split(",");	
			for(var i = 0;i < arr.length; i++){
				total=total+parseInt(arr[i]);
			}
			if(total>=0){
	   	   		$("#dataTable_itemReq").footerData("set",{"markup":myRound(total/arr.length,2)+"%"},false);
			}
		}
	};
	Global.JqGrid.initJqGrid("dataTable_itemReq",gridOption);
	function DiyFmatter (cellvalue, options, rowObject){ 
	    return cellvalue+"%";
	}
});
</script>
<div class="body-box-list" style="margin-top:0px;">
	<div id="content-list">
		<table id="dataTable_itemReq" style="overflow:auto;"></table>
	</div>
</div>




