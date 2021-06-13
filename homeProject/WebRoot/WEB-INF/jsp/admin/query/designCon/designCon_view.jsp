<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>设计进度跟踪查看</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
    <style type="text/css">
        .processBar {
            width: 120px
        }
        
        .processBar .bar {
            width: auto;
            margin-left: 10px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<a id="downloadElem" download style="display: none"></a>
<div class="body-box-list">
    <div class="panel-body" style="padding:0 0">
        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
            <ul class="ul-form">
                <div class="validate-group row">
                    <div style="margin: 10px 0;">
                        <!--  标题进度条 start-->
                        <div class="content" style="margin: 0 auto; width: 860px;">
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">设计师对接</span>
                                </div>
                                <div id="line0" class="bar">
                                    <div id="point_crtDate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${customer.crtDate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">量房</span>
                                </div>
                                <div id="line1" class="bar">
                                    <div id="point_measuredate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${info.measuredate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">设计出图</span>
                                </div>
                                <div id="line2" class="bar">
                                    <div id="point_designdrawingdate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${info.designdrawingdate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">到店</span>
                                </div>
                                <div id="line3" class="bar">
                                    <div id="point_visitDate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${customer.visitDate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">下定</span>
                                </div>
                                <div id="line4" class="bar">
                                    <div id="point_setDate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${customer.setDate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">报价</span>
                                </div>
                                <div id="line5" class="bar">
                                    <div id="point_priceDate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${customer.priceDate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">效果图出图</span>
                                </div>
                                <div id="line6" class="bar">
                                    <div id="point_renderingdate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${info.renderingdate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                            <div class="processBar" style="width:20px;">
                                <div class="text" style="margin:0px 0px 10px -10px;">
                                    <span class='poetry' style="font-weight:bold">签单</span>
                                </div>
                                <div id="line7" class="bar" style="width: 0;">
                                    <div id="point_signDate" class="c-step"></div>
                                </div>
                                <div class="text" style="margin: 10px -25px;">
                                    <span class='poetry'>
                                        <fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd'/>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <!--  标题进度条 end-->
                    </div>
                </div>
                <hr style="height: 2px; margin: 5px 0px 5px 0px;">
                <div class="validate-group row">
                    <li class="form-validate">
                        <label>客户姓名</label>
                        <input type="text" id="descr" name="descr" value="${customer.descr}"/>
                    </li>
                    <li>
                        <label>性别</label>
                        <house:xtdm id="gender" dictCode="GENDER" value="${customer.gender}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>客户来源</label>
                        <select id="source" name="source"></select>
                    </li>
                    <li class="form-validate">
                        <label>渠道</label>
                        <select id="netChanel" name="netChanel"></select>
                    </li>
                    <li class="form-validate">
                        <label>户型</label>
                        <house:xtdm id="layout" dictCode="LAYOUT" value="${customer.layout}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>面积</label>
                        <input type="text" name="area" value="${customer.area}"/>
                        <span style="position: absolute;left:290px;width: 30px;top:5px;">m&sup2;</span>
                    </li>
                    <li class="form-validate">
                        <label>项目名称</label>
                        <input type="text" id="builderCode" name="builderCode" value="${customer.builderCode} "/>
                    </li>
                    <li>
                        <label>楼盘</label>
                        <input type="text" id="address" name="address" value="${customer.address}"/>
                    </li>
                    <li>
                        <label>设计风格</label>
                        <house:xtdm id="designStyle" dictCode="DESIGNSTYLE" value="${customer.designStyle}"></house:xtdm>
                    </li>
                    <li>
                        <label>施工方式</label>
                        <house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType}"></house:xtdm>
                    </li>
                    <li>
                        <label>到店次数</label>
                        <input type="text" name="comeTimes" value="${customer.comeTimes}"/>
                    </li>
                    <li>
                        <label>客户状态</label>
                        <house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status}"></house:xtdm>
                    </li>
                    <li>
                        <label>业务员</label>
                        <input type="text" id="businessMan" name="businessMan" value="${info.businessmanname}"/>
                    </li>
                    <li>
                        <label>设计师</label>
                        <input type="text" id="designMan" name="designMan" value="${info.designmanname}"/>
                    </li>
                    <li>
                        <label>翻单员</label>
                        <input type="text" id="againMan" name="againMan" value="${info.againmanname}"/>
                    </li>
                    <li class="form-validate">
                        <label class="control-textarea">备注</label>
                        <textarea name="remark" rows="3">${customer.remarks}</textarea>
                    </li>
                </div>
            </ul>
        </form>
    </div>
    <hr style="height: 2px;margin:0 0">
    <div class="panel-body">
        <div id="content-list">
            <table id="dataTable"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
    
        Global.LinkSelect.initSelect("${ctx}/admin/ResrCust/sourceByAuthority", "source", "netChanel", null, false, true, false, true);
        Global.LinkSelect.setSelect({
            firstSelect: 'source',
            firstValue: '${customer.source}',
            secondSelect: 'netChanel',
            secondValue: '${customer.netChanel}'
        });
                
        $("#builderCode").openComponent_builder({showValue: '${customer.builderCode}'});
        $("#openComponent_builder_builderCode").blur();

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/designCon/goConJqGrid",
            postData: {
                code: "${customer.code}",
                czybh: "${params.conMan}",
                role: "${params.contactRole}"
            },
            height: 280,
            rowNum: 10000000,
            onSortColEndFlag: true,
            colModel: [
                {name: 'conmandescr', index: 'conmandescr', width: 90, label: '跟踪人', sortable: true, align: "left",},
                {name: 'conmanroledescr', index: 'conmanroledescr', width: 90, label: '跟踪人角色', sortable: true, align: "left",},
                {name: 'condate', index: 'condate', width: 140, label: '跟踪日期', sortable: true, align: "left", formatter: formatTime},
                {name: 'conwaydescr', index: 'conwaydescr', width: 80, label: '跟踪方式', sortable: true, align: "left",},
                {name: 'typedescr', index: 'typedescr', width: 110, label: '跟踪类型', sortable: true, align: "left",},
                {name: 'conduration', index: 'conduration', width: 100, label: '通话时长（秒）', sortable: true, align: "right",},
                {name: 'remarks', index: 'remarks', width: 365, label: '跟踪说明', sortable: true, align: "left",},
                {name: 'nextcondate', index: 'nextcondate', width: 95, label: '下次联系时间', sortable: true, align: "left", formatter: formatDate},
                {name: 'callrecordpath', index: 'callrecordpath', width: 80, label: '功能', sortable: true, align: "left", formatter: formatBtns}
            ],
            gridComplete: function() {
                dataTableCheckBox("dataTable", "typedescr");
            },
        });

        initSelectStep();

    });

    function formatBtns(value, selectInfo) {
        if (value && value != "") {
            return "<a onclick=\"downloadFileView('" + selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">下载</a>&nbsp;&nbsp;<a onclick=\"listenCallRecoardView('"
                + selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">播放</a>";
        }
        return "";
    }

    function downloadFileView(dataTable, rowId, path) {
        $("#downloadElem")[0].href = path;
        $("#downloadElem")[0].click();
    }

    function listenCallRecoardView(dataTable, rowId, path) {
        Global.Dialog.showDialog("CallRecord", {
            title: "录音",
            url: "${ctx}/admin/callRecord/goView",
            postData: {
                path: path
            },
            height: 150,
            width: 600,
        });
    }

    function initSelectStep() {
        if ("${customer.crtDate}") {
            $("#point_crtDate").addClass("c-select");
            $("#point_crtDate").parent().addClass("b-select");
        }
        if ("${info.measuredate}") {
            $("#point_measuredate").addClass("c-select");
            $("#point_measuredate").parent().addClass("b-select");
        }
        if ("${info.designdrawingdate}") {
            $("#point_designdrawingdate").addClass("c-select");
            $("#point_designdrawingdate").parent().addClass("b-select");
        }
        if ("${customer.visitDate}") {
            $("#point_visitDate").addClass("c-select");
            $("#point_visitDate").parent().addClass("b-select");
        }
        if ("${customer.setDate}") {
            $("#point_setDate").addClass("c-select");
            $("#point_setDate").parent().addClass("b-select");
        }
        if ("${customer.priceDate}") {
            $("#point_priceDate").addClass("c-select");
            $("#point_priceDate").parent().addClass("b-select");
        }
        if ("${info.renderingdate}") {
            $("#point_renderingdate").addClass("c-select");
            $("#point_renderingdate").parent().addClass("b-select");
        }
        if ("${customer.signDate}") {
            $("#point_signDate").addClass("c-select");
        }
    }
</script>
</body>
</html>
