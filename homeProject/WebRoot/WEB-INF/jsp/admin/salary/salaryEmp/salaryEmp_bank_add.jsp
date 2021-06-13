<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加材料明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//校验函数	
$(function() {
	parent.$("#iframe_${frameName}").attr("height","98%"); //消灭掉无用的滑动条
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				bankType:{  
					validators: {  
						notEmpty: {  
							message: '银行卡类型不能为空'  
						}
					}  
				},
				actName:{  
					validators: {  
						notEmpty: {  
							message: '户名不能为空'  
						}
					}  
				},
				cardId:{  
					validators: {  
						notEmpty: {  
							message: '卡号不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${salaryEmp.m_umState}"=="V"){
		$("#saveBtn").addClass("hidden");
	}
	$("#saveBtn").on("click",function(){	
		$("#dataForm").bootstrapValidator('validate'); //验证表单
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
		var datas=$("#dataForm").jsonForm();					
	    console.log(datas); 
		Global.Dialog.returnData = datas;			
		closeWin();
	});	
});

</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system">保存</button>
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<input type="hidden" id="bankTypeDescr" name="bankTypeDescr" value="${salaryEmp.bankTypeDescr }">
					<house:token></house:token>
					<ul class="ul-form">
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span>银行</label> 
								<house:xtdm id="bankType" dictCode="SALBANKTYPE" value="${salaryEmp.bankType }" onchange="getDescrByCode('bankType','bankTypeDescr')"></house:xtdm>
						</li>
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span> 户名</label> <input type="text" id="actName"
							name="actName" value="${salaryEmp.actName }" />
						</li>
						<li class="form-validate"><label style="width:120px"><span
								class="required">*</span> 卡号</label> <input type="text" id="cardId"
							name="cardId" value="${salaryEmp.cardId }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
						<li >
							<label style="width:120px">备注</label>
							<textarea id="remarks" name="remarks" style="width:250px;border-radius:5px;">${salaryEmp.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
