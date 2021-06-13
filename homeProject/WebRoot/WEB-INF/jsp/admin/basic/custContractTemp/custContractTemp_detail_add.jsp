<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加文本域信息</title>
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
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	if("${custContractTemp.m_umState}"=="V"){
		$("input,#saveBtn").attr("disabled",true);
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

function checkExpired(obj){
	if ($(obj).is(':checked')){
		$('#fieldExpired').val('T');
	}else{
		$('#fieldExpired').val('F');
	}
}
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
					<input type="hidden" id="fieldExpired" name="fieldExpired" value="${custContractTemp.fieldExpired}"/>
					<ul class="ul-form">
						<li>
							<label>书签编码</label>
							<input type="text" id="code" name="code"  value="${custContractTemp.code}"  />
						</li>
						<li>
							<label>表达式</label>
							<input type="text" id="expression" name="expression"  value="${custContractTemp.expression}"  />
						</li>
						<li>
							<label>说明</label>
							<input type="text" id="fieldRemarks" name="fieldRemarks"  value="${custContractTemp.fieldRemarks}"  />
						</li>
						<li>
							<label>序号</label>
							<input type="text" id="dispSeq" name="dispSeq"  value="${custContractTemp.dispSeq}"  />
						</li>
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${custContractTemp.fieldExpired}" 
								onclick="checkExpired(this)" ${custContractTemp.fieldExpired=="T"?"checked":""}/>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
