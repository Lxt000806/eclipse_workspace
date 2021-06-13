<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改角色</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var oldCode = '${role.roleCode }'

function save(){
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/role/doUpdate',
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
			roleCode:{  
				validators: {  
					notEmpty: {  
						message: '必填字段'  
					}
				}  
			},
			roleName:{  
				validators: {  
					notEmpty: {  
						message: '必填字段'  
					}
				}  
			},
			sysCode:{  
				validators: {  
					notEmpty: {  
						message: '必填字段'  
					}
				}  
			},
			priority:{  
				validators: {  
					notEmpty: {  
						message: '必填字段'  
					}
				}  
			},
			remark:{  
				validators: {  
					stringLength: {
                        max: 200,
                        message: '长度不超过200个字符'
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
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" id="roleId" name="roleId" value="${role.roleId }"/>
				<input type="hidden" id="createrId" name="createrId" value="${role.createrId }"/>
				<input type="hidden" id="oldRoleCode" name="oldRoleCode" value="${role.roleCode }"/>
				<ul class="ul-form">
					<div class="row">
						<li class="form-validate">
							<label><span class="required">*</span>角色名称</label>
						<input type="text" style="width:160px;" id="roleName" name="roleName" value="${role.roleName }"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>角色编码</label>
							<input type="text" id="roleCode" name="roleCode" value="${role.roleCode }"/>
						</li>
					</div>
					<div class="row">
						<li class="form-validate">
							<label><td class="td-label"><span class="required">*</span>平台类型</label>
							<house:dict id="sysCode" dictCode="ptdm" value="${role.sysCode }" unShowValue="0"></house:dict>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>优先级</label>
							<input type="text" style="width:160px;" id="priority" name="priority" value="${role.priority }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'').replace('.','');"/>
						</li>
					</div>
					<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea  rows="2" id="remark" name="remark">${role.remark }</textarea>
					</li>
				</ul>	
			</form>
			</div>
			</div>
	</div>
</div>
</body>
</html>
