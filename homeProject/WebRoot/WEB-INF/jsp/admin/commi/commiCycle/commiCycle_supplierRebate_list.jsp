<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>商家返利信息</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}"></script>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}"></script>
    <script type="text/javascript">
    
        function clearForm() {
            $("#page_form input[type='text']").val("");
            $("#page_form select").val("");
        }
        
        function add() {
            Global.Dialog.showDialog("supplierRebateSave", {
                title: "商家返利信息--新增",
                url: "${ctx}/admin/commiCycle/supplierRebate/goSave",
                height: 450,
                width: 700,
                returnFun: goto_query
            });
        }

        function update() {
            var row =selectDataTableRow();
            
            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }
            
            Global.Dialog.showDialog("supplierRebateUpdate", {
                title: "商家返利信息--编辑",
                postData: {pk: row.pk},
                url: "${ctx}/admin/commiCycle/supplierRebate/goUpdate",
                height: 450,
                width: 700,
                returnFun: goto_query
            });
        }
        
        function importExcel() {
		    Global.Dialog.showDialog("supplierRebateImportExcel", {
		        title: "商家返利信息——从Excel导入",
		        url: "${ctx}/admin/commiCycle/supplierRebate/goImportExcel",
		        height: 700,
		        width: 1200,
		        returnFun: goto_query
		    });
		}

        $(function() {
            $("#custCode").openComponent_customer();
            $("#supplCode").openComponent_supplier();
            Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1");
            
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/commiCycle/goSupplierRebateJqGrid",
                postData: $("#page_form").jsonForm(),
                height: $(document).height() - $("#content-list").offset().top - 100,
                colModel: [
                    {name: "pk", index: "pk", width: 50, label: "PK", sortable: true, align: "left", hidden: true},
                    {name: "date", index: "date", width: 80, label: "销售日期", sortable: true, align: "left", formatter: formatDate},
                    {name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true},
                    {name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
                    {name: "supplcode", index: "supplcode", width: 80, label: "供应商名称", sortable: true, align: "left", hidden: true},
                    {name: "suppldescr", index: "suppldescr", width: 120, label: "供应商名称", sortable: true, align: "left"},
                    {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
                    {name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
                    {name: "itemdescr", index: "itemdescr", width: 120, label: "材料名称", sortable: true, align: "left"},
                    {name: "amount", index: "amount", width: 100, label: "返利总金额", sortable: true, align: "right"},
                    {name: "empcode", index: "empcode", width: 80, label: "干系人", sortable: true, align: "left", hidden: true},
                    {name: "empname", index: "empname", width: 80, label: "干系人", sortable: true, align: "left"},
                    {name: "commiamount", index: "commiamount", width: 100, label: "业务员提成", sortable: true, align: "right"},
                    {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
                    {name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
                ],
            });
        });

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>销售日期从</label>
                    <input type="text" id="dateFrom" name="dateFrom" class="i-date"
                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="dateTo" name="dateTo" class="i-date"
                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>客户编号</label>
                    <input type="text" id="custCode" name="custCode"/>
                </li>
                <li>
                    <label>材料类型1</label>
                    <select id="itemType1" name="itemType1"></select>
                </li>
                <li>
                    <label>供应商</label>
                    <input type="text" id="supplCode" name="supplCode"/>
                </li>
                <li>
                    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <button type="button" class="btn btn-system" onclick="add()">
                    <span>新增</span>
                </button>
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
                <button type="button" class="btn btn-system" onclick="importExcel()">
                    <span>从excel导入</span>
                </button>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</div>
</body>
</html>
