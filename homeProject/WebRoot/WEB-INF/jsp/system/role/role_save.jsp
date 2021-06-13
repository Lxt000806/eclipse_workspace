<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加角色</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			valid : 'glyphicon ',
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
function save(){
	//验证
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	if($("#infoBoxDiv").css("display")!='none'){return false;}
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/role/doSave',
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

function createTree(){
	var nodes = zNodes_cityCodes;
	for(var i=0; i<nodes.length; i++){
		if(nodes[i].itemCode != '${fuJianCode}'){
			nodes[i].pId = '${fuJianCode}';
		}else{
			nodes[i].pId = "";
			nodes[i].open = true;
		}
	}
}

function fliterFuJian(e, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var checkNodes = zTree.getCheckedNodes(true);
	var myIds = [];
	var myNames = [];
	for(var i=0; i<checkNodes.length; i++){
		if(checkNodes[i].check_Child_State == -1 || checkNodes[i].check_Child_State == 2){
			myIds.push(checkNodes[i].itemCode);
			myNames.push(checkNodes[i].itemLabel);
		}
	}
	resetVal(myIds, myNames)
	return true;
}

function resetVal(myIds, myNames){
	$("#cityCodes").val(myIds.join(','));
	$("#cityCodes_NAME").val(myNames.join(','))
}

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
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
						<ul class="ul-form">
							<div class="row">
								<li class="form-validate">
									<label><span class="required">*</span>角色名称</label>
									<input type="text" style="width:160px;" id="roleName" name="roleName"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>角色编码</label>
									<input type="text" style="width:160px;" id="roleCode" name="roleCode"/> 
								</li>
							</div>
							<div class="row">
								<li class="form-validate">
									<label><span class="required">*</span>平台类型</label>
									<house:dict id="sysCode" dictCode="ptdm" unShowValue="0"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>优先级</label>
									<input type="text" style="width:160px;" id="priority" name="priority" value="0"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'').replace('.','');"/>
								</li>
							</div>
							<li class="form-validate">
								<label class="control-textarea">备注</label>
								<textarea  rows="2"  id="remark" name="remark"></textarea>
							</li>
						</ul>
			</form>
			</div>
			</div>
	</div>
</div>
</body>
</html>
