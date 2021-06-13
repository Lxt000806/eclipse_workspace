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
    <title>套内减项</title>
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
            url: "${ctx}/admin/itemChg/goSetDeductionsGrid",
            postData: {
                custCode: "${itemChg.custCode}",
                itemType2: "${itemChg.itemType2}"
            },
            height: $(document).height() - $("#content-list").offset().top - 70,
            rowNum: 1000,
            colModel: [
                {name: "pk", index: "pk", width: 100, label: "PK", sortable: true, align: "left"},
                {name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域", sortable: true, align: "left"},
                {name: "baseitemdescr", index: "baseitemdescr", width: 180, label: "基础项目", sortable: true, align: "left"},
                {name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
                {name: "uomdescr", index: "uomdescr", width: 70, label: "单位", sortable: true, align: "left"},
                {name: "unitprice", index: "unitprice", width: 70, label: "单价", sortable: true, align: "right"},
                {name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right"},
                {name: "remark", index: "remark", width: 260, label: "备注", sortable: true, align: "left"},
            ]
        })

    })

</script>
</body>
</html>
