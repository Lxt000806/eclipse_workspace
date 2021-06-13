<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>工人定责管理-查看历史定责</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="pageContent">
        <div class="btn-panel">
            <div class="btn-group-sm">
                <house:authorize authCode="FIXDUTY_VIEW">
                    <button type="button" class="btn btn-system" onclick="goView()">
                        <span>查看</span>
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
<script type="text/javascript">

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/fixDuty/goJqGrid",
            height: $(document).height() - $("#content-list").offset().top - 80,
            postData: {
                custCode: "${fixDuty.custCode}",
                status: "1,2,3,4,5,6,7",
                type: "2"
            },
            colModel: [
                {name: "no", index: "no", width: 100, label: "定责申请编号", sortable: true, align: "left"},
                {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
                {name: "custtype", index: "custtype", width: 80, label: "客户类型编号", sortable: true, align: "left", hidden: true},
                {name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
                {name: "custwkpk", index: "custwkpk", width: 80, label: "安排pk", sortable: true, align: "left", hidden: true},
                {name: "worktype12", index: "worktype12", width: 80, label: "工种分类12", sortable: true, align: "left", hidden: true},
                {name: "workercode", index: "workercode", width: 80, label: "安排工人编号", sortable: true, align: "left", hidden: true},
                {name: "workername", index: "workername", width: 80, label: "安排工人", sortable: true, align: "left", hidden: true},
                {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
                {name: "cwstatusdescr", index: "cwstatusdescr", width: 80, label: "工地状态", sortable: true, align: "left"},
                {name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种", sortable: true, align: "left"},
                {name: "appmantypedescr", index: "appmantypedescr", width: 110, label: "申请人类型", sortable: true, align: "left"},
                {name: "statusdescr", index: "statusdescr", width: 90, label: "状态", sortable: true, align: "left"},
                {name: "appdate", index: "appdate", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
                {name: "appmandescr", index: "appmandescr", width: 80, label: "申请人", sortable: true, align: "left"},
                {name: "offerprj", index: "offerprj", width: 100, label: "人工金额", sortable: true, align: "right"},
                {name: "material", index: "material", width: 120, label: "材料金额", sortable: true, align: "right"},
                {name: "remarks", index: "remarks", width: 100, label: "描述", sortable: true, align: "left"},
                {name: "status", index: "status", width: 120, label: "状态", sortable: true, align: "left", hidden: true},
                {name: "prjconfirmdate", index: "prjconfirmdate", width: 120, label: "项目经理确认时间", sortable: true, align: "left", formatter: formatTime},
                {name: "prjmandescr", index: "prjmandescr", width: 100, label: "确认项目经理", sortable: true, align: "left"},
                {name: "department2", index: "department2", width: 80, label: "工程部", sortable: true, align: "left"},
                {name: "prjremark", index: "prjremark", width: 120, label: "项目经理确认说明", sortable: true, align: "left"},
                {name: "cfmdate", index: "cfmdate", width: 120, label: "金额确认日期", sortable: true, align: "left", formatter: formatTime},
                {name: "cfmmandescr", index: "cfmmandescr", width: 80, label: "金额确认人", sortable: true, align: "left"},
                {name: "cfmofferprj", index: "cfmofferprj", width: 90, label: "确认人工金额", sortable: true, align: "right"},
                {name: "cfmmaterial", index: "cfmmaterial", width: 90, label: "确认材料金额", sortable: true, align: "right"},
                {name: "cfmremark", index: "cfmremark", width: 110, label: "专员确认说明", sortable: true, align: "left"},
                {name: "dutymandescr", index: "dutymandescr", width: 80, label: "判责人", sortable: true, align: "left"},
                {name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
                {name: "fixamount", index: "fixamount", width: 80, label: "判责金额", sortable: true, align: "right"},
                {name: "managecfmdate", index: "managecfmdate", width: 120, label: "经理审批时间", sortable: true, align: "left", formatter: formatTime},
                {name: "managecfmmandescr", index: "managecfmmandescr", width: 80, label: "经理审批人", sortable: true, align: "left"},
                {name: "providemandescr", index: "providemandescr", width: 80, label: "发放人", sortable: true, align: "left"},
                {name: "providedate", index: "providedate", width: 120, label: "发放时间", sortable: true, align: "left", formatter: formatTime},
                {name: "payamount", index: "payamount", width: 80, label: "发放金额", sortable: true, align: "right"},
                {name: "cancelremark", index: "cancelremark", width: 110, label: "取消说明", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
                {name: "cfmreturnremark", index: "cfmreturnremark", width: 80, label: "cfmreturnremark", sortable: true, align: "left", hidden: true},
                {name: "projectman", index: "projectman", width: 80, label: "projectman", sortable: true, align: "left", hidden: true},
                {name: "projectmandescr", index: "projectmandescr", width: 80, label: "projectmandescr", sortable: true, align: "left", hidden: true},
                {name: "appmantype", index: "appmantype", width: 80, label: "appmantype", sortable: true, align: "left", hidden: true},
                {name: "appworkercode", index: "appworkercode", width: 80, label: "appworkercode", sortable: true, align: "left", hidden: true},
                {name: "isprjallduty", index: "isprjallduty", width: 80, label: "isprjallduty", sortable: true, align: "left", hidden: true},
                {name: "designman", index: "designman", width: 80, label: "designman", sortable: true, align: "left", hidden: true},
                {name: "designriskfund", index: "designriskfund", width: 80, label: "designriskfund", sortable: true, align: "left", hidden: true},
                {name: "prjmanriskfund", index: "prjmanriskfund", width: 80, label: "prjmanriskfund", sortable: true, align: "left", hidden: true},
                {name: "type", index: "type", width: 80, label: "类型", sortable: true, align: "left", hidden: true},
            ],
        })
    })

    function goView() {
        var ret =selectDataTableRow()
        
        if (!ret) {
            art.dialog({content: "请选择一条记录进行查看！"})
	        return
        }
        
        Global.Dialog.showDialog("viewDuty", {
            title: "工人定责管理--查看",
            postData: {map: JSON.stringify(ret), m_umState: "goViewHistory", content: "查看"},
            url: "${ctx}/admin/fixDuty/goView",
            height: 750,
            width: 1350
        })
    }

</script>
</body>
</html>
