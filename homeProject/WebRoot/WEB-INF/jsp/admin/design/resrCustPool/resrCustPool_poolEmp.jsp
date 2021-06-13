<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">
        
        var memberGrid;

        function getMemberGrid() {
            return !!memberGrid ? memberGrid : memberGrid = $("#dataTable_member");
        }

        $(function() {
            Global.JqGrid.initJqGrid("dataTable_member", {
                url: "${ctx}/admin/resrCustPool/member/goJqGrid",
                postData: {no: "${resrCustPool.no}"},
                multiselect: true,
                rowNum: 10000,
                height: 500,
                colModel: [
                    {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                    {name: "type", index: "type", width: 50, label: "类型", sortable: true, align: "left", hidden: true},
                    {name: "czybh", index: "czybh", width: 80, label: "操作员编号", sortable: true, align: "left"},
                    {name: "zwxm", index: "zwxm", width: 80, label: "姓名", sortable: true, align: "left"},
                    {name: "onleave", index: "onleave", width: 50, label: "休假中", sortable: true, align: "left", hidden: true},
                    {name: "state", index: "state", width: 50, label: "状态", sortable: true, align: "left", cellattr: leaveState},
                    {name: "weight", index: "weight", width: 50, label: "权重", sortable: true, align: "right"},
                    {name: "groupsign", index: "groupsign", width: 120, label: "组标签", sortable: true, align: "left"},
                    {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right", hidden: true},
                    {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
                ],
                gridComplete: function() {

                },
            });

        });

        function getCheckedRows() {
            var grid = getMemberGrid();
            var ids = grid.getGridParam("selarrrow");
            var checkedRows = [];

            for (var i = 0; i < ids.length; i++)
                checkedRows.push(grid.getRowData(ids[i]));

            return checkedRows;
        }
        
        function leaveState(rowid, val, rowObject, cm, rdata) {
            return 'style="background-color:' + (rowObject.onleave ? 'LightGrey' : 'LimeGreen') + ';"';
        }

        function addMember() {
            Global.Dialog.showDialog("resrCustPoolAddMember", {
                title: "成员--新增",
                url: "${ctx}/admin/resrCustPool/member/goAdd",
                postData: {resrCustPoolNo: "${resrCustPool.no}"},
                height: 360,
                width: 400,
                returnFun: function() {
                    getMemberGrid().trigger("reloadGrid");
                }
            });
        }

        function updMember() {
            var rows = getCheckedRows();

            if (rows.length !== 1) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("resrCustPoolUpdateMember", {
                title: "成员--编辑",
                postData: rows[0],
                url: "${ctx}/admin/resrCustPool/member/goUpdate",
                height: 360,
                width: 400,
                returnFun: function() {
                    getMemberGrid().trigger("reloadGrid");
                }
            });
        }

        function importMember() {
            Global.Dialog.showDialog("resrCustPoolImportMember", {
                title: "成员--导入",
                url: "${ctx}/admin/resrCustPool/member/goImport",
                postData: {resrCustPoolNo: "${resrCustPool.no}"},
                height: 500,
                width: 800,
                returnFun: function(rows) {
                    getMemberGrid().trigger("reloadGrid");
                }
            });
        }

        function setMemberWeight() {
            var rows = getCheckedRows();
            var pks = [];

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < rows.length; i++)
                pks.push(rows[i].pk);

            Global.Dialog.showDialog("resrCustPoolSetMemberWeight", {
                title: "成员--设置权重",
                url: "${ctx}/admin/resrCustPool/member/goSetWeight",
                postData: {pks: pks.join(",")},
                height: 360,
                width: 400,
                returnFun: function() {
                    getMemberGrid().trigger("reloadGrid");
                }
            });
        }

        function setMemberGroupSign() {
            var rows = getCheckedRows();
            var pks = [];

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < rows.length; i++) {
                pks.push(rows[i].pk);
            }

            Global.Dialog.showDialog("resrCustPoolSetMemberGroupSign", {
                title: "成员--设置组标签",
                url: "${ctx}/admin/resrCustPool/member/goSetGroupSign",
                postData: {pks: pks.join(",")},
                height: 360,
                width: 400,
                returnFun: function(data) {
                    getMemberGrid().trigger("reloadGrid");
                }
            });
        }

        function delMember() {
            var rows = getCheckedRows();
            var pks = [];

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < rows.length; i++) {
                pks.push(rows[i].pk);
            }

            art.dialog({
                content: '确定删除选中的成员吗？',
                lock: true,
                ok: function() {
                    $.ajax({
                        url: "${ctx}/admin/resrCustPool/member/doDelete",
                        type: "POST",
                        data: {
                            resrCustPoolNo: "${resrCustPool.no}",
                            pks: pks.join(",")
                        },
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
                                        getMemberGrid().trigger("reloadGrid");
                                    }
                                });
                            } else {
                                art.dialog({content: obj.msg});
                            }
                        }
                    });
                },
                cancel: function() {
                }
            });
        }
        
        function switchMemberState() {
            var rows = getCheckedRows();

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }
            
            $.ajax({
                url: "${ctx}/admin/resrCustPool/member/doSwitchState",
                type: "POST",
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify(rows),
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
                                getMemberGrid().trigger("reloadGrid");
                            }
                        });
                    } else {
                        art.dialog({content: obj.msg});
                    }
                }
            });
        }
        
        function setMemberDefaultPool() {
            var rows = getCheckedRows();
            var czybhs = [];

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < rows.length; i++) {
                czybhs.push(rows[i].czybh);
            }
            
            $.ajax({
                url: "${ctx}/admin/resrCustPool/member/doSetDefaultPool",
                type: "POST",
                data: {
                    poolNo: "${resrCustPool.no}",
                    czybhs: czybhs
                },
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
                                getMemberGrid().trigger("reloadGrid");
                            }
                        });
                    } else {
                        art.dialog({content: obj.msg});
                    }
                }
            });
        }
        
    </script>
</head>
<body>
<div class="body-box-list">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button id="addMemberBtn" class="btn btn-system" onclick="addMember()">新增</button>
                <button id="updMemberBtn" class="btn btn-system" onclick="updMember()">编辑</button>
                <button id="importMemberBtn" class="btn btn-system" onclick="importMember()">从Excel导入</button>
                <button id="setMemberWeightBtn" class="btn btn-system" onclick="setMemberWeight()">设定权重</button>
                <button id="setMemberGroupSignBtn" class="btn btn-system" onclick="setMemberGroupSign()">设定组标签</button>
                <button id="delMemberBtn" class="btn btn-system" onclick="delMember()">删除</button>
                <button id="switchMemberStateBtn" class="btn btn-system" onclick="switchMemberState()">状态切换</button>
                <button id="setMemberDefaultPoolBtn" class="btn btn-system" onclick="setMemberDefaultPool()">设置默认线索池</button>
            </div>
        </div>
    </div>
    <table id="dataTable_member"></table>
</div>
</body>
</html>
