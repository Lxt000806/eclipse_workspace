<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>人工费用账户新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			actName:{
				validators:{
					notEmpty:{
						message:"户名不能为空"
					}
				}
			},
			cardId:{
				validators:{
					notEmpty:{
						message:"卡号不能为空"
					}
				}
			},
			amount:{
				validators:{
					notEmpty:{
						message:"金额不能为空"
					}
				}
			},
			deductAmount: {
			    validators: {
			        greaterThan: {
			            message: "扣款金额须大于等于0",
			            value: 0
			        }
			    }
			}
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
    
    initializePage("${laborFeeAccount.m_umState}")
    
});

function initializePage(action) {
    switch (action) {
        case "A":
            break
        case "M":
            break
        case "C":
            $("input:not(#deductAmount)").attr("readonly", "true")
            $("#deductAmount_li").show()
            break
        case "V":
            break
        default:
    }
}

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	var datas=$("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
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
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
							        <label><span class="required">*</span>户名</label>
									<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFeeAccount.actName }"/>
				   				 </li>
								<li class="form-validate">
									<label><span class="required">*</span>卡号</label>
									<input type="text" id="cardId" name="cardId" style="width:160px;" value="${laborFeeAccount.cardId }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>开户行</label>
									<input type="text" id="bank" name="bank" style="width:160px;" value="${laborFeeAccount.bank }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>金额</label>
									<input type="text" id="amount" name="amount" 
										onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${laborFeeAccount.amount}"/>
								</li>
							</div>
							<div class="validate-group row">
								<li id="deductAmount_li" class="form-validate" style="display:none">
									<label>扣款</label>
									<input type="text" id="deductAmount" name="deductAmount" value="${laborFeeAccount.deductAmount}"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
