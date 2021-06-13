<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工种分类12--编辑上一工种</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function saveBefWorkType(){
    $("#dataForm").bootstrapValidator('validate');
    if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var workType12 = "${workType12.code}";
    var newBefWorkTypeId = $("#befWorkType option:selected").val();
    var isEval = $("#isEval option:selected").val();
    var isNext = $("#isNext option:selected").val();
    var workerArrCtrl = $("#workerArrCtrl").val();
    var xhr = new XMLHttpRequest();
    var url = "${ctx}/admin/workType12/doUpdateBefWorkType?workType12Code=" + workType12
                    + "&oldBefWorkTypeId=${befWorkType12Record.id.befWorktype12}" 
                    + "&newBefWorkTypeId=" + newBefWorkTypeId 
                    + "&isEval=" + isEval
                    + "&isNext=" + isNext+"&workerArrCtrl="+workerArrCtrl;
    xhr.open("get", url, true);
    xhr.onload = function(){
        if(xhr.responseText === "success"){
            //刷新iframe窗口
            window.location.reload(true);
        }else{
            art.dialog({
                content : "编辑上一工种失败，请重试"
            });
        }
    }
    xhr.send(null);
}

$(function() {
        $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                befWorkType:{
                    validators:{
                        callback:{
                            message:"请选择上一工种",
                            callback:function(value){
                                if(value === ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                },
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
                        <button type="button" class="btn btn-system" id="saveBefWorkTypeBut"
                            onclick="saveBefWorkType()">
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
                            <li class="form-validate">
                                <label style="width:100px;">上一工种</label>
                                <house:dict id="befWorkType" dictCode=""
                                    sql="select rtrim(Code)+ ' '+ Descr fd, Code from tWorkType12 where Code != ${workType12.code} and Code not in(select BefWorkType12 from tBefWorkType12 where WorkType12 = ${workType12.code} and BefWorkType12 != ${befWorkType12Record.id.befWorktype12}) order by Code"
                                    sqlValueKey="Code" sqlLableKey="fd" value="${befWorkType12Record.id.befWorktype12}" style="width:110px;" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">是否评价</label>
                                <select id="isEval" name="isEval" style="width:110px;">
                                    <option value="1" ${befWorkType12Record.isEval.equals('1')?'selected':''}>是</option>
                                    <option value="0" ${befWorkType12Record.isEval.equals('0')?'selected':''}>否</option>
                                </select>
                            </li>    
                            <li class="form-validate">
                                <label style="width:100px;">是否相邻工种</label>
                                <select id="isNext" name="isNext" style="width:110px;">
                                    <option value="1" ${befWorkType12Record.isNext.equals('1')?'selected':''}>是</option>
                                    <option value="0" ${befWorkType12Record.isNext.equals('0')?'selected':''}>否</option>
                                </select>
                            </li>     
                            <li class="form-validate">
								<label>安排工人控制完成</label>
								<house:xtdm  id="workerArrCtrl" dictCode="WORKERARRCTRL" style="width:110px;" value="${befWorkType12Record.workerArrCtrl}"></house:xtdm>
							</li>           
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
