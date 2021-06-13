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
    <script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
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
                <input type="hidden" id="itemdescr" name="itemdescr" value="${itemTransformDetail.itemdescr}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>源材料</label>
                            <input type="text" id="itemcode" name="itemcode" value=""/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>转换系数</label>
                            <input type="text" id="transformper" name="transformper" value="${itemTransformDetail.transformper}"/>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    
    $(function() {
        $("#itemcode").openComponent_item({
            showLabel: "${itemTransformDetail.itemdescr}",
            showValue: "${itemTransformDetail.itemcode}",
            callBack: function(data) {
                $("#itemcode").val(data.code);
                $("#itemdescr").val(data.descr);
                $("#page_form").data('bootstrapValidator')
                               .revalidateField('openComponent_item_itemcode');
            }
        });
    
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                openComponent_item_itemcode: {
                    validators: {
                        notEmpty: {message: '源材料不能为空'}
                    }
                },
                transformper: {
                    validators: {
                        notEmpty: {message: '转换系数不能为空'},
                        numeric: {message: '转换系数须为数字'},
                        greaterThan: {message: '转换系数须大于等于0', value: 0.0, inclusive: true}
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
