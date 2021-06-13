<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>优惠额度查询-明细</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form">
    <div class="query-form" hidden>
        <form action="" method="post" id="page_form">
            <input name="code" value="${code}"/>
        </form>
    </div>
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <!-- <li id="extragiftamount_li" class=""><a href="#extragiftamount" data-toggle="tab">额外赠送额度明细</a></li> -->
            <li id="discamountexpense_li" class=""><a href="#discamountexpense" data-toggle="tab">优惠额度支出明细</a></li>
            <li id="frontendriskfundexpense_li" class=""><a href="#frontendriskfundexpense" data-toggle="tab">前端风险金支出明细</a></li>
            <li id="prjriskfundexpense_li" class=""><a href="#prjriskfundexpense" data-toggle="tab">工程风险金支出明细</a></li>
        </ul>
        <div class="tab-content">
            <div id="extragiftamount" class="tab-pane fade">
                <div id="content-list1">
                    <table id="extragiftamountDataTable"></table>
                    <div id="extragiftamountDataTablePager"></div>
                </div>
            </div>
            <div id="discamountexpense" class="tab-pane fade">
                <div id="content-list2">
                    <table id="discamountexpenseDataTable"></table>
                    <div id="discamountexpenseDataTablePager"></div>
                </div>
            </div>
            <div id="frontendriskfundexpense" class="tab-pane fade">
                <div id="content-list3">
                    <table id="frontendriskfundexpenseDataTable"></table>
                    <div id="frontendriskfundexpenseDataTablePager"></div>
                </div>
            </div>
            <div id="prjriskfundexpense" class="tab-pane fade">
                <div id="content-list4">
                    <table id="prjriskfundexpenseDataTable"></table>
                    <div id="prjriskfundexpenseDataTablePager"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var formatOptions = {decimalPlaces: 2, thousandsSeparator: ""}

    $(function() {
    
        $("#${tab}_li").addClass("active")
        $("#${tab}").addClass("in active")

        Global.JqGrid.initJqGrid("extragiftamountDataTable", {
            // url: "${ctx}/admin/discAmtQuery/goExtraGiftAmountJqGrid",
            postData: $("#page_form").jsonForm(),
            height: 400,
            rowNum: 1000,
            pager: '1',
            colModel: [
                {name: 'typedescr', index: 'typedescr', width: 100, label: '变动类型', sortable: true, align: "left"},
                {name: 'amount', index: 'amount', width: 80, label: '金额', sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, sum: true},
                {name: 'isriskfunddescr', index: 'isriskfunddescr', width: 80, label: '风控基金', sortable: true, align: "left"},
                {name: 'isextradescr', index: 'isextradescr', width: 80, label: '额外赠送', sortable: true, align: "left"},
                {name: 'remarks', index: 'remarks', width: 260, label: '说明', sortable: true, align: "left"}
            ]
        })

        Global.JqGrid.initJqGrid("discamountexpenseDataTable", {
            url: "${ctx}/admin/discAmtQuery/goDiscAmountExpenseJqGrid",
            postData: $("#page_form").jsonForm(),
            height: 400,
            rowNum: 1000,
            pager: '1',
            colModel: [
                {name: 'typedescr', index: 'typedescr', width: 100, label: '变动类型', sortable: true, align: "left"},
                {name: 'amount', index: 'amount', width: 80, label: '金额', sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, sum: true},
                {name: 'isriskfunddescr', index: 'isriskfunddescr', width: 80, label: '风控基金', sortable: true, align: "left"},
                {name: 'isextradescr', index: 'isextradescr', width: 80, label: '额外赠送', sortable: true, align: "left"},
                {name: 'remarks', index: 'remarks', width: 260, label: '说明', sortable: true, align: "left"}
            ]
        })

        Global.JqGrid.initJqGrid("frontendriskfundexpenseDataTable", {
            url: "${ctx}/admin/discAmtQuery/goFrontendRiskFundExpenseJqGrid",
            postData: $("#page_form").jsonForm(),
            height: 400,
            rowNum: 1000,
            pager: '1',
            colModel: [
                {name: 'typedescr', index: 'typedescr', width: 100, label: '变动类型', sortable: true, align: "left"},
                {name: 'amount', index: 'amount', width: 80, label: '金额', sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, sum: true},
                {name: 'isriskfunddescr', index: 'isriskfunddescr', width: 80, label: '风控基金', sortable: true, align: "left"},
                {name: 'isextradescr', index: 'isextradescr', width: 80, label: '额外赠送', sortable: true, align: "left"},
                {name: 'remarks', index: 'remarks', width: 260, label: '说明', sortable: true, align: "left"},
                {name: 'fixno', index: 'fixno', width: 80, label: '定责单号', sortable: true, align: "left",formatter:fixBtn},
            ]
        })
        
        if ("${canViewPrjRiskFund}" === "0") {
            $("#prjriskfundexpense_li").remove();
            $("#prjriskfundexpense").remove();
        } else {
	        Global.JqGrid.initJqGrid("prjriskfundexpenseDataTable", {
	            url: "${ctx}/admin/discAmtQuery/goPrjRiskFundExpenseJqGrid",
	            postData: $("#page_form").jsonForm(),
	            height: 400,
	            rowNum: 1000,
	            pager: '1',
	            colModel: [
	                {name: 'empname', index: 'empname', width: 80, label: '责任人', sortable: true, align: "left"},
	                {name: 'faulttypedescr', index: 'faulttypedescr', width: 150, label: '责任人类型', sortable: true, align: "left"},
	                {name: 'riskfund', index: 'riskfund', width: 120, label: '风险金', sortable: true, align: "right", formatter: "number", formatoptions: formatOptions, sum: true},
	            ]
	        })
        }

    })
    
    function fixBtn(v,x,r){
		return "<a href='#' onclick='viewFix(this);'>"+v+"</a>";
	}   	
	
	function viewFix(v){
		Global.Dialog.showDialog("view",{
            title:"工人定责管理--查看",
            postData:{no:$(v).text().trim()},
            url:"${ctx}/admin/discAmtQuery/goViewFix",
            height: 600,
            width:1300,
            returnFun: goto_query 
        });
	}
</script>
</body>
</html>
