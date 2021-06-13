<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    var detailGrid;
    
    function getDetailGrid() {
        return !!detailGrid ? detailGrid : detailGrid = $("#dataTable_detail");
    }
    
    $(function() {
    
        switch ("${itemTransform.m_umState}") {
            case "A":
                break;
            case "C":
                break;
            case "M":
                break;
            case "V":
                $("#addDetailBtn,#updDetailBtn,#delDetailBtn").hide();
                break;
            default:
        }
    
        Global.JqGrid.initJqGrid("dataTable_detail", {
            url: "${ctx}/admin/itemTransform/detail/goJqGrid",
            postData: {no: "${itemTransform.no}"},
            rowNum: 10000,
            height: $(document).height() - $("#detailTableContainer").offset().top - 50,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                {name: "itemcode", index: "itemcode", width: 90, label: "源材料编号", sortable: true, align: "left"},
                {name: "itemdescr", index: "itemdescr", width: 300, label: "源材料名称", sortable: true, align: "left"},
                {name: "transformper", index: "transformper", width: 80, label: "转换系数", sortable: true, align: "right"},
            ],
            gridComplete: function() {

            },
        });
        

    });
    
    function addDetail() {
        var grid = getDetailGrid();
        var tableId = grid.get(0).id;
        
        Global.Dialog.showDialog("itemTransformAddDetail", {
            title: "转换明细--新增",
            url: "${ctx}/admin/itemTransform/detail/goAdd",
            postData: {},
            height: 360,
            width: 400,
            returnFun: function(data) {
                Global.JqGrid.addRowData(tableId, data);
            }
        });
    }
    
    function updDetail() {
        var grid = getDetailGrid();
        var tableId = grid.get(0).id;
        var rowId = grid.getGridParam("selrow");

        if (!rowId) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }

        Global.Dialog.showDialog("itemTransformUpdateDetail", {
            title: "转换明细--编辑",
            postData: grid.getRowData(rowId),
            url: "${ctx}/admin/itemTransform/detail/goUpdate",
            height: 360,
            width: 400,
            returnFun: function(data) {
                Global.JqGrid.updRowData(tableId, rowId, data);
            }
        });
    }
    
    function delDetail() {
        var grid = getDetailGrid();
        var tableId = grid.get(0).id;
        var rowId = grid.getGridParam("selrow");

        if (!rowId) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        art.dialog({
            content:'确定删除选中的转换明细吗？',
            lock: true,
            ok: function() {
		        Global.JqGrid.delRowData(tableId, rowId);
            },
            cancel: function() {}
        });
    }

</script>
<div class="body-box-list">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button id="addDetailBtn" class="btn btn-system" onclick="addDetail()">新增</button>
                <button id="updDetailBtn" class="btn btn-system" onclick="updDetail()">编辑</button>
                <button id="delDetailBtn" class="btn btn-system" onclick="delDetail()">删除</button>
            </div>
        </div>
    </div>
    <div id="detailTableContainer">
	    <table id="dataTable_detail"></table>
    </div>
</div>
