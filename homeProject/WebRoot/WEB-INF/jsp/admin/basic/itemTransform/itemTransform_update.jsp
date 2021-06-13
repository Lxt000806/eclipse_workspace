<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
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
                <button type="button" id="saveBtn" class="btn btn-system" onclick='execute("${itemTransform.m_umState}")'>保存</button>
                <button type="button" id="closeBtn" class="btn btn-system" onclick='closeWin(false)'>关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <house:token></house:token>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label>转换编号</label>
                            <input type="text" id="no" name="no" value="${itemTransform.no}" placeholder="系统自动生成" readonly/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>加工材料</label>
                            <input type="text" id="itemCode" name="itemCode" value=""/>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label class="control-textarea">描述</label>
                            <textarea id="remarks" name="remarks" rows="3">${itemTransform.remarks}</textarea>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" id="expired" name="expired" value="${itemTransform.expired}"
                                   ${itemTransform.expired== 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#tab_detail" data-toggle="tab">转换明细</a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="tab_detail" class="tab-pane fade in active">
            <jsp:include page="tab_detail.jsp"></jsp:include>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function() {
        $("#itemCode").openComponent_item({
            showValue: "${itemTransform.itemCode}",
            callBack: function(data) {
                $("#page_form").data('bootstrapValidator')
                               .revalidateField('openComponent_item_itemCode');
            }
        });
        $("#openComponent_item_itemCode").blur();

        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                openComponent_item_itemCode: {
                    validators: {
                        notEmpty: {message: '加工材料不能为空'}
                    }
                },
            }
        });

        switch ("${itemTransform.m_umState}") {
            case "A":
                $("#expired").parent().hide();
                break;
            case "C":
                break;
            case "M":
                break;
            case "V":
                $("#saveBtn").hide();
                break;
            default:
        }

    });

    function execute(action) {
        switch (action) {
            case "A":
                doSave();
                break;
            case "C":
                break;
            case "M":
                doUpdate();
                break;
            case "V":
                break;
            default:
        }
    }

    function doSave() {
        var bootstrapValidator = $("#page_form").data('bootstrapValidator');

        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;
        
        var params = $("#page_form").jsonForm();
        var rows = getDetailGrid().getRowData();
        
        if (rows.length === 0) {
            art.dialog({content: "不能保存空的转换明细！"});
            return;
        }
        
        if (hasDuplicates(rows, function(row) { return row.itemcode; })) {
            art.dialog({content: "转换明细中不能存在相同的源材料！"});
            return;
        }
        
        params.details = rows;
        
        $.ajax({
            url: "${ctx}/admin/itemTransform/doSave",
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

    function doUpdate() {
        var bootstrapValidator = $("#page_form").data('bootstrapValidator');

        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;
        
        var params = $("#page_form").jsonForm();
        var rows = getDetailGrid().getRowData();
        
        if (rows.length === 0) {
            art.dialog({content: "不能保存空的转换明细！"});
            return;
        }
        
        if (hasDuplicates(rows, function(row) { return row.itemcode; })) {
            art.dialog({content: "转换明细中不能存在相同的源材料！"});
            return;
        }
        
        params.details = rows;
        
        $.ajax({
            url: "${ctx}/admin/itemTransform/doUpdate",
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
    
    // 用属性提取器或对象本身判断一个对象数组中是否存在相同对象
    function hasDuplicates(objects, propExtractor) {
        for (var i = 0; i < objects.length - 1; i++) {
            for (var j = i + 1; j < objects.length; j++) {
                if (typeof propExtractor === "function") {
                    if (propExtractor(objects[i]) === propExtractor(objects[j]))
                        return true;
                } else {
                    if (objects[i] === objects[j])
                        return true;
                }
            }
        }

        return false;
    }

</script>
</body>
</html>
