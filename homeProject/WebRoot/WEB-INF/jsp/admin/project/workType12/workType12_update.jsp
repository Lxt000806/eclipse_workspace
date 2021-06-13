<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工种分类12--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
    $(function() {
        $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                descr:{
                    validators:{
                        notEmpty:{
                            message:"工种分类12名称不能为空"
                        }
                    }
                },
                workType1:{
                    validators:{
                        callback:{
                            message:"请选择工种分类1",
                            callback:function(value){
                                if(value === ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                },
                payNum:{  
                    validators: {
                        callback:{
                            message:"付款期数只能是0-9期",
                            callback:function(value){
                                var v = $.trim(value);
                                var regexp = new RegExp("^[0-9]{1}$");
                                if(v.length != 0 && !regexp.test(v)){
                                    return false;
                                }
                                return true;
                            }
                        }
                    } 
                },
                beginCheck: {
                    validators: {
                        notEmpty: {
                            message: "请选择工期考核启动工种"
                        }
                    }
                },
                minSalaryProvideAmount: {
                    validators: {
                        notEmpty: {message: '最低发放金额不能为空'},
                        numeric: {message: '最低发放金额须为数字'}
                    }
                },
                confType: {
                    validators: {
                        notEmpty: {
                            message: "请选择验收类型"
                        }
                    }
                },
                isRegisterMall: {
                    validators: {
                        notEmpty: {
                            message: "请选择是否用于自装通"
                        }
                    }
                },
                isPrjApp: {
                    validators: {
                        notEmpty: {
                            message: "请选择是否项目经理申请"
                        }
                    }
                },
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });

        //保存更新按钮                        
        $("#updateBtn").on("click", function() {
            $("#dataForm").bootstrapValidator('validate');
            if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
            if($("#salaryCtrl").val().trim() == ''){
           		$("#salaryCtrl").val(0.0);
            }
            var datas = $("#dataForm").serialize();
            if($("#maxPhotoNum").val().trim() != '' && $("#minPhotoNum").val().trim() !=''){
	            if(myRound($("#maxPhotoNum").val()) < myRound($("#minPhotoNum").val())){
	            	art.dialog({
	                    content :"最少上传数量不能大于最大上传数量!",
	                });
	                return;
	            }
            }
            $.ajax({
                url : "${ctx}/admin/workType12/doUpdate",
                type : "post",
                data : datas,
                dataType : "json",
                cache : false,
                error : function(obj) {
                    showAjaxHtml({
                        "hidden" : false,
                        "msg" : "保存数据出错~"
                    });
                },
                success : function(obj) {
                    if (obj.rs) {
                        art.dialog({
                            content : obj.msg,
                            time : 1000,
                            beforeunload : function() {
                                closeWin();
                            }
                        });
                    } else {
                        $("#_form_token_uniq_id").val(obj.token.token);
                        art.dialog({
                            content : obj.msg,
                            width : 200
                        });
                    }
                }
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
                        <button type="button" class="btn btn-system" id="updateBtn">
                            <span>保存</span>
                        </button>
                        <button type="button" class="btn btn-system" id="closeBut"
                            onclick="closeWin(false)">
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
                            <li class="form-validate"><label style="width:200px;"><span
                                    class="required">*</span>编号</label> <input type="text" id="code"
                                maxlength="10" name="code" style="width:160px;"
                                value="${workType12.code}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>名称</label>
                                <input type="text" id="descr" name="descr" value="${workType12.descr.trim()}"
                                    style="width:160px;" value="0" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>工种分类1</label>
                                <house:dict id="workType1" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tWorkType1 order by code"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.workType1.trim()}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">人工工种分类2</label>
                                <house:dict id="offerWorkType2" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tWorkType2 order by dispSeq"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.offerWorkType2}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">需要工种分类2人工需求</label>
                                <house:dict id="needWorktype2Req" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tWorkType2 order by dispSeq"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.needWorktype2Req.trim()}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">上一同类工种</label>
                                <house:dict id="befSameWorkType12" dictCode=""
                                    sql="select rtrim(Code)+ ' '+ Descr fd, Code from tWorkType12 order by Code"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.befSameWorkType12.trim()}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">签约每工地扣质保金</label>
                                <input type="text" id="perQualityFee" name="perQualityFee" style="width:160px;"
                                    value="${workType12.perQualityFee}"
                                    data-bv-numeric data-bv-numeric-message="签约每工地扣质保金只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">签约质保金总额</label>
                                <input type="text" id="signQualityFee" name="signQualityFee" style="width:160px;"
                                    value="${workType12.signQualityFee}"
                                    data-bv-numeric data-bv-numeric-message="签约质保金总额只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">质保金起扣点</label>
                                <input type="text" id="qualityFeeBegin" name="qualityFeeBegin" style="width:160px;"
                                    value="${workType12.qualityFeeBegin}"
                                    data-bv-numeric data-bv-numeric-message="质保金起扣点只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">付款期数</label>
                                <input type="text" id="payNum" name="payNum" style="width:160px;" value="${workType12.payNum}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>是否付款才允许申请</label>
                                <select name="mustPay">
                                    <option value="0" ${workType12.mustPay==0?'selected':''}>否</option>
                                    <option value="1" ${workType12.mustPay==1?'selected':''}>是</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">最少施工天数</label>
                                <input type="text" id="minDay" name="minDay" style="width:160px;"
                                    value="${workType12.minDay}" data-bv-numeric data-bv-numeric-message="最少施工天数只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">最大施工天数</label>
                                <input type="text" id="maxDay" name="maxDay" style="width:160px;"
                                    value="${workType12.maxDay}" data-bv-numeric data-bv-numeric-message="最大施工天数只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">申请最小延后天数</label>
                                <input type="text" id="appMinDay" name="appMinDay" style="width:160px;"
                                    value="${workType12.appMinDay}" data-bv-numeric data-bv-numeric-message="申请最小延后天数只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">申请最大延后天数</label>
                                <input type="text" id="appMaxDay" name="appMaxDay" style="width:160px;"
                                    value="${workType12.appMaxDay}" data-bv-numeric data-bv-numeric-message="申请最大延后天数只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">开始施工项目</label>
                                <house:dict id="beginPrjItem" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tPrjItem1 order by code"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.beginPrjItem}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">结束施工项目</label>
                                <house:dict id="prjItem" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tPrjItem1 order by code"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.prjItem}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">验收施工项目</label>
                                <house:dict id="confPrjItem" dictCode="" sql="select rtrim(Code)+ ' '+ Descr fd, Code from tPrjItem1 order by seq"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${workType12.confPrjItem}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">显示顺序</label>
                                <input type="text" id="dispSeq" name="dispSeq" style="width:160px;"
                                    value="${workType12.dispSeq}" data-bv-numeric data-bv-numeric-message="显示顺序只能是数值" />
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">最少上传图片数量</label>
                                <input type="text" id="minPhotoNum" name="minPhotoNum" style="width:160px;"onkeyup="value=value.replace(/[^\?\d]/g,'')"
                                    value="${workType12.minPhotoNum}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">最多上传图片数量</label>
                                <input type="text" id="maxPhotoNum" name="maxPhotoNum" style="width:160px;"onkeyup="value=value.replace(/[^\?\d]/g,'')"
                                    value="${workType12.maxPhotoNum}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">定额工资控制比例</label>
                                <input type="text" id="salaryCtrl" name="salaryCtrl" style="width:160px;"onkeyup="value=value.replace(/[^\?\d.]/g,'')"
                                    value="${workType12.salaryCtrl}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>工期考核启动工种</label>
                                <house:xtdm id="beginCheck" dictCode="YESNO" value="${workType12.beginCheck}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>最低发放金额</label>
                                <input type="text" id="minSalaryProvideAmount" name="minSalaryProvideAmount" value="${workType12.minSalaryProvideAmount}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>验收类型</label>
                                <house:xtdm id="confType" dictCode="WT12CONFTYPE" value="${workType12.confType}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>是否用于自装通</label>
                                <house:xtdm id="isRegisterMall" dictCode="YESNO" value="${workType12.isRegisterMall}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;"><span class="required">*</span>是否项目经理申请</label>
                                <house:xtdm id="isPrjApp" dictCode="YESNO" value="${workType12.isPrjApp}"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">过期</label>
                                <input type="hidden" id="expired" value="${workType12.expired}" />
                                <input type="checkbox" name="expired" value="T" onclick="checkExpired(this)"
                                    ${workType12.expired.equals('T')?'checked':''}  />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
