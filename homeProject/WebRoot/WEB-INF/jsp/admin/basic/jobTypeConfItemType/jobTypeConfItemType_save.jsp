<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加任务材料分类对应关系</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function save(){
    //校验数据
	$("#dataForm").bootstrapValidator('validate');
    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/jobTypeConfItemType/doSave',
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
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
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
	$("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                jobType:{
                    validators:{
                        notEmpty:{
                            message:"请选择任务类型"
                        }
                    }
                }, 
                confItemType:{
                    validators:{
                        notEmpty:{
                            message:"请选择施工材料类型"
                        }
                    }
                }, 
                
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});

</script>
</head>
<body>
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
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<ul class="ul-form">
					   <li class="form-validate">
	                       <label><span class="required">*</span>编号</label>
	                       <input type="text" value="保存时自动生成" disabled="disabled" />
                       </li>
                       <li class="form-validate">
                           <label><span class="required">*</span>任务类型</label>
                           <house:dict id="jobType" dictCode="" sql=" select Code,Descr from tJobType where expired = 'F' and isMaterialsendjob = '1' order by Code " 
                                   sqlValueKey="Code" sqlLableKey="Descr" />
                       </li>
                       <li class="form-validate">
                        <label><span class="required">*</span>施工材料类型</label>
                        	<house:dict id="confItemType" dictCode="" sql=" select Code,Descr from tConfItemType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" />
                       </li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>   
</html>
