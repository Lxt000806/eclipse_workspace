<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>未安排工地</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
			<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#custCode").openComponent_customer();	
		$("#buyer").openComponent_employee();	
		$("#designMan").openComponent_employee();
		Global.JqGrid.initJqGrid("dataTable_emp",{
			url:"${ctx}/admin/softPerf/goReportDetailJqGrid",
			postData:{
			    no:"${softPerf.no}",
			    countType:"${softPerf.countType}",
			    designMan:"${softPerf.designMan}",
			    department2: "${softPerf.department2}",
			    tabName: "DESIGNER"
			},
			height:$(document).height()-$("#content-list").offset().top-130,
			styleUI: "Bootstrap", 
			rowNum:500,
			colModel : [
			  {name : "custcode",index : "custcode",width : 70,label:"客户编号",sortable : true,align : "left"},
			  {name : "custdescr",index : "custdescr",width : 70,label:"客户名称",sortable : true,align : "left"},
			  {name : "address",index : "address",width : 160,label:"楼盘",sortable : true,align : "left"},
			  {name : "typedescr",index : "typedescr",width : 70,label:"发放类型",sortable : true,align : "left"},
			  {name : "designmancommiper",index : "designmancommiper",width : 90,label:"设计师抽成点数",sortable : true,align : "left"},
			  {name : "designper",index : "designper",width : 70,label:"设计师提成",sortable : true,align : "left", sum: true},
			  {name : "prodmgrcommiper",index : "prodmgrcommiper",width : 90,label:"产品经理抽成点数",sortable : true,align : "right"},
			  {name : "prodmgrcommi",index : "prodmgrcommi",width : 90,label:"产品经理提成",sortable : true,align : "right"},
			  {name : "buyercommiper",index : "buyercommiper",width : 90,label:"买手抽成点数",sortable : true,align : "left"},
			  {name : "buyerper",index : "buyerper",width : 70,label:"买手提成",sortable : true,align : "left"},
			  {name : "buyer2commiper",index : "buyer2commiper",width : 90,label:"买手2抽成点数",sortable : true,align : "right"},
			  {name : "buyer2per",index : "buyer2per",width : 70,label:"买手2提成",sortable : true,align : "right"},
			  {name : "profitper",index : "profitper",width : 70,label:"毛利率",sortable : true,align : "right",hidden:${costRight=='1'?false:true}},
			  {name : "lineamount",index : "lineamount",width : 70,label:"总价",sortable : true,align : "right"},
			  {name : "disccost",index : "disccost",width : 70,label:"优惠分摊",sortable : true,align : "right"},
			  {name : "allcost",index : "allcost",width : 70,label:"总成本",sortable : true,align : "right",hidden:${costRight=='1'?false:true}},
			  {name : "qty",index : "qty",width : 70,label:"数量",sortable : true,align : "right"},
			  {name : "unitprice",index : "unitprice",width : 100,label:"单价",sortable : true,align : "right"},
			  {name : "beflineamount",index : "beflineamount",width : 70,label:"折扣前金额",sortable : true,align : "right"},
			  {name : "markup",index : "markup",width : 60,label:"折扣",sortable : true,align : "right"},
			  {name : "processcost",index : "processcost",width : 70,label:"其他费用",sortable : true,align : "right"},
			  {name : "cost",index : "cost",width : 70,label:"成本",sortable : true,align : "right",hidden:${costRight=='1'?false:true}},
			  {name : "designman",index : "designman",width : 75,label:"设计师",sortable : true,align : "left"},
			  {name : "designdept2descr",index : "designdept2descr",width : 75,label:"设计师部门",sortable : true,align : "left"},
			  {name : "per",index : "per",width : 100,label:"发放比例",sortable : true,align : "right"},
			  {name : "iano",index : "iano",width : 100,label:"领料编号",sortable : true,align : "left"},
			  {name : "itemcode",index : "itemcode",width : 100,label:"材料编号",sortable : true,align : "left"},
			  {name : "itemdescr",index : "itemdescr",width : 160,label:"产品名称",sortable : true,align : "left"},
			  {name : "item12descr",index : "item12descr",width : 100,label:"材料分类",sortable : true,align : "left"},
			  {name : "item2descr",index : "item2descr",width : 100,label:"材料类型2",sortable : true,align : "left"},
			  {name : "checkoutdate",index : "checkoutdate",width : 100,label:"客户结算日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "confirmdate",index : "confirmdate",width : 100,label:"领料审核日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "businessman",index : "businessman",width : 100,label:"业务员",sortable : true,align : "left"},
			  {name : "buyerman",index : "buyerman",width : 70,label:"买手",sortable : true,align : "left"},
			  {name : "buyerdept2descr",index : "buyerdept2descr",width : 70,label:"买手部门",sortable : true,align : "left"},
			  {name : "buyer2man",index : "buyer2man",width : 70,label:"买手2",sortable : true,align : "left"},
			  {name : "buyer2dept2descr",index : "buyer2dept2descr",width : 70,label:"买手2部门",sortable : true,align : "left"},
			  {name : "lastupdate",index : "lastupdate",width : 100,label:"最后更新时间",sortable : true,align : "left",formatter:formatDate},
			  {name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"最后更新人",sortable : true,align : "left"},
			  {name : "expired",index : "expired",width : 100,label:"是否过期",sortable : true,align : "left"},
			  {name : "actionlog",index : "actionlog",width : 100,label:"操作类型",sortable : true,align : "left"},
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div id="content-list">
				<table id= "dataTable_emp"></table>
				<div id="dataTable_empPager"></div>
			</div>
		</div>
	</body>	
</html>
