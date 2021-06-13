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
	<title>销售订单管理——明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script src="${resourceRoot}/pub/component_saleCust.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript" defer></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript" defer></script>
</head>  
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="excelBtn" onclick="doExcelNow('销售订单明细查询_')">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" id="expired" name="expired" value="${salesInvoice.expired}"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>销售发票号</label> 
							<input type="text" id="no" name="no" style="width:160px;"/>
						</li>
						<li>
							<label>客户编号</label> 
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label>销售人员</label> 
							<input type="text" id="saleMan" name="saleMan" style="width:160px;"/>
						</li>
						<li>
							<label>销售类型</label> 
							<house:xtdm id="type" dictCode="SALESINVTYPE" style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>销售日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>材料类型1</label> 
							<house:dict id="itemType1" dictCode="" 
								sql="select Code,Code+' '+Descr cd from tItemType1 where Expired != 'T'" 
								sqlValueKey="Code" sqlLableKey="cd"></house:dict>
						</li>
						<li>
							<label>材料编号</label> 
							<input type="text" id="itCode" name="itCode" style="width:160px;"/>
						</li>
						<li>
							<label>销售单状态</label> 
							<house:xtdmMulit id="status" dictCode="SALESINVSTATUS" selectedValue="APLY,OPEN"></house:xtdmMulit>
						</li>
						<li>
							<input type="checkbox" id="expired_show" name="expired_show" value="${salesInvoice.expired}" 
								onclick="hideExpired(this)" ${salesInvoice.expired!='T' ?'checked':'' }/>
							<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
							<button type="button" class="btn  btn-sm btn-system"
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" id="clear" 
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</div>
<script type="text/javascript" defer>/*defer 做到一边下载JS(还是只能同时下载两个JS)，一边解析HTML。*/
/**初始化表格*/
$(function(){
	$("#itCode").openComponent_item({
		condition:{
			isItemTran:1,
		}
	});

	var postData = $("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/salesInvoice/goDetailViewPageBySql",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top - 100,
		sortable: true,
		styleUI: "Bootstrap",
		colModel : [
			{name : "no",index : "no",width : 80,label:"销售单号",sortable : true,align : "left"},
			{name : "status",index : "status",width : 80,label:"销售单状态",sortable : true,align : "left", hidden:true},
			{name : "statusdescr",index : "statusdescr",width : 80,label:"销售单状态",sortable : true,align : "left",count:true},
			{name : "date",index : "date",width : 80,label:"销售日期",sortable : true,align : "left",formatter:formatDate},
			{name : "itemtype1",index : "itemtype1",width : 80,label:"材料类型1",sortable : true,align : "left",hidden:true},
			{name : "itemtype1descr",index : "itemtype1descr",width : 80,label:"材料类型1",sortable : true,align : "left"},
			{name : "type",index : "type",width : 80,label:"类型",sortable : true,align : "left", hidden:true},
			{name : "typedescr",index : "typedescr",width : 80,label:"销售类型",sortable : true,align : "left"},
			{name : "getitemdate",index : "getitemdate",width : 80,label:"取货日期",sortable : true,align : "left",formatter:formatDate},
			{name : "whcode",index : "whcode",width : 80,label:"仓库编号",sortable : true,align : "left",hidden:true},
			{name : "warehouse",index : "warehouse",width : 120,label:"仓库名称",sortable : true,align : "left"},
			{name : "custcode",index : "custcode",width : 80,label:"客户编号",sortable : true,align : "left",hidden:true},
			{name : "custdescr",index : "custdescr",width : 80,label:"客户名称",sortable : true,align : "left"},
			{name : "address",index : "address",width : 120,label:"楼盘地址",sortable : true,align : "left"},
			{name : "itcode",index : "itcode",width : 80,label:"产品编号",sortable : true,align : "left"},
			{name : "itemtype2descr",index : "itemtype2descr",width : 80,label:"材料类型2",sortable : true,align : "left"},
			{name : "itcodedescr",index : "itcodedescr",width : 120,label:"产品名称",sortable : true,align : "left"},
			{name : "qty",index : "qty",width : 60,label:"数量",sortable : true,align : "right",sum:true},
			{name : "unitdescr",index : "unitdescr",width : 60,label:"单位",sortable : true,align : "left"},
			{name : "unitprice",index : "unitprice",width : 60,label:"单价",sortable : true,align : "right"},
			{name : "beflineamount",index : "beflineamount",width : 80,label:"折扣前金额",sortable : true,align : "right",sum:true},
			{name : "markup",index : "markup",width : 100,label:"折扣比率（%）",sortable : true,align : "right"},
			{name : "lineamount",index : "lineamount",width : 60,label:"总价",sortable : true,align : "right",sum:true},
			{name : "remarks",index : "remarks",width : 200,label:"备注",sortable : true,align : "left"},
        ],
        ondblClickRow: function(){
			view();
		},
	});

	$("#custCode").openComponent_saleCust();
	$("#saleMan").openComponent_employee();

	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	});

});

</script>
</body>
</html>
