<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button id="addGroupDetailBtn" class="btn btn-system" onclick="addGroupDetail()">新增</button>
            <button id="updateGroupDetailBtn" class="btn btn-system" onclick="updateGroupDetail()">编辑</button>
            <button id="deleteGroupDetailBtn" class="btn btn-system" onclick="deleteGroupDetail()">删除</button>
        </div>
    </div>
</div>
<div>
    <table id="groupDetailTable"></table>
</div>

<script>

    $(function() {
        Global.JqGrid.initJqGrid("groupDetailTable", {
            height: 280,
            rowNum: 100,
            cellsubmit: "clientArray",
            colModel: [
                {name: "custtype", index: "custtype", width: 120, label: "客户类型编号", align: "left", sortable: false},
                {name: "custtypedescr", index: "custtypedescr", width: 180, label: "客户类型", align: "left", sortable: false},
            ]
        })

        initializeGroupDetailPage("${custTypeGroup.m_umState}")
    })

    function initializeGroupDetailPage(action) {
        switch (action) {
            case "A":
                break
            case "C":
                $("#groupDetailTable")
                    .setGridParam({
                        url: "${ctx}/admin/custTypeGroup/goGroupDetailJqGrid",
                        postData: {no: "${custTypeGroup.no}"}
                    }).trigger("reloadGrid")
                break
            case "M":
                $("#groupDetailTable")
                    .setGridParam({
                        url: "${ctx}/admin/custTypeGroup/goGroupDetailJqGrid",
                        postData: {no: "${custTypeGroup.no}"}
                    }).trigger("reloadGrid")
                break
            case "V":
                $("#groupDetailTable")
                    .setGridParam({
                        url: "${ctx}/admin/custTypeGroup/goGroupDetailJqGrid",
                        postData: {no: "${custTypeGroup.no}"}
                    }).trigger("reloadGrid")
                $("#addGroupDetailBtn").hide()
                $("#updateGroupDetailBtn").hide()
                $("#deleteGroupDetailBtn").hide()
                break
            default:
                break
        }
    }

    function addGroupDetail() {
        Global.Dialog.showDialog("addGroupDetail", {
            title: "客户类型分组明细-新增",
            url: "${ctx}/admin/custTypeGroup/addGroupDetail",
            height: 330,
            width: 400,
            returnFun: function(data) {
                var grid = $("#groupDetailTable")
                grid.addRowData(generateRowId("groupDetailTable"), data, 'first')
            }
        })
    }

    function updateGroupDetail() {
        var grid = $("#groupDetailTable")
        var selectedRowId = grid.getGridParam("selrow")

        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }

        Global.Dialog.showDialog("updateGroupDetail", {
            title: "客户类型分组明细-编辑",
            url: "${ctx}/admin/custTypeGroup/updateGroupDetail",
            postData: grid.getRowData(selectedRowId),
            height: 330,
            width: 400,
            returnFun: function(data) {
                grid.setRowData(selectedRowId, data)
            }
        })
    }

    function deleteGroupDetail() {
        var grid = $("#groupDetailTable")
        var selectedRowId = grid.getGridParam("selrow")

        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }

        grid.delRowData(selectedRowId)
    }

</script>
