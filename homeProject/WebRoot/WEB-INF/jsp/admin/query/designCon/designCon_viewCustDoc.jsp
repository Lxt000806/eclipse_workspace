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
    <title>查看项目资料</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
    <style type="text/css">
        .SelectBG {
            background-color: #198ede !important;
            color: rgb(255, 255, 255)
        }

        .SelectBG_yellow {
            background-color: yellow;
        }
    </style>
    <script type="text/javascript">
        $(function() {

            var gridOption = {
                height: $(document).height() - $("#content-list").offset().top - 82,
                multiselect: true,
                rowNum: 1000,
                colModel: [
                    {name: 'PK', index: 'PK', width: 90, label: 'PK', sortable: true, align: "left", hidden: true},
                    {name: 'Descr', index: 'Descr', width: 90, label: '资料名称', sortable: true, align: "left",},
                    {name: 'DocName', index: 'DocName', width: 90, label: '资料名称', sortable: true, align: "left", hidden: true},
                    {name: 'typedescr', index: 'typedescr', width: 120, label: '资料类型', sortable: true, align: "left",},
                    {name: 'custdoctypedescr', index: 'custdoctypedescr', width: 60, label: '类型', sortable: true, align: "left",},
                    {name: 'statusdescr', index: 'statusdescr', width: 60, label: '状态', sortable: true, align: "left",},
                    {name: 'ConfirmRemark', index: 'ConfirmRemark', width: 200, label: '变更审核说明', sortable: true, align: "left",},
                    {name: 'confirmczydescr', index: 'confirmczydescr', width: 130, label: '审核人员', sortable: true, align: "left",},
                    {name: 'ConfirmDate', index: 'ConfirmDate', width: 130, label: '审核时间', sortable: true, align: "left", formatter: formatTime},
                    {name: 'uploaddescr', index: 'uploaddescr', width: 80, label: '上传人员', sortable: true, align: "left",},
                    {name: 'UploadDate', index: 'UploadDate', width: 130, label: '上传时间', sortable: true, align: "left", formatter: formatTime},
                    {name: 'LastUpdate', index: 'LastUpdate', width: 95, label: '最后修改时间', sortable: true, align: "left", formatter: formatTime},
                    {name: 'LastUpdatedBy', index: 'LastUpdatedBy', width: 110, label: '最后修改人员', sortable: true, align: "left", hidden: true},
                    {name: 'Remark', index: 'Remark', width: 175, label: '备注', sortable: true, align: "left",},
                    {name: 'updatedescr', index: 'updatedescr', width: 95, label: '最后修改人员', sortable: true, align: "left",},
                ],
                onCellSelect: function(id, iCol, cellParam, e) {
                    var ids = $("#dataTable").jqGrid("getDataIDs");
                    var custCode = $.trim($("#code").val());
                    var url = $.trim($("#url").val());
                    var docName = Global.JqGrid.allToJson("dataTable", "DocName");
                    var arr = new Array();
                    arr = docName.fieldJson.split(",");
                    var arry = new Array();
                    arry = arr[id - 1].split(".");
                    if (arry[1] == "png" || arry[1] == 'jpg' || arry[1] == 'gif' || arry[1] == 'jpeg' || arry[1] == 'bmp') {//jpg/gif/jpeg/png/bmp.
                        document.getElementById("docPic").src = url + arr[id - 1];
                    } else {
                        document.getElementById("docPic").src = "";
                    }
                    for (var i = 0; i < ids.length; i++) {
                        if (i != id - 1) {
                            $('#' + ids[i]).find("td").removeClass("SelectBG");
                        }
                    }
                    $('#' + ids[id - 1]).find("td").addClass("SelectBG");
                },
            };
            $.extend(gridOption, {
                url: "${ctx}/admin/custDoc/goDocJqGrid",
                postData: {
                    custCode: '${customer.code}',
                    docType2: '${custDoc.docType2}'
                },
            });
            Global.JqGrid.initJqGrid("dataTable", gridOption);

            $("#download").on("click", function() {
                var docName = Global.JqGrid.allToJson("dataTable", "DocName");
                var arr = new Array();
                arr = docName.fieldJson.split(",");
                var custCode = $.trim($("#code").val());
                var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
                var docNameArr = new Array();
                var i = 0;
                if (ids.length == 0) {
                    Global.Dialog.infoDialog("请选择下载记录！");
                    return;
                }
                for (var i = 0; i < ids.length; i++) {
                    docNameArr[i] = arr[ids[i] - 1];
                }
                window.open("${ctx}/admin/custDoc/download?docNameArr=" + docNameArr
                    + "&docType2Descr=" + '设计资料' + "&custCode=" + custCode);
            });

        });

    </script>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form action="" method="post" id="dataForm" class="form-search">
            <input type="hidden" name="jsonString" value=""/>
            <input type="hidden" id="url" name="url" value="${url}"/>
            <ul class="ul-form">
                <li>
                    <label>客户编号</label>
                    <input type="text" id="code" name="code" value="${customer.code}" readonly="readonly"/>
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" id="address" name="address" readonly="true" value="${customer.address}"/>
                </li>
                <li>
                    <label class="control-textarea">审核说明</label>
                    <textarea id="remarks" name="remarks" rows="2" readonly="true">${designPicPrg.confirmRemark}</textarea>
                </li>
            </ul>
        </form>
    </div>
    <div class="btn-panel">
        <div class="btn-group-sm">
            <button type="button" class="btn btn-system " id="download"><span>下载</span></button>
        </div>
    </div>
</div>
<div style="width:53%; float:left; margin-left:0px; ">
    <div class="body-box-form">
        <div class="container-fluid" style="whith:800px">
            <div id="content-list" style="whith:800px">
                <table id="dataTable"></table>
            </div>
        </div>
    </div>
</div>
<div style="width:46.5%; float:right; margin-left:0px; ">
    <img id="docPic" src=" " onload="AutoResizeImage(500,500,'docPic');" width="521" height="510">
</div>
</body>
</html>
