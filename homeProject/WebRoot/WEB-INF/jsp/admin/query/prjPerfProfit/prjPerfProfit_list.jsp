<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工地结算利润分析</title>
	<link href="${resourceRoot}/jqGrid/5.0.0/plugins/ui.multiselect.css?v=${v}" type="text/css" rel="stylesheet"/>
	<!-- <script src="${resourceRoot}/jqGrid/5.0.0/plugins/ui.multiselect.js?v=${v}" type="text/javascript"></script> -->
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.checkbox-label {
			text-align: left !important;
			margin-left: -3px !important;
		}
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
	<script type="text/javascript" defer>
		// 使用新的关于ColumnChooserjs
		useNewMultiselect();
		useNewColumnChooser();
		var formatSum2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		format2Percent = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
		var original_colModel_1 = [
			{name: "address", index: "address", excelname: "楼盘", width: 120, label: "楼盘", align: "left", frozen: true, count: true},
			{name: "custcode", index: "custcode", excelname: "客户编号", width: 70, label: "客户编号", align: "left", frozen: true},
			{name: "documentno", index: "documentno", excelname: "档案编号", width: 70, label: "档案编号", align: "left", frozen: true},
			{name: "custtypedescr", index: "custtypedescr", excelname: "客户类型", width: 70, label: "客户类型", align: "left", frozen: true},
			{name: "area", index: "area", excelname: "面积", width: 50, label: "面积", align: "right", frozen: true, sum: true},
			{name: "innerarea", index: "innerarea", excelname: "套内面积", width: 80, label: "套内面积", align: "right", frozen: true, sum: true},
			{name: "checkstatusdescr", index: "checkstatusdescr", excelname: "结算状态", width: 80, label: "结算状态", align: "left"},
			{name: "constructstatusdescr", index: "constructstatusdescr", excelname: "施工状态", width: 80, label: "施工状态", align: "left"},
			{name: "custcheckdate", index: "custcheckdate", excelname: "结算时间", width: 80 , label: "结算时间", align: "left", formatter:formatDate},
			{name: "constructtypedescr", index: "constructtypedescr", excelname: "施工方式", width: 70, label: "施工方式", align: "left"},
			{name: "contractfee", index: "contractfee", excelname: "签单合同总额", width: 90, label: "签单合同总额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "designfee", index: "designfee", excelname: "设计费", width: 60, label: "设计费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "tax", index: "tax", excelname: "税金", width: 60, label: "税金", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "basefee", index: "basefee", excelname: "基础费", width: 60, label: "基础费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainmanagefee", index: "mainmanagefee", excelname: "主材管理费（含主材、服务性）", width: 90, label: "主材管理费（含主材、服务性）", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intmanagefee", index: "intmanagefee", excelname: "集成管理费（含集成、橱柜）", width: 90, label: "集成管理费（含集成、橱柜）", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softmanagefee", index: "softmanagefee", excelname: "软装管理费", width: 90, label: "软装管理费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "managefee", index: "managefee", excelname: "管理费（含远程费）", width: 70, label: "管理费（含远程费）", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "basedisc", index: "basedisc", excelname: "协议优惠", width: 70, label: "协议优惠", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainfee", index: "mainfee", excelname: "主材费", width: 60, label: "主材费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "integratefee", index: "integratefee", excelname: "集成费", width: 60, label: "集成费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cupboardfee", index: "cupboardfee", excelname: "橱柜费", width: 60, label: "橱柜费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softfee", index: "softfee", excelname: "软装费", width: 60, label: "软装费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainservfee", index: "mainservfee", excelname: "服务性产品费", width: 100, label: "服务性产品费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "haspay", index: "haspay", excelname: "已付款", width: 60, label: "已付款", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "totalcost", index: "totalcost", excelname: "发包总成本", width: 90, label: "发包总成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cmptotalcost", index: "cmptotalcost", excelname: "公司总成本", width: 90, label: "公司总成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "custbasectrl", index: "custbasectrl", excelname: "发包额", width: 60, label: "发包额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "prjwithhold", index: "prjwithhold", excelname: "项目经理预扣", width: 100, label: "项目经理预扣", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "excludedprjcost", index: "excludedprjcost", excelname: "不计项目经理成本", width: 100, label: "不计项目经理成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cmpbasecost", index: "cmpbasecost", excelname: "公司基础成本", width: 100, label: "公司基础成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "maincost", index: "maincost", excelname: "主材成本", width: 70, label: "主材成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "outsetmaincost", index: "outsetmaincost", excelname: "套餐外主材成本（含服务性成本）", width: 110, label: "套餐外主材成本（含服务性成本）", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "servicecost", index: "servicecost", excelname: "服务性成本", width: 90, label: "服务性成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intcost", index: "intcost", excelname: "集成成本", width: 70, label: "集成成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softcost", index: "softcost", excelname: "软装成本", width: 70, label: "软装成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "frontendriskfundexpense", index: "frontendriskfundexpense", excelname: "前端风险金支出", width: 100, label: "前端风险金支出", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "grossprofitmargin", index: "grossprofitmargin", excelname: "发包毛利率", width: 85, label: "发包毛利率", sorttype: "number", align: "right", formatter:"number", formatoptions:format2Percent, avg: true,
					title:"发包毛利率=(已付款-设计费-发包额-主材成本-集成成本-软装成本-前端风险金支出)/(已付款-设计费)"},
			{name: "cmpgrossprofitmargin", index: "cmpgrossprofitmargin", excelname: "公司毛利率", width: 85, label: "公司毛利率", sorttype: "number", align: "right", formatter:"number", formatoptions:format2Percent, avg: true,
					title:"公司毛利率=(已付款-设计费-公司总成本)/(已付款-设计费)"},
		];
		var original_colModel_2 = [
			{name: "baseitemperlabor", index: "baseitemperlabor", excelname: "基础|基础结算人工", width: 95, label: "基础结算人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "baseitemperfcost", index: "baseitemperfcost", excelname: "基础|材料结算成本", width: 95, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "baseitemlabor", index: "baseitemlabor", excelname: "基础|人工成本", width: 70, label: "人工成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "baseitemcost", index: "baseitemcost", excelname: "基础|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemperfcost01", index: "itemperfcost01", excelname: "拆除及其他|材料结算成本", width: 90, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true, sum: true},
			{name: "itemcost01", index: "itemcost01", excelname: "拆除及其他|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "simulationitemcost01", index: "simulationitemcost01", excelname: "拆除及其他|模拟放样材料成本", width: 90, label: "模拟放样材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "simulationitemperfcost01", index: "simulationitemperfcost01", excelname: "拆除及其他|模拟放样材料结算成本", width: 110, label: "模拟放样材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "hygienefee01", index: "hygienefee01", excelname: "拆除及其他|卫生费", width: 60, label: "卫生费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "finishproductprotection01", index: "finishproductprotection01", excelname: "拆除及其他|成品保护", width: 70, label: "成品保护", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "secondhandling01", index: "secondhandling01", excelname: "拆除及其他|二次搬运", width: 70, label: "二次搬运", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "demolitionlabor01", index: "demolitionlabor01", excelname: "拆除及其他|拆除人工", width: 70, label: "拆除人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "simulationlabor01", index: "simulationlabor01", excelname: "拆除及其他|模拟放样人工", width: 90, label: "模拟放样人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "manned01", index: "manned01", excelname: "拆除及其他|已领人工", width: 70, label: "已领人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "recvfeefixduty01", index: "recvfeefixduty01", excelname: "拆除及其他|已领定责", width: 70, label: "已领定责", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "lateprotect01", index: "lateprotect01", excelname: "拆除及其他|后期成品保护", width: 90, label: "后期成品保护", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemperfcost02", index: "itemperfcost02", excelname: "水电|材料结算成本", width: 90, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemcost02", index: "itemcost02", excelname: "水电|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "labor02", index: "labor02", excelname: "水电|水电人工", width: 70, label: "水电人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "setlabor02", index: "setlabor02", excelname: "水电|后期安装人工", width: 90, label: "后期安装人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemrorp02", index: "itemrorp02", excelname: "水电|水电材料奖惩", width: 90, label: "水电材料奖惩", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "fixduty02", index: "fixduty02", excelname: "水电|定责工资", width: 70, label: "定责工资", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "slottinglabor02", index: "slottinglabor02", excelname: "水电|开槽人工", width: 70, label: "开槽人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "healthlabor02", index: "healthlabor02", excelname: "水电|卫生人工", width: 70, label: "卫生人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemperfcost03", index: "itemperfcost03", excelname: "土建|材料结算成本", width: 90, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemcost03", index: "itemcost03", excelname: "土建|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "protitemperfcost03", index: "protitemperfcost03", excelname: "土建|防水材料结算成本", width: 120, label: "防水材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "protitemcost03", index: "protitemcost03", excelname: "土建|防水材料成本", width: 90, label: "防水材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "walllabor03", index: "walllabor03", excelname: "土建|砌墙人工", width: 70, label: "砌墙人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "finishlabor03", index: "finishlabor03", excelname: "土建|饰面人工", width: 70, label: "饰面人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "waterprooflabor03", index: "waterprooflabor03", excelname: "土建|防水人工", width: 70, label: "防水人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "waterproofsubsidy03", index: "waterproofsubsidy03", excelname: "土建|防水饰面补贴", width: 90, label: "防水饰面补贴", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "levelinglabor03", index: "levelinglabor03", excelname: "土建|找平人工", width: 70, label: "找平人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "fixduty03", index: "fixduty03", excelname: "土建|定责工资", width: 70, label: "定责工资", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "wallloftinglabor03", index: "wallloftinglabor03", excelname: "土建|墙体放样人工", width: 70, label: "墙体放样人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "makepartylabor03", index: "makepartylabor03", excelname: "土建|找方人工", width: 70, label: "找方人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "seamLabor03", index: "seamLabor03", excelname: "土建|美缝人工", width: 70, label: "美缝人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "groundbeamlabor03", index: "groundbeamlabor03", excelname: "土建|地梁模板人工", width: 70, label: "地梁模板人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemperfcost04", index: "itemperfcost04", excelname: "木作|材料结算成本", width: 90, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemcost04", index: "itemcost04", excelname: "木作|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "woodlabor04", index: "woodlabor04", excelname: "木作|木作人工", width: 70, label: "木作人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "fixduty04", index: "fixduty04", excelname: "木作|定责工资", width: 70, label: "定责工资", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemperfcost05", index: "itemperfcost05", excelname: "油漆|材料结算成本", width: 90, label: "材料结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "itemcost05", index: "itemcost05", excelname: "油漆|材料成本", width: 70, label: "材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "paintlabor05", index: "paintlabor05", excelname: "油漆|油漆人工", width: 70, label: "油漆人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "decoralabor05", index: "decoralabor05", excelname: "油漆|饰面人工", width: 70, label: "饰面人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "screedlabor05", index: "screedlabor05", excelname: "油漆|冲筋人工", width: 70, label: "冲筋人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "fixduty05", index: "fixduty05", excelname: "油漆|定责工资", width: 70, label: "定责工资", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "makelevellabor05", index: "makelevellabor05", excelname: "油漆|找平人工", width: 70, label: "找平人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "repairlabor05", index: "repairlabor05", excelname: "油漆|修补人工", width: 70, label: "修补人工", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainctrl", index: "mainctrl", excelname: "主材控制线", width: 90, label: "主材控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "servctrl", index: "servctrl", excelname: "服务性产品控制线", width: 120, label: "服务性产品控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "tilecost", index: "tilecost", excelname: "主材+服务性产品|瓷砖", width: 60, label: "瓷砖", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "floorcost", index: "floorcost", excelname: "主材+服务性产品|地板", width: 60, label: "地板", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "ceilingcost", index: "ceilingcost", excelname: "主材+服务性产品|吊顶", width: 60, label: "吊顶", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "doorcost", index: "doorcost", excelname: "主材+服务性产品|门", width: 60, label: "门", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "bathcost", index: "bathcost", excelname: "主材+服务性产品|卫浴", width: 60, label: "卫浴", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "stonecost", index: "stonecost", excelname: "主材+服务性产品|石材", width: 60, label: "石材", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "switchcost", index: "switchcost", excelname: "主材+服务性产品|开关", width: 60, label: "开关", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "eleccost", index: "eleccost", excelname: "主材+服务性产品|电器", width: 60, label: "电器", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainothercost", index: "mainothercost", excelname: "主材+服务性产品|其他材料", width: 70, label: "其他材料", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			//{name: "otherfee", index: "otherfee", excelname: "主材+服务性产品|其他费用", width: 70, label: "其他费用", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "zclaberfee", index: "zclaberfee", excelname: "主材+服务性产品|主材人工费", width: 80, label: "主材人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "setitemperfcost", index: "setitemperfcost", excelname: "主材+服务性产品|套内结算成本", width: 90, label: "套内结算成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "setitemcost", index: "setitemcost", excelname: "主材+服务性产品|套内材料成本", width: 90, label: "套内材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "notsetitemcost", index: "notsetitemcost", excelname: "主材+服务性产品|套外材料成本", width: 90, label: "套外材料成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "giftcost", index: "giftcost", excelname: "主材+服务性产品|赠送项目成本", width: 70, label: "赠送项目成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "setlaberfee", index: "setlaberfee", excelname: "主材+服务性产品|套内人工费", width: 90, label: "套内人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "notsetlaberfee", index: "notsetlaberfee", excelname: "主材+服务性产品|套外人工费", width: 80, label: "套外人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "tileline", index: "tileline", excelname: "主材+服务性产品|瓷砖销售额", width: 80, label: "瓷砖销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "floorline", index: "floorline", excelname: "主材+服务性产品|地板销售额", width: 80, label: "地板销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "ceilingline", index: "ceilingline", excelname: "主材+服务性产品|吊顶销售额", width: 80, label: "吊顶销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "doorline", index: "doorline", excelname: "主材+服务性产品|门销售额", width: 70, label: "门销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "bathline", index: "bathline", excelname: "主材+服务性产品|卫浴销售额", width: 80, label: "卫浴销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "stoneline", index: "stoneline", excelname: "主材+服务性产品|石材销售额", width: 80, label: "石材销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "switchline", index: "switchline", excelname: "主材+服务性产品|开关销售额", width: 80, label: "开关销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "elecline", index: "elecline", excelname: "主材+服务性产品|电器销售额", width: 80, label: "电器销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "mainotherline", index: "mainotherline", excelname: "主材+服务性产品|其他材料销售额", width: 90, label: "其他材料销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intctrl", index: "intctrl", excelname: "集成控制线", width: 90, label: "集成控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intpartcost", index: "intpartcost", excelname: "集成|集成成本", width: 70, label: "集成成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intlaber", index: "intlaber", excelname: "集成|集成人工费", width: 80, label: "集成人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "intline", index: "intline", excelname: "集成|集成销售额", width: 80, label: "集成销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cupcost", index: "cupcost", excelname: "集成|橱柜成本", width: 70, label: "橱柜成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cuplaber", index: "cuplaber", excelname: "集成|橱柜人工费", width: 80, label: "橱柜人工费", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "cupline", index: "cupline", excelname: "集成|橱柜销售额", width: 80, label: "橱柜销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softctrl", index: "softctrl", excelname: "软装|软装控制线", width: 90, label: "软装控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softpartcost", index: "softpartcost", excelname: "软装|软装成本", width: 70, label: "软装成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "curtainctrl", index: "curtainctrl", excelname: "软装|窗帘控制线", width: 90, label: "窗帘控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "curtaincost", index: "curtaincost", excelname: "软装|窗帘成本", width: 70, label: "窗帘成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "furniturectrl", index: "furniturectrl", excelname: "软装|家具控制线", width: 90, label: "家具控制线", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "furniturecost", index: "furniturecost", excelname: "软装|家具成本", width: 70, label: "家具成本", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
			{name: "softline", index: "softline", excelname: "软装|销售额", width: 60, label: "销售额", sorttype: "number", align: "right", formatter:"number", formatoptions:formatSum2, sum: true},
		];
		//如果为空就用最初的colModel
		var colModel_1 = original_colModel_1;
		var colModel_2 = original_colModel_2;
		// 读取文件保存的选择列，如果没有就显示最初的
		/*if ("" != '${colModel}') {
			//先对语句处理，否则无法解析
			var colReplace = '${colModel}'.replace(/\\/g,'').replace(/"\[/g,"[").replace(/\]"/g,"]");
			var readArr = eval('('+colReplace+')');
			colModel_1 = readArr.colModel_save_1;
			if (0 != readArr.colModel_save_2.length) colModel_2 = readArr.colModel_save_2;
		}*/
		var colModel = colModel_1;
		//初始化表格
	    var jqGridOption = {
			styleUI: "Bootstrap",
			datatype: "json",
			rowNum: 100000,
			colModel: colModel,
		};
		// 是否已经显示了详细：0-没；1-是
		var haveChanged = "0"; 
		//导出EXCEL
		function doExcelNow(){
			var url = "${ctx}/admin/prjPerfProfit/doExcel";
		 	var pageFormId="page_form";
			var tableId="dataTable";
			var colModel = $("#"+tableId).jqGrid("getGridParam","colModel");
			var rows = $("#"+tableId).jqGrid("getRowData");
			if (!rows || rows.length==0){
				art.dialog({
					content: "无数据导出"
				});
				return;
			}
			var datas = {
				colModel: JSON.stringify(colModel),
				rows: JSON.stringify(rows),
			};
			$.form_submit($("#"+pageFormId).get(0), {
				"action": url,
				"jsonString": JSON.stringify(datas)
			});
		}
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			$.each(colModel, function (i, colVal) {
				if (colVal.name && colVal.sum) {
					refreshSum(colVal.name);
				}
			});
			/*refreshSum("area");refreshSum("innerarea");refreshSum("contractfee");
			refreshSum("designfee");refreshSum("tax");refreshSum("basefee");refreshSum("managefee");
			refreshSum("basedisc");refreshSum("mainfee");refreshSum("integratefee");
			refreshSum("cupboardfee");refreshSum("softfee");refreshSum("mainservfee");
			refreshSum("haspay");refreshSum("totalcost");refreshSum("cmptotalcost");
			refreshSum("prjwithhold");refreshSum("cmpbasecost");refreshSum("custbasectrl");refreshSum("maincost");
			refreshSum("outsetmaincost");refreshSum("servicecost");refreshSum("intcost");
			refreshSum("softcost");*/
			calGPM("totalcost", "grossprofitmargin");calGPM("cmptotalcost", "cmpgrossprofitmargin");

			/*refreshSum("baseitemperlabor");refreshSum("baseitemperfcost");
			refreshSum("baseitemlabor");refreshSum("baseitemcost");
			refreshSum("itemperfcost01");refreshSum("itemcost01");refreshSum("simulationitemcost01");
			refreshSum("simulationitemperfcost01");refreshSum("hygienefee01");
			refreshSum("finishproductprotection01");
			refreshSum("secondhandling01");refreshSum("demolitionlabor01");refreshSum("simulationlabor01");
			refreshSum("manned01");refreshSum("itemperfcost02");refreshSum("itemcost02");
			refreshSum("labor02");refreshSum("setlabor02");refreshSum("itemrorp02");refreshSum("fixduty02");
			refreshSum("itemperfcost03");refreshSum("itemcost03");refreshSum("protitemperfcost03");
			refreshSum("protitemcost03");refreshSum("walllabor03");refreshSum("finishlabor03");
			refreshSum("waterprooflabor03");refreshSum("waterproofsubsidy03");refreshSum("levelinglabor03");
			refreshSum("fixduty03");
			refreshSum("itemperfcost04");refreshSum("itemcost04");refreshSum("woodlabor04");refreshSum("fixduty04");
			refreshSum("itemperfcost05");refreshSum("itemcost05");refreshSum("paintlabor05");
			refreshSum("screedlabor05");refreshSum("fixduty05");refreshSum("tilecost");refreshSum("floorcost");
			refreshSum("ceilingcost");refreshSum("doorcost");refreshSum("bathcost");
			refreshSum("stonecost");refreshSum("switchcost");refreshSum("eleccost");
			refreshSum("mainothercost");refreshSum("otherfee");refreshSum("zclaberfee");
			refreshSum("setitemperfcost");refreshSum("setitemcost");refreshSum("notsetitemcost");
			refreshSum("setlaberfee");refreshSum("notsetlaberfee");refreshSum("tileline");
			refreshSum("floorline");refreshSum("ceilingline");refreshSum("doorline");
			refreshSum("bathline");refreshSum("stoneline");refreshSum("switchline");
			refreshSum("elecline");refreshSum("mainotherline");refreshSum("intpartcost");
			refreshSum("intline");refreshSum("cupcost");refreshSum("cupline");
			refreshSum("intlaber");refreshSum("softpartcost");refreshSum("curtaincost");
			refreshSum("furniturecost");refreshSum("softline");*/
			//显示冻结列的底部汇总栏
			displayFrozenBottom();
		}
		//选择列
		/*function columnChooser() {
			$("#dataTable").jqGrid("columnChooser", { useIndex:true, });
		}*/
		//保存选择列
		/*function columnSave() {
			var savedColModel = $("#dataTable").jqGrid("getGridParam","colModel");
			var colModel_save_1 = [];
			var colModel_save_2 = [];
			var isGetDetail = $("#isGetDetail").val();
			savedColModel.splice(0,1); //删除rn
			if ("[]" == savedColModel) {
				art.dialog({content: "要保存的列为空",width: 220});
				return;
			}
			//当非显示详细时，分开保存
			if ("0" != isGetDetail) {
				$.each(savedColModel,function(i,v){
					$.each(original_colModel_1,function(j,val){
						if (v.name==val.name){
							colModel_save_1.push(v);
						}
					});
					$.each(original_colModel_2,function(k,value){
						if (v.name==value.name){
							colModel_save_2.push(v);
						}
					});
				});
			} else {
				colModel_save_1 = savedColModel;
				colModel_save_2 = colModel_2;
			}
			var datas = {
				"colModel_save_1": JSON.stringify(colModel_save_1),
				"colModel_save_2": JSON.stringify(colModel_save_2),
			};
			// 处理完成后，让现在的表格等于保存的表格
			colModel_1 = colModel_save_1;
			colModel_2 = colModel_save_2;
			$.ajax({
				url:"${ctx}/admin/prjPerfProfit/doSaveCols",
				type: "post",
				data: datas,
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				},
				success: function(obj){
					if (obj.rs) {
						art.dialog({content: obj.msg,width: 220,time: 500});
					} else {
						art.dialog({content: obj.msg,width: 220,});
					}
				}
			});
		}*/
		// 重置列
		/*function columnReset() {
			art.dialog({
				content:"是否重新设置列?",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/prjPerfProfit/doResetCols",
						dataType: "text",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if (!obj) {
					    		art.dialog({content: "重置失败",width: 220});
					    	} else {
					    		colModel_1 = original_colModel_1;
								colModel_2 = original_colModel_2;
								checkbox("#isGetDetail");
								goto_query("dataTable", true);
								art.dialog({content: "重置成功",width: 220,time: 500});
					    	}
					    }
					 });
				},
				cancel: function () {
					return true;
				}
			});	
		}*/
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		//是否显示材料成本信息
		function checkbox(obj) {
			colModel = colModel_1;
			if ($(obj).is(":checked")){
				$(obj).val("1");
				colModel = colModel.concat(colModel_2); //组合1、2数组
			}else{
				$(obj).val("0");
			}
		}
		// 日期格式化到月
		function formatMonth(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var y = date.getFullYear();
				var m = date.getMonth()+1;
			    return y+"-"+(m>9?m:'0'+m)+"-01";
			}
			return "";
		}
		//刷新sum并取两位小数
		function refreshSum(colModel_name) {
			var colModel_name_sum = myRound($("#dataTable").getCol(colModel_name,false,"sum"), 2);
			var sumObj = {}; //json先要用{}定义，再传参
			sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
			$("#dataTable").footerData("set", sumObj);
		}
		//刷新avg并取2位小数
		function refreshAvg(colModel_name) {
			var colModel_name_avg = myRound($("#dataTable").getCol(colModel_name,false,"avg"), 2);
			var avgObj = {};
			avgObj[colModel_name] = colModel_name_avg;
			$("#dataTable").footerData("set", avgObj);
		}
		/*
			用汇总栏数据直接计算‘毛利率’平均值
			calGPM:calculating gross profit margin（计算毛利率）
			totalCostName: 要作为输入的字段名字
			gpmName: 输出计算结果的字段名
		*/
		function calGPM(totalCostName, gpmName) {
			var haspay = $("#dataTable").getCol("haspay",false,"sum");
			var totalcost = $("#dataTable").getCol(totalCostName,false,"sum");
			var grossprofitmargin = haspay == 0 ? 0 : myRound((haspay-totalcost)/haspay*100, 2);
			var obj = {}; 
			obj[gpmName] = grossprofitmargin;//当用参数作为键时，用此方法
			$("#dataTable").footerData("set", obj);
		}
		//刷新表格
		function goto_query(tableId, reloadGrid){
			reloadGrid = reloadGrid===undefined?false:reloadGrid;
			var dataForm = $("#page_form").jsonForm();
			var height = dataForm.isGetDetail==0?63:88;
			if (""==dataForm.custCheckDateFrom && ""==dataForm.custCheckDateTo && ""==dataForm.address) {
				art.dialog({content: "结算日期、楼盘，必填一个",width: 200});
				return;
			}
			if (!tableId || typeof(tableId)!="string"){
				tableId = "dataTable";
			}
			$(".s-ico").css("display","none");
			//当表格没做详细改变时，不重新构建表格
			if (haveChanged == dataForm.isGetDetail && !reloadGrid) {
				$("#dataTable")
					.jqGrid("setGridParam",
						{url: "${ctx}/admin/prjPerfProfit/goJqGrid",postData:dataForm,page:1,sortname:''})
					.trigger("reloadGrid");
			} else {
				$.jgrid.gridUnload("dataTable"); //删除jqGrid
				Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
					url: "${ctx}/admin/prjPerfProfit/goJqGrid",
					postData: dataForm,
					height: $(document).height()-$("#content-list").offset().top-height,
					colModel:colModel,
					page: 1,
					loadComplete: function(){
						refreshSumAndAvg();
						frozenHeightReset("dataTable");
					},
				}));
				if ("0" != dataForm.isGetDetail) {
					$("#dataTable").jqGrid("setGroupHeaders", {
						useColSpanStyle: true, //true:和并列，false:不和并列
						groupHeaders:[
							{startColumnName: "baseitemperlabor", numberOfColumns: 4, titleText: "基础"},
							{startColumnName: "itemperfcost01", numberOfColumns: 12, titleText: "拆除及其他"},
							{startColumnName: "itemperfcost02", numberOfColumns: 8, titleText: "水电"},
							{startColumnName: "itemperfcost03", numberOfColumns: 14, titleText: "土建"},
							{startColumnName: "itemperfcost04", numberOfColumns: 4, titleText: "木作"},
							{startColumnName: "itemperfcost05", numberOfColumns: 8, titleText: "油漆"},
							{startColumnName: "mainctrl", numberOfColumns: 27, titleText: "主材+服务性产品"},
							{startColumnName: "intctrl", numberOfColumns: 7, titleText: "集成"},
							{startColumnName: "softctrl", numberOfColumns: 7, titleText: "软装"},
						]
					});
				}
				$("#dataTable").jqGrid("setFrozenColumns");
			}
			haveChanged = dataForm.isGetDetail;
		}
		$(function(){
			var dateNow = formatMonth(new Date());
			$("#custCheckDateFrom").val(dateNow);
			var postData = $("#page_form").jsonForm();
			postData.checkStatus="3,4";
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				height: $(document).height()-$("#content-list").offset().top-63,
				postData: postData,
				page: 1,
				loadComplete: function(){
					refreshSumAndAvg();
					frozenHeightReset("dataTable");
				},
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#clear").on("click",function(){
				zTreeCheckFalse("custType");
				zTreeCheckFalse("constructType");
				zTreeCheckFalse("checkStatus");
			});
		});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>结算日期从</label>
						<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label for="address">楼盘</label>
						<input type="text" id="address" name="address">
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>施工方式</label>
						<house:xtdmMulit id="constructType" dictCode="CONSTRUCTTYPE"/>
					</li>
					<li>
						<label for="constructStatus">施工状态</label>
						<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS"></house:xtdmMulit>
					</li>
					<li>
						<label>客户结算状态</label>
						<house:xtdmMulit id="checkStatus" dictCode="CheckStatus" selectedValue="3,4"/>
					</li>
					<li>
						<input type="checkbox" id="isGetDetail" name="isGetDetail" onclick="checkbox(this)" value="0" />
						<label class="checkbox-label" for="isGetDetail">显示材料成本详细</label>
					</li>
					<li>
						<input type="checkbox" id="isNotPrint" name="isNotPrint" onclick="checkbox(this)" value="1" checked/>
						<label class="checkbox-label" for="isNotPrint">包含油漆未做</label>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							id="clear" onclick="clearForm();">清空</button>
					</li>		
				</ul>
			</form>
		</div>
		<div class="btn-panel">
	       <div class="btn-group-sm">
                <house:authorize authCode="PRJPERFPROFIT_EXCEL">
                     <button type="button" class="btn btn-system" id="excel" onclick="doExcelNow()">导出excel</button>
				</house:authorize>
				<!-- <button type="button" class="btn btn-system" onclick="columnChooser()">选择列</button>
				<button type="button" class="btn btn-system" onclick="columnSave()">保存选择列</button>
				<button type="button" class="btn btn-system" onclick="columnReset()">重置列</button> -->
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
