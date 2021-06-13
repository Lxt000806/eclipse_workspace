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
                <button type="button" class="btn btn-system" onclick='execute("${resrCustPoolRule.m_umState}")'>保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <house:token></house:token>
                <input type="hidden" id="pk" name="pk" value="${resrCustPoolRule.pk}"/>
                <input type="hidden" id="resrCustPoolNo" name="resrCustPoolNo" value="${resrCustPoolRule.resrCustPoolNo}"/>
                <input type="hidden" id="ruleClass" name="ruleClass" value="${resrCustPoolRule.ruleClass}"/>
                <input type="hidden" id="dispSeq" name="dispSeq" value="${resrCustPoolRule.dispSeq}"/>
                <input type="hidden" id="expired" name="expired" value="${resrCustPoolRule.expired}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>名称</label>
                            <input type="text" id="descr" name="descr" value="${resrCustPoolRule.descr}"/>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label class="control-textarea"><span class="required">*</span>SQL</label>
                            <textarea id="sql" name="sql" rows="3">${resrCustPoolRule.sql}</textarea>
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
                descr: {
                    validators: {
                        notEmpty: {message: '名称不能为空'}
                    }
                },
                sql: {
                    validators: {
                        notEmpty: {message: 'SQL不能为空'},
                    }
                }
            }
        });
    });
    
    function execute(action) {
        switch(action) {
            case "A":
                save();
                break;
            case "M":
                update();
                break;
            default:
        }
    }

    function save() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        $.ajax({
            url: "${ctx}/admin/resrCustPool/recover/doAdd",
            type: "POST",
            data: $("#page_form").jsonForm(),
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
                            closeWin(true);
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token);
                    art.dialog({content: obj.msg});
                }
            }
        });
    }
    
    function update() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        $.ajax({
            url: "${ctx}/admin/resrCustPool/recover/doUpdate",
            type: "POST",
            data: $("#page_form").jsonForm(),
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
                            closeWin(true);
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
