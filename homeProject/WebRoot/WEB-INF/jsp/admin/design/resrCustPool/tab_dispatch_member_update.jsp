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
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>权重</label>
                            <input type="text" id="weight" name="weight" value="${resrCustPoolRuleCzy.weight}"/>
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
                weight: {
                    validators: {
                        notEmpty: {message: '权重不能为空'},
                        integer: {message: '权重须为整数'}
                    }
                }
            }
        });

    });

    function save() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        var data = $("#page_form").jsonForm();

        Global.Dialog.returnData = data;
        closeWin(true);
    }

</script>
</body>
</html>
