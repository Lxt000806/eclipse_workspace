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
	<title>客户标签管理</title>
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
                    <label>标签组名称</label>
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

<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="CUSTTAG_SAVE">
                <button type="button" class="btn btn-system" onclick="save()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="CUSTTAG_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="CUSTTAG_VIEW">
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
</div>
</body>

<script type="text/javascript">

var postData = $("#page_form").jsonForm()

$(function () {
    Global.JqGrid.initJqGrid("dataTable", {
        sortable: true,
        url: "${ctx}/admin/custTag/goJqGridList",
        postData: postData,
        height: $(document).height() - $("#content-list").offset().top - 80,
        colModel: [
            {name: "pk", index: "pk", width: 80, label: "主键", align: "left", key: true, hidden: true},
            {name: "descr", index: "descr", width: 100, label: "组名", sortable: true, align: "left"},
            {name: "color", index: "color", width: 40, label: "颜色", sortable: true, align: "left", cellattr: addCellAttr},
            {name: "ismultidescr", index: "ismultidescr", width: 80, label: "支持多选", sortable: true, align: "left"},
            {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "left"},
            {name: "crtname", index: "crtname", width: 100, label: "创建人", sortable: true, align: "left"},
            {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
            {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人", sortable: true, align: "left"},
            {name: "expired", index: "expired", width: 70, label: "过期", sortable: true, align: "left"},
            {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
        ],
        ondblClickRow: view
    })

})

function addCellAttr(rowId, val, rawObject, cm, rdata) {
    return "style='background-color:" + val + ";color:" + val + "'"
}

function save() {
    Global.Dialog.showDialog("add", {
        title: "客户标签管理-新增",
        url: "${ctx}/admin/custTag/goSave",
        height: 600,
        width: 780,
        returnFun: goto_query
    })
}

function update() {
    var row = selectDataTableRow()

    Global.Dialog.showDialog("update", {
        title: "客户标签管理-编辑",
        url: "${ctx}/admin/custTag/goUpdate",
        postData: {pk: row.pk},
        height: 600,
        width: 780,
        returnFun: goto_query
    })
}

function view() {
    var row = selectDataTableRow()

    Global.Dialog.showDialog("update", {
        title: "客户标签管理-查看",
        url: "${ctx}/admin/custTag/goView",
        postData: {pk: row.pk},
        height: 600,
        width: 780,
        returnFun: goto_query
    })
}

</script>
</html>
