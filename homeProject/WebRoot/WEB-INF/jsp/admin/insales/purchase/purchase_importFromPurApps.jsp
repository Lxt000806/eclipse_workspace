<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_purchaseApp.js?v=${v}" type="text/javascript"></script>

    <script type="text/javascript">

        function SelectedRowArray() {
            this.rows = [];

            this.push = function(row) {
                this.rows.push(row);
            };

            this.getRows = function() {
                return this.rows;
            };

            this.getColumn = function(columnName) {
                if (!columnName) return [];

                var column = [];
                for (var i = 0; i < this.rows.length; i++) {
                    column.push(this.rows[i][columnName]);
                }

                return column;
            };
        }

        var selectedRowArray = new SelectedRowArray();

        var grid;

        function getGrid() {
            return grid || $("#dataTable");
        }

        function addToSelectedRows() {
            var grid = getGrid();
            var ids = grid.getGridParam("selarrrow");
            
            if (ids.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < ids.length; i++) {
                selectedRowArray.push(grid.getRowData(ids[i]));
            }

            reloadGrid();
        }

        function reloadGrid() {
            var grid = getGrid();
            var pageForm = $("#page_form");
            var params = pageForm.jsonForm();
            var selectedAppDetailPks = selectedRowArray.getColumn("pk");
            var savedAppDetailPks = params.savedAppDetailPks
                                    ? params.savedAppDetailPks.split(",") : [];

            [].push.apply(selectedAppDetailPks, savedAppDetailPks);
            
            delete params.savedAppDetailPks;
            params.savedAppDetailPks = selectedAppDetailPks.join(",");

            grid.setGridParam({
                url: "${ctx}/admin/purchaseApp/exportingPurchaseAppDetailJqGrid",
                postData: params
            }).trigger("reloadGrid");
        }

        function saveSelectedRowsAndCloseWin() {
            Global.Dialog.returnData = selectedRowArray.getRows();
            closeWin();
        }

        function goto_query() {
            var no = $("#no").val();
            
            if (!no) {
                art.dialog({content: "请选择采购申请单号!"});
                return;
            }
            
            reloadGrid();
        }

        $(function() {
            $("#no").openComponent_purchaseApp({
                condition: {
                    status: "1",
                    itemType1: "${purchaseApp.itemType1}",
                    disabledElements: "status,itemType1"
                }
            });
            $("#supplier").openComponent_supplier({
                showLabel: "",
                showValue: "${purchaseApp.supplier}",
                condition: {
                    itemType1: "${purchaseApp.itemType1}",
                    readonly: '1'
                }
            });
            $("#openComponent_supplier_supplier").blur();

            Global.JqGrid.initJqGrid("dataTable", {
                height: $(document).height() - $("#content-list").offset().top - 80,
                rowNum: 10000,
                multiselect: true,
                colModel: [
                    {name: "pk", index: "pk", width: 70, label: "PK", sortable: true, align: "left", hidden: true},
                    {name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
                    {name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
                    {name: "qty", index: "qty", width: 80, label: "申请数量", sortable: true, align: "right"},
                    {name: "unitdescr", index: "unitdescr", width: 80, label: "单位", sortable: true, align: "left"},
                    {name: "supplierdescr", index: "supplierdescr", width: 150, label: "供应商", sortable: true, align: "left"},
                    {name: "remark", index: "remark", width: 300, label: "备注", sortable: true, align: "left"},
                    {name: "branddescr", index: "branddescr", width: 97, label: "品牌", sortable: true, align: "left", hidden: true},
                    {name: "itemtype2", index: "itemtype2", width: 100, label: "材料类型2", sortable: true, align: "left", hidden: true},
                    {name: "allqty", index: "allqty", width: 80, label: "库存", sortable: true, align: "left", hidden: true},
                    {name: "price", index: "price", width: 100, label: "单价", sortable: true, align: "left", hidden: true},
                    {name: "cost", index: "cost", width: 80, label: "进价", sortable: true, align: "left", hidden: true},
                    {name: "color", index: "color", width: 80, label: "颜色", sortable: true, align: "left", hidden: true},
                    {name: "projectcost", index: "projectcost", width: 80, label: "项目经理结算价", sortable: true, align: "left", hidden: true},
                    {name: "purqty", index: "purqty", width: 100, label: "在途采购量", sortable: true, align: "left", hidden: true},
                    {name: "useqty", index: "useqty", width: 100, label: "可用量", sortable: true, align: "left", hidden: true},
                ],
            });

        });

    </script>
</head>

<body>
<div class="body-box-list">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick="addToSelectedRows()"><span>保存</span></button>
                <button type="button" class="btn btn-system" onclick="saveSelectedRowsAndCloseWin()"><span>关闭</span></button>
            </div>
        </div>
    </div>
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="savedAppDetailPks" value="${purchaseApp.savedAppDetailPks}">
            <input type="hidden" name="itemType1" value="${purchaseApp.itemType1}">
            <ul class="ul-form">
                <li>
                    <label>采购申请单号</label>
                    <input type="text" id="no" name="no" value=""/>
                </li>
                <li>
                    <label>供应商</label>
                    <input type="text" id="supplier" name="supplier" value=""/>
                </li>
                <li class="search-group">
                    <button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
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
