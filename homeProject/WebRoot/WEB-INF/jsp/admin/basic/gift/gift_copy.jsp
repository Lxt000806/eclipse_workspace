<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
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
                <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <div class="body-box-form">
                <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
                    <input type="hidden" id="m_umState" name="m_umState" value="${gift.m_umState}"/>
                    <input type="hidden" id="expired" name="expired" value="${gift.expired}"/>
                    <input type="hidden" id="pk" name="pk" value="${gift.pk}"/>
                    <house:token></house:token>
                    <ul class="ul-form">
                        <div class="validate-group">
                            <li class="form-validate"><label><span class="required">*</span>优惠标题</label>
                                <input type="text" id="descr" name="descr" value="${gift.descr}"/>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>类型</label>
                                <house:xtdm id="type" dictCode="GIFTTYPE" value="${gift.type}" onchange="changeType()"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>开始时间</label>
                                <input type="text" id="beginDate" name="beginDate" class="i-date"
                                       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                       value="<fmt:formatDate value='${gift.beginDate}' pattern='yyyy-MM-dd'/>" />
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>结束时间</label>
                                <input type="text" id="endDate" name="endDate" class="i-date"
                                       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                       value="<fmt:formatDate value='${gift.endDate}' pattern='yyyy-MM-dd'/>" />
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate"><label><span class="required">*</span>优惠金额分类</label>
                                <select id="discAmtType" name="discAmtType"></select>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>优惠折扣类型</label>
                                <house:xtdm id="discType" dictCode="GIFTDISCTYPE" value="${gift.discType}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>适用最小面积</label>
                                <input type="text" id="minArea" name="minArea" style="width:160px;" value="${gift.minArea}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>最大适用面积</label>
                                <input type="text" id="maxArea" name="maxArea" style="width:160px;" value="${gift.maxArea}"/>
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate"><label><span class="required">*</span>活动名称</label>
                                <input type="text" id="actName" name="actName" value="${gift.actName}"/>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>优惠比例</label>
                                <input type="text" id="discPer" name="discPer" style="width:160px;" value="${gift.discPer}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;">报价模块</label>
                                <select id="quoteModule" name="quoteModule"></select>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;">是否服务性产品</label>
                                <house:xtdm id="isService" dictCode="YESNO" value="${gift.isService}"></house:xtdm>
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate">
                                <label class="control-textarea"><span class="required">*</span>优惠项目说明</label>
                                <textarea id="remarks" name="remarks">${gift.remarks}</textarea>
                            </li>
                            <li class="form-validate">
                                <label class="control-textarea" style="width:120px;">最高优惠金额公式</label>
                                <textarea id="maxDiscAmtExpr" name="maxDiscAmtExpr" style="width:470px">${gift.maxDiscAmtExpr}</textarea>
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate">
                                <label>业绩扣减方式</label>
                                <house:xtdm id="perfDiscType" dictCode="GIFTPERFDSTYPE" value="${gift.perfDiscType}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label>业绩扣减比例</label>
                                <input type="text" id="perfDiscPer" name="perfDiscPer" style="width:160px;" value="${gift.perfDiscPer}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;">优惠额度计算方式</label>
                                <house:xtdm id="discAmtCalcType" dictCode="GIFTDACALCTYPE" value="${gift.discAmtCalcType}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>计入优惠额度比例</label>
                                <input type="text" id="calcDiscCtrlPer" name="calcDiscCtrlPer" style="width:160px;" value="${gift.calcDiscCtrlPer}"/>
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate">
                                <label>套外项目</label>
                                <house:xtdm id="isOutSet" dictCode="YESNO" value="${gift.isOutSet}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label><span class="required">*</span>是否软装券</label>
                                <house:xtdm id="isSoftToken" dictCode="YESNO" value="${gift.isSoftToken}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;">是否限定报价项目</label>
                                <house:xtdm id="isLimitItem" dictCode="YESNO" value="${gift.isLimitItem}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:120px;">是否橱柜</label>
                                <house:xtdm id="isCupboard" dictCode="YESNO" value="${gift.isCupboard}"></house:xtdm>
                            </li>
                        </div>
                        <div class="validate-group">
                            <li class="form-validate">
                                <label>额度预支项目</label>
                                <house:xtdm id="isAdvance" dictCode="YESNO" value="${gift.isAdvance}"></house:xtdm>
                            </li>
                            <li class="form-validate">							
								<label>审批级别</label>
								<house:xtdm id="confirmLevel" dictCode="CONFIRMLEVEL" value="${gift.confirmLevel}"></house:xtdm>		
							</li>
                            <li class="form-validate">
                                <label style="width:120px;"><span class="required">*</span>显示顺序</label>
                                <input type="text" id="dispSeq" name="dispSeq" style="width:160px;" value="${gift.dispSeq}"/>
                            </li>
                        </div>
                    </ul>
                </form>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li id="tabCustType" class="active"><a href="#tab_custType" data-toggle="tab">适用产品线</a></li>
            <li id="tabItem" class=""><a href="#tab_item" data-toggle="tab">对应材料</a></li>
        </ul>
        <div class="tab-content">
            <div id="tab_custType" class="tab-pane fade in active">
                <jsp:include page="gift_tab_custType.jsp"></jsp:include>
            </div>
            <div id="tab_item" class="tab-pane fade">
                <jsp:include page="gift_tab_item.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" defer>
    $(function() {
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "quoteModule")
        var dataSet1 = {
            firstSelect: "quoteModule",
            firstValue: '${gift.quoteModule}',
        }
        Global.LinkSelect.setSelect(dataSet1)
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "discAmtType")
        var dataSet2 = {
            firstSelect: "discAmtType",
            firstValue: '${gift.discAmtType}',
        }
        Global.LinkSelect.setSelect(dataSet2)
        if ("" == "${gift.isAdvance}") $("#isAdvance").val("0")
        
        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {/*input状态样式图片*/},
            fields: {
                descr: {
                    validators: {
                        notEmpty: {
                            message: '优惠标题不能为空'
                        }
                    }
                },
                type: {
                    validators: {
                        notEmpty: {
                            message: '类型不能为空'
                        }
                    }
                },
                beginDate: {
                    validators: {
                        notEmpty: {
                            message: '开始时间不能为空'
                        },
                    }
                },
                endDate: {
                    validators: {
                        notEmpty: {
                            message: '结束时间不能为空'
                        },
                    }
                },
                discAmtType: {
                    validators: {
                        notEmpty: {
                            message: '优惠金额分类不能为空'
                        }
                    }
                },
                discType: {
                    validators: {
                        notEmpty: {
                            message: '优惠折扣类型不能为空'
                        }
                    }
                },
                minArea: {
                    validators: {
                        notEmpty: {
                            message: '适用最小面积不能为空'
                        },
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
                maxArea: {
                    validators: {
                        notEmpty: {
                            message: '适用最大面积不能为空'
                        },
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
                discPer: {
                    validators: {
                        notEmpty: {
                            message: '优惠比例不能为空'
                        },
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
                dispSeq: {
                    validators: {
                        notEmpty: {
                            message: '显示顺序不能为空'
                        },
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
                remarks: {
                    validators: {
                        notEmpty: {
                            message: '优惠项目说明不能为空'
                        }
                    }
                },
                perfDiscPer: {
                    validators: {
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
                actName: {
                    validators: {
                        notEmpty: {
                            message: '活动名称不能为空'
                        }
                    }
                },
                calcDiscCtrlPer: {
                    validators: {
                        notEmpty: {
                            message: '计入优惠额度控制比例'
                        },
                        numeric: {
                            message: '该值只能包含数字。'
                        }
                    }
                },
	            isSoftToken: {
	                validators: {
	                    notEmpty: {message: '请选择是否软装券'}
	                }
	            }
            },
            submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        })
        changeType()
    })

    function save() {
        $("#dataForm").bootstrapValidator('validate')
        if (!$("#dataForm").data('bootstrapValidator').isValid()) return
        
        var beginDate = $("#beginDate").val()
        var endDate = $("#endDate").val()
        if (beginDate > endDate) {
            art.dialog({content: "开始时间不能大于结束时间！"})
            return
        }
        
        if ("2" == $("#discAmtCalcType").val() && "ZC" != $("#discAmtType").val()) {
            art.dialog({
                content: "优惠额度计算方式:必须是主材类，才能按项目经理结算价计算",
            })
            return
        }
        
        var datas = $("#dataForm").jsonForm()
        var custTypeJson = allToJson('dataTable_custType', 'custTypeJson')
        var itemJson = allToJson('dataTable_item', 'itemJson')
        $.extend(datas, custTypeJson)
        $.extend(datas, itemJson)
        
        $.ajax({
            url: "${ctx}/admin/gift/doCopy",
            type: "post",
            data: datas,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin()
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({
                        content: obj.msg,
                        width: 200
                    })
                }
            }
        })
    }

    //重写allToJson方法，自定义表格名
    function allToJson(tableId, tableName) {
        var json = {}
        var rows = $("#" + tableId).jqGrid("getRowData")
        json[tableName] = JSON.stringify(rows)
        return json
    }

    function changeType() {
        if ($("#type").val() == "3") {
            $("#quoteModule").prev().prepend("<span class='required'>*</span>")
            $("#dataForm").bootstrapValidator("addField", "quoteModule", {
                validators: {
                    notEmpty: {
                        message: '报价模块不能为空'
                    }
                }
            })
        } else {
            $("#quoteModule").prev().text("报价模块")
            $('#dataForm').data('bootstrapValidator').updateStatus('quoteModule', 'NOT_VALIDATED', null)
            $("#dataForm").bootstrapValidator("removeField", "quoteModule")
        }
    }
</script>
</body>
</html>
