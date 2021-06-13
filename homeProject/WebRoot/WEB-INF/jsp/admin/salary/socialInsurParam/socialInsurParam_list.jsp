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
    <title>社保公积金参数管理</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <input type="hidden" name="jsonString" value=""/>
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
            <house:authorize authCode="SOCIALINSURPARAM_SAVE">
                <button type="button" class="btn btn-system" onclick="save()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SOCIALINSURPARAM_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SOCIALINSURPARAM_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SOCIALINSURPARAM_EMPLIST">
                <button type="button" class="btn btn-system" onclick="viewEmpList()">
                    <span>人员名单</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SOCIALINSURPARAM_EXCEL">
                <button type="button" class="btn btn-system" onclick="excel()">
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
<script type="text/javascript">

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/socialInsurParam/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "ID", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 200, label: "名称", sortable: true, align: "left"},
                {name: "edminsurbasemin", index: "edminsurbasemin", width: 120, label: "养老保险最低基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "edminsurbasemax", index: "edminsurbasemax", width: 120, label: "养老保险最高基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "edminsurindrate", index: "edminsurindrate", width: 120, label: "养老保险个人费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "edminsurcmprate", index: "edminsurcmprate", width: 120, label: "养老保险单位费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "uneinsurindrate", index: "uneinsurindrate", width: 120, label: "失业保险个人费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "uneinsurcmprate", index: "uneinsurcmprate", width: 120, label: "失业保险单位费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "medinsurbasemin", index: "medinsurbasemin", width: 120, label: "医保最低基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "medinsurbasemax", index: "medinsurbasemax", width: 120, label: "医保最高基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "medinsurindrate", index: "medinsurindrate", width: 120, label: "医保个人费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "medinsurcmprate", index: "medinsurcmprate", width: 120, label: "医保单位费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "birthinsurcmprate", index: "birthinsurcmprate", width: 120, label: "生育保险单位费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "birthinsurbasemin", index: "birthinsurbasemin", width: 120, label: "生育保险最低基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "houfundbasemin", index: "houfundbasemin", width: 120, label: "公积金最低基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "houfundbasemax", index: "houfundbasemax", width: 120, label: "公积金最高基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "houfundindrate", index: "houfundindrate", width: 120, label: "公积金个人费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "houfundcmprate", index: "houfundcmprate", width: 120, label: "公积金单位费率", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "injuryinsurbasemin", index: "injuryinsurbasemin", width: 100, label: "工伤保险基数", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "injuryinsurrate", index: "injuryinsurrate", width: 100, label: "工伤保险费率", sortable: true, align: "right", formatter: "number", formatoptions: injuryinsurrateOptions},
                {name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            ondblClickRow: view
        })

    })

    var formatOptions = {decimalPlaces: 3, thousandsSeparator: ""}
    var injuryinsurrateOptions = {decimalPlaces: 4, thousandsSeparator: ""}

    function save() {
        Global.Dialog.showDialog("socialInsurParamSave", {
            title: "社保公积金参数管理--新增",
            url: "${ctx}/admin/socialInsurParam/goSave",
            height: 700,
            width: 750,
            returnFun: goto_query
        })
    }

    function update() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("socialInsurParamUpdate", {
            title: "社保公积金参数管理--编辑",
            url: "${ctx}/admin/socialInsurParam/goUpdate",
            postData: {pk: ret.pk},
            height: 720,
            width: 770,
            returnFun: goto_query
        })
    }

    function view() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("socialInsurParamView", {
            title: "社保公积金参数管理--查看",
            url: "${ctx}/admin/socialInsurParam/goView",
            postData: {pk: ret.pk},
            height: 700,
            width: 750,
        })
    }

    function viewEmpList() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("socialInsurParamEmpList", {
            title: "社保公积金参数管理--人员名单",
            url: "${ctx}/admin/socialInsurParam/goEmpList",
            postData: {pk: ret.pk},
            height: 650,
            width: 1000
        })
    }

    function excel() {
        doExcelAll("${ctx}/admin/socialInsurParam/doExcel")
    }

</script>
</body>
</html>
