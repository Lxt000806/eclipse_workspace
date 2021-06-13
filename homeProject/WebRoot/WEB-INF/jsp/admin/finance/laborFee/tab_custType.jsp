<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

<script type="text/javascript">
    $(function() {
        Global.JqGrid.initJqGrid("dataTable_custType", {
            url: "${ctx}/admin/laborFee/goDetailCustTypeJqGrid",
            height: 270,
            postData: {no: $.trim("${laborFee.no}")},
            rowNum: 10000,
            sortable: true,
            colModel: [
                {name: "custtype", index: "custtype", width: 80, label: "客户类型编号", sortable: true, align: "left", hidden: true},
                {name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
                {name: "amount", index: "amount", width: 100, label: "金额", sortable: true, align: "right", sum: true},
            ]
        })

    })
</script>
<div class="body-box-list" style="margin-top: 0;">
    <div id="content-list-custType">
        <table id="dataTable_custType"></table>
    </div>
</div>
