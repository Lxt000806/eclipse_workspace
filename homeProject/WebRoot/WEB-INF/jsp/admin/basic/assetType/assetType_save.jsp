<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="submit" class="btn btn-system " id="orderBtn" onclick="doSave()">
                    <span>保存</span>
                </button>
                <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="page_form" class="form-search">
                <house:token></house:token>
                <ul class="ul-form">
                    <div class="row">
                        <div class="col-sm-12">
                            <li class="form-validate">
                                <label><span class="required">*</span>名称</label>
                                <input type="text" id="descr" name="descr"/>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>助记码</label>
                                <input type="text" id="remCode" name="remCode"/>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>折旧方法</label>
                                <house:xtdm id="deprType" dictCode="ASSETDEPRTYPE"></house:xtdm>
                            </li>
                        </div>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {

        $("#page_form").bootstrapValidator({
            excluded: [':disabled'],
            fields: {
                remCode: {
                    validators: {
                        notEmpty: {
                            message: '请输入助记码'
                        }
                    }
                },
                descr: {
                    validators: {
                        notEmpty: {
                            message: '请输入名称'
                        }
                    }
                },
                deprType: {
                    validators: {
                        notEmpty: {
                            message: '请选择折旧方法'
                        }
                    }
                }
            },
        })

    })

    function doSave() {

        $("#page_form").bootstrapValidator("validate")
        if (!$("#page_form").data("bootstrapValidator").isValid()) return

        var data = $("#page_form").serialize()
        $.ajax({
            url: "${ctx}/admin/assetType/doSave",
            type: "post",
            data: data,
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
</html>
