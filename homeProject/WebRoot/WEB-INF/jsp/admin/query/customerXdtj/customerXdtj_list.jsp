<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>下定签单统计</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function goto_query2(){
	var tableId;
   
	if ($("#statistcsMethod").val() == '1') {
		tableId = 'dataTable';
		$("#content-list-group-by-dept1").hide(); 
		$("#content-list-detail").show();
	} else {
		tableId = 'dataTableGroupByDept1';
		$("#content-list-detail").hide();
		$("#content-list-group-by-dept1").show();
		if ($("#statistcsMethod").val() == '2') {
			$("#"+tableId).jqGrid('showCol', "depart1descr");
			$("#"+tableId).jqGrid('hideCol', "depart2descr2");
			$("#"+tableId).jqGrid('hideCol', "teamdescr");
			$("#"+tableId).jqGrid('hideCol', "builderdescr");
			$("#"+tableId).jqGrid('hideCol', "custtypedescr");
			$("#"+tableId).jqGrid('hideCol', "delivdate");
		} else if ($("#statistcsMethod").val() == '3') {
			$("#"+tableId).jqGrid('showCol', "depart1descr");
			$("#"+tableId).jqGrid('showCol', "depart2descr2");
			$("#"+tableId).jqGrid('hideCol', "teamdescr");
			$("#"+tableId).jqGrid('hideCol', "builderdescr");
			$("#"+tableId).jqGrid('hideCol', "custtypedescr");
			$("#"+tableId).jqGrid('hideCol', "delivdate");
		} else if ($("#statistcsMethod").val() == '4') {
			$("#"+tableId).jqGrid('hideCol', "depart1descr");
			$("#"+tableId).jqGrid('hideCol', "depart2descr2");
			$("#"+tableId).jqGrid('showCol', "teamdescr");
			$("#"+tableId).jqGrid('hideCol', "builderdescr");
			$("#"+tableId).jqGrid('hideCol', "custtypedescr");
			$("#"+tableId).jqGrid('hideCol', "delivdate");
		} else if ($("#statistcsMethod").val() == '5') {
			$("#"+tableId).jqGrid('hideCol', "depart1descr");
			$("#"+tableId).jqGrid('hideCol', "depart2descr2");
			$("#"+tableId).jqGrid('hideCol', "teamdescr");
			$("#"+tableId).jqGrid('showCol', "builderdescr");
			$("#"+tableId).jqGrid('hideCol', "custtypedescr");
			$("#"+tableId).jqGrid('hideCol', "delivdate");
		} else if ($("#statistcsMethod").val() == '6') {
			$("#"+tableId).jqGrid('hideCol', "depart1descr");
			$("#"+tableId).jqGrid('hideCol', "depart2descr2");
			$("#"+tableId).jqGrid('hideCol', "teamdescr");
			$("#"+tableId).jqGrid('hideCol', "builderdescr");
			$("#"+tableId).jqGrid('showCol', "custtypedescr");
			$("#"+tableId).jqGrid('hideCol', "delivdate");
		};
	}
	
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/customerXdtj/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}

