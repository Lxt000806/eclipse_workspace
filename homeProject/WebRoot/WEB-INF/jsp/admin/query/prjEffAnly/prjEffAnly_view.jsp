<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>工地效率分析详情页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("prjEffAnlyViewDataTable",{
				url:"${ctx}/admin/PrjEffAnly/goJqGridView",
				postData:{
					dateFrom:"${data.dateFrom}",
					dateTo:"${data.dateTo}",
					department2s:"${data.department2s}",
					custTypes:"${data.custTypes}",
					dept2Code:"${data.dept2Code}",
					custType:"${data.custType}",
					constructType:"${data.constructType}",
					builderCode:"${data.builderCode}"
				},
				height:500,
	        	styleUI: "Bootstrap",
				colModel : [
					{name : "address",index : "address",width : 140,label:"楼盘",sortable : true,align : "left"},
					{name : "projectman",index : "projectman",width : 80,label:"项目经理",sortable : true,align : "left"},
					{name : "custtypedescr",index : "custtypedescr",width : 60,label:"客户类型",sortable : true,align : "left"},
					{name : "constructtypedescr",index : "constructtypedescr",width : 60,label:"施工方式",sortable : true,align : "left"},
					{name : "constructstatusdescr",index : "constructstatusdescr",width : 60,label:"施工状态",sortable : true,align : "left"},
					{name : "delayday",index : "delayday",width : 75,label:"总拖期天数",sortable : true,align : "right"},
					{name : "currprjitem",index : "currprjitem",width : 75,label:"当前阶段",sortable : true,align : "left"},
					{name : "confirmbegin", index : "confirmbegin", width : 85, label : "实际开工日期", sortable : true, align : "left", formatter : formatTime},
					{name : "sdplanend", index : "sdplanend", width : 85, label : "计划结束日期", sortable : true, align : "left", formatter : formatTime},
					{name : "sdconfirmdate", index : "sdconfirmdate", width : 75, label : "验收日期", sortable : true, align : "left", formatter : formatTime},
					{name : "nssmplanend", index : "nssmplanend", width : 85, label : "计划结束日期", sortable : true, align : "left", formatter : formatTime},
					{name : "nssmconfirmdate", index : "nssmconfirmdate", width : 75, label : "验收日期", sortable : true, align : "left", formatter : formatTime},
					{name : "gddmplanend", index : "gddmplanend", width : 85, label : "计划结束日期", sortable : true, align : "left", formatter : formatTime},
					{name : "gddmconfirmdate", index : "gddmconfirmdate", width : 75, label : "验收日期", sortable : true, align : "left", formatter : formatTime},
					{name : "jgysplanend", index : "jgysplanend", width : 85, label : "计划结束日期", sortable : true, align : "left", formatter : formatTime},
					{name : "jgysconfirmdate", index : "jgysconfirmdate", width : 75, label : "验收日期", sortable : true, align : "left", formatter : formatTime}
	            ]
			});
			$("#prjEffAnlyViewDataTable").jqGrid("setGroupHeaders", {
				useColSpanStyle: true, 
				groupHeaders:[
					{startColumnName: "sdplanend", numberOfColumns:2, titleText:"水电预埋"},
					{startColumnName: "nssmplanend", numberOfColumns:2, titleText:"泥水饰面"},
					{startColumnName: "gddmplanend", numberOfColumns:2, titleText:"刮底打磨"},
					{startColumnName: "jgysplanend", numberOfColumns:2, titleText:"竣工验收"}
				] 
			});
		});
	</script>
	</head>
	    
	<body>
		<table id="prjEffAnlyViewDataTable"></table>
		<div id="prjEffAnlyViewDataTablePager"></div>
	</body>
</html>
