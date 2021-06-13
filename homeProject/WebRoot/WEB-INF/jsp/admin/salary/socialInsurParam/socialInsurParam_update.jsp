<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>社保公积金参数管理--编辑</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style>
        .form-search .ul-form li label {
            width: 120px;
        }
    </style>
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
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
                <input type="hidden" id="pk" name="pk" value="${socialInsurParam.pk}"/>
                <input type="hidden" id="expired" name="expired" value="${socialInsurParam.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>参数名称</label>
                            <input style="width:470px;" type="text" id="descr" name="descr" value="${socialInsurParam.descr}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>养老保险最低基数</label>
                            <input type="text" id="edmInsurBaseMin" name="edmInsurBaseMin" value="${socialInsurParam.edmInsurBaseMin}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>养老保险最高基数</label>
                            <input type="text" id="edmInsurBaseMax" name="edmInsurBaseMax" value="${socialInsurParam.edmInsurBaseMax}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>养老保险个人费率</label>
                            <input type="text" id="edmInsurIndRate" name="edmInsurIndRate" value="${socialInsurParam.edmInsurIndRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>养老保险单位费率</label>
                            <input type="text" id="edmInsurCmpRate" name="edmInsurCmpRate" value="${socialInsurParam.edmInsurCmpRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>失业保险个人费率</label>
                            <input type="text" id="uneInsurIndRate" name="uneInsurIndRate" value="${socialInsurParam.uneInsurIndRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>失业保险单位费率</label>
                            <input type="text" id="uneInsurCmpRate" name="uneInsurCmpRate" value="${socialInsurParam.uneInsurCmpRate}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>医保最低基数</label>
                            <input type="text" id="medInsurBaseMin" name="medInsurBaseMin" value="${socialInsurParam.medInsurBaseMin}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>医保最高基数</label>
                            <input type="text" id="medInsurBaseMax" name="medInsurBaseMax" value="${socialInsurParam.medInsurBaseMax}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>医保个人费率</label>
                            <input type="text" id="medInsurIndRate" name="medInsurIndRate" value="${socialInsurParam.medInsurIndRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>医保单位费率</label>
                            <input type="text" id="medInsurCmpRate" name="medInsurCmpRate" value="${socialInsurParam.medInsurCmpRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>生育保险单位费率</label>
                            <input type="text" id="birthInsurCmpRate" name="birthInsurCmpRate" value="${socialInsurParam.birthInsurCmpRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>生育保险最低基数</label>
                            <input type="text" id="birthInsurBaseMin" name="birthInsurBaseMin" value="${socialInsurParam.birthInsurBaseMin}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>公积金最低基数</label>
                            <input type="text" id="houFundBaseMin" name="houFundBaseMin" value="${socialInsurParam.houFundBaseMin}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>公积金最高基数</label>
                            <input type="text" id="houFundBaseMax" name="houFundBaseMax" value="${socialInsurParam.houFundBaseMax}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>公积金个人费率</label>
                            <input type="text" id="houFundIndRate" name="houFundIndRate" value="${socialInsurParam.houFundIndRate}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>公积金单位费率</label>
                            <input type="text" id="houFundCmpRate" name="houFundCmpRate" value="${socialInsurParam.houFundCmpRate}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>工伤保险基数</label>
                            <input type="text" id="injuryInsurBaseMin" name="injuryInsurBaseMin" value="${socialInsurParam.injuryInsurBaseMin}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>工伤保险费率</label>
                            <input type="text" id="injuryInsurRate" name="injuryInsurRate" value="${socialInsurParam.injuryInsurRate}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks">${socialInsurParam.remarks}</textarea>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${socialInsurParam.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {

        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
            	birthInsurBaseMin: {
                    validators: {
                        notEmpty: {message: '生育保险最低基数不能为空'},
                        numeric: {message: '生育保险最低基数须为数字'}
                    }
                },
                descr: {
                    validators: {
                        notEmpty: {message: '参数名称不能为空'}
                    }
                },
                edmInsurBaseMin: {
                    validators: {
                        notEmpty: {message: '养老保险最低基数不能为空'},
                        numeric: {message: '养老保险最低基数须为数字'}
                    }
                },
                edmInsurBaseMax: {
                    validators: {
                        notEmpty: {message: '养老保险最高基数不能为空'},
                        numeric: {message: '养老保险最高基数须为数字'}
                    }
                },
                edmInsurIndRate: {
                    validators: {
                        notEmpty: {message: '养老保险个人费率不能为空'},
                        between: {min: 0, max: 1, message: '养老保险个人费率须在0到1之间'}
                    }
                },
                edmInsurCmpRate: {
                    validators: {
                        notEmpty: {message: '养老保险单位费率不能为空'},
                        between: {min: 0, max: 1, message: '养老保险单位费率须在0到1之间'}
                    }
                },
                uneInsurIndRate: {
                    validators: {
                        notEmpty: {message: '失业保险个人费率不能为空'},
                        between: {min: 0, max: 1, message: '失业保险个人费率须在0到1之间'}
                    }
                },
                uneInsurCmpRate: {
                    validators: {
                        notEmpty: {message: '失业保险单位费率不能为空'},
                        between: {min: 0, max: 1, message: '失业保险单位费率须在0到1之间'}
                    }
                },
                medInsurBaseMin: {
                    validators: {
                        notEmpty: {message: '医保最低基数不能为空'},
                        numeric: {message: '医保最低基数须为数字'}
                    }
                },
                medInsurBaseMax: {
                    validators: {
                        notEmpty: {message: '医保最高基数不能为空'},
                        numeric: {message: '医保最高基数须为数字'}
                    }
                },
                medInsurIndRate: {
                    validators: {
                        notEmpty: {message: '医保个人费率不能为空'},
                        between: {min: 0, max: 1, message: '医保个人费率须在0到1之间'}
                    }
                },
                medInsurCmpRate: {
                    validators: {
                        notEmpty: {message: '医保单位费率不能为空'},
                        between: {min: 0, max: 1, message: '医保单位费率须在0到1之间'}
                    }
                },
                birthInsurCmpRate: {
                    validators: {
                        notEmpty: {message: '生育保险费率不能为空'},
                        between: {min: 0, max: 1, message: '生育保险费率须在0到1之间'}
                    }
                },
                houFundBaseMin: {
                    validators: {
                        notEmpty: {message: '公积金最低基数不能为空'},
                        numeric: {message: '公积金最低基数须为数字'}
                    }
                },
                houFundBaseMax: {
                    validators: {
                        notEmpty: {message: '公积金最高基数不能为空'},
                        numeric: {message: '公积金最高基数须为数字'}
                    }
                },
                houFundIndRate: {
                    validators: {
                        notEmpty: {message: '公积金个人费率不能为空'},
                        between: {min: 0, max: 1, message: '公积金个人费率须在0到1之间'}
                    }
                },
                houFundCmpRate: {
                    validators: {
                        notEmpty: {message: '公积金单位费率不能为空'},
                        between: {min: 0, max: 1, message: '公积金单位费率须在0到1之间'}
                    }
                },
                injuryInsurBaseMin: {
                    validators: {
                        notEmpty: {message: '工伤保险基数不能为空'},
                        numeric: {message: '工伤保险基数须为数字'}
                    }
                },
                injuryInsurRate: {
                    validators: {
                        notEmpty: {message: '工伤保险费率不能为空'},
                        between: {min: 0, max: 1, message: '工伤保险费率须在0到1之间'}
                    }
                },
            }
        })
    })

    function save() {
        var bootstrapValidator = $("#dataForm").data('bootstrapValidator')

        bootstrapValidator.validate()
        if (!bootstrapValidator.isValid()) return

        var data = $("#dataForm").jsonForm()

        if (parseFloat(data.edmInsurBaseMin) > parseFloat(data.edmInsurBaseMax)) {
            art.dialog({content: "养老保险最低基数不能高于最高基数"})
            return
        }

        if (parseFloat(data.medInsurBaseMin) > parseFloat(data.medInsurBaseMax)) {
            art.dialog({content: "医保最低基数不能高于最高基数"})
            return
        }

        if (parseFloat(data.houFundBaseMin) > parseFloat(data.houFundBaseMax)) {
            art.dialog({content: "公积金最低基数不能高于最高基数"})
            return
        }

        $.ajax({
            url: "${ctx}/admin/socialInsurParam/doUpdate",
            type: "POST",
            data: data,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                })
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(true)
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })

    }

</script>
</body>
</html>
