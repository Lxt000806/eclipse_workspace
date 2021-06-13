<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>班组成员--编辑</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
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
                <input type="hidden" id="expired" name="expired" value="${map.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>工人编号</label>
                            <input type="text" id="workercode" name="workercode" value="${map.workercode}" placeholder="自动生成" readonly/>
                        </li>
                        <li>
                            <label>成员编号</label>
                            <input type="text" id="code" name="code" value="${map.code}" placeholder="自动生成" readonly/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>成员姓名</label>
                            <input type="text" id="namechi" name="namechi" value="${map.namechi}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>手机号</label>
                            <input type="text" id="phone" name="phone" value="${map.phone}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>是否班组长</label>
                            <house:xtdm id="isheadman" dictCode="YESNO" value="${map.isheadman}" disabled="true"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>身份证号</label>
                            <input type="text" id="idnum" name="idnum" value="${map.idnum}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>卡号1</label>
                            <input type="text" id="cardid" name="cardid" value="${map.cardid}" placeholder="建行卡"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>开户行及支行1</label>
                            <input type="text" id="bank" name="bank" value="${map.bank}" placeholder="如：建行福州铜盘支行"/>
                        </li>
                        <li class="form-validate">
                            <label>卡号2</label>
                            <input type="text" id="cardid2" name="cardid2" value="${map.cardid2}"/>
                        </li>
                        <li class="form-validate">
                            <label>开户行及支行2</label>
                            <input type="text" id="bank2" name="bank2" value="${map.bank2}" placeholder="如：建行福州铜盘支行"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>工资分配比例</label>
                            <input type="text" id="salaryratio" name="salaryratio" value="${map.salaryratio}"/>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${map.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
    
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                namechi: {
                    validators: {
                        notEmpty: {message: '成员姓名不能为空'}
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {message: '手机号不能为空'},
                        phone: {
	                        message: '手机号为11位数字',
	                        country: 'CN'
	                    }
                    }
                },
                idnum: {
                    validators: {
                        notEmpty: {message: '身份证号码不能为空'},
                        stringLength: {
                            message: '身份证号码为15位到18位',
                            min: 15,
                            max: 18
                        }
                    }
                },
                cardid: {
                    validators: {
                        notEmpty: {message: '卡号1不能为空'},
                        stringLength: {
                            message: '银行卡号码为16位到19位数字',
                            min: 16,
                            max: 19,
                        },
                        integer: {
                            message: '银行卡号码为16位到19位数字'
                        }
                    }
                },
                bank: {
                    validators: {
                        notEmpty: {message: '开户行及支行1不能为空'},
                    }
                },
                cardid2: {
                    validators: {
                        callback: {
                            message: '卡号2不能为空',
                            callback: function(cardid2, validator, $field) {
                                var bank2 = validator.getFieldElements("bank2").val()
                                
                                if (!cardid2 && !bank2) {
                                    validator.updateStatus("cardid2", "VALID")
                                    validator.updateStatus("bank2", "VALID")
                                    return true
                                } else if (cardid2 && !bank2) {
                                    validator.updateStatus("bank2", "INVALID")
                                    return true
                                } else if (!cardid2 && bank2)
                                    return false
                                else
                                    return true
                            }
                        },
                        stringLength: {
                            message: '长度16位到19位',
                            min: 16,
                            max: 19,
                        },
                        integer: {
                            message: '银行卡号码须为数字'
                        }
                    }
                },
                bank2: {
                    validators: {
                        callback: {
                            message: '开户行及支行2不能为空',
                            callback: function(bank2, validator, $field) {
                                var cardid2 = validator.getFieldElements("cardid2").val()
                                
                                if (!cardid2 && !bank2) {
                                    validator.updateStatus("cardid2", "VALID")
                                    validator.updateStatus("bank2", "VALID")
                                    return true
                                } else if (cardid2 && !bank2)
                                    return false
                                else if (!cardid2 && bank2) {
                                    validator.updateStatus("cardid2", "INVALID")
                                    return true
                                } else
                                    return true
                            }
                        }
                    }
                },
                salaryratio: {
                    validators: {
                        notEmpty: {message: '工资分配比例不能为空'},
                        between: {min: 0, max: 1, message: '工资分配比例须在0到1之间'}
                    }
                },
            }
        })
        
    })

    function save() {
        var bv = $("#page_form").data('bootstrapValidator')
        bv.validate()
        if (!bv.isValid()) return
    
        var data = $("#page_form").jsonForm()
        
        Global.Dialog.returnData = data
        closeWin()
    }

</script>
</body>
</html>
