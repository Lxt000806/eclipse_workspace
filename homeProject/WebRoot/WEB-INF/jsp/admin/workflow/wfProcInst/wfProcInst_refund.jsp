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
	<title>借款余额——还款</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#empCode").openComponent_employee({showValue:"${employee.number}",showLabel:"${employee.nameChi}",readonly:true});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			expenseAdvanceNo:{
				validators:{
					notEmpty:{
						message:"预支单号不能为空"
					}
				}
			},
			chgAmount:{
				validators:{
					notEmpty:{
						message:"还款金额不能为空"
					}
				}
			},
			amount:{
				validators:{
					notEmpty:{
						message:"借款金额不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doSaveRefund",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(true);
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

function getAdvanceNo(){
	Global.Dialog.showDialog("EmpAccount",{
		title:"选择预支单",
		url:"${ctx}/admin/wfProcInst/getAdvanceNo",
		postData:{czybh:$("#empCode").val()},
		height: 640,
		width:900,
		returnFun:function(data){
			$("#totalAmount").val(data.amount);
			$("#remainAmount").val(data.nodeductionamount);
			$("#expenseAdvanceNo").val(data.no);
			
			$("#dataForm").data("bootstrapValidator")
		    .updateStatus("expenseAdvanceNo", "NOT_VALIDATED",null)  
		    .validateField("expenseAdvanceNo"); 
		}
	});
}

function chgAdvanceAmount(){
	var chgAmount = $("#chgAmount").val();
	var amount = "${employee.advanceAmount}";
	
	var amount = amount - chgAmount;
	
	if(amount < 0 ){
		art.dialog({
			content:"修改失败,修改后借款金额不能小于0",
		});
		$("#chgAmount").val(0.0);
		return ;
	}
	$("#amount").val(amount);
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
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
									<label><span class="required">*</span>员工编号</label>
									<input type="text" id="empCode" name="empCode" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>余额</label>
									<input type="text" id="amount" name="amount" style="width:160px;" value="${employee.advanceAmount }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
							        <label><span class="required">*</span>预支单</label>
									<input type="text" id="expenseAdvanceNo" name="expenseAdvanceNo"  
											style="width:136px;border-top-right-radius:0;border-bottom-right-radius:0"/>
								    <button type="button" class="btn btn-system" data-disabled="false" 
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;height:24px;padding-top:2.5px" onclick="getAdvanceNo()">
								    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button>
							    </li>	
								<li class="form-validate">
									<label>预支额</label>
									<input type="text" id="totalAmount" name="totalAmount" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>剩余金额</label>
									<input type="text" id="remainAmount" name="remainAmount" style="width:160px;" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>还款金额</label>
									<input type="text" id="chgAmount" name="chgAmount" style="width:160px;" value="0.0" onchange="chgAdvanceAmount()"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="3"></textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
