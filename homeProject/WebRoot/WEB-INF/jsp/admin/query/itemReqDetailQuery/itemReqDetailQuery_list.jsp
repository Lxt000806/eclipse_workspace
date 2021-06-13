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
	<title>材料需求明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
		var postData = $("#page_form").jsonForm();
		//初始化表格
		var gridOption ={
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			sortable: true,
			colModel : [
				{name:"custcode",	index:"custcode",	width:75,	label:"客户编号",	sortable:true,align:"left" ,},
				{name:"address",	index:"address",	width:110,	label:"楼盘",	sortable:true,align:"left" ,},
				{name:"custtypedescr",	index:"custtypedescr",	width:110,	label:"客户类型",	sortable:true,align:"left" ,},
				{name:"area",	index:"area",	width:60,	label:"面积",	sortable:true,align:"right" ,},
				{name:"custcheckdate",	index:"custcheckdate",	width:95,	label:"客户结算时间",	sortable:true,align:"left" ,formatter:formatDate},
				{name:"areadescr",	index:"areadescr",	width:90,	label:"区域名称",	sortable:true,align:"left" ,},
				{name:"intdescr",	index:"intdescr",	width:80,	label:"集成成品",	sortable:true,align:"left" ,},
				{name:"iscupboarddescr",	index:"iscupboarddescr",	width:80,	label:"是否橱柜",	sortable:true,align:"left" ,},
				{name:"itemcode",	index:"itemcode",	width:75,	label:"材料编号",	sortable:true,align:"left" ,},
				{name:"itemdescr",	index:"itemdescr",	width:90,	label:"材料名称",	sortable:true,align:"left" ,},
				{name:"item1descr",	index:"item1descr",	width:75,	label:"材料类型1",	sortable:true,align:"left" ,},
				{name:"item2descr",	index:"item2descr",	width:75,	label:"材料类型2",	sortable:true,align:"left" ,},
				{name:"sqldescr",	index:"sqldescr",	width:75,	label:"品牌",	sortable:true,align:"left" ,},
				{name:"qty",	index:"qty",	width:75,	label:"需求数量",	sortable:true,align:"right" ,sum:true},
				{name:"sendqtybywh",	index:"sendqtybywh",	width:110,	label:"仓库发货数量",	sortable:true,align:"right" ,sum:true},
				{name:"sendqtybyspl",	index:"sendqtybyspl",	width:115,	label:"供应商发货数量",	sortable:true,align:"right" ,sum:true},
				{name:"sendqty",	index:"sendqty",	width:85,	label:"已发货数量",	sortable:true,align:"right" ,sum:true},
				{name:"uomdescr",	index:"uomdescr",	width:60,	label:"单位",	sortable:true,align:"left" ,},
				{name:"unitprice",	index:"unitprice",	width:70,	label:"单价",	sortable:true,align:"right" ,},
				{name:"cost",	index:"cost",	width:90,	label:"成本",	sortable:true,align:"right",sum:true, hidden:true},
				{name:"costamount",	index:"costamount",	width:70,	label:"成本总价",	sortable:true,align:"right", sum:true, hidden:true},
				{name:"projectcost",	index:"projectcost",	width:115,	label:"项目经理结算价",	sortable:true,align:"right",sum:true, hidden:true},
				{name:"lineamount",	index:"lineamount",	width:70,	label:"总价",	sortable:true,align:"right" ,sum:true},
				{name:"beflineamount",	index:"beflineamount",	width:90,	label:"折扣前金额",	sortable:true,align:"right" ,sum:true},
				{name:"markup",	index:"markup",	width:70,	label:"折扣",	sortable:true,align:"right" ,},
				{name:"amount",	index:"amount",	width:70,	label:"小计",	sortable:true,align:"right" ,},
				{name:"processcost",	index:"processcost",	width:70,	label:"其他费用",	sortable:true,align:"right" ,sum:true},
				{name:"disccost",	index:"disccost",	width:70,	label:"优惠分摊",	sortable:true,align:"right" ,},
				{name:"afterdisccost",	index:"afterdisccost",	width:95,	label:"优惠后总价",	sortable:true,align:"right" ,},
				{name:"remarks",	index:"remarks",	width:70,	label:"材料描述",	sortable:true,align:"left" ,},
				{name:"isoutsidedescr",	index:"isoutsidedescr",	width:80,	label:"套餐外材料",	sortable:true,align:"left" ,},
				{name:"itemsetdescr",	index:"itemsetdescr",	width:90,	label:"套餐包",	sortable:true,align:"left" ,},	
				{name:"intdesigner",	index:"intdesigner",	width:90,	label:"集成设计师",	sortable:true,align:"left" ,},	
				{name:"cupdesigner",	index:"cupdesigner",	width:90,	label:"橱柜设计师",	sortable:true,align:"left" ,},	
			],
			gridComplete:function(){ 
		         if ('${costRight}' == '1' ) {
		        	$("#dataTable").jqGrid('showCol', "cost");
		       	 	$("#dataTable").jqGrid('showCol', "costamount");
		       	    $("#dataTable").jqGrid('showCol', "projectcost");
		         }
		    },
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		
		window.goto_query = function(){
			var dateFrom=new Date($.trim($("#dateFrom").val()));
			var dateTo=new Date($.trim($("#dateTo").val()));
			if(dateFrom>dateTo){
				art.dialog({
					content: "开始日期不能大于结束日期",
				});
				return false;
			}
			if($.trim($("#dateFrom").val())==""||$.trim($("#dateTo").val())==""){
				art.dialog({
					content: "时间查询条件不能为空",
				});
				return false;
			}		
			$("#dataTable").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),
								page:1,url:"${ctx}/admin/itemReqDetailQuery/goJqGrid",}).trigger("reloadGrid");
		}
		
	});
	function doExcel(){
		var url = "${ctx}/admin/itemReqDetailQuery/doExcel";
		doExcelAll(url);
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#custType").val('');
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	} 
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2"></select>
						</li>
						<li>
							<label>是否橱柜</label>
							<house:xtdm  id="isCupboard" dictCode="YESNO" style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" ></house:custTypeMulit>
						</li>
						<li>
							<label>结算时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>是否服务性产品</label>
							<house:xtdm  id="isServiceItem" dictCode="YESNO" style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
   				<div class="btn-group-sm">
					<house:authorize authCode="ITEMREQDETAILQUERY_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">
							<span>导出excel</span>
						</button>	
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
