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
    <title>固定资产查询</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">
        $(function() {
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/assetQuery/goJqGrid",
                height: $(document).height() - $("#content-list").offset().top - 70,
                styleUI: 'Bootstrap',
                colModel: [
                    {name: "code", index: "code", width: 70, label: "资产编号", sortable: true, align: "left",},
                    {name: "descr", index: "descr", width: 150, label: "资产名称", sortable: true, align: "left"},
                    {name: "model", index: "model", width: 120, label: "规格型号", sortable: true, align: "left"},
                    {name: "assettypedescr", index: "assettypedescr", width: 80, label: "类别名称", sortable: true, align: "left",},
                    {name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
                    {name: "addtypedescr", index: "addtypedescr", width: 80, label: "增加方式", sortable: true, align: "left"},
                    {name: "departmentdescr", index: "departmentdescr", width: 100, label: "部门", sortable: true, align: "left",},
                    {name: "usemanname", index: "usemanname", width: 60, label: "使用人", sortable: true, align: "left",},
                    {name: "statusdescr", index: "statusdescr", width: 70, label: "使用状况", sortable: true, align: "left"},
                    {name: "address", index: "address", width: 120, label: "存放地点", sortable: true, align: "left"},
                    {name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",},
                    {name: "originalvalue", index: "originalvalue", width: 80, label: "原值", sortable: true, align: "right", formatter:"number", formatoptions: {thousandsSeparator: " ", decimalPlaces: 2}},
                    {name: "begindate", index: "begindate", width: 100, label: "开始使用日期", sortable: true, align: "left", formatter: formatDate},
                    {name: "useyear", index: "useyear", width: 70, label: "使用年限", sortable: true, align: "right"},
                    {name: "createname", index: "createname", width: 80, label: "录入人", sortable: true, align: "left"},
                    {name: "createdate", index: "createdate", width: 80, label: "录入日期", sortable: true, align: "left", formatter: formatDate},
                ],
            })

        })

        function exportExcel() {
            var url = "${ctx}/admin/assetQuery/doExcel"
            doExcelAll(url)
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
                    <label>资产类别</label>
                    <house:dict id="assetType" dictCode=""
                        sql="select a.Code, a.RemCode + ' ' + a.Descr Descr from tAssetType a where a.Expired != 'T'"
                        sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="ASSETQUERY_EXCEL">
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
</body>
</html>
