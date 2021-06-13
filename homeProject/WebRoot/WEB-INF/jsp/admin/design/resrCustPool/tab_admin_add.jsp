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
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}"></script>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick='execute("${resrCustPoolEmp.m_umState}")'>保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="resrCustPoolNo" name="ResrCustPoolNo" value="${resrCustPoolEmp.resrCustPoolNo}"/>
                <input type="hidden" id="type" name="type" value="${resrCustPoolEmp.type}"/>
                <input type="hidden" id="dispSeq" name="dispSeq" value="${resrCustPoolEmp.dispSeq}"/>
                <input type="hidden" id="weight" name="weight" value="${resrCustPoolEmp.weight}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>操作员</label>
                            <input type="text" id="czybh" name="czybh"/>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    
    $(function() {
        $("#czybh").openComponent_czybm({
            condition: {
                empStatus: "ACT",
                zfbz: false
            },
            callBack: function(data) {
                $("#page_form").data('bootstrapValidator')
                        .revalidateField("openComponent_czybm_czybh");
            }
        });

        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                openComponent_czybm_czybh: {
                    validators: {
                        notEmpty: {message: '操作员不能为空'}
                    }
                },
            }
        });
    });
    
    function execute(action) {
        switch (action) {
            case "A":
                save();
                break;
            case "M":
                break;
            default:
        }
    }

    function save() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        var params = $("#page_form").jsonForm();
        
        $.ajax({
            url: "${ctx}/admin/resrCustPool/admin/doAdd",
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
