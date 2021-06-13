<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>线索池管理</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script type="text/javascript">

        function clearForm() {
            $("#page_form input[type='text']").val("");
            $("#page_form select").val("");
        }

        function goSave() {
            Global.Dialog.showDialog("resrCustPoolSave", {
                title: "线索池管理--新增",
                url: "${ctx}/admin/resrCustPool/goSave",
                height: 800,
                width: 1300,
                returnFun: goto_query
            });
        }
        
        function goCopy() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }
            
            Global.Dialog.showDialog("resrCustPoolCopy", {
                title: "线索池管理--复制",
                postData: {no: row.no},
                url: "${ctx}/admin/resrCustPool/goCopy",
                height: 800,
                width: 1300,
                returnFun: goto_query
            });
        }

        function goUpdate() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("resrCustPoolUpdate", {
                title: "线索池管理--编辑",
                postData: {no: row.no},
                url: "${ctx}/admin/resrCustPool/goUpdate",
                height: 800,
                width: 1300,
                returnFun: goto_query
            });
        }
        
        function goView() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("resrCustPoolView", {
                title: "线索池管理--查看",
                postData: {no: row.no},
                url: "${ctx}/admin/resrCustPool/goView",
                height: 800,
                width: 1300,
                returnFun: goto_query
            });
        }
        
        function goPoolEmp() {
            var row = selectDataTableRow();

            if (!row) {
                art.dialog({content: "请选择一条记录！"});
                return;
            }

            Global.Dialog.showDialog("resrCustPoolEmp", {
                title: "线索池管理--成员管理",
                postData: {no: row.no},
                url: "${ctx}/admin/resrCustPool/goPoolEmp",
                height: 650,
                width: 1000,
            });
        }

        $(function() {
            Global.JqGrid.initJqGrid("dataTable", {
                url: "${ctx}/admin/resrCustPool/goJqGrid",
                postData: $("#page_form").jsonForm(),
                height: $(document).height() - $("#content-list").offset().top - 80,
                colModel: [
                    {name: "no", index: "no", width: 80, label: "编号", sortable: true, align: "left"},
                    {name: "descr", index: "descr", width: 100, label: "名称", sortable: true, align: "left"},
                    {name: "type", index: "type", width: 80, label: "类型", sortable: true, align: "left", hidden: true},
                    {name: "typedescr", index: "typedescr", width: 80, label: "类型", sortable: true, align: "left"},
                    {name: "isvirtualphone", index: "isvirtualphone", width: 80, label: "是否虚拟拨号", sortable: true, align: "left", hidden: true},
                    {name: "isvirtualphonedescr", index: "isvirtualphonedescr", width: 100, label: "是否虚拟拨号", sortable: true, align: "left"},
                    {name: "ishidechannel", index: "ishidechannel", width: 80, label: "是否隐藏渠道", sortable: true, align: "left", hidden: true},
                    {name: "ishidechanneldescr", index: "ishidechanneldescr", width: 100, label: "是否隐藏渠道", sortable: true, align: "left"},
                    {name: "receiverule", index: "receiverule", width: 120, label: "领取规则", sortable: true, align: "left", hidden: true},
                    {name: "receiveruledescr", index: "receiveruledescr", width: 100, label: "领取规则", sortable: true, align: "left"},
                    {name: "dispatchrule", index: "dispatchrule", width: 80, label: "派单规则", sortable: true, align: "left", hidden: true},
                    {name: "dispatchruledescr", index: "dispatchruledescr", width: 100, label: "派单规则", sortable: true, align: "left"},
                    {name: "recoverrule", index: "recoverrule", width: 100, label: "回收规则", sortable: true, align: "left", hidden: true},
                    {name: "recoverruledescr", index: "recoverruledescr", width: 100, label: "回收规则", sortable: true, align: "left"},
                    {name: "maxvalidresrcustnumber", index: "itemdescr", width: 100, label: "有效客资上限", sortable: true, align: "right"},
                    {name: "maxnodispatchalarmnumber", index: "maxnodispatchalarmnumber", width: 110, label: "待分配客资提醒", sortable: true, align: "right"},
                    {name: "priority", index: "priority", width: 60, label: "优先级", sortable: true, align: "right"},
                    {name: "lastupdate", index: "lastupdate", width: 130, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
                ],
                ondblClickRow: goView
            });
        });

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <input type="hidden" id="expired" name="expired"/>
            <ul class="ul-form">
                <li>
                    <label>名称</label>
                    <input type="text" id="descr" name="descr"/>
                </li>
                <li>
                    <label>是否虚拟拨号</label>
                    <house:xtdm id="isVirtualPhone" dictCode="YESNO"></house:xtdm>
                </li>
                <li>
                    <label>是否隐藏渠道</label>
                    <house:xtdm id="isHideChannel" dictCode="YESNO"></house:xtdm>
                </li>
                <li>
                    <label>领取规则</label>
                    <house:xtdm id="receiveRule" dictCode="RECEIVERULE"></house:xtdm>
                </li>
                <li>
                    <label>派单规则</label>
                    <house:xtdm id="dispatchRule" dictCode="DISPATCHRULE"></house:xtdm>
                </li>
                <li>
                    <label>回收规则</label>
                    <house:xtdm id="recoverRule" dictCode="RECOVERRULE"></house:xtdm>
                </li>
                <li>
                    <label>类型</label>
                    <house:xtdm id="type" dictCode="POOLTYPE"></house:xtdm>
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
                <house:authorize authCode="RESRCUSTPOOL_SAVE">
                    <button type="button" class="btn btn-system" onclick="goSave()">
                        <span>新增</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="RESRCUSTPOOL_COPY">
                    <button style="display:none;" type="button" class="btn btn-system" onclick="goCopy()">
                        <span>复制</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="RESRCUSTPOOL_UPDATE">
                    <button type="button" class="btn btn-system" onclick="goUpdate()">
                        <span>编辑</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="RESRCUSTPOOL_VIEW">
                    <button type="button" class="btn btn-system" onclick="goView()">
                        <span>查看</span>
                    </button>
                </house:authorize>
                <house:authorize authCode="RESRCUSTPOOL_POOLEMP">
                    <button type="button" class="btn btn-system" onclick="goPoolEmp()">
                        <span>成员管理</span>
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
