<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>软装材料销售分析</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">

        $(function() {
            
            Global.JqGrid.initJqGrid("dataTable", {
                height: $(document).height() - $("#content-list").offset().top - 100,
                colModel: [
                    {name: "设计师", index: "设计师", width: 80, label: "设计师", sortable: true, align: "left", sum: true},
                    {name: "墙纸", index: "墙纸", width: 80, label: "墙纸", sortable: true, align: "right", sum: true},
					{name: "窗帘", index: "窗帘", width: 80, label: "窗帘", sortable: true, align: "right", sum: true},
					{name: "灯具", index: "灯具", width: 80, label: "灯具", sortable: true, align: "right", sum: true},
					{name: "家具", index: "家具", width: 80, label: "家具", sortable: true, align: "right", sum: true},
					{name: "软装其它", index: "软装其它", width: 80, label: "软装其它", sortable: true, align: "right", sum: true},
					{name: "床垫", index: "床垫", width: 80, label: "床垫", sortable: true, align: "right", sum: true},
					{name: "床品", index: "床品", width: 80, label: "床品", sortable: true, align: "right", sum: true},
					{name: "饰品", index: "饰品", width: 80, label: "饰品", sortable: true, align: "right", sum: true},
					{name: "升降衣架", index: "升降衣架", width: 80, label: "升降衣架", sortable: true, align: "right", sum: true},
					{name: "皮革材料", index: "皮革材料", width: 80, label: "皮革材料", sortable: true, align: "right", sum: true},
					{name: "皮革人工", index: "皮革人工", width: 80, label: "皮革人工", sortable: true, align: "right", sum: true},
					{name: "合计", index: "合计", width: 80, label: "合计", sortable: true, align: "right", sum: true},
                ],
                
            });
        });
        
        function goto_query() {
        
            if (!$("#dateFrom").val()) {
                art.dialog({content: "请选择统计开始时间！"});
                return;
            }
        
            $("#dataTable").setGridParam({
                url: "${ctx}/admin/softSaleAnalyse/goJqGrid",
                postData: $("#page_form").jsonForm(),
                page: 1
            }).trigger("reloadGrid");
        }
        
	    function clearForm() {
	        $("#page_form input[type='text']").val('');
	        $("#page_form input[type='hidden']").val('');
	        $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	    }
	    
        function doExcel() {
            doExcelAll("${ctx}/admin/softSaleAnalyse/doExcel");
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
                    <label>客户类型</label>
                    <house:custTypeMulit id="custType"></house:custTypeMulit>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <house:authorize authCode="SOFTSALEANALYSE_EXCEL">
                    <button type="button" class="btn btn-system" onclick="doExcel()">
                        <span>输出到Excel</span>
                    </button>
                </house:authorize>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</div>
</body>
</html>
