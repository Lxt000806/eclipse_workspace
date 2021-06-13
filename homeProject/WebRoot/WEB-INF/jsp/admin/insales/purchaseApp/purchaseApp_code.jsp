<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>搜寻——采购申请单号</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
    <script type="text/javascript">
        
        $(function() {
            var disabledElements = "${purchaseApp.disabledElements}".split(",");
            for (var i = 0; i < disabledElements.length; i++) {
                $("#" + disabledElements[i]).attr("disabled", true);
            }
        
            Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1");
            Global.LinkSelect.setSelect({
                firstSelect: 'itemType1',
                firstValue: '${purchaseApp.itemType1}',
                secondSelect: '',
                secondValue: '',
                thridSelect: '',
                thirdValue: '',
            });
            
            $("#whCode").openComponent_wareHouse();

            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/purchaseApp/goJqGrid",
                postData: $("#page_form").jsonForm(),
                height: $(document).height() - $("#content-list").offset().top - 70,
                colModel: [
                    {name: "no", index: "no", width: 80, label: "申请编号", sortable: true, align: "left"},
                    {name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true},
                    {name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
                    {name: "status", index: "status", width: 50, label: "状态", sortable: true, align: "left", hidden: true},
                    {name: "statusdescr", index: "statusdescr", width: 50, label: "状态", sortable: true, align: "left"},
                    {name: "whcode", index: "whcode", width: 80, label: "仓库编号", sortable: true, align: "left", hidden: true},
                    {name: "whdescr", index: "whdescr", width: 150, label: "仓库", sortable: true, align: "left"},
                    {name: "appczy", index: "appczy", width: 70, label: "申请人编号", sortable: true, align: "left", hidden: true},
                    {name: "appczydescr", index: "appczydescr", width: 80, label: "申请人", sortable: true, align: "left"},
                    {name: "appdate", index: "appdate", width: 70, label: "申请时间", sortable: true, align: "left", formatter: formatDate},
                    {name: "confirmczy", index: "confirmczy", width: 70, label: "审核人", sortable: true, align: "left", hidden: true},
                    {name: "confirmczydescr", index: "confirmczydescr", width: 80, label: "审核人", sortable: true, align: "left"},
                    {name: "confirmdate", index: "confirmdate", width: 70, label: "审核时间", sortable: true, align: "left", formatter: formatDate},
                    {name: "remark", index: "remark", width: 200, label: "备注", sortable: true, align: "left"},
                ],
                ondblClickRow: function(rowid, iRow, iCol, e) {
                    Global.Dialog.returnData = $(this).getRowData(rowid);
                    Global.Dialog.closeDialog();
                }
            });

        });
        
        function toggleIncludeAllDetailsPurchased(obj) {
            $("#includeAllDetailsPurchased").val(obj.checked ? "1" : "0");
        }

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form action="" method="post" id="page_form" class="form-search">
            <input type="hidden" id="includeAllDetailsPurchased" name="includeAllDetailsPurchased" value="0"/>
            <ul class="ul-form">
                <li>
                    <label>采购申请单号</label>
                    <input type="text" name="no"/>
                </li>
                <li>
                    <label>仓库</label>
                    <input type="text" id="whCode" name="whCode"/>
                </li>
                <li>
                    <label>材料类型1</label>
                    <select id="itemType1" name="itemType1"></select>
                </li>
                <li>
                    <label>状态</label>
                    <house:xtdm id="status" dictCode="PURAPPSTAT" value="${purchaseApp.status}"></house:xtdm>
                </li>
                <li>
                    <label>含全部明细已采购</label>
                    <input type="checkbox" name="includeAllDetailsPurchasedCheckbox" onclick="toggleIncludeAllDetailsPurchased(this)"/>
                </li>
                <li id="loadMore">
                    <button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
                </li>
            </ul>
        </form>
    </div>

    <div class="pageContent">
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</div>
</body>
</html>
