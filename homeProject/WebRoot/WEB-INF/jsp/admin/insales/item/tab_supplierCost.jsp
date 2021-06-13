<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button id="addSupplCostBtn" class="btn btn-system" onclick="addSupplierCost()">新增</button>
            <button id="updateSupplCostBtn" class="btn btn-system" onclick="updateSupplierCost()">编辑</button>
            <button id="deleteSupplCostBtn" class="btn btn-system" onclick="deleteSupplierCost()">删除</button>
            <span style="color:red;margin-left:20px;vertical-align:middle">说明：用于集采材料需从本地供应商调货的采购价。</span>
        </div>
    </div>
</div>
<div>
    <table id="supplierCostTable"></table>
</div>

<script>

    $(function() {
        Global.JqGrid.initJqGrid("supplierCostTable", {
            height: 450,
            cellsubmit: "clientArray",
            colModel: [
                {name: "pk", index: "pk", width: 80, label: "pk", align: "left", sortable: false, hidden: true},
                {name: "supplcode", index: "supplcode", width: 80, label: "供应商", align: "left", sortable: false, hidden: true},
                {name: "suppldescr", index: "suppldescr", label: "供应商", width: 80, align: "left", sortable: false},
                {name: "cost", index: "cost", label: "供货价", width: 100, align: "right", sortable: false, formatter: 'number'},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "修改时间", sortable: false, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label:"修改人", sortable: false, align: "left"},
            ]
        })
        
        initializePage("${item.m_umState}")
    })
    
    function initializePage(action) {
        switch (action) {
            case "A":
                break
            case "C":
                break
            case "M":
                $("#supplierCostTable")
                    .setGridParam({
                        url: "${ctx}/admin/item/goSupplCostJqGrid",
                        postData: {code: "${item.code}"}
                    }).trigger("reloadGrid")
                break
            case "V":
                $("#supplierCostTable")
                    .setGridParam({
                        url: "${ctx}/admin/item/goSupplCostJqGrid",
                        postData: {code: "${item.code}"}
                    }).trigger("reloadGrid")
                $("#addSupplCostBtn").hide()
                $("#updateSupplCostBtn").hide()
                $("#deleteSupplCostBtn").hide()
                break
            default:
                break
        }
    }
    
    function addSupplierCost() {
        Global.Dialog.showDialog("addSupplierCost", {
            title: "供应商供货价-新增",
            url: "${ctx}/admin/item/addSupplierCost",
            height: 330,
            width: 400,
            returnFun: function(data) {
                var grid = $("#supplierCostTable")
                grid.addRowData(generateRowId(), data, 'first')
            }
        })
    }
    
    function updateSupplierCost() {
        var grid = $("#supplierCostTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        Global.Dialog.showDialog("updateSupplierCost", {
            title: "供应商供货价-编辑",
            url: "${ctx}/admin/item/updateSupplierCost",
            postData: grid.getRowData(selectedRowId),
            height: 330,
            width: 400,
            returnFun: function(data) {
                grid.setRowData(selectedRowId, data)
            }
        })
    }
    
    function deleteSupplierCost() {
        var grid = $("#supplierCostTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        grid.delRowData(selectedRowId)
    }
    
    function generateRowId() {
        var grid = $("#supplierCostTable")
        var ids = grid.getDataIDs()
        
        var maxId = 0
        for (var i = 0; i < ids.length; i++) {
            var temporaryId = parseInt(ids[i])
            maxId = maxId >= temporaryId ? maxId : temporaryId
        }

        return ++maxId
    }
    
</script>
