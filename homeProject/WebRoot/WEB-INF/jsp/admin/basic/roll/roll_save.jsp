<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加角色</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
    $("#dataForm").bootstrapValidator('validate');
    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas = $("#dataForm").serialize();
    $.ajax({
        url:'${ctx}/admin/roll/doSave',
        type: 'post',
        data: datas,
        dataType: 'json',
        cache: false,
        error: function(obj){
            showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
        },
        success: function(obj){
            if(obj.rs){
                art.dialog({
                    content: obj.msg,
                    time: 1000,
                    beforeunload: function () {
                        closeWin();
                    }
                });
            }else{
                $("#_form_token_uniq_id").val(obj.token.token);
                art.dialog({
                    content: obj.msg,
                    width: 200
                });
            }
        }
     });
}

function validateRefresh(){
    $('#dataForm').data('bootstrapValidator')
                  .validateField('openComponent_roll_childCode');
}

//校验函数
$(function() {

    $("#childCode").openComponent_roll({callBack:validateRefresh, showValue:"${roll.childCode}"});
    
    $("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                code:{
                    validators:{
                        notEmpty:{
                            message:"请填写角色编码"
                        },
                        numeric:{
                            message:"角色编码只能是数值"
                        }
                    }
                },
                descr:{
                    validators:{
                        notEmpty:{
                            message:"请填写角色名称"
                        }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'
    });
    
    $(function() {
        
        Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType",
                "department1", "department2", "department3");
        Global.LinkSelect.setSelect({
            firstSelect : "department1",
            firstValue : "${roll.department1}",
            secondSelect : "department2",
            secondValue : "${roll.department2}",
            thirdSelect : "department3",
            thirdValue : "${roll.department3}",
        });
    });
});
</script>
</head>
<body>
<form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
                            <span>保存</span>
                        </button>
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;"><span
                                    class="required">*</span>角色编码</label> <input type="text" id="code" name="code"
                                style="width:160px;" value="${roll.code}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>角色名称</label>
                                <input type="text" id="descr" name="descr" style="width:160px;" value="${roll.descr}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">下级角色</label>
                                <input type="text" id="childCode" name="childCode" style="width:160px;" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认一级部门</label>
                                <select id="department1" name="department1"></select>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认二级部门</label>
                                <select id="department2" name="department2"></select>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认三级部门</label>
                                <select id="department3" name="department3"></select>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">是否销售角色</label>
                                <select id="isSale" name="isSale">
                                    <option value="0" ${roll.isSale.equals('0') ? 'selected' : ''}>否</option>
                                    <option value="1" ${roll.isSale.equals('1') ? 'selected' : ''}>是</option>
                                </select>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>
