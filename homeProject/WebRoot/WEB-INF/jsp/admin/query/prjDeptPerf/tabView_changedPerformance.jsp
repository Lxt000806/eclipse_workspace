<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<table id="dataTable_changedPerformance"></table>
<script type="text/javascript">
    $(function() {
        Global.JqGrid.initJqGrid("dataTable_changedPerformance", {
            url: "${ctx}/admin/PrjDeptPerf/goChangedPerformanceJqGrid",
            postData: {
                empCode: $("#empCode").val(),
                dateFrom: $("#dateFrom").val(),
                dateTo: $("#dateTo").val(),
                custType: $("#custType").val(),
                department2: $("#department2").val()
            },
            autowidth: false,
            rowNum: 10000000,
            height: 400,
            colModel: [
                {name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left"},
                {name: "area", index: "area", width: 70, label: "面积", sortable: true, align: "right"},
                {name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
                {name: "typedescr", index: "typedescr", width: 80, label: "业绩类型", sortable: true, align: "left"},
                {name: "businessmandescr", index: "businessmandescr", width: 80, label: "业务员", sortable: true, align: "left"},
                {name: "againmandescr", index: "againmandescr", width: 80, label: "翻单员", sortable: true, align: "left"},
                {name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
                {name: "leadername", index: "leadername", width: 80, label: "归属领导", sortable: true, align: "left"},
                {name: "department2descr", index: "department2descr", width: 80, label: "部门", sortable: true, align: "left"},
                {name: "signdate", index: "signdate", width: 70, label: "业绩日期", sortable: true, align: "left", formatter: formatTime},
                {name: "quantity", index: "quantity", width: 70, label: "单量", sortable: true, align: "right", sum: true},
                {name: "perfamount", index: "perfamount", width: 110, label: "业绩金额", sortable: true, align: "right", sum: true, formatter: myRound},
                {name: "contractamount", index: "contractamount", width: 90, label: "合同额", sortable: true, align: "right", sum: true},
            ]
        })
    })
</script>
