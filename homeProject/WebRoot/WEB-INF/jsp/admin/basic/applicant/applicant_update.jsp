<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改应聘人员信息</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
</style>
<script type="text/javascript">
    function update() {
        $("#dataForm").bootstrapValidator('validate');
        if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
        var idNum = document.getElementById("idNum").value;
        var datas1 = $("#dataForm").serialize();
        var checkIdNumExist = idNum === "" ? false : true;
        datas1 += "&checkIdNumExist=" + checkIdNumExist;
        //第一次尝试保存
        $.ajax({
                url : "${ctx}/admin/applicant/doUpdate",
                type : "post",
                data : datas1,
                dataType : "json",
                cache : false,
                error : function(resp1) {
                    showAjaxHtml({
                        "hidden" : false,
                        "msg" : "保存数据出错~"
                    });
                },
                success : function(resp1) {
                    //发生异常，保存失败
                    if(resp1.success === false && resp1.errorcode === "exception"){
                        art.dialog({
                            content:"修改应聘人员信息失败，请重试！"
                        });
                        return;
                    }
                    //身份证号已存在
                    if(resp1.success === false && resp1.errorcode === "idNumExist"){
                        art.dialog({
                            content:"应聘人员中已存在相同身份证号，是否继续保存",
                            //第二次保存
                            ok:function(){
                                var datas2 = $("#dataForm").serialize();
                                datas2 += "&checkIdNumExist=false";
                                $.ajax({
                                    url : "${ctx}/admin/applicant/doUpdate",
                                    type : "post",
                                    data : datas2,
                                    dataType : "json",
                                    cache : false,
                                    error : function(resp2) {
                                        showAjaxHtml({
                                            "hidden" : false,
                                            "msg" : "保存数据出错~"
                                        });
                                    },
                                    success : function(resp2) {
                                        //再次保存时成功
                                        if(resp2.success === true){
                                            art.dialog({
                                                content:"保存成功",
                                                time:1000,
                                                beforeunload:function(){
                                                    closeWin();
                                                }
                                            });
                                            return;
                                        }
                                        //再次保存时失败
                                        if(resp2.success === false){
                                            art.dialog({
                                                content:"修改应聘人员信息失败，请重试！"
                                            });
                                            return;
                                        }
                                    }
                                 });
                            },
                            cancel:function(){}
                        });
                        return;
                    }
                    //保存成功
                    if(resp1.success === true){
                        art.dialog({
                            content:"保存成功",
                            time:1000,
                            beforeunload:function(){
                                closeWin();
                            }
                        });
                        return;
                    }
                }
            });
    }

//校验函数
$(function() {    
    $("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                nameChi:{
                    validators:{
                        notEmpty:{
                            message:"请输入姓名"
                        }
                    }
                },
                phone:{
                    validators:{
                        notEmpty:{
                            message:"请填写电话"
                        },
                        stringLength : {
                            min : 11,
                            max : 11,
                            message : "电话长度必须11位"
                        }
                    }
                },
                positionDescr:{
                    validators:{
                        notEmpty:{
                            message:"请填写面试岗位"
                        }
                    }
                },
                appDate:{
                    validators:{
                        notEmpty:{
                            message:"请选择面试时间"
                        }
                    }
                },
                idNum : {
                    validators : {
                       callback:{
                           message:"请输入正确的身份证号",
                           callback:function(value){
                               var regexp = new RegExp("[a-zA-Z0-9]+");
                               var idNumLength = value.length;
                               if(idNumLength === 0)
                                    return true;
                               if((idNumLength === 15 || idNumLength === 18) && regexp.test(value))
                                    return true;
                               return false;
                           }
                       }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'
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
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="update()">
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
                    <input type="hidden" id="expired" name="expired" value="${applicant.expired}" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label >序号</label> <input type="text" id="pk" name="pk"
                                 value="${applicant.pk}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label ><span class="required">*</span>姓名</label>
                                <input type="text" id="nameChi" name="nameChi"  value="${applicant.nameChi}" />
                            </li>
                            <li class="form-validate">
                                <label >状态</label>
                                <house:dict dictCode="" id="status" sql="select cbm, note from tXTDM where ID = 'APPLICANTSTS'"
                                    sqlLableKey="note" sqlValueKey="cbm" value="${applicant.status}" />
                            </li>
                            <li class="form-validate">
                                <label ><span class="required">*</span>性别</label>
                                <select id="gender" name="gender">
                                    <option value="M" ${applicant.gender == 'M' ? 'selected' : ''}>男</option>
                                    <option value="F" ${applicant.gender == 'F' ? 'selected' : ''}>女</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label >籍贯</label>
                                <input type="text" id="birtPlace" name="birtPlace"  value="${applicant.birtPlace}" />
                            </li>
                            <li class="form-validate">
                                <label >身份证号</label>
                                <input type="text" id="idNum" name="idNum" maxLength="18" value="${applicant.idNum}" />
                            </li>
                            <li class="form-validate">
                                <label >文化程度</label>
                                <house:dict dictCode="" id="edu" sql="select cbm, note from tXTDM where ID = 'EDU'"
                                    sqlLableKey="note" sqlValueKey="cbm" value="${applicant.edu}" />
                            </li>
                            <li class="form-validate">
                                <label >出生日期</label>
                                <input type="text" id="birth" name="birth" class="i-date"  
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
                                    value="<fmt:formatDate value='${applicant.birth}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label >毕业院校</label>
                                <input type="text" id="school" name="school"  value="${applicant.school}" />
                            </li>
                            <li class="form-validate">
                                <label ><span class="required">*</span>电话</label>
                                <input type="text" id="phone" name="phone" maxLength="11" value="${applicant.phone}" />
                            </li>
                            <li class="form-validate">
                                <label >专业</label>
                                <input type="text" id="schDept" name="schDept"  value="${applicant.schDept}" />
                            </li>
                            <li class="form-validate">
                                <label >家庭住址</label>
                                <input type="text" id="address" name="address" style="width:450px;" value="${applicant.address}" />
                            </li>
                            <li class="form-validate">
                                <label >应聘部门</label>
                                <input type="text" id="deptDescr" name="deptDescr"  value="${applicant.deptDescr}" />
                            </li>
                            <li class="form-validate">
                                <label >一级部门</label>
                                <input type="text" id="dept1Descr" name="dept1Descr"  value="${applicant.dept1Descr}" />
                            </li>
                            <li class="form-validate">
                                <label ><span class="required">*</span>面试岗位</label>
                                <input type="text" id="positionDescr" name="positionDescr"  value="${applicant.positionDescr}" />
                            </li>
                            <li class="form-validate">
                                <label >途径</label>
                                <input type="text" id="source" name="source"  value="${applicant.source}" />
                            </li>
                            <li class="form-validate">
                                <label ><span class="required">*</span>面试时间</label>
                                <input type="text" id="appDate" name="appDate" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
                                    value="<fmt:formatDate value='${applicant.appDate}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label >预入时间</label>
                                <input type="text" id="comeInDate" name="comeInDate" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
                                    value="<fmt:formatDate value='${applicant.comeInDate}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label >备注</label>
                                <textarea type="text" id="remarks" name="remarks" style="width:450px;" rows="3">${applicant.remarks}</textarea>
                            </li>
                            <li>
                                <label >过期</label>
                                <input type="checkbox" onclick="checkExpired(this)"
                                    ${applicant.expired.equals('T') ? 'checked':''} />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>
