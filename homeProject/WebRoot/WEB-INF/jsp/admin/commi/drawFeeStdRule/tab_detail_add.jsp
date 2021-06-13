<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加明细</title>
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
				beginArea:{  
					validators: {  
						notEmpty: {  
							message: '起始面积不能为空'  
						}
					}  
				},
				endArea:{  
					validators: {  
						notEmpty: {  
							message: '截止面积不能为空'  
						}
					}  
				},
				colorDrawFee:{  
					validators: {  
						notEmpty: {  
							message: '普通效果图费不能为空'  
						}
					}  
				},
				colorDrawFee3D:{  
					validators: {  
						notEmpty: {  
							message: '3d效果图费不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${drawFeeStdRule.m_umState}"=="V"){
		$("#saveBtn").attr("disabled",true);
	}
	$("#saveBtn").on("click",function(){	
		$("#dataForm").bootstrapValidator('validate'); //验证表单
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
		var selectRows = [];		 
		var datas=$("#dataForm").jsonForm();					
		selectRows.push(datas);		
	    console.log(datas); 
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
					<house:token></house:token>
					<ul class="ul-form">
						<li class="form-validate"><label><span
								class="required">*</span>起始面积</label> <input type="text" id="beginArea"
							name="beginArea" value="${drawFeeStdRule.beginArea }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>截止面积</label> <input type="text" id="endArea"
							name="endArea" value="${drawFeeStdRule.endArea }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>普通效果图标准</label> <input type="text" id="colorDrawFee"
							name="colorDrawFee" value="${drawFeeStdRule.colorDrawFee }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
						<li class="form-validate"><label><span
								class="required">*</span>3d效果图标准</label> <input type="text" id="colorDrawFee3D"
							name="colorDrawFee3D" value="${drawFeeStdRule.colorDrawFee3D }"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
