<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>软装业绩排名</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">

function Table() {
    this.urls = {
        employeeDataTable: "${ctx}/admin/softPerfAny/goJqGrid",
        customerDataTable: "${ctx}/admin/softPerfAny/goCustomerJqGrid"
    };
    
    this.contents = {
        employeeDataTable: "content-list-employee",
        customerDataTable: "content-list-customer"
    };
    
    this.excelNames = {
        employeeDataTable: "软装部业绩统计-按楼盘",
        customerDataTable: "软装部业绩统计-按个人"
    };
}

Table.prototype.setId = function(id) {
    this.id = id;

    for (var prop in this.contents) {
        if (prop !== id) {
            $("#" + this.contents[prop]).hide();
        } else {
            $("#" + this.contents[prop]).show();
        }
    }
};

Table.prototype.getId = function() {
    return this.id;
};

Table.prototype.getUrl = function() {
    return this.urls[this.id];
};

Table.prototype.getExcelName = function() {
    return this.excelNames[this.id];
};

// 全局变量，保存当前显示的表格ID
var currentTable = new Table();


function goto_query() {
	if($.trim($("#dateFrom").val())==""){
		art.dialog({
			content: "统计开始日期不能为空"
		});
		return false;
	} 
	if($.trim($("#dateTo").val())==""){
		art.dialog({
			content: "统计结束日期不能为空"
		});
		return false;
	}
    var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
   		art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
 		return false;
    }
    
	$("#" + currentTable.getId()).setGridParam({
	   url: currentTable.getUrl(),
	   datatype:'json',
	   postData: $("#page_form").jsonForm(),
	   page: 1,
	   fromServer: true
    }).trigger("reloadGrid");
}				

$(function(){
	var postData=$("#page_form").jsonForm();

	Global.JqGrid.initJqGrid("employeeDataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list-employee").offset().top-70,
        rowNum:100000,  
        pager :'1',
		colModel : [
			{name: "empcode", index: "empcode", width: 70, label: "员工编号", sortable: true, align: "left"},
		 	{name: "namechi", index: "namechi", width: 70, label: "姓名", sortable: true, align: "left"},
			{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
			{name: "department3descr", index: "department3descr", width: 80, label: "三级部门", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "业绩金额", sortable: true, align: "right", sum: true},
	    ],
	});
	
    Global.JqGrid.initJqGrid("customerDataTable",{
        postData: postData,
        height:$(document).height()-$("#content-list-employee").offset().top-30,
        rowNum:100000,
        loadonce: true,
        pager :'1',
        colModel : [
            {name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
            {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
            {name: "designer", index: "designer", width: 80, label: "设计师", sortable: true, align: "left"},
            {name: "businessman", index: "businessman", width: 80, label: "业务员", sortable: true, align: "left"},
            {name: "againman", index: "againman", width: 80, label: "翻单员", sortable: true, align: "left"},
            {name: "softdesigner", index: "softdesigner", width: 80, label: "软装设计师", sortable: true, align: "left"},
            {name: "contractfee", index: "contractfee", width: 70, label: "工程造价", sortable: true, align: "right"},
            {name: "softamount", index: "softamount", width: 70, label: "软装金额", sortable: true, align: "right"},
            {name: "softdiscamount", index: "softdiscamount", width: 70, label: "软装优惠", sortable: true, align: "right"},
            {name: "softdisccost", index: "softdisccost", width: 70, label: "软装产品优惠", sortable: true, align: "right"},
            {name: "intamount", index: "intamount", width: 70, label: "集成金额", sortable: true, align: "right"},
            {name: "intdiscamount", index: "intdiscamount", width: 70, label: "集成优惠", sortable: true, align: "right"},
            {name: "intdisccost", index: "intdisccost", width: 70, label: "集成产品优惠", sortable: true, align: "right"},
            {name: "mainamount", index: "mainamount", width: 70, label: "主材金额", sortable: true, align: "right"},
            {name: "maindiscamount", index: "maindiscamount", width: 70, label: "主材优惠", sortable: true, align: "right"},
            {name: "mainservamount", index: "mainservamount", width: 70, label: "服务性金额", sortable: true, align: "right"},
            {name: "mainservdiscamount", index: "mainservdiscamount", width: 70, label: "服务性优惠", sortable: true, align: "right"},
            {name: "baseamount", index: "baseamount", width: 70, label: "基础金额", sortable: true, align: "right"},
            {name: "basediscamount", index: "basediscamount", width: 70, label: "基础优惠", sortable: true, align: "right"},
            {name: "perfamount", index: "perfamount", width: 70, label: "业绩金额", sortable: true, align: "right", sum: true},
        ],
    });
    
	$("#statisticalMethod").change();
	
});

function view(){
	var ret = selectDataTableRow(currentTable.getId());
	var postData=$("#page_form").jsonForm();
	postData.empCode=ret.empcode;
	
    if (ret) {
      Global.Dialog.showDialog("detail",{
		  title: "查看明细",
		  url: "${ctx}/admin/softPerfAny/goDetail",
		  postData: postData,
		  height: 700,
		  width: 1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function switchStatisticalMethod(obj) {
    currentTable.setId(obj.value);
    
    var role = $("#role");
    var viewButton = $("#viewButton");
    if (obj.value === "customerDataTable") {
        role.parent().hide();
        viewButton.hide();
    } else if (obj.value === "employeeDataTable") {
        role.parent().show();
        viewButton.show();
    }
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>一级部门</label>
						<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx}')"></house:department1>
					</li>
					<li>
						<label>二级部门</label>
						<house:department2 id="department2" dictCode=" " ></house:department2>
					</li>
					<li>
						<label>三级部门</label>
						<house:department3 id="department3" dictCode="${employee.department2}"></house:department3>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>统计时间从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>统计方式</label>
						<select id="statisticalMethod" name="statisticalMethod" onchange="switchStatisticalMethod(this)">
							<option value="employeeDataTable">按个人</option>
							<option value="customerDataTable">按楼盘</option>
						</select>
					</li>
					<li>
					    <label>角色</label>
					    <select id="role" name="role">
                            <option value="01">业务员</option>
                            <option value="50">设计师</option>
                            <option value="24">翻单员</option>
					    </select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SOFTPERFANY_VIEW">
					<button type="button" id="viewButton" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="SOFTPERFANY_EXCEL">
					<button type="button" class="btn btn-system"
					        onclick="doExcelNow(currentTable.getExcelName(), currentTable.getId(), 'page_form')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list-employee">
				<table id="employeeDataTable"></table>
			</div>
			<div id="content-list-customer">
				<table id="customerDataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>
