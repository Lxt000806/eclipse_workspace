<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理--编辑成绩信息</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//用于将对象的属性名全部转为小写
function nameTo_(object) {
    let regObj = new RegExp("([A-Z]+)", "g");
    for (let i in object) {
        if (object.hasOwnProperty(i)) {
            let temp = object[i];
            if (regObj.test(i.toString())) {
                temp = object[i.replace(regObj, function (result) {
                    return result.toLowerCase();
                })] = object[i];
                delete object[i];
            }
            if (typeof temp === 'object' || Object.prototype.toString.call(temp) === '[object Array]') {
                nameTo_(temp);
            }
        }
    }
    return object;
};

function updateScoreDetailInfo(){
    $("#dataForm").bootstrapValidator('validate');
    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas = new FormData();
    datas.append("empCode", '${emp.number}');
    var xhr = new XMLHttpRequest();
    xhr.open("post", "${ctx}/admin/course/doAddOrUpdateScoreDetailInfo", true);
    xhr.onload = function(){
        var respResult = JSON.parse(xhr.responseText);
        if(respResult.success === true){
            var empInfo = respResult.data;//员工信息
            //添加课程成绩信息
            empInfo.score = document.getElementById("score").value;//成绩
            empInfo.ispass = document.getElementById("isPass").value;//是否毕业
            empInfo.ispassdescr = empInfo.ispass === "0" ? "否" : "是";
            empInfo.ismakeup = document.getElementById("isMakeUp").value;//是否补考
            empInfo.ismakeupdescr = empInfo.ismakeup === "0" ? "否" : "是";
            empInfo.makeupscore = document.getElementById("makeUpScore").value;//补考成绩
            empInfo.remark = document.getElementById("remark").value;//备注
            nameTo_(empInfo);
            Global.Dialog.returnData = empInfo;
            closeWin();
        }else{
            art.dialog({
                content:respResult.message
            });
        }
    }
    xhr.send(datas);
}




    $(function() {

        $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                empCode:{
                    validators:{
                        notEmpty:{
                            message:"员工编号不能为空"
                        }
                    }
                }, 
                score:{  
                    validators: {
                        notEmpty:{
                            message:"成绩不能为空"
                        },
                        numeric:{
                            message:"成绩只能是数值"
                        }
                    } 
                },
                makeUpScore:{
                    validators:{
                        numeric:{
                            message:"补考成绩只能是数值"
                        },
                        callback:{
                            message:"未补考不能填写补考成绩",
                            callback:function(value){
                                var isMakeUp = $("#isMakeUp option:selected").val();
                                if(isMakeUp == "0" && value.trim() !== ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
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
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="updateScoreDetailInfo()">
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
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:150px;"><span
                                    class="required">*</span>员工编号</label> <input type="text" id="empCode" readonly="only"
                                maxlength="10" name="empCode" style="width:150px;" value="${emp.number.concat('|').concat(emp.nameChi)}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>成绩</label>
                                <input type="text" id="score" name="score" style="width:150px;" value="${score.score}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>是否毕业</label>
                                <select id="isPass" name="isPass" style="width:150px;">
                                    <option value="0" <c:if test="${score.isPass.equals('0')}">selected</c:if>>否</option>
                                    <option value="1" <c:if test="${score.isPass.equals('1')}">selected</c:if>>是</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>是否补考</label>
                                <select id="isMakeUp" name="isMakeUp" style="width:150px;">
                                    <option value="0" <c:if test="${score.isMakeUp.equals('0')}">selected</c:if>>否</option>
                                    <option value="1" <c:if test="${score.isMakeUp.equals('1')}">selected</c:if>>是</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>补考成绩</label>
                                <input type="text" id="makeUpScore" name="makeUpScore" style="width:150px;" value="${score.makeUpScore}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;">备注</label>
                                <textarea id="remark" name="remark" style="width:150px;">${socre.remark}</textarea>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
