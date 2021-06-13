<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
    <title>客户类型分组管理</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <ul class="ul-form">
                <li>
                    <label>编号</label>
                    <input type="text" id="no" name="no"/>
                </li>
                <li>
                    <label>名称</label>
                    <input type="text" id="descr" name="descr"/>
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
</div>
<div class="btn-panel">
    <div class="btn-group-sm">
        <house:authorize authCode="CUSTTYPEGROUP_SAVE">
            <button type="button" class="btn btn-system" onclick="save()">
                <span>新增</span>
            </button>
        </house:authorize>
        <house:authorize authCode="CUSTTYPEGROUP_COPY">
            <button type="button" class="btn btn-system" onclick="copy()">
                <span>复制</span>
            </button>
        </house:authorize>
        <house:authorize authCode="CUSTTYPEGROUP_UPDATE">
            <button type="button" class="btn btn-system" onclick="update()">
                <span>编辑</span>
            </button>
        </house:authorize>
        <house:authorize authCode="CUSTTYPEGROUP_VIEW">
            <button type="button" class="btn btn-system" onclick="view()">
                <span>查看</span>
            </button>
        </house:authorize>
    </div>
</div>
<div id="content-list">
    <table id="dataTable"></table>
    <div id="dataTablePager"></div>
</div>
<script>

    $(function() {
        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/custTypeGroup/goJqGrid",
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "no", index: "no", width: 50, label: "编号", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 200, label: "名称", sortable: true, align: "left"},
                {name: "remarks", index: "remarks", width: 400, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
            ],
        })

    })

    function save() {
        Global.Dialog.showDialog("custTypeGroupSave", {
            title: "客户类型分组管理--新增",
            url: "${ctx}/admin/custTypeGroup/goSave",
            postData: {m_umState: "A"},
            height: 500,
            width: 700,
            returnFun: goto_query
        })
    }

    function copy() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("custTypeGroupUpdate", {
            title: "客户类型分组管理--复制",
            url: "${ctx}/admin/custTypeGroup/goCopy",
            postData: {m_umState: "C", no: ret.no},
            height: 500,
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

        Global.Dialog.showDialog("custTypeGroupUpdate", {
            title: "客户类型分组管理--编辑",
            url: "${ctx}/admin/custTypeGroup/goUpdate",
            postData: {m_umState: "M", no: ret.no},
            height: 500,
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

        Global.Dialog.showDialog("custTypeGroupView", {
            title: "客户类型分组管理--查看",
            url: "${ctx}/admin/custTypeGroup/goView",
            postData: {m_umState: "V", no: ret.no},
            height: 500,
            width: 700
        })
    }

</script>
</body>
</html>
