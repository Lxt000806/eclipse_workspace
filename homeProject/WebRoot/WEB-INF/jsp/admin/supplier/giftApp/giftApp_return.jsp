<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>礼品供货管理-退回</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system " id="saveBtn" onclick="save()">保存</button>
                <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="dataForm" class="form-search">
                <house:token></house:token>
                <ul class="ul-form">
                    <li class="form-validate">
                        <label>领用单号</label>
                        <input type="text" id="no" name="no" value="${giftApp.no}" readonly="readonly"/>
                    </li>
                    <li class="form-validate">
                        <label>状态</label>
                        <house:xtdm id="status" dictCode="GIFTAPPSTATUS" value="${giftApp.status}" disabled="true"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>楼盘</label>
                        <input type="text" id="address" name="address" value="${giftApp.address}" readonly="readonly"/>
                    </li>
                    <li class="form-validate">
                        <label>供应商状态</label>
                        <house:xtdm id="splStatus" dictCode="GIFTAPPSPLSTAT" value="${giftApp.splStatus}" disabled="true"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label class="control-textarea">供应商备注</label>
                        <textarea id="splRemark" name="splRemark">${giftApp.splRemark}</textarea>
                    </li>
                </ul>
            </form>
        </div>
    </div>

    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tabGiftAppDetail" data-toggle="tab">礼品明细</a></li>
        </ul>
        <div class="tab-content">
            <div id="tabGiftAppDetail" class="tab-pane fade in active">
                <jsp:include page="tabGiftAppDetail.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>

<script>

    function save() {
        var formData = $("#dataForm").jsonForm()
        
        $.ajax({
            url: "${ctx}/admin/supplierGiftApp/doReturn",
            type: "post",
            data: formData,
            dataType: "json",
            cache: false,
            error: function (obj) {
                showAjaxHtml({"hidden": false, "msg": "保存出错"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function () {
                            closeWin()
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({
                        content: obj.msg,
                    })
                }
            }
        })
    }

</script>
</body>
</html>
