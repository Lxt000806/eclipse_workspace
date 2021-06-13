<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">

    var dispatchMemberGrid;
    
    function getDispatchMemberGrid() {
        return !!dispatchMemberGrid ? dispatchMemberGrid
                                    : dispatchMemberGrid = $("#dataTable_dispatch_member");
    }
    
    var deletedMemberPks = [];
    
    $(function() {
        $("#page_form_2").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                dispatchCZYScope: {
                    validators: {
                        notEmpty: {message: '派单成员范围不能为空'}
                    }
                },
                groupSign: {
                    validators: {
                        notEmpty: {message: '组标签不能为空'},
                    }
                }
            }
        });
    
        Global.JqGrid.initJqGrid("dataTable_dispatch_member", {
            url: "${ctx}/admin/resrCustPool/dispatch/member/goJqGrid",
            postData: {
                pk: "${resrCustPoolRule.dispatchCZYScope}" === "3" ? "${resrCustPoolRule.pk}" : ""
            },
            rowNum: 10000,
            height: 280,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                {name: "czybh", index: "czybh", width: 80, label: "操作员编号", sortable: true, align: "left"},
                {name: "zwxm", index: "zwxm", width: 80, label: "姓名", sortable: true, align: "left"},
                {name: "weight", index: "weight", width: 50, label: "权重", sortable: true, align: "right"},
                {name: "resrcustnumber", index: "resrcustnumber", width: 80, label: "待分配资源客户数", sortable: true, align: "right", hidden: true},
                {name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right", hidden: true},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
                {name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left", hidden: true},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left", hidden: true},
                {name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left", hidden: true},
            ],
            gridComplete: function() {

            },
        });
        
        changeDispatchScope($("#dispatchCZYScope").get(0));
    });
    
    function addDispatchMember() {
        var savedCzys =
                Global.JqGrid.allToJson("dataTable_dispatch_member", "czybh").fieldJson;
        
        Global.Dialog.showDialog("resrCustPoolAddDispatchMember", {
            title: "成员--新增",
            url: "${ctx}/admin/resrCustPool/dispatch/member/goAdd",
            postData: {
                resrCustPoolNo: "${resrCustPoolRule.resrCustPoolNo}",
                savedCzys: savedCzys
            },
            height: 550,
            width: 800,
            returnFun: function(selectedRows) {
                for (var i = 0; i < selectedRows.length; i++) {
                    var row = selectedRows[i];
                    
                    delete row.pk;
                    
                    row.resrcustnumber = row.weight;
                    row.dispseq = 0;
                    row.lastupdatedby = "${sessionScope.USER_CONTEXT_KEY.czybh}";
                    row.lastupdate = new Date();
                    row.actionlog = "ADD";
                    row.expired = "F";
                    
                    Global.JqGrid.addRowData("dataTable_dispatch_member", row);
                }
            }
        });
    }
    
    function updDispatchMember() {
        var grid = getDispatchMemberGrid();
        var rowid = grid.getGridParam("selrow");

        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        var row = grid.getRowData(rowid);

        Global.Dialog.showDialog("resrCustPoolUpdateDispatchMember", {
            title: "成员--编辑",
            postData: {
                weight: row.weight,
            },
            url: "${ctx}/admin/resrCustPool/dispatch/member/goUpdate",
            height: 360,
            width: 400,
            returnFun: function(row) {
                row.lastupdatedby = "${sessionScope.USER_CONTEXT_KEY.czybh}";
                row.lastupdate = new Date();
                row.actionlog = "EDIT";
                Global.JqGrid.updRowData("dataTable_dispatch_member", rowid, row);
            }
        });
    }
    
    function delDispatchMember() {
        var grid = getDispatchMemberGrid();
        var rowid = grid.getGridParam("selrow");
        
        if (!rowid) {
            art.dialog({content: "请选择一条记录！"});
            return;
        }
        
        deletedMemberPks.push(grid.getRowData(rowid).pk);
        Global.JqGrid.delRowData("dataTable_dispatch_member", rowid);
    }
    
    function changeDispatchScope(obj) {
        var groupSign = $("#groupSign");
        var dispatchMemberTab = $("#dispatchMemberTab");
        
        switch (obj.value) {
            case "1":
                groupSign.parent().hide();
                dispatchMemberTab.hide();
                break;
            case "2":
                groupSign.parent().show();
                dispatchMemberTab.hide();
                break;
            case "3":
                groupSign.parent().hide();
                dispatchMemberTab.show();
                break;
            default:
                groupSign.parent().hide();
                dispatchMemberTab.hide();
        }
    }

</script>

<form role="form" action="" method="post" id="page_form_2" class="form-search" target="targetFrame">
    <ul class="ul-form">
        <li class="form-validate">
            <label><span class="required">*</span>派单成员范围</label>
            <house:xtdm id="dispatchCZYScope" dictCode="DPCZYSCOPE" value="${resrCustPoolRule.dispatchCZYScope}"
                        onchange="changeDispatchScope(this)"></house:xtdm>
        </li>
        <li class="form-validate">
            <label><span class="required">*</span>组标签</label>
            <house:dict id="groupSign" dictCode="" value="${resrCustPoolRule.groupSign}"
                        sql="select a.GroupSign SQL_LABEL_KEY, a.GroupSign SQL_VALUE_KEY
							from tResrCustPoolEmp a
							where isnull(a.GroupSign, '') <> ''
							    and a.ResrCustPoolNo = '${resrCustPoolRule.resrCustPoolNo}'
							group by a.GroupSign"></house:dict>
        </li>
    </ul>
</form>
<div id="dispatchMemberTab" class="body-box-list">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button class="btn btn-system" onclick="addDispatchMember()">新增</button>
                <button class="btn btn-system" onclick="updDispatchMember()" style="display:none">编辑</button>
                <button class="btn btn-system" onclick="delDispatchMember()">删除</button>
            </div>
        </div>
    </div>
    <table id="dataTable_dispatch_member"></table>
</div>
