<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>薪酬方案类型管理</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <ul class="ul-form">
                <li>
                    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
                    <button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="btn-panel">
        <div class="btn-group-sm">
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
            url: "${ctx}/admin/salarySchemeType/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "code", index: "code", width: 80, label: "ID", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 100, label: "名称", sortable: true, align: "left"},
                {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "left", formatter: "integer"},
                {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
        })

    })

</script>
</body>
</html>
