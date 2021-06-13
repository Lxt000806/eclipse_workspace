<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			drawPrice:{  
				validators: {  
					notEmpty: {  
						message: '每平米绘图费不能为空'  
					}
				}  
			},
			drawFeeMin:{  
				validators: {  
					notEmpty: {  
						message: '打底绘图费不能为空'  
					}
				}  
			},
			mustDesignPicCfm:{  
				validators: {  
					notEmpty: {  
						message: '必须图纸审核才发提成不能为空'  
					}
				}  
			},
			prior:{  
				validators: {  
					notEmpty: {  
						message: '优先级不能为空'  
					}
				}  
			},
			commiStdDesignRulePK:{  
				validators: {  
					notEmpty: {  
						message: '提成标准设计费规则不能为空'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'
	});
	
	if("${drawFeeStdRule.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select,#addItem,#updateItem,#delItem").attr("disabled",true);
	}
	
});
		
function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	
	var detail = $("#dataTable").jqGrid("getRowData");
	var params = {
	    "drawFeeStdRuleDetailJson": JSON.stringify(detail),
	};
	
	Global.Form.submit("dataForm","${ctx}/admin/drawFeeStdRule/doSave",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
			    	closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			 art.dialog({
				content: ret.msg,
			});
		}
					
	});
};

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="m_umState" name="m_umState" value="${drawFeeStdRule.m_umState}" />
						<input type="hidden" id="pk" name="pk" value="${drawFeeStdRule.pk}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
							   <li class="form-validate">
			                        <label>收款单位</label>
			                        <house:dict id="payeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
			                                    sqlValueKey="Code" sqlLableKey="Descr" value="${drawFeeStdRule.payeeCode}"></house:dict>
			                    </li>
			                 	<li class="form-validate">
									<label style="width:140px"><span class="required">*</span>必须图纸审核才发提成</label>
									<house:xtdm id="mustDesignPicCfm" dictCode="YESNO" value="${drawFeeStdRule.mustDesignPicCfm}"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate">
									<label><span class="required">*</span>每平米绘图费</label>
									<input type="number" id="drawPrice" name="drawPrice" value="${drawFeeStdRule.drawPrice }"
										step="0.1"/>
								</li>
							    <li class="form-validate">
									<label style="width:140px"><span class="required">*</span>打底绘图费</label>
									<input type="number" id="drawFeeMin" name="drawFeeMin" value="${drawFeeStdRule.drawFeeMin }"
										step="0.1"/>
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate">
									<label><span class="required">*</span>优先级</label>
									<input type="number" id="prior" name="prior" value="${drawFeeStdRule.prior }"/>
								</li>
								<li class="form-validate">
									<label style="width:140px"><span class="required">*</span>提成标准设计费规则</label>
									<house:dict id="commiStdDesignRulePK" dictCode="" sql="select pk, Descr from tCommiStdDesignRule where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr" value="${drawFeeStdRule.commiStdDesignRulePK }"></house:dict>
								</li>
							</div>
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks">${drawFeeStdRule.remarks}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
 		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">效果图公司标准分段明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<jsp:include page="tab_detail.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
