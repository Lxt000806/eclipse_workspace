<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改收款账户</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"></script>
</head>
    
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn">
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="code" name="code" value="${rcvAct.code }"/>
				<input type="hidden" id="expired" name="expired" value="${rcvAct.expired }"/>
				<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh }"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li>
							<label>账户编号</label>
							<input type="text" id="CODE_SHOW" name="CODE_SHOW" style="width:160px;" value="${rcvAct.code }" disabled="disabled"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>账户名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${rcvAct.descr }"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate"><label>收款账号</label> <input type="text" id="cardId"
							name="cardId" style="width:160px;" value="${rcvAct.cardId }" />
						</li>
					</div>
					<div class="validate-group row">
						<li><label>收款单位</label> <house:dict id="payeeCode"
								dictCode=""
								sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
								sqlLableKey="descr" sqlValueKey="code" value="${rcvAct.payeeCode }"></house:dict>
						</li>
					</div>
					<div class="validate-group row">
							<li>
								<label>银行</label> 
								<house:dict id="bankCode" dictCode="" 
									sql="select Code,Code+' '+Descr Descr from tBank where Expired = 'F'" 
									sqlValueKey="Code" sqlLableKey="Descr" value="${rcvAct.bankCode}"/>
							</li>
						</div>
				    <div class="validate-group row">
                        <li>
                            <label>管理员</label>
                            <input id="admin" name="admin" />
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>转账收款</label>
                            <house:xtdm id="allowTrans" dictCode="YESNO" value="${rcvAct.allowTrans}"></house:xtdm>
                        </li>
                    </div>
					<div class="validate-group row">
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${rcvAct.expired }",
								onclick="checkExpired(this)" ${rcvAct.expired=="T"?"checked":"" }/>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
$(function() {
    $("#admin").openComponent_employee({
        showValue: "${rcvAct.admin}"
    })
    
    $("#openComponent_employee_admin").blur()

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			descr:{ 
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				    stringLength: {
						min: 1,  
						max: 60,  
						message: "长度必须在1-60之间" 
				    }
				}  
			}
		}
	});
	
	//保存
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return; /* 验证失败return */
		
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/rcvAct/doUpdate",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 500,
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
	});
});
</script>
</html>
