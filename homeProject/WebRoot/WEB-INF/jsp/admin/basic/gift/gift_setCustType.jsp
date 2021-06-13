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

        var grid;

        function getGrid() {
            return grid || $("#dataTable");
        }

        $(function() {
            Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "discAmtType");
            
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/gift/goJqGrid",
                multiselect: true,
                rowNum: 10000,
                height: $(document).height() - $("#content-list").offset().top - 90,
                colModel: [
                    {name: 'pk', index: 'pk', width: 50, label: '编号', sortable: true, align: "left", hidden: false},
                    {name: 'descr', index: 'descr', width: 200, label: '优惠标题', sortable: true, align: "left"},
                    {name: 'remarks', index: 'remarks', width: 300, label: '优惠项目说明', sortable: true, align: "left"},
                    {name: 'typedescr', index: 'typedescr', width: 80, label: '类型', sortable: true, align: "left"},
                    {name: 'disctypedescr', index: 'disctypedescr', width: 120, label: '优惠折扣类型', sortable: true, align: "left"},
                    {name: 'discamttypedescr', index: 'discamttypedescr', width: 120, label: '优惠金额分类', sortable: true, align: "left"},
                ],
            });

        });

        function getCheckedRows() {
            var grid = getGrid();
            var ids = grid.getGridParam("selarrrow");
            var checkedRows = [];

            for (var i = 0; i < ids.length; i++)
                checkedRows.push(grid.getRowData(ids[i]));

            return checkedRows;
        }

        function setCustType() {
            var rows = getCheckedRows();
            var giftPks = [];

            if (rows.length === 0) {
                art.dialog({content: "请至少选择一条记录！"});
                return;
            }

            for (var i = 0; i < rows.length; i++) {
                giftPks.push(rows[i].pk);
            }
            
		    Global.Dialog.showDialog("giftSelectCustType", {
		        title: "赠送项目管理--选择产品线",
		        url: "${ctx}/admin/gift/goSelectCustType",
		        postData: {giftPkString: giftPks.join(",")},
		        height: 450,
		        width: 500,
		        returnFun: goto_query
		    });
        }
        
        function clearForm() {
            $("#page_form input[type='text']").val("");
            $("#page_form input[type='hidden']").val("");
            $("#page_form select").val("");
            $.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);
        }

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <input type="hidden" id="expired" name="expired" value=""/>
            <ul class="ul-form">
                <li><label>客户类型</label>
                    <house:dict id="custType" dictCode=""
                                sql="select Code SQL_VALUE_KEY, Code + ' ' + Desc1 SQL_LABEL_KEY from tCusttype where Expired = 'F' order by cast(Code as int)">
                    </house:dict>
                </li>
                <li>
                    <label>类型</label>
                    <house:xtdmMulit id="type" dictCode="GIFTTYPE"></house:xtdmMulit>
                </li>
                <li>
                    <label>优惠折扣类型</label>
                    <house:xtdm id="discType" dictCode="GIFTDISCTYPE"></house:xtdm>
                </li>

                <li><label>优惠金额分类</label>
                    <select id="discAmtType" name="discAmtType"></select>
                </li>
                <li class="search-group">
                    <button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button id="setCustTypeBtn" class="btn btn-system" onclick="setCustType()">设定产品线</button>
            </div>
        </div>
    </div>
    <div id="content-list">
        <table id= "dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
</body>
</html>
