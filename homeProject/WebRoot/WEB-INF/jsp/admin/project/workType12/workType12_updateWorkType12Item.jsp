<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工种分类12--编辑工种施工需准备材料</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){
    $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                itemType1:{
                    validators:{
                        callback:{
                            message:"请选择材料类型1",
                            callback:function(value){
                                var itemType1OptionsLength = document.getElementById("itemType1").options.length;
                                if(itemType1OptionsLength > 1 && value === ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                },
                itemType2:{
                    validators:{
                        callback:{
                            message:"请选择材料类型2",
                            callback:function(value){
                                var itemType2OptionsLength = document.getElementById("itemType2").options.length;
                                if(itemType2OptionsLength > 1 && value === ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                },
                appType:{
                    validators:{
                        callback:{
                            message:"请选择申请类型",
                            callback:function(value){
                                if(value === ""){
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                },
                lastAppDay:{
                    validators:{
                        notEmpty:{
                            message:"不能为空"
                        },
                        numeric:{
                            message:"只能是数字"
                        }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});


function updateWorkType12Item(){
    $("#dataForm").bootstrapValidator('validate');
    if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas = new FormData(document.getElementById("dataForm"));
    var xhr = new XMLHttpRequest();
    var url = "${ctx}/admin/workType12/doUpdateWorkType12Item";
    xhr.open("post", url, true);
    xhr.onload = function(){
        if(xhr.responseText === "success"){
            //刷新iframe父窗口
            window.location.reload(true);
        }else{
            art.dialog({
                content : "编辑工种施工需准备材料失败，请重试"
            });
        }
    }
    xhr.send(datas);
}

$(function() {
    Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority",
            "itemType1", "itemType2", "itemType3");
    Global.LinkSelect.setSelect({
        firstSelect : "itemType1",
        firstValue : "${workType12Item.itemType1}",
        secondSelect : "itemType2",
        secondValue : "${workType12Item.itemType2}",
        thirdSelect : "itemType3",
        thirdValue : "${workType12Item.itemType3}",
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
                            onclick="updateWorkType12Item()">
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
                            <input type="hidden" name="pk" value="${workType12Item.pk}">
                            <input type="hidden" name="workType12" value="${workType12Item.workType12}">
                            <li class="form-validate"><label style="width:150px;">材料类型1</label> <select id="itemType1"
                                name="itemType1">
                            </select>
                            </li>
                            <li class="form-validate"><label style="width:150px;">材料类型2</label> <select id="itemType2"
                                name="itemType2">
                            </select>
                            </li>
                            <li class="form-validate"><label style="width:150px;">材料类型3</label> <select id="itemType3"
                                name="itemType3"></select>
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;">申请类型</label>
                                <house:dict id="appType" dictCode="" sql="select a.cbm cbm, a.note note from tXTDM a where a.id = 'APPTYPE'"
                                        sqlLableKey="note" sqlValueKey="cbm" unShowValue="0" headerLabel="请选择" value="${workType12Item.appType}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;">最后申请日与今天差几天</label>
                                <input type="text" name="lastAppDay" value="${workType12Item.lastAppDay}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;">过期</label>
                                <input type="hidden" id="expired" name="expired" value="${workType12Item.expired}" />
                                <input type="checkbox" onclick="checkExpired(this)" ${workType12Item.expired=='T'?'checked':''} />
                            </li>                                                             
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
