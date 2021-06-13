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
	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				custType:{  
					validators: {  
						notEmpty: {  
							message: '客户类型不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${gift.m_umState}"=="V"){
		$("#saveBtn").attr("disabled",true);
	}
	$("#saveBtn").on("click",function(){	
		$("#dataForm").bootstrapValidator('validate'); //验证表单
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	 	var custType=$("#custType").val();
		var custTypes="${gift.custTypes}";
		if("${gift.m_umState}"=="M"){
			if(custTypes.indexOf(custType)>-1 && custType!="${gift.custType}"){
				art.dialog({    	
					content: "客户类型重复！"
				});
				return;
			}
		}else{
			if(custTypes.indexOf(custType)>-1){
				art.dialog({    	
					content: "客户类型重复！"
				});
				return;
			}
		} 
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
			selectRows.push(datas);		
		Global.Dialog.returnData = selectRows;			
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
		      <button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<input type="hidden" id="custTypeDescr" name="custTypeDescr" value="${gift.custTypeDescr}" />
					<house:token></house:token>
					<ul class="ul-form">
						<li class="form-validate"><label>客户类型</label> <house:dict
								id="custType" dictCode="" sql="select Code ,Code +' '+Desc1 descr  
								from tCustType where Expired = 'F'  order By dispSeq "
								sqlValueKey="code" sqlLableKey="descr" value="${gift.custType.trim()}" onchange="getDescrByCode('custType','custTypeDescr')">
							</house:dict>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
