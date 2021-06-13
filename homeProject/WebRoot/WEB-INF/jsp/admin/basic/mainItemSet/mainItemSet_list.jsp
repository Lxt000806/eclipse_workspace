<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>主材套餐包管理</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">
        $(function() {

            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/mainItemSet/goJqGrid",
                height: $(document).height() - $("#content-list").offset().top - 70,
                colModel: [
                    {name: 'no', index: 'no', width: 70, label: '编号', sortable: true, align: "left"},
                    {name: 'descr', index: 'descr', width: 120, label: '名称', sortable: true, align: "left"},
                    {name: 'itemtype1descr', index: 'itemtype1descr', width: 80, label: '材料类型1', sortable: true, align: "left"},
                    {name: 'itemtype1', index: 'itemtype1', width: 70, label: '材料类型1编号', sortable: true, align: "left", hidden: true},
                    {name: 'isoutsetdescr', index: 'isoutsetdescr', width: 80, label: '套外材料', sortable: true, align: "left"},
                    {name: 'custtypedescr', index: 'custtypedescr', width: 80, label: '客户类型', sortable: true, align: "left"},
                    {name: 'custtype', index: 'CustType', width: 70, label: '客户类型编号', sortable: true, align: "left", hidden: true},
                    {name: 'remarks', index: 'remarks', width: 150, label: '备注', sortable: true, align: "left",},
                    {name: 'lastupdate', index: 'lastupdate', width: 150, label: '最后修改时间', sortable: true, align: "left", formatter: formatTime},
                    {name: 'lastupdatedby', index: 'lastupdatedby', width: 70, label: '修改人', sortable: true, align: "left"},
                    {name: 'expired', index: 'expired', width: 70, label: '是否过期', sortable: true, align: "left"},
                    {name: 'actionlog', index: 'actionlog', width: 70, label: '操作日志', sortable: true, align: "left"}
                ]
            });

        });

        function add() {
            Global.Dialog.showDialog("mainItemSetAdd", {
                title: "添加主材套餐包",
                url: "${ctx}/admin/mainItemSet/goSave",
                height: 600,
                width: 1000,
                returnFun: goto_query
            });
        }

        function update() {
            var ret = selectDataTableRow();
            if (ret) {
                Global.Dialog.showDialog("mainItemSetUpdate", {
                    title: "修改主材套餐包",
                    url: "${ctx}/admin/mainItemSet/goUpdate?id=" + ret.no,
                    height: 600,
                    width: 1000,
                    returnFun: goto_query
                });
            } else {
                art.dialog({
                    content: "请选择一条记录"
                });
            }
        }

        function view() {
            var ret = selectDataTableRow();
            if (ret) {
                Global.Dialog.showDialog("mainItemSetView", {
                    title: "查看主材套餐包",
                    url: "${ctx}/admin/mainItemSet/goView?id=" + ret.no,
                    height: 600,
                    width: 1000
                });
            } else {
                art.dialog({
                    content: "请选择一条记录"
                });
            }
        }
        
        function exportExcel() {
            doExcelAll("${ctx}/admin/mainItemSet/doExcel")
	    }
    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <input type="hidden" id="expired" name="expired"/>
            <ul class="ul-form">
                <li>
                    <label>编号</label>
                    <input type="text" id="no" name="no" value="${itemSet.no}"/>
                </li>
                <li>
                    <label>名称</label>
                    <input type="text" id="descr" name="descr" value="${itemSet.descr}"/>
                </li>
                <li>
                    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
                    <button type="button" class="btn  btn-system btn-sm" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="clear_float"></div>

    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <house:authorize authCode="MAINITEMSET_SAVE">
                    <button type="button" class="btn btn-system " onclick="add()">新增</button>
                </house:authorize>
                <house:authorize authCode="MAINITEMSET_UPDATE">
                    <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                </house:authorize>
                <house:authorize authCode="MAINITEMSET_DELETE">
                    <button type="button" class="btn btn-system " style="display:none" onclick="del()">删除</button>
                </house:authorize>
                <house:authorize authCode="MAINITEMSET_VIEW">
                    <button type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
                <house:authorize authCode="MAINITEMSET_EXCEL">
                    <button type="button" class="btn btn-system " onclick="exportExcel()">输出到Excel</button>
                </house:authorize>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</div>
</body>
</html>
