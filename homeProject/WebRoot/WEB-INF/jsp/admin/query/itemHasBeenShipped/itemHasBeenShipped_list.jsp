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
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
    <script type="text/javascript">

        $(function() {
            $("#whCode").openComponent_wareHouse();
            $("#supplier").openComponent_supplier();
            
            Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1");
            
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/itemHasBeenShipped/goJqGrid",
                postData: $("#page_form").jsonForm(),
                height: $(document).height() - $("#content-list").offset().top - 80,
                colModel: [
					{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
					{name: "no", index: "no", width: 80, label: "领料单号", sortable: true, align: "left"},
					{name: "delivtypedescr", index: "delivtypedescr", width: 80, label: "配送方式", sortable: true, align: "left"},
					{name: "supplierdescr", index: "supplierdescr", width: 80, label: "供应商", sortable: true, align: "left"},
					{name: "warehousedescr", index: "warehousedescr", width: 100, label: "仓库", sortable: true, align: "left"},
					{name: "appdate", index: "appdate", width: 80, label: "申请日期", sortable: true, align: "left", formatter: formatDate},
					{name: "confirmdate", index: "confirmdate", width: 80, label: "审核日期", sortable: true, align: "left", formatter: formatDate},
					{name: "arrivedate", index: "arrivedate", width: 80, label: "预计到货日期", sortable: true, align: "left", formatter: formatDate},
					{name: "notifysenddate", index: "notifysenddate", width: 80, label: "通知发货日期", sortable: true, align: "left", formatter: formatDate},
					{name: "senddate", index: "senddate", width: 80, label: "发货日期", sortable: true, align: "left", formatter: formatDate},
					{name: "delaydays", index: "delaydays", width: 80, label: "延误天数", sortable: true, align: "right"},
					{name: "delayresondescr", index: "delayresondescr", width: 80, label: "延误原因", sortable: true, align: "left"},
					{name: "delayremark", index: "delayremark", width: 200, label: "延误说明", sortable: true, align: "left"},
					{name: "splremark", index: "splremark", width: 200, label: "供应商备注", sortable: true, align: "left"},
					{name: "mainsteward", index: "mainsteward", width: 80, label: "主材管家", sortable: true, align: "left"},
                ],
                
            });
        });
	    
        function doExcel() {
            doExcelAll("${ctx}/admin/itemHasBeenShipped/doExcel");
        }

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>楼盘</label>
                    <input type="text" name="address"/>
                </li>
                <li>
                    <label>材料类型1</label>
                    <select id="itemType1" name="itemType1"></select>
                </li>
                <li>
                    <label>发货类型</label>
                    <house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE"></house:xtdm>
                </li>
                <li>
                    <label>仓库</label>
                    <input type="text" id="whCode" name="whCode"/>
                </li>
                <li>
                    <label>供应商</label>
                    <input type="text" id="supplier" name="supplier"/>
                </li>
                <li>
                    <label>是否通知发货</label>
                    <house:xtdm id="whetherNotifySend" dictCode="YESNO"></house:xtdm>
                </li>
                <li>
                    <label>通知发货日期从</label> 
                    <input type="text" id="notifySendDateFrom" name="notifySendDateFrom" class="i-date"
                           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="notifySendDateTo" name="notifySendDateTo" class="i-date"
                           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>发货日期从</label> 
                    <input type="text" id="dateFrom" name="dateFrom" class="i-date"
                           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                           value="<fmt:formatDate value="${customer.dateFrom}" pattern='yyyy-MM-dd'/>" />
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="dateTo" name="dateTo" class="i-date"
                           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                           value="<fmt:formatDate value="${customer.dateTo}" pattern='yyyy-MM-dd'/>" />
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <house:authorize authCode="ITEMHASBEENSHIPPED_VIEW">
                    <button hidden class="">
                        <span>查看</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="ITEMHASBEENSHIPPED_EXCEL">
                    <button class="btn btn-system" onclick="doExcel()">
                        <span>输出到Excel</span>
                    </button>
                </house:authorize>
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
