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
    <title>薪酬结算企业管理</title>
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
            <house:authorize authCode="SALARYSETTLECMP_SAVE">
                <button type="button" class="btn btn-system" onclick="save()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYSETTLECMP_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYSETTLECMP_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYSETTLECMP_EMPLIST">
                <button type="button" class="btn btn-system" onclick="viewEmpList()">
                    <span>人员名单</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYSETTLECMP_EXCEL">
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
            url: "${ctx}/admin/salarySettleCmp/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "ID", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 200, label: "企业名称", sortable: true, align: "left"},
                {name: "bankdescr", index: "bankdescr", width: 200, label: "银行卡类型", sortable: true, align: "left"},
                {name: "actname", index: "actname", width: 200, label: "户名", sortable: true, align: "left"},
                {name: "cardid", index: "cardid", width: 200, label: "卡号", sortable: true, align: "left"},
                {name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            ondblClickRow: view
        })

    })

    function save() {
        Global.Dialog.showDialog("salarySettleCmpSave", {
            title: "薪酬结算企业管理--新增",
            url: "${ctx}/admin/salarySettleCmp/goSave",
            height: 350,
            width: 700,
            returnFun: goto_query
        })
    }

    function update() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("salarySettleCmpUpdate", {
            title: "薪酬结算企业管理--编辑",
            url: "${ctx}/admin/salarySettleCmp/goUpdate",
            postData: {pk: ret.pk},
            height: 350,
            width: 700,
            returnFun: goto_query
        })
    }

    function view() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("salarySettleCmpView", {
            title: "薪酬结算企业管理--查看",
            url: "${ctx}/admin/salarySettleCmp/goView",
            postData: {pk: ret.pk},
            height: 350,
            width: 700
        })
    }
    
    function viewEmpList() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }
        
        Global.Dialog.showDialog("salarySettleCmpEmpList", {
            title: "薪酬结算企业管理--人员名单",
            url: "${ctx}/admin/salarySettleCmp/goEmpList",
            postData: {pk: ret.pk},
            height: 650,
            width: 1000
        })
    }

    function excel() {
        doExcelAll("${ctx}/admin/salarySettleCmp/doExcel")
    }

</script>
</body>
</html>
