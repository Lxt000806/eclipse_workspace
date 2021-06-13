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
    <title>优惠额度查询</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style type="text/css">
        .table>tbody>tr.success>td {
            background-color: #D0EEF3;
            color: #000;
        }
    </style>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>楼盘</label>
                    <input type="text" name="address"/>
                </li>
                <li>
                    <label>客户状态</label>
                    <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="4"
                            unShowValue="${canViewAllCustomers == '1' ? '1,2,3' : '1,2,3,5'}"></house:xtdmMulit>
                </li>
                <li>
					<label>客户类型</label>
					<house:custTypeMulit id="custType"  selectedValue=""></house:custTypeMulit>
				</li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="DISCAMTQUERY_EXCEL">
                <button type="button" class="btn btn-system" onclick="exportExcel()">
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

<script type="text/javascript">
    $(function() {
        Global.JqGrid.initJqGrid("dataTable", {
            height: $(document).height() - $("#content-list").offset().top - 100,
            colModel: [
                {name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left"},
                {name: "documentno", index: "documentno", width: 60, label: "档案号", sortable: true, align: "left"},
                {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
                {name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
                {name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
                {name: "designerdept", index: "designerdept", width: 100, label: "设计部", sortable: true, align: "left"},
                {name: "salesmandept", index: "salesmandept", width: 100, label: "业务部", sortable: true, align: "left"},
                {name: "discamount", index: "discamount", width: 120, label: "优惠额度（不含风险金）", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "designriskfund", index: "designriskfund", width: 120, label: "前端风险金额度", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "discamountexpense", index: "discamountexpense", width: 80, label: "优惠支出", sortable: true, align: "right", sum:true, formatter: "number", formatoptions: formatOptions, cellattr: addCellAttr},
                {name: "frontendriskfundexpense", index: "frontendriskfundexpense", width: 120, label: "前端风险金支出", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, cellattr: addCellAttr},
                {name: "discbalance", index: "discbalance", width: 80, label: "优惠余额", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "frontendriskbalance", index: "frontendriskbalance", width: 120, label: "前端风险金余额", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "totaldiscbalance", index: "totaldiscbalance", width: 80, label: "总优惠余额", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "prjmanriskfund", index: "prjmanriskfund", width: 120, label: "工程风险金额度", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "prjriskfundexpense", index: "prjriskfundexpense", width: 120, label: "工程风险金支出", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, cellattr: addCellAttr},
                {name: "prjriskbalance", index: "prjriskbalance", width: 120, label: "工程风险金余额", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "lpexpense", index: "lpexpense", width: 80, label: "礼品支出", sortable: true, align: "right",  formatter: "number", formatoptions: formatOptions,},
                {name: "iscq",index: "iscq",width : 70,label:"是否重签",sortable : true,align : "left"},
                {name: "signdate", index: "signdate", width: 80, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
                {name: "custcheckdate", index: "custcheckdate", width: 80, label: "结算日期", sortable: true, align: "left", formatter: formatDate}, 
                {name: "maxdiscamount", index: "maxdiscamount", width: 100, label: "最高优惠额度", sortable: true, hidden: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "extragiftamount", index: "extragiftamount", width: 100, label: "额外赠送额度", sortable: true, hidden: true, align: "right",sum:true ,formatter: "number", formatoptions: formatOptions, cellattr: addCellAttr},
            ],
            onCellSelect: showDetail
        })
        
        if ("${canViewPrjRiskFund}" === "0") {
            $("#dataTable").hideCol(["prjmanriskfund", "prjriskfundexpense", "prjriskbalance"])
        }
        
        $("#dataTable").setGridParam({url: "${ctx}/admin/discAmtQuery/goJqGrid"})
    })
    
    var formatOptions = {decimalPlaces: 2, thousandsSeparator: ""}
    
    function addCellAttr(rowId, val, rawObject, cm, rdata) {
        return "style='cursor: pointer; color: Blue;"
    }
    
    function showDetail(rowid, iCol, cellcontent, e) {   
        var rowData = $("#dataTable").getRowData(rowid)
        var colModel = $("#dataTable").getGridParam("colModel")
        var columnTitle = colModel[iCol].name
        
        if (columnTitle === "extragiftamount"
            || columnTitle === "discamountexpense"
            || columnTitle === "frontendriskfundexpense"
            || columnTitle === "prjriskfundexpense") {
            
            detail(rowData.code, rowData.address, columnTitle)
        }
            
    }
        
    function detail(code, address, tab) {
	    Global.Dialog.showDialog("discAmtQueryDetail", {
	        title: "优惠额度查询-明细【" + address + "】",
	        postData: {code: code, tab: tab},
	        url: "${ctx}/admin/discAmtQuery/goDetail",
	        height: 620,
	        width: 900,
	        returnFun: goto_query
	    })
	}

    function exportExcel() {
        doExcelAll("${ctx}/admin/discAmtQuery/doExcel")
    }

</script>
</body>
</html>
