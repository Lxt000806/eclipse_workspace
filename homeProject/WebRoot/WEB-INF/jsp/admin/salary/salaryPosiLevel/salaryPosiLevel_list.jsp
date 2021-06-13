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
    <title>薪酬岗位级别管理</title>
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
                    <label>前后端</label>
                    <house:xtdm id="isFront" dictCode="SALISFRONT"></house:xtdm>
                </li>
                <li>
                    <label>岗位类别</label>
                    <house:dict id="salaryPosiClass" dictCode="" sqlValueKey="PK" sqlLableKey="Descr"
                        sql="select PK, cast(PK as nvarchar(11)) + ' ' + Descr Descr from tSalaryPosiClass where Expired = 'F'"></house:dict>
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
            <house:authorize authCode="SALARYPOSILEVEL_SAVE">
                <button type="button" class="btn btn-system" onclick="goSave()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYPOSILEVEL_UPDATE">
                <button type="button" class="btn btn-system" onclick="goUpdate()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYPOSILEVEL_VIEW">
                <button type="button" class="btn btn-system" onclick="goView()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYPOSILEVEL_EXCEL">
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
            url: "${ctx}/admin/salaryPosiLevel/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "pk", index: "pk", width: 80, label: "级别编码", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 120, label: "级别名称", sortable: true, align: "left"},
                {name: "dispseq", index: "dispseq", width: 50, label: "序号", sortable: true, align: "right", hidden: true, formatter: "number", formatoptions: formatOptions},
                {name: "salaryposiclassdescr", index: "salaryposiclassdescr", width: 120, label: "岗位类别", sortable: true, align: "left"},
                {name: "isfront", index: "isfront", width: 70, label: "前后端", sortable: true, align: "left", hidden:true},
                {name: "isfrontdescr", index: "isfrontdescr", width: 70, label: "前后端", sortable: true, align: "left"},
                {name: "minperfamount", index: "minperfamount", width: 110, label: "业绩指标/万元", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "salary", index: "salary", width: 80, label: "工资", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "basicsalary", index: "basicsalary", width: 80, label: "基本工资", sortable: true, align: "right", formatter: "number", formatoptions: formatOptions},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
            ],
            ondblClickRow: goView,
        })

    })
    
    var formatOptions = {decimalPlaces: 2, thousandsSeparator: ""}

    function goSave() {
        art.dialog({content: "此功能暂未实现，敬请期待"})
        return
        
        var ret = selectDataTableRow()
        
        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }
        
        Global.Dialog.showDialog("salaryPosiLevelSave", {
            title: "薪酬岗位级别管理--新增",
            url: "${ctx}/admin/salaryPosiLevel/goSave",
            postData: {pk: ret.pk},
            height: 350,
            width: 700,
            returnFun: goto_query
        })
    }

    function goUpdate() {
        var ret = selectDataTableRow()
        
        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }
        
        Global.Dialog.showDialog("salaryPosiLevelUpdate", {
            title: "薪酬岗位级别管理--编辑",
            url: "${ctx}/admin/salaryPosiLevel/goUpdate",
            postData: {pk: ret.pk},
            height: 350,
            width: 700,
            returnFun: goto_query
        })
    }

    function goView() {
        var ret = selectDataTableRow()
        
        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }
        
        Global.Dialog.showDialog("salaryPosiLevelView", {
            title: "薪酬岗位级别管理--查看",
            url: "${ctx}/admin/salaryPosiLevel/goView",
            postData: {pk: ret.pk},
            height: 350,
            width: 700
        })
    }

    function doExcel() {
        doExcelAll("${ctx}/admin/salaryPosiLevel/doExcel")
    }

</script>
</body>
</html>
