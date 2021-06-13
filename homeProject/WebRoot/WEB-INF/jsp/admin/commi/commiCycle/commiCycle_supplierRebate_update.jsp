<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
    <title>商家返利信息--编辑</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}"></script>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}"></script>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick='execute("${commiCustStakeholderSuppl.m_umState}")'>保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <house:token></house:token>
                <input type="hidden" name="pk" value="${commiCustStakeholderSuppl.pk}"/>
                <input type="hidden" id="expired" name="expired" value="${commiCustStakeholderSuppl.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
		                <li class="form-validate">
		                    <label><span class="required">*</span>销售日期</label>
		                    <input type="text" id="date" name="date" class="i-date"
		                           onFocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})"
		                           value="<fmt:formatDate value='${commiCustStakeholderSuppl.date}' pattern='yyyy-MM-dd'/>"/>
		                </li>
		                <li class="form-validate">
		                    <label><span class="required">*</span>材料类型1</label>
		                    <select id="itemType1" name="itemType1" onchange="changeItemType1(this)"></select>
		                </li>
		                <li class="form-validate">
		                    <label><span class="required">*</span>供应商</label>
		                    <input type="text" id="supplCode" name="supplCode"/>
		                </li>
		                <li class="form-validate">
		                    <label><span class="required">*</span>客户编号</label>
		                    <input type="text" id="custCode" name="custCode"/>
		                </li>
		                <li>
		                    <label>材料名称</label>
		                    <input type="text" id="itemDescr" name="itemDescr" value="${commiCustStakeholderSuppl.itemDescr}"/>
		                </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>返利总金额</label>
                            <input type="text" id="amount" name="amount" value="${commiCustStakeholderSuppl.amount}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>干系人编号</label>
                            <input type="text" id="empCode" name="empCode"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>业务员提成</label>
                            <input type="text" id="commiAmount" name="commiAmount" value="${commiCustStakeholderSuppl.commiAmount}"/>
                        </li>
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea id="remarks" name="remarks" rows="3">${commiCustStakeholderSuppl.remarks}</textarea>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                ${commiCustStakeholderSuppl.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1");
        Global.LinkSelect.setSelect({firstSelect: 'itemType1', firstValue: '${commiCustStakeholderSuppl.itemType1}'});
        
        $("#custCode").openComponent_customer({
            showValue: "${commiCustStakeholderSuppl.custCode}",
            callBack: function(data) {
                $("#page_form").data('bootstrapValidator')
                        .revalidateField("openComponent_customer_custCode");
            }
        });
        $("#openComponent_customer_custCode").blur();

        if ("${commiCustStakeholderSuppl.itemType1}") {
	        $("#supplCode").openComponent_supplier({
	            showValue: "${commiCustStakeholderSuppl.supplCode}",
	            condition: {
	                itemType1: "${commiCustStakeholderSuppl.itemType1}",
	                readonly: 1
	            },
	            callBack: function(data) {
	                $("#page_form").data('bootstrapValidator')
	                        .revalidateField("openComponent_supplier_supplCode");
	            }
	        });
	        $("#openComponent_supplier_supplCode").blur();
        } else {
            $("#supplCode").openComponent_supplier({
                disabled: true
            });
        }
        
        $("#empCode").openComponent_employee({
            showValue: "${commiCustStakeholderSuppl.empCode}",
            callBack: function(data) {
                $("#page_form").data('bootstrapValidator')
                        .revalidateField("openComponent_employee_empCode");
            }
        });
        $("#openComponent_employee_empCode").blur();
    
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                date: {
                    validators: {
                        notEmpty: {message: '销售日期不能为空'}
                    }
                },
                itemType1: {
                    validators: {
                        notEmpty: {message: '材料类型1不能为空'}
                    }
                },
                openComponent_supplier_supplCode: {
                    validators: {
                        notEmpty: {message: '供应商不能为空'}
                    }
                },
                openComponent_customer_custCode: {
                    validators: {
                        notEmpty: {message: '客户编号不能为空'}
                    }
                },
                amount: {
                    validators: {
                        notEmpty: {message: '返利总金额不能为空'},
                        greaterThan: {value: 0, inclusive: true, message: '返利总金额须大于等于0'}
                    }
                },
                openComponent_employee_empCode: {
                    validators: {
                        notEmpty: {message: '干系人编号不能为空'}
                    }
                },
                commiAmount: {
                    validators: {
                        notEmpty: {message: '业务员提成不能为空'},
                        greaterThan: {value: 0, inclusive: true, message: '业务员提成须大于等于0'}
                    }
                }
            }
        })
        
        switch("${commiCustStakeholderSuppl.m_umState}") {
            case "A":
                $("input[name=expiredCheckbox]").parent().hide();
                break;
            case "M":
                break;
            default:
        }
    })
    
    function changeItemType1(obj) {
        var supplierComponent = $("#openComponent_supplier_supplCode");
        var supplier = $("#supplCode");
        supplierComponent.val("");
        supplier.val("");
        
        if (obj.value) {
	        $("#supplCode").openComponent_supplier({
	            condition: {
	                itemType1: obj.value,
	                readonly: 1
	            },
	            callBack: function(data) {
	                $("#page_form").data('bootstrapValidator')
	                        .revalidateField("openComponent_supplier_supplCode");
	            }
	        });
        } else {
            $("#supplCode").openComponent_supplier({
	            disabled: true
	        });
        }
    }
    
    function execute(action) {
        switch(action) {
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
        var bootstrapValidator = $("#page_form").data('bootstrapValidator');

        bootstrapValidator.revalidateField("date");
        bootstrapValidator.revalidateField("openComponent_supplier_supplCode");
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;
	
	    $.ajax({
	        url: "${ctx}/admin/commiCycle/supplierRebate/doSave",
	        type: "POST",
	        data: $("#page_form").jsonForm(),
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
        var bootstrapValidator = $("#page_form").data('bootstrapValidator');

        bootstrapValidator.revalidateField("date");
        bootstrapValidator.revalidateField("openComponent_supplier_supplCode");
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) return;
	
	    $.ajax({
	        url: "${ctx}/admin/commiCycle/supplierRebate/doUpdate",
	        type: "POST",
	        data: $("#page_form").jsonForm(),
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
