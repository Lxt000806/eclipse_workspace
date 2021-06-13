<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>查看主材套餐包</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
                <house:token></house:token>
                <ul class="ul-form">
                    <li>
                        <label><span class="required">*</span>编号</label>
                        <input type="text" id="no" name="no" value="${itemSet.no}" readonly/>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>名称</label>
                        <input type="text" id="descr" name="descr" value="${itemSet.descr}"/>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>材料类型1</label>
                        <select id="itemType1" name="itemType1" disabled></select>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>套外材料</label>
                        <house:xtdm id="isOutSet" dictCode="YESNO" value="${itemSet.isOutSet}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>客户类型</label>
                        <house:xtdm id="custType" dictCode="CUSTTYPE" value="${itemSet.custType}" unShowValue="0" ></house:xtdm>
                    </li>
                    <li>
                        <label>过期</label>
                        <input type="checkbox" name="expiredCheckbox" ${itemSet.expired == 'T' ? 'checked' : ''} disabled/>
                    </li>
                </ul>
                <ul class="ul-form">
                    <li class="form-validate">
                        <label class="control-textarea">备注</label>
                        <textarea id="remarks" name="remarks">${itemSet.remarks}</textarea>
                    </li>
                </ul>
            </form>
        </div>
    </div>
    <div class="clear_float"></div>
    <div class="pageContent">
        <div class="panel panel-system">
            <div class="panel-body">
                <div class="btn-group-xs">
                    <button type="button" class="btn btn-system" onclick="view()">查看</button>
                </div>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
    </div>
</div>
<script>
    $(function() {
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2", "itemType3");

        Global.LinkSelect.setSelect({
            firstSelect: "itemType1",
            firstValue: '${itemSet.itemType1}'
        });
        
        $("#isOutSet").attr("disabled", "true");
        $("#custType").attr("disabled", "true");

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/mainItemSet/goDetailJqGrid?no=" + "${itemSet.no}",
            height: $(document).height() - $("#content-list").offset().top - 60,
            rowNum: 10000000,
            colModel: [
                {name: 'custtypeitempk', index: 'custtypeitempk', width: 90, label: '套餐材料PK', sortable: true, align: "left"},
                {name: 'itemcode', index: 'itemcode', width: 80, label: '材料编号', sortable: true, align: "left"},
                {name: 'itemdescr', index: 'itemdescr', width: 150, label: '材料名称', sortable: true, align: "left"},
                {name: 'algorithm', index: 'algorithm', width: 100, label: '算法', sortable: true, align: "left", hidden: true},
                {name: 'algorithmdescr', index: 'algorithmdescr', width: 100, label: '算法', sortable: true, align: "left"},
                {name: 'algorithmper', index: 'algorithmper', width: 80, label: '算法系数', sortable: true, align: "right"},
                {name: 'algorithmdeduct', index: 'algorithmdeduct', width: 90, label: '算法扣除数', sortable: true, align: "right"},
                {name: 'qty', index: 'qty', width: 50, label: '数量', sortable: true, align: "right"},
                {name: 'cuttype', index: 'cuttype', width: 80, label: '切割类型', sortable: true, align: "left", hidden: true},
                {name: 'cuttypedescr', index: 'cuttypedescr', width: 80, label: '切割类型', sortable: true, align: "left"},
                {name: 'itemtypecode', index: 'itemtypecode', width: 110, label: '材料类型说明', sortable: true, align: "left", hidden: true},
                {name: 'itemtypedescr', index: 'itemtypedescr', width: 110, label: '材料类型说明', sortable: true, align: "left"},
                {name: 'remarks', index: 'remarks', width: 150, label: '备注', sortable: true, align: "left"},
                {name: 'lastupdate', index: 'lastupdate', width: 130, label: '修改时间', sortable: true, align: "left", formatter: formatTime},
                {name: 'expired', index: 'expired', width: 50, label: '过期', sortable: true, align: "left"},
                {name: 'actionlog', index: 'actionlog', width: 50, label: '操作', sortable: true, align: "left"}
            ]
        });
        
    });

    function view() {
        var isOutSet = $("#isOutSet").val();
        var custType = $("#custType").val();
        var grid = $("#dataTable");
        var selectedRowId = grid.getGridParam("selrow");
        var rowData = grid.getRowData(selectedRowId);
        rowData.isoutset = isOutSet
        rowData.custtype = custType
        
        if (rowData.length === 0) {
            art.dialog({content: "请选择一条记录"});
            return;
        }
        
        Global.Dialog.showDialog("mainItemSetViewDetail", {
            title: "查看主材套餐包",
            url: "${ctx}/admin/mainItemSet/goViewDetail",
            postData: rowData,
            height: 600,
            width: 1000
        });
    }

</script>
</body>
</html>
