<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>

<div class="body-box-list">
    <table id="dataTable_GiftAppDetail"></table>
</div>

<script type="text/javascript">
    $(function() {
        
        var gridOption = {
            url: "${ctx}/admin/supplierGiftApp/goGiftAppDetailJqGrid",
            postData: {no: "${giftApp.no}"},
            height: 170,
            rowNum: 1000,
            pager: '1',
            colModel: [
                {name: 'itemcode', index: 'itemcode', width: 80, label: '礼品编号', sortable: true, align: "left"},
                {name: 'itemdescr', index: 'itemdescr', width: 180, label: '礼品名称', sortable: true, align: "left"},
                {name: 'qty', index: 'qty', width: 50, label: '数量', sortable: true, align: "right"},
                {name: 'uomdescr', index: 'uomdescr', width: 50, label: '单位', sortable: true, align: "right"},
                {name: 'remarks', index: 'remarks', width: 300, label: '备注', sortable: true, align: "left"},
            ],
            gridComplete: function() {
                $("#dataTable_GiftAppDetail").resetSelection()
            }
        }
        
        Global.JqGrid.initJqGrid("dataTable_GiftAppDetail", gridOption)
        
    })

</script>
