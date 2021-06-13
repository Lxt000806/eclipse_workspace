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
    <title>外部人员管理</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <input type="hidden" name="viewEmpType" value="1"/>
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>姓名</label>
                    <input type="text" id="nameChi" name="nameChi"/>
                </li>
                <li>
                    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
                    <button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
                    <button type="button" class="btn btn-system btn-sm" id="clear" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="OUTEMPLOYEE_SAVE">
                <button type="button" class="btn btn-system" onclick="goSave()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="OUTEMPLOYEE_UPDATE">
                <button type="button" class="btn btn-system" onclick="goUpdate()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="OUTEMPLOYEE_VIEW">
                <button type="button" class="btn btn-system" onclick="goView()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="OUTEMPLOYEE_EXCEL">
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
<script type="text/javascript">

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/outEmployee/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "number", index: "number", width: 100, label: "员工编号", sortable: true, align: "left", hidden: true},
                {name: "namechi", index: "namechi", width: 80, label: "姓名", sortable: true, align: "left"},
                {name: "sexdesc", index: "sexdesc", width: 50, label: "性别", sortable: true, align: "left"},
                {name: "idnum", index: "idnum", width: 130, label: "身份证号", sortable: true, align: "left"},
                {name: "phone", index: "phone", width: 90, label: "电话", sortable: true, align: "left"},
                {name: "cardid", index: "cardid", width: 130, label: "银行卡号", sortable: true, align: "left"},
                {name: "bank", index: "bank", width: 100, label: "开户行", sortable: true, align: "left"},
                {name: "department1descr", index: "department1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
                {name: "department2descr", index: "department2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
                {name: "department3descr", index: "department3descr", width: 100, label: "三级部门", sortable: true, align: "left"},
                {name: "statusdesc", index: "statusdesc", width: 70, label: "状态", sortable: true, align: "left"},
                {name: "joindate", index: "joindate", width: 80, label: "加入日期", sortable: true, align: "left", formatter: formatDate},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            ondblClickRow: goView,
        })

    })

    function goSave() {
        Global.Dialog.showDialog("outEmployeeSave", {
            title: "外部员工信息--新增",
            url: "${ctx}/admin/outEmployee/goSave",
            height: 520,
            width: 700,
            returnFun: goto_query
        })
    }

    function goUpdate() {
        var ret = selectDataTableRow()
        if (ret) {
            Global.Dialog.showDialog("outEmployeeUpdate", {
                title: "外部员工信息--修改",
                url: "${ctx}/admin/outEmployee/goUpdate",
                postData: {number: ret.number},
                height: 520,
                width: 700,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }

    function goView() {
        var ret = selectDataTableRow()
        if (ret) {
            Global.Dialog.showDialog("outEmployeeView", {
                title: "外部员工信息--查看",
                url: "${ctx}/admin/outEmployee/goView",
                postData: {number: ret.number},
                height: 520,
                width: 700
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }

    function doExcel() {
        doExcelAll("${ctx}/admin/outEmployee/doExcel")
    }

</script>
</body>
</html>
