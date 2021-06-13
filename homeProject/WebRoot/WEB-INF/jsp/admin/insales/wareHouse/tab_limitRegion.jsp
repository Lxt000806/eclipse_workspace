<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button id="addLimitRegionBtn" class="btn btn-system" onclick="addLimitRegion()">新增</button>
            <button id="updateLimitRegionBtn" class="btn btn-system" onclick="updateLimitRegion()">编辑</button>
            <button id="deleteLimitRegionBtn" class="btn btn-system" onclick="deleteLimitRegion()">删除</button>
        </div>
    </div>
</div>
<div>
    <table id="limitRegionTable"></table>
</div>

<script>

    $(function() {
        Global.JqGrid.initJqGrid("limitRegionTable", {
            height: 300,
            cellsubmit: "clientArray",
            colModel: [
                {name: "regioncode", index: "regioncode", width: 80, label: "区域", align: "left", sortable: false, hidden: true},
                {name: "regiondescr", index: "regiondescr", width: 80, label: "区域", align: "left", sortable: false},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "修改时间", sortable: false, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label:"修改人", sortable: false, align: "left"},
                {name: "expired", index: "expired", width: 50, label:"过期", sortable: false, align: "left"},
                {name: "actionlog", index: "actionlog", width: 50, label:"操作", sortable: false, align: "left"},
            ]
        })
        
        initializeLimitRegionPage("${wareHouse.m_umState}")
    })
    
    function initializeLimitRegionPage(action) {
        switch (action) {
            case "A":
                break
            case "C":
                break
            case "M":
                $("#limitRegionTable")
                    .setGridParam({
                        url: "${ctx}/admin/wareHouse/goLimitRegionJqGrid",
                        postData: {code: "${wareHouse.code}"}
                    }).trigger("reloadGrid")
                break
            case "V":
                $("#limitRegionTable")
                    .setGridParam({
                        url: "${ctx}/admin/wareHouse/goLimitRegionJqGrid",
                        postData: {code: "${wareHouse.code}"}
                    }).trigger("reloadGrid")
                $("#addLimitRegionBtn").hide()
                $("#updateLimitRegionBtn").hide()
                $("#deleteLimitRegionBtn").hide()
                break
            default:
                break
        }
    }
    
    function addLimitRegion() {
        Global.Dialog.showDialog("addLimitRegion", {
            title: "配送区域-新增",
            url: "${ctx}/admin/wareHouse/addLimitRegion",
            height: 330,
            width: 400,
            returnFun: function(data) {
                var grid = $("#limitRegionTable")
                grid.addRowData(generateRowId("limitRegionTable"), data, 'first')
            }
        })
    }
    
    function updateLimitRegion() {
        var grid = $("#limitRegionTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        Global.Dialog.showDialog("updateLimitRegion", {
            title: "配送区域-编辑",
            url: "${ctx}/admin/wareHouse/updateLimitRegion",
            postData: grid.getRowData(selectedRowId),
            height: 330,
            width: 400,
            returnFun: function(data) {
                grid.setRowData(selectedRowId, data)
            }
        })
    }
    
    function deleteLimitRegion() {
        var grid = $("#limitRegionTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        grid.delRowData(selectedRowId)
    }
    
</script>
