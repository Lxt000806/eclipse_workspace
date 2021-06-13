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
    
	    function SelectedRows() {
	        this.rows = [];
	        
	        this.push = function(row) {
	            this.rows.push(row);
	        }
	        
	        this.getRows = function() {
	            return this.rows;
	        }
	        
	        this.getCellOfEachRow = function(cellName) {
	            if (!cellName) return [];
	            
	            var result = [];
	            for (var i = 0; i < this.rows.length; i++) {
	                result.push(this.rows[i][cellName])
	            }
	            
	            return result;
	        }
	    }
	
	    var selectedRows = new SelectedRows();

        var memberGrid;

        function getMemberGrid() {
            return !!memberGrid ? memberGrid : memberGrid = $("#dataTable_member");
        }

        $(function() {
            Global.JqGrid.initJqGrid("dataTable_member", {
                url: "${ctx}/admin/resrCustPool/member/goJqGrid",
                postData: {
                    no: "${resrCustPoolRule.resrCustPoolNo}",
                    excludedCzys: "${resrCustPoolRule.savedCzys}"
                },
                multiselect: true,
                rowNum: 10000,
                height: 400,
                colModel: [
                    {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
                    {name: "type", index: "type", width: 50, label: "类型", sortable: true, align: "left", hidden: true},
                    {name: "czybh", index: "czybh", width: 80, label: "操作员编号", sortable: true, align: "left"},
                    {name: "zwxm", index: "zwxm", width: 80, label: "姓名", sortable: true, align: "left"},
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


	    function addToSelectedRows() {
	        var dataTable = getMemberGrid();
	        var ids = dataTable.getGridParam("selarrrow");
	        
	        for (var i = 0; i < ids.length; i++) {
	            selectedRows.push(dataTable.getRowData(ids[i]));
	        }
	
	        reloadGrid();
	    }
	    
	    function reloadGrid() {
	        var grid = getMemberGrid();
	        var pageForm = $("#page_form");
	        var params = pageForm.jsonForm();
	        var excludedCzysArray = selectedRows.getCellOfEachRow("czybh");
	        var savedCzysArray = params.savedCzys.split(",");
	        
	        [].push.apply(excludedCzysArray, savedCzysArray);
	        
	        params.excludedCzys = excludedCzysArray.join(",");
	        
	        grid.setGridParam({
	            url: "${ctx}/admin/resrCustPool/member/goJqGrid",
	            postData: params
	        }).trigger("reloadGrid");
	    }
	    
	    function saveSelectedRowsAndCloseWin() {
	        Global.Dialog.returnData = selectedRows.getRows();
	        closeWin(true);
	    }

    </script>
</head>
<body>
<div class="panel panel-system">
    <div class="panel-body">
        <div class="btn-group-xs">
            <button type="button" class="btn btn-system" onclick='addToSelectedRows()'>保存</button>
            <button type="button" class="btn btn-system" onclick='saveSelectedRowsAndCloseWin()'>关闭</button>
        </div>
    </div>
</div>
<div class="body-box-list">
    <div class="query-form" style="display: none;">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="savedCzys" value="${resrCustPoolRule.savedCzys}"/>
            <ul class="ul-form">

                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="reloadGrid()">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">

            </div>
        </div>
        <div id="content-list">
            <table id="dataTable_member"></table>
        </div>
    </div>
</div>
</body>
</html>
