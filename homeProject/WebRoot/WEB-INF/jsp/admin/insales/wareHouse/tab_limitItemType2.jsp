<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button id="addLimitItemType2Btn" class="btn btn-system" onclick="addLimitItemType2()">新增</button>
            <button id="updateLimitItemType2Btn" class="btn btn-system" onclick="updateLimitItemType2()">编辑</button>
            <button id="deleteLimitItemType2Btn" class="btn btn-system" onclick="deleteLimitItemType2()">删除</button>
        </div>
    </div>
</div>
<div>
    <table id="limitItemType2Table"></table>
</div>

<script>

    $(function() {
        Global.JqGrid.initJqGrid("limitItemType2Table", {
            height: 300,
            cellsubmit: "clientArray",
            colModel: [
                {name: "itemtype1", index: "itemtype1", width: 120, label: "材料类型1", align: "left", sortable: false, hidden: true},
                {name: "itemtype2", index: "itemtype2", width: 120, label: "材料类型2", align: "left", sortable: false, hidden: true},
                {name: "itemtype2descr", index: "itemtype2descr", width: 120, label: "材料类型2", align: "left", sortable: false},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "修改时间", sortable: false, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label:"修改人", sortable: false, align: "left"},
                {name: "expired", index: "expired", width: 50, label:"过期", sortable: false, align: "left"},
                {name: "actionlog", index: "actionlog", width: 50, label:"操作", sortable: false, align: "left"},
            ]
        })
        
        initializeItemType2Page("${wareHouse.m_umState}")
    })
    
    function initializeItemType2Page(action) {
        switch (action) {
            case "A":
                break
            case "C":
                break
            case "M":
                $("#limitItemType2Table")
                    .setGridParam({
                        url: "${ctx}/admin/wareHouse/goLimitItemType2JqGrid",
                        postData: {code: "${wareHouse.code}"}
                    }).trigger("reloadGrid")
                break
            case "V":
                $("#limitItemType2Table")
                    .setGridParam({
                        url: "${ctx}/admin/wareHouse/goLimitItemType2JqGrid",
                        postData: {code: "${wareHouse.code}"}
                    }).trigger("reloadGrid")
                $("#addLimitItemType2Btn").hide()
                $("#updateLimitItemType2Btn").hide()
                $("#deleteLimitItemType2Btn").hide()
                break
            default:
                break
        }
    }
    
    function addLimitItemType2() {
        Global.Dialog.showDialog("addLimitItemType2", {
            title: "配送品类-新增",
            url: "${ctx}/admin/wareHouse/addLimitItemType2",
            height: 330,
            width: 400,
            returnFun: function(data) {
                var grid = $("#limitItemType2Table")
                grid.addRowData(generateRowId("limitItemType2Table"), data, 'first')
            }
        })
    }
    
    function updateLimitItemType2() {
        var grid = $("#limitItemType2Table")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        Global.Dialog.showDialog("updateLimitItemType2", {
            title: "配送品类-编辑",
            url: "${ctx}/admin/wareHouse/updateLimitItemType2",
            postData: grid.getRowData(selectedRowId),
            height: 330,
            width: 400,
            returnFun: function(data) {
                grid.setRowData(selectedRowId, data)
            }
        })
    }
    
    function deleteLimitItemType2() {
        var grid = $("#limitItemType2Table")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        grid.delRowData(selectedRowId)
    }
    
</script>
