<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>薪酬结算企业管理--编辑</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<script type="text/javascript">
    $(function() {
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
				bankType:{  
					validators: {  
						notEmpty: {  
							message: "银行卡类型不能为空"  
						}
					}  
				},
				actName:{  
					validators: {  
						notEmpty: {  
							message: "户名不能为空"  
						}
					}  
				},
				cardId:{  
					validators: {  
						notEmpty: {  
							message: "卡号不能为空"  
						}
					}  
				},
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
    });
</script>
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
                <input type="hidden" id="pk" name="pk" value="${salarySettleCmp.pk}"/>
                <input type="hidden" id="expired" name="expired" value="${salarySettleCmp.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>企业名称</label>
                            <input style="width:448px;" type="text" id="descr" name="descr" value="${salarySettleCmp.descr}"/>
                        </li>
                       <li class="form-validate">
                        	<label>
                        	<span class="required">*</span>银行</label> 
							<house:xtdm id="bankType" dictCode="SALBANKTYPE" value="${salarySettleCmp.bankType}"> </house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span> 户名</label> 
							<input type="text" id="actName" name="actName" value="${salarySettleCmp.actName}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span> 卡号</label> 
							<input type="text" id="cardId" name="cardId" value="${salarySettleCmp.cardId}"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks">${salarySettleCmp.remarks}</textarea>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${salarySettleCmp.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {

    })

    function save() {
		$("#page_form").bootstrapValidator("validate");
		if (!$("#page_form").data("bootstrapValidator").isValid()) return;
        if (!$("#descr").val()) {
            art.dialog({content: "请输入薪酬结算企业名称"})
            return
        }

        var data = $("#page_form").jsonForm()

        $.ajax({
            url: "${ctx}/admin/salarySettleCmp/doUpdate",
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
