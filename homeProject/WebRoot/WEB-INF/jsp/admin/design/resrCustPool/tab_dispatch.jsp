<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    var dispatchGrid;
    
    function getDispatchGrid() {
        return !!dispatchGrid ? dispatchGrid : dispatchGrid = $("#dataTable_dispatch");
    }
    
    $(function() {
        Global.JqGrid.initJqGrid("dataTable_dispatch", {
            url: "${ctx}/admin/resrCustPool/dispatch/goJqGrid",
            postData: {no: "${resrCustPool.no}"},
            rowNum: 10000,
            height: 400,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                {name: "descr", index: "descr", width: 200, label: "名称", sortable: false, align: "left"},
                {name: "ruleclass", index: "ruleclass", width: 80, label: "分类", sortable: true, align: "left", hidden: true},
                {name: "scope", index: "scope", width: 80, label: "资源范围", sortable: true, align: "left", hidden: true},
                {name: "type", index: "type", width: 50, label: "类型", sortable: true, align: "left", hidden: true},
                {name: "dispatchczyscope", index: "dispatchczyscope", width: 50, label: "派单成员范围", sortable: true, align: "left", hidden: true},
                {name: "groupsign", index: "groupsign", width: 120, label: "组标签", sortable: true, align: "left", hidden: true},
                {name: "toresrcustpoolno", index: "toresrcustpoolno", width: 80, label: "分配线索池编号", sortable: true, align: "right", hidden: true},
                {name: "sql", index: "sql", width: 80, label: "查询脚本", sortable: true, align: "right", hidden: true},
                {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right", hidden: true},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: false, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: false, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: false, align: "left"},
                {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: false, align: "left"},
            ],
            gridComplete: function() {

            },
        });
        
        if ("${resrCustPool.m_umState}" !== "V") {
	        getDispatchGrid().sortableRows({
	            update: function() { savaDispatchOrder(); }
	        });
	    }
        
        switch ("${resrCustPool.m_umState}") {
            case "A":
                break;
            case "C":
                break;
            case "M":
                break;
            case "V":
                $("#addDispatchBtn,#updDispatchBtn,#delDispatchBtn,#dispatchPrompt").hide();
                break;
            default:
        }
    });
    
    function savaDispatchOrder() {                
        $.ajax({
            url: "${ctx}/admin/resrCustPool/dispatch/saveOrder",
            type: "POST",
            data: {pks: getDispatchGrid().getCol("pk")},
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    getDispatchGrid().trigger("reloadGrid");
                } else {
                    art.dialog({content: obj.msg});
                }
            }
        });
    }
    
    function addDispatch() {
        Global.Dialog.showDialog("resrCustPoolAddDispatch", {
            title: "派单规则--新增",
            url: "${ctx}/admin/resrCustPool/dispatch/goAdd",
            postData: {resrCustPoolNo: "${resrCustPool.no}"},
            height: 700,
            width: 1250,
            returnFun: function() {
                getDispatchGrid().trigger("reloadGrid");
            }
        });
    }
    
    function updDispatch() {
        var grid = getDispatchGrid();
        var rowid = grid.getGridParam("selrow");

        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }

        Global.Dialog.showDialog("resrCustPoolUpdateDispatch", {
            title: "派单规则--编辑",
            postData: grid.getRowData(rowid),
            url: "${ctx}/admin/resrCustPool/dispatch/goUpdate",
            height: 700,
            width: 1250,
            returnFun: function() {
                grid.trigger("reloadGrid");
            }
        });
    }
    
    function delDispatch() {
        var grid = getDispatchGrid();
        var rowid = grid.getGridParam("selrow");

        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        var row = grid.getRowData(rowid);
        
        art.dialog({
            content:'确定删此规则吗？',
            lock: true,
            ok: function() {
                $.ajax({
                    url: "${ctx}/admin/resrCustPool/dispatch/doDelete",
                    type: "POST",
                    data: {pk: row.pk},
                    dataType: "json",
                    error: function(obj) {
                        showAjaxHtml({
                            "hidden": false,
                            "msg": "保存数据出错~"
                        });
                    },
                    success: function(obj) {
                        if (obj.rs) {
                            art.dialog({
                                content: obj.msg,
                                time: 1000,
                                beforeunload: function() {
                                    grid.trigger("reloadGrid");
                                }
                            });
                        } else {
                            art.dialog({content: obj.msg});
                        }
                    }
                });
            },
            cancel: function() {}
        });
    }

</script>
<div class="body-box-list">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button id="addDispatchBtn" class="btn btn-system" onclick="addDispatch()">新增</button>
                <button id="updDispatchBtn" class="btn btn-system" onclick="updDispatch()">编辑</button>
                <button id="delDispatchBtn" class="btn btn-system" onclick="delDispatch()">删除</button>
                <span id="dispatchPrompt" class="prompt">表格支持拖动排序</span>
            </div>
        </div>
    </div>
    <table id="dataTable_dispatch"></table>
</div>
