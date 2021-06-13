<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

    var recoverGrid;
    
    function getRecoverGrid() {
        return !!recoverGrid ? recoverGrid : recoverGrid = $("#dataTable_recover");
    }
    
    $(function() {
        Global.JqGrid.initJqGrid("dataTable_recover", {
            url: "${ctx}/admin/resrCustPool/recover/goJqGrid",
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
	        getRecoverGrid().sortableRows({
	            update: function() { savaRecoverOrder(); }
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
                $("#addRecoverBtn,#updRecoverBtn,#delRecoverBtn,#recoverPrompt").hide();
                break;
            default:
        }
    });
    
    function savaRecoverOrder() {                
        $.ajax({
            url: "${ctx}/admin/resrCustPool/recover/saveOrder",
            type: "POST",
            data: {pks: getRecoverGrid().getCol("pk")},
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    getRecoverGrid().trigger("reloadGrid");
                } else {
                    art.dialog({content: obj.msg});
                }
            }
        });
    }
    
    function addRecover() {
        Global.Dialog.showDialog("resrCustPoolAddRecover", {
            title: "回收规则--新增",
            url: "${ctx}/admin/resrCustPool/recover/goAdd",
            postData: {resrCustPoolNo: "${resrCustPool.no}"},
            height: 360,
            width: 700,
            returnFun: function() {
                getRecoverGrid().trigger("reloadGrid");
            }
        });
    }
    
    function updRecover() {
        var grid = getRecoverGrid();
        var rowid = grid.getGridParam("selrow");

        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        var row = grid.getRowData(rowid);

        Global.Dialog.showDialog("resrCustPoolUpdateRecover", {
            title: "回收规则--编辑",
            postData: {pk: row.pk},
            url: "${ctx}/admin/resrCustPool/recover/goUpdate",
            height: 360,
            width: 700,
            returnFun: function() {
                getRecoverGrid().trigger("reloadGrid");
            }
        });
    }
    
    function delRecover() {
        var grid = getRecoverGrid();
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
		            url: "${ctx}/admin/resrCustPool/recover/doDelete",
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
                <button id="addRecoverBtn" class="btn btn-system" onclick="addRecover()">新增</button>
                <button id="updRecoverBtn" class="btn btn-system" onclick="updRecover()">编辑</button>
                <button id="delRecoverBtn" class="btn btn-system" onclick="delRecover()">删除</button>
                <span id="recoverPrompt" class="prompt">表格支持拖动排序</span>
            </div>
        </div>
    </div>
    <table id="dataTable_recover"></table>
</div>
