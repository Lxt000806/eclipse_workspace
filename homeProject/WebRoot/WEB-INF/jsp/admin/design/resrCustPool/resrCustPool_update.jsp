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
    <style>
		span[class=prompt] {
		    color: red;
		    margin: 0 20px;
		    vertical-align: middle;
		    border: 1px solid;
		    border-radius: 4px;
		    padding: 2px;
		}
    </style>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" id="saveBtn" class="btn btn-system" onclick='execute("${resrCustPool.m_umState}")'>保存</button>
                <button type="button" id="closeBtn" class="btn btn-system" onclick='perform("${resrCustPool.m_umState}")'>关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="expired" name="expired" value="${resrCustPool.expired}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label>编号</label>
                            <input type="text" id="no" name="no" value="${resrCustPool.no}" placeholder="系统自动生成" readonly/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>名称</label>
                            <input type="text" id="descr" name="descr" value="${resrCustPool.descr}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>类型</label>
                            <house:xtdm id="type" dictCode="POOLTYPE" value="${resrCustPool.type}" disabled="true"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>虚拟拨号</label>
                            <house:xtdm id="isVirtualPhone" dictCode="YESNO" value="${resrCustPool.isVirtualPhone}"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>隐藏渠道</label>
                            <house:xtdm id="isHideChannel" dictCode="YESNO" value="${resrCustPool.isHideChannel}"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>领取规则</label>
                            <house:xtdm id="receiveRule" dictCode="RECEIVERULE" value="${resrCustPool.receiveRule}"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>派单规则</label>
                            <house:xtdm id="dispatchRule" dictCode="DISPATCHRULE" value="${resrCustPool.dispatchRule}"
                                        onchange="changeDispatchRule(this)"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>回收规则</label>
                            <house:xtdm id="recoverRule" dictCode="RECOVERRULE" value="${resrCustPool.recoverRule}"
                                        onchange="changeRecoverRule(this)"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>有效客资上限</label>
                            <input type="text" id="maxValidResrCustNumber" name="maxValidResrCustNumber"
                                   value="${resrCustPool.maxValidResrCustNumber}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>未分配客资提醒</label>
                            <input type="text" id="maxNoDispatchAlarmNumber" name="maxNoDispatchAlarmNumber"
                                   value="${resrCustPool.maxNoDispatchAlarmNumber}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>优先级</label>
                            <input type="text" id="priority" name="priority" value="${resrCustPool.priority}"/>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${resrCustPool.expired== 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
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
            <a href="#tab_admin" data-toggle="tab">管理员</a>
        </li>
        <li class="">
            <a href="#tab_member" data-toggle="tab">成员</a>
        </li>
        <li id="dispatchLi" class="">
            <a href="#tab_dispatch" data-toggle="tab">自定义派单</a>
        </li>
        <li id="recoverLi" class="">
            <a href="#tab_recover" data-toggle="tab">自定义回收</a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="tab_admin" class="tab-pane fade in active">
            <jsp:include page="tab_admin.jsp"></jsp:include>
        </div>
        <div id="tab_member" class="tab-pane fade">
            <jsp:include page="tab_member.jsp"></jsp:include>
        </div>
        <div id="tab_dispatch" class="tab-pane fade">
            <jsp:include page="tab_dispatch.jsp"></jsp:include>
        </div>
        <div id="tab_recover" class="tab-pane fade">
            <jsp:include page="tab_recover.jsp"></jsp:include>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function() {

        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                descr: {
                    validators: {
                        notEmpty: {message: '名称不能为空'}
                    }
                },
                type: {
                    validators: {
                        notEmpty: {message: '类型不能为空'}
                    }
                },
                isVirtualPhone: {
                    validators: {
                        notEmpty: {message: '虚拟拨号不能为空'}
                    }
                },
                isHideChannel: {
                    validators: {
                        notEmpty: {message: '隐藏渠道不能为空'}
                    }
                },
                receiveRule: {
                    validators: {
                        notEmpty: {message: '领取规则不能为空'}
                    }
                },
                dispatchRule: {
                    validators: {
                        notEmpty: {message: '派单规则不能为空'}
                    }
                },
                recoverRule: {
                    validators: {
                        notEmpty: {message: '回收规则不能为空'}
                    }
                },
                maxValidResrCustNumber: {
                    validators: {
                        notEmpty: {message: '有效客资上限不能为空'},
                        integer: {message: '有效客资上限须为整数'},
                        greaterThan: {value: 0, inclusive: true, message: '有效客资上限须大于等于0'}
                    }
                },
                maxNoDispatchAlarmNumber: {
                    validators: {
                        notEmpty: {message: '未分配客资提醒不能为空'},
                        integer: {message: '未分配客资提醒须为整数'},
                        greaterThan: {value: 0, inclusive: true, message: '未分配客资提醒须大于等于0'}
                    }
                },
                priority: {
                    validators: {
                        notEmpty: {message: '优先级不能为空'},
                        integer: {message: '优先级须为整数'}
                    }
                }
            }
        });

        switch ("${resrCustPool.m_umState}") {
            case "A":
                $("input[name=expiredCheckbox]").parent().hide();
                rebindCloseEvent();
                break;
            case "C":
                $("#no").val("");
                break;
            case "M":
                break;
            case "V":
                $("#saveBtn").hide();
                break;
            default:
        }

        changeDispatchRule($("#dispatchRule").get(0));
        changeRecoverRule($("#recoverRule").get(0));
    });
    
    function changeDispatchRule(obj) {
        var dispatchLi = $("#dispatchLi");
        
        switch (obj.value) {
            case "0":
                dispatchLi.hide();
                break;
            case "1":
                dispatchLi.hide();
                break;
            case "2":
                dispatchLi.show();
                break;
            default:
                dispatchLi.hide();
        }
    }
    
    function changeRecoverRule(obj) {
        var recoverLi = $("#recoverLi");
        
        switch (obj.value) {
            case "0":
                recoverLi.hide();
                break;
            case "1":
                recoverLi.show();
                break;
            default:
                recoverLi.hide();
        }
    }

    function execute(action) {
        switch (action) {
            case "A":
                doSave();
                break;
            case "C":
                doSave();
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
        
        if (params.dispatchRule === "2"
            && getDispatchGrid().getGridParam("records") === 0) {
            art.dialog({content: "自定义派单时，派单规则不能为空！"});
            return;
        }
        
        if (params.recoverRule === "1"
            && getRecoverGrid().getGridParam("records") === 0) {
            art.dialog({content: "自定义回收时，回收规则不能为空！"});
            return;
        }

        $.ajax({
            url: "${ctx}/admin/resrCustPool/doSave",
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
        
        if (params.dispatchRule === "2"
            && getDispatchGrid().getGridParam("records") === 0) {
            art.dialog({content: "自定义派单时，派单规则不能为空！"});
            return;
        }
        
        if (params.recoverRule === "1"
            && getRecoverGrid().getGridParam("records") === 0) {
            art.dialog({content: "自定义回收时，回收规则不能为空！"});
            return;
        }
        
        $.ajax({
            url: "${ctx}/admin/resrCustPool/doUpdate",
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
    
    function perform(operation) {
        switch (operation) {
            case "A":
                doClear();
                break;
            case "C":
                break;
            case "M":
                closeWin(false);
                break;
            case "V":
                closeWin(false);
                break;
            default:
        }
    }
    
    function rebindCloseEvent() {
        var closeBtn = parent.$("button[title=Close]");
        var closeBtnParent = closeBtn.parent();
        var closeBtnCopy = closeBtn.clone();
        
        closeBtnParent.remove("button");
        closeBtnParent.append(closeBtnCopy);
        closeBtnCopy.on("click", doClear);
    }
    
    function doClear() {
        $.ajax({
            url: "${ctx}/admin/resrCustPool/doClear",
            type: "POST",
            data: {no: "${resrCustPool.no}"},
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function() {
                closeWin(false);
            }
        });
    }

</script>
</body>
</html>
