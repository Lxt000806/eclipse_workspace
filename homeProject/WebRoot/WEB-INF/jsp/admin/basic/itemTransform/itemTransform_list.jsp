<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>材料转换信息管理</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">

        function goSave() {
            Global.Dialog.showDialog("itemTransformSave", {
                title: "材料转换信息管理--新增",
                url: "${ctx}/admin/itemTransform/goSave",
                height: 650,
                width: 1000,
                returnFun: goto_query
            });
        }

        function goUpdate() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("itemTransformUpdate", {
                title: "材料转换信息管理--编辑",
                postData: {no: row.no},
                url: "${ctx}/admin/itemTransform/goUpdate",
                height: 650,
                width: 1000,
                returnFun: goto_query
            });
        }
        
        function goView() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("itemTransformView", {
                title: "材料转换信息管理--查看",
                postData: {no: row.no},
                url: "${ctx}/admin/itemTransform/goView",
                height: 650,
                width: 1000,
                returnFun: goto_query
            });
        }
        
	    function doExcel() {
	        doExcelAll("${ctx}/admin/itemTransform/doExcel");
	    }

        $(function() {
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/itemTransform/goJqGrid",
                postData: $("#page_form").jsonForm(),
                height: $(document).height() - $("#content-list").offset().top - 80,
                colModel: [
                    {name: "no", index: "no", width: 80, label: "转换编号", sortable: true, align: "left"},
                    {name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
                    {name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
                    {name: "remarks", index: "remarks", width: 320, label: "描述", sortable: true, align: "left"},
                    {name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
                ],
                ondblClickRow: goView
            });
        });

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>转换编号</label>
                    <input type="text" id="no" name="no"/>
                </li>
                <li>
                    <label>材料编号</label>
                    <input type="text" id="itemCode" name="itemCode"/>
                </li>
                <li>
                    <label>描述</label>
                    <input type="text" id="remarks" name="remarks"/>
                </li>
                <li>
                    <input type="checkbox" id="expired" name="expired" value="" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <house:authorize authCode="ITEMTRANSFORM_SAVE">
                    <button type="button" class="btn btn-system" onclick="goSave()">
                        <span>新增</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="ITEMTRANSFORM_UPDATE">
                    <button type="button" class="btn btn-system" onclick="goUpdate()">
                        <span>编辑</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="ITEMTRANSFORM_VIEW">
                    <button type="button" class="btn btn-system" onclick="goView()">
                        <span>查看</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="ITEMTRANSFORM_EXCEL">
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
</div>
</body>
</html>
