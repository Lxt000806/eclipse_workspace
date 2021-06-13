<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加职位</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/position/doSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
                    content : obj.msg,
                    time : 1000,
                    beforeunload : function() {
                        closeWin();
                    }
                });
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}
//校验函数
$(function() {
	changeType();
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                code:{
                    validators:{
                        notEmpty:{
                            message:"请填写职位编号"
                        },
                        numeric:{
                            message:"职位编号只能是数字"
                        }
                    }
                },
                desc2:{
                    validators:{
                        notEmpty:{
                            message:"请填写职位名称"
                        }
                    }
                },
                type:{
                    validators:{
                        callback:{
                           message:"请选择类型",
                           callback:function(value){
                               if(value === ""){
                                    return false;
                               }
                               return true;
                           } 
                        }
                    }
                }
            }
	});
});
function changeType(){	
   	if ($("#type").val()=='6'){
   		$("#isSign").attr("disabled",false);  
	}else{
		$("#isSign").val(" ");
		$("#isSign").attr("disabled",true); 		  
	}	
}
</script>

</head>
    
<body>
<form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
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
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;"><span
                                    class="required">*</span>职位编号</label> <input type="text" style="width:160px;" value="保存时自动生成" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>职位名称</label>
                                <input type="text" id="desc2" name="desc2" style="width:160px;" value="${position.desc2}" />
                            </li>
                            <li class="form-validate"><label><span class="required">*</span>类型</label> <house:xtdm
								id="type" dictCode="POSTIONTYPE" value="${position.type}"  headerLabel="请选择" onchange="changeType()"></house:xtdm>
							</li>
                            <li class="form-validate"><label><span class="required">*</span>是否监理</label> <house:xtdm
								id="isSign" dictCode="YESNO" value="${position.isSign}"  headerLabel="请选择" ></house:xtdm>
							</li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>
