<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <title>礼品供货管理</title>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form action="" method="post" id="page_form" class="form-search">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
                <li>
                    <label>领用单状态</label>
                    <house:xtdmMulit id="status" dictCode="GIFTAPPSTATUS" selectedValue="OPEN"></house:xtdmMulit>
                </li>
                <li>
                    <label>供应商状态</label>
                    <house:xtdmMulit id="splStatus" dictCode="GIFTAPPSPLSTAT"></house:xtdmMulit>
                </li>
                <li>
                    <label>发货时间从</label>
                    <input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date"
                        onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>到</label>
                    <input type="text" id="sendDateTo" name="sendDateTo" class="i-date"
                        onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" id="address" name="address"/>
                </li>
                <li>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                </li>
            </ul>
        </form>
    </div>

    <div class="clear_float"></div>

    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="SUPPLIERGIFTAPP_RECEIVE">
                <button type="button" class="btn btn-system" onclick="receive()">接收</button>
            </house:authorize>
            <house:authorize authCode="SUPPLIERGIFTAPP_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">修改</button>
            </house:authorize>
            <house:authorize authCode="SUPPLIERGIFTAPP_RETURN">
                <button type="button" class="btn btn-system" onclick="sendBack()">退回</button>
            </house:authorize>
            <house:authorize authCode="SUPPLIERGIFTAPP_SEND">
                <button type="button" class="btn btn-system" onclick="delivery()">发货</button>
            </house:authorize>
            <house:authorize authCode="SUPPLIERGIFTAPP_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">查看</button>
            </house:authorize>
            <house:authorize authCode="SUPPLIERGIFTAPP_PRINT">
                <button type="button" class="btn btn-system " onclick="print()">打印</button>
            </house:authorize>
        </div>
    </div>
    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
    
        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/supplierGiftApp/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 80,
            colModel: [
                {name: 'no', index: 'no', width: 80, label: '领用单号', sortable: true, align: "left"},
                {name: 'address', index: 'address', width: 120, label: '楼盘', sortable: true, align: "left"},
                {name: 'custdescr', index: 'custdescr', width: 70, label: '客户名称', sortable: true, align: "left"},
                {name: 'custdocno', index: 'custdocno', width: 70, label: '档案号', sortable: true, align: "left"},
                {name: 'status', index: 'status', width: 80, label: '领料单状态', sortable: true, align: "left", hidden: true},
                {name: 'statusdescr', index: 'statusdescr', width: 80, label: '领料单状态', sortable: true, align: "left"},
                {name: 'senddate', index: 'senddate', width: 80, label: '发货日期', sortable: true, align: "left", formatter: formatDate},
                {name: 'splstatus', index: 'splstatus', width: 90, label: '供应商状态', sortable: true, align: "left", hidden: true},
                {name: 'splstatusdescr', index: 'splstatusdescr', width: 90, label: '供应商状态', sortable: true, align: "left"},
                {name: 'splrcvdate', index: 'splrcvdate', width: 110, label: '供应商接收日期', sortable: true, align: "left", formatter: formatDate},
                {name: 'splremark', index: 'splremark', width: 130, label: '供应商备注', sortable: false, align: "left"},
                {name: 'lastupdate', index: 'lastupdate', width: 135, label: '最后更新日期', sortable: true, align: 'left', formatter: formatTime},
                {name: 'lastupdatedby', index: 'lastupdatedby', width: 100, label: '最后更新人员', sortable: true, align: "left"},
                {name: 'expired', index: 'a.Expired', width: 50, label: '过期', sortable: true, align: "left"},
                {name: 'actionlog', index: 'a.ActionLog', width: 50, label: '操作', sortable: true, align: "left"}
            ],
            ondblClickRow: view
        })
        
    })
    
    function clearForm() {
        $("#page_form input[type='text']").val('')
        $("#page_form select").val('')
        
        $("#status").val('')
        $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false)
        $("#splStatus").val('')
        $.fn.zTree.getZTreeObj("tree_splStatus").checkAllNodes(false)
    }
    
    function receive() {
        var rowData = selectDataTableRow()
        
        if (rowData) {
            if (rowData.splstatus !== "0" && rowData.splstatus !== "1") {
                art.dialog({content: '未接收与接收退回状态的记录才能进行接收操作'})
                return
            }
            
            Global.Dialog.showDialog("giftAppReceive", {
                title: "礼品供货管理-接收",
                url: "${ctx}/admin/supplierGiftApp/goReceive?no=" + rowData.no,
                height: 550,
                width: 750,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }
    
    function update() {
        var rowData = selectDataTableRow()
        
        if (rowData) {
            if (rowData.splstatus !== "2") {
                art.dialog({content: '已接收状态的记录才能进行修改操作'})
                return
            }
            
            Global.Dialog.showDialog("giftAppUpdate", {
                title: "礼品供货管理-修改",
                url: "${ctx}/admin/supplierGiftApp/goUpdate?no=" + rowData.no,
                height: 550,
                width: 750,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }
    
    function sendBack() {
        var rowData = selectDataTableRow()
        
        if (rowData) {
            if (rowData.splstatus !== "2") {
                art.dialog({content: '已接收状态的记录才能进行退回操作'})
                return
            }
            
            Global.Dialog.showDialog("giftAppReturn", {
                title: "礼品供货管理-退回",
                url: "${ctx}/admin/supplierGiftApp/goReturn?no=" + rowData.no,
                height: 550,
                width: 750,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }
    
    function delivery() {
        var rowData = selectDataTableRow()
        
        if (rowData) {
            if (rowData.splstatus !== "2") {
                art.dialog({content: '已接收状态的记录才能进行发货操作'})
                return
            }
            
            Global.Dialog.showDialog("giftAppSend", {
                title: "礼品供货管理-发货",
                url: "${ctx}/admin/supplierGiftApp/goSend?no=" + rowData.no,
                height: 550,
                width: 750,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }
    
    function view() {
        var rowData = selectDataTableRow()
        
        if (rowData) {
            Global.Dialog.showDialog("giftAppView", {
                title: "礼品供货管理-查看",
                url: "${ctx}/admin/supplierGiftApp/goView?no=" + rowData.no,
                height: 550,
                width: 750,
                returnFun: goto_query
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }

    function print() {
        var rowData = selectDataTableRow()
        if (!rowData) {
            art.dialog({content: "请选择一条记录"})
            return
        }
        
        if (rowData) {
            if (rowData.splstatus !== "2"
                && rowData.splstatus !== "3") {
                art.dialog({content: '已接收与已发货状态的记录才能进行打印操作'})
                return
            }
        }
        
        var reportName = "supplierGiftAppDetail.jasper"
        Global.Print.showPrint(reportName, {
            No: rowData.no,
            LogoPath: "<%=basePath%>jasperlogo/",
            SUBREPORT_DIR: "<%=jasperPath%>"
        })
    }

</script>
</body>
</html>
