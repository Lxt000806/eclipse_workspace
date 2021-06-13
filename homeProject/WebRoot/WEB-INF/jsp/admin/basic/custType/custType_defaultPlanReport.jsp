<%@page import="com.house.framework.web.login.UserContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick="save()">保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="custTypeCode" name="custTypeCode" value="${custType.code}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>客户预算报表</label>
                            <house:dict id="custPlanReportCode" dictCode=""
                                        sql="select Desc2 SQL_LABEL_KEY, Code SQL_VALUE_KEY from tCustPlanReport where IsEnable = '1' and Type = ${custType.type}"
                                        value="${defaultReport.code}"></house:dict>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    
    $(function() {
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                custPlanReportCode: {
                    validators: {
                        notEmpty: {message: '请选择客户预算报表'},
                    }
                }
            }
        });
    });

    function save() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        var params = $("#page_form").jsonForm();
        
        $.ajax({
            url: "${ctx}/admin/custType/doDefaultPlanReport",
            type: "POST",
            data: params,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(false);
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token);
                    art.dialog({content: obj.msg});
                }
            }
        });
    }

</script>
</body>
</html>
