<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    
    var adminGrid;
    
    function getAdminGrid() {
        return !!adminGrid ? adminGrid : adminGrid = $("#dataTable_admin");
    }
    
    $(function() {
        Global.JqGrid.initJqGrid("dataTable_admin", {
            url: "${ctx}/admin/resrCustPool/admin/goJqGrid",
            postData: {no: "${resrCustPool.no}"},
            rowNum: 10000,
            height: 400,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                {name: "type", index: "type", width: 50, label: "类型", sortable: true, align: "left", hidden: true},
                {name: "czybh", index: "czybh", width: 100, label: "操作员编号", sortable: true, align: "left"},
                {name: "zwxm", index: "zwxm", width: 120, label: "姓名", sortable: true, align: "left"},
                {name: "weight", index: "weight", width: 50, label: "权重", sortable: true, align: "right", hidden: true},
                {name: "groupsign", index: "groupsign", width: 80, label: "组标签", sortable: true, align: "left", hidden: true},
                {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right", hidden: true},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
                {name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left", hidden: true},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left", hidden: true},
                {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left", hidden: true},
            ],
            gridComplete: function() {

            }
        });
        
        switch ("${resrCustPool.m_umState}") {
            case "A":
                break;
            case "C":
                break;
            case "M":
                break;
            case "V":
                $("#addAdminBtn,#delAdminBtn").hide();
                break;
            default:
        }
    });
    
    function addAdmin() {
        Global.Dialog.showDialog("resrCustPoolAddAdmin", {
            title: "管理员--新增",
            url: "${ctx}/admin/resrCustPool/admin/goAdd",
            postData: {resrCustPoolNo: "${resrCustPool.no}"},
            height: 330,
            width: 400,
            returnFun: function() {
                getAdminGrid().trigger("reloadGrid");
            }
        });
    }
    
    function delAdmin() {
        var grid = getAdminGrid();
        var rowid = grid.getGridParam("selrow");
        
        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        var row = grid.getRowData(rowid);
        
        art.dialog({
            content:'确定删除此管理员吗？',
            lock: true,
            ok: function() {
                $.ajax({
                    url: "${ctx}/admin/resrCustPool/admin/doDelete",
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
                <button id="addAdminBtn" class="btn btn-system" onclick="addAdmin()">新增</button>
                <button id="delAdminBtn" class="btn btn-system" onclick="delAdmin()">删除</button>
            </div>
        </div>
    </div>
    <table id="dataTable_admin"></table>
</div>
