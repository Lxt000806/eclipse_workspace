<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE html>
<html>
<head>
	<title>材料签单统计-主材明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
       .SelectBG{
           background-color:red;
           }
</style>

<script type="text/javascript"> 

$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		postData:$("#page_form").jsonForm(),
		//url:"${ctx}/admin/secondPayRecvCust/goJqGrid",
		styleUI: 'Bootstrap',
		colModel : [
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
				{name: "contractfee", index: "contractfee", width: 70, label: "工程造价", sortable: true, align: "right"},
				{name: "designfee", index: "designfee", width: 70, label: "设计费", sortable: true, align: "right"},
				{name: "paytypedescr", index: "paytypedescr", width: 80, label: "付款方式", sortable: true, align: "left"},
				{name: "haspay", index: "haspay", width: 70, label: "已付款", sortable: true, align: "right"},
				{name: "firstpay", index: "firstpay", width: 70, label: "首批付款", sortable: true, align: "right"},
				{name: "secondpay", index: "secondpay", width: 70, label: "二批付款", sortable: true, align: "right"},
				{name: "thirdpay", index: "thirdpay", width: 70, label: "三批付款", sortable: true, align: "right"},
				{name: "fourpay", index: "fourpay", width: 70, label: "尾款", sortable: true, align: "right"},
				{name: "secondpaydate", index: "secondpaydate", width: 105, label: "二期款到账日期", sortable: true, align: "left", formatter: formatDate},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "againmandescr", index: "againmandescr", width: 70, label: "翻单员", sortable: true, align: "left"},
				{name: "scenedesignman", index: "scenedesignman", width: 90, label: "现场设计师", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 95, label: "实际开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "qqconfirmdate", index: "qqconfirmdate", width: 95, label: "砌墙验收日期", sortable: true, align: "left", formatter: formatDate},
				{name: "sdconfirmdate", index: "sdconfirmdate", width: 95, label: "水电验收日期", sortable: true, align: "left", formatter: formatDate},
				{name: "chgconfirmdate", index: "chgconfirmdate", width: 120, label: "图纸变更审核日期", sortable: true, align: "left", formatter: formatDate},
				{name: "maxdate", index: "maxdate", width: 120, label: "二期款到账和图纸变更较大的日期", sortable: true, align: "left", formatter: formatDate,hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 70, label: "一级区域", sortable: true, align: "left"},
	    ],
	});
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/secondPayRecvCust/doExcel";
	doExcelAll(url);
} 

function goto_query(){
	if(($("#dateFrom").val()=="" || $("#dateTo").val()=="") && $("#statistcsMethod").val()=="2" && $.trim($("#address").val())=="" ){
		art.dialog({
			content:"起止日期不能为空",
		});
		return;
	}
	$("#dataTable").jqGrid("setGridParam",{
		url:"${ctx}/admin/secondPayRecvCust/goJqGrid",
		postData:$("#page_form").jsonForm(),
		page:1,
		sortname:''
	}).trigger("reloadGrid");
}

function changeStatistcsMethod(){
	var statistcsMethod=$("#statistcsMethod").val();
	if(statistcsMethod=="1"){
		$(".showif").removeClass("hidden");
	}else{
		$(".showif").addClass("hidden");
		$(".showif input").val("");
	}
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
							   <label>二期款到账日期</label>
							   <input type="text" id="dateFrom"
							    name="dateFrom"  class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li class="showif">
                               <label>实际开工日期</label>
                               <input type="text" id="confirmBeginFrom"
                                   name="confirmBeginFrom"  class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li class="showif">
                                <label>至</label>
                                <input type="text" id="confirmBeginTo"
                                    name="confirmBeginTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li class="showif">
                               <label>砌墙验收日期</label>
                               <input type="text" id="confirmWallDateFrom"
                                   name="confirmWallDateFrom"  class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li >
                            <li class="showif">
                                <label>至</label>
                                <input type="text" id="confirmWallDateTo"
                                    name="confirmWallDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li >
                            <li class="showif">
                               <label>水电验收日期</label>
                               <input type="text" id="confirmWaterDateFrom"
                                   name="confirmWaterDateFrom"  class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li class="showif">
                                <label>至</label>
                                <input type="text" id="confirmWaterDateTo"
                                    name="confirmWaterDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li class="showif">
							   <label>图变更审核日期</label>
							   <input type="text" id="changeDateFrom"
							    name="changeDateFrom"  class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li class="showif">
								<label>至</label>
								<input type="text" id="changeDateTo" name="changeDateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" value="${itemPlan.address}" />
                            <li>		
								<label>统计方式</label>
								<select id="statistcsMethod" name="statistcsMethod" onchange="changeStatistcsMethod()">
									<option value="1" selected>二期款到账日期</option>
									<option value="2">二期款到账&图纸变更审核</option>
								</select>
							</li>
							<li >
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>	
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
					<house:authorize authCode="SECONDPAYRECVCUST_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				  </div>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
