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
    <script type="text/javascript">
    
        $(function() {
	        $("#dataForm").bootstrapValidator({
	            message: 'This value is not valid',
	            fields: {
	                custType: {
	                    validators: {
	                        notEmpty: {message: '请选择客户类型'}
	                    }
	                },
	            }
	        });
        });

        function save() {
	        var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
	
	        bootstrapValidator.validate();
	        if (!bootstrapValidator.isValid()) return;
	        
	        var params = $("#dataForm").jsonForm();
	        params.giftPkString = "${giftPkString}";
        
            $.ajax({
                url: "${ctx}/admin/gift/doSetCustType",
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
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
                <house:token></house:token>
                <ul class="ul-form">
                    <li class="form-validate">
                        <label><span class="required">*</span>客户类型</label>
                        <house:dict id="custType" dictCode=""
                                    sql="select Code SQL_VALUE_KEY, Code + ' ' + Desc1 SQL_LABEL_KEY from tCusttype where Expired = 'F' order by cast(Code as int)">
                        </house:dict>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
</body>
</html>
