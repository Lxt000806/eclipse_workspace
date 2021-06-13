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
    <title>集成出货分析</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}"></script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>出货时间从</label>
                    <input type="text" id="completeDateFrom" name="completeDateFrom" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>                        
                    <label>至</label>
                    <input type="text" id="completeDateTo" name="completeDateTo" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" name="address"/>
                </li>
                <li>
                    <label>供应商</label>
                    <input type="text" id="supplCode" name="supplCode" />
                </li>
                <li>
                    <label>任务类型</label>
                    <house:DictMulitSelect id="jobType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
                        sql="select Code, Descr from tJobType where Code in('08', '09', '27', '28')"></house:DictMulitSelect>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
                    <button type="button" class="btn btn-system btn-sm" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="INTDELIVERANALYSIS_EXCEL">
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
    
        $("#supplCode").openComponent_supplier({
            condition: {itemType1: "JC", readonly: "1"}
        })
        
        Global.JqGrid.initJqGrid("dataTable", {
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
                {name: "appdate", index: "appdate", width: 100, label: "下单日期", sortable: true, align: "left", formatter: formatDate},
                {name: "plandelivdate", index: "plandelivdate", width: 100, label: "计划出货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "jobtypedescr", index: "jobtypedescr", width: 100, label: "任务类型", sortable: true, align: "left"},
                {name: "supplierdescr", index: "supplierdescr", width: 150, label: "供应商", sortable: true, align: "left"},
                {name: "date", index: "date", width: 80, label: "申请出货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "assigneddate", index: "assigneddate", width: 80, label: "指派日期", sortable: true, align: "left", formatter: formatDate},
                {name: "completedate", index: "completedate", width: 130, label: "任务完成日期", sortable: true, align: "left", formatter: formatTime},
                {name: "inwhdate", index: "inwhdate", width: 130, label: "货好时间", sortable: true, align: "left", formatter: formatTime},
                {name: "overduedays", index: "overduedays", width: 80, label: "超期天数", sortable: true, align: "right"},
                {name: "supplremarks", index: "supplremarks", width: 180, label: "供应商备注", sortable: true, align: "left"},
                {name: "senddate", index: "senddate", width: 130, label: "最早发货时间", sortable: true, align: "left", formatter: formatTime},
            ]
        })

        $("#dataTable").setGridParam({url: "${ctx}/admin/intDeliverAnalysis/goJqGrid"})
        
    })

    function exportExcel() {
        doExcelAll("${ctx}/admin/intDeliverAnalysis/doExcel")
    }
    
    function clearForm() {
        $("#page_form input[type=text]").val('')
        $("#page_form select").val('')
        
        $("#jobType").val('')
        $.fn.zTree.getZTreeObj("tree_jobType").checkAllNodes(false)
    } 

</script>
</body>
</html>
