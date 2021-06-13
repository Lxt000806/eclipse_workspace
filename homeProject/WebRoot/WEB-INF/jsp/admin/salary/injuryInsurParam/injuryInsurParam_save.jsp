<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>工商缴费参数管理管理--新增</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
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
                <c:if test="${injuryInsurParam.m_umState!='V' }">
	                <button type="button" class="btn btn-system" onclick="save()">保存</button>
	            </c:if>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
               	<input type="hidden" id="expired" name="expired" value="${injuryInsurParam.expired}"/>
                <ul class="ul-form">
                	<div class="validate-group row">
	                    <li class="form-validate ">
							<label ><span class="required">*</span>签约公司</label>
							<house:dict id="conSignCmp" dictCode=""
								sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
								sqlLableKey="descr" sqlValueKey="code" value="${injuryInsurParam.conSignCmp}" />
						</li>
					</div>
                    <div class="validate-group row">
						<li class="form-validate">
                            <label ><span class="required">*</span>工伤保险最低基数</label>
                            <input type="text" id="injuryInsurBaseMin" name="injuryInsurBaseMin" value="${injuryInsurParam.injuryInsurBaseMin }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
                        </li>
                    </div>
                    <div class="validate-group row">
                    	<li class="form-validate">
                            <label ><span class="required">*</span>工伤保险费率</label>
                            <input type="text" id="injuryInsurRate" name="injuryInsurRate" value="${injuryInsurParam.injuryInsurRate }" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
                        </li>
                    </div>
                    <c:if test="${injuryInsurParam.m_umState!='A'}">
	                    <div class="validate-group row">
	                        <li>
	                            <label>过期</label>
	                            <input type="checkbox" name="expiredCheckbox"
	                                   ${injuryInsurParam.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
	                        </li>
	                    </div>
                    </c:if>
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
                conSignCmp: {
                    validators: {
                        notEmpty: {
                        	message: '公司不能为空'
                        },
                    }
                },
                injuryInsurBaseMin: {
                    validators: {
                        notEmpty: {
                       		message: '工伤保险最低基数不能为空'
                        },
                    }
                },
                injuryInsurRate: {
                    validators: {
                        notEmpty: {
                    	    message: '工伤保险费率不能为空'
                        },
	                    greaterThan: {
							value: 0
						},
						lessThan: {
							value: 1
						}
                    },
                },
            }
        });
        parent.$("#iframe_${dialogId}").attr("height","98%"); //消灭掉无用的滑动条
        if("${injuryInsurParam.m_umState}" == "M"){
        	$("#conSignCmp").attr("disabled",true);
        }
        if("${injuryInsurParam.m_umState}" == "V"){
        	disabledForm("dataForm");
        }
    });

    function save() {
        var bootstrapValidator = $("#dataForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;

        var data = $("#dataForm").jsonForm();
        
        var requestMap="doSave";
        if("${injuryInsurParam.m_umState}"=="M"){
        	requestMap="doUpdate";
        }
        $.ajax({
            url: "${ctx}/admin/injuryInsurParam/"+requestMap,
            type: "POST",
            data: data,
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
                            closeWin(true)
                        }
                    });
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
