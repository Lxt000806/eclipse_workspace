<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>结算退款</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button type="button" class="btn btn-system" onclick="addToSelectedRows()">确认新增</button>
            <button type="button" class="btn btn-system" onclick="saveSelectedRowsAndCloseWin()">关闭</button>
        </div>
    </div>
</div>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <ul class="ul-form">
                <li>
                    <label>实际结算日期从</label>
                    <input type="text" id="checkoutDateFrom" name="checkoutDateFrom" class="i-date"
                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                           value="<fmt:formatDate value='${returnPay.checkoutDateFrom}' pattern='yyyy-MM-dd'/>"/>
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="checkoutDateTo" name="checkoutDateTo" class="i-date"
                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                           value="<fmt:formatDate value='${returnPay.checkoutDateTo}' pattern='yyyy-MM-dd'/>"/>
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" id="address" name="address"/>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="reloadGrid()">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>

    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
<script type="text/javascript">

    function SelectedRows() {
	    this.rows = [];
	    
	    this.push = function(row) {
	        this.rows.push(row);
	    }
	    
	    this.getRows = function() {
	        return this.rows;
	    }
	    
	    this.getCellOfEachRow = function(cellName) {
	        if (!cellName) return [];
	        
	        var result = [];
	        for (var i = 0; i < this.rows.length; i++) {
	            result.push(this.rows[i][cellName])
	        }
	        
	        return result;
	    }
	}

    var selectedRows = new SelectedRows();

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            height: $(document).height() - $("#content-list").offset().top - 80,
            multiselect: true,
            colModel: [
                {name: 'custcode', index: 'custcode', width: 80, label: '客户编号', sortable: true, align: "left"},
                {name: 'descr', index: 'descr', width: 80, label: '客户名称', sortable: true, align: "left"},
                {name: 'custtypedescr', index: 'custtypedescr', width: 80, label: '客户类型', sortable: true, align: "left"},
                {name: 'address', index: 'address', width: 200, label: '楼盘', sortable: true, align: "left"},
                {name: 'statusdescr', index: 'statusdescr', width: 80, label: '客户状态', sortable: true, align: "left"},
                {name: 'checkoutdate', index: 'checkoutdate', width: 120, label: '实际结算日期', sortable: true, align: "left", formatter: formatDate},
                {name: 'designmanname', index: 'designmanname', width: 80, label: '设计师', sortable: true, align: "left"},
                {name: 'projectmanname', index: 'projectmanname', width: 80, label: '项目经理', sortable: true, align: "left"},
                {name: 'region', index: 'region', width: 70, label: '区域编号', sortable: true, align: "left", hidden: true},
			    {name: 'regiondescr', index: 'regiondescr', width: 50, label: '区域', sortable: true, align: "left", hidden: true},
			    {name: 'documentno', index: 'documentno', width: 70, label: '档案号', sortable: true, align: "left", hidden: true},
			    {name: 'ispubreturndescr', index: 'ispubreturndescr', width: 70, label: '对公退款', sortable: true, align: "left", hidden: true},
			    {name: 'returnamount', index: 'returnamount', width: 70, label: '退款金额', sortable: true, align: "left", hidden: true},
			    {name: 'enddescr', index: 'enddescr', width: 70, label: '结束代码', sortable: true, align: "left", hidden: true},
			    {name: 'prjdeptleadername', index: 'prjdeptleadername', width: 80, label: '工程部经理', sortable: true, align: "left"},
			    {name: 'checkstatus', index: 'checkstatus', width: 100, label: '客户结算状态', sortable: true, align: "left", hidden: true},
			    {name: 'checkstatusdescr', index: 'checkstatusdescr', width: 100, label: '客户结算状态', sortable: true, align: "left", hidden: true},
			    {name: 'unpaidamount', index: 'unpaidamount', width: 100, label: '未达账金额', sortable: true, align: "left", hidden: true, formatter: "number", formatoptions: {decimalPlaces: 2, thousandsSeparator: ""}},
            ]
        });

    });
    
    function addToSelectedRows() {
        var dataTable = $("#dataTable");
        var ids = dataTable.getGridParam("selarrrow");
        
        for (var i = 0; i < ids.length; i++) {
            selectedRows.push(dataTable.getRowData(ids[i]));
        }

        reloadGrid();
    }
    
    function reloadGrid(gridId) {
        gridId = gridId || "dataTable";
        
        var grid = $("#" + gridId);
        var pageForm = $("#page_form");
        var params = pageForm.jsonForm();
        var excludedCustsArray = selectedRows.getCellOfEachRow("custcode");
        
        // 新增或编辑页面明细表格中已存在的客户
        var savedCustsArray = "${returnPay.excludedCusts}".split(",");
        
        // 合并已存在的客户和当前已选择的客户
        [].push.apply(excludedCustsArray, savedCustsArray);
        
        params.excludedCusts = excludedCustsArray.join(",");
        
        grid.setGridParam({
            url: "${ctx}/admin/returnPay/settleRefundsGrid",
            postData: params
        }).trigger("reloadGrid");
    }
    
    function saveSelectedRowsAndCloseWin() {
        Global.Dialog.returnData = selectedRows.getRows();
        closeWin(true);
    }

</script>
</body>
</html>
