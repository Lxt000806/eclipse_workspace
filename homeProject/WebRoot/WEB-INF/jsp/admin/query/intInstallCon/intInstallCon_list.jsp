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
    <title>集成安装跟踪</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}"></script>
    <script src="${resourceRoot}/pub/component_worker.js?v=${v}"></script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>类型</label>
                    <house:DictMulitSelect id="workType12" dictCode="" selectedValue="17,18"
                        sql="select rtrim(Code) SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tWorkType12 where Code in ('17', '18')"></house:DictMulitSelect>
                </li>
                <li>
                    <label>出货供应商</label>
                    <input type="text" id="supplier" name="supplier" />
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" name="address"/>
                </li>
                <li>
                    <label>集成部</label>
                    <house:DictMulitSelect id="department2" dictCode=""
                        sql="select Code SQL_VALUE_KEY, Desc1 SQL_LABEL_KEY from tDepartment2 where DepType = '6' and Expired = 'F'"></house:DictMulitSelect>
                </li>
                <li>
                    <label>出货日期从</label>
                    <input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="sendDateTo" name="sendDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>完成日期从</label>
                    <input type="text" id="deliverDateFrom" name="deliverDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="deliverDateTo" name="deliverDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>客户状态</label>
                    <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="4" unShowValue="1,2,3"></house:xtdmMulit>
                </li>
                <li>
                    <label>安装状态</label>
                    <house:DictMulitSelect id="installationStatus" dictCode="" selectedValue="1,2,3"
                        sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY
                            from (values('1', '待安排'), ('2', '已安排'), ('3', '补货'), ('4', '已完成')) as InstallationStatus(Code, Descr)"></house:DictMulitSelect>
                </li>
                <li>
                    <label>工人编号</label>
                    <input type="text" id="workerCode" name="workerCode"/>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
                    <button type="button" class="btn btn-system btn-sm" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="INTINSTALLCON_EXCEL">
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

<script type="text/javascript">
    $(function() {

        $("#supplier").openComponent_supplier({
            condition: {itemType1: "JC", readonly: "1"}
        })
        
        $("#workerCode").openComponent_worker()

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/intInstallCon/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "worktype12", index: "worktype12", width: 70, label: "类型", sortable: true, align: "left", hidden: true},
                {name: "worktype12descr", index: "worktype12descr", width: 70, label: "类型", sortable: true, align: "left"},
                {name: "supplierdescr", index: "supplierdescr", width: 130, label: "出货工厂", sortable: true, align: "left"},
                {name: "code", index: "code", width: 150, label: "客户编号", sortable: true, align: "left", hidden: true},
                {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
                {name: "projectmanname", index: "projectmanname", width: 80, label: "项目经理", sortable: true, align: "left"},
                {name: "projectmanphone", index: "projectmanphone", width: 100, label: "项目经理电话", sortable: true, align: "left"},
                {name: "intdesignername", index: "intdesignername", width: 80, label: "集成设计师", sortable: true, align: "left"},
                {name: "intdesignerphone", index: "intdesignerphone", width: 100, label: "集成设计师电话", sortable: true, align: "left"},
                {name: "repcount", index: "repcount", width: 40, label: "补货", sortable: true, align: "left", cellattr: addCellAttr, formatter: replenishment},
                {name: "effort", index: "effort", width: 80, label: "工作量（平方米/延米）", sortable: true, align: "right", formatter: "number"},
                {name: "senddate", index: "senddate", width: 80, label: "出货日期", sortable: true, align: "left", formatter: formatDate},
                {name: "comedate", index: "comedate", width: 100, label: "安排进场日期", sortable: true, align: "left", formatter: formatDate},
                {name: "workername", index: "workername", width: 80, label: "安装师傅", sortable: true, align: "left"},
                {name: "deliverdate", index: "deliverdate", width: 80, label: "完成日期", sortable: true, align: "left", formatter: formatDate},
                {name: "cannotinsremark", index: "cannotinsremark", width: 150, label: "不能安装备注", sortable: true, align: "left", cellattr: function() { return "style='color:red;'" }},
            ],
            onCellSelect: showDetail
        })

    })
    
    function addCellAttr(rowId, val, rawObject, cm, rdata) {
        return "style='cursor: pointer; color: Blue;"
    }
    
    function replenishment(cellvalue, options, rowObject) {
        return rowObject.repcount > 0 ? '有' : '无'
    }
    
    function showDetail(rowid, iCol, cellcontent, e) {   
        var rowData = $("#dataTable").getRowData(rowid)
        var colModel = $("#dataTable").getGridParam("colModel")
        var columnTitle = colModel[iCol].name
        
        if (columnTitle === "repcount") {
            detail(rowData.code, rowData.address, rowData.worktype12)
        }
            
    }
        
    function detail(code, address, worktype12) {
        Global.Dialog.showDialog("intInstallConDetail", {
            title: "集成安装跟踪-补货明细【" + address + "】",
            postData: {code: code, workType12: worktype12},
            url: "${ctx}/admin/intInstallCon/goDetail",
            height: 550,
            width: 1000
        })
    }

    function exportExcel() {
        doExcelAll("${ctx}/admin/intInstallCon/doExcel")
    }

    function clearForm() {
        $("#page_form input[type=text]").val('')
        $("#page_form select").val('')

        $("#workType12").val('')
        $.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false)
        $("#department2").val('')
        $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false)
        $("#status").val('')
        $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false)
        $("#installationStatus").val('')
        $.fn.zTree.getZTreeObj("tree_installationStatus").checkAllNodes(false)
    }

</script>
</body>
</html>
