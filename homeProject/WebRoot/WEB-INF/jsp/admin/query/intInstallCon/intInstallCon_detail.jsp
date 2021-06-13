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
    <title>补货详情</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>

<div class="pageContent">
    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>

<script type="text/javascript">
    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/intReplenishDT/goJqGridByNo",
            postData: {
                custCode: "${customer.code}",
                isCupboard: "${customer.workType12}" === "17" ? "0" : "1"
            },
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "no", index: "no", width: 70, label: "补货单号", sortable: true, align: "left"},
                {name: "sourcedescr", index: "sourcedescr", width: 80, label: "补货来源", sortable: true, align: "left"},
                {name: "intspldescr", index: "intspldescr", width: 100, label: "补货工厂", sortable: true, align: "left"},
                {name: "typedescr", index: "typedescr", width: 70, label: "补货类型", sortable: true, align: "left"},
                {name: "remarks", index: "remarks", width: 300, label: "补件详情", sortable: true, align: "left", cellattr: function() { return "style='white-space:normal !important;'" }},
                {name: "respartdescr", index: "respartdescr", width: 80, label: "责任人", sortable: true, align: "left"},
                {name: "date", index: "date", width: 80, label: "补货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "okdate", index: "okdate", width: 80, label: "货好日期", sortable: true, align: "left", formatter: formatDate},
                {name: "prearrivdate", index: "prearrivdate", width: 100, label: "计划到货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "arrivedate", index: "arrivedate", width: 100, label: "实际到货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "readydate", index: "readydate", width: 80, label: "货齐时间", sortable: true, align: "left", formatter: formatDate},
            ]
        })

    })
    
</script>
</body>
</html>
