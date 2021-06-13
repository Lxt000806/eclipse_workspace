<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>固定资产类别管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <input type="hidden" name="jsonString" value="" />
            <ul class="ul-form">
                <li>
                    <label>名称</label>
                    <input type="text" id="descr" name="descr" style="width: 160px;"/>
                </li>
                <li>
                    <label>助记码</label>
                    <input type="text" id="remCode" name="remCode" style="width: 160px;"/>
                </li>
                <li>
                    <label>折旧方法</label>
                    <house:xtdm id="deprType" dictCode="ASSETDEPRTYPE"></house:xtdm>
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
<div class="clear_float"></div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="ASSETTYPE_SAVE">
                <button type="button" class="btn btn-system" onclick="save()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="ASSETTYPE_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="ASSETTYPE_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="ASSETTYPE_EXCEL">
                <button type="button" class="btn btn-system" onclick="exportExcel()">
                    <span>导出Excel</span>
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

<script type="text/javascript">

var postData = $("#page_form").jsonForm()

$(function () {
    Global.JqGrid.initJqGrid("dataTable", {
        sortable: true,
        url: "${ctx}/admin/assetType/goJqGridList",
        postData: postData,
        height: $(document).height() - $("#content-list").offset().top - 80,
        styleUI: "Bootstrap",
        colModel: [
            {name: "code", index: "code", width: 80, label: "主键", sortable: true, align: "left", hidden: true},
            {name: "descr", index: "descr", width: 100, label: "名称", sortable: true, align: "left"},
            {name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
            {name: "note", index: "note", width: 100, label: "折旧方法", sortable: true, align: "left"},
            {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
            {name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
            {name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
            {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
        ],
        ondblClickRow: view
    })

})

function save() {
    Global.Dialog.showDialog("add", {
        title: "固定资产类别管理-新增",
        url: "${ctx}/admin/assetType/goSave",
        height: 350,
        width: 400,
        returnFun: goto_query
    })
}

function update() {
    var row = selectDataTableRow()

    Global.Dialog.showDialog("update", {
        title: "固定资产类别管理-编辑",
        url: "${ctx}/admin/assetType/goUpdate",
        postData: {code: row.code},
        height: 350,
        width: 400,
        returnFun: goto_query
    })
}

function view() {
    var row = selectDataTableRow()

    Global.Dialog.showDialog("update", {
        title: "固定资产类别管理-查看",
        url: "${ctx}/admin/assetType/goView",
        postData: {code: row.code},
        height: 350,
        width: 400,
        returnFun: goto_query
    })
}

function exportExcel() {
    var url = "${ctx}/admin/assetType/doExcel";
    doExcelAll(url);
}

</script>
</html>
