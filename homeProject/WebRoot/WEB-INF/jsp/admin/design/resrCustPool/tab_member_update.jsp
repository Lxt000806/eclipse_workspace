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
                <input type="hidden" id="pk" name="pk" value="${resrCustPoolEmp.pk}"/>
                <input type="hidden" id="resrCustPoolNo" name="ResrCustPoolNo" value="${resrCustPoolEmp.resrCustPoolNo}"/>
                <input type="hidden" id="type" name="type" value="${resrCustPoolEmp.type}"/>
                <input type="hidden" id="oldGroupSign" name="oldGroupSign" value="${resrCustPoolEmp.groupSign}"/>
                <input type="hidden" id="dispSeq" name="dispSeq" value="${resrCustPoolEmp.dispSeq}"/>
                <input type="hidden" id="expired" name="expired" value="${resrCustPoolEmp.expired}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>操作员</label>
                            <input type="text" id="czybh" name="czybh"/>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>权重</label>
                            <input type="text" id="weight" name="weight" value="${resrCustPoolEmp.weight}"/>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label>组标签</label>
                            <input type="text" id="groupSign" name="groupSign" value="${resrCustPoolEmp.groupSign}"/>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    
    $(function() {
    
        switch ("${resrCustPoolEmp.m_umState}") {
            case "A":
		        $("#czybh").openComponent_czybm({
			        condition: {
		                empStatus: "ACT",
		                zfbz: false
		            },
		            showValue: "${resrCustPoolEmp.czybh}",
		            callBack: function(data) {
		                $("#page_form").data('bootstrapValidator')
		                        .revalidateField("openComponent_czybm_czybh");
		            }
		        });
                break;
            case "M":
		        $("#czybh").openComponent_czybm({
		            readonly: true,
		            disabled: true,
		            showValue: "${resrCustPoolEmp.czybh}",
		            callBack: function(data) {
		                $("#page_form").data('bootstrapValidator')
		                        .revalidateField("openComponent_czybm_czybh");
		            }
		        });
                break;
            case "V":
                break;
            default:
        }
        
        $("#openComponent_czybm_czybh").blur();

        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                openComponent_czybm_czybh: {
                    validators: {
                        notEmpty: {message: '操作员不能为空'}
                    }
                },
                weight: {
                    validators: {
                        notEmpty: {message: '权重不能为空'},
                        integer: {message: '权重须为整数'}
                    }
                }
            }
        });
    });
    
    function execute(action) {
        switch (action) {
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

        var params = $("#page_form").jsonForm();
        params.resrCustNumber = params.weight;
        
        $.ajax({
            url: "${ctx}/admin/resrCustPool/member/doAdd",
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
    
    function update() {
        var bv = $("#page_form").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) return;

        var params = $("#page_form").jsonForm();
        params.resrCustNumber = params.weight;
        
        $.ajax({
            url: "${ctx}/admin/resrCustPool/member/doUpdate",
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