$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list-detail").offset().top-100,
			styleUI: 'Bootstrap',
			loadonce: true,
		    rowNum:100000,  
		    pager :'1',
			colModel : [
				{name: "depart1descr", index: "depart1descr", width: 80, label: "一级部门", sortable: true, align: "left", count: true, frozen: true},
				{name: "depart2descr2", index: "depart2descr2", width: 80, label: "二级部门", sortable: true, align: "left", frozen: true},
				{name: "depart3descr", index: "depart3descr", width: 80, label: "三级部门", sortable: true, align: "left", frozen: true},
				{name: "address", index: "address", width: 160, label: "楼盘地址", sortable: true, align: "left", frozen: true},
				{name: "sourcedescr", index: "sourcedescr", width: 80, label: "客户来源", sortable: true, align: "left"},
				{name: "netchaneldescr", index: "netchaneldescr", width: 80, label: "网络渠道", sortable: true, align: "left"},
				{name: "builderdescr", index: "builderdescr", width: 100, label: "项目名称", sortable: true, align: "left"},
				{name: "delivdate", index: "delivdate", width: 72, label: "交房时间", sortable: true, align: "left", formatter: formatTime},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 70, label: " 设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "againmandescr", index: "againmandescr", width: 70, label: "翻单员", sortable: true, align: "left"},
				{name: "discremark", index: "discremark", width: 150, label: "优惠政策", sortable: true, align: "left"},
				{name: "linebasefee", index: "linebasefee", width: 75, label: "基础造价", sortable: true, align: "right", sum: true},
				{name: "managefee", index: "managefee", width: 75, label: "管理费", sortable: true, align: "right", sum: true},
				{name: "linemainfee", index: "linemainfee", width: 75, label: "主材", sortable: true, align: "right", sum: true},
				{name: "linesoftfee", index: "linesoftfee", width: 75, label: "软装", sortable: true, align: "right", sum: true},
				{name: "lineintegratefee", index: "lineintegratefee", width: 75, label: "集成", sortable: true, align: "right", sum: true},
				{name: "linecupboardfee", index: "linecupboardfee", width: 75, label: "橱柜", sortable: true, align: "right", sum: true},
				{name: "mainservfee", index: "mainservfee", width: 80, label: "服务性产品", sortable: true, align: "right", sum: true},
				{name: "designfee", index: "designfee", width: 75, label: "设计费", sortable: true, align: "right", sum: true},
				{name: "drawfee", index: "drawfee", width: 75, label: "制图费", sortable: true, align: "right", sum: true},
				{name: "colordrawfee", index: "colordrawfee", width: 75, label: "效果图费", sortable: true, align: "right", sum: true},
				{name: "basedisc", index: "basedisc", width: 95, label: "基础协议优惠", sortable: true, align: "right", sum: true},
				{name: "gift", index: "gift", width: 75, label: "实物赠送", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 75, label: "工程造价", sortable: true, align: "right", sum: true},
				{name: "achievded", index: "achievded", width: 85, label: "业绩扣除数", sortable: true, align: "right", sum: true},
				{name: "achievfee", index: "achievfee", width: 75, label: "业绩金额", sortable: true, align: "right", sum: true},
				{name: "crtdate", index: "crtdate", width: 75, label: "创建时间", sortable: true, align: "left", formatter: formatDate},
				{name: "visitdate", index: "visitdate", width: 75, label: "到店时间", sortable: true, align: "left", formatter: formatDate},
				{name: "setdate", index: "setdate", width: 75, label: "下定时间", sortable: true, align: "left", formatter: formatDate},
				{name: "measuredate", index: "measuredate", width: 75, label: "量房时间", sortable: true, align: "left", formatter: formatDate},
				{name: "planedate", index: "planedate", width: 75, label: "平面时间", sortable: true, align: "left", formatter: formatDate},
				{name: "facadedate", index: "facadedate", width: 75, label: "立面时间", sortable: true, align: "left", formatter: formatDate},
				{name: "pricedate", index: "pricedate", width: 75, label: "报价时间", sortable: true, align: "left", formatter: formatDate},
				{name: "drawdate", index: "drawdate", width: 85, label: "效果图时间", sortable: true, align: "left", formatter: formatDate},
				{name: "prevdate", index: "prevdate", width: 75, label: "放样时间", sortable: true, align: "left", formatter: formatDate},
				{name: "signdate", index: "signdate", width: 75, label: "签单时间", sortable: true, align: "left",formatter: formatDate}
	      	],
	        loadComplete: function(){
              	$('.ui-jqgrid-bdiv').scrollTop(0);
              	frozenHeightReset("dataTable");
         	},    
		});

		//初始化表格
		Global.JqGrid.initJqGrid("dataTableGroupByDept1",{
			height:$(document).height()-$("#content-list-detail").offset().top-100,
			styleUI: 'Bootstrap',
			loadonce: true,
		    rowNum:100000,  
		    pager :'1',
			colModel : [
				{name: "depart1descr", index: "depart1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "depart2descr2", index: "depart2descr2", width: 80, label: "二级部门", sortable: true, align: "left"},
				{name: "teamdescr", index: "teamdescr", width: 80, label: "团队", sortable: true, align: "left"},
				{name: "builderdescr", index: "builderdescr", width: 120, label: "项目名称", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "custcount", index: "custcount", width: 70, label: "下定数", sortable: true, align: "right", sum: true},
				{name: "linebasefee", index: "linebasefee", width: 75, label: "基础造价", sortable: true, align: "right", sum: true},
				{name: "managefee", index: "managefee", width: 75, label: "管理费", sortable: true, align: "right", sum: true},
				{name: "linemainfee", index: "linemainfee", width: 75, label: "主材", sortable: true, align: "right", sum: true},
				{name: "linesoftfee", index: "linesoftfee", width: 75, label: "软装", sortable: true, align: "right", sum: true},
				{name: "lineintegratefee", index: "lineintegratefee", width: 75, label: "集成", sortable: true, align: "right", sum: true},
				{name: "linecupboardfee", index: "linecupboardfee", width: 75, label: "橱柜", sortable: true, align: "right", sum: true},
				{name: "mainservfee", index: "mainservfee", width: 80, label: "服务性产品", sortable: true, align: "right", sum: true},
				{name: "designfee", index: "designfee", width: 75, label: "设计费", sortable: true, align: "right", sum: true},
				{name: "drawfee", index: "drawfee", width: 75, label: "制图费", sortable: true, align: "right", sum: true},
				{name: "colordrawfee", index: "colordrawfee", width: 75, label: "效果图费", sortable: true, align: "right", sum: true},
				{name: "basedisc", index: "basedisc", width: 75, label: "基础协议优惠", sortable: true, align: "right", sum: true},
				{name: "gift", index: "gift", width: 75, label: "实物赠送", sortable: true, align: "right", sum: true},
				{name: "contractfee", index: "contractfee", width: 75, label: "工程造价", sortable: true, align: "right", sum: true},
				{name: "achievded", index: "achievded", width: 75, label: "业绩扣除数", sortable: true, align: "right", sum: true},
				{name: "achievfee", index: "achievfee", width: 75, label: "业绩金额", sortable: true, align: "right", sum: true}
	      	]
		});
		
		$("#content-list-group-by-dept1").hide();
		
		$("#builderCode").openComponent_builder();
		
		$("#dataTable").jqGrid("setFrozenColumns");
});

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customerXdtj/doExcel";
	var tableId = 'dataTable';
	if ($("#statistcsMethod").val() == '1') {
		tableId = 'dataTable';
	} else {
		tableId = 'dataTableGroupByDept1';
	}
	doExcelAll(url, tableId);
} 
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>一级部门</label>
						<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
					</li>
					<li>
						<label>二级部门</label>
						<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
					</li>
					<li>
						<label>三级部门</label>
						<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department3 }"></house:DictMulitSelect>
					</li>
					<li>
					   <label>团队</label>
					   <house:DataSelect id="team" className="Team" keyColumn="code" valueColumn="desc1" filterValue="Expired='F'"></house:DataSelect>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
					</li>
					<li>
					   <label>项目名称</label>
					   <input type="text" id="builderCode" name="builderCode"  value="${customer.builderCode}"/>
					</li>
					<li>
					   <label>统计角色</label>
					   <select id="role" name="role" >
							<option value="00">按设计师统计</option>
							<option value="01">按业务员统计</option>
							<option value="24">按翻单员统计</option>
						</select>
					</li>
				    <li>
					   <label>统计时间</label>
					   <select id="statistcsDateType" name="statistcsDateType" >
							<option value="1">按下定时间统计</option>
							<option value="2">按合同签订时间统计</option>
						</select>
					</li>
					 
					<li>
					   <label>统计日期</label>
					   <input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
			        <li>
					   <label>至</label>
					   <input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
			        
					 <li>
					   <label>统计方式</label>
					   <select id="statistcsMethod" name="statistcsMethod" >
							<option value="1">明细</option>
							<option value="2">按一级部门</option>
							<option value="3">按二级部门</option>
							<option value="4">按团队</option>
							<option value="5">按项目名称</option>
							<option value="6">按客户类型</option>
						</select>
					</li>			
					<li>
					   <label>一级区域</label>
					  <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
					</li>	
					<li>
						<label>客户来源</label>
						<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${customer.source }"></house:xtdm>
					</li>	
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query2();"  >查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
		           	 <house:authorize authCode="XDTJ_EXCEL"> 
				     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				     </house:authorize>
			  </div>
			<div id="content-list-detail">
				<table id= "dataTable"></table> 
				<div id="dataTablePager"></div>
			</div> 
			<div id="content-list-group-by-dept1">
				<table id= "dataTableGroupByDept1"></table> 
				<div id="dataTableGroupByDept1Pager"></div>
			</div>
		</div>
</body>
</html>


